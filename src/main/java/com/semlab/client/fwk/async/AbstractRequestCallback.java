package com.semlab.client.fwk.async;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.semlab.client.app.gin.Injector;

public abstract class AbstractRequestCallback implements RequestCallback {

    private static final EventBus eventBus = Injector.INSTANCE.getEventBus();

    public AbstractRequestCallback() {
        eventBus.fireEvent(AsyncActivityEvent.START);
    }

    public void failure(Request request, Throwable caught) {
    }

    @Override
    public void onError(Request request, Throwable exception) {
        eventBus.fireEvent(AsyncActivityEvent.END);
        eventBus.fireEvent(new AsyncExceptionEvent(exception));
        failure(request, exception);
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        eventBus.fireEvent(AsyncActivityEvent.END);
        success(request, response);
    }

    public abstract void success(Request request, Response response);
    
}
