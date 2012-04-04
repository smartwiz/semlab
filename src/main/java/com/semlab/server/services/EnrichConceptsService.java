package com.semlab.server.services;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.semlab.server.enrichment.DBpediaExtractor;
import com.semlab.server.neo4j.repos.ConceptRepository;
import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;
import com.semlab.shared.resources.NamespaceConstants;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class EnrichConceptsService extends BaseService{

	private static Log log = LogFactory.getLog(EnrichConceptsService.class);
	
	@Scheduled(fixedRate=1000)
	public void process() {

		// get concept from neo4j
		Concept concept = concepts.getNextConceptToEnrich();
		
		if (concept != null && concept.getDateEnriched() + ConceptRepository.ENRICH_OFFSET < new Date().getTime()) {
				
			log.debug(":: trying to enrich concept: " + concept);
	
			// ask nemanja for concepts
			Concept dbconcept = dbpedia.enrichConcept(concept);
			
			if(dbconcept == null){
				concept.changeDateEnriched(false);
				concept.incrementEnrichCounter();
				if(concept.getEnrichCounter() < ConceptRepository.ENRICH_LIMIT){
					concepts.assignConceptForEnrichment(concept);
				}else{
					concept.setEnriched(true);
				}
				concepts.persist(concept);
			}else{
				dbconcept.changeDateEnriched(true);
				concepts.persist(dbconcept);
			}

		} else {
//			log.debug(":: No concepts to enrich in queue ::");
		}

	}
	
}
