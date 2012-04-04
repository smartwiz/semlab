package com.semlab.server.neo4j.repos;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.index.lucene.QueryContext;
import org.neo4j.index.lucene.ValueContext;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.index.NoSuchIndexException;
import org.springframework.stereotype.Component;

import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;
import com.semlab.server.resources.Profile;
import com.semlab.server.resources.RelationshipTypes;
import com.semlab.server.resources.RelationshipTypes.MyRelationshipType;
import com.semlab.shared.DomainObject;
import com.semlab.shared.Type;
import com.semlab.shared.resources.NamespaceConstants;

@Component
public class ConceptRepository extends Repository<Concept>{

	private Index<Node> concepts;
	private Index<Node> conceptsForUpdate;
	private Index<Node> conceptsForEnrich;
	
	public static final int ENRICH_LIMIT = 2;
	public static final int ENRICH_OFFSET = 3000;
	
	
	
	public ConceptRepository() {
	}
	
	@PostConstruct
	public void init() {
		try {
			concepts = template.getIndex(CONCEPTS);
			conceptsForUpdate = template.getIndex(CONCEPTS_FOR_UPDATE);
			conceptsForEnrich = template.getIndex(CONCEPTS_FOR_ENRICH);
		} catch (NoSuchIndexException e) {
			graphdb.index().forNodes(CONCEPTS);
			graphdb.index().forNodes(CONCEPTS_FOR_ENRICH);
			graphdb.index().forNodes(CONCEPTS_FOR_UPDATE);
			init();
		}
	}

	public Node persist(Concept concept) {
		
		Node node = null;
		
		/**
		 * extract node from index before creating
		 */
		
		//regular index
		if(concept.getId() != null){
			Node inIndex = getConcept(NamespaceConstants.NAME+NamespaceConstants.TYPE, concept.getId());
			if(inIndex != null){
				node = inIndex;
			}else{
				node = template.createNode();
				node.setProperty(NamespaceConstants.DATE_CREATED, new Date().getTime());
				addConcept(node, concept.getId());
			}
		}
		
		//uri index
		if(concept.getUri() != null){
			Node inUriIndex = getConcept(NamespaceConstants.URI, concept.getUri());
			if(inUriIndex != null){
				node = inUriIndex;
			}else{
				if(node == null){
					node = template.createNode();
					node.setProperty(NamespaceConstants.DATE_CREATED, new Date().getTime());
				}
				
				addConceptByUri(node, concept.getUri());
			}
		}

		if(node == null){
			node = template.createNode();
			node.setProperty(NamespaceConstants.DATE_CREATED, new Date().getTime());
		}
		
		concept.setNodeId(node.getId());
		
		
		
		/**
		 * parse node
		 */
		
		if (concept.getId() != null){
			node.setProperty(NamespaceConstants.CONCEPT_ID, concept.getId());
		}
		
		utils.setNodeProperty(node, NamespaceConstants.ENRICHED, concept.isEnriched());
		
		utils.setNodeProperty(node, NamespaceConstants.URI_COUNTER, concept.getUriCounter());
		utils.setNodeProperty(node, NamespaceConstants.ENRICH_COUNTER, concept.getEnrichCounter());

		
		if (concept.getName() != null)
			node.setProperty(NamespaceConstants.NAME, concept.getName());
		if (concept.getType() != null)
			node.setProperty(NamespaceConstants.TYPE, concept.getType());
		if (concept.getUri() != null){
			node.setProperty(NamespaceConstants.URI, concept.getUri());
		}
		if (concept.getProperties() != null && concept.getProperties().size() > 0) {
			Set<String> keys = concept.getProperties().keySet();
			for (String key : keys) {
				Object property = concept.getProperties().get(key);
				if (property instanceof Concept) {
					Concept c = (Concept) property;
					Node n = persist(c);
					
					utils.relate(node, n, new MyRelationshipType(key));
				} else if (property instanceof ArrayList) {
					try{
						ArrayList<Concept> concepts = (ArrayList<Concept>) property;
						for (Concept c : concepts) {
							Node n = persist(c);
							
							utils.relate(node, n, new MyRelationshipType(key));
						}
					}catch (Exception e) {
						ArrayList<Object> properties = (ArrayList<Object>) property;
						differenciateAndSetProperties(properties, node, key);
					}
				} else{
					node.setProperty(key, property);
				}
			}
		}
		
		if(concept.getDateCreated() != 0)
			node.setProperty(NamespaceConstants.DATE_CREATED, concept.getDateCreated());

		if(concept.getDateUpdated() != 0)
			node.setProperty(NamespaceConstants.DATE_UPDATED, concept.getDateUpdated());

		if(concept.getDateEnriched() != 0L)
			node.setProperty(NamespaceConstants.DATE_ENRICHED, concept.getDateEnriched());

		
		return node;
	}

