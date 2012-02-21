package com.semlab.client.mvp.home;

import java.util.logging.Logger;

import com.semlab.client.app.events.LoginEvent;
import com.semlab.client.fwk.BasePresenter;
import com.semlab.client.fwk.Event;
import com.semlab.client.fwk.Place;
import com.semlab.shared.services.SemlabServiceAsync;

public class HomePresenter extends BasePresenter<HomeMVP.View> implements HomeMVP.Presenter {

	private static final Logger log = Logger.getLogger(HomePresenter.class.getName());

	private SemlabServiceAsync service;

	public HomePresenter(Place place, HomeMVP.View display, SemlabServiceAsync service) {
		super(place, display);
		this.service = service;
	}

	@Override
	protected void onStart() {
		log.fine("MyProjectsPresenter onStart");
		display.setPresenter(this);
		container.setWidget(display);
		
		eventBus.addHandler(LoginEvent.TYPE, new Event.Handler<LoginEvent>() {
			@Override
			public void on(LoginEvent e) {
				log.fine("logged in [name=" + e.getClient().getName() + ", token=" + e.getClient().getToken() + ", id=" + e.getClient().getId() + "]");
				display.setClientInfo(e.getClient());
			}
		});
		
	}


}
