package com.semlab.server.enrichment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.semlab.server.enrichment.util.JaroWinklerDistance;
import com.semlab.server.http.HttpManager;
import com.semlab.server.http.HttpQueryBuilder;
import com.semlab.server.http.XMLLookupAPIParser;
import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;
import com.semlab.server.sparql.QueryBuilder;
import com.semlab.shared.config.Property;
import com.semlab.shared.config.Thing;
import com.semlab.shared.config.Things;

@Component
public class DBpediaExtractor {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private Things things;
	
	@Autowired
	private HttpManager httpManager;
	
	@Autowired
	private XMLLookupAPIParser xmlParser;

//	private List<Concept> getUrisForConcept(Concept conceptToGetURIFor) {
//		final List<Concept> uris = new LinkedList<Concept>();
//
//		if (conceptToGetURIFor.getType().equalsIgnoreCase("Musician/Band")
//				| conceptToGetURIFor.getType().equalsIgnoreCase("Music")) {
//			String typeMusician = FType.Musician.getFbLabel();
//			conceptToGetURIFor.setType(typeMusician);
//			String queryMusician = QueryBuilder.buildGetUriForConcept(conceptToGetURIFor);
//			log.debug("Find dbpedia URI " + queryMusician);
//			Thing thingMusician = things.findThing(FType.Musician.getUri());
//			new SparqlExecutor(queryMusician, thingMusician.getSource().getSparqlEndpoint(), new SparqlResultHandler() {
//
//				@Override
//				public void handleResult(ResultSet results) {
//					while (results.hasNext()) {
//						QuerySolution solution = results.next();
//						String tempUri = solution.get("URI").toString();
//						Concept c = new Concept();
//						c.setUri(tempUri);
//						c.setType(FType.Musician.getUri());
//						uris.add(c);
//					}
//
//				}
//			}).executeSelect();
//
//			if (uris.size() == 0) {
//				String typeBand = FType.Band.getFbLabel();
//				conceptToGetURIFor.setType(typeBand);
//				String queryBand = QueryBuilder.buildGetUriForConcept(conceptToGetURIFor);
//				log.debug("Find dbpedia URI " + queryBand);
//				Thing thingBand = things.findThing(FType.Band.getUri());
//				new SparqlExecutor(queryBand, thingBand.getSource().getSparqlEndpoint(), new SparqlResultHandler() {
//
//							@Override
//							public void handleResult(ResultSet results) {
//								while (results.hasNext()) {
//									QuerySolution solution = results.next();
//									String tempUri = solution.get("URI")
//											.toString();
//									Concept c = new Concept();
//									c.setUri(tempUri);
//									c.setType(FType.Band.getUri());
//									uris.add(c);
//								}
//							}
//						}).executeSelect();
//			}
//
//		} else {
//			final String uri = conceptToGetURIFor.getType();
////			log.debug("URI " + uri);
////					.getUri();
//			String query;
//			if (uri.equalsIgnoreCase(FType.Place.getFbLabel())){
//				query = QueryBuilder.buildGetUriForPlace(conceptToGetURIFor);
//				log.debug("Find dbpedia URI PLACE " + query);
//			} else {
//				query = QueryBuilder.buildGetUriForConcept(conceptToGetURIFor);
//				log.debug("Find dbpedia URI " + query);
//			}
//			Thing thing = things.findThing(FType.getTypeForFBType(uri).getUri());
//			
//			new SparqlExecutor(query, thing.getSource().getSparqlEndpoint(), new SparqlResultHandler() {
//
//				@Override
//				public void handleResult(ResultSet results) {
//					while (results.hasNext()) {
//						QuerySolution solution = results.next();
//						String tempUri = solution.get("URI").toString();
//						Concept c = new Concept();
//						c.setUri(tempUri);
//						c.setType(uri);
//						uris.add(c);
////						uris.add(tempUri);
//					}
//
//				}
//			}).executeSelect();
//		}
//		return uris;
//	}
	
