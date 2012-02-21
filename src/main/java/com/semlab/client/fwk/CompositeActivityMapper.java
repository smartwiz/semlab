package com.semlab.client.fwk;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.semlab.client.fwk.DefaultActivityManager.MappingsEntry;

public class CompositeActivityMapper implements ActivityMapper {
	
	private static final Logger log = Logger.getLogger(CompositeActivityMapper.class.getName());
	
	private List<MappingsEntry> mappings;
	
	@Inject
	public CompositeActivityMapper(List<MappingsEntry> mappers) {
		this.mappings = mappers;
	}

	@Override
	public Activity getActivity(Place place) {
		for (MappingsEntry mappingsEntry : mappings) {
			if(mappingsEntry.getPlace().equals(place)) {
				Activity activity = mappingsEntry.getActivityMapper().getActivity(place);
				if(activity != null) {
					log.fine("founded activity = " + activity.getClass());
					return activity;
				} 
			}
		}
		return null;
	}

}
