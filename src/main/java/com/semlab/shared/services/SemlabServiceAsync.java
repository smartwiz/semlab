package com.semlab.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.semlab.shared.config.Configuration;

/**
 * The async counterpart of <code>SemlabService</code>.
 */
public interface SemlabServiceAsync {

	void getConfig(AsyncCallback<Configuration> abstractAsyncCallback);

	void startImport(String token,
			AsyncCallback<Void> abstractAsyncCallback);

	void startEnrich(String token,
			AsyncCallback<Void> abstractAsyncCallback);
	


}
