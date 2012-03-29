package com.semlab.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.semlab.client.app.gin.Injector;

public class Semlab implements EntryPoint{

	@Override
	public void onModuleLoad() {
		Injector.INSTANCE.getApplication().start();
	}
	
}
