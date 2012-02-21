package com.semlab.shared;

public enum Source {
	
	Facebook("Facebook"),
	LinkedIn("LinkedIn"),
	Excel("Excel");
	
	private String sourceLabel;
	
	private Source(String sourceLabel){
		this.sourceLabel = sourceLabel;
	}
	
	public static Source getSourceForSourceLabel(String sourceLabel) {
		for (Source source : Source.values()) {
			if(source.getSourceLabel().equals(sourceLabel)) return source;
		}
		return null;
	}

	public String getSourceLabel() {
		return sourceLabel;
	}

}
