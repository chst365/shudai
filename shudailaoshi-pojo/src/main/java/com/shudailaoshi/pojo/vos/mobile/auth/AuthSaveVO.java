package com.shudailaoshi.pojo.vos.mobile.auth;

import java.io.Serializable;

public class AuthSaveVO implements Serializable {

	private static final long serialVersionUID = -3335877591830162929L;

	private String refreshToken;
	private String accessToken;
	private String userName;

	public AuthSaveVO() {
	}

	public AuthSaveVO(String accessToken, String refreshToken, String userName) {
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
