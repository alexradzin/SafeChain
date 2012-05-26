package com.safechain.impl;


import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;

/**
 * Creates instance of dynamic proxy created by {@link ProxyClassFactory}
 * 
 * @see ProxyClassFactory
 * @author alexr
 *
 */
public class ProxyInstanceFactory {
	private ProxyClassFactory proxyClassFactory = new ProxyClassFactory(); 
	
	
	public Object proxy(final Object obj, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		Class<?> c = proxyClassFactory.createProxyClass(clazz);
		
		MethodHandler mi = new NullSafeMethodHandler(this, obj);

		Object proxy = c.newInstance();
		((Proxy)proxy).setHandler(mi);
		

		return proxy;
	}
	
	// This is nice-to-have feature: ability to retrieve unwrapped object.
	
	// add $() method
//	ClassFile cf = null;
//	MethodInfo $ =  null;
//	cf.addMethod($);

	// $(person).getAddress().getPhone().$();
	
}
