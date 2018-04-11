package com.shudailaoshi.entity.sys;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "sys_role_resource")
public class RoleResource implements Serializable {

	private static final long serialVersionUID = 4419255150733388531L;

	private Long roleId;
	private Long resourceId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
