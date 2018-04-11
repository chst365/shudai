package com.shudailaoshi.manager.auth;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.utils.AjaxUtil;
import com.shudailaoshi.utils.SessionUtil;

public class WebAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {
        Subject subject = super.getSubject(request, response);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        boolean isLogin = subject.isAuthenticated() || subject.isRemembered();
        if (!isLogin) {
            return false;
        }
        if (httpServletRequest.getMethod().equals(Constant.POST)) {
            String servletPath = httpServletRequest.getServletPath().substring(1);
            if (subject.hasRole(Constant.ADMIN) || subject.isPermitted(servletPath)) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = super.getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            HttpServletResponse hResponse = WebUtils.toHttp(response);
            if (AjaxUtil.isAjax(WebUtils.toHttp(request))) {
                hResponse.setHeader(Constant.REQUESTURL, WebUtils.toHttp(request).getServletPath().substring(1));
                hResponse.setStatus(Constant.AUTH_ERROR_STATUS);
            } else {
                hResponse.sendError(Constant.AUTH_ERROR_STATUS);
            }
        } else {
            if (AjaxUtil.isAjax(WebUtils.toHttp(request))) {
                HttpServletResponse hResponse = WebUtils.toHttp(response);
                hResponse.setHeader(Constant.REFERER, WebUtils.toHttp(request).getHeader(Constant.REFERER));
                hResponse.setStatus(Constant.SESSION_ERROR_STATUS);
            } else {
                SessionUtil.setSessionAttribute(Constant.REQUESTURL, WebUtils.toHttp(request).getServletPath());
                super.saveRequestAndRedirectToLogin(request, response);
            }
        }
        return false;
    }

}
