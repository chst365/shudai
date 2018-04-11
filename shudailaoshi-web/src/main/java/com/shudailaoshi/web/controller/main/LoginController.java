package com.shudailaoshi.web.controller.main;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.MD5Util;
import com.shudailaoshi.utils.RSAUtil;
import com.shudailaoshi.utils.SessionUtil;
import com.shudailaoshi.utils.ShiroUtil;
import com.shudailaoshi.utils.TokenUtil;
import com.shudailaoshi.utils.ValidUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

/**
 * @author Liaoyifan
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisRepository redisRepository;

	@RequestMapping("app/index.html")
	public String appHtml() {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			return "redirect:/app/login.html";
		}
		return "/app/index";
	}

	@RequestMapping("app/login.html")
	public String appLoginHtml(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/app/index.html";
		}
		return "/login/app/login";
	}

	@RequestMapping("login.html")
	public String loginHtml(HttpServletRequest request) {
		return "/login/front/login";
	}

	/**
	 * 会员登录
	 * 
	 * @param user
	 * @param remember
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO login(User user, boolean remember, String imageCode, HttpServletRequest request) {
		UsernamePasswordToken usernamePasswordToken = null;
		try {
			String userName = user.getUserName();
			if (StringUtils.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			String errorPwdKey = Constant.PWD_ERROR_NUM + userName;
			int errorPwdNum = 0;
			if (this.redisRepository.exist(errorPwdKey)) {
				errorPwdNum = (Integer) this.redisRepository.get(errorPwdKey);
				if (errorPwdNum >= 3) {
					if (StringUtils.isBlank(imageCode)) {
						throw new ControllerException(ExceptionEnum.PWD_ERROR_TOO_MANY);
					}
					if (!this.redisRepository.exist(Constant.VALIDCODE + userName)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
					if (!((String) this.redisRepository.get(Constant.VALIDCODE + userName))
							.equalsIgnoreCase(imageCode)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
				}
			}
			String userPwd = user.getUserPwd();
			if (StringUtils.isBlank(userPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_NOT_NULL);
			}
			// 获取用户名密码
			userPwd = new String(RSAUtil.decryptByPrivateKey(user.getUserPwd(),
					SessionUtil.getSessionAttribute(Constant.PRIVATE_KEY).toString()));
			// 获取登录用户
			User loginUser = this.userService.get(new User(userName));
			if (loginUser == null) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			int status = loginUser.getStatus();
			if (status == StatusEnum.DELETE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			if (status == StatusEnum.FREEZE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_FREEZE);
			}
			// 登陆
			usernamePasswordToken = new UsernamePasswordToken(userName,
					MD5Util.getMD5Pwd(loginUser.getSalt(), userPwd));
			if (remember) {
				usernamePasswordToken.setRememberMe(true);
			}
			try {
				SecurityUtils.getSubject().login(usernamePasswordToken);
			} catch (AuthenticationException e) {
				this.redisRepository.set(errorPwdKey, errorPwdNum + 1, 86400L);
				throw new ControllerException(ExceptionEnum.USERPWD_ERROR, e);
			}
			if (errorPwdNum != 0) {
				this.redisRepository.delete(errorPwdKey);
			}
			return ResultUtil.success((String) SessionUtil.getSessionAttribute(Constant.REQUESTURL));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		} finally {
			if (usernamePasswordToken != null) {
				usernamePasswordToken.clear();
			}
		}
	}

	/**
	 * 员工登录
	 * 
	 * @param user
	 * @param remember
	 * @return
	 */
	@RequestMapping(value = "app/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO appLogin(User user, boolean remember, HttpServletRequest request) {
		UsernamePasswordToken usernamePasswordToken = null;
		try {
			String userName = user.getUserName();
			if (StringUtils.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			String userPwd = user.getUserPwd();
			if (StringUtils.isBlank(userPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_NOT_NULL);
			}
			// 获取用户名密码
			userPwd = new String(RSAUtil.decryptByPrivateKey(user.getUserPwd(),
					SessionUtil.getSessionAttribute(Constant.PRIVATE_KEY).toString()));
			// 获取登录用户
			User loginUser = this.userService.get(new User(userName));
			if (loginUser == null) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			int status = loginUser.getStatus();
			if (status == StatusEnum.DELETE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			if (status == StatusEnum.FREEZE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_FREEZE);
			}
			// 登陆
			usernamePasswordToken = new UsernamePasswordToken(userName,
					MD5Util.getMD5Pwd(loginUser.getSalt(), userPwd));
			if (remember) {
				usernamePasswordToken.setRememberMe(true);
			}
			try {
				SecurityUtils.getSubject().login(usernamePasswordToken);
			} catch (AuthenticationException e) {
				throw new ControllerException(ExceptionEnum.USERPWD_ERROR, e);
			}
			return ResultUtil.success((String) SessionUtil.getSessionAttribute(Constant.REQUESTURL));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		} finally {
			if (usernamePasswordToken != null) {
				usernamePasswordToken.clear();
			}
		}
	}

	/**
	 * 手机会员登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "m/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO mLogin(User user, String imageCode) {
		UsernamePasswordToken usernamePasswordToken = null;
		try {
			String userName = user.getUserName();
			if (StringUtils.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			String errorPwdKey = Constant.PWD_ERROR_NUM + userName;
			int errorPwdNum = 0;
			if (this.redisRepository.exist(errorPwdKey)) {
				errorPwdNum = (Integer) this.redisRepository.get(errorPwdKey);
				if (errorPwdNum >= 3) {
					if (StringUtils.isBlank(imageCode)) {
						throw new ControllerException(ExceptionEnum.PWD_ERROR_TOO_MANY);
					}
					if (!this.redisRepository.exist(Constant.VALIDCODE + userName)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
					if (!((String) this.redisRepository.get(Constant.VALIDCODE + userName))
							.equalsIgnoreCase(imageCode)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
				}
			}
			String userPwd = user.getUserPwd();
			if (StringUtils.isBlank(userPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_NOT_NULL);
			}
			// 获取登录用户
			User loginUser = this.userService.get(new User(userName));
			if (loginUser == null) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			int status = loginUser.getStatus();
			if (status == StatusEnum.DELETE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			if (status == StatusEnum.FREEZE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_FREEZE);
			}
			// 登陆
			usernamePasswordToken = new UsernamePasswordToken(userName,
					MD5Util.getMD5Pwd(loginUser.getSalt(), userPwd));
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(usernamePasswordToken);
			} catch (AuthenticationException e) {
				this.redisRepository.set(errorPwdKey, errorPwdNum + 1, 86400L);
				throw new ControllerException(ExceptionEnum.USERPWD_ERROR);
			}
			if (errorPwdNum != 0) {
				this.redisRepository.delete(errorPwdKey);
			}
			try {
				subject.checkRole(Constant.CUSTOMER);
			} catch (Exception e) {
				throw new ControllerException(ExceptionEnum.CUSTOMER_ONLY);
			}
			loginUser.setPermissions(ShiroUtil.getPermissions());
			subject.logout();
			// 返回登录成功
			return ResultUtil.success(this.userService.createToken(loginUser));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		} finally {
			if (usernamePasswordToken != null) {
				usernamePasswordToken.clear();
			}
		}
	}

	/**
	 * 手机员工登录
	 * 
	 * @param user
	 * @param imageCode
	 * @return
	 */
	@RequestMapping(value = "m/app/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO mAppLogin(User user, String imageCode) {
		UsernamePasswordToken usernamePasswordToken = null;
		try {
			String userName = user.getUserName();
			if (StringUtils.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			String errorPwdKey = Constant.PWD_ERROR_NUM + userName;
			int errorPwdNum = 0;
			if (this.redisRepository.exist(errorPwdKey)) {
				errorPwdNum = (Integer) this.redisRepository.get(errorPwdKey);
				if (errorPwdNum >= 3) {
					if (StringUtils.isBlank(imageCode)) {
						throw new ControllerException(ExceptionEnum.PWD_ERROR_TOO_MANY);
					}
					if (!this.redisRepository.exist(Constant.VALIDCODE + userName)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
					if (!((String) this.redisRepository.get(Constant.VALIDCODE + userName))
							.equalsIgnoreCase(imageCode)) {
						throw new ControllerException(ExceptionEnum.IMAGE_VALID_CODE_ERROR);
					}
				}
			}
			String userPwd = user.getUserPwd();
			if (StringUtils.isBlank(userPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_NOT_NULL);
			}
			// 获取登录用户
			User loginUser = this.userService.get(new User(userName));
			if (loginUser == null) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			int status = loginUser.getStatus();
			if (status == StatusEnum.DELETE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
			}
			if (status == StatusEnum.FREEZE.getValue()) {
				throw new ControllerException(ExceptionEnum.USER_FREEZE);
			}
			// 登陆
			usernamePasswordToken = new UsernamePasswordToken(userName,
					MD5Util.getMD5Pwd(loginUser.getSalt(), userPwd));
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(usernamePasswordToken);
			} catch (AuthenticationException e) {
				this.redisRepository.set(errorPwdKey, errorPwdNum + 1, 86400L);
				throw new ControllerException(ExceptionEnum.USERPWD_ERROR);
			}
			if (errorPwdNum != 0) {
				this.redisRepository.delete(errorPwdKey);
			}
			try {
				subject.checkRole(Constant.WAITER);
			} catch (Exception e) {
				throw new ControllerException(ExceptionEnum.EMPLOYEE_ONLY);
			}
			loginUser.setPermissions(ShiroUtil.getPermissions());
			subject.logout();
			// 返回登录成功
			return ResultUtil.success(this.userService.createToken(loginUser));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		} finally {
			if (usernamePasswordToken != null) {
				usernamePasswordToken.clear();
			}
		}
	}

	@RequestMapping(value = { "m/register", "register" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO register(User user, String checkUserPwd, String mobileCode, String referrerMobile,
			String inviteMobile) {
		UsernamePasswordToken usernamePasswordToken = null;
		try {
			String mobile = user.getMobile();
			if (mobile == null || !ValidUtil.isMobile(mobile)) {
				throw new ControllerException(ExceptionEnum.MOBILE_FORMAT_ERROR);
			}
			String userPwd = user.getUserPwd();
			if (userPwd == null || !ValidUtil.isPwd(userPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_FORMAT_ERROR);
			}
			if (ValidUtil.isBlank(checkUserPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_CHECK_NULL);
			}
			if (!userPwd.equals(checkUserPwd)) {
				throw new ControllerException(ExceptionEnum.USERPWD_EQUALS_ERROR);
			}
			String mobileCodeKey = Constant.VALIDCODE + mobile;
			if (!this.redisRepository.exist(mobileCodeKey)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_NOT_EXISTS);
			}
			if (!((String) this.redisRepository.get(mobileCodeKey)).equalsIgnoreCase(mobileCode)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_CODE_ERROR);
			}
			usernamePasswordToken = new UsernamePasswordToken(mobile, MD5Util.getMD5Pwd(user.getSalt(), userPwd));
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(usernamePasswordToken);
			} catch (AuthenticationException e) {
				throw new ControllerException(ExceptionEnum.USERPWD_ERROR);
			}
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping("app/logout")
	public String appLogout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/app/login.html";
	}

	@RequestMapping("logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/index.html";
	}

	@RequestMapping("m/logout")
	@ResponseBody
	public ResultVO mLogout(HttpServletRequest request) {
		try {
			String accessTokenKey = TokenUtil
					.getAccessTokenKey(WebUtils.toHttp(request).getHeader(Constant.ACCESS_TOKEN));
			this.userService.deleteToken(
					TokenUtil.getUserNameKey(((User) this.redisRepository.get(accessTokenKey)).getUserName()));
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}