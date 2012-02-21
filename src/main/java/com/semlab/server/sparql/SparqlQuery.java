package com.semlab.server.sparql;

public class SparqlQuery {

	private String query;
	private boolean includeInferred;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isIncludeInferred() {
		return includeInferred;
	}

	public void setIncludeInferred(boolean includeInferred) {
		this.includeInferred = includeInferred;
	}

	@Override
	public String toString() {
		return "SparqlQuery [query=" + query
				+ ", includeInferred=" + includeInferred + "]";
	}
	
}
