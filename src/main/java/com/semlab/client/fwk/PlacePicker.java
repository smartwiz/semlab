package com.semlab.client.fwk;

import com.google.gwt.activity.shared.FilteredActivityMapper;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.HasConstrainedValue;

public class PlacePicker implements ValueChangeHandler<Place>, PlaceChangeEvent.Handler {

	public static class ProxyPlaceToListPlace implements FilteredActivityMapper.Filter {

		/**
		 * Required by {@link FilteredActivityMapper.Filter}, calls
		 * {@link #proxyListPlaceFor()}.
		 */
		public Place filter(com.google.gwt.place.shared.Place place) {
			return proxyListPlaceFor(place);
		}

		/**
		 * @param place
		 *            a place to process
		 * @return an appropriate ProxyListPlace, or null if the given place has
		 *         nothing to do with proxies
		 */
		public Place proxyListPlaceFor(com.google.gwt.place.shared.Place place) {
			if (place instanceof Place) {
				return (Place) place;
			} return null;
		}
	}

	private HasConstrainedValue<Place> view;

	private final PlaceController placeController;

	private final ProxyPlaceToListPlace proxyPlaceToListPlace;

	public PlacePicker(PlaceController placeController, ProxyPlaceToListPlace proxyPlaceToListPlace) {
		this.placeController = placeController;
		this.proxyPlaceToListPlace = proxyPlaceToListPlace;
	}

	public void onPlaceChange(PlaceChangeEvent event) {
		view.setValue(proxyPlaceToListPlace.proxyListPlaceFor(event.getNewPlace()), false);
	}

	public void onValueChange(ValueChangeEvent<Place> event) {
		
		placeController.goTo(event.getValue());
	}

	public HandlerRegistration register(EventBus eventBus, HasConstrainedValue<Place> view) {

		this.view = view;

		final HandlerRegistration placeRegistration = eventBus.addHandler(PlaceChangeEvent.TYPE, this);

		final HandlerRegistration viewRegistration = view.addValueChangeHandler(this);

		return new HandlerRegistration() {
			public void removeHandler() {
				placeRegistration.removeHandler();
				viewRegistration.removeHandler();
				PlacePicker.this.view = null;
			}
		};
	}
}