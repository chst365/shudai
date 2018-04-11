package com.shudailaoshi.entity.sys;

import javax.persistence.Table;

import com.shudailaoshi.entity.base.BaseEntity;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.constants.Constant;

/**
 * 角色
 * 
 * @author Liaoyifan
 *
 */
@Table(name = "sys_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 760552462524013836L;

	@Comment(value = "角色名称", clazz = Constant.class, description = "(管理员角色名) Constant.ADMIN ")
	private String roleName;

	@Comment("描述")
	private String description;

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
