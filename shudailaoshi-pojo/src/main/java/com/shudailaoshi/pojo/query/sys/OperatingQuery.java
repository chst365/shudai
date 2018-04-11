package com.shudailaoshi.pojo.query.sys;

import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.query.base.BaseQuery;

/**
 * @createAuthor liaoyifan
 * @createDate 2018-04-08
 * @modifyAuthor liaoyifan
 * @modifyDate 2018-04-08
 */
public class OperatingQuery extends BaseQuery {

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