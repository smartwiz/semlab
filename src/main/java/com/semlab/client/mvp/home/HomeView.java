package com.semlab.client.mvp.home;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.semlab.client.app.Application;
import com.semlab.client.app.gin.Injector;
import com.semlab.client.app.widgets.SocialClient;
import com.semlab.client.fwk.BaseView;
import com.semlab.client.fwk.async.AbstractAsyncCallback;
import com.semlab.client.fwk.widgets.Button;

public class HomeView extends BaseView<HomeMVP.Presenter> implements HomeMVP.View, RequiresResize  {
	
	private static final Logger log = Logger.getLogger(HomeView.class.getName());

	private static NavigatordViewUiBinder uiBinder = GWT.create(NavigatordViewUiBinder.class);

	interface NavigatordViewUiBinder extends UiBinder<Widget, HomeView> {
	}
	
	private SocialClient activeClient;
	
	@UiField
	DockLayoutPanel container;
	
	@UiField
	Button startImport;
	
	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setClientInfo(SocialClient client) {
		Window.alert("You are logged in as "+client.getName());
		this.activeClient = client;
	}
	
	@UiHandler("startImport")
	void onStartImport(ClickEvent e) {
		if(checkLoggedIn()){
			Injector.INSTANCE.getService().startImport(activeClient.getToken(), new AbstractAsyncCallback<Void>() {
				@Override
				public void success(Void result) {
					GWT.log(":: import started");
				}
			});
		}
	}
	
	@UiHandler("startEnrichment")
	void onStartEnrichment(ClickEvent e) {
		if(checkLoggedIn()){
			Injector.INSTANCE.getService().startEnrich(activeClient.getToken(), new AbstractAsyncCallback<Void>() {
				@Override
				public void success(Void result) {
					GWT.log(":: enrichment started ");
				}
			});
		}
	}
	
	private boolean checkLoggedIn(){
		if(activeClient != null){
			return true;
		}else{
			Window.alert("you must login first");
			return false;
		}
	}

	@Override
	public void onResize() {
		container.onResize();
	}
}
