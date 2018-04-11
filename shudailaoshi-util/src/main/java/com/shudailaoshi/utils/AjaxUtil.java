package com.shudailaoshi.utils;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtil {

	private static final String X_REQUESTED_WITH = "X-Requested-With";
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	private AjaxUtil() {
	}

	public static boolean isAjax(HttpServletRequest request) {
		return request.getHeader(X_REQUESTED_WITH) != null
				&& request.getHeader(X_REQUESTED_WITH).equalsIgnoreCase(XML_HTTP_REQUEST);
	}

}
