package com.shudailaoshi.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.pojo.query.sys.OperatingQuery;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.sys.OperatingService;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

@RequestMapping("sys/operating")
@Controller
public class OperatingController extends BaseController {

	@Autowired
	private OperatingService operatingService;

	/**
	 * 查询操作日志
 	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
 	 * @param operatingQuery
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("pageOperating")
	@ResponseBody
	public ResultVO pageOperating(OperatingQuery operatingQuery, Integer start, Integer limit) {
		try {
			return ResultUtil.success(this.operatingService.pageOperating(operatingQuery, start, limit));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 新增操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param operating
	 * @return
	 */
	@Comment("新增操作日志")
	@RequestMapping(value = "saveOperating", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO saveOperating(Operating operating) {
		try {
			this.operatingService.saveOperating(operating);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}
	
	/**
	 * 更新操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param operating
	 * @return
	 */
	@Comment("更新操作日志")
	@RequestMapping(value = "updateOperating", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO updateOperating(Operating operating) {
		try {
			this.operatingService.updateOperating(operating);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 删除操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 * @return
	 */
	@Comment("删除操作日志")
	@RequestMapping(value = "removeOperating", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO removeOperating(long id) {
		try {
			this.operatingService.removeOperating(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 冻结
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 * @return
	 */
	@Comment("冻结操作日志")
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO freeze(long id) {
		try {
			this.operatingService.freeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 解冻操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 * @return
	 */
	@Comment("解冻操作日志")
	@RequestMapping(value = "unfreeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO unfreeze(long id) {
		try {
			this.operatingService.unfreeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}