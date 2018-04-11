package com.shudailaoshi.pojo.vos.mobile.auth;

import java.io.Serializable;

public class AuthTokenVO implements Serializable {

	private static final long serialVersionUID = -4675757225232954600L;

	private String accessToken;
	private long accessTokenExpire;
	private String refreshToken;
	private long refreshTokenExpire;

	public AuthTokenVO() {
	}

	public AuthTokenVO(String accessToken, long accessTokenExpire, String refreshToken, long refreshTokenExpire) {
		this.accessToken = accessToken;
		this.accessTokenExpire = accessTokenExpire;
		this.refreshToken = refreshToken;
		this.refreshTokenExpire = refreshTokenExpire;
	}

	public long getAccessTokenExpire() {
		return accessTokenExpire;
	}

	public void setAccessTokenExpire(long accessTokenExpire) {
		this.accessTokenExpire = accessTokenExpire;
	}

	public long getRefreshTokenExpire() {
		return refreshTokenExpire;
	}

	public void setRefreshTokenExpire(long refreshTokenExpire) {
		this.refreshTokenExpire = refreshTokenExpire;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
