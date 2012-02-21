package com.semlab.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.semlab.shared.config.Thing;

@SuppressWarnings("serial")
public class PathTreeItem implements Serializable {
	
	private String label;
	private Path path;
	private boolean hasChildren;
	private ArrayList<Thing> things;
	
	public PathTreeItem(String label, Thing thing, Path path, boolean hasChildren) {
		this.label = label;
		this.path = path;
		this.hasChildren = hasChildren;
		this.things = new ArrayList<Thing>();
		this.things.add(thing);
	}
	
	public String getLabel() {
		return label;
	}
	
	public Path getPath() {
		return path;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public Thing getThing() {
		if(things.size() > 0) {
			return things.get(0);
		} else {
			return null;
		}
	}
	
	public ArrayList<Thing> getAllThings() {
		return things;
	}
	
	public void addThing(Thing thing) {
		things.add(thing);
	} 

	@Override
	public String toString() {
		return "PathTreeItem [label=" + label + ", path=" + path + ", hasChildren=" + hasChildren + ", things=" + things
				+ "]";
	}

}
