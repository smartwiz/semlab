package com.semlab.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Criteria implements Serializable {
	
	private Path path;
	private Operator operator;
	private String value;
	
	public Criteria() {
	}

	public Criteria(Path path, Operator operator, String value) {
		this.path = path;
		this.operator = operator;
		this.value = value;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path field) {
		this.path = field;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Criteria [path=" + path + ", operator=" + operator + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Criteria other = (Criteria) obj;
		if (operator != other.operator)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
}
