package com.shudailaoshi.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertUtil {

	private ConvertUtil() {
	}

	/**
	 * @param String[]
	 * @return Set<Long>
	 */
	public static Set<Long> convertSet(String[] strs) {
		Set<Long> result = new HashSet<Long>();
		for (String s : strs) {
			result.add(Long.parseLong(s));
		}
		return result;
	}

	/**
	 * @param String[]
	 * @return List<Long>
	 */
	public static List<Long> convertList(String[] strs) {
		List<Long> result = new ArrayList<Long>();
		for (String s : strs) {
			result.add(Long.parseLong(s));
		}
		return result;
	}

}
