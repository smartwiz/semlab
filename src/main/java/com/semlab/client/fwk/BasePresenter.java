package com.semlab.client.fwk;

import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.semlab.client.fwk.BaseMVP.Presenter;
import com.semlab.client.fwk.BaseMVP.View;

public abstract class BasePresenter<D extends View<?>> extends AbstractActivity implements Presenter<D> {
	
	private static boolean doRefreshView = true;
	
	public static boolean isDoRefreshView() {
		return doRefreshView;
	}

	public static void setDoRefreshView(boolean refreshView) {
		doRefreshView = refreshView;
	}

	protected Logger log = Logger.getLogger(getClass().getName());

	protected EventBus eventBus;

	protected Place place;
	protected D display;

	protected AcceptsOneWidget container;
	
	public BasePresenter(Place place, D display) {
		log.fine("Creating " + getClass());
		this.display = display;
		this.place = place;
	}

	@Override
	public final void start(AcceptsOneWidget container, EventBus eventBus) {
		this.container = container;
		this.eventBus = eventBus;
		onStart();
	}

	protected void goTo(com.google.gwt.place.shared.Place newPlace) {
		fireEvent(new GoToPlaceRequestEvent(newPlace));
	}
	
	protected void fireEvent(GwtEvent<?> event) {
		if(eventBus != null) {
			log.fine("firing event"+ event);
			eventBus.fireEvent(event);
		} else {
			log.warning("activity is no active so can't fire events");
		}
	}
	
	protected <H extends EventHandler> HandlerRegistration handleEvent(Type<H> type, H handler) {
		if(eventBus != null) {
			log.fine("adding event handler");
			return eventBus.addHandler(type, handler);
		} else {
			log.warning("activity is no active so can't handle events");
			return null;
		}
	}
	
	@Override
	public String mayStop() {
		setDoRefreshView(true);
		return super.mayStop();
	}

	protected abstract void onStart();

}
