package com.semlab.server.sparql;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class QueryRetriever {
	
	private static Log log = LogFactory.getLog(QueryRetriever.class);
	
	private HashMap<String, SparqlQuery> queries;

	public HashMap<String, SparqlQuery> getQueries() {
		return queries;
	}

	public void setQueries(HashMap<String, SparqlQuery> queries) {
		this.queries = queries;
	}
	
	public SparqlQuery getQuery(String id){
		SparqlQuery query = queries.get(id);
		log.debug("Query: "+query.getQuery());
		return query;
	}
	

}
