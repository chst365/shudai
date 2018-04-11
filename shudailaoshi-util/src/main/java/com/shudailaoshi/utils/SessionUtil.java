package com.shudailaoshi.utils;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.WebSessionKey;

public class SessionUtil {

	public static final String IS_APP = "isAPP";

	private SessionUtil() {
	}

	/**
	 * 获取shiro sessionAttribute
	 * 
	 * @return
	 */
	public static Object getSessionAttribute(String name) {
		Session session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * 设置shiro sessionAttribute
	 * 
	 * @return
	 */
	public static void setSessionAttribute(String name, Object value) {
		Session session = getSession(false);
		if (session != null) {
			session.setAttribute(name, value);
		} else {
			getSession(true).setAttribute(name, value);
		}
	}

	/**
	 * 删除shiro sessionAttribute
	 * 
	 * @return
	 */
	public static void removeSessionAttribute(String name) {
		Session session = getSession(false);
		if (session != null) {
			session.removeAttribute(name);
		}
	}

	private static Session getSession(boolean create) {
		return SecurityUtils.getSubject().getSession(create);
	}

	/**
	 * 获取shiro sessionId
	 * 
	 * @return
	 */
	public static Serializable getSessionId() {
		Session session = SecurityUtils.getSubject().getSession(false);
		return (session != null ? session.getId() : null);
	}

	/**
	 * 获取shiro session
	 * 
	 * @return
	 */
	public static Session getSession(String sessionId, ServletRequest request, ServletResponse response) {
		return SecurityUtils.getSecurityManager().getSession(new WebSessionKey(sessionId, request, response));
	}

	public static boolean isApp() {
		Boolean isApp = (Boolean) getSessionAttribute(IS_APP);
		if (isApp != null && isApp)
			return true;
		return false;
	}

}
