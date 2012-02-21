package com.semlab.client.fwk.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class WaitPanel extends PopupPanel {

    private static WaitPanel instance;

    public static void wait(boolean status) {
        if (instance == null) {
            instance = new WaitPanel();
            instance.setGlassEnabled(false);
        }
//		Log.debug("WaitPanel -> showRequestCounter = " + instance.showRequestCounter + ", status = " + status);
        if (status)
            instance.center();
        else
            instance.hide();
    }

    private int showRequestCounter = 0;
    private HTML loading;

    private WaitPanel() {
        super(false, true);
        loading = new HTML("<div id='loader' style='position:absolute;'>" +
                "<div><img src='images/loading.gif'></div>");
//            loading.setText("Loading...");
        loading.setVisible(false);
        DOM.setStyleAttribute(loading.getElement(), "position", "absolute");
        DOM.setStyleAttribute(loading.getElement(), "zIndex", "99999999");
        RootPanel.get().add(loading);
        setGlassEnabled(true);
//            HTML i = new HTML("<div><image src='images/ajax-loader.gif'/></div>");
//            setWidget(i);
    }

    protected void onPreviewNativeEvent(Event.NativePreviewEvent event) {
        positionLoadingWidget(event.getNativeEvent().getClientX() + Window.getScrollLeft(),
                event.getNativeEvent().getClientY() + Window.getScrollTop());
        super.onPreviewNativeEvent(event);
    }

    private void positionLoadingWidget(int x, int y) {
//            DOM.setStyleAttribute(loading.getElement(), "left", (x - 25) + "px");
//            DOM.setStyleAttribute(loading.getElement(), "top", (y + 10) + "px");
        DOM.setStyleAttribute(loading.getElement(), "left", x + "px");
        DOM.setStyleAttribute(loading.getElement(), "top", y + "px");
    }

    @Override
    public void center() {
        showRequestCounter++;
        if (showRequestCounter == 1) {
            Event e = DOM.eventGetCurrentEvent();
            if (e != null) {
                positionLoadingWidget(e.getClientX(), e.getClientY());
            } else {
            }
            loading.setVisible(true);
            super.center();
        }
    }

    @Override
    public void hide() {
        loading.setVisible(true);
        showRequestCounter--;
        if (showRequestCounter == 0) {
            loading.setVisible(false);
            super.hide();
        }
    }
}