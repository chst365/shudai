package com.shudailaoshi.manager.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.shudailaoshi.dao.sys.ResourceDao;
import com.shudailaoshi.dao.sys.RoleDao;
import com.shudailaoshi.dao.sys.UserDao;
import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.entity.sys.Role;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.utils.SessionUtil;

/**
 * 
 * @author Liaoyifan
 * @date 2016年8月6日
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前用户名
		String userName = (String) principals.getPrimaryPrincipal();
		if (StringUtils.isBlank(userName)) {
			throw new ControllerException(ExceptionEnum.AUTHORIZATION);
		}
		// 获取当前用户
		User user = this.userDao.get(new User(userName));
		if (user == null) {
			throw new ControllerException(ExceptionEnum.AUTHORIZATION);
		} else {
			// 准备授权数据
			Set<String> roleNames = new HashSet<String>();
			Set<String> resourceUrls = new HashSet<String>();
			// 遍历
			List<Role> roles = this.roleDao.listByUserId(user.getId());
			if (CollectionUtils.isNotEmpty(roles)) {
				for (Role role : roles) {
					roleNames.add(role.getRoleName());
					List<Resource> resources = this.resourceDao.listByRoleId(role.getId());
					if (CollectionUtils.isNotEmpty(resources)) {
						for (Resource resource : resources) {
							resourceUrls.add(resource.getUrl());
						}
					}
				}
			}
			// 给用户授权
			SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
			simpleAuthorizationInfo.setRoles(roleNames);
			simpleAuthorizationInfo.setStringPermissions(resourceUrls);
			return simpleAuthorizationInfo;
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		User user = this.userDao.get(new User((String) token.getPrincipal()));
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),
				user.getUserPwd(), super.getName());
		SessionUtil.setSessionAttribute(Constant.CURRENT_USER, user);
		return simpleAuthenticationInfo;
	}

}