	private void differenciateAndSetProperties(ArrayList<Object> properties, Node node, String key) {
		ArrayList<String> stringProperties = new ArrayList<String>();
		ArrayList<Integer> intProperties = new ArrayList<Integer>();
		ArrayList<Long> longProperties = new ArrayList<Long>();
		ArrayList<Double> doubleProperties = new ArrayList<Double>();
		for (Object object : properties) {
			if(object instanceof Integer){
				intProperties.add((Integer) object);
			} 
			if(object instanceof Long){
				longProperties.add((Long) object);
			}
			if(object instanceof Double){
				doubleProperties.add((Double) object);
			}
			if(object instanceof String){
				stringProperties.add((String) object);
			}
		}
		
		
		if(stringProperties.size() > 0){
			Set<String> setOfStringProperties = new LinkedHashSet<String>(stringProperties);
			ArrayList<String> noDoubleStringProps = new ArrayList<String>(setOfStringProperties);
			String[] stringProps = new String[noDoubleStringProps.size()];
			for (int i = 0; i < noDoubleStringProps.size(); i++) {
				stringProps[i] = noDoubleStringProps.get(i);
			}
			node.setProperty(key, stringProps[0]);
//			if(stringProps.length == 1){
//				node.setProperty(key, stringProps[0]);
//			} else {
//				node.setProperty(key, stringProps);
//			}
		}
		
		if(intProperties.size() > 0){
			Set<Integer> setOfIntProperties = new LinkedHashSet<Integer>(intProperties);
			ArrayList<Integer> noDoubleIntProps = new ArrayList<Integer>(setOfIntProperties);
			Integer[] intProps = new Integer[noDoubleIntProps.size()];
			for (int i = 0; i < noDoubleIntProps.size(); i++) {
				intProps[i] = noDoubleIntProps.get(i);
			}
			node.setProperty(key, intProps[0]);
//			if(intProps.length == 1){
//				node.setProperty(key, intProps[0]);
//			} else {
//				node.setProperty(key, intProps);
//			}
		}
		
		if(longProperties.size() > 0){
			Set<Long> setOfLongProperties = new LinkedHashSet<Long>(longProperties);
			ArrayList<Long> noDoubleLongProps = new ArrayList<Long>(setOfLongProperties);
			Long[] longProps = new Long[noDoubleLongProps.size()];
			for (int i = 0; i < noDoubleLongProps.size(); i++) {
				longProps[i] = noDoubleLongProps.get(i);
			}
			node.setProperty(key, longProps[0]);
//			if(longProps.length == 1){
//				node.setProperty(key, longProps[0]);
//			} else {
//				node.setProperty(key, longProps);
//			}
		}
		
		if(doubleProperties.size() > 0){
			Set<Double> setOfDoubleProperties = new LinkedHashSet<Double>(doubleProperties);
			ArrayList<Double> noDoubleDoubleProps = new ArrayList<Double>(setOfDoubleProperties);
			Double[] doubleProps = new Double[noDoubleDoubleProps.size()];
			for (int i = 0; i < noDoubleDoubleProps.size(); i++) {
				doubleProps[i] = noDoubleDoubleProps.get(i);
			}
			node.setProperty(key, doubleProps[0]);
//			if(doubleProps.length == 1){
//				node.setProperty(key, doubleProps[0]);
//			} else {
//				node.setProperty(key, doubleProps);
//			}
		}
		
	}

