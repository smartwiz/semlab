package com.semlab.client.app.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.semlab.client.fwk.ActivityRegistry;
import com.semlab.client.fwk.AppPlaceHistoryMapper;
import com.semlab.client.fwk.DefaultActivityManager;
import com.semlab.client.fwk.DefaultPlaceController;

public class SystemModule extends AbstractGinModule {
	
	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceController.class).toProvider(PlaceControllerProvider.class).in(Singleton.class);
		bind(PlaceHistoryHandler.class).toProvider(PlaceHistoryHandlerProvider.class).in(Singleton.class);
		bind(DefaultActivityManager.class).in(Singleton.class);
		bind(ActivityRegistry.class).to(DefaultActivityManager.class).asEagerSingleton();
		bind(PlaceHistoryMapper.class).to(AppPlaceHistoryMapper.class).in(Singleton.class);
	}

	public static class PlaceControllerProvider implements Provider<PlaceController> {

		private final EventBus eventBus;

		@Inject
		public PlaceControllerProvider(EventBus eventBus) {
			this.eventBus = eventBus;
		}

		public PlaceController get() {
			return new DefaultPlaceController(eventBus);
		}
	}
	
	public static class PlaceHistoryHandlerProvider implements Provider<PlaceHistoryHandler> {
		
		private PlaceHistoryMapper placeHistoryMapper;

		@Inject
		public PlaceHistoryHandlerProvider(PlaceHistoryMapper placeHistoryMapper) {
			this.placeHistoryMapper = placeHistoryMapper;
		}
		
		public PlaceHistoryHandler get() {
			return new PlaceHistoryHandler(placeHistoryMapper);
		}
	}

}
