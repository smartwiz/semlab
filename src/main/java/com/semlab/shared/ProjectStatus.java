package com.semlab.shared;

public enum ProjectStatus {
	
	NEW("NEW"), IMPORT_IN_PROGRESS("IMPORT IN PROGRESS"), IMPORT_ERROR("IMPORT ERROR"), IMPORT_COMPLETED("IMPORT COMPLETED"), ENRICHMENT_IN_PROGRESS("ENRICHMENT IN PROGRESS"), ENRICHMENT_STOPPED("ENRICHMENT STOPPED"), ENRICHMENT_ERROR("ENRICHMENT ERROR"), ENRICHMENT_COMPLETED("ENRICHMENT COMPLETED");

	private String projectStatusLabel;
	
	private ProjectStatus(String projectStatusLabel){
		this.projectStatusLabel = projectStatusLabel;
	}
	
	public static ProjectStatus getProjectStatusFromLabel(String projectStatusLabel){
		for (ProjectStatus projectStatus : ProjectStatus.values()) {
			if(projectStatus.getProjectStatusLabel().equals(projectStatusLabel)) return projectStatus;
		}
		return null;
	}
	
	@Override
	public String toString() {
		String value = super.toString();
		value = value.replace('_', ' ');
		return value;
	}

	public String getProjectStatusLabel() {
		return projectStatusLabel;
	}

}
