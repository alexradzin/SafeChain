package com.safechain.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import javassist.bytecode.Opcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.safechain.NullSafeChain;

/**
 * This class is responsible on retrieving the invocation chain, i.e. sequence of methods
 * invoked in one chain.
 * For example method {@link #getInvokationChains(String, String, int, String)} must return {@code getSpouse()}
 * and {@code getFirstName()} for invocation chain {@code person.getSpouse().getFirstName()}
 * <br/>
 * This class is public because it is used by {@link NullSafeChain} from other package.
 * 
 * @author alexr
 */
public class InvocationChainRetriever {
	private static Pattern allParamsPattern = Pattern.compile("(\\(.*?\\))");
	private static Pattern paramsPattern = Pattern.compile("(\\[?)(C|Z|S|I|J|F|D|(:?L[^;]+;))");
	private Map<InvocationPosition, List<List<String>>> cache = new HashMap<InvocationPosition, List<List<String>>>();
	private static Logger logger = LoggerFactory.getLogger(InvocationChainRetriever.class);
	
	public List<List<String>> getInvokationChains(String className, String methodName, int lineNumber, String wrapperName) {
		return getInvokationChains(new InvocationPosition(className, methodName, lineNumber, wrapperName));
	}

	
	private List<List<String>> getInvokationChains(InvocationPosition position) {
		List<List<String>> cached = cache.get(position);
		if (cached != null) {
			return cached;
		}
		
		try {
			List<List<String>> chains = retrieveInvokationChains(position);
			if (chains == null) {
				throw new IllegalStateException("Cannot retrieve invocation chain for " + position);
			}
			cache.put(position, chains);
			return chains;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot retrieve invocation chain for " + position, e);
		}
	}

	private List<List<String>> retrieveInvokationChains(InvocationPosition position) throws IOException, BadBytecode {
		List<List<String>> chains = new ArrayList<List<String>>();
		String className = position.getClazz();
		InputStream in = this.getClass().getResourceAsStream("/" + className.replace('.', '/') + ".class");
		ClassFile cf = new ClassFile(new DataInputStream(in));
		
		String wrapperName = position.getWrapperName();
		String methodName = position.getMethodName();
		int lineNumber = position.getLineNumber();
		
		for (Object m : cf.getMethods()) {
			MethodInfo minfo = (MethodInfo)m;
			CodeAttribute ca = minfo.getCodeAttribute();
			if (!methodName.equals(minfo.getName())) {
				continue;
			}
		
			CodeIterator ci = ca.iterator();
			
			int op = -1;
			int prevOp = -1;
			//int aloadCount = 0;
			int argCount = 0;
			
			while(ci.hasNext()) {
				int index = ci.next();
				prevOp = op;
				op = ci.byteAt(index);
				int codeLineNumber = minfo.getLineNumber(index);
				if (codeLineNumber > lineNumber) {
					break;
				}
				
				if (isInvoke(op)) {
					logger.debug(Mnemonic.OPCODE[op] + "  " + getMethodName(cf, ci, index, op));
				} else {
					logger.debug(Mnemonic.OPCODE[op]);
				}
				
				
				
				if (isAload(op)) {
					continue;
				}

				
				if(Opcode.LDC == op) {
					continue;
				} 
				if(Opcode.GETFIELD == op) {
	                int a1 = ci.s16bitAt(index + 1);
	                String fieldName = " " + cf.getConstPool().getFieldrefName(a1); // OK. returns "strings"
	                logger.debug("\t\tfield=" + fieldName);
					continue;
				} 
				
				
				if (isInvoke(op)) {
	                String invokedMethodName = getMethodName(cf, ci, index, op);
	                if (wrapperName.equals(invokedMethodName) && codeLineNumber == lineNumber) {
	        			List<String> chain = new ArrayList<String>();
	        			int paramCount = 0;
	        			boolean popAfterInvoke = false;
	        			while(ci.hasNext()) {
	        				index = ci.next();
	        				prevOp = op;
	        				op = ci.byteAt(index);
	        				logger.debug("\t" + Mnemonic.OPCODE[op] + "   " + (isInvoke(op) ? getMethodInfo(cf, ci, index, op) : ""));

	        				if (isArgumentPass(op) || (Opcode.INVOKESTATIC == op && isInvoke(op) && !getMethodInfo(cf, ci, index, op).contains(")V"))) {
	        					argCount++;
	        				}
	        				/////////////////////// ????????????????
	        				// Looks like a strange patch.
	        				// The idea is that if number of parameters so far is equal to number of arguemnts 
	        				// and current operation is not invocation of regular (non static) method it should be the end of the chain.
	        				if (paramCount == argCount && !(op == Opcode.INVOKEINTERFACE || op == Opcode.INVOKESPECIAL || op == Opcode.INVOKEVIRTUAL) && !chain.isEmpty()) {
	        					break;
	        				}
	        				
	        				
	        				if (Opcode.POP == op && (prevOp == Opcode.INVOKEVIRTUAL || prevOp == Opcode.INVOKEINTERFACE)) {
	        					popAfterInvoke = true;
	        				}
	        				if (!popAfterInvoke && op == Opcode.INVOKESTATIC) {
	        					// static method that is not called as continuation of chain:
	        					// but wraps the invocation chain or is called into an argument list of one of the methods in chain. 
	        					continue; 
	        				}
	        				if (isInvoke(op)) {
		        				int methodAddress = ci.s16bitAt(index + 1);
		        				int nParams = getMethodParamCount(op, cf.getConstPool(), methodAddress);
//		        				int nParams = getMethodParamCount(cf.getConstPool().getMethodrefType(methodAddress));
//		        				if (op == Opcode.INVOKEVIRTUAL || op == Opcode.INVOKEINTERFACE) {
//		        					nParams++; // this is sent as yet another argument.
//		        					argCount++;
//		        				}
		        				paramCount += nParams;
	        				}
	        				
	        				
	        				if (isInvoke(op)) {
	        					chain.add(getMethodName(cf, ci, index, op));
	        				}
	        				logger.debug("			paramCount: {}, argCount={}, chain={}", new Object[] {new Integer(paramCount), new Integer(argCount), chain});
	        				if ((Opcode.LDC == op && prevOp == Opcode.POP) || Opcode.RETURN == op) {
	        					break;
	        				}
	        				if (paramCount == argCount && ((prevOp == Opcode.POP && Opcode.NEW == op) || isOperator(op))) {
	        					break;
	        				}
	        				if (op == Opcode.POP) {
	        					break; //// just to try.
	        				}
	        				if (paramCount > argCount) {
	        					logger.debug("paramCount > argCount: {} > {} chain={}", new Object[] {new Integer(paramCount), new Integer(argCount), chain});
	        					chain = chain.subList(0, chain.size() - 1);
	        					break; // ????
	        				}
	        			}
	        			chains.add(chain);
	                }
				}
				
			}
		}
			
		return chains;
	}
	
	
	private boolean isAload(int op) {
		return op == Opcode.AALOAD || op == Opcode.ALOAD_0  || op == Opcode.ALOAD_1  || op == Opcode.ALOAD_2 || op == Opcode.ALOAD_3;
	}

