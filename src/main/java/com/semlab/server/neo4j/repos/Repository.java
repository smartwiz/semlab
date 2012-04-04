package com.semlab.server.neo4j.repos;

import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import com.semlab.server.resources.Neo4jUtils;
import com.semlab.shared.DomainObject;
import com.semlab.shared.config.Things;

@Configurable
public abstract class Repository<T extends DomainObject> {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected static final String PROJECTS = "projects";
	protected static final String REPORTS = "reports";
	protected static final String CONCEPTS = "concepts";
	protected static final String PROFILE = "profile";
	protected static final String CONCEPTS_FOR_UPDATE = "concepts_for_update";
	protected static final String CONCEPTS_FOR_ENRICH = "concepts_for_enrich";
	protected static final String TYPES = "types";
	protected static final String WEATHER_PLACES = "weather_placeS";
	
	@Autowired
	Neo4jUtils utils;

	@Autowired
	protected Neo4jTemplate template;

	@Autowired
	EmbeddedGraphDatabase graphdb;
	
    @Autowired
    Things things;
    
    protected Object lock = new Object();
	
	public abstract Node persist(T object);
	public abstract T getById(String id);
	public abstract Node getDomainNode(String id);
	public abstract T getByNode(Node node);
	
}
