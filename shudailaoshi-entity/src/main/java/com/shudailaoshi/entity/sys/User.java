package com.shudailaoshi.entity.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.shudailaoshi.entity.base.BaseEntity;
import com.shudailaoshi.pojo.annotation.Comment;

/**
 * 用户
 * 
 * @author Liaoyifan
 *
 */
@Table(name = "sys_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 2128616842976550236L;

	@Comment("用户名")
	private String userName;
	@Comment("密码")
	private String userPwd;
	@Comment("盐")
	private String salt;
	@Comment("邮箱")
	private String email;
	@Comment("手机")
	private String mobile;

	@Transient
	private Set<String> permissions = new HashSet<>();

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
