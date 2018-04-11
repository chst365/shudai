package com.shudailaoshi.utils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import com.shudailaoshi.utils.MD5Util;
import com.shudailaoshi.utils.NumberUtil;

/**
 * 第三方支付工具类
 * 
 * @author Heguoyong
 *
 */
public class PayUtil {

	private PayUtil() {
	}

	/**
	 * 统一下单-微信支付二维码：CODE_URL
	 */
	public static String CODE_URL = "code_url";
	/**
	 * 统一下单：RETURN_CODE
	 */
	public static String RETURN_CODE = "return_code";
	/**
	 * 统一下单：RESULT_CODE
	 */
	public static String RESULT_CODE = "result_code";

	private static String SIGN;

	public static String wechatCodeAndMsg(boolean success) {
		StringBuffer sb = new StringBuffer();
		if (success) {
			sb.append("<xml><return_code><![CDATA[SUCCESS]]></return_code>");
			sb.append("<return_msg><![CDATA[OK]]></return_msg></xml>");
		} else {
			sb.append("<xml><return_code><![CDATA[FAIL]]></return_code>");
			sb.append("<return_msg><![CDATA[NO]]></return_msg></xml>");
		}
		return sb.toString();
	}

	public static int fromatWechatTotalToInt(BigDecimal totalFee) {

		return NumberUtil.mul(totalFee, BigDecimal.valueOf(100)).intValue();
	}

	public static BigDecimal fromatWechatTotalToBigDecimal(String totalFee) {
		return NumberUtil.div(BigDecimal.valueOf(Double.parseDouble(totalFee)), BigDecimal.valueOf(100));
	}

	public static String wechatXmlParams(TreeMap<String, String> treeMap, String secretkey) {

		StringBuffer param = new StringBuffer();
		for (String key : treeMap.keySet()) {
			param.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		param.append("key=" + secretkey);
		SIGN = MD5Util.MD5Encode(param.toString(), "utf-8").toUpperCase();
		treeMap.put("sign", SIGN);

		StringBuilder xml = new StringBuilder();
		xml.append("<xml>\n");
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
				xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue())
						.append("]]></" + entry.getKey() + ">\n");
			} else {
				xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
			}
		}
		xml.append("</xml>");
		System.out.println("统一下单预支付订单：\n" + xml);
		return xml.toString();
	}
}
