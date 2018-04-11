package com.shudailaoshi.service.sys.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.dao.sys.ResourceDao;
import com.shudailaoshi.dao.sys.RoleDao;
import com.shudailaoshi.dao.sys.UserDao;
import com.shudailaoshi.dao.sys.UserRoleDao;
import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.entity.sys.Role;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.entity.sys.UserRole;
import com.shudailaoshi.pojo.annotation.RedisCacheMethod;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.query.sys.UserQuery;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.pojo.vos.mobile.auth.AuthSaveVO;
import com.shudailaoshi.pojo.vos.mobile.auth.AuthTokenVO;
import com.shudailaoshi.service.base.impl.BaseServiceImpl;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.CodeUtil;
import com.shudailaoshi.utils.DateUtil;
import com.shudailaoshi.utils.MD5Util;
import com.shudailaoshi.utils.PropertiesUtil;
import com.shudailaoshi.utils.TokenUtil;
import com.shudailaoshi.utils.ValidUtil;

/**
 * 
 * @author Liaoyifan
 * @date 2016年8月6日
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public PageVO page(UserQuery userQuery, int start, int limit) {
		return new PageVO(start, limit, this.userDao.countUser(userQuery),
				this.userDao.listUser(userQuery, start, limit));
	}

	@Override
	public void resetPwd(long id) {
		User user = this.userDao.getByPrimaryKey(id);
		String salt = CodeUtil.getUniqueCode();
		user.setSalt(salt);
		user.setUserPwd(MD5Util.getMD5Pwd(salt, PropertiesUtil.getConfigProperty("user.resetPwd")));
		user.setModifyTime(DateUtil.getTime());
		this.userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public void saveUser(User user) {
		if (this.userDao.getCount(new User(user.getUserName())) > 0) {
			throw new ServiceException(ExceptionEnum.USERNAME_EXISTED);
		}
		if (ValidUtil.isNotBlank(user.getMobile())) {
			User search = new User();
			search.setMobile(user.getMobile());
			if (this.userDao.getCount(search) > 0) {
				throw new ServiceException(ExceptionEnum.MOBILE_EXISTS);
			}
		}
		String salt = CodeUtil.getUniqueCode();
		user.setSalt(salt);
		user.setUserPwd(MD5Util.getMD5Pwd(salt, user.getUserPwd()));
		long time = DateUtil.getTime();
		user.setCreateTime(time);
		user.setModifyTime(time);
		user.setStatus(StatusEnum.NORMAL.getValue());
		this.userDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		User searchUser = this.userDao.get(new User(user.getUserName()));
		long userId = user.getId();
		if (searchUser != null && searchUser.getId().longValue() != userId) {
			throw new ServiceException(ExceptionEnum.USERNAME_EXISTED);
		}
		User saveUser = this.userDao.getByPrimaryKey(userId);
		saveUser.setEmail(user.getEmail());
		saveUser.setMobile(user.getMobile());
		saveUser.setUserName(user.getUserName());
		saveUser.setModifyTime(DateUtil.getTime());
		this.userDao.updateByPrimaryKey(saveUser);
	}

	@Override
	public void removeUser(long id) {
		try {
			UserRole userRole = new UserRole();
			userRole.setUserId(id);
			this.userRoleDao.remove(userRole);
			this.userDao.removeByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException(ExceptionEnum.USERD_NOT_ALLOW_DELETE);
		}
	}

	@Override
	public void recover(long id) {
		User user = new User();
		user.setId(id);
		user.setModifyTime(DateUtil.getTime());
		user.setStatus(StatusEnum.NORMAL.getValue());
		this.userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public void freeze(long id) {
		User user = new User();
		user.setId(id);
		user.setModifyTime(DateUtil.getTime());
		user.setStatus(StatusEnum.FREEZE.getValue());
		this.userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public void unfreeze(long id) {
		User user = new User();
		user.setId(id);
		user.setModifyTime(DateUtil.getTime());
		user.setStatus(StatusEnum.NORMAL.getValue());
		this.userDao.updateByPrimaryKeySelective(user);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByUserId", paramNames = { "id" })
	@Override
	public void grant(long id, Set<Long> roleIds) {
		UserRole userRole = new UserRole();
		userRole.setUserId(id);
		this.userRoleDao.remove(userRole);
		if (ValidUtil.isNotEmpty(roleIds)) {
			this.userRoleDao.saveUserRoleList(id, roleIds);
		}
	}

	@Override
	public AuthTokenVO refreshToken(String refreshToken) {
		String refreshTokenKey = TokenUtil.getRefreshTokenKey(refreshToken);
		if (!this.redisRepository.exist(refreshTokenKey)) {
			throw new ServiceException(ExceptionEnum.REFRESH_TOKEN_NOT_EXISTS);
		}
		long userId = (long) this.redisRepository.get(refreshTokenKey);
		User user = this.userDao.getByPrimaryKey(userId);
		if (user == null) {
			throw new ServiceException(ExceptionEnum.USER_NOT_EXISTS);
		}
		int status = user.getStatus();
		if (status == StatusEnum.DELETE.getValue()) {
			throw new ServiceException(ExceptionEnum.USER_NOT_EXISTS);
		}
		if (status == StatusEnum.FREEZE.getValue()) {
			throw new ServiceException(ExceptionEnum.USER_FREEZE);
		}
		String userName = user.getUserName();
		if (!this.redisRepository.exist(TokenUtil.getUserNameKey(userName))) {
			throw new ServiceException(ExceptionEnum.REFRESH_TOKEN_NOT_EXISTS);
		}
		user.setPermissions(this.listPermisstions(user.getUserName()));
		return this.createToken(user);
	}

	@Override
	public AuthTokenVO createToken(User user) {
		final String userName = user.getUserName();
		String userNameKey = TokenUtil.getUserNameKey(userName);
		this.deleteToken(userNameKey);
		// 生成token
		final String accessToken = CodeUtil.getUniqueCode();
		final String refreshToken = CodeUtil.getUniqueCode();
		// 存储token
		this.redisRepository.set(TokenUtil.getAccessTokenKey(accessToken), user, TokenUtil.ACCESS_TOKEN_EXPIRE);
		this.redisRepository.set(TokenUtil.getRefreshTokenKey(refreshToken), userNameKey,
				TokenUtil.REFRESH_TOKEN_EXPIRE);
		this.redisRepository.set(userNameKey, new AuthSaveVO(accessToken, refreshToken, userNameKey),
				TokenUtil.REFRESH_TOKEN_EXPIRE);
		return new AuthTokenVO(accessToken, TokenUtil.ACCESS_TOKEN_EXPIRE, refreshToken,
				TokenUtil.REFRESH_TOKEN_EXPIRE);
	}

	@Override
	public void changePwd(Long userId, String oldPwd, String newPwd, String newPwdRe) {
		User user = userDao.getByPrimaryKey(userId);
		String salt = user.getSalt();
		if (!MD5Util.getMD5Pwd(salt, oldPwd).equals(user.getUserPwd())) {
			throw new ServiceException(ExceptionEnum.OLD_PWD_ERROR);
		}
		if (!newPwd.equals(newPwdRe)) {
			throw new ServiceException(ExceptionEnum.USERPWD_EQUALS_ERROR);
		}
		if (MD5Util.getMD5Pwd(salt, newPwd).equals(user.getUserPwd())) {
			throw new ServiceException("密码未发生改变");
		}
		String newSalt = CodeUtil.getUniqueCode();
		user.setSalt(newSalt);
		user.setUserPwd(MD5Util.getMD5Pwd(newSalt, newPwd));
		user.setModifyTime(DateUtil.getTime());
		userDao.updateByPrimaryKey(user);

	}

	@Override
	public User getCurUserInfo(Long id) {
		return userDao.getByPrimaryKey(id);
	}

	@Override
	public void deleteToken(String userNameKey) {
		// 清空token
		if (this.redisRepository.exist(userNameKey)) {
			AuthSaveVO vo = (AuthSaveVO) this.redisRepository.get(userNameKey);
			this.redisRepository.delete(userNameKey);
			String accessTokenKey = TokenUtil.getAccessTokenKey(vo.getAccessToken());
			if (this.redisRepository.exist(accessTokenKey)) {
				this.redisRepository.delete(accessTokenKey);
			}
			String refreshTokenKey = TokenUtil.getRefreshTokenKey(vo.getRefreshToken());
			if (this.redisRepository.exist(refreshTokenKey)) {
				this.redisRepository.delete(refreshTokenKey);
			}
		}
	}

	@Override
	public Set<String> listPermisstions(String userName) {
		User user = this.userDao.get(new User(userName));
		// 准备授权数据
		Set<String> permissions = new HashSet<>();
		// 遍历
		List<Role> roles = this.roleDao.listByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(roles)) {
			for (Role role : roles) {
				List<Resource> resources = this.resourceDao.listByRoleId(role.getId());
				if (CollectionUtils.isNotEmpty(resources)) {
					for (Resource resource : resources) {
						permissions.add(resource.getUrl());
					}
				}
			}
		}
		return permissions;
	}

	@Override
	public String getUserMobileByCustomerId(Long id) {

		return this.userDao.getUserMobileByCustomerId(id);
	}

}
