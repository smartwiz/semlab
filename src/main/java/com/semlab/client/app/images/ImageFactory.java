package com.semlab.client.app.images;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.semlab.client.fwk.widgets.WidgetFactory;

public class ImageFactory {
	
	private static final String IMAGES_URL = "images/";
	
	public static Image create(String fileName, String width, String height, String style) {
		return WidgetFactory.createImage(toURL(fileName) , width, height, style);
	}
	
	public static String toURL(String imageFile) {
		return IMAGES_URL + imageFile;
	}
	
	public static Image create(ImageResource resource) {
		return new Image(resource);
	}
	
	private static final String FACEBOOK = "facebook_button.gif";
	public static Image facebookButton() {
		return create(FACEBOOK, "169px", "21px", null);
	}

}
