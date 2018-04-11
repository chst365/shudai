package com.shudailaoshi.manager.redis;

import java.util.Set;

public interface RedisManager {

	/**
	 * 通过userNames用户名集合 删除 shiro 权限缓存
	 * 
	 * @param userNames
	 */
	void deleteAuthorizationCacheByUserNames(Set<String> userNames);

	/**
	 * 通过userName用户名 删除 shiro 权限缓存
	 * 
	 * @param userName
	 */
	void deleteAuthorizationCacheByUserName(String userName);

	/**
	 * 通过roleId 删除 shiro 权限缓存
	 * 
	 * @param roleId
	 */
	void deleteAuthorizationCacheByRoleId(long roleId);

	/**
	 * 通过resourceId 删除 shiro 权限缓存
	 * 
	 * @param roleId
	 */
	void deleteAuthorizationCacheByResourceId(long resourceId);

	/**
	 * 通过userId 删除 shiro 权限缓存
	 * 
	 * @param id
	 */
	void deleteAuthorizationCacheByUserId(long id);

}
