package com.shudailaoshi.manager.redis.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.dao.sys.UserDao;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.manager.redis.RedisManager;
import com.shudailaoshi.pojo.vos.mobile.auth.AuthSaveVO;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.PropertiesUtil;
import com.shudailaoshi.utils.TokenUtil;
import com.shudailaoshi.utils.ValidUtil;

/**
 * 
 * @author liaoyifan
 *
 */
@Component("redisManager")
public class RedisManagerImpl implements RedisManager {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisRepository redisRepository;

	private static String AUTHORIZATIONCACHE = PropertiesUtil.getConfigProperty("shiro.authorizationCache");

	@Override
	public void deleteAuthorizationCacheByUserNames(Set<String> userNames) {
		if (ValidUtil.isNotEmpty(userNames)) {
			for (String name : userNames) {
				this.deleteAuthorizationCacheByUserName(name);
			}
		}
	}

	@Override
	public void deleteAuthorizationCacheByUserId(long id) {
		this.deleteAuthorizationCacheByUserName(this.userDao.getByPrimaryKey(id).getUserName());
	}

	@Override
	public void deleteAuthorizationCacheByUserName(String userName) {
		String key = new StringBuilder("shiro_cache:").append(AUTHORIZATIONCACHE).append(":").append(userName)
				.toString();
		if (redisRepository.exist(key)) {
			redisRepository.delete(key);
		}
		//手机端清空权限
		String userNameKey = TokenUtil.getUserNameKey(userName);
		if (this.redisRepository.exist(userNameKey)) {
			AuthSaveVO vo = (AuthSaveVO) this.redisRepository.get(userNameKey);
			String accessTokenKey = TokenUtil.getAccessTokenKey(vo.getAccessToken());
			if (this.redisRepository.exist(accessTokenKey)) {
				User user = (User) this.redisRepository.get(accessTokenKey);
				user.setPermissions(this.userService.listPermisstions(userName));
				this.redisRepository.set(accessTokenKey, user, TokenUtil.ACCESS_TOKEN_EXPIRE);
			}
		}

	}

	@Override
	public void deleteAuthorizationCacheByRoleId(long roleId) {
		this.deleteAuthorizationCacheByUserNames(this.userDao.listUserNameByRoleId(roleId));
	}

	@Override
	public void deleteAuthorizationCacheByResourceId(long resourceId) {
		this.deleteAuthorizationCacheByUserNames(this.userDao.listUserNameByResourceId(resourceId));
	}

}
