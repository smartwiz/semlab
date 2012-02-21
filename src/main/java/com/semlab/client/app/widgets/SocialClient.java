package com.semlab.client.app.widgets;

import com.google.gwt.core.client.JavaScriptObject;

public class SocialClient extends JavaScriptObject {

	protected SocialClient() {
	}
	
	public final native String getToken() /*-{
		return this.accessToken;
	}-*/;

	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	public final native String getId() /*-{
		return this.id;
	}-*/;
	
	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;
	
	public final native void setId(String id)/*-{
		this.id = id;
	}-*/;

}
