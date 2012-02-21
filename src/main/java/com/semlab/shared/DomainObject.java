package com.semlab.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DomainObject implements Serializable{
	
	private long nodeId;
	
	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

}
