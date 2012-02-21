package com.semlab.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gwtwidgets.server.spring.GWTRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semlab.shared.config.Configuration;
import com.semlab.shared.services.SemlabService;

/**
 * The server side implementation of the RPC service.
 */
@Service
@GWTRequestMapping("/semlab/gwt.rpc")
public class SemlabServiceImpl implements SemlabService {

	private static Log log = LogFactory.getLog(SemlabServiceImpl.class);
	
	@Autowired
	Configuration config;
	
	public SemlabServiceImpl() {
	}

	@Override
	public Configuration getConfig() {
		return config;
	}

}
