package com.semlab.shared;

public class Type extends DomainObject {
	
	protected String classType;
	
	public Type() {
		super();
	}
	public Type(String classType) {
		super();
		this.classType = classType;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classType == null) ? 0 : classType.hashCode());
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
		Type other = (Type) obj;
		if (classType == null) {
			if (other.classType != null)
				return false;
		} else if (!classType.equals(other.classType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Type [classType=" + classType + "]";
	}
	
}
