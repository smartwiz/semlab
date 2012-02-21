package com.semlab.shared.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SocialParameters implements Serializable{
	
	private String facebookApiKey;
	
	public SocialParameters() {
	}

	public String getFacebookApiKey() {
		return facebookApiKey;
	}

	public void setFacebookApiKey(String facebookApiKey) {
		this.facebookApiKey = facebookApiKey;
	}
	
}
