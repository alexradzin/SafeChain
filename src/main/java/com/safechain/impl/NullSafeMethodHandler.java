package com.safechain.impl;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;

import javassist.util.proxy.MethodHandler;

/**
 * Implementation of {@link MethodHandler} that implements logic of "smart reference". 
 * It wraps object with dynamic proxy or returns original object if end of the invocation
 * chain is reached.
 * @author alexr
 *
 */
class NullSafeMethodHandler implements MethodHandler {
	private Object obj;
	private ProxyInstanceFactory proxyInstanceFactory; 
	
	NullSafeMethodHandler(ProxyInstanceFactory proxyInstanceFactory, Object obj) {
		this.proxyInstanceFactory = proxyInstanceFactory;
		this.obj = obj;
	}

	@Override
	public Object invoke(Object self, Method m, Method proceed, Object[] args)
			throws Throwable {
		m.setAccessible(true);
		Object result = obj == null ? null : m.invoke(obj, args);
		Class<?> type = m.getReturnType();
		ChainStateHolder.appendToChain(m.getName());
		return wrap(result, type);
	}

	
	@SuppressWarnings("unchecked")
	private Object wrap(final Object obj, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if (clazz.isAssignableFrom(Iterator.class)) {
			ChainStateHolder.clearChain();
			return new EndSafeIterator<Object>((Iterator<Object>)obj);
		}
		if (clazz.isAssignableFrom(Enumeration.class)) {
			ChainStateHolder.clearChain();
			return new EndSafeEnumeration<Object>((Enumeration<Object>)obj);
		}
		return ChainStateHolder.isChainEnd() ? obj :  proxyInstanceFactory.proxy(obj, clazz);
	}
}
