package com.semlab.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.semlab.shared.resources.NamespaceConstants;

@SuppressWarnings("serial")
public class Path implements Serializable {
	
	private String id;
	private ArrayList<Field> path = new ArrayList<Field>();
	
	public Path() {
	}

	public Path(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Field> getPath() {
		return path;
	}

	public void setPath(ArrayList<Field> path) {
		this.path = path;
	}
	
	public Path addToPath(Field field) {
		path.add(field);
		return this;
	}
	
	public int getLength(){
		return path.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else {
			if(!path.equals(other.path))
				return false;
		}
		return true;
	}
	

	@Override
	public String toString() {
//		return "Path [id=" + id + ", path=" + path + "]";
		return path.toString();
	}
	
	public String asLabelString() {
		if(path == null || path.size() == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.size(); i++) {
			Field field = path.get(i);
			if(field.getClassLabel() != null && !field.getClassLabel().isEmpty() && !NamespaceConstants.FB_PROFILE.equals(field.getClassUri())) {
				sb.append(field.getClassLabel());
				sb.append(".");
			}
			if(field.getLabel() != null && !field.getLabel().isEmpty() && !NamespaceConstants.LIKES.equals(field.getUri())) {
				sb.append(field.getLabel());
			}
			if(i < path.size()-1) {
				sb.append(".");
			}
		}
		String label = sb.toString();
		String result = label.replaceAll("^\\.", "");
		return result;
	}
	
	public boolean isRoot() {
		return path.size() == 0;
	}
	
	public Field getLastField() {
		if(path == null) return null;
		return path.get(path.size()-1);
	}

}
