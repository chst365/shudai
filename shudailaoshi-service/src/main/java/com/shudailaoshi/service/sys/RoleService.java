package com.shudailaoshi.service.sys;

import java.util.List;
import java.util.Set;

import com.shudailaoshi.entity.sys.Role;
import com.shudailaoshi.service.base.BaseService;

/**
 * RoleService
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 角色授权
	 * 
	 * @param id
	 * @param resourceIds
	 */
	void grant(long id, Set<Long> resourceIds);

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	void saveRole(Role role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	void removeRole(long id);

	/**
	 * 恢复角色
	 * 
	 * @param id
	 */
	void recover(long id);

	/**
	 * 冻结角色
	 * 
	 * @param id
	 */
	void freeze(long id);

	/**
	 * 解冻角色
	 * 
	 * @param id
	 */
	void unfreeze(long id);

	/**
	 * 编辑角色
	 * 
	 */
	void updateRole(long id, Role role);

	/**
	 * 获取角色id集合通过用户id
	 * 
	 * @param userId
	 * @return
	 */
	List<Long> listIdByUserId(long userId);

}