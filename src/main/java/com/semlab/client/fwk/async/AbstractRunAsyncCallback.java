package com.semlab.client.fwk.async;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.semlab.client.app.gin.Injector;

public abstract class AbstractRunAsyncCallback implements RunAsyncCallback {

    private static final EventBus eventBus = Injector.INSTANCE.getEventBus();

    public AbstractRunAsyncCallback() {
        eventBus.fireEvent(AsyncActivityEvent.START);
    }

    public void failure(Throwable caught) {
    }

    @Override
    public final void onFailure(Throwable caught) {
        eventBus.fireEvent(AsyncActivityEvent.END);
        eventBus.fireEvent(new AsyncExceptionEvent(caught));
        failure(caught);
    }

    @Override
    public final void onSuccess() {
        eventBus.fireEvent(AsyncActivityEvent.END);
        success();
    }

    public abstract void success();
}
