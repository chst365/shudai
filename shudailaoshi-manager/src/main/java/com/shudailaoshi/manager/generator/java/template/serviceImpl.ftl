package com.shudailaoshi.service.${moduleName}.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shudailaoshi.dao.${moduleName}.${entityName}Dao;
import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.${moduleName}.${entityName};
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.query.${moduleName}.${entityName}Query;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.service.base.impl.BaseServiceImpl;
import com.shudailaoshi.service.${moduleName}.${entityName}Service;
import com.shudailaoshi.utils.DateUtil;

@Service("${packageName}Service")
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}> implements ${entityName}Service {

	@Autowired
	private ${entityName}Dao ${packageName}Dao;

	@Override
	public PageVO page${entityName}(${entityName}Query ${packageName}Query, Integer start, Integer limit) {
		return new PageVO(start, limit, this.${packageName}Dao.count${entityName}(${packageName}Query), this.${packageName}Dao.list${entityName}(${packageName}Query, start, limit));
	}

	@Override
	public void save${entityName}(${entityName} ${packageName}) {
		long time = DateUtil.getTime();
		${packageName}.setCreateTime(time);
		${packageName}.setModifyTime(time);
		${packageName}.setStatus(StatusEnum.NORMAL.getValue());
		this.${packageName}Dao.save(${packageName});
	}
	
	@Override
	public void update${entityName}(${entityName} ${packageName}) {
		${packageName}.setModifyTime(DateUtil.getTime());
		this.${packageName}Dao.updateByPrimaryKeySelective(${packageName});
	}

	@Override
	public void remove${entityName}(long id) {
		try {
			this.${packageName}Dao.removeByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException(ExceptionEnum.USERD_NOT_ALLOW_DELETE);
		}
	}
	
	@Override
	public void freeze(long id) {
		${entityName} ${packageName} = new ${entityName}();
		${packageName}.setId(id);
		${packageName}.setModifyTime(DateUtil.getTime());
		${packageName}.setStatus(StatusEnum.FREEZE.getValue());
		this.${packageName}Dao.updateByPrimaryKeySelective(${packageName});
	}

	@Override
	public void unfreeze(long id) {
		${entityName} ${packageName} = new ${entityName}();
		${packageName}.setId(id);
		${packageName}.setModifyTime(DateUtil.getTime());
		${packageName}.setStatus(StatusEnum.NORMAL.getValue());
		this.${packageName}Dao.updateByPrimaryKeySelective(${packageName});
	}

}
