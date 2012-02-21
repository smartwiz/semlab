package com.semlab.server.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.semlab.shared.DomainObject;

@SuppressWarnings("serial")
public class Concept extends DomainObject{

	private String id;
	private String uri;
	private String name;
	private String type;
	private HashMap<String, Object> properties;
	private long dateCreated;
	private long dateUpdated;
	private long dateEnriched;
	private boolean enriched;
	private int uriCounter;
	private int enrichCounter;

	public Concept() {
	}


	public Concept(String id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public Concept(String uri, String type) {
		super();
		this.uri = uri;
		this.type = type;
	}

	public Concept(String id, String uri, String name, String type) {
		super();
		this.id = id;
		this.uri = uri;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashMap<String, Object> getProperties() {
		return properties;
	}
	
	public long getDateUpdated() {
		return dateUpdated;
	}


	public void setDateUpdated(long dateUpdated) {
		this.dateUpdated = dateUpdated;
	}


	public boolean isEnriched() {
		return enriched;
	}


	public void setEnriched(boolean enriched) {
		this.enriched = enriched;
	}
	
	public int getUriCounter() {
		return uriCounter;
	}


	public void setUriCounter(int uriCounter) {
		this.uriCounter = uriCounter;
	}
	public void incrementUriCounter() {
		this.uriCounter++;
	}

	public int getEnrichCounter() {
		return enrichCounter;
	}


	public void setEnrichCounter(int enrichCounter) {
		this.enrichCounter = enrichCounter;
	}
	
	public void incrementEnrichCounter() {
		this.enrichCounter++;
	}


	public Object getProperty(String name){
		if(properties.containsKey(name)){
			return properties.get(name);
		}
		return null;
	}

	public void addProperty(String name, Object value) {
		ensureProperties();
		if(properties.containsKey(name)){
			Object valueObject = properties.get(name);
			if(valueObject instanceof List){
				((List<Object>) valueObject).add(value);
			} else {
				List<Object> transformList = new ArrayList<Object>();
				transformList.add(valueObject);
				transformList.add(value);
				properties.put(name,transformList);
			}
		} else {
			properties.put(name, value);
		}
//		persist();
	}

	public boolean hasProperty(String name) {
		if (properties != null) {
			return properties.containsKey(name);
		}
		return false;
	}

	private void ensureProperties() {
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}
	}

//	public void parseFObject(FObject fobject) {
//		id = fobject.getId();
//		name = fobject.getName();
//		type = fobject.getCategory();
//	}
//	
	
	public long getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date date) {
		this.dateCreated = date.getTime();
	}

	public long getDateEnriched() {
		return dateEnriched;
	}

	public void setDateEnriched(long dateEnriched) {
		this.dateEnriched = dateEnriched;
	}
	
	public void setDateEnriched(Date dateEnriched) {
		this.dateEnriched = dateEnriched.getTime();
	}
	
	public Date changeDateEnriched(boolean finished) {
		Date date = new Date();
		this.dateEnriched = date.getTime();
		enriched = finished;
		return date;
	}
	
	public Date changeDateUpdated() {
		Date date = new Date();
		this.dateUpdated = date.getTime();
		return date;
	}


	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}


	@Override
	public String toString() {
		return "Concept [id=" + id + ", uri=" + uri + ", name=" + name
				+ ", type=" + type + ", properties=" + properties
				+ ", dateCreated=" + dateCreated + ", dateUpdated="
				+ dateUpdated + ", dateEnriched=" + dateEnriched
				+ ", enriched=" + enriched + ", uriCounter=" + uriCounter
				+ ", enrichCounter=" + enrichCounter + "]";
	}

	
}
