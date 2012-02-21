package com.semlab.client.fwk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class DefaultActivityManager implements ActivityRegistry {

	private static final Logger log = Logger.getLogger(DefaultActivityManager.class.getName());

	private HashMap<String, List<MappingsEntry>> activityMappings = new HashMap<String, List<MappingsEntry>>();
	private HashMap<String, AcceptsOneWidget> displayMappings = new HashMap<String, AcceptsOneWidget>();

	private EventBus eventBus;

	@Inject
	public DefaultActivityManager(EventBus eventBus) {
		log.fine("creating...");
		this.eventBus = eventBus;
	}

	public static class MappingsEntry {

		private Place place;
		private ActivityMapper activityMapper;

		public MappingsEntry(Place place, ActivityMapper activityMapper) {
			this.place = place;
			this.activityMapper = activityMapper;
		}

		public Place getPlace() {
			return place;
		}

		public ActivityMapper getActivityMapper() {
			return activityMapper;
		}

	}

	@Override
	public void registerContainer(String displayId, AcceptsOneWidget display) {
		if (displayId == null || display == null) {
			throw  new IllegalArgumentException("Container and container id must not be null");
		}
		String theContainerId = displayId.trim();
		if(!Utils.hasLength(theContainerId)) {
			throw  new IllegalArgumentException("Container id must not be empty");
		}
		if (displayMappings.containsKey(displayId)) {
			throw  new IllegalArgumentException("Container with id [ "+ theContainerId + "] already exist");
		} else {
			displayMappings.put(displayId, display);
		}
	}

	public void process() {
		log.fine("proces Mappings " + displayMappings );
		for (Entry<String, AcceptsOneWidget> displayMappingEntry : displayMappings.entrySet()) {
			log.fine("processing...");
			List<MappingsEntry> activities = activityMappings.get(displayMappingEntry.getKey());
			if (activities == null || activities.size() == 0) {
				log.fine("there no activities for display [" + displayMappingEntry.getKey() + "]");
				continue;
			}
			log.fine("creating activity manager for display [" + displayMappingEntry.getKey() + "] with ["
					+ activities.size() + "] activities");
			CompositeActivityMapper activityMapper = new CompositeActivityMapper(activities);
//			CachingActivityMapper cached = new CachingActivityMapper(activityMapper);
			final ActivityManager masterActivityManager = new ActivityManager(activityMapper, eventBus);
			masterActivityManager.setDisplay(displayMappingEntry.getValue());
		}
	}

	@Override
	public void registerActivity(Place place, ActivityMapper activityMapper) {
		log.fine("register ActivityMapper for" + place );
		if (activityMapper == null)
			return;
		if (!activityMappings.containsKey(place.getContainerId())) {
			activityMappings.put(place.getContainerId(), new ArrayList<MappingsEntry>());
		}
		activityMappings.get(place.getContainerId()).add(new MappingsEntry(place, activityMapper));
	}

}
