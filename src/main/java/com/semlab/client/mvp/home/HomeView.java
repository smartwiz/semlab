package com.semlab.client.mvp.home;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.semlab.client.app.widgets.SocialClient;
import com.semlab.client.fwk.BaseView;

public class HomeView extends BaseView<HomeMVP.Presenter> implements HomeMVP.View  {
	
	private static final Logger log = Logger.getLogger(HomeView.class.getName());

	private static NavigatordViewUiBinder uiBinder = GWT.create(NavigatordViewUiBinder.class);

	interface NavigatordViewUiBinder extends UiBinder<Widget, HomeView> {
	}
	
	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setClientInfo(SocialClient client) {
		Window.alert("You are logged in as "+client.getName());
	}

}
