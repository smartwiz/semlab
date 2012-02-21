package com.semlab.client.fwk.async;

import com.google.gwt.event.shared.EventHandler;

public interface AsyncExceptionHandler extends EventHandler {

    void onRunAsyncException(AsyncExceptionEvent event);
}