	public Concept getByNode(Node node){
			Concept concept = new Concept();
			if (node != null) {
				
				concept.setNodeId(node.getId());
				
				Iterable<String> iterable = node.getPropertyKeys();
				for (String key : iterable) {
					
					if (key.equals(NamespaceConstants.CONCEPT_ID)){
						concept.setId(node.getProperty(NamespaceConstants.CONCEPT_ID).toString());
						continue;
					}
					
					if (key.equals(NamespaceConstants.NAME)){
						concept.setName(node.getProperty(NamespaceConstants.NAME).toString());
						continue;
					}
					
					if (key.equals(NamespaceConstants.TYPE)){
						concept.setType(node.getProperty(NamespaceConstants.TYPE).toString());
						continue;
					}
					
					if (key.equals(NamespaceConstants.URI)){
						concept.setUri(node.getProperty(NamespaceConstants.URI).toString());
						continue;
					}
					
					
					if (key.equals(NamespaceConstants.DATE_CREATED)){
						concept.setDateCreated(Long.parseLong(node.getProperty(NamespaceConstants.DATE_CREATED).toString()));
						continue;
					}
					

					if (key.equals(NamespaceConstants.DATE_UPDATED)){
						concept.setDateUpdated(Long.parseLong(node.getProperty(NamespaceConstants.DATE_UPDATED).toString()));
						continue;
					}
					
					
					if (key.equals(NamespaceConstants.DATE_ENRICHED)){
						concept.setDateEnriched(Long.parseLong(node.getProperty(NamespaceConstants.DATE_ENRICHED).toString()));
						continue;
					}
					

					if (key.equals(NamespaceConstants.ENRICHED)){
						concept.setEnriched(Boolean.parseBoolean(node.getProperty(NamespaceConstants.ENRICHED).toString()));
						continue;
					}
					
					if (key.equals(NamespaceConstants.ENRICH_COUNTER)){
						concept.setEnrichCounter(Integer.parseInt(node.getProperty(NamespaceConstants.ENRICH_COUNTER).toString()));
						continue;
					}
					
					if (key.equals(NamespaceConstants.URI_COUNTER)){
						concept.setUriCounter(Integer.parseInt(node.getProperty(NamespaceConstants.URI_COUNTER).toString()));
						continue;
					}
					
//					if (key.equals(NamespaceConstants.GEO_LAT)){
//						concept.(node.getProperty(NamespaceConstants.GEO_LAT).toString());
//						continue;
//					}
					
					concept.addProperty(key, node.getProperty(key));
					
				}
			}
		return concept;
	}
	
	public Node getDomainNode(String id) {
//		log.debug(":: index found concept with id: "+id+" ::");
		IndexHits<Node> nodes = concepts.get(NamespaceConstants.NAME+NamespaceConstants.TYPE, id);
		return nodes.getSingle();
	}
	
	//TODO:Nemanja:add indexing with name because of LinkedIn
//	public Node getDomainNodeByName(String name){
//		IndexHits<Node> nodes = concepts.get(NamespaceConstants.NAME, value);
//	}
	

	@Override
	public Concept getById(String id) {
		Node node = getDomainNode(id);
		return (node != null)? getByNode(node): null;
	}
	
	public Node getConceptByUri(String uri) {
		IndexHits<Node> nodes = concepts.get(NamespaceConstants.URI, uri);
		return nodes.getSingle();
	}
	
	public Node getConcept(String key, String value) {
		IndexHits<Node> nodes = concepts.get(key, value);
		return nodes.getSingle();
	}

	public void addConcept(Node concept, String id) {
		concepts.add(concept, NamespaceConstants.NAME+NamespaceConstants.TYPE, id);
	}
	
	public void addConcept(Node concept, String key, String value) {
		concepts.add(concept, key, value);
	}
	
	public void addConceptByUri(Node concept, String uri) {
		concepts.add(concept, NamespaceConstants.URI, uri);
	}
	
	public void assignConceptForEnrichment(Concept concept) {
		synchronized (lock) {
			conceptsForEnrich.add(template.getNode(concept.getNodeId()),
					NamespaceConstants.DATE_ENRICHED, new ValueContext( concept.getDateEnriched() ).indexNumeric() );
		}
	}
	

	public void assignConceptForUpdate(Concept concept) {
		log.debug(":: assigning concept for update ="+concept);
		conceptsForUpdate.add(template.getNode(concept.getNodeId()),
				NamespaceConstants.DATE_UPDATED, new ValueContext( concept.getDateUpdated()).indexNumeric() );
	}
	


	public void removeFromCE(DomainObject concept) {
		conceptsForEnrich.remove(template.getNode(concept.getNodeId()), NamespaceConstants.DATE_ENRICHED);
	}
	public void removeFromCU(DomainObject concept) {
		conceptsForUpdate.remove(template.getNode(concept.getNodeId()), NamespaceConstants.DATE_UPDATED);
	}

