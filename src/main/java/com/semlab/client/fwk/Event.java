package com.semlab.client.fwk;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

@SuppressWarnings("rawtypes")
public abstract class Event extends GwtEvent<Event.Handler> {
	
	public static interface Handler<E> extends EventHandler {
		void on(E e);
	}
	
	private GwtEvent.Type<Handler> type;

	protected Event(GwtEvent.Type<Handler> type) {
		this.type = type;
	}

	@Override
	public GwtEvent.Type<Handler> getAssociatedType() {
		return type;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void dispatch(Handler handler) {
		handler.on(this);
	}
	
}
