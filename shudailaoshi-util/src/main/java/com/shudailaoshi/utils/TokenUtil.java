package com.shudailaoshi.utils;

public class TokenUtil {

	/**
	 * mobile ACCESS_TOKEN
	 */
	public static final String MOBILE_ACCESS_TOKEN = "mobile_access_token:";

	/**
	 * mobile REFRESH_TOKEN
	 */
	public static final String MOBILE_REFRESH_TOKEN = "mobile_refresh_token:";

	/**
	 * mobile SHIRO_USER_ID
	 */
	public static final String MOBILE_USER_NAME = "mobile_user_name:";

	/**
	 * ACCESS_TOKEN 超時秒數
	 */
	public static final long ACCESS_TOKEN_EXPIRE = Long
			.valueOf(PropertiesUtil.getConfigProperty("token.access.expire"));

	/**
	 * REFRESH_TOKEN 超時秒數
	 */
	public static final long REFRESH_TOKEN_EXPIRE = Long
			.valueOf(PropertiesUtil.getConfigProperty("token.refresh.expire"));

	private TokenUtil() {
	}

	/**
	 * 获取accessTokenKey
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getAccessTokenKey(String accessToken) {
		return new StringBuilder(MOBILE_ACCESS_TOKEN).append(accessToken).toString();
	}

	/**
	 * 获取refreshTokenKey
	 * 
	 * @param refreshToken
	 * @return
	 */
	public static String getRefreshTokenKey(String refreshToken) {
		return new StringBuilder(MOBILE_REFRESH_TOKEN).append(refreshToken).toString();
	}

	/**
	 * 获取userNameKey
	 * 
	 * @param userName
	 * @return
	 */
	public static String getUserNameKey(String userName) {
		return new StringBuilder(MOBILE_USER_NAME).append(userName).toString();
	}

}
