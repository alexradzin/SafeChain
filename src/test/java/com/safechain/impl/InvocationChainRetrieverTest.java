package com.safechain.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.safechain.CallerRetriever;
import com.safechain.ChainSamples;
import com.safechain.impl.InvocationChainRetriever;

public class InvocationChainRetrieverTest {
	private InvocationChainRetriever retriever = new InvocationChainRetriever();
	
	@Test
	public void privateInvokeEmptyChainOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[0]});
	}

	@Test
	public void privateInvokeOneCallOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"length"}});
	}

	
	@Test
	public void privateInvokeOneCallWithConstArgOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}
	
	
	@Test
	public void privateInvokeTwoCallsWithConstArgOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	
	@Test
	public void privateInvokeTwoCallsWithConstExprArgOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}
	
		
	@Test
	public void privateInvokeTwoCallsWithVarExprArgOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	
	@Test
	public void privateInvokeSevaralCallsWithArgsOneLine() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "substring", "toString", "toUpperCase", "toCharArray"}});
	}
	
	
	@Test
	public void privateInvokeSevaralCallsWithStaticCallInArgs() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "substring", "toString", "toUpperCase", "toCharArray"}});
	}
	
	
//	@Test
//	public void test() {
//		test(ChainSamples.class, "test", 51, "nullsafe", new String[][] {new String[] {"substring", "substring", "toString", "valueOf"}});
//	}
	
	
	@Test
	public void invokeChainThenCode() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	
	@Test
	public void invokeChainIntoMethodArgument() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainIntoMethodArgument2() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainIntoMethodArgument3() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainIntoMethodArgument4() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainIntoStaticMethodArgument() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}
	
	@Test
	public void invokeChainIntoStaticMethodArgument2() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}
		
	@Test
	public void invokeChainIntoStaticMethodArgument3() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainIntoStaticMethodArgumente4() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "length"}});
	}

	@Test
	public void invokeChainReturningArrayIntoStaticMethodArgument() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "split"}});
	}
	
	@Test 
	public void invokeChainWithStaticMethod() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"/*, "valueOf"*/}});
	}

	@Test 
	public void invokeChainWithStaticMethodDouble() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", /*"valueOf"*/}});
	}

	@Test
	public void invokeChainIntoVirtualMethodObjectArgument() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}
	
	@Test
	public void invokeChainIntoVirtualMethodObjectArgument2() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring", "subSequence"}});
	}

	@Test
	public void invokeChainIntoVirtualMethodObjectArgument3() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}
	
	
	@Test
	public void invokeChainThatReturnsArray() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"split"}});
	}
	
	@Test
	public void nullArray1() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"split"}}); 
	}
	
	@Test
	public void nullArray2() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"split"}}); 
	}

	@Test
	public void nullArray3() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"split"}}); 
	}
	
	
	@Test
	public void invokeChainAndThenConstructor() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}}); 
	}

	@Test
	public void invokeChainAndThenIf() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}

	@Test
	public void invokeChainAndThenFor() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}

	@Test
	public void invokeChainAndThenWhile() {
		test(ChainSamples.class, "nullsafe", new String[][] {new String[] {"substring"}});
	}
	
	private void test(Class<?> clazz, String wrapperName, String[][] expecteds) {
		String methodName = CallerRetriever.getCaller(getClass(), Test.class).getMethodName();
		int lineNumber = CallerRetriever.getMarkedPoint(clazz, methodName).getLineNumber();
		test(clazz, methodName, lineNumber, wrapperName, expecteds);
	}
	
	private void test(Class<?> clazz, String methodName, int lineNumber, String wrapperName, String[][] expecteds) {
		List<List<String>> actuals = retriever.getInvokationChains(clazz.getName(), methodName, lineNumber, wrapperName);
		Assert.assertEquals(toLists(expecteds), actuals);
	}
	
	private List<List<String>> toLists(String[][] arr) {
		if (arr == null) {
			return null;
		}
		List<List<String>> result = new ArrayList<List<String>>();
		for (String[] a : arr) {
			result.add(Arrays.asList(a));
		}
		return result;
	}
}
