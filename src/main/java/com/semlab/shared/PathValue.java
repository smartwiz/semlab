package com.semlab.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PathValue implements Serializable {
	
	private Path path;
	private String value;
	
	private PathValue() {
	}

	private PathValue(Path path, String value) {
		this.path = path;
		this.value = value;
	}
	
	public Path getPath() {
		return path;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "FieldValue [path=" + path + ", value=" + value + "]";
	}

}
