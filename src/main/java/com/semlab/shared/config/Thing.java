package com.semlab.shared.config;

import java.util.ArrayList;

import com.semlab.shared.Type;

@SuppressWarnings("serial")
public class Thing extends Type {

	private DataSource source;
	private ArrayList<Property> properties;
	private String label;
	private String sourcePlatform;

	public Thing() {
	}
	
	public Thing(String classType) {
		super(classType);
	}

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}

	public ArrayList<Property> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getSourcePlatform() {
		return sourcePlatform;
	}

	public void setSourcePlatform(String sourcePlatform) {
		this.sourcePlatform = sourcePlatform;
	}

	@Override
	public String toString() {
		return "Thing [source=" + source + ", properties=" + properties
				+ ", label=" + label + ", sourcePlatform=" + sourcePlatform
				+ "]";
	}

}
