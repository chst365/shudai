package com.shudailaoshi.utils;

/**
 * 过滤Xss
 * 
 * @author Liaoyifan
 */
public class XssUtils {

	private XssUtils() {
	}

	public static String cleanXss(final String value) {
		return value.replaceAll("<", "&lt;")//
				.replaceAll(">", "&gt;")//
				.replaceAll("\\(", "&#40;")//
				.replaceAll("\\)", "&#41;")//
				.replaceAll("'", "&#39;")//
				.replaceAll("eval\\((.*)\\)", "")//
				.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"")//
				.replaceAll("script", "&#115;&#99;&#114;&#105;&#112;&#116;");
	}

}
