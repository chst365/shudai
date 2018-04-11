package com.shudailaoshi.pojo.vos.sys;

import com.shudailaoshi.pojo.vos.base.BaseVO;

public class UserVO extends BaseVO {

	private static final long serialVersionUID = 1259752563000310872L;
	
	private String userName;// 用户名
	private String userPwd;// 密码
	private String salt;// 盐
	private String email;// 邮箱
	private String mobile;// 手机
	private Integer userType;// com.shudailaoshi.entity.enums.sys.UserTypeEnum

	private Long employeeId;
	private String employeeName;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
