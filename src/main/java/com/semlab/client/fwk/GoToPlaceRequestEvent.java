package com.semlab.client.fwk;

import com.google.gwt.place.shared.Place;

public class GoToPlaceRequestEvent extends Event {
	
	@SuppressWarnings("rawtypes")
	public static final Type<Handler> TYPE = new Type<Handler>();
	
	private Place placeToGo;

	public GoToPlaceRequestEvent(Place placeToGo) {
		super(TYPE);
		this.placeToGo = placeToGo;
	}

	public Place getPlaceToGo() {
		return placeToGo;
	}

}
