package com.safechain.impl;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Special implementation of {@link Enumeration} interface.
 * Its {@link #nextElement()} method never throws {@link NoSuchElementException}.
 * It returns {@code null} when no more elements are available.
 * 
 * @see EndSafeIterator
 * @author alexr
 *
 * @param <E>
 */
class EndSafeEnumeration<E> implements Enumeration<E> {
	private Enumeration<E> enumeration;
	
	EndSafeEnumeration(Enumeration<E> enumeration) {
		this.enumeration = enumeration;
	}
	

	@Override
	public boolean hasMoreElements() {
		return enumeration != null && enumeration.hasMoreElements();
	}

	@Override
	public E nextElement() {
		return enumeration != null && enumeration.hasMoreElements() ? enumeration.nextElement() : null;
	}

}
