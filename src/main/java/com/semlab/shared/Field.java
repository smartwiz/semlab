package com.semlab.shared;

import java.io.Serializable;

import com.semlab.shared.config.PropertyType;

@SuppressWarnings("serial")
public class Field implements Serializable {
	
	private String uri;
	private String classUri;
	private String label;
	private String classLabel;
	private PropertyType type;
	
	public Field() {
	}
	
	public Field(String uri, String classUri, PropertyType type) {
		this(uri, classUri, null, null, type);
	}
	
	public Field(String uri, String classUri, String label, String classLabel) {
		this(uri, classUri, label, classLabel, null);
	}
	
	public Field(String uri, String classUri, String label, String classLabel, PropertyType type) {
		this.uri = uri;
		this.classUri = classUri;
		this.label = label;
		this.classLabel = classLabel;
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getClassUri() {
		return classUri;
	}

	public void setClassUri(String classUri) {
		this.classUri = classUri;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classUri == null) ? 0 : classUri.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Field other = (Field) obj;
		if (classUri == null) {
			if (other.classUri != null)
				return false;
		} else if (!classUri.equals(other.classUri))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Field [uri=" + uri + ", classUri=" + classUri + ", type=" + type + "]";
	}

	
}
