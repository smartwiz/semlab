package com.semlab.client.fwk;

import java.util.logging.Logger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class BaseMVP {
	
	protected Logger log = Logger.getLogger(getClass().getName());
	
	public static interface Presenter<D extends View<?>> extends Activity {
	}
	
	public static interface View<P extends Presenter<?>> extends IsWidget {
		void setPresenter(P presenter);
	}

	public static interface Factory<P extends Presenter<?>> {
		P create(Place place);
	}

	private ActivityRegistry registry;
	private Place[] places;
	private ActivityMapper mapper;
	private Factory<?> factory;

	public BaseMVP(ActivityRegistry registry) {
		this.registry = registry;
		mapper = new ActivityMapper() {
			@Override
			public Activity getActivity(com.google.gwt.place.shared.Place place) {
				if (place instanceof Place) {
					return factory.create((Place) place);
				} else {
					throw new IllegalArgumentException("Bad place type");
				}
			}
		};
	}
	
	protected void registerContainer(String id, AcceptsOneWidget container) {
		registry.registerContainer(id, container);
	}

	public BaseMVP mapPlaces(Place... places) {
		this.places = places;
		return this;
	}

	public void to(Factory<?> factory) {
		this.factory = factory;
		for (Place place : places) {
			registry.registerActivity(place, mapper);
		}
	}

}
