package com.safechain;

import java.util.Collection;
import java.util.Collections;

public class Phone {
	public static enum Capability {
		CALL, SMS, EMAIL, IM, GPS,;
	}
	
	private String number;
	private String carrier;
	private Kind kind;
	private Collection<Capability> capabilitiues = Collections.emptySet();
	
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public Kind getKind() {
		return kind;
	}
	public void setKind(Kind kind) {
		this.kind = kind;
	}
	public Collection<Capability> getCapabilitiues() {
		return capabilitiues;
	}
	public void setCapabilitiues(Collection<Capability> capabilitiues) {
		this.capabilitiues = capabilitiues;
	}
	
	
	
}
