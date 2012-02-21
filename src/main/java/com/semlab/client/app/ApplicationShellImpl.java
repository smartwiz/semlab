package com.semlab.client.app;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.semlab.client.fwk.widgets.SlidingPanel;

/**
 * The outermost UI of the application.
 */
public class ApplicationShellImpl extends ResizeComposite implements ProvidesResize, ApplicationShell {
	
	private static final Logger log = Logger.getLogger(ApplicationShellImpl.class.getName());
	
	interface Binder extends UiBinder<Widget, ApplicationShellImpl> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);
	
	@UiField
	SlidingPanel master;

	@Inject
	public ApplicationShellImpl() {
		initWidget(BINDER.createAndBindUi(this));
	}

	/**
	 * @return the panel to hold the master list
	 */
	public SlidingPanel getMasterPanel() {
		return master;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
	}

	@Override
	public Widget getDisplay() {
		return this;
	}

	@Override
	public AcceptsOneWidget getWorkspaceContainer() {
		return new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget w) {
				if(w == null) return;
				log.fine("set workspace widget");
				if(!master.containsWidget(w.asWidget())) {
					log.fine("adding a new workspace widget");
					master.add(w);
				}
				master.setWidget(w);
			}
		};
	}
	
}
