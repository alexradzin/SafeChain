package com.safechain;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;


public abstract class NullSafeChainTest {
	@Test(expected = NullPointerException.class)
	public void emptyPersonGetHomeAddress() {
		new Person().getHome().getCity();
	}
	
	@Test
	public void emptyWrappedPersonGetHomeAddress() {
		assertNull(wrap(new Person()).getHome().getCity());
	}

	
	@Test 
	public void test() {
		//String city = chain.nullsafe(new Person()).getHome().getCity();
		assertNull(wrap(new Person()).getHome().getCity());
		
		String phone = "123456789";
		Person person = new Person();
		Address home = new Address();
		home.setPhone(phone);
		person.setHome(home);
		
		assertEquals(phone, person.getHome().getPhone()); // unwrapped
		assertEquals(phone, wrap(person).getHome().getPhone()); // wrapped - the same result
	}
	
	@Test 
	public void test2() {
		String city = wrap(new Person()).getHome().getCity();
		assertNull(city);
		
		
		for (int i = 0;  i < 5; i++) {
			String phone = "123456789";
	 		Person person = new Person();
			Address home = new Address();
			home.setPhone(phone);
			person.setHome(home);
			
			assertEquals(phone, person.getHome().getPhone()); // unwrapped
			assertEquals(phone, wrap(person).getHome().getPhone()); // wrapped - the same result
		}
	}
	
	@Test //@Ignore
	public void testDelegatingWrapper() {
		assertNull(wrap(new Person()).getHome().getCity());
		//assertNull(NullSafeUtil.$(new Person()).getHome().getCity());
	}
	
	protected abstract <T> T wrap(T obj);
	protected abstract <T> T wrapArray(T[] arr, int i);
//	private Person wrap(Person p) {
//		return chain.nullsafe(p);
//	}
	
	
	
	@Test
	public void personWithPhoneGetPhone() {
		final String phone = "123456789";
		Person person = new Person();
		Address home = new Address();
		home.setPhone(phone);
		person.setHome(home);
		
		assertEquals(phone, person.getHome().getPhone()); // unwrapped
		assertEquals(phone, wrap(person).getHome().getPhone()); // wrapped - the same result
	}

	@Test
	public void nullArray() {
		Person person = new Person();
		assertNull(wrapArray(wrap(person).getPreviuousNames(), 0)); // NPE for regular object
 	}

	@Test
	public void emptyArray() {
		Person person = new Person();
		person.setPreviuousNames(new String[0]);
		assertNull(wrapArray(wrap(person).getPreviuousNames(), 0)); // ArrayIndexOutOfBounds for regular object
 	}

	@Test
	public void accessArrayElementThatDoesNotExist() {
		Person person = new Person();
		person.setPreviuousNames(new String[] {"Joe"});
		assertEquals("Joe", wrapArray(wrap(person).getPreviuousNames(), 0)); // ArrayIndexOutOfBounds for regular object
		assertNull(wrapArray(wrap(person).getPreviuousNames(), 1)); // ArrayIndexOutOfBounds for regular object
 	}
	
	@Test
	public void nullCollection() {
		Person person = new Person();
		assertNull(wrap(person).getFriends().iterator().next()); // NPE for regular object
 	}
	
	@Test
	public void emptyCollection() {
		Person person = new Person();
		person.setNickNames(Collections.<String>emptyList());
		assertNull(wrap(person).getFriends().iterator().next()); // NoSuchElementException for regular object 
 	}
	
	@Test
	public void accessCollectionElementThatDoesNotExist() {
		Person person = new Person();
		person.setNickNames(Collections.singleton("Jim"));
		Iterator<String> it = wrap(person).getNickNames().iterator();
		assertEquals("Jim", it.next()); 
		assertNull(it.next()); // NoSuchElementException for regular object
 	}

	@Test
	public void accessNestedElement() {
		Person zeus = new Person();
		zeus.setFirstName("Zeus");
		zeus.setNickNames(Arrays.asList("Zeus Meilichios", "Brontios"));
		
		String[] childrenNames = new String[] {"Ares", "Athena", "Apollo", "Artemis", "Aphrodite", "Dionysus", "Hebe", "Hermes", "Heracles", "Helen of Troy", "Hephaestus", "Perseus", "Minos", "Muses", "Graces"};
		Person[] children = new Person[childrenNames.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = new Person();
			children[i].setFirstName(childrenNames[i]);
		}
		zeus.setChildren(children);

		for (int i = 0; i < children.length; i++) {
			assertEquals(childrenNames[i], wrapArray(wrap(zeus).getChildren(), i).getFirstName());
		}
		
		assertNull(wrapArray(wrap(zeus).getChildren(), children.length)); // index out of range
		//assertNull(chain.nullsafeElement(chain.nullsafe(zeus).getChildren(), children.length).getFirstName()); // index out of range
		
		
		assertEquals(0, wrap(zeus).getAge());
 	}
	
}
