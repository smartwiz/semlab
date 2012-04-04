package com.semlab.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gwtwidgets.server.spring.GWTRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import com.semlab.shared.config.Configuration;
import com.semlab.shared.services.SemlabService;

/**
 * The server side implementation of the RPC service.
 */
@Service
@GWTRequestMapping("/semlab/gwt.rpc")
public class SemlabServiceImpl implements SemlabService {

	private static Log log = LogFactory.getLog(SemlabServiceImpl.class);

	@Autowired
	Configuration config;

	public SemlabServiceImpl() {
	}

	@Override
	public Configuration getConfig() {
		return config;
	}

	public void importFacebookData(String accessToken) {
		Facebook facebook = new FacebookTemplate(accessToken);
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		List<Post> posts = facebook.feedOperations().getFeed(0, 100);
		for (Post post : posts) {
			log.debug(":: FEED :: " + post.getDescription() + " "
					+ post.getMessage() + " " + post.getCaption());
		}
		String profileId = profile.getFirstName();
		List<FacebookProfile> friends = facebook.friendOperations()
				.getFriendProfiles();
		for (FacebookProfile friend : friends) {
			List<org.springframework.social.facebook.api.Page> movies = facebook
					.likeOperations().getMovies(friend.getId());
			for (org.springframework.social.facebook.api.Page page : movies) {
				log.debug(":: TEST :: movie = " + page.getName());
			}
		}
		log.debug(":: TEST :: first name = " + profileId);
	}

	@Override
	public void startImport(String token) {
		log.debug(":: Starting import ::");
		Facebook facebook = new FacebookTemplate(token);
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		List<Post> posts = facebook.feedOperations().getFeed(0, 100);
		for (Post post : posts) {
			log.debug(":: FEED :: " + post.getDescription() + " "
					+ post.getMessage() + " " + post.getCaption());
		}
		String profileId = profile.getFirstName();
		List<FacebookProfile> friends = facebook.friendOperations()
				.getFriendProfiles();
		for (FacebookProfile friend : friends) {
			List<org.springframework.social.facebook.api.Page> movies = facebook
					.likeOperations().getMovies(friend.getId());
			for (org.springframework.social.facebook.api.Page page : movies) {
				log.debug(":: TEST :: movie = " + page.getName());
			}
		}
		log.debug(":: TEST :: first name = " + profileId);
	}

	@Override
	public void startEnrich(String token) {
		log.debug(":: Starting enrichment ::");
		Facebook facebook = new FacebookTemplate(token);
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		List<Post> posts = facebook.feedOperations().getFeed(0, 100);
		for (Post post : posts) {
			log.debug(":: FEED :: " + post.getDescription() + " "
					+ post.getMessage() + " " + post.getCaption());
		}
		String profileId = profile.getFirstName();
		List<FacebookProfile> friends = facebook.friendOperations()
				.getFriendProfiles();
		for (FacebookProfile friend : friends) {
			List<org.springframework.social.facebook.api.Page> movies = facebook
					.likeOperations().getMovies(friend.getId());
			for (org.springframework.social.facebook.api.Page page : movies) {
				log.debug(":: TEST :: movie = " + page.getName());
			}
		}
		log.debug(":: TEST :: first name = " + profileId);
	}

}
