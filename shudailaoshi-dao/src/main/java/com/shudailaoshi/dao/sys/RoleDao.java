package com.shudailaoshi.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.Role;

/**
 * 
 * @author Liaoyifan
 * @date 2016年8月6日
 *
 */
public interface RoleDao extends BaseDao<Role> {

	List<Role> listByUserId(@Param("userId") long userId);

	List<Long> listIdByUserId(@Param("userId") long userId);

}
