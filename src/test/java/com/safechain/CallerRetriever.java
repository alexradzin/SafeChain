package com.safechain;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallerRetriever {
	private final static String MARKING_EXCEPTION_MESSAGE = "MARKING_EXCEPTION_MESSAGE";
	private static boolean mark;

	
	public static StackTraceElement getCaller(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		return getCaller(clazz, annotationClass, Thread.currentThread().getStackTrace());
	}
	
	
	private static StackTraceElement getCaller(Class<?> clazz, Class<? extends Annotation> annotationClass, StackTraceElement[] stackTrace) {
		String requiredClassName = clazz.getName();
		for (StackTraceElement elem : stackTrace) {
			String className = elem.getClassName();
			if (!requiredClassName.equals(className)) {
				continue;
			}
			
			String methodName = elem.getMethodName();
			
			try {
				if (annotationClass == null || Class.forName(className).getDeclaredMethod(methodName).getAnnotation(annotationClass) != null) {
					return elem;
				}
			} catch (NoSuchMethodException e) {
				// this happens if method requires arguments. We just ignore such methods here.
				continue;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		throw new IllegalArgumentException("Cannot find caller method");
	}
	
	
	public static StackTraceElement getMarkedPoint(Class<?> clazz, String methodName) {
		mark = true;
		try {
			Method method = clazz.getDeclaredMethod(methodName);
			method.setAccessible(true);
			method.invoke(clazz.newInstance());
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			String msg = cause.getMessage();
			if (!MARKING_EXCEPTION_MESSAGE.equals(msg)) {
				throw new IllegalStateException(e);
			}
			return getCaller(clazz, null, cause.getStackTrace());
		}
		mark = false;
		throw new IllegalStateException("Special marking exception was not thrown");
	}
	
	
	public static void mark() {
		if (mark) {
			throw new RuntimeException(MARKING_EXCEPTION_MESSAGE);
		}
	}
	
}
