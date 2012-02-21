package com.semlab.shared.config;

import com.semlab.shared.Operator;
import static com.semlab.shared.Operator.*;

public enum PropertyType {
	
	Text(new Operator[]{Eq, NotEq, Empty, NotEmpty, Begins, Contains, Ends}),
	Number(new Operator[]{Eq, NotEq, Empty, NotEmpty, Gt, Ge, Lt, Le}),
	Date(new Operator[]{Eq, NotEq, Empty, NotEmpty, Gt, Ge, Lt, Le}) ;
	
	private Operator[] operators;

	private PropertyType(Operator[] operators) {
		this.operators = operators;
	}

	public Operator[] getOperators() {
		return operators;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	public static PropertyType fromString(String type){
		for (PropertyType ptype : PropertyType.values()) {
			if(ptype.toString().equalsIgnoreCase(type))
				return ptype;
		}
		return null;
	}
	
	
}