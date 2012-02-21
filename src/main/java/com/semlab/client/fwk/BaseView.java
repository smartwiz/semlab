package com.semlab.client.fwk;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.semlab.client.fwk.BaseMVP.Presenter;

public abstract class BaseView<P extends Presenter<?>> extends Composite implements BaseMVP.View<P> {
	
	protected Logger log = Logger.getLogger(getClass().getName());
	
	protected P presenter; 

	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}

}
