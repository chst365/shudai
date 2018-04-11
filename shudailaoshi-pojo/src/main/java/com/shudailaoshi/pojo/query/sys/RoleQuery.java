package com.shudailaoshi.pojo.query.sys;

import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.query.base.BaseQuery;

public class RoleQuery extends BaseQuery {

	private static final long serialVersionUID = 760552462524013836L;

	@Comment(value = "角色名称")
	private String roleName;
	@Comment("描述")
	private String description;

	public RoleQuery() {
	}

	public RoleQuery(String roleName) {
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
