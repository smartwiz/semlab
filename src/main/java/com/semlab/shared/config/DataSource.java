package com.semlab.shared.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataSource implements Serializable {
	
	private String sparqlEndpoint;

	public String getSparqlEndpoint() {
		return sparqlEndpoint;
	}

	public void setSparqlEndpoint(String sparqlEndpoint) {
		this.sparqlEndpoint = sparqlEndpoint;
	}

	@Override
	public String toString() {
		return "DataSource [sparqlEndpoint=" + sparqlEndpoint + "]";
	}
	
}
