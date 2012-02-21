package com.semlab.server.sparql;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

public class SparqlExecutor {
	
	private QueryExecution executor;
	private String query;
	private SparqlResultHandler handler;
	private String sparqlEndpoint;
	
	public SparqlExecutor(String query, String sparqlEndpoint, SparqlResultHandler handler) {
		this.query = query;
		this.handler = handler;
		this.sparqlEndpoint = sparqlEndpoint;
	}
	
	private void create(){
		executor = QueryExecutionFactory.sparqlService(sparqlEndpoint,  QueryFactory.create(query, Syntax.syntaxARQ));
	}
	
	private void close(){
		executor.close();
	}

	public void executeSelect(){
		create();
		handler.handleResult(executor.execSelect());
		close();
	}
	
	public void executeConstruct(){
		throw new RuntimeException("Not yet implemented");
	}
}
