package com.semlab.client.fwk.async;

import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.semlab.client.app.gin.Injector;

public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {

	private static Logger log = Logger.getLogger(AbstractAsyncCallback.class.getName());
	
	private static void fireEvent(AsyncActivityEvent event) {
		Injector.INSTANCE.getEventBus().fireEvent(event);
	}
	
	public AbstractAsyncCallback() {
		fireEvent(AsyncActivityEvent.START);
	}

	@Override
	public final void onFailure(Throwable caught) {
		fireEvent(AsyncActivityEvent.END);
		failure(caught);
	}

	public final void onSuccess(T result) {
		fireEvent(AsyncActivityEvent.END);
		success(result);
	}
	
	public void failure(Throwable caught) {
		log.severe("server error " + caught);
	};

	public abstract void success(T result);

}
