package com.safechain.impl;

/**
 * Value object that holds stack trace information: class and method name, line number and 
 * name of wrapper method that was used to wrap original object to create null-safe proxy.
 * The existence of last member (wrapper name) made author to create this class. Otherwise
 * regular {@link StackTraceElement} could be used. Unfortunately {@link StackTraceElement}
 * is final and cannot be inherited, so this class contians all fields.
 *    
 * @author alexr
 *
 */
class InvocationPosition {
	private String className;
	private String methodName;
	private int lineNumber;
	private String wrapperName;
	
	
	
	InvocationPosition(String className, String methodName, int lineNumber, String wrapperName) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.wrapperName = wrapperName;
	}
	
	public String getClazz() {
		return className;
	}
	public void setClazz(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getWrapperName() {
		return wrapperName;
	}
	public void setWrapperName(String wrapperName) {
		this.wrapperName = wrapperName;
	}

	@Override
	public String toString() {
		return "InvocationPosition [className=" + className + ", methodName="
				+ methodName + ", lineNumber=" + lineNumber + ", wrapperName="
				+ wrapperName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + lineNumber;
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result
				+ ((wrapperName == null) ? 0 : wrapperName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvocationPosition other = (InvocationPosition) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (wrapperName == null) {
			if (other.wrapperName != null)
				return false;
		} else if (!wrapperName.equals(other.wrapperName))
			return false;
		return true;
	}
	
	
	
}
