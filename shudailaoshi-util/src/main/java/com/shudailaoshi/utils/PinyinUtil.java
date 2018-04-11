package com.shudailaoshi.utils;

/**
 * 拼音工具类
 * 
 * @author Liaoyifan
 */
public class PinyinUtil {

	private static int BEGIN = 45217;
	private static int END = 63486;
	private static char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪',
			'期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', };
	private static int[] table = new int[27];
	private static char[] initialtable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z', };

	private PinyinUtil() {
	}

	// 初始化
	static {
		for (int i = 0; i < 26; i++) {
			table[i] = gb2312(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
		}
		table[26] = END;// 区间表结尾
	}

	/**
	 * 获取字符串第一个字的首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringFirstLetter(String str) {
		return getPinyinFirstLetter(str.substring(0, 1));
	}

	/**
	 * 获取字符串每个字的首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinyinFirstLetter(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append(getInitialtable(str.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * 获取首字母
	 * 
	 * @param ch
	 * @return
	 */
	private static char getInitialtable(char ch) {
		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;
		int gb = gb2312(ch);// 汉字转换首字母
		if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回
			return ch;
		int i;
		for (i = 0; i < 26; i++) {
			if ((gb >= table[i]) && (gb < table[i + 1]))
				break;
		}

		if (gb == END) {
			i = 25;
		}
		return initialtable[i]; // 在码表区间中，返回首字母
	}

	private static int gb2312(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

}