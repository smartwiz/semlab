package com.semlab.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.semlab.server.neo4j.repos.ConceptRepository;
import com.semlab.server.neo4j.repos.ProfileRepository;
import com.semlab.server.resources.Neo4jUtils;
import com.semlab.server.resources.Root;
import com.semlab.server.services.EnrichConceptsService;
import com.semlab.server.services.InsertProfileService;
import com.semlab.server.services.UpdateConceptsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-context.xml", "/spring/*.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class Test {

	@Autowired
	Neo4jTemplate template;

	@Autowired
	Root root;
	
	@Autowired
	ProfileRepository profiles;
	
	@Autowired
	ConceptRepository concepts;
	
	@Autowired
	Neo4jUtils utils;
	
	@Autowired
	InsertProfileService insertService;
	
	@Autowired
	EnrichConceptsService enrichService;
	
	@Autowired
	UpdateConceptsService updateService;
	
	private static final String TOKEN = "AAACEdEose0cBANxZAXViXPN0ZCqza2uw7uZAlgYaFQVdkxPvkgdPzKQxAyTKo6OkZB5wTvAA1pFSz8kePjDb0w5PZAyGWzbkZAIIZBeu0QyB97xbTtZA0S9s";
	
	@org.junit.Test
	public void test() {
//		insertService.process(TOKEN);
//		updateService.process();
//		enrichService.process();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
