package com.shudailaoshi.web.controller.sys;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.dao.redis.RedisRepository;
import com.shudailaoshi.entity.sys.User;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.query.sys.UserQuery;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.sys.UserService;
import com.shudailaoshi.utils.CodeUtil;
import com.shudailaoshi.utils.DateUtil;
import com.shudailaoshi.utils.MD5Util;
import com.shudailaoshi.utils.ValidUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

/**
 * @author Liaoyifan
 * @date 2016年8月21日
 */
@RequestMapping({ "sys/user", "m/sys/user" })
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisRepository redisRepository;
	@Value("${user.resetPwd}")
	private String resetPwd;

	/**
	 * 分页查询
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param userQuery
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public ResultVO page(UserQuery userQuery, int start, int limit) {
		try {
			return ResultUtil.success(this.userService.page(userQuery, start, limit));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 保存用户
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param user
	 * @return
	 */
	@Comment("保存用户")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO saveUser(User user/* , String userPwdRe */) {
		try {
			// 验证
			if (user == null) {
				throw new ControllerException(ExceptionEnum.PARAMS_NOT_NULL);
			}
			// 用户名
			String userName = user.getUserName();
			if (ValidUtil.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			if (!ValidUtil.isUserName(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_FORMAT_ERROR);
			}
			// 手机
			String mobile = user.getMobile();
			if (ValidUtil.isNotBlank(mobile)) {
				if (!ValidUtil.isMobile(mobile)) {
					throw new ControllerException(ExceptionEnum.MOBILE_FORMAT_ERROR);
				}
			} else {
				user.setMobile(null);
			}
			// 邮箱
			String email = user.getEmail();
			if (ValidUtil.isNotBlank(email)) {
				if (!ValidUtil.isEmail(email)) {
					throw new ControllerException(ExceptionEnum.EMAIL_FORMAT_ERROR);
				}
			} else {
				user.setEmail(null);
			}
			user.setUserPwd(resetPwd);
			this.userService.saveUser(user);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 更新用户
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param user
	 * @return
	 */
	@Comment("更新用户")
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO updateUser(User user) {
		try {
			// 验证
			if (user == null) {
				throw new ControllerException(ExceptionEnum.PARAMS_NOT_NULL);
			}
			// 用户名
			String userName = user.getUserName();
			if (ValidUtil.isBlank(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_NOT_NULL);
			}
			if (!ValidUtil.isUserName(userName)) {
				throw new ControllerException(ExceptionEnum.USERNAME_FORMAT_ERROR);
			}
			// 手机
			String mobile = user.getMobile();
			if (ValidUtil.isNotBlank(mobile)) {
				if (!ValidUtil.isMobile(mobile)) {
					throw new ControllerException(ExceptionEnum.MOBILE_FORMAT_ERROR);
				}
			} else {
				user.setMobile(null);
			}
			// 邮箱
			String email = user.getEmail();
			if (ValidUtil.isNotBlank(email)) {
				if (!ValidUtil.isEmail(email)) {
					throw new ControllerException(ExceptionEnum.EMAIL_FORMAT_ERROR);
				}
			} else {
				user.setEmail(null);
			}
			this.userService.updateUser(user);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 重置密码
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("重置密码")
	@RequestMapping(value = "resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO resetPwd(long id) {
		try {
			this.userService.resetPwd(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 删除用户
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("删除用户")
	@RequestMapping(value = "removeUser", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO removeUser(long id) {
		try {
			this.userService.removeUser(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 冻结用户
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("冻结用户")
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO freeze(long id) {
		try {
			this.userService.freeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 解冻用户
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("解冻用户")
	@RequestMapping(value = "unfreeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO unfreeze(long id) {
		try {
			this.userService.unfreeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 授予角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@Comment("授予角色")
	@RequestMapping(value = "grant", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO grant(long userId, String roleIds) {
		try {
			Set<Long> roleSet = null;
			if (ValidUtil.isNotBlank(roleIds)) {
				roleSet = new HashSet<Long>();
				String[] roleArr = roleIds.split(",");
				for (String roleId : roleArr) {
					roleSet.add(Long.valueOf(roleId));
				}
			}
			this.userService.grant(userId, roleSet);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 获取用户名
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUserName", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO getUserName(long id) {
		try {
			return ResultUtil.success(userService.getByPrimaryKey(id).getUserName());
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param oldPwd
	 * @param newPwd
	 * @param newPwdRe
	 * @return
	 */
	@Comment("修改密码")
	@RequestMapping(value = { "changePwd", "front/changePwd" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVO changePwd(String oldPwd, String newPwd, String newPwdRe) {
		try {
			User user = super.getCurrentUser();
			String salt = user.getSalt();
			if (!MD5Util.getMD5Pwd(salt, oldPwd).equals(user.getUserPwd())) {
				throw new ControllerException(ExceptionEnum.OLD_PWD_ERROR);
			}
			if (!newPwd.equals(newPwdRe)) {
				throw new ControllerException(ExceptionEnum.USERPWD_EQUALS_ERROR);
			}
			String newSalt = CodeUtil.getUniqueCode();
			user.setSalt(newSalt);
			user.setUserPwd(MD5Util.getMD5Pwd(newSalt, newPwd));
			user.setModifyTime(DateUtil.getTime());
			this.userService.updateByPrimaryKey(user);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param oldPwd
	 * @param newPwd
	 * @param newPwdRe
	 * @param code
	 * @return
	 */
	@Comment("修改密码")
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO updatePwd(String oldPwd, String newPwd, String newPwdRe, String code) {
		try {
			String mobileCodeKey = Constant.VALIDCODE + getCurrentUser().getMobile();
			if (!((String) this.redisRepository.get(mobileCodeKey)).equalsIgnoreCase(code)) {
				throw new ControllerException("验证码错误");
			}
			this.userService.changePwd(getCurrentUser().getId(), oldPwd, newPwd, newPwdRe);
			redisRepository.delete(mobileCodeKey);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}

	}

}