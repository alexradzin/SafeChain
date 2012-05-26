package com.safechain.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.ProxyFactory;

/**
 * Creates proxy for given original class. Wraps Javassist's API. 
 * Caches generated proxy class for better performance.
 * 
 * @see ProxyInstanceFactory
 * @author alexr
 *
 */
public class ProxyClassFactory {
	private Map<Class<?>, Class<?>> cache = new HashMap<Class<?>, Class<?>>();
	
	
	Class<?> createProxyClass(Class<?> clazz) {
		Class<?> cached = cache.get(clazz);
		if (cached != null) {
			return cached;
		}
		
		
		ProxyFactory f = new ProxyFactory();
		
		if (clazz.isInterface()) {
			f.setInterfaces(new Class[] {clazz});
		} else {
			f.setSuperclass(clazz);
		}
		
		f.setFilter(new MethodFilter() {
			public boolean isHandled(Method m) {
				return true;
			}
		});
		

		Class<?> c = f.createClass();
		cache.put(clazz, c);
		
		return c;
	}
}
