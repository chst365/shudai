package com.shudailaoshi.web.controller.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.utils.SessionUtil;

/**
 * BaseController
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public abstract class BaseController {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 得到当前登录用户信息
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER);
	}

}