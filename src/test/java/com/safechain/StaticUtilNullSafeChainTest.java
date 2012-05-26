package com.safechain;


import static com.safechain.NullSafeUtil.$arr;

import com.safechain.annotation.SafeChainWrapper;


public class StaticUtilNullSafeChainTest extends NullSafeChainTest {

	@SafeChainWrapper
	@Override
	protected <T> T wrap(T obj) {
		return NullSafeUtil.$(obj);
	}

	@SafeChainWrapper
	@Override
	protected <T> T wrapArray(T[] arr, int i) {
		return $arr(arr, i);
	}

}
