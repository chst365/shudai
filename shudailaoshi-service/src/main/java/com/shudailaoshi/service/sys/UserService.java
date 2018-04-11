package com.shudailaoshi.service.sys;

import java.util.Set;

import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.query.sys.UserQuery;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.pojo.vos.mobile.auth.AuthTokenVO;
import com.shudailaoshi.service.base.BaseService;

/**
 * UserService
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public interface UserService extends BaseService<User> {

	/**
	 * 分页显示
	 * 
	 * @param userQuery
	 * @param start
	 * @param limit
	 * @return
	 */
	PageVO page(UserQuery userQuery, int start, int limit);

	/**
	 * 重置密码
	 * 
	 * @param id
	 */
	void resetPwd(long id);

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	void removeUser(long id);

	/**
	 * 恢复用户
	 * 
	 * @param id
	 */
	void recover(long id);

	/**
	 * 冻结用户
	 * 
	 * @param id
	 */
	void freeze(long id);

	/**
	 * 解冻用户
	 * 
	 * @param id
	 */
	void unfreeze(long id);

	/**
	 * 授予角色
	 * 
	 * @param id
	 * @param roleIds
	 */
	void grant(long id, Set<Long> roleIds);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * 通过refreshToken刷新token
	 * 
	 * @param refreshToken
	 * @return
	 */
	AuthTokenVO refreshToken(String refreshToken);

	/**
	 * 创建token
	 * 
	 * @param user
	 * @return
	 */
	AuthTokenVO createToken(User user);

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @param newPwdRe
	 *            确认密码
	 */
	void changePwd(Long userId, String oldPwd, String newPwd, String newPwdRe);

	User getCurUserInfo(Long id);

	void deleteToken(String userNameKey);

	Set<String> listPermisstions(String userName);

	/**
	 * 会员ID获取会员手机号(登陆账号)
	 * @param id
	 * @return
	 */
	String getUserMobileByCustomerId(Long id);

}