	private boolean isConst(int op) {
		return op == Opcode.ACONST_NULL || 
				op == Opcode.ICONST_0  || op == Opcode.ICONST_1  || op == Opcode.ICONST_2  || op == Opcode.ICONST_3  || op == Opcode.ICONST_4 || op == Opcode.ICONST_5;
	}

	private boolean isPush(int op) {
		return op == Opcode.BIPUSH || op == Opcode.SIPUSH;
	}

	private boolean isIload(int op) {
		return op == Opcode.ILOAD || op == Opcode.ILOAD_0  || op == Opcode.ILOAD_1  || op == Opcode.ILOAD_2 || op == Opcode.ILOAD_3;
	}
	
	boolean isInvoke(int op) {
		return op == Opcode.INVOKEINTERFACE || op == Opcode.INVOKESPECIAL || op == Opcode.INVOKESTATIC || op == Opcode.INVOKEVIRTUAL;
	}
	
	boolean isLdc(int op) {
		return op == Opcode.LDC || op == Opcode.LDC_W || op == Opcode.LDC2_W;
	}
	
	//TODO: add other operators
	boolean isOperator(int op) {
		return op == Opcode.LCMP;
	}
	
	//TODO this method must be improved to support other cases like dload* etc
	private boolean isArgumentPass(int op) {
		return isConst(op) || isAload(op) || isIload(op) || isPush(op) || isLdc(op); // ||  
	}
	
	static String getMethodName(ClassFile cf, CodeIterator ci, int index, int op) {
        int methodAddress = ci.s16bitAt(index + 1);
        ConstPool pool = cf.getConstPool();
        
        
        
        
        switch(op) {
	        case Opcode.INVOKEINTERFACE:
	        	return pool.getInterfaceMethodrefName(methodAddress);
	        case Opcode.INVOKESPECIAL:  
	    	case Opcode.INVOKESTATIC: 
	    	case Opcode.INVOKEVIRTUAL:
	    		return pool.getMethodrefName(methodAddress);
    		default: throw new IllegalStateException("Unsupported operation code " + op + " " + Mnemonic.OPCODE[op]);
        }
        
	}
	
	private String getMethodInfo(ClassFile cf, CodeIterator ci, int index, int op) {
        int methodAddress = ci.s16bitAt(index + 1);
        ConstPool pool = cf.getConstPool();
        
        
        switch(op) {
	        case Opcode.INVOKEINTERFACE:
	        	return pool.getInterfaceMethodrefName(methodAddress);
	        case Opcode.INVOKESPECIAL:  
	    	case Opcode.INVOKESTATIC: 
	    	case Opcode.INVOKEVIRTUAL:
	    		return pool.getMethodrefName(methodAddress) + " type=" + pool.getMethodrefType(methodAddress) + " className=" + pool.getMethodrefClassName(methodAddress);
    		default: throw new IllegalStateException("Unsupported operation code " + op + " " + Mnemonic.OPCODE[op]);
        }
        
	}
	
	
//	int getMethodParamCount(ConstPool pool, int methodAddress) {
//		return getMethodParamCount(pool.getMethodrefType(methodAddress));
//	}
	
	int getMethodParamCount(String methodRefType) {
		Matcher m = allParamsPattern.matcher(methodRefType);
		if (!m.find()) {
			throw new IllegalArgumentException("Method signature does not contain parameters");
		}
		String paramsDescriptor = m.group(1);
		Matcher mParam = paramsPattern.matcher(paramsDescriptor);
		
		int count = 0;
		while (mParam.find()) {
			count++;
		}
		return count;
	}
	
	private int getMethodParamCount(int op, ConstPool constPool, int methodAddress) {
		switch (op) {
			case Opcode.INVOKEINTERFACE:
				return getMethodParamCount(constPool.getInterfaceMethodrefType(methodAddress));
			case Opcode.INVOKEVIRTUAL:
			case Opcode.INVOKESPECIAL:
			case Opcode.INVOKESTATIC:
				return getMethodParamCount(constPool.getMethodrefType(methodAddress));
			default: throw new IllegalStateException();
		}
	}
	
}
