package com.semlab.shared;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Report extends DomainObject {
	
	private long id;
	private Project project;
	private String name;
	private String source;
	private Date created;
	private ArrayList<Path> paths;
	private ArrayList<Criteria> criteria;
	private String pathsAsString;
	private String criteriaAsString;
	
	public Report() {
		project = new Project();
	}
	
	public Report(Project project) {
		this.project = project;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public long getProjectId() {
		return project.getId();
	}

	public void setProjectId(long projectId) {
		this.project.setId(projectId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public void addPath(Path path) {
		if(paths == null) {
			paths = new ArrayList<Path>();
		}
		paths.add(path);
	}

	public ArrayList<Path> getPaths() {
		return paths;
	}
	
	public void setPaths(ArrayList<Path> paths) {
		this.paths = paths;
	}
	
	public void addCriteria(Criteria criteria) {
		if(this.criteria == null) {
			this.criteria = new ArrayList<Criteria>();
		}
		this.criteria.add(criteria);
	}

	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}
	
	public void setCriteria(ArrayList<Criteria> criteria) {
		this.criteria = criteria;
	}
	
	public String getPathsAsString() {
		return pathsAsString;
	}

	public void setPathsAsString(String pathsAsString) {
		this.pathsAsString = pathsAsString;
	}
	
	public void setPathsAsString(ArrayList<Path> paths) {
		if(paths != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < paths.size(); i++) {
				Path path = paths.get(i);
				sb.append(path.asLabelString());
				if(i < paths.size()-1) {
					sb.append(";");
				}
			}
			setPathsAsString(sb.toString());
		}
	}

	public String getCriteriaAsString() {
		return criteriaAsString;
	}

	public void setCriteriaAsString(String criteriaAsString) {
		this.criteriaAsString = criteriaAsString;
	}
		
	public void clearCriteria() {
		if(criteria != null) {
			criteria.clear();
		}
	}
	
	public void clearPath() {
		if(paths != null) {
			paths.clear();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", project=" + project.getId() + ", name=" + name + ", source=" + source + ", created="
				+ created + ", paths=" + paths + ", criteria=" + criteria + ", pathsAsString=" + pathsAsString
				+ ", criteriaAsString=" + criteriaAsString + "]";
	}
	
	public Criteria getCriteriaForPath(Path path){
		if(this.criteria != null)
			for (Criteria criteria : this.criteria) {
				if(criteria.getPath().equals(path))
					return criteria;
			}
		return null;
	}


}
