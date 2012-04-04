package com.semlab.server.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.social.facebook.api.FacebookProfile;

import com.semlab.shared.DomainObject;
import com.semlab.shared.resources.NamespaceConstants;

@Configurable
public class Profile extends DomainObject{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String id;
	private String name;
	private String type;
	private String first_name;
	private String last_name;
	private String profile_url;
	
	//fb
	private String username;
	private long birthday;
	private String gender;
	private String email;
	private String website;
	

	
	public Profile() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getProfile_url() {
		return profile_url;
	}
	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getBirthday() {
		return birthday;
	}
	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public long parseBirthday(String birthday){
		if(birthday != null){
			long bd;
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
			Date d;
			try {
				d = sdf1.parse(birthday);
			} catch (ParseException e) {
				try {
					d= sdf2.parse(birthday);
				} catch (ParseException e1) {
					return 0L;
				}
			}
			bd = d.getTime();
			return bd;
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", name=" + name + ", type=" + type
				+ ", first_name=" + first_name + ", last_name=" + last_name
				+ ", profile_url=" + profile_url + ", username=" + username + ", birthday="
				+ birthday + ", gender=" + gender + ", email=" + email
				+ ", website=" + website + "]";
	}

}
