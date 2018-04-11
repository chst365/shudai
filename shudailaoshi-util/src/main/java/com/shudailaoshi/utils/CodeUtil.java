package com.shudailaoshi.utils;

import java.util.UUID;

/**
 * 
 * @author Liaoyifan
 *
 */
public class CodeUtil {

	private static final String RANDOM_STRING = "1234567890";

	public static final String RECHARGE_TRADE = "RE";

	public static final String BESPEAK_TRADE = "BE";

	public static final String WASH_TRADE = "WE";

	private CodeUtil() {
	}

	/**
	 * 获取唯一的编码
	 */
	public static String getUniqueCode() {
		return new StringBuffer(DateUtil.getDateNumber()).append(MD5Util.getMD5Bit16(UUID.randomUUID().toString()))
				.toString();
	}

	public static String getBespeakNo() {
		return BESPEAK_TRADE + getUniqueCode();
	}

	public static String getRechargeNo() {
		return RECHARGE_TRADE + getUniqueCode();
	}

	public static String getWashNo() {
		return WASH_TRADE + getUniqueCode();
	}

	public static String getRandom(int n) {
		String rand = "";
		int len = RANDOM_STRING.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			rand = rand + RANDOM_STRING.charAt((int) r);
		}
		return rand;
	}

}
