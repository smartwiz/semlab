package com.semlab.server.resources;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.semlab.shared.Operator;
import com.semlab.shared.config.PropertyType;
import com.semlab.shared.resources.NamespaceConstants;

public class CypherQuery {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private String skip;
	private String limit;
	private Map<String, Object> params;
	private StringBuilder query;
	private StringBuilder match;
	private StringBuilder where;
	private StringBuilder returnPars;
	private StringBuilder orderBy;
	private boolean aggregate;

	private long startNode = -1L;

	public CypherQuery() {
		this(null);
	}

	public CypherQuery(int offset, int limit) {
		setLimit(limit);
		setOffset(offset);
	}

	public CypherQuery(String query, int offset, int limit) {
		this(query);
		setLimit(limit);
		setOffset(offset);
	}

	public CypherQuery(String query) {
		skip = "";
		limit = "";
		if (query != null)
			this.query = new StringBuilder(query);
	}

	public void setQuery(String query) {
		this.query = new StringBuilder(query);
	}

	public void setOffset(int offset) {
		skip = " SKIP " + offset;
	}

	public void setLimit(int limit) {
		this.limit = " LIMIT " + limit;
	}

	public CypherQuery addParameter(String key, Object value) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(key, value);
		return this;
	}

	public boolean hasParameters() {
		return (params != null) ? true : false;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getQuery() {
		if (query == null) {
			if (startNode == -1L && match == null
					&& (returnPars == null && !aggregate)) {
				return null;
			} else {
				query = new StringBuilder();
				query.append("START n=node(" + startNode + ") ");

				query.append(match.toString());

			}
		}

		StringBuilder sbq = new StringBuilder();
		sbq.append(query.toString());

		if (where != null) {
			sbq.append(where.toString());
		}
		if (aggregate) {
			sbq.append(" RETURN COUNT(");
			sbq.append("*");
			sbq.append(")");
		}else if (returnPars != null) {
			sbq.append(returnPars.toString());
		}
		if(orderBy != null){
			sbq.append(orderBy.toString());
		}
		return sbq.append(skip).append(limit).toString();
	}
	
	public void addOrderBy(String pattern, String direction){
		orderBy = new StringBuilder();
		orderBy.append(" ORDER BY ");
		orderBy.append(pattern);
		orderBy.append(" ");
		orderBy.append(direction);
		orderBy.append(" ");
	}
	
	public void addOrderBy(String node, String property, String direction){
		String temp = node+".`"+property+"`";
		addOrderBy(temp, direction);
	}

	public void addWhere(String node, String property, Object value,
			Operator operator, PropertyType type, boolean returnValue) {

		if (where == null) {
			where = new StringBuilder();
			where.append(" WHERE ");
		}

		StringBuilder sb = new StringBuilder();

		if (!where.toString().equals(" WHERE ")) {
			where.append(" AND ");
		}
		
		String id = node+".`"+property.toString()+"`";

		sb.append(id);
//		sb.append(" AND ");
//		sb.append(id);
		
//		sb.append(node);
//		sb.append(".`");
//		sb.append(property.toString());
//		sb.append("`");

		if (returnValue) {
			addReturnValue(node, property);
		}

		String temp = sb.toString();

		if (property.toString().equals(NamespaceConstants.TYPE)){
			sb.append("?");
		}else{
			sb.insert(0,temp+" AND ");
		}
		
		if (type == PropertyType.Text) {
			switch (operator) {
			case Begins:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("=~ /(?i)");
				sb.append(value);
				sb.append(".*/");
				break;
			case Contains:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("=~ /(?i)^.*");
				sb.append(value);
				sb.append(".*$/");
				break;
			case Ends:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("=~ /(?i)^.*");
				sb.append(value);
				sb.append("$/");
				break;
			case Eq:
				sb.append("=\"");
				sb.append(value);
				sb.append("\"");
				break;
			case NotEq:
				sb.insert(0, "not(");
				sb.append("=\"");
				sb.append(value);
				sb.append("\"");
				sb.append(")");
				sb.append(" AND ");
				sb.append(temp);
				sb.append(" IS NOT NULL ");
				break;
			case NotEmpty:
//				sb.append(value);
				sb.append(" IS NOT NULL");
				break;
			case Empty:
				sb.append(" IS NULL");
				break;
			default:
				log.error("Invalid operator " + operator.toString()
						+ " for type STRING");
			}

		} else if (type == PropertyType.Number || type == PropertyType.Date) {
			switch (operator) {
			case Ge:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append(">=");
				sb.append(value);
				break;
			case Gt:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append(">");
				sb.append(value);
				break;
			case Le:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("<=");
				sb.append(value);
				break;
			case Lt:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("<");
				sb.append(value);
				break;
			case Eq:
				sb.append(" IS NOT NULL AND ");
				sb.append(temp);
				sb.append("=");
				sb.append(value);
				break;
			case NotEq:
				sb.insert(0, "not(");
				sb.append("=");
				sb.append(value);
				sb.append(")");
				sb.append(" AND ");
				sb.append(temp);
				sb.append(" IS NOT NULL ");
				break;
			case NotEmpty:
				sb.append(value);
				sb.append(" IS NOT NULL");
				break;
			case Empty:
				sb.append(" IS NULL");
				break;
			default:
				log.error("Invalid operator " + operator.toString()
						+ " for type LONG");
			}
		} else {
			log.error("Invalid property type " + type);
		}

		where.append(sb.toString());

	}

	public void addMatchPath(boolean optional, String startNode,
			String nodeName, String... paths) {
		if (match == null) {
			match = new StringBuilder();
			match.append(" MATCH ");
		}

		StringBuilder sb = new StringBuilder();

		if (!match.toString().equals(" MATCH ") && paths.length > 0) {
			match.append(", ");
		}
		for (int i = 0; i < paths.length; i++) {
			if (i == 0) {
				sb.append("(" + startNode + ")-");
			} else {
				sb.append("()-");
			}
			sb.append("[");
			if (optional) {
				sb.append("?");
			}
			sb.append(":`" + paths[i] + "`]->");
			if (i == paths.length - 1) {
				sb.append("(" + nodeName + ")");
			}
		}

		match.append(sb.toString());

	}

	public String addReturnValue(String node, String property) {
		StringBuilder sb = new StringBuilder();
		sb.append(node + ".");
		sb.append("`");
		sb.append(property);
		sb.append("`");

		sb.append("?");
//		if (!node.equals("x0")) {
//			sb.insert(0, "collect( ");
//			sb.append(")");
//		}

		addReturnValues(sb.toString());
		return sb.toString();
	}

	public void addReturnValues(String... toReturn) {
		if (returnPars == null) {
			returnPars = new StringBuilder();
			returnPars.append(" RETURN ");
		}

		StringBuilder sb = new StringBuilder();
		if (!returnPars.toString().equals(" RETURN ")) {
			sb.append(", ");
		}

		for (int i = 0; i < toReturn.length; i++) {
			sb.append(toReturn[i]);
			if (i < toReturn.length - 1) {
				sb.append(", ");
			}
		}

		returnPars.append(sb.toString());

	}

	public void setAggregate(boolean aggregate) {
		this.aggregate = aggregate;
		skip = "";
		limit = "";
		orderBy = null;
	}

	public void setStartNode(long startNode) {
		this.startNode = startNode;
	}

	public void clearReturnValues() {
		returnPars = null;
	}

	public void insertReturnValue(String node, String property) {
		String temp = null;
		if(returnPars != null){
			temp = returnPars.toString().substring(8);
			returnPars = null;
		}
		returnPars = new StringBuilder();
		returnPars.append(" RETURN ");
		returnPars.append(node);
		returnPars.append(".");
		returnPars.append("`"+property+"`");
		
		if(temp != null){
			returnPars.append(",");
			returnPars.append(temp);
		}
		
	}

}
