package com.shudailaoshi.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;

import com.shudailaoshi.utils.exceptions.UtilException;
import com.shudailaoshi.utils.exceptions.UtilExceptionEnum;

/**
 * MD5工具类
 * 
 * @author Liaoyifan
 */
public class MD5Util {

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };
	private static MessageDigest messagedigest = null;

	private MD5Util() {
	}

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new UtilException(UtilExceptionEnum.MD5_INIT_ERROR, e);
		}
	}

	/**
	 * 获取MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(final String str) {
		try {
			byte[] btInput = str.getBytes();
			messagedigest.update(btInput);
			byte[] md = messagedigest.digest();
			int j = md.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
				chars[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(chars);
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.MD5_GET_ERROR,e);
		}
	}
	
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits2[d1] + hexDigits2[d2];
	}

	private static final String hexDigits2[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/**
	 * 获取HashCode
	 * 
	 * @param object
	 * @return
	 */
	public static String getHashCode(final Object object) {
		ObjectOutputStream oos = null;
		OutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return MD5Util.getMD5(baos.toString());
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.MD5_GET_ERROR, e);
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(baos);
		}
	}

	/**
	 * 获取16位MD5字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Bit16(final String str) {
		return MD5Util.getMD5(str).substring(8, 24);
	}

	/**
	 * 获取MD5的密码字符串
	 * 
	 * @param salt
	 * @param pwd
	 * @return
	 */
	public static String getMD5Pwd(final String salt, final String pwd) {
		return MD5Util.getMD5(salt + pwd);
	}

}
