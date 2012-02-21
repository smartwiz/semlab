package com.semlab.shared;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Project extends DomainObject {

	private long id;
	private String name;
	private Source source;
	private ProjectStatus status;
	private int levelOfContacts;
	private String profileName;
	private String profileId;
	private String profileToken;
	private String keywords;
	private int maxNumberOfContacts;
	private ArrayList<Type> dataEnrichment;
	private Date dateCreated;
	private Date dateCompleted;
	private int size;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	
	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public int getLevelOfContacts() {
		return levelOfContacts;
	}

	public void setLevelOfContacts(int levelOfContacts) {
		this.levelOfContacts = levelOfContacts;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getMaxNumberOfContacts() {
		return maxNumberOfContacts;
	}

	public void setMaxNumberOfContacts(int maxNumberOfContacts) {
		this.maxNumberOfContacts = maxNumberOfContacts;
	}

	public ArrayList<Type> getDataEnrichment() {
		return dataEnrichment;
	}

	public void setDataEnrichment(ArrayList<Type> dataEnrichment) {
		this.dataEnrichment = dataEnrichment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getProfileToken() {
		return profileToken;
	}

	public void setProfileToken(String profileToken) {
		this.profileToken = profileToken;
	}
	
	public void addConfigType(Type type){
		if(dataEnrichment == null){
			dataEnrichment = new ArrayList<Type>();
		}
		dataEnrichment.add(type);
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", source=" + source
				+ ", status=" + status + ", levelOfContacts=" + levelOfContacts
				+ ", profileName=" + profileName + ", profileId=" + profileId
				+ ", profileToken=" + profileToken + ", keywords=" + keywords
				+ ", maxNumberOfContacts=" + maxNumberOfContacts
				+ ", dataEnrichment=" + dataEnrichment + ", dateCreated="
				+ dateCreated + ", dateCompleted=" + dateCompleted + ", size="
				+ size + "]";
	}

}
