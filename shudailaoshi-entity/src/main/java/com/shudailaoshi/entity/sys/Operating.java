package com.shudailaoshi.entity.sys;

import javax.persistence.Table;

import com.shudailaoshi.entity.base.IdEntity;
import com.shudailaoshi.pojo.annotation.Comment;

/**
 * @createAuthor liaoyifan
 * @createDate 2018-04-08
 * @modifyAuthor liaoyifan
 * @modifyDate 2018-04-08
 */
@Table(name = "sys_operating")
public class Operating extends IdEntity {

	private static final long serialVersionUID = 1L;

	@Comment("用户名")
	private String userName;
	@Comment("用户ID")
	private Long userId;
	@Comment("日志")
	private String logText;
	@Comment("IP")
	private String ipAddress;
	@Comment("类名")
	private String clazzName;
	@Comment("方法名")
	private String methodName;
	@Comment("创建时间")
	private Long createTime;

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLogText() {
		return logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}