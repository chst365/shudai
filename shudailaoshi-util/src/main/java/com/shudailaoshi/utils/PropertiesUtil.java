package com.shudailaoshi.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.shudailaoshi.utils.exceptions.UtilException;
import com.shudailaoshi.utils.exceptions.UtilExceptionEnum;

/**
 * 
 * @author Liaoyifan
 * @date 2016年8月8日
 *
 */
public class PropertiesUtil {

	private static final String CONFIG = "config.properties";

	private PropertiesUtil() {
	}

	public static String getConfigProperty(final String key) {
		InputStream input = null;
		try {
			input = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG);
			Properties properties = new Properties();
			properties.load(input);
			return properties.getProperty(key);
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.PROPERTIES_READ_ERROR, e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static String getProperty(final String filePath, final String key) {
		InputStream input = null;
		try {
			input = FileUtils.openInputStream(new File(filePath));
			Properties properties = new Properties();
			properties.load(input);
			return properties.getProperty(key);
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.PROPERTIES_READ_ERROR, e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static Map<String, String> getPropertys(final String filePath) {
		InputStream input = null;
		try {
			input = FileUtils.openInputStream(new File(filePath));
			Properties properties = new Properties();
			properties.load(input);
			Enumeration<?> enumeration = properties.propertyNames();
			Map<String, String> map = new HashMap<String, String>();
			while (enumeration.hasMoreElements()) {
				String key = String.valueOf(enumeration.nextElement());
				String value = properties.getProperty(key);
				map.put(key, value);
			}
			return map;
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.PROPERTIES_READ_ERROR, e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static void write(final String filePath, final String key, final String value) {
		OutputStream output = null;
		try {
			output = FileUtils.openOutputStream(new File(filePath));
			Properties properties = new Properties();
			properties.setProperty(key, value);
			properties.store(output, DateUtil.getTimeString());
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.PROPERTIES_WRITE_ERROR, e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	public static void write(final String filePath, final Map<String, String> keyValues) {
		OutputStream output = null;
		try {
			output = FileUtils.openOutputStream(new File(filePath));
			Properties properties = new Properties();
			if (keyValues != null && !keyValues.isEmpty()) {
				for (Entry<String, String> entry : keyValues.entrySet())
					properties.setProperty(entry.getKey(), entry.getValue());
			}
			properties.store(output, DateUtil.getTimeString());
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.PROPERTIES_WRITE_ERROR, e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

}