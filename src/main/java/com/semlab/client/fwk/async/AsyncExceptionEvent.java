package com.semlab.client.fwk.async;

import com.google.gwt.event.shared.GwtEvent;

public class AsyncExceptionEvent extends GwtEvent<AsyncExceptionHandler> {

    private static Type<AsyncExceptionHandler> type;

    public static Type<AsyncExceptionHandler> getType() {
        return type != null ? type : (type = new Type<AsyncExceptionHandler>());
    }

    private final Throwable caught;

    public AsyncExceptionEvent(Throwable caught) {
        this.caught = caught;
    }

    @Override
    protected void dispatch(AsyncExceptionHandler handler) {
        handler.onRunAsyncException(this);
    }

    @Override
    public Type<AsyncExceptionHandler> getAssociatedType() {
        return getType();
    }

    public Throwable getCaught() {
        return caught;
    }
    
    @Override
    public String toDebugString() {
        return super.toDebugString() + " " + caught.toString();
    }
}
