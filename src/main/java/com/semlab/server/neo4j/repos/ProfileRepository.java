package com.semlab.server.neo4j.repos;

import javax.annotation.PostConstruct;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.springframework.data.neo4j.support.index.NoSuchIndexException;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Component;

import com.semlab.server.resources.Profile;
import com.semlab.server.resources.RelationshipTypes;
import com.semlab.shared.resources.NamespaceConstants;

@Component
public class ProfileRepository extends Repository<Profile>{

	private Index<Node> profiles;	
	public ProfileRepository() {
	}
	
	@PostConstruct
	public void init() {
		try {
			profiles = template.getIndex(PROFILE);
		} catch (NoSuchIndexException e) {
			graphdb.index().forNodes(PROFILE);
			init();
		}
	}

	public void addProfile(Profile profile) {
//		log.debug("adding profile: "+profile);
		profiles.add(template.getNode(profile.getNodeId()), NamespaceConstants.CONCEPT_ID,
				profile.getId());
	}

	public Profile getById(String id) {
		IndexHits<Node> hits = profiles.get(NamespaceConstants.CONCEPT_ID, id);
		Node profile = null;
		if (hits.hasNext()) {
			profile = hits.next();
    		log.debug(":: Index found profile with id: "+profile.getId()+" ::");
		}
		return (profile != null) ? getByNode(profile) : null;
	}
	
	@Override
	public Node persist(Profile profile) {
		Node node = null;
		if(profile.getId() != null){
			node = getDomainNode(profile.getId());
		}
		if(node == null){
			node = template.createNode();
			profile.setNodeId(node.getId());
			if(profile.getId() != null)
				addProfile(profile);
		}
		
		
		profile.setNodeId(node.getId());
		utils.setNodeProperty(node, NamespaceConstants.CONCEPT_ID, profile.getId());
		utils.setNodeProperty(node, NamespaceConstants.PROFILE_NAME, profile.getName());
		utils.setNodeProperty(node, NamespaceConstants.TYPE, profile.getType());
		utils.setNodeProperty(node, NamespaceConstants.FIRST_NAME, profile.getFirst_name());
		utils.setNodeProperty(node, NamespaceConstants.LAST_NAME, profile.getLast_name());
		utils.setNodeProperty(node, NamespaceConstants.PROFILE_URL, profile.getProfile_url());
		utils.setNodeProperty(node, NamespaceConstants.USERNAME, profile.getUsername());
		utils.setNodeProperty(node, NamespaceConstants.BIRTHDAY, profile.getBirthday());
		utils.setNodeProperty(node, NamespaceConstants.GENDER, profile.getGender());
		utils.setNodeProperty(node, NamespaceConstants.EMAIL, profile.getEmail());
		utils.setNodeProperty(node, NamespaceConstants.WEBSITE, profile.getWebsite());
		
		return node;
	}

	@Override
	public Profile getByNode(Node node) {
		if(node == null)
			return null;
		Profile profile = new Profile();
		profile.setNodeId(node.getId());
		return profile;
	}

	@Override
	public Node getDomainNode(String id) {
		log.debug(":: id= "+id);
		IndexHits<Node> hits = profiles.get(NamespaceConstants.CONCEPT_ID, id);
		Node profile = null;
		if (hits.hasNext()) {
			profile = hits.next();
    		log.debug(":: Index found profile with id: "+profile.getId()+" ::");
		}
		return (profile != null) ? profile : null;
	}

	public Profile parseFacebookProfile(FacebookProfile profile){
		Profile person = new Profile();
		person.setId(profile.getId());
		person.setFirst_name(profile.getFirstName());
//		person.setLast_name(profile.getLastName());
		if(profile.getBirthday() != null)
			person.setBirthday(person.parseBirthday(profile.getBirthday()));
		
//		person.setEmail(profile.getEmail());
		person.setGender(profile.getGender());
//		person.setName(profile.getName());
//		person.setProfile_url(profile.getLink());
		person.setWebsite(profile.getWebsite());
		return person;
	}
	
	public void makeFriends(Profile p1, Profile p2){
		Node n1 = template.getNode(p1.getNodeId());
		Node n2 = template.getNode(p2.getNodeId());
		utils.relate(n1, n2, RelationshipTypes.FRIEND);
	}
}
