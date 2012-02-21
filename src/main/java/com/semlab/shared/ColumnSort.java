package com.semlab.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ColumnSort implements Serializable {
	
	private String column;
	private boolean ascending;
	
	public ColumnSort() {
	}

	public ColumnSort(String column, boolean ascending) {
		this.column = column;
		this.ascending = ascending;
	}

	public String getColumn() {
		return column;
	}

	public boolean isAscending() {
		return ascending;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ascending ? 1231 : 1237);
		result = prime * result + ((column == null) ? 0 : column.hashCode());
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
		ColumnSort other = (ColumnSort) obj;
		if (ascending != other.ascending)
			return false;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColumnSort [column=" + column + ", ascending=" + ascending + "]";
	}

	public String getDirection() {
		return (ascending) ? "ASC":"DESC";
	}

}
