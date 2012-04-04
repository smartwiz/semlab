package com.semlab.client.app.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.semlab.client.app.Application;
import com.semlab.shared.services.SemlabServiceAsync;

@GinModules({SystemModule.class, ClientModule.class})
public interface Injector extends Ginjector {
	
	static final Injector INSTANCE = GWT.create(Injector.class);
	
	Application getApplication();
	
	EventBus getEventBus();
	
	SemlabServiceAsync getService();
	
}

