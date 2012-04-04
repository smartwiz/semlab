package com.semlab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.semlab.server.enrichment.DBpediaExtractor;
import com.semlab.server.neo4j.repos.ConceptRepository;
import com.semlab.server.neo4j.repos.ProfileRepository;
import com.semlab.server.resources.Neo4jUtils;
import com.semlab.server.resources.Root;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class BaseService {

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
	DBpediaExtractor dbpedia;
	
}
