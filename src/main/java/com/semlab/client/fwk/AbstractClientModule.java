package com.semlab.client.fwk;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Singleton;

public abstract class AbstractClientModule extends AbstractGinModule {

	/**
	 * Convenience method for binding a presenter as well as it's display.
	 * 
	 * @param <D>
	 *            The display type.
	 * @param mvp
	 *            The MVP.
	 * @param display
	 *            The display type.
	 * @param displayImpl
	 *            The display implementation.
	 */
	protected <D extends IsWidget> void bindMVP(Class<? extends BaseMVP> mvp, Class<D> display,
			Class<? extends D> displayImpl) {
		bind(mvp).asEagerSingleton();
		bindDisplay(display, displayImpl);
	}

	/**
	 * Convenience method for binding a display implementation.
	 * 
	 * @param <D>
	 *            The display interface type
	 * @param display
	 *            The display interface
	 * @param displayImpl
	 *            The display implementation
	 */
	protected <D extends IsWidget> void bindDisplay(Class<D> display, Class<? extends D> displayImpl) {
		bind(display).to(displayImpl).in(Singleton.class);
	}

}
