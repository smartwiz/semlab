package com.semlab.client.fwk;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.semlab.shared.Criteria;
import com.semlab.shared.config.PropertyType;

/**
 *  
 */
public class Utils {
	
	public static FocusImpl focusImpl = FocusImpl.getFocusImplForPanel();

	public static DateTimeFormat dateFormater = DateTimeFormat.getFormat("dd/MM/yyyy");
	public static DateTimeFormat shortDateFormater = DateTimeFormat.getFormat("dd/MM/yy");
	
	final static Element div = DOM.createDiv();

	public static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";
	
	private static long id = new Date().getTime();

	public static void showMessage(String message) {
		Window.alert(message);
	}

	public static Map<String, Object> listValues(Enum<?>[] values) {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		for (Enum<?> e : values) {
			result.put(e.name(), e);
		}
		return result;
	}

	public static boolean isWebKit() {
		return getUserAgent().contains("applewebkit");
	}

	public static boolean isIE() {
		return getUserAgent().contains("msie");
	}

	public static boolean isIE7() {
		return getUserAgent().contains("msie 7.0");
	}

	public static boolean isIE6() {
		return getUserAgent().contains("msie 6.0");
	}

	public String getUA() {
		return getUserAgent();
	}

	public static native String getUserAgent() /*-{
												return navigator.userAgent.toLowerCase();
												}-*/;

	public static <T> ArrayList<T> asList(T... a) {
		ArrayList<T> ret = new ArrayList<T>(a.length);
		for (int i = 0; i < a.length; i++) {
			ret.add(a[i]);
		}
		return ret;
	}

