package com.shudailaoshi.dao.sys;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.pojo.vos.sys.PermitVO;

/**
 * ResourceDao
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public interface ResourceDao extends BaseDao<Resource> {

	List<Resource> listByRoleId(@Param("roleId") long roleId);

	List<Long> listIdByRoleId(@Param("roleId") long roleId);

	List<PermitVO> listPermit(@Param("permissions") Set<String> permissions);

}
