package com.semlab.client.fwk;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class DefaultPlaceController extends PlaceController {

	@Inject
	public DefaultPlaceController(EventBus eventBus) {
		super(eventBus);
		eventBus.addHandler(GoToPlaceRequestEvent.TYPE, new GoToPlaceRequestEvent.Handler<GoToPlaceRequestEvent>() {
			@Override
			public void on(GoToPlaceRequestEvent e) {
				goTo(e.getPlaceToGo());
			}});
	}

	@Override
	public Place getWhere() {
		return Place.NOWHERE;
	}
	
}
