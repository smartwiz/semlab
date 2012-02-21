package com.semlab.client.app.gin;

import com.google.inject.Singleton;
import com.semlab.client.app.Application;
import com.semlab.client.app.ApplicationShell;
import com.semlab.client.app.ApplicationShellImpl;
import com.semlab.client.fwk.AbstractClientModule;
import com.semlab.client.mvp.home.HomeMVP;

public class ClientModule extends AbstractClientModule {

	@Override
	protected void configure() {
		bind(Application.class).in(Singleton.class);
		bind(ApplicationShell.class).to(ApplicationShellImpl.class).in(Singleton.class);
		bind(HomeMVP.class).asEagerSingleton();
	}

}
