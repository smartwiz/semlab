package com.semlab.client.fwk;

import com.google.gwt.text.shared.AbstractRenderer;

public class PlaceRenderer extends AbstractRenderer<Place> {

	@Override
	public String render(Place place) {
		return place.getTitle();
	}

}
