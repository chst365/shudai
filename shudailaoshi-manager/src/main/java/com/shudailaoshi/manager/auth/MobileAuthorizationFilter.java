package com.shudailaoshi.manager.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.utils.SessionUtil;
import com.shudailaoshi.utils.TokenUtil;
import com.shudailaoshi.utils.ValidUtil;

public class MobileAuthorizationFilter extends AuthenticationFilter {

	@Autowired
	private RedisRepository redisRepository;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String accessToken = WebUtils.toHttp(request).getHeader(Constant.ACCESS_TOKEN);
		if (ValidUtil.isBlank(accessToken)) {
			return this.denied(response);
		}
		String accessTokenKey = TokenUtil.getAccessTokenKey(accessToken);
		if (!this.redisRepository.exist(accessTokenKey)) {
			return this.denied(response);
		}
		User user = (User) this.redisRepository.get(accessTokenKey);
		if (!user.getPermissions().contains((WebUtils.toHttp(request).getServletPath().substring(1)))) {
			WebUtils.toHttp(response).setStatus(Constant.AUTH_ERROR_STATUS);
			return false;
		}
		SessionUtil.setSessionAttribute(SessionUtil.IS_APP, true);
		SessionUtil.setSessionAttribute(Constant.CURRENT_USER, user);
		return true;
	}

	private boolean denied(ServletResponse response) {
		WebUtils.toHttp(response).setStatus(Constant.SESSION_ERROR_STATUS);
		return false;
	}
}
