package com.shudailaoshi.web.controller.sys;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.sys.Role;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.query.sys.RoleQuery;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.pojo.vos.common.SortVo;
import com.shudailaoshi.service.sys.RoleService;
import com.shudailaoshi.utils.ValidUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Liaoyifan
 * @date 2016年8月21日
 */
@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	/**
	 * 分页查询
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public ResultVO page(RoleQuery roleQuery, int start, int limit) {
		try {
			Example example = new Example(Role.class);
			Criteria criteria = example.createCriteria();
			String roleName = roleQuery.getRoleName();
			if (ValidUtil.isNotBlank(roleName)) {
				criteria.andLike("roleName", "%" + roleName + "%");
			}
			SortVo sortVo = roleQuery.getSort();
			if(sortVo!=null) {
				if (sortVo.getDirection().equalsIgnoreCase("DESC")) {
					example.orderBy(sortVo.getProperty()).desc();
				}
				example.orderBy(sortVo.getProperty()).asc();
			}
			return ResultUtil.success(this.roleService.pageByExample(example, start, limit));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 获取全部角色集合
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<Role> list() {
		Role role = new Role();
		role.setStatus(StatusEnum.NORMAL.getValue());
		return this.roleService.list(role);
	}

	/**
	 * 获取用户角色集合
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param userId
	 * @return
	 */
	@RequestMapping("listIdByUserId")
	@ResponseBody
	public List<Long> listIdByUserId(long userId) {
		return this.roleService.listIdByUserId(userId);
	}

	/**
	 * 角色授权
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @param resourceIds
	 * @return
	 */
	@Comment("角色授权")
	@RequestMapping("grant")
	@ResponseBody
	public ResultVO grant(long id, @RequestParam(name = "resourceIds", required = false) Set<Long> resourceIds) {
		try {
			this.roleService.grant(id, resourceIds);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 添加角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param role
	 * @return
	 */
	@Comment("添加角色")
	@RequestMapping("save")
	@ResponseBody
	public ResultVO save(Role role) {
		try {
			this.roleService.saveRole(role);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 编辑角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param role
	 * @return
	 */
	@Comment("编辑角色")
	@RequestMapping("updateRole")
	@ResponseBody
	public ResultVO updateRole(Role role) {
		try {
			this.roleService.updateRole(role.getId(), role);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 删除角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("删除角色")
	@RequestMapping(value = "removeRole", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO removeRole(long id) {
		try {
			this.roleService.removeRole(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 冻结角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("冻结角色")
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO freeze(long id) {
		try {
			this.roleService.freeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 解冻角色
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@Comment("解冻角色")
	@RequestMapping(value = "unfreeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO unfreeze(long id) {
		try {
			this.roleService.unfreeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}