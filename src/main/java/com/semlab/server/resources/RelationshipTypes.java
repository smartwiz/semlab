package com.semlab.server.resources;

import org.neo4j.graphdb.RelationshipType;

import com.semlab.shared.resources.NamespaceConstants;

public class RelationshipTypes {

	public static final MyRelationshipType ROOT = new MyRelationshipType(NamespaceConstants.ROOT);
	
	//profile related
	public static final MyRelationshipType LIKES = new MyRelationshipType(NamespaceConstants.LIKES);
	public static final RelationshipType EDUCATED_AT = new MyRelationshipType(NamespaceConstants.EDUCATED_AT);
	public static final RelationshipType LIVES_IN = new MyRelationshipType(NamespaceConstants.LIVES_IN);
	public static final RelationshipType BORN_IN = new MyRelationshipType(NamespaceConstants.BORN_IN);
	public static final RelationshipType WORKS_AT = new MyRelationshipType(NamespaceConstants.WORKS_AT);
	public static final RelationshipType BASED_IN = new MyRelationshipType(NamespaceConstants.BASED_IN);
	public static final RelationshipType FRIEND = new MyRelationshipType(NamespaceConstants.FRIEND);
	
	/* use FRIEND instead of BIZ_CONNECTION for linkedin*/
	public static final RelationshipType BIZ_CONNECTION = new MyRelationshipType(NamespaceConstants.BIZ_CONNECTION);
	public static final RelationshipType PERSON_EDUCATION = new MyRelationshipType(NamespaceConstants.PERSON_EDUCATION);
	public static final RelationshipType IN_SCHOOL = new MyRelationshipType(NamespaceConstants.IN_SCHOOL);
	public static final RelationshipType LOCATED_AT = new MyRelationshipType(NamespaceConstants.LOCATED_AT);
	public static final RelationshipType HAS_POSTAL_CODE = new MyRelationshipType(NamespaceConstants.HAS_POSTAL_CODE);
	public static final RelationshipType IN_CITY = new MyRelationshipType(NamespaceConstants.IN_CITY);
	public static final RelationshipType IN_COUNTRY = new MyRelationshipType(NamespaceConstants.IN_COUNTRY);
	
	//other
	public static final RelationshipType PROJECT = new MyRelationshipType(NamespaceConstants.PROJECT);
	public static final RelationshipType PROFILE = new MyRelationshipType(NamespaceConstants.PROFILE);
	public static final RelationshipType REPORT = new MyRelationshipType(NamespaceConstants.REPORT);
	public static final RelationshipType PATH = new MyRelationshipType(NamespaceConstants.PATH);
	public static final RelationshipType CONFIG_CLASS = new MyRelationshipType(NamespaceConstants.CONFIG_CLASS);
	public static final RelationshipType TEMP_JSON = new MyRelationshipType(NamespaceConstants.TEMP_JSON);
	public static final RelationshipType FORECAST = new MyRelationshipType(NamespaceConstants.FORECAST);
	
	public static final RelationshipType CRITERIA = new MyRelationshipType(NamespaceConstants.CRITERIA);
	
	public static class MyRelationshipType implements RelationshipType{

		private String name;
		
		public MyRelationshipType(String name) {
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}

	}
}
