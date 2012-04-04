package com.semlab.server.services;

import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.semlab.server.neo4j.repos.ConceptRepository;
import com.semlab.server.resources.Concept;
import com.semlab.server.resources.RelationshipTypes;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class UpdateConceptsService extends BaseService{

	private static Log log = LogFactory.getLog(UpdateConceptsService.class);

	@Scheduled(fixedRate=1000)
	public void process(){
		// get concept from neo4j
		Concept concept = concepts.getNextConceptToUpdate();
		if (concept != null && concept.getDateUpdated() + ConceptRepository.ENRICH_OFFSET < new Date().getTime()) {
			log.debug(":: trying to get concept uri: " + concept);
			Concept dbconcept = getUriFromDB(concept);
			if (dbconcept.getUri() != null) {
				log.debug(":: found uri! "+dbconcept.getUri());
				concepts.persist(dbconcept);
				concepts.assignConceptForEnrichment(dbconcept);
			}else{
				concept.changeDateUpdated();
				concept.incrementUriCounter();
				if(concept.getUriCounter() < ConceptRepository.ENRICH_LIMIT){
					concepts.assignConceptForUpdate(concept);
				}else{
					concept.setEnriched(true);
				}
				concepts.persist(concept);
			}

		} else {
//			log.debug(":: No concepts to update in queue ::");	
		}
	}
	
	private Concept getUriFromDB(Concept concept) {
		Concept dbconcept = dbpedia.chooseBestUri(concept);
		if (dbconcept != null) {

			// search by uri
			Node inIndex = concepts.getConceptByUri(dbconcept.getUri());
			Node dbnode = null;
			if (inIndex == null) {
				dbnode = concepts.persist(dbconcept);
			} else {
				// concept already exist, connect it for profiles
				dbnode = concepts.persist(dbconcept);
				ArrayList<Node> nodes = utils.getNodesByRelationship(dbnode,
						RelationshipTypes.LIKES, Direction.INCOMING, true);
				for (Node node : nodes) {
					utils.relate(node, inIndex, RelationshipTypes.LIKES);
				}
			}
			return dbconcept;
		}
		return concept;
	}
	
}
