package com.shudailaoshi.dao.sys;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.UserRole;

/**
 * UserRoleDao
 * 
 * @author Liaoyifan
 * @date 2016年9月1日
 *
 */
public interface UserRoleDao extends BaseDao<UserRole> {

	void saveUserRoleList(@Param("userId") long userId, @Param("roleIds") Set<Long> roleIds);

}