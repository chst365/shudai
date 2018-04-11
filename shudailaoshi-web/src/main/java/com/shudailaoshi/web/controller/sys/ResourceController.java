package com.shudailaoshi.web.controller.sys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ControllerException;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.pojo.vos.sys.IconVO;
import com.shudailaoshi.pojo.vos.sys.PermitVO;
import com.shudailaoshi.service.sys.ResourceService;
import com.shudailaoshi.utils.PropertiesUtil;
import com.shudailaoshi.utils.ShiroUtil;
import com.shudailaoshi.utils.ValidUtil;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

/**
 * ResourceController
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
@RequestMapping("sys/resource")
@Controller
public class ResourceController extends BaseController {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 获取菜单
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @return
	 */
	@RequestMapping("listMenu")
	@ResponseBody
	public Resource listMenu() {
		return this.resourceService.listMenu();
	}

	/**
	 * 获取资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Resource list() {
		return this.resourceService.listResource();
	}

	/**
	 * 获取授权资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param roleId
	 * @return
	 */
	@RequestMapping("listGrant")
	@ResponseBody
	public Resource listGrant(long roleId) {
		return this.resourceService.listGrant(roleId);
	}

	/**
	 * 获取权限集
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @return
	 */
	@RequestMapping("listPermit")
	@ResponseBody
	public List<PermitVO> listPermit() {
		if (SecurityUtils.getSubject().hasRole(Constant.ADMIN)) {
			return this.resourceService.listPermit(null);
		}
		Set<String> permits = ShiroUtil.getPermissions();
		if (ValidUtil.isEmpty(permits)) {
			return null;
		}
		return this.resourceService.listPermit(permits);
	}

	/**
	 * 获取图标
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("pageIcon")
	@ResponseBody
	public PageVO pageIcon(HttpServletRequest request, int start, int limit) {
		File[] files = new File(request.getServletContext()
				.getRealPath(PropertiesUtil.getConfigProperty("resource.path") + "/assets/icon/icons")).listFiles();
		int filesLength = files.length;
		int st = start, li = start + limit;
		if (li > filesLength) {
			li = filesLength;
		}
		List<IconVO> fileNames = new ArrayList<IconVO>();
		for (int i = st; i < li; i++) {
			String name = files[i].getName();
			fileNames.add(new IconVO("/assets/icon/icons/" + name, "icon_" + name.substring(0, name.indexOf("."))));
		}
		return new PageVO(start, limit, Long.valueOf(filesLength), fileNames);
	}

	/**
	 * 更新资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param resource
	 * @return
	 */
	@Comment("更新资源")
	@RequestMapping(value = "updateResource", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO updateResource(Resource resource) {
		try {
			this.checkSave(resource);
			if (resource.getParentId() == null) {
				throw new ControllerException(ExceptionEnum.PARAMS_NOT_NULL);
			}
			this.resourceService.updateResource(resource.getId(), resource);
			return ResultUtil.success(resource);
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 新增资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param resource
	 * @return
	 */
	@Comment("新增资源")
	@RequestMapping(value = "saveFrist", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO saveFrist(Resource resource) {
		try {
			this.checkSave(resource);
			resource.setParentId(1L);
			this.resourceService.saveResource(resource);
			return ResultUtil.success(resource);
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 新增子资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param resource
	 * @return
	 */
	@Comment("新增子资源")
	@RequestMapping(value = "saveChild", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO saveChild(Resource resource) {
		try {
			this.checkSave(resource);
			if (resource.getParentId() == null) {
				throw new ControllerException(ExceptionEnum.PARAMS_NOT_NULL);
			}
			this.resourceService.saveResource(resource);
			return ResultUtil.success(resource);
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 检查 resource
	 * 
	 * @param resource
	 */
	private void checkSave(Resource resource) {
		if (resource == null) {
			throw new ControllerException(ExceptionEnum.PARAMS_NOT_NULL);
		}
		if (ValidUtil.isBlank(resource.getResourceName())) {
			throw new ControllerException(ExceptionEnum.TEXT_NOT_NULL);
		}
		if (ValidUtil.isBlank(resource.getUrl())) {
			throw new ControllerException(ExceptionEnum.URL_NOT_NULL);
		}
	}

	/**
	 * 冻结资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO freeze(long id) {
		try {
			this.resourceService.freeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 解冻资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "unfreeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO unfreeze(long id) {
		try {
			this.resourceService.unfreeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 删除资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeResource", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO removeResource(long id) {
		try {
			return ResultUtil.success(this.resourceService.removeResource(id));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 移动资源
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2016年8月21日
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2016年8月21日
	 * @param id
	 * @param parentId
	 * @return
	 */
	@Comment("移动资源")
	@RequestMapping(value = "move", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO move(long id, long parentId) {
		try {
			if (id == parentId) {
				throw new ControllerException(ExceptionEnum.PARENT_NOT_EQUAL_ID);
			}
			return ResultUtil.success(this.resourceService.move(id, parentId));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}
