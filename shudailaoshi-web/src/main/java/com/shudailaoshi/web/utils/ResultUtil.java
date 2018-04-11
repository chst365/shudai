package com.shudailaoshi.web.utils;

import java.io.Serializable;

import org.apache.logging.log4j.Logger;

import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.vos.common.ResultVO;

/**
 * 
 * @author Liaoyifan
 *
 */
public class ResultUtil {

	public final static String SUCCESS_MSG = "操作成功";

	private ResultUtil() {
	}

	public static ResultVO success() {
		return new ResultVO(true, null, SUCCESS_MSG);
	}

	public static ResultVO success(final Serializable data) {
		return new ResultVO(true, data, null, SUCCESS_MSG);
	}

	public static ResultVO success(final Serializable data, final String msg) {
		return new ResultVO(true, data, null, msg);
	}

	public static ResultVO error(final Logger log, final Exception e) {
		log.info(e.getMessage(), e);
		if (e instanceof ControllerException) {
			return new ResultVO(false, ((ControllerException) e).getCode(), e.getMessage());
		}
		if (e instanceof ServiceException) {
			return new ResultVO(false, ((ServiceException) e).getCode(), e.getMessage());
		}
		return new ResultVO(false, ExceptionEnum.UNKNOWN.getCode(), ExceptionEnum.UNKNOWN.getMsg());
	}

}
