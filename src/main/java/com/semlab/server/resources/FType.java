package com.semlab.server.resources;

import com.semlab.shared.resources.NamespaceConstants;

public enum FType {
	
	Place("Place", "http://dbpedia.org/ontology/Place"),
	Country("Country", "http://dbpedia.org/ontology/Country"),
	EducationalInstitution("EducationalInstitution", "http://dbpedia.org/ontology/EducationalInstitution"),
	Book("Book", "http://dbpedia.org/ontology/Book"),
	Movie("Movie", "http://dbpedia.org/ontology/Film"),
	Tv_show("Tv show", "http://dbpedia.org/ontology/TelevisionShow") ,
	Musician_Band("Musician/Band", null),
	Music("Music", null),
	Musician("Musician", "http://dbpedia.org/ontology/MusicalArtist"),
	Band("Band", "http://dbpedia.org/ontology/Band"),
	Athlete("Athlete", "http://dbpedia.org/ontology/Athlete"),
	Professional_sports_team("Professional sports team", "http://dbpedia.org/ontology/SportsTeam"), 
	Company("Company", "http://dbpedia.org/ontology/Organisation"),
	Product_service("Product/service", "http://dbpedia.org/ontology/Organisation"),
	Local_business("Local business", "http://dbpedia.org/ontology/Organisation"),
	Friend("Friend", NamespaceConstants.FB_PROFILE),
	Unknown("Unknown", null);
	
	private String fbLabel;
	private String uri;

	private FType(String fbLabel, String uri) {
		this.fbLabel = fbLabel;
		this.uri = uri;
	}

	public String getFbLabel() {
		return fbLabel;
	}

	public String getUri() {
		return uri;
	}

	public static boolean isSupportedType(String type) {
		for (FType fTypes : FType.values()) {
			if(fTypes.getFbLabel().equals(type)) return true;
		}
		return false;
	}
	
	public static FType getTypeForFBType(String fbType) {
		for (FType fTypes : FType.values()) {
			if(fTypes.getFbLabel().equals(fbType)) return fTypes;
		}
		return null;
	}
	
}
