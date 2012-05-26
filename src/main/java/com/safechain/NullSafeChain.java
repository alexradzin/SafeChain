package com.safechain;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.safechain.annotation.SafeChainWrapper;
import com.safechain.impl.ChainStateHolder;
import com.safechain.impl.InvocationChainRetriever;
import com.safechain.impl.ProxyInstanceFactory;


/**
 * The entry point to {@code NullSafeChain}.
 * Wraps given object with dynamic proxy that provides functionality of null safe reference.
 * For example code like {@code person.getSpouse().getFirstName()} throws {@link NullPointerException}
 * if {@code person} is single and returns {@code null} if person is wrapped with null-safe chain:
 * {@code chain.nullsafe(person).getSpouse().getFirstName()}.
 * 
 * Similar problem may happen when working with collections.
 * For example code {@code person.getChildren().iterator().next().getFirstName()} throws {@link NoSuchElementException}
 * if person does not have children, so method {@code getChildren()} returns empty collection.
 * 
 * Arrays require special processing because access to array element is not done using method but using syntax built-in
 * into java language: {@code arr[index]}. This class provides series of methods {@code nullsafeArray()} for 
 * 1D, 2D and 3D arrays of generic and primitive types. 
 * 
 * Although this class actually implements the core technology of this library the author supposes that 
 * class {@link NullSafeUtil} provides more convenient way to use it. Please see description of class {@link NullSafeUtil}
 * for details. 
 *  
 * @author alexr
 */
@SafeChainWrapper
public class NullSafeChain {
	private ProxyInstanceFactory proxyInstanceFactory = new ProxyInstanceFactory();
	
	private InvocationChainRetriever chainRetriever = new InvocationChainRetriever();
	private Collection<StackTraceElement> libraryClassesInChain = new TreeSet<StackTraceElement>(new Comparator<StackTraceElement>() {
		public int compare(StackTraceElement element, StackTraceElement template) {
			@SuppressWarnings("rawtypes")
			List<Comparable> templateData = Arrays.<Comparable>asList(template.getClassName(), template.getMethodName());
			@SuppressWarnings("rawtypes")
			List<Comparable> elementData = Arrays.<Comparable>asList(element.getClassName(), element.getMethodName());
			
			for (int i = 0;  i < templateData.size();  i++) {
				if (templateData.get(i) != null && !templateData.get(i).equals("")) {
					@SuppressWarnings("unchecked")
					int comp = templateData.get(i).compareTo(elementData.get(i));
						
					if (comp != 0) {
						return comp;
					}
				}
			}
			return 0;
		}
		public boolean equals(Object obj) {
			return false;
		}
	});
	
	private final static ThreadLocal<Integer> currentLine = new ThreadLocal<Integer>(); 
	
	
	public NullSafeChain() {
	}
	
	/**
	 * Wraps object with proxy.
	 * @param instance
	 * @return
	 */
	public <T> T nullsafe(T instance) {
		try {
			return (T)proxyImpl(instance);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T nullsafeArray(T[] arr, int i) {
		return arr == null || arr.length <= i ? null : arr[i];
	}

	public <T> T nullsafeArray(T[][] arr, int i, int j) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j ? null : arr[i][j];
	}

	public <T> T nullsafeArray(T[][][] arr, int i, int j, int k) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j || arr[i][j] == null || arr[i][j].length <= k ? null : arr[i][j][k];
	}

	@SuppressWarnings("unchecked")
	public <T> T nullsafeArray(Object arr, Class<T> clazz, int ... i) {
		if (arr == null) {
			return null;
		}
		if (!arr.getClass().isArray()) {
			throw new IllegalArgumentException(arr + " is not array");
		}

		Object array = arr;
		for (int index : i) {
			array = Array.get(array, index);
		}
		
		return (T)array; // n-dimensional array
	}

	
	public boolean nullsafeArray(boolean[] arr, int i) {
		return arr == null || arr.length <= i ? null : arr[i];
	}

