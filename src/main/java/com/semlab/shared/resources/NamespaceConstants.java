package com.semlab.shared.resources;


public class NamespaceConstants {
	
	public static final String ROOT = "ROOT";
	
	public static final String FACEBOOK = "facebook";
	public static final String LINKEDIN = "linkedin";
	public static final String EXCEL = "excel";

	//project/report properties
	public static final String ID = "id";
	public static final String URI = "uri";
	public static final String TYPE = "type";
	public static final String SOURCE = "source";
	public static final String NAME = "name";
	public static final String DATE_CREATED = "date_created";
	public static final String DATE_UPDATED = "date_updated";
	public static final String DATE_ENRICHED = "date_enriched";
	public static final String PROJECT_STATUS = "project_status";
	public static final String LEVEL_OF_CONTACTS = "level_of_contacts";
	public static final String FB_TOKEN = "fb_token";
	public static final String KEYWORDS = "keywords";
	public static final String MAX_NUM_OF_CONTACTS = "max_num_of_contacts";
	public static final String DATE_COMPLETED = "date_completed";
	public static final String SIZE = "size";

	// profile properties
	public static final String CONCEPT_ID = "concept_id";
	public static final String FB_PROFILE = "http://www.facebook.com/profile";
	public static final String LINKEDIN_PROFILE = "http://www.linkedin.com/profile";
	public static final String EXCEL_PROFILE = "excel_profile";
	public static final String PROFILE_ID = "profile_id";
	public static final String PROFILE_TYPE = "profile";
	public static final String PROFILE_NAME = "profile_name";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String PROFILE_URL = "profile_url";
	public static final String USERNAME = "http://www.facebook.com/username";
	public static final String BIRTHDAY = "http://www.facebook.com/birthday";
	public static final String GENDER = "http://www.facebook.com/gender";
	public static final String EMAIL = "http://www.facebook.com/email";
	public static final String WEBSITE = "http://www.facebook.com/website";
	public static final String WORK_POSITION = "work_position";
	public static final String CLASS_YEAR = "http://www.facebook.com/education/class_year";
	public static final String INTERESTS = "interests";
	public static final String SPECIALTIES = "specialties";
	public static final String NUM_CONNECTIONS = "num_connections";
	public static final String NUM_CONN_CAPPED = "num_conn_capped";
	
	public static final String START_YEAR = "start_year";
	public static final String END_YEAR = "end_year";
	public static final String ACTIVITIES = "activities";
	public static final String NOTES = "notes";
	public static final String FIELD_OF_STUDY = "field_of_study";
	public static final String DEGREE = "degree";
	
	public static final String HQ = "hq";
	public static final String STREET = "street";
	public static final String POSTAL_CODE_VALUE = "postal_code_value";
	public static final String COMPANY_WEBSITE = "website";

	//fb relations
	public static final String BASED_IN = "BASED_IN";
	public static final String WORKS_AT = "WORKS_AT";
	public static final String BORN_IN = "BORN_IN";
	public static final String LIVES_IN = "LIVES_IN";
	public static final String EDUCATED_AT = "EDUCATED_AT";
	public static final String LIKES = "LIKES";
	public static final String FRIEND = "FRIEND";
	
	//linkedin relations
	public static final String BIZ_CONNECTION = "BIZ_CONNECTION";
	public static final String PERSON_POSITION = "PERSON_POSITION";
	public static final String COMPANY_POSITION = "COMPANY_POSITION";
	public static final String TECHNOLOGY_POSITION = "TECHNOLOGY_POSITION";
	public static final String LOCATED_AT = "LOCATED_AT";
	public static final String IN_CITY = "IN_CITY";
	public static final String IN_COUNTRY = "IN_COUNTRY";
	public static final String HAS_POSTAL_CODE = "HAS_POSTAL_CODE";
	public static final String IN_STATE = "IN_STATE";
	public static final String IN_REGION = "IN_REGION";
	public static final String HAS_SPECIALTY = "HAS_SPECIALTY";
	public static final String PERSON_EDUCATION = "PERSON_EDUCATION";
	public static final String IN_FIELD = "IN_FIELD";
	public static final String IN_SCHOOL = "IN_SCHOOL";
	public static final String WITH_DEGREE = "WITH_DEGREE";
	public static final String BELONGS_TO = "BELONGS_TO";
	public static final String HAS_EMAIL_DOMAIN = "HAS_EMAIL_DOMAIN";
	
	//fields, paths and criterions
	public static final String CLASS_URI = "class_uri";
	public static final String CLASS_LABEL = "class_label";
	public static final String LABEL = "label";
	public static final String POSITION = "position";
	public static final String CRITERIA = "criteria";
	public static final String VALUE = "value";
	public static final String OPERATOR = "operator";
	

	public static final String JSON = "json";
	
	public static final String FORECAST = "forecast";
	
	
	//cypher queries related
    public static final String ROOT_IDENTIFIER = "x0";
	

	//other
	public static final String TEMP_JSON = "TEMP_JSON";
	public static final String CONFIG_CLASS = "CONFIG_CLASS";
	public static final String PATH = "PATH";
	public static final String REPORT = "REPORT";
	public static final String PROFILE = "PROFILE";
	public static final String PROJECT = "PROJECT";
	public static final String GEO_LAT = "http://www.w3.org/2003/01/geo/wgs84_pos#lat";
	public static final String GEO_LONG = "http://www.w3.org/2003/01/geo/wgs84_pos#long";

	public static final String URI_COUNTER = "uri_counter";
	public static final String ENRICH_COUNTER = "enrich_counter";
	public static final String ENRICHED = "enriched";
	public static final String UPDATED = "updated";
	
	//urls
	public static final String GET_CURRENT_PROFILE = "https://api.linkedin.com/v1/people/~:(id,public-profile-url,headline,first-name,last-name,positions,educations,specialties,interests,num-connections,num-connections-capped)";
	public static final String GET_CONNECTIONS = "https://api.linkedin.com/v1/people/~/connections:(id,public-profile-url,headline,first-name,last-name,positions,educations,specialties,interests,num-connections,num-connections-capped)";
	public static final String GET_COMPANY_DETAILS_BEGIN = "https://api.linkedin.com/v1/companies/";
	public static final String GET_COMPANY_DETAILS_END = ":(website-url,email-domains,specialties,locations:(is-headquarters,address:(street1,street2,city,state,country-code,postal-code,region-code)))";
}