	private List<Concept> getUrisForConceptLookupAPI(Concept conceptToGetURIFor){
		List<Concept> uris;
		String httpQuery = HttpQueryBuilder.buildHttpGetDBpediaLookupAddress(conceptToGetURIFor);
		String xmlResponse = httpManager.executeGet(httpQuery);
		boolean unknownType;
		if(FType.getTypeForFBType(conceptToGetURIFor.getType()).getUri() == null){
			unknownType = true;
		} else {
			unknownType = false;
		}
		uris = xmlParser.updateConcept(xmlResponse, unknownType);
		return uris;
	}
	

	public Concept chooseBestUri(Concept concept) {
		List<Concept> uris;
		try {
			uris = getUrisForConceptLookupAPI(concept);
//			uris = getUrisForConcept(concept);
		} catch (Exception e) {
			return null;
		}
		
		if(uris.size() == 0){
			return null;
		}
		
		int index = 0;
		Concept uri = null;
		
		if(uris.size() == 1 && uris.get(index).getType() != null){
			uri = uris.get(index);
		} else {
			double[] jwdValues = new double[uris.size()];
			//TODO:Nemanja:Change method to static
			JaroWinklerDistance jwd = new JaroWinklerDistance();
			for (int i=0; i<uris.size(); i++) {
				if(concept.getType().equalsIgnoreCase(FType.Place.getFbLabel())){
					String[] placeNames = concept.getName().split(", ");
					jwdValues[i] = jwd.distance(uris.get(i).getName(), placeNames[0]);
				} else {
//					jwdValues[i] = jwd.distance(uris.get(i).getUri().substring(uris.get(i).getUri().lastIndexOf("/")), concept.getName());
					jwdValues[i] = jwd.distance(uris.get(i).getName(), concept.getName());
				}
//				log.debug(uris.get(i) + " " + jwdValues[i]);
			}
			double min = jwdValues[0];
			for (int i=1; i<jwdValues.length; i++){
				if(min > jwdValues[i]){
					min = jwdValues[i];
					index = i;
//					log.debug("Current min " + min + " is for " + uris.get(i));
				}
			}
			uri = uris.get(index);
		}
		
		concept.setUri(uri.getUri());
		
		String type = FType.getTypeForFBType(concept.getType()).getUri();
		if (type == null){
			concept.setType(uri.getType());
		} else {
			concept.setType(type);
		}
		
		if(concept.getType() == null){
			concept.setUri(null);
			return null;
		}
		
		log.debug("Found uri: " + concept.getUri() + " Found type: " + concept.getType());
		
		return concept;
	}

	
	public Concept enrichConcept(Concept concept){
		try{
			Thing thing = things.findThing(concept);
	
			String query = QueryBuilder.buildEnrichConcept(concept.getUri(), thing);
	
			QueryExecution executor = QueryExecutionFactory.sparqlService(thing.getSource().getSparqlEndpoint(), QueryFactory.create(query, Syntax.syntaxARQ));
			
			log.debug("::SPARQL enrich concept " + query + " FROM " + thing.getSource().getSparqlEndpoint());
			
			ResultSet results = executor.execSelect();
			
			while (results.hasNext()) {
				QuerySolution solution = results.next();
				for (int i =0; i<thing.getProperties().size(); i++){
					Property property = thing.getProperties().get(i);
					RDFNode solutionNode = solution.get("var"+(i+1));
					if(solutionNode!=null){
						if (property.getTypeOf() != null){
							if(!property.isIgnoreDBP()){
								Concept simpleConcept = new Concept(solutionNode.toString(), property.getTypeOf().getClassType());
								log.debug("property uri " + property.getUri() + " solution is var" + (i+1) + " " + solutionNode.toString());
								Concept enrichedConcept = enrichConcept(simpleConcept);
								if(enrichedConcept != null){
									if(property.isUseDifferentUri()){
										concept.addProperty(property.getOverrideUri(), enrichConcept(simpleConcept));
									} else {
										concept.addProperty(property.getUri(), enrichConcept(simpleConcept));
									}
								}
							}
						} else {
							//TODO:Nemanja:do override uri on simple properties too
							if(!property.isIgnoreDBP()){
								log.debug("property uri " + property.getUri() + " solution value " + (i+1) +" " + solutionNode.asLiteral().getValue());
								if (solutionNode.asLiteral().getValue() instanceof XSDDateTime){
									XSDDateTime dbpDate = (XSDDateTime) solutionNode.asLiteral().getValue();
									concept.addProperty(property.getUri(), dbpDate.asCalendar().getTimeInMillis());
		//							log.debug("property uri " + property.getUri() + " is XSDDateTime");
								} else if(solutionNode.asLiteral().getValue() instanceof Integer) {
									concept.addProperty(property.getUri(), (Integer)solutionNode.asLiteral().getValue());
		//							log.debug("property uri " + property.getUri() + " is JAVA Integer");
								} else if(solutionNode.asLiteral().getValue() instanceof Double) {
									concept.addProperty(property.getUri(), (Double)solutionNode.asLiteral().getValue());
	//								log.debug("property uri " + property.getUri() + " is JAVA Double");
		//						} else if (solutionNode.asLiteral().getValue() instanceof Float){
		//							concept.addProperty(property.getUri(), (Double)solutionNode.asLiteral().getValue());
		////							log.debug("property uri " + property.getUri() + " is JAVA Float");
								} else {
									concept.addProperty(property.getUri(), solutionNode.asLiteral().getValue().toString());
								}
							}
						}
					}
				}
			}
			return concept;
		} catch (Exception e){
			log.debug("DBpedia sucks!");
			e.printStackTrace();
			return null;
		}
	}
	
//	private Concept enrichSimpleConcept(Concept concept){
//		Thing thing = things.findThing(concept);
//		
//		String query = QueryBuilder.buildEnrichConcept(concept.getUri(), thing);
//		
//		QueryExecution executor = QueryExecutionFactory.sparqlService(thing.getSource().getSparqlEndpoint(), QueryFactory.create(query, Syntax.syntaxARQ));
//		log.debug("Query enrich SIMPLE concept " + query);
//		ResultSet results = executor.execSelect();
//		while (results.hasNext()){
//			QuerySolution solution = results.next();
//			for (int i =0; i<thing.getProperties().size(); i++){
//				Property property = thing.getProperties().get(i);
//				RDFNode solutionNode = solution.get("var"+(i+1));
//				if(solutionNode!=null){
//					if(solutionNode.isLiteral()){
//						log.debug("property uri " + property.getUri() + " solution value " + (i+1) +" " + solutionNode.asLiteral().getValue());
//						if (solutionNode.asLiteral().getValue() instanceof XSDDateTime){
//							XSDDateTime dbpDate = (XSDDateTime) solutionNode.asLiteral().getValue();
//							concept.addProperty(property.getUri(), dbpDate.asCalendar().getTimeInMillis());
////							log.debug("property uri " + property.getUri() + " is XSDDateTime");
//						} else if(solutionNode.asLiteral().getValue() instanceof Integer) {
//							concept.addProperty(property.getUri(), (Integer)solutionNode.asLiteral().getValue());
////							log.debug("property uri " + property.getUri() + " is JAVA Integer");
//						} else if(solutionNode.asLiteral().getValue() instanceof Double) {
//							concept.addProperty(property.getUri(), (Double)solutionNode.asLiteral().getValue());
//							log.debug("property uri " + property.getUri() + " is JAVA Double");
////						} else if (solutionNode.asLiteral().getValue() instanceof Float){
////							concept.addProperty(property.getUri(), (Double)solutionNode.asLiteral().getValue());
//////							log.debug("property uri " + property.getUri() + " is JAVA Float");
//						} else {
//							concept.addProperty(property.getUri(), solutionNode.asLiteral().getValue().toString());
//						}
//					} else {
//						log.debug("property uri " + property.getUri() + " solution is var" + (i+1) + solutionNode.asResource().getURI());
//						concept.addProperty(property.getUri(), solutionNode.asResource().getURI());
//					}
//				}
//			}
//		}
//		return concept;
//	}

}
