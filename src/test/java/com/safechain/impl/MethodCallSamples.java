package com.safechain.impl;

import java.util.List;

public class MethodCallSamples {
	public void v0(){}
	public void vlong(int i){}
	public void vString(String s){}
	public void vlong(long s){}
	public void vintlong(int i, long l) {}
	public void vstringlong(String s, long l) {}
	public void vlongstring(long l, String s) {}
	
	public void vboolean(boolean b){}
	public void vshort(short s){}
	public void vfloat(float f){}
	public void vdouble(double f){}
	public void vchar(char c){}
	
	public void vMethods(MethodCallSamples m) {}

	public void vintarr(int[] i){}
	public void vintarr2(int[] a, int i){}
	public void vstringarr(String[] a){}
	public void vlist(List<String> list){}


	
	public void test() {
		v0();
		vlong(0);
		vString(null);
		vlong(0);
		vintlong(0, 1);
		vstringlong("", 1234L);
		vlongstring(567L, "");
		
		
		vboolean(true);
		vshort((short)1);
		vfloat(1.1f);
		vdouble(1.1);
		vchar('a');
		
		
		vMethods(this);
		
		vintarr(null);
		vintarr2(null, 0);
		vstringarr(null);
		vlist(null);
	}
}
