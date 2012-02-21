package com.semlab.server.resources;

import java.util.ArrayList;

import com.semlab.shared.Field;

public class CypherPathHolder{
	
	private ArrayList<Field> fields;
	private String id;
	private boolean matched;

	public CypherPathHolder(ArrayList<Field> fields, String id) {
		this.fields = new ArrayList<Field>();
		for (Field field : fields) {
			this.fields.add(field);
		}
		this.id = id;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public String getId() {
		return id;
	}
	
	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	@Override
	public String toString() {
		return "\nCypherPathHolder [fields=" + fields + ", id=" + id
				+ ", matched=" + matched + "]";
	}

	
}