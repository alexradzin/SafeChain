package com.safechain;

import java.util.Collection;


public class Person {
	private String firstName;
	private String lastName;
	private String [] previuousNames;
	private Collection<String> nickNames;
	private Person[] children;
	private Collection<Person> friends;
	private Address home;
	private Address work;
	private int age;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String[] getPreviuousNames() {
		return previuousNames;
	}
	public void setPreviuousNames(String[] previuousNames) {
		this.previuousNames = previuousNames;
	}
	public Address getHome() {
		return home;
	}
	public void setHome(Address home) {
		this.home = home;
	}
	public Address getWork() {
		return work;
	}
	public void setWork(Address work) {
		this.work = work;
	}
	public Person[] getChildren() {
		return children;
	}
	public void setChildren(Person[] children) {
		this.children = children;
	}
	public Collection<String> getNickNames() {
		return nickNames;
	}
	public void setNickNames(Collection<String> nickNames) {
		this.nickNames = nickNames;
	}
	public Collection<Person> getFriends() {
		return friends;
	}
	public void setFriends(Collection<Person> friends) {
		this.friends = friends;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
