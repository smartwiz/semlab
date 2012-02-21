package com.semlab.shared;

@SuppressWarnings("serial")
public class ObjectResponse<T> extends Response {

	private T object;

	public ObjectResponse(){
	}
	
	public ObjectResponse(boolean status){
		super(status);
	}
	
	public ObjectResponse(boolean status, String msg){
		super(status, msg);
	}
	
	public ObjectResponse(T object) {
		super(true);
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	
}
