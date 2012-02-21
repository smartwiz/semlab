package com.semlab.server.sparql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.semlab.shared.config.Property;
import com.semlab.shared.config.Thing;

public class QueryBuilder {
	
	public static String buildEnrichConcept(String conceptUri, Thing thing) {
		List<Property> properties = thing.getProperties();
		List<Property> dbProperties = new LinkedList<Property>();
		int varSize = properties.size();
		for (int i=0; i < varSize; i++) {
			if(!properties.get(i).isIgnoreDBP()){
				dbProperties.add(properties.get(i));
			}
		}
		int nuVarSize = dbProperties.size();
		StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT ");
		List<String> vars = new ArrayList<String>();
		List<String> filterVars = new ArrayList<String>();
		for (int i = 0; i < nuVarSize; i++) {
			queryBuilder.append("?var" + (i+1) + " ");
			vars.add("?var"+(i+1));
		}
		queryBuilder.append("WHERE {");
		for (int i = 0; i < nuVarSize; i++) {
			if(!dbProperties.get(i).isIgnoreDBP()){
				if (dbProperties.get(i).isOptional()){
					queryBuilder.append("OPTIONAL{");
				}
				if (dbProperties.get(i).isInverse()) {
					queryBuilder.append(vars.get(i) + " " + "<" + dbProperties.get(i).getUri() + ">" + " " + "<" + conceptUri + ">. ");
				} else {
					queryBuilder.append("<" + conceptUri + ">" + " " + "<" + dbProperties.get(i).getUri() + ">" + " " + vars.get(i) + ". ");
				}
				if (dbProperties.get(i).isOptional()){
					queryBuilder.append("} ");
				}
				if(dbProperties.get(i).getFilter() != null){
					filterVars.add(vars.get(i));
				}
			}
		}
		if (filterVars.size() != 0) {
			queryBuilder.append("FILTER (");
			for (int i = 0; i < filterVars.size(); i++) {
				if(i==0){
					queryBuilder.append("lang("+filterVars.get(i)+")='en'");
				} else {
					queryBuilder.append(" && lang("+filterVars.get(i)+")='en'");
				}
			}
			queryBuilder.append(")");
		}
		queryBuilder.append("}");
		// TODO:Nemanja:Implement filter based on a label and complex types
		return queryBuilder.toString();
	}
	
}
