package com.shudailaoshi.dao.sys;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.query.sys.UserQuery;

/**
 * UserDao
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public interface UserDao extends BaseDao<User> {

	List<User> listUser(@Param("userQuery") UserQuery userQuery, @Param("start") Integer start, @Param("limit") Integer limit);

	Long countUser(@Param("userQuery") UserQuery userQuery);

	Set<String> listUserNameByRoleId(@Param("roleId") long roleId);

	Set<String> listUserNameByResourceId(@Param("resourceId") long resourceId);
	
	String getUserMobileByCustomerId(@Param("customerId") long customerId);
}
