package com.shudailaoshi.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.utils.CookieUtil;
import com.shudailaoshi.utils.SessionUtil;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 检查token
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	private boolean checkToken(HttpServletRequest request) {
		String serverToken = String.valueOf(SessionUtil.getSessionAttribute(Constant.SUBMIT_TOKEN));
		if (StringUtils.isBlank(serverToken)) {
			return false;
		}
		String clinetToken = CookieUtil.get(request, Constant.SUBMIT_TOKEN);
		if (clinetToken == null) {
			return false;
		}
		if (!serverToken.equals(clinetToken)) {
			return false;
		}
		return true;
	}

	/**
	 * 预处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			if (request.getMethod().equals(Constant.POST)) {
				if (!checkToken(request)) {
					response.setStatus(Constant.TOKEN_ERROR_STATUS);
					return false;
				}
				SessionUtil.removeSessionAttribute(Constant.SUBMIT_TOKEN);
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

}
