package com.semlab.server.resources;

import java.util.ArrayList;
import java.util.Iterator;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.stereotype.Component;

@Component
public class Neo4jUtils {

	public Relationship relate(Node from, Node to, RelationshipType type){
		//check if rel exist
		Iterable<Relationship> iterable = from.getRelationships(type, Direction.OUTGOING);
		for (Relationship relationship : iterable) {
			if(relationship.getEndNode().equals(to)){
				return relationship;
			}
		}
		return from.createRelationshipTo(to, type);
	}
	
	public void delete(Node node){
		node.delete();
	}
	
	public void setNodeProperty(Node node, String key, Object value){
		if(value == null)
			return;
		
		if(value instanceof Long){
			Long l = (Long) value;
			if(l == 0L)
				return;
		}
		
		if(value instanceof Integer){
			Integer i = (Integer) value;
			if(i == 0)
				return;
		}
		
		node.setProperty(key, value.toString());
		
	}


	public void setRelationshipProperty(Relationship rel,
			String key, String value) {						
		rel.setProperty(key, value);
	}
	
	public ArrayList<Node> getNodesByRelationship(Node node, RelationshipType type, Direction dir, boolean destroy){
		ArrayList<Node> nodes = new ArrayList<Node>();
		Iterable<Relationship> rels = node.getRelationships(type, dir);
		for (Relationship relationship : rels) {
			if(dir == Direction.OUTGOING)
				nodes.add(relationship.getEndNode());
			else
				nodes.add(relationship.getStartNode());
			if(destroy)
				relationship.delete();
		}
		return nodes;
	}
	
	public void deleteSubgraph(Node node, RelationshipType... types){
		Iterator<Relationship> iterator;
		if(types == null){
			iterator = node.getRelationships(Direction.OUTGOING).iterator();
		}else{
			iterator = node.getRelationships(Direction.OUTGOING,types).iterator();
		}
		if(iterator.hasNext()){
			while(iterator.hasNext()){
				Relationship rel = iterator.next();
				deleteSubgraph(rel.getEndNode());
				rel.delete();
			}
		}else{
			node.delete();
		}
	}
	
}
