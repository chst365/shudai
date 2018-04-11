package com.shudailaoshi.pojo.query.sys;

import com.shudailaoshi.pojo.query.base.BaseQuery;

public class UserQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	private String userName;// 用户名
	private String email;// 邮箱
	private String mobile;// 手机

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
