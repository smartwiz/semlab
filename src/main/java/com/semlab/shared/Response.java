package com.semlab.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Response implements Serializable {

	private boolean status;
	private String msg;

	public Response() {
	}

	public Response(boolean status) {
		this.status = status;
	}

	public Response(boolean status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public void setStatus(boolean status){
		this.status = status;
	}
	
	public boolean isStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", msg=" + msg + "]";
	}

}
