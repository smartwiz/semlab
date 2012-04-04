package com.semlab.server.services;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.springframework.social.facebook.api.EducationEntry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.WorkEntry;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;
import com.semlab.server.resources.Profile;
import com.semlab.server.resources.RelationshipTypes;
import com.semlab.shared.resources.NamespaceConstants;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class InsertProfileService extends BaseService{
	
	public InsertProfileService() {
	}

	public void process(String token){
		Facebook facebook = new FacebookTemplate(token);
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		
		Profile me = profiles.parseFacebookProfile(profile);
		Node profileNode = profiles.persist(me);
		
		//hometown
		Reference ref = profile.getHometown();
		if(ref != null){
			Node hometown = getConcept(ref.getId(), ref.getName(), FType.Place.getFbLabel());
			utils.relate(profileNode, hometown, RelationshipTypes.BORN_IN);
		}

		//location
		ref = profile.getLocation();
		if(ref != null){
			Node hometown = getConcept(ref.getId(), ref.getName(), FType.Place.getFbLabel());
			utils.relate(profileNode, hometown, RelationshipTypes.LIVES_IN);
		}
		
		//parse education
		List<EducationEntry> education = profile.getEducation();
		if(education != null){
			for (EducationEntry educationEntry : education) {
				if(educationEntry.getSchool() != null){
					Reference school = educationEntry.getSchool();
					Node concept = getConcept(school.getId(), school.getName(), FType.EducationalInstitution.getFbLabel());
					if(educationEntry.getYear() != null){
						utils.setNodeProperty(concept, NamespaceConstants.CLASS_YEAR, educationEntry.getYear().getName());

					}
					utils.relate(profileNode, concept, RelationshipTypes.EDUCATED_AT);

				}
			}
		}
		
		//work
		List<WorkEntry> workList = profile.getWork();
		if(workList != null){
			for (WorkEntry work : workList) {
				if(work.getEmployer() != null){
					Reference w = work.getEmployer();
					Node concept = getConcept(w.getId(), w.getName(), FType.Company.getFbLabel());
					utils.relate(profileNode, concept, RelationshipTypes.WORKS_AT);
				}
			}
		}
		
		//likes
		parseLikes(profileNode, facebook, profile.getId());
		
		List<FacebookProfile> friends = facebook.friendOperations()
				.getFriendProfiles();
		for (FacebookProfile friend : friends) {
			
			Profile nfriend = profiles.parseFacebookProfile(friend);
			Node friendNode = profiles.persist(nfriend);
//			parseLikes(friendNode, facebook, friend.getId());
//			profiles.makeFriends(me, nfriend);
			
		}
		

		root.relate(template.getNode(me.getNodeId()));
	}
	
	private void parseLikes(Node profileNode, Facebook facebook, String id){
		//movies
		List<Page> list = facebook.likeOperations().getMovies(id);
		createLikes(list, profileNode);
		
		//books
		list = facebook.likeOperations().getBooks(id);
		createLikes(list, profileNode);

		//music
		list = facebook.likeOperations().getMusic(id);
		createLikes(list, profileNode);

		//tv
		list = facebook.likeOperations().getTelevision(id);
		createLikes(list, profileNode);
		
		//pages
		list = facebook.likeOperations().getPagesLiked(id);
		createLikes(list, profileNode);
		
	}
	
	private void createLikes(List<Page> list, Node profile, String type){
		for (org.springframework.social.facebook.api.Page page : list) {
			Node concept = getConcept(page.getId(), page.getName(), type);
			utils.relate(profile, concept, RelationshipTypes.LIKES);
		}
	}
	
	private void createLikes(List<Page> list, Node profile){
		for (org.springframework.social.facebook.api.Page page : list) {
			if(FType.isSupportedType(page.getCategory())){
				System.out.println(":: saving page:: "+page.getName());
				Node concept = getConcept(page.getId(), page.getName(), page.getCategory());
				utils.relate(profile, concept, RelationshipTypes.LIKES);
			}
		}
	}
	
	private Node getConcept(String id, String name, String type){
		Node indexed = concepts.getDomainNode(id);
		Concept concept = null;
    	if(indexed == null){
    		concept = new Concept(id,name,type);
    		Node back = concepts.persist(concept);
    		concepts.assignConceptForUpdate(concept);
			return back;
    	}else{
    		return indexed;
    	}
	}
	
}
