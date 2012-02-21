package com.semlab.client.fwk;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface ActivityRegistry {
	
	void registerActivity(Place place, ActivityMapper activityMapper);
	
	void registerContainer(String displayId, AcceptsOneWidget display);

}
