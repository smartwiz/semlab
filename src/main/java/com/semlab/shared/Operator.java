package com.semlab.shared;

public enum Operator {
	
	Eq(true), NotEq(true),  Gt(true), Lt(true), Ge(true), Le(true), Empty(false), NotEmpty(false), Begins(true), Contains(true), Ends(true);
	
	public static Operator fromString(String operator){
		for (Operator o : Operator.values()) {
			if(o.toString().equalsIgnoreCase(operator))
				return o;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	private boolean needsValue;
	
	private Operator(boolean needsValue) {
		this.needsValue = needsValue;
	}

	public boolean isNeedsValue() {
		return needsValue;
	}
	
}