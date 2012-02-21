package com.semlab.client.fwk.widgets;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class WidgetFactory {

	protected static Logger log = Logger.getLogger(WidgetFactory.class.getName());
	
    public static FlexTable createFlexTable() {
        return createFlexTable(null, 0, 0);
    }

    public static FlexTable createFlexTable(String styleName) {
        return createFlexTable(styleName, 0, 0);
    }

    public static FlexTable createFlexTable(String styleName, int cellSpacing, int cellPadding) {
        FlexTable ft = new FlexTable();
        if (styleName != null) {
            ft.setStyleName(styleName);
        }
        ft.setCellSpacing(cellSpacing);
        ft.setCellPadding(cellPadding);
        return ft;
    }

    /**
     * Creates a {@link com.google.gwt.user.client.ui.PushButton} with the specified face images and stylename.
     *
     * @param upImage   the image to be used on the up face
     * @param styleName the stylename to use for the widget
     * @param handler   a click handler to which to bind the button
     * @return the button
     */
    public static PushButton createPushButtonWithImageStates(Image upImage, String styleName, ClickHandler handler) {
        final PushButton button = new PushButton(upImage, handler);
        button.setStyleName(styleName);
        return button;
    }

    public static PushButton createPushButtonWithImageStates(Image upImage, String styleName) {
        final PushButton button = new PushButton(upImage);
        button.setStyleName(styleName);
        return button;
    }

    public static Image createImage(String url, String width, String height, String... styles) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL for image must not be empty");
        }
        Image img = new Image(url);
        if (styles != null) {
            for (String style : styles) {
                if (style != null) {
                    img.addStyleName(style);
                }
            }
        }
        if (width != null && !width.isEmpty()) {
            img.setWidth(width);
        }
        if (height != null && !height.isEmpty()) {
            img.setHeight(height);
        }
        return img;
    }
    
}
