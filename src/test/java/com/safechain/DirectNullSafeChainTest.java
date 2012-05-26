package com.safechain;

import com.safechain.NullSafeChain;
import com.safechain.annotation.SafeChainWrapper;


public class DirectNullSafeChainTest extends NullSafeChainTest {
	private final NullSafeChain chain = new NullSafeChain();
	

	@SafeChainWrapper
	@Override
	protected <T> T wrap(T obj) {
		return chain.nullsafe(obj);
	}
	
	@SafeChainWrapper
	@Override
	protected <T> T wrapArray(T[] arr, int i) {
		return chain.nullsafeArray(arr, i);
	}


}
