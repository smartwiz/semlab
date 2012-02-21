package com.semlab.server.resources;

public class ProfileStatus {
	
	public static final String FINISHED = "FINISHED";
	public static final String IN_PROGRESS = "IN_PROGRESS";
	
	private String percentage;
	private String status;
	
	
	public ProfileStatus() {
	}
	public ProfileStatus(String percentage, String status) {
		this.percentage = percentage;
		this.status = status;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProfileStatus [percentage=" + percentage + ", status=" + status
				+ "]";
	}
	
	
}
