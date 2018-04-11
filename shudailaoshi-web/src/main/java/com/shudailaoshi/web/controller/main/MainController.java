package com.shudailaoshi.web.controller.main;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.CodeUtil;
import com.shudailaoshi.utils.DateUtil;
import com.shudailaoshi.utils.EncodeUtil;
import com.shudailaoshi.utils.MD5Util;
import com.shudailaoshi.utils.RSAUtil;
import com.shudailaoshi.utils.SessionUtil;
import com.shudailaoshi.utils.ValidUtil;
import com.shudailaoshi.utils.VerifyCodeUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

/**
 * 
 * @author Liaoyifan
 *
 */
@Controller
public class MainController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisRepository redisRepository;
	@Value("${versionFilePath}")
	private String versionFilePath;

	@RequestMapping("index.html")
	public String indexHtml() {
		return "redirect:/app/index.html";
	}

	/**
	 * 通用controller
	 */
	@RequestMapping("/**/*.html")
	public String fontView(HttpServletRequest request) {
		return "front/views" + request.getServletPath().replaceAll(".html", "");
	}

	/**
	 * 获取getSubmitToken
	 * 
	 * @return
	 */
	@RequestMapping("getSubmitToken")
	@ResponseBody
	public String getSubmitToken() {
		final String token = UUID.randomUUID().toString();
		SessionUtil.setSessionAttribute(Constant.SUBMIT_TOKEN, token);
		return token;
	}

	/**
	 * 刷新refreshToken
	 * 
	 * @return
	 */
	@RequestMapping("m/refreshToken")
	@ResponseBody
	public ResultVO mRefreshToken(HttpServletRequest request) {
		try {
			String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
			if (ValidUtil.isBlank(refreshToken)) {
				throw new ControllerException(ExceptionEnum.REFRESH_TOKEN_NOT_EXISTS);
			}
			return ResultUtil.success(this.userService.refreshToken(refreshToken));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 获取公有密钥
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("getPublicKey")
	@ResponseBody
	public String getPublicKey() {
		Map<String, Object> keyMap = RSAUtil.genKeyPair();
		SessionUtil.setSessionAttribute(Constant.PRIVATE_KEY, RSAUtil.getPrivateKey(keyMap));
		return RSAUtil.getPublicKey(keyMap);
	}

	/**
	 * 验证权限
	 * 
	 * @param permittedUrl
	 * @return
	 */
	@RequestMapping("isPermitted")
	@ResponseBody
	public boolean isPermitted(String permittedUrl) {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole(Constant.ADMIN) ? true : subject.isPermitted(permittedUrl);
	}

	@RequestMapping({ "imageCode", "m/imageCode" })
	public void imageCode(String mobile, HttpServletResponse response) {
		try {
			if (ValidUtil.isBlank(mobile)) {
				throw new ControllerException(ExceptionEnum.MOBILE_NOT_NULL);
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 生成随机字串
			String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
			// 存入
			redisRepository.set(Constant.VALIDCODE + mobile, verifyCode, 1800L);
			// 生成图片
			int w = 200, h = 80;
			VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			throw new ControllerException(ExceptionEnum.VALID_CODE_GET_ERROR);
		}
	}

	private User checkExistsUser(String mobile) {
		User search = new User();
		search.setMobile(mobile);
		User user = this.userService.get(search);
		if (user == null) {
			throw new ControllerException(ExceptionEnum.USER_NOT_EXISTS);
		}
		return user;
	}

	@RequestMapping(value = { "web/front/validSms" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO validSms(String mobile, String mobileCode) {
		try {
			this.checkExistsUser(mobile);
			String mobileCodeKey = Constant.VALIDCODE + mobile;
			if (!this.redisRepository.exist(mobileCodeKey)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_NOT_EXISTS);
			}
			if (!((String) this.redisRepository.get(mobileCodeKey)).equalsIgnoreCase(mobileCode)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_CODE_ERROR);
			}
			this.redisRepository.delete(mobileCodeKey);
			SessionUtil.setSessionAttribute(Constant.CHANGE_PWD_MOBILE, mobile);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	@RequestMapping(value = { "m/front/validSms" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO mValidSms(String mobile, String mobileCode) {
		try {
			this.checkExistsUser(mobile);
			String mobileCodeKey = Constant.VALIDCODE + mobile;
			if (!this.redisRepository.exist(mobileCodeKey)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_NOT_EXISTS);
			}
			if (!((String) this.redisRepository.get(mobileCodeKey)).equalsIgnoreCase(mobileCode)) {
				throw new ControllerException(ExceptionEnum.MOBILE_VALID_CODE_ERROR);
			}
			this.redisRepository.delete(mobileCodeKey);
			this.redisRepository.set(Constant.CHANGE_PWD_MOBILE + ":" + mobile, mobile, 1800L);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	@RequestMapping(value = { "web/front/changePwd" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO changePwd(String userPwd, String checkUserPwd) {
		try {
			String mobile = (String) SessionUtil.getSessionAttribute(Constant.CHANGE_PWD_MOBILE);
			if (mobile == null) {
				throw new ControllerException(ExceptionEnum.MOBILE_NOT_NULL);
			}
			if (!userPwd.equals(checkUserPwd)) {
				throw new ServiceException(ExceptionEnum.USERPWD_EQUALS_ERROR);
			}
			User user = this.checkExistsUser(mobile);
			String salt = CodeUtil.getUniqueCode();
			user.setSalt(salt);
			user.setUserPwd(MD5Util.getMD5Pwd(salt, userPwd));
			user.setModifyTime(DateUtil.getTime());
			this.userService.updateByPrimaryKey(user);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	@RequestMapping(value = { "m/front/changePwd" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO changePwd(String mobile, String userPwd, String checkUserPwd) {
		try {
			if (!this.redisRepository.exist(Constant.CHANGE_PWD_MOBILE + ":" + mobile)) {
				throw new ControllerException(ExceptionEnum.MOBILE_NOT_NULL);
			}
			if (!userPwd.equals(checkUserPwd)) {
				throw new ServiceException(ExceptionEnum.USERPWD_EQUALS_ERROR);
			}
			User user = this.checkExistsUser(mobile);
			String salt = CodeUtil.getUniqueCode();
			user.setSalt(salt);
			user.setUserPwd(MD5Util.getMD5Pwd(salt, userPwd));
			user.setModifyTime(DateUtil.getTime());
			this.userService.updateByPrimaryKey(user);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	@RequestMapping(value = { "getInviteLink", "m/getInviteLink" }, method = RequestMethod.GET)
	@ResponseBody
	public ResultVO getInvitelink(HttpServletRequest request) {
		try {
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			return ResultUtil.success(basePath + "/invite/link.html?invite="
					+ EncodeUtil.base64UrlSafeEncode(super.getCurrentUser().getMobile().getBytes()));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}