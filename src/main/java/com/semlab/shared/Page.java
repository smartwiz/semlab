package com.semlab.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Page<E extends Serializable> implements Serializable {
	
    private int totalLength = -1;
    private ArrayList<E> items = new ArrayList<E>();
    
    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public ArrayList<E> getItems() {
        return items;
    }

    public void addItem(E item) {
        items.add(item);
    }

    public void addAllItems(ArrayList<E> items) {
        this.items.addAll(items);
    }

    @Override
    public String toString() {
        return "Page{" + "totalLength=" + totalLength + " items=[" + items + "]}";
    }

	public void removeItem(Item item) {
		items.remove(item);
	}

}
