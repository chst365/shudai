package com.shudailaoshi.web.controller.${moduleName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shudailaoshi.entity.${moduleName}.${entityName};
import com.shudailaoshi.pojo.query.${moduleName}.${entityName}Query;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.vos.common.ResultVO;
import com.shudailaoshi.service.${moduleName}.${entityName}Service;
import com.shudailaoshi.web.controller.base.BaseController;
import com.shudailaoshi.web.utils.ResultUtil;

@RequestMapping("${moduleName}/${packageName}")
@Controller
public class ${entityName}Controller extends BaseController {

	@Autowired
	private ${entityName}Service ${packageName}Service;

	/**
	 * 查询${serviceName}
 	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
 	 * @param ${packageName}Query
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page${entityName}")
	@ResponseBody
	public ResultVO page${entityName}(${entityName}Query ${packageName}Query, Integer start, Integer limit) {
		try {
			return ResultUtil.success(this.${packageName}Service.page${entityName}(${packageName}Query, start, limit));
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 新增${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param ${packageName}
	 * @return
	 */
	@Comment("新增${serviceName}")
	@RequestMapping(value = "save${entityName}", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO save${entityName}(${entityName} ${packageName}) {
		try {
			this.${packageName}Service.save${entityName}(${packageName});
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}
	
	/**
	 * 更新${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param ${packageName}
	 * @return
	 */
	@Comment("更新${serviceName}")
	@RequestMapping(value = "update${entityName}", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO update${entityName}(${entityName} ${packageName}) {
		try {
			this.${packageName}Service.update${entityName}(${packageName});
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 删除${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 * @return
	 */
	@Comment("删除${serviceName}")
	@RequestMapping(value = "remove${entityName}", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO remove${entityName}(long id) {
		try {
			this.${packageName}Service.remove${entityName}(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 冻结
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 * @return
	 */
	@Comment("冻结${serviceName}")
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO freeze(long id) {
		try {
			this.${packageName}Service.freeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

	/**
	 * 解冻${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 * @return
	 */
	@Comment("解冻${serviceName}")
	@RequestMapping(value = "unfreeze", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO unfreeze(long id) {
		try {
			this.${packageName}Service.unfreeze(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(log, e);
		}
	}

}