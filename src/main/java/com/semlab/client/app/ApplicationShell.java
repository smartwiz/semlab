package com.semlab.client.app;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;

public interface ApplicationShell {
	
	AcceptsOneWidget getWorkspaceContainer();
	
	Widget getDisplay();

}
