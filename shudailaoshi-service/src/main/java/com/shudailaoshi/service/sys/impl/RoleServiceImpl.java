package com.shudailaoshi.service.sys.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shudailaoshi.dao.sys.RoleDao;
import com.shudailaoshi.dao.sys.RoleResourceDao;
import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.sys.Role;
import com.shudailaoshi.entity.sys.RoleResource;
import com.shudailaoshi.pojo.annotation.RedisCacheMethod;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.service.base.impl.BaseServiceImpl;
import com.shudailaoshi.service.sys.RoleService;
import com.shudailaoshi.utils.DateUtil;
import com.shudailaoshi.utils.ValidUtil;

/**
 * @author Liaoyifan
 * @date 2016年8月6日
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleResourceDao roleResourceDao;

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void grant(long id, Set<Long> resourceIds) {
		RoleResource roleResource = new RoleResource();
		roleResource.setRoleId(id);
		roleResourceDao.remove(roleResource);
		if (ValidUtil.isNotEmpty(resourceIds)) {
			resourceIds.add(Constant.ROOT);
			this.roleResourceDao.saveRoleResourceList(id, resourceIds);
		}
	}

	@Override
	public void saveRole(Role role) {
		if (this.roleDao.getCount(new Role(role.getRoleName())) > 0) {
			throw new ServiceException(ExceptionEnum.ROLENAME_EXISTED);
		}
		long time = DateUtil.getTime();
		role.setCreateTime(time);
		role.setModifyTime(time);
		role.setStatus(StatusEnum.NORMAL.getValue());
		this.roleDao.save(role);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void updateRole(long id, Role role) {
		Role nameRole = this.roleDao.get(new Role(role.getRoleName()));
		if (nameRole != null && nameRole.getId().longValue() != id) {
			throw new ServiceException(ExceptionEnum.ROLENAME_EXISTED);
		}
		role.setModifyTime(DateUtil.getTime());
		this.roleDao.updateByPrimaryKeySelective(role);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void removeRole(long id) {
		if (this.roleDao.getByPrimaryKey(id).getRoleName().equals(Constant.ADMIN)) {
			throw new ServiceException(ExceptionEnum.ADMIN_NOT_ALLOW_DELETE);
		}
		try {
			RoleResource roleResource = new RoleResource();
			roleResource.setRoleId(id);
			this.roleResourceDao.remove(roleResource);
			this.roleDao.removeByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException(ExceptionEnum.USERD_NOT_ALLOW_DELETE);
		}
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void recover(long id) {
		Role role = new Role();
		role.setId(id);
		role.setModifyTime(DateUtil.getTime());
		role.setStatus(StatusEnum.NORMAL.getValue());
		this.roleDao.updateByPrimaryKeySelective(role);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void freeze(long id) {
		if (this.roleDao.getByPrimaryKey(id).getRoleName().equals(Constant.ADMIN)) {
			throw new ServiceException(ExceptionEnum.ADMIN_NOT_ALLOW_FREEZE);
		}
		Role role = new Role();
		role.setId(id);
		role.setModifyTime(DateUtil.getTime());
		role.setStatus(StatusEnum.FREEZE.getValue());
		this.roleDao.updateByPrimaryKeySelective(role);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByRoleId", paramNames = { "id" })
	@Override
	public void unfreeze(long id) {
		Role role = new Role();
		role.setId(id);
		role.setModifyTime(DateUtil.getTime());
		role.setStatus(StatusEnum.NORMAL.getValue());
		this.roleDao.updateByPrimaryKeySelective(role);
	}

	@Override
	public List<Long> listIdByUserId(long userId) {
		return this.roleDao.listIdByUserId(userId);
	}

}
