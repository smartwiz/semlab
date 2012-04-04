package com.semlab.client.app;

import java.util.logging.Logger;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.semlab.client.app.events.LoginEvent;
import com.semlab.client.fwk.DefaultActivityManager;
import com.semlab.client.fwk.Event;
import com.semlab.client.fwk.Place;
import com.semlab.client.fwk.async.AbstractAsyncCallback;
import com.semlab.client.fwk.async.AsyncActivityEvent;
import com.semlab.client.fwk.async.AsyncActivityHandler;
import com.semlab.client.fwk.async.AsyncActivityType;
import com.semlab.client.fwk.widgets.WaitPanel;
import com.semlab.client.mvp.home.HomeMVP;
import com.semlab.shared.config.Configuration;
import com.semlab.shared.services.SemlabServiceAsync;

public class Application {
	
	private static final Logger log = Logger.getLogger(Application.class.getName());
	
	public static final String WORKSPACE_CONTAINER_ID = "w";

	private Configuration config;
	private final EventBus eventBus;
	private final PlaceController placeController;
	private final PlaceHistoryHandler placeHistoryHandler;
	private final DefaultActivityManager activityManager;
	private final SemlabServiceAsync service;
	private final ApplicationShell shell;
	private static String token;

	private Place defaultPlace = HomeMVP.place();
	
	@Inject
	public Application(EventBus eventBus, PlaceController placeController, ApplicationShell shell, 
			DefaultActivityManager activityManager, PlaceHistoryHandler placeHistoryHandler, SemlabServiceAsync service) {
		this.eventBus = eventBus;
		this.placeController = placeController;
		this.activityManager = activityManager;
		this.placeHistoryHandler = placeHistoryHandler;
		this.service = service;
		this.shell = shell;
	}
	
	public void start(){
		log.fine("starting application result");

		init();
		placeHistoryHandler.handleCurrentHistory();

		/* Hide the loading... */
		Element loading = Document.get().getElementById("loader");
		loading.getParentElement().removeChild(loading);

		/* And show the user the shell */
		RootLayoutPanel.get().add(shell.getDisplay());
	}
	
	private void init() {

		activityManager.registerContainer(WORKSPACE_CONTAINER_ID, shell.getWorkspaceContainer());

		// GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
		// public void onUncaughtException(Throwable e) {
		// Window.alert("Error global handler: " + e.getMessage());
		// log.severe(e.getMessage());
		// }
		// });

		eventBus.addHandler(AsyncActivityEvent.getType(), new AsyncActivityHandler() {
			@Override
			public void onRpcEvent(AsyncActivityEvent event) {
				if (event.getActivityType() == AsyncActivityType.START) {
					log.fine("AsyncActivityType.START");
					WaitPanel.wait(true);
					// shell.getMole().show();
				}
				if (event.getActivityType() == AsyncActivityType.END) {
					log.fine("AsyncActivityType.END");
					WaitPanel.wait(false);
					// shell.getMole().hide();
				}
			}
		});

		eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				if (event.getNewPlace() instanceof Place) {
					Place thePlace = (Place) event.getNewPlace();
					if (thePlace.getContainerId().equals(WORKSPACE_CONTAINER_ID)) {
//						String commandID = thePlace.getName() + PlaceCommand.MENUITEM_SUFIX;
//						log.fine("select command = [" + commandID + "]");
					}
				}
			}
		});

		eventBus.addHandler(LoginEvent.TYPE, new Event.Handler<LoginEvent>() {

			@Override
			public void on(LoginEvent e) {
				if(e.getClient().getId() != null){
					log.fine(":: saving token in app:: token="+e.getClient().getToken());
					token = e.getClient().getToken();
				}else{
					log.fine("## login unsuccessful");
				}
			}
		});
		
		activityManager.process();
		placeHistoryHandler.register(placeController, eventBus, defaultPlace);
	}
	
	public void getConfig(final AbstractAsyncCallback<Configuration> callback){
		if(config == null) {
			service.getConfig(new AbstractAsyncCallback<Configuration>() {
				@Override
				public void success(Configuration result) {
					config = result;
					callback.onSuccess(config);
				}
			});
		} else {
			callback.onSuccess(config);
		}
	}
	
	public static String getActiveToken(){
		return token;
	}

}