	public static Date stringToDate(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Date value can not be empty");
		}
		return dateFormater.parse(value);
	}

	public static String dateToString(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date can not be empty");
		}
		return dateFormater.format(date);
	}

	public static Date stringToDateSafe(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return dateFormater.parse(value);
	}

	public static String dateToStringSafe(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormater.format(date);
	}
	
	public static String dateToShortString(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date can not be empty");
		}
		return shortDateFormater.format(date);
	}
	
	public static String dateToShortStringSafe(Date date) {
		if (date == null) {
			return "";
		}
		return shortDateFormater.format(date);
	}
	
	public static Date stringAsLongToDateSafe(String stringAsLong){
		if (stringAsLong == null || stringAsLong.trim().isEmpty()) {
			return null;
		}
		long tempDateLong = Long.parseLong(stringAsLong);
		return new Date(tempDateLong);
	}


	/**
	 * Creates a list of all elements contained by the specified element
	 * (inclusive) which have the specified CSS class (in GWT terms
	 * <em>stylename</em>).
	 * 
	 * @param element
	 *            the element which is at the root of the hierarchy that you
	 *            wish to search
	 * @param className
	 *            the name of the CSS class to search for
	 * @param result
	 *            a writable list which will be returned as the result (for
	 *            recursion). Typically, you pass <code>null</code> and the list
	 *            will be created on the fly.
	 * @return <b>result</b> a list containing 0 or more HTML elements
	 */
	public static List<Element> findElementsWithClass(Element element, String className, List<Element> result) {
		if (result == null) {
			result = new ArrayList<Element>();
		}
		String cls = DOM.getElementProperty(element, "className");
		if (cls != null && cls.indexOf(className) >= 0) {
			result.add(element);
		}
		int childCount = DOM.getChildCount(element);
		for (int i = 0; i < childCount; i++) {
			findElementsWithClass(DOM.getChild(element, i), className, result);
		}
		return result;
	}

	public static void setElementOffset(Element element, int offset) {
		if (Utils.isIE()) {
			element.getStyle().setMarginTop(offset, Unit.PX);
		} else {
			element.getStyle().setPaddingTop(offset, Unit.PX);
		}
	}

	public static native String getHostPageLocation() /*-{
		var s = $doc.location.href;

		// Pull off any hash.
		var i = s.indexOf('#');
		if (i != -1)
		s = s.substring(0, i);

		// Pull off any query string.
		i = s.indexOf('?');
		if (i != -1)
		s = s.substring(0, i);

		// Ensure a final slash if non-empty.
		return s;
	}-*/;

	public native static void disableTextSelectInternal(Element e, boolean disable)/*-{
		if (disable) {
			e.ondrag = function () { return false; };
			e.onselectstart = function () { return false; };
		} else {
			e.ondrag = null;
			e.onselectstart = null;
		}
	}-*/;

	public static void hideFocusOutline(com.google.gwt.user.client.Element element) {
		DOM.setStyleAttribute(element, "outline", "0px");
		// Hide focus outline in IE 6/7
		DOM.setElementAttribute(element, "hideFocus", "true");
	}
	
	public static native String getBrowserUserLanguage() /*-{
		var userLang = (navigator.language) ? navigator.language : navigator.userLanguage; 
		return userLang;
	}-*/;
	
	public static LinkedHashMap<String, String> parseParameters(String paramsValue) {
		LinkedHashMap<String, String> paramsData = new LinkedHashMap<String, String>();
		String[] params = paramsValue.split("\n");
		for (String param : params) {
			String[] entry = param.split(":");
			paramsData.put(entry[0], entry[1]);
		}
		return paramsData;
	}
	
	public static native String getStyle(Element oElm, String strCssRule) /*-{
		var strValue = "";
		if(document.defaultView && document.defaultView.getComputedStyle){
			strValue = document.defaultView.getComputedStyle(oElm, null)[strCssRule];
		}
		else if(oElm.currentStyle){
			strCssRule = strCssRule.replace(/\-(\w)/g, function (strMatch, p1){
				return p1.toUpperCase();
			});
			strValue = oElm.currentStyle[strCssRule];
		} else {
			strValue = el.style[prop];
		}
		return strValue;
	}-*/;
	
	public static int getComputedWidth(Element e) {
		return Integer.parseInt(Utils.getStyle(e, "width").replace("px", ""));
	}
	
	public static int getComputedHeight(Element e) {
		return Integer.parseInt(Utils.getStyle(e, "height").replace("px", ""));
	}
	
	public static int getComputedWidth(UIObject o) {
		return Integer.parseInt(Utils.getStyle(o.getElement(), "width").replace("px", ""));
	}
	
	public static int getComputedHeight(UIObject o) {
		return Integer.parseInt(Utils.getStyle(o.getElement(), "height").replace("px", ""));
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	public static long generateUniqueLongId() {
		return ++id;
	}

	public static String generateUniqueStringId() {
		return "#" + generateUniqueLongId();
	}

	public static String escapeHtml(String maybeHtml) {
		DOM.setInnerText(div, maybeHtml);
		return DOM.getInnerHTML(div);
	}
	
	public static native void scrollToTop() /*-{
    	$wnd.scroll(0, 0);
	}-*/;

    public static Widget createClearElement() {
        SimplePanel clear = new SimplePanel();
        DOM.setStyleAttribute(clear.getElement(), "clear", "both");
        return clear;
    }

	public static String getLabelForCriteria(Criteria criteria) {
		StringBuilder sb = new StringBuilder();
		if(criteria.getPath() != null) {
			sb.append(criteria.getPath().asLabelString());
		}
		if(criteria.getOperator() != null) {
			sb.append(" ");
			sb.append(criteria.getOperator().toString());
		}
		if(criteria.getValue() != null) {
			sb.append(" ");
			if(criteria.getPath().getLastField().getType() != PropertyType.Date) {
				sb.append(criteria.getValue());
			} else {
				sb.append(Utils.dateToStringSafe(new Date(Long.parseLong(criteria.getValue()))));
			}
		}
		return sb.toString();
	}
	
	public static String getCriteriaAsString(ArrayList<Criteria> paths) {
		StringBuilder sb = new StringBuilder();
		if(paths != null) {
			for (int i = 0; i < paths.size(); i++) {
				Criteria criteria = paths.get(i);
				sb.append(Utils.getLabelForCriteria(criteria));
				if(i < paths.size()-1) {
					sb.append(";");
				}
			}
		}
		return sb.toString();
	}


}
