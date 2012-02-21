package com.semlab.client.mvp.home;

import com.google.inject.Inject;
import com.semlab.client.app.Application;
import com.semlab.client.app.widgets.SocialClient;
import com.semlab.client.fwk.ActivityRegistry;
import com.semlab.client.fwk.BaseMVP;
import com.semlab.client.fwk.Place;
import com.semlab.shared.services.SemlabServiceAsync;

public class HomeMVP extends BaseMVP {
	
	public static final String ACTIVITY_ID = "home";
	
	public static Place place() {
		return new Place(Application.WORKSPACE_CONTAINER_ID, ACTIVITY_ID);
	}
	
	public static interface View extends BaseMVP.View<Presenter> {
		void setClientInfo(SocialClient client);
	}
	
	public static interface Presenter extends BaseMVP.Presenter<View> {

	}
	
	private HomeView view;

	@Inject
	public HomeMVP(ActivityRegistry registry, final SemlabServiceAsync service) {
		super(registry);
		mapPlaces(place()).to(new Factory<Presenter>() {
			@Override
			public Presenter create(Place place) {
				if(view == null) {
					view = new HomeView();
				}
				return new HomePresenter(place, view, service); 
			}
		});
	}
}
