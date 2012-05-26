package com.safechain.impl;

import java.io.DataInputStream;
import java.io.InputStream;

import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;

import org.junit.Assert;
import org.junit.Test;

import com.safechain.impl.InvocationChainRetriever;

public class MethodParamParserTest {
	@Test
	public void testVoidNoArgs() throws Exception {
		test(MethodCallSamples.class, "v0", 0);
	}

	@Test
	public void testVoidLongArg() throws Exception {
		test(MethodCallSamples.class, "vlong", 1);
	}
	
	@Test
	public void testVoidStringArg() throws Exception {
		test(MethodCallSamples.class, "vString", 1);
	}

	@Test
	public void testVoidIntLongArgs() throws Exception {
		test(MethodCallSamples.class, "vintlong", 2);
	}

	@Test
	public void testVoidStringLongArgs() throws Exception {
		test(MethodCallSamples.class, "vstringlong", 2);
	}
	
	@Test
	public void testVoidLongStringArgs() throws Exception {
		test(MethodCallSamples.class, "vlongstring", 2);
	}
	
	@Test
	public void testVoidBooleanArg() throws Exception {
		test(MethodCallSamples.class, "vboolean", 1);
	}

	@Test
	public void testVoidShortArg() throws Exception {
		test(MethodCallSamples.class, "vshort", 1);
	}

	@Test
	public void testVoidFloatArg() throws Exception {
		test(MethodCallSamples.class, "vfloat", 1);
	}

	@Test
	public void testVoidDoubleArg() throws Exception {
		test(MethodCallSamples.class, "vdouble", 1);
	}

	@Test
	public void testVoidCharArg() throws Exception {
		test(MethodCallSamples.class, "vchar", 1);
	}

	@Test
	public void testVoidObjectArg() throws Exception {
		test(MethodCallSamples.class, "vMethods", 1);
	}
	
	@Test
	public void testVoidIntArrayArg() throws Exception {
		test(MethodCallSamples.class, "vintarr", 1);
	}
	
	@Test
	public void testVoidIntArrayIntArg() throws Exception {
		test(MethodCallSamples.class, "vintarr2", 2);
	}
	
	@Test
	public void testVoidStringArrayArg() throws Exception {
		test(MethodCallSamples.class, "vstringarr", 1);
	}

	@Test
	public void testVoidListArg() throws Exception {
		test(MethodCallSamples.class, "vlist", 1);
	}

	
	
	private void test(Class<?> clazz, String methodName, int expectedParamsCount) throws Exception {
		InvocationChainRetriever retriever = new InvocationChainRetriever();
		
		
		String className = clazz.getName();
		InputStream in = this.getClass().getResourceAsStream("/" + className.replace('.', '/') + ".class");
		ClassFile cf = new ClassFile(new DataInputStream(in));
		for (Object m : cf.getMethods()) {
			MethodInfo minfo = (MethodInfo)m;
			CodeAttribute ca = minfo.getCodeAttribute();
			if (!"test".equals(minfo.getName())) {
				continue;
			}
		
			CodeIterator ci = ca.iterator();
			
	        ConstPool pool = cf.getConstPool();
			
			while(ci.hasNext()) {
				int index = ci.next();
				int op = ci.byteAt(index);
				if (!retriever.isInvoke(op)) {
					continue;
				}
				if (!methodName.equals(InvocationChainRetriever.getMethodName(cf, ci, index, op))) {
					continue;
				}
		        int methodAddress = ci.s16bitAt(index + 1);
		        String methodRefType = pool.getMethodrefType(methodAddress);
				int paramCount = retriever.getMethodParamCount(methodRefType);
				Assert.assertEquals(expectedParamsCount, paramCount);
				return;
			}
		}
		Assert.fail("Method " + methodName + " is not found in samples class " + clazz);
	}
	
	
}
