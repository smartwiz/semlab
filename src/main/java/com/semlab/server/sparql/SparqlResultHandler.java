package com.semlab.server.sparql;

import com.hp.hpl.jena.query.ResultSet;

public interface SparqlResultHandler{
	void handleResult(ResultSet results);
}