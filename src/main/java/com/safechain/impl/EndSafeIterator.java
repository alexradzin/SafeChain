package com.safechain.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Special implementation of {@link Iterator} interface.
 * Its {@link #next()} method never throws {@link NoSuchElementException}.
 * It returns {@code null} when no more elements are available.
 * 
 * @see EndSafeEnumeration
 * @author alexr
 *
 * @param <E>
 */
class EndSafeIterator<E> implements Iterator<E> {
	private Iterator<E> it;
	
	EndSafeIterator(Iterator<E> it) {
		this.it = it;
	}
	

	@Override
	public boolean hasNext() {
		return it != null && it.hasNext();
	}

	@Override
	public E next() {
		return it != null && it.hasNext() ? it.next() : null;
	}

	@Override
	public void remove() {
		it.remove();
	}

}
