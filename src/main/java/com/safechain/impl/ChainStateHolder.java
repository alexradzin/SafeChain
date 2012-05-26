package com.safechain.impl;

import java.util.List;

import com.safechain.NullSafeChain;

/**
 * This class provides access to the state of the system that is stored in {@code ThreadLocal} variables.
 * The class is {@code public} because it is used by {@link NullSafeChain} from other package.
 * @author alexr
 */
public class ChainStateHolder {
	private final static ThreadLocal<List<String>> chain = new ThreadLocal<List<String>>(); 
	private final static ThreadLocal<List<List<String>>> possibleChains = new ThreadLocal<List<List<String>>>(); 
	private final static ThreadLocal<Integer> currentChainIndex = new ThreadLocal<Integer>(); 


	public static void setChain(List<String> newChain) {
		chain.set(newChain);
	}
	
	
	public static void appendToChain(String method) {
		chain.get().add(method);
	}

	//TODO: improve this method. 
	// The currentChainIndex might contain wrong value if exception was thrown during execution or if
	// current line contains conditional statement. So, we really have to *look for* the matching possible
	// chain starting from currentChainIndex
	public static boolean isChainEnd() {
		boolean chainEnd =  chain.get().equals(possibleChains.get().get(currentChainIndex.get()));
		if (chainEnd) {
			clearChain();
		}
		return chainEnd;
	}
	
	public static void clearChain() {
		currentChainIndex.set(null);
	}

	
	public static void setPossibleChains(List<List<String>> chains) {
		possibleChains.set(chains);
	}

	
	public static Integer getCurrentChainIndex() {
		return currentChainIndex.get();
	}

	public static void setCurrentChainIndex(Integer index) {
		currentChainIndex.set(index);
	}
}
