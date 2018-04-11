package com.shudailaoshi.entity.sys;

import java.io.Serializable;
import javax.persistence.Table;

/**
 * 用户角色表
 * 
 * @author Liaoyifan
 *
 */
@Table(name = "sys_user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8656487491997478235L;

	private Long userId;
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
