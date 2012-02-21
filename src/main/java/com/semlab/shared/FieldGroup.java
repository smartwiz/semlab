package com.semlab.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class FieldGroup implements Serializable {
	
	private String label;
	private ArrayList<Field> fields = new ArrayList<Field>();
	
	private FieldGroup() {
	}

	private FieldGroup(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	public void addField(Field field) {
		fields.add(field);
	}

	public ArrayList<Field> getFields() {
		return fields;
	}
	

	@Override
	public String toString() {
		return "FieldGroup [label=" + label + ", fields=" + fields + "]";
	}

}
