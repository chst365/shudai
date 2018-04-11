package com.shudailaoshi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.shudailaoshi.utils.exceptions.UtilException;
import com.shudailaoshi.utils.exceptions.UtilExceptionEnum;

/**
 * Cookie 辅助类
 * 
 * @author Liaoyifan
 */
public class CookieUtil {

	/**
	 * cookie path
	 */
	public static final String COOKIE_PATH = "/";
	/**
	 * UTF-8
	 */
	public static final String UTF8 = "utf-8";

	private CookieUtil() {
	}

	/**
	 * 获得cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static String get(final HttpServletRequest request, final String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					try {
						return URLDecoder.decode(c.getValue(), UTF8);
					} catch (UnsupportedEncodingException e) {
						throw new UtilException(UtilExceptionEnum.COOKIE_GET_ERROR,e);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @return
	 */
	public static void set(final HttpServletResponse response, final String name, final String value) {
		set(response, name, value, null, null, null, false);
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @param expiry
	 *            时间(秒)
	 * @param domain
	 * @return
	 */
	public static void set(final HttpServletResponse response, final String name, final String value, final String path,
			final Integer expiry, final String domain, final boolean httpOnly) {
		try {
			Cookie cookie = new Cookie(name, URLEncoder.encode(value, UTF8));
			if (StringUtils.isBlank(path)) {
				cookie.setPath(COOKIE_PATH);
			} else {
				cookie.setPath(path);
			}
			if (expiry != null) {
				cookie.setMaxAge(expiry);
			}
			if (StringUtils.isNotBlank(domain)) {
				cookie.setDomain(domain);
			}
			cookie.setHttpOnly(httpOnly);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			throw new UtilException(UtilExceptionEnum.COOKIE_ADD_ERROR,e);
		}
	}

	/**
	 * 取消cookie
	 * 
	 * @param response
	 * @param name
	 */
	public static void remove(final HttpServletResponse response, final String name) {
		remove(response, name, null, null);
	}

	/**
	 * 取消cookie
	 * 
	 * @param response
	 * @param name
	 * @param path
	 * @param domain
	 */
	public static void remove(final HttpServletResponse response, final String name, final String path,
			final String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		if (StringUtils.isBlank(path)) {
			cookie.setPath(COOKIE_PATH);
		} else {
			cookie.setPath(path);
		}
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

}