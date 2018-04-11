package com.shudailaoshi.utils;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.mgt.RealmSecurityManager;

public class ShiroUtil {

	private static String AUTHORIZATIONCACHE = PropertiesUtil.getConfigProperty("shiro.authorizationCache");

	/**
	 * 获取当前用户权限字符串集合
	 * 
	 * @return
	 */
	public static Set<String> getPermissions() {
		return getSimpleAuthorizationInfo().getStringPermissions();
	}

	/**
	 * 获取当前用户角色字符串集合
	 * 
	 * @return
	 */
	public static Set<String> getRoles() {
		return getSimpleAuthorizationInfo().getRoles();
	}

	/**
	 * 获取shiro缓存
	 * 
	 * @return
	 */
	public static Cache<Object, Object> getShiroCache(String key) {
		return ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getCacheManager().getCache(key);
	}

	/**
	 * 获取缓存中当前登陆用户的授权信息
	 * 
	 * @return
	 */
	public static SimpleAuthorizationInfo getSimpleAuthorizationInfo() {
		return (SimpleAuthorizationInfo) getShiroCache(AUTHORIZATIONCACHE)
				.get(SecurityUtils.getSubject().getPrincipal());
	}
}
