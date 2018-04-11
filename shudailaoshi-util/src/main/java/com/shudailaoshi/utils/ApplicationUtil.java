package com.shudailaoshi.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Liaoyifan
 *
 */
public class ApplicationUtil implements ApplicationContextAware {

	private static ApplicationContext APPLICATIONCONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		APPLICATIONCONTEXT = applicationContext;
	}

	public static Object getBean(Class<?> clazz) {
		return APPLICATIONCONTEXT.getBean(clazz);
	}

}