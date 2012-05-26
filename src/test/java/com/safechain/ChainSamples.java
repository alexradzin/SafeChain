package com.safechain;

import static com.safechain.CallerRetriever.mark;
import static com.safechain.MyHelper.voidStat;

import com.safechain.NullSafeChain;


public class ChainSamples {
	private NullSafeChain chain = new NullSafeChain(); NullSafeChain chain2 = new NullSafeChain();
	private MyHelper helper = new MyHelper();
	@SuppressWarnings("unused")
	private void privateInvokeEmptyChainOneLine() {
		mark(); chain.nullsafe(""); // line 8
	}

	@SuppressWarnings("unused")
	private void privateInvokeOneCallOneLine() {
		mark(); chain.nullsafe("").length(); // line 13
	}

	@SuppressWarnings("unused")
	private void privateInvokeOneCallWithConstArgOneLine() {
		mark(); chain.nullsafe("").substring(0); // line 18
	}

	@SuppressWarnings("unused")
	private void privateInvokeTwoCallsWithConstArgOneLine() {
		mark(); chain.nullsafe("").substring(0).length(); // line 23
	}

	@SuppressWarnings("unused")
	private void privateInvokeTwoCallsWithConstExprArgOneLine() {
		mark(); chain.nullsafe("").substring(1+2).length(); // line 28
	}

	@SuppressWarnings("unused")
	private void privateInvokeTwoCallsWithVarExprArgOneLine() {
		int n = 5;
		int m = 6;
		mark(); chain.nullsafe("").substring(n - m + 1).length(); // line 35
	}

	
	@SuppressWarnings("unused")
	private void privateInvokeSevaralCallsWithArgsOneLine() {
		int n = 5;
		int m = 6; 
		mark(); chain.nullsafe("hello").substring(0).substring(n, m).toString().toUpperCase(null).toCharArray(); // line 43
		//chain.nullsafe("hello").substring("a".indexOf("a")).substring(n, m).toString().toUpperCase(java.util.Locale.getDefault()).toCharArray(); // line 43
	}

	@SuppressWarnings("unused")
	private void privateInvokeSevaralCallsWithStaticCallInArgs() {
		int n = 5;
		int m = 6; 
		mark(); chain.nullsafe("hello").substring(0).substring(n, m).toString().toUpperCase(java.util.Locale.getDefault()).toCharArray(); // line 51
	}
	

	@SuppressWarnings({ "unused"})
	private void invokeChainThenCode() {
		mark(); chain.nullsafe("").substring(0).length(); // line 57
		String str = "hi";
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainIntoMethodArgument() {
		mark(); String s = ""; foo(foo(chain.nullsafe("").substring(0).length())); // line 63
		//new MyHelper().foo(chain.nullsafe("").substring(0).length()); // line 63
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainIntoMethodArgument2() {
		mark(); foo(1);helper.foo(helper.foo(chain.nullsafe("").substring(0).length())); // line 69
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainIntoMethodArgument3() {
		mark(); foo(1);helper.foo(foo(chain.nullsafe("").substring(0).length())); // line 74
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainIntoMethodArgument4() {
		mark(); foo(1);foo(helper.foo(chain.nullsafe("").substring(0).length())); // line 79
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainIntoStaticMethodArgument() {
		mark(); MyHelper.stat(chain.nullsafe("").substring(0).length()); // line 84
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainIntoStaticMethodArgument2() {
		mark(); localStat(chain.nullsafe("").substring(0).length()); // line 89
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainIntoStaticMethodArgument3() {
		int iii = 555;
		mark(); localStat(iii, chain.nullsafe("").substring(0).length()); // line 95
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainIntoStaticMethodArgumente4() {
		int iii = 555;
		mark(); voidStat(iii, chain.nullsafe("").substring(0).length()); // line 101
	}

	@SuppressWarnings({ "unused"})
	private void invokeChainReturningArrayIntoStaticMethodArgument() {
		int iii = 555;
		mark(); voidStat(iii, chain.nullsafeArray(chain.nullsafe("").substring(0).split(","), 0)); // line 107
	}

	@SuppressWarnings({ "unused", "static-access"})
	private void invokeChainWithStaticMethod() {
		mark(); chain.nullsafe("").substring(0).valueOf(true); // line 112
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainIntoVirtualMethodObjectArgument() {
		mark(); helper.foo(chain.nullsafe("").substring(0)); // line 117
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainIntoVirtualMethodObjectArgument2() {
		Object obj = new Object();
		mark(); helper.foo(helper.foo(chain.nullsafe("").substring(0).subSequence(0, 0), obj)); // line 123
	}
	
	@SuppressWarnings({ "unused"})
	private void invokeChainThatReturnsArray() {
		mark(); MyHelper.voidStat(123, chain.nullsafeArray(chain.nullsafe("").split(""), 0)); // line 128
	}

	@SuppressWarnings({ "unused"}) 
	private void nullArray1() {
		mark(); MyHelper.stat(chain.nullsafeArray(chain.nullsafe("").split(""), 0)); // line 133 //ok
 	}

	@SuppressWarnings({ "unused"}) 
	private void nullArray2() {
		String s = "";
		mark(); MyHelper.stat(chain.nullsafeArray(chain.nullsafe(new String("")).split(s), 0)); // line 139 // fails, too long array: split, nullSafeArray
 	}
	
	@SuppressWarnings({ "unused"}) 
	private void nullArray3() {
		String s = "";
		mark(); MyHelper.stat(chain2.nullsafeArray(chain.nullsafe(s).split(s), 0)); // line 145 // fails, empty array
 	}
	
	@SuppressWarnings({ "unused"}) // numeric constant greater than 5
	private void invokeChainIntoVirtualMethodObjectArgument3() {
		mark(); helper.foo(chain.nullsafe("").substring(10)); // line 150
	}
	
	@SuppressWarnings({ "unused", "static-access"})
	private void invokeChainWithStaticMethodDouble() {
		mark(); chain.nullsafe("").substring(0).valueOf(1234356789.987654321); // line 155
	}

	@SuppressWarnings("unused")
	private void invokeChainAndThenConstructor() {
		mark(); chain.nullsafe("").substring(0);
		new String();
	}

	@SuppressWarnings("unused")
	private void invokeChainAndThenIf() {
		long now = System.currentTimeMillis();
		mark(); chain.nullsafe("").substring(0);
		if (now < 0) {
			System.out.println("The world is not created yet " + (now < 0 ? "nnn" : "ppp"));
		}
	}

	@SuppressWarnings("unused")
	private void invokeChainAndThenFor() {
		long now = System.currentTimeMillis();
		mark(); chain.nullsafe("").substring(0);
		for(;"".length() < 0;);
	}
	
	@SuppressWarnings("unused")
	private void invokeChainAndThenWhile() {
		long now = System.currentTimeMillis();
		mark(); chain.nullsafe("").substring(0);
		while("".length() < 0);
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	private void test() {
		int n = 5;
		int m = 6; 
		mark(); chain.nullsafe("hello").substring(1).substring(n, m).toString().valueOf("").indexOf(""); // line 86
		//chain.nullsafe("hello").substring(1).substring(n, m).toString().indexOf(String.valueOf("")); // line 51
		//chain.nullsafe("hello").substring(1).substring(n, m).toString().indexOf(""); // line 51
	}

	
	public void foo(Object obj) {
		// do nothing
	}

	public int foo(int i) {
		return i;
	}
	
	public static int localStat(int i) {
		return i;
	}

	public static int localStat(int i, int j) {
		return i + j;
	}
}