	public <T> boolean nullsafeArray(boolean[][] arr, int i, int j) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j ? null : arr[i][j];
	}

	public boolean nullsafeArray(boolean[][][] arr, int i, int j, int k) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j || arr[i][j] == null || arr[i][j].length <= k ? null : arr[i][j][k];
	}

	
	public long nullsafeArray(long[] arr, int i) {
		return arr == null || arr.length <= i ? null : arr[i];
	}

	public <T> long nullsafeArray(long[][] arr, int i, int j) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j ? null : arr[i][j];
	}

	public long nullsafeArray(long[][][] arr, int i, int j, int k) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j || arr[i][j] == null || arr[i][j].length <= k ? null : arr[i][j][k];
	}

	
	
	public short nullsafeArray(short[] arr, int i) {
		return arr == null || arr.length <= i ? null : arr[i];
	}

	public <T> short nullsafeArray(short[][] arr, int i, int j) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j ? null : arr[i][j];
	}

	public short nullsafeArray(short[][][] arr, int i, int j, int k) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j || arr[i][j] == null || arr[i][j].length <= k ? null : arr[i][j][k];
	}
	

	public int nullsafeArray(int[] arr, int i) {
		return arr == null || arr.length <= i ? null : arr[i];
	}

	public <T> int nullsafeArray(int[][] arr, int i, int j) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j ? null : arr[i][j];
	}

	public int nullsafeArray(int[][][] arr, int i, int j, int k) {
		return arr == null || arr.length <= i || arr[i] == null || arr[i].length <= j || arr[i][j] == null || arr[i][j].length <= k ? null : arr[i][j][k];
	}
	
	
	/**
	 * Wraps given object using proxy created by {@link ProxyInstanceFactory}. 
	 * @param obj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private <T> T proxyImpl(T obj) throws InstantiationException, IllegalAccessException {
		ChainStateHolder.setChain(new ArrayList<String>());
		retrievePossibleChains();
		
		Integer chainIndex = ChainStateHolder.getCurrentChainIndex();
		if (chainIndex == null) {
			chainIndex = 0;
		} else {
			++chainIndex;
		}
		ChainStateHolder.setCurrentChainIndex(chainIndex);
		
		
		return (T)proxyInstanceFactory.proxy(obj, (Class<T>)obj.getClass());
	}
	
	
	
	private void retrievePossibleChains() {
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		String wrapperName = null;
		for (StackTraceElement elem : stackTrace) {
			if (!libraryClassesInChain.contains(elem)) {
				StackTraceElement libraryClassElement = getLibraryClassElement(elem);
				if (libraryClassElement == null) {
					ChainStateHolder.setPossibleChains(chainRetriever.getInvokationChains(elem.getClassName(), elem.getMethodName(), elem.getLineNumber(), wrapperName));
					currentLine.set(elem.getLineNumber());
					return;
				}
				libraryClassesInChain.add(libraryClassElement);
			}
			
			
			wrapperName = elem.getMethodName();
		}
		
		throw new IllegalStateException("No caller found");
	}
	
	
	private StackTraceElement getLibraryClassElement(StackTraceElement elem) {
		try {
			Class<?> clazz = Class.forName(elem.getClassName());
			if (clazz.getAnnotation(SafeChainWrapper.class) != null) {
				return new StackTraceElement(elem.getClassName(), "", elem.getClassName() + ".java", -1);
			}
			//elem.getMethodName()
			//clazz.getMethod(name, parameterTypes)

			for(Class<?> c = clazz;  !Object.class.equals(c); c = c.getSuperclass()) {
				for (Method m : clazz.getDeclaredMethods()) {
					if (m.getAnnotation(SafeChainWrapper.class) != null) {
						return new StackTraceElement(elem.getClassName(), m.getName(), elem.getClassName() + ".java", -1);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}
}
