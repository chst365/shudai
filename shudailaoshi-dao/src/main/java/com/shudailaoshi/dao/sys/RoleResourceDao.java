package com.shudailaoshi.dao.sys;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.RoleResource;


/**
 * RoleResourceDao
 * 
 * @author Liaoyifan
 * @date 2016年8月30日
 *
 */
public interface RoleResourceDao extends BaseDao<RoleResource> {

	void saveRoleResourceList(@Param("roleId") long roleId, @Param("resourceIds") Set<Long> resourceIds);

}