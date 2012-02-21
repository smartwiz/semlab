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
	
	private static final String CLOSE = "bt_close.gif";
	public static Image close() {
		return create(CLOSE , "22px", "12px", null);
	}
	
	private static final String EDIT = "bt_edit_34_16.gif";
	public static Image edit() {
		return create(EDIT , "22px", "12px", null);
	}
	
	private static final String ADD = "add.png";
	public static Image add() {
		return create(ADD , "16px", "16px", null);
	}
	
	private static final String UP = "arrow_up.png";
	public static Image up() {
		return create(UP , "16px", "16px", null);
	}
	
	private static final String DOWN = "arrow_down.png";
	public static Image down() {
		return create(DOWN , "16px", "16px", null);
	}
	
	private static final String DELETE = "delete.png";
	public static Image delete() {
		return create(DELETE, "16px", "16px", null);
	}
	
	private static final String FACEBOOK = "facebook_button.gif";
	public static Image facebookButton() {
		return create(FACEBOOK, "169px", "21px", null);
	}

	private static final String EXCEL = "excel_button.png";
	public static Image excelButton() {
		return create(EXCEL, "100px", "21px", null);
	}
}
