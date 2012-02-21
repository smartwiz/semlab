package com.semlab.server.resources;

import javax.annotation.PostConstruct;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class Root {

	@Autowired
	Neo4jTemplate template;

	private Node node;

	public Root() {
	}

	@PostConstruct
	private void init() {
		node = template.getNode(0L);
	}

	public void relate(Node to) {
		node.createRelationshipTo(to, RelationshipTypes.ROOT);
	}

	public void relate(Node to, RelationshipType type) {
		node.createRelationshipTo(to, type);
	}
}
