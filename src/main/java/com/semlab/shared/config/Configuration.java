package com.semlab.shared.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
@Component
public class Configuration implements Serializable{

	@Autowired
	private List<Thing> things;
	
	@Autowired
	private SocialParameters socialParams;

	public Configuration() {
	}
	
	public SocialParameters getSocialParams() {
		return socialParams;
	}

	public ArrayList<Thing> getThings() {
		return (ArrayList<Thing>) things;
	}

}
