package com.semlab.server.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.cypher.commands.Query;
import org.neo4j.cypher.javacompat.CypherParser;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.neo4j.support.Neo4jTemplate;

@Configurable(preConstruction=true)
public class CypherQueryExecutor {

	private static Log log = LogFactory.getLog(CypherQueryExecutor.class);

	@Autowired
	private Neo4jTemplate template;

	private ExecutionEngine engine;
	
	public CypherQueryExecutor() {
	}

	public CypherQueryExecutor(String query){
		this(new CypherQuery(query));
	}

	public CypherQueryExecutor(CypherQuery query) {
		try{
			engine = new ExecutionEngine(template.getGraphDatabaseService());
			CypherParser parser = new CypherParser();
			String stringQuery = query.getQuery();
			log.debug(":: query= "+stringQuery);
			Query q = parser.parse(stringQuery);
			
			ExecutionResult result;
			if(query.hasParameters()){
				result = engine.execute(q, query.getParams());
			}else{
				result = engine.execute(q);
			}
			handleSolution(result);
		}catch(Exception e){
			log.debug("## exception caught in query execution: "+e.getMessage());
			e.printStackTrace();
			handleSolution(null);
		}
	}
	

	// Override to create custom result handling
	public void handleSolution(ExecutionResult result) {
		if(result != null)
			log.debug("\n"+result.toString());
	}
	
}