	public Concept getNextConceptToEnrich() {
		synchronized (lock) {
			IndexHits<Node> hits = conceptsForEnrich.query(new QueryContext(
					NumericRangeQuery.newLongRange(NamespaceConstants.DATE_ENRICHED,
							Long.MIN_VALUE, Long.MAX_VALUE, true, true))
					.sort(new Sort(new SortField(NamespaceConstants.DATE_ENRICHED,
							SortField.LONG, false))));
	
			if (hits.hasNext()) {
				Node job = hits.next();
				Concept concept = getByNode(job);
				removeFromCE(concept);
				return concept;
			}
			
		}
		return null;
	}
	
	public Concept getNextConceptToUpdate() {
		synchronized (lock) {
			IndexHits<Node> hits = conceptsForUpdate.query(new QueryContext(
					NumericRangeQuery.newLongRange(NamespaceConstants.DATE_UPDATED,
							Long.MIN_VALUE, Long.MAX_VALUE, true, true))
					.sort(new Sort(new SortField(NamespaceConstants.DATE_UPDATED,
							SortField.LONG, false))));
	
			if (hits.hasNext()) {
				Node job = hits.next();
				System.out.println(job.getId());
				Concept concept = getByNode(job);
				removeFromCU(concept);
				return concept;
			}
		}
		return null;
	}
	

	/**
	 * 
	 * @param profile to start from
	 * @param types to filter
	 * @return list of concepts
	 */
	public ArrayList<Concept> getConceptsForProfile(final Profile person, final List<Type> types){
		final Node node = template.getNode(person.getNodeId());
		ArrayList<Concept> concepts = new ArrayList<Concept>();
	    TraversalDescription td = Traversal.description()
	            .depthFirst()
	            .relationships(RelationshipTypes.LIKES, Direction.OUTGOING)
	            .relationships(RelationshipTypes.FRIEND, Direction.OUTGOING)
	            .relationships(RelationshipTypes.BORN_IN, Direction.OUTGOING)
	            .relationships(RelationshipTypes.LIVES_IN, Direction.OUTGOING)
	            .relationships(RelationshipTypes.EDUCATED_AT, Direction.OUTGOING)
	            .relationships(RelationshipTypes.WORKS_AT, Direction.OUTGOING)
	            .relationships(RelationshipTypes.BASED_IN, Direction.OUTGOING)
	            .evaluator(new Evaluator() {
					@Override
					public Evaluation evaluate(Path path) {
						if(path.lastRelationship() != null){
							if(path.lastRelationship().getType().equals(RelationshipTypes.FRIEND)){
								if(!path.lastRelationship().getStartNode().equals(node)){
									return Evaluation.EXCLUDE_AND_PRUNE;
								}
								else {				
									return Evaluation.EXCLUDE_AND_CONTINUE;
								}
							}else{
								if(path.endNode().hasProperty(NamespaceConstants.TYPE)){
									String conceptType = path.endNode().getProperty(NamespaceConstants.TYPE).toString();
//									if(things.isInTypes(types,conceptType) | (FType.getTypeForFBType(conceptType) != null && things.findThing(FType.getTypeForFBType(conceptType).getUri()) != null)){
//										return Evaluation.INCLUDE_AND_PRUNE;
//									} else {
//										return Evaluation.EXCLUDE_AND_PRUNE;
//									}
									if(conceptType.startsWith("http")){
										if(things.isInTypes(types, conceptType)){
											return Evaluation.INCLUDE_AND_PRUNE;
										} else {
											return Evaluation.EXCLUDE_AND_PRUNE;
										}
									} else {
										if(things.isInTypesFromFBTypes(types, conceptType)){
											return Evaluation.INCLUDE_AND_PRUNE;
										} else {
											return Evaluation.EXCLUDE_AND_PRUNE;
										}
									}
								} else {
									return Evaluation.EXCLUDE_AND_PRUNE;
								}
								
							}
						}else
							return Evaluation.EXCLUDE_AND_CONTINUE;
					}
				});
	    Traverser t = td.traverse(node);
	    for (Path path : t) {
	    	Concept c = getByNode(path.endNode());
	    	concepts.add(c);
	    }
	    return concepts;
	}

	public String getUri(Node node) {
		if(node.hasProperty(NamespaceConstants.URI)){
			return node.getProperty(NamespaceConstants.URI).toString();
		}
		return null;
	}
	
}
