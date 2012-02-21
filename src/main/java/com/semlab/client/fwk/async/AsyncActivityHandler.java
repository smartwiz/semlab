package com.semlab.client.fwk.async;

import com.google.gwt.event.shared.EventHandler;

public interface AsyncActivityHandler extends EventHandler {

    void onRpcEvent(AsyncActivityEvent event);
    
}
