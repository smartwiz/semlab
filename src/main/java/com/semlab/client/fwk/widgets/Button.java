package com.semlab.client.fwk.widgets;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class Button extends com.google.gwt.user.client.ui.Button {
	
	public static enum Alignment {

		TOP, RIGHT, BOTTOM, LEFT;
		
	}

	private static final String STYLENAME_DEFAULT = "x-button";
	
	private Image icon;
	private HTML label;
	private Alignment iconAlignment = Alignment.LEFT;
	private boolean isHtml;

	public Button() {
		this(null, null, null);
	}
	
	public Button(String text) {
		this(text, null, null);
	}
	
	public Button(String text, ClickHandler handler) {
		this(text, null, handler);
	}
	
	public Button(Image icon) {
		this(null, icon, null);
	}
	
	public Button(Image icon, ClickHandler handler) {
		this(null, icon, handler);
	}
	
	public Button(String html, Image icon) {
		this(html, icon, null);
	}
	
	public Button(String html, Image icon, ClickHandler handler) {
		setStyleName(STYLENAME_DEFAULT);
		if(html != null) {
			setHTML(html);
		}
		if(icon != null) {
			setIcon(icon);
		}
		if(handler != null) {
			addClickHandler(handler);
		}
	}

	public void setHTML(String html) {
		setHTML(html, false);
	}
	
	public void setHTML(String html, boolean render) {
		assert html != null;
		if(label == null) {
			createLabel();
		}
		isHtml = true;
		this.label.setHTML(html);
		if(render) {
			render();
		}
	}
	
	public String getHTML() {
		if(label != null) {
			return label.getHTML();
		} else {
			return null;
		}
	}

	public void setText(String text) {
		setText(text, false);
	}
	
	public void setText(String text, boolean render) {
		assert text != null;
		if(label == null) {
			createLabel();
		}
		isHtml = false;
		this.label.setText(text);
		if(render) {
			render();
		}
	}
	
	public String getText() {
		if(label != null) {
			return label.getText();
		} else {
			return null;
		}
	}
	
	public void setIcon(Image icon) {
		setIcon(icon, false);
	}
	
	public void setIcon(Image icon, boolean render) {
		assert icon != null;
		createIcon(icon);
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public Alignment getIconAlignment() {
		return iconAlignment;
	}

	public void setIconAlignment(Alignment iconAlignment) {
		setIconAlignment(iconAlignment, false);
	}
	
	public void setIconAlignment(Alignment iconAlignment, boolean render) {
		this.iconAlignment = iconAlignment;
		if(render) {
			render();
		}
	}
	
	private void createIcon(Image icon) {
		this.icon = icon;
		icon.setStyleName(STYLENAME_DEFAULT+ "-icon");
	}
	
	private void createLabel() {
		label = new HTML();
		label.getElement().getStyle().setProperty("whiteSpace", "nowrap");
		label.setStyleName(STYLENAME_DEFAULT+ "-label");
	}

	protected void render() {
		super.setHTML(getContent());
	}
	
	protected String getContent() {
		if(icon == null && label == null) {
			return "";
		}
		if(icon != null && label == null) {
			return icon.toString();
		}
		if(icon == null && label != null) {
			return isHtml ? label.getHTML() : label.getText();
		}
		FlowPanel panel = new FlowPanel();
		if(iconAlignment == Alignment.LEFT || iconAlignment == Alignment.RIGHT) {
			label.getElement().getStyle().setDisplay(Display.INLINE);
		} else {
			label.getElement().getStyle().setDisplay(Display.BLOCK);
		}
		if(iconAlignment == Alignment.LEFT || iconAlignment == Alignment.TOP) {
			panel.add(icon);
			panel.add(label);
		} else {
			panel.add(label);
			panel.add(icon);
		}
		return panel.toString();
	}

	@Override
	protected void onLoad() {
		render();
	} 

}
