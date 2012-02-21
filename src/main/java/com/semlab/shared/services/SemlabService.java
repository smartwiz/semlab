package com.semlab.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.semlab.shared.config.Configuration;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gwt.rpc")
public interface SemlabService extends RemoteService {

	Configuration getConfig();
	
}
