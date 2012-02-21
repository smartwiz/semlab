package com.semlab.client.fwk.async;

import com.google.gwt.event.shared.GwtEvent;

public class AsyncActivityEvent extends GwtEvent<AsyncActivityHandler> {

    private static Type<AsyncActivityHandler> type;

    public static final AsyncActivityEvent START = new AsyncActivityEvent(AsyncActivityType.START);
    public static final AsyncActivityEvent END = new AsyncActivityEvent(AsyncActivityType.END);

    public static Type<AsyncActivityHandler> getType() {
        return type != null ? type : (type = new Type<AsyncActivityHandler>());
    }

    private final AsyncActivityType activityType;

    private AsyncActivityEvent(AsyncActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    protected void dispatch(AsyncActivityHandler handler) {
        handler.onRpcEvent(this);
    }

    public AsyncActivityType getActivityType() {
        return activityType;
    }

    @Override
    public Type<AsyncActivityHandler> getAssociatedType() {
        return getType();
    }

    @Override
    public String toDebugString() {
        return super.toDebugString() + activityType.toString();
    }
}
