package com.semlab.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Item implements Serializable {
	
	private String id;
	private ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
	
	public Item() {
	}
	
	public Item(String id) {
		this.id = id;
	}
	
    
    public boolean hasValues(){
    	return (values.size() > 0) ? true : false;
    }

	public ArrayList<ArrayList<String>> getValues() {
		return values;
	}
	
	public void addValue(ArrayList<String> list){
		values.add(list);
	}
	
	public void addValue(String list){
		ArrayList<String> alist = new ArrayList<String>();
		if(list != null && !list.isEmpty())
		alist.add(list);
		values.add(alist);
	}
	
	public void setValues(ArrayList<ArrayList<String>> values) {
		this.values = values;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", values=" + values + "]";
	}


}
