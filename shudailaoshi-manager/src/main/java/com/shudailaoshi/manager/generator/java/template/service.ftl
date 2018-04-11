package com.shudailaoshi.service.${moduleName};

import com.shudailaoshi.entity.${moduleName}.${entityName};
import com.shudailaoshi.pojo.query.${moduleName}.${entityName}Query;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.service.base.BaseService;

public interface ${entityName}Service extends BaseService<${entityName}> {

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
	PageVO page${entityName}(${entityName}Query ${packageName}Query, Integer start, Integer limit);

	/**
	 * 新增${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param ${packageName}
	 */
	void save${entityName}(${entityName} ${packageName});
	
	/**
	 * 更新${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param ${packageName}
	 */
	void update${entityName}(${entityName} ${packageName});

	/**
	 * 删除${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 */
	void remove${entityName}(long id);

	/**
	 * 冻结${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 */
	void freeze(long id);

	/**
	 * 解冻${serviceName}
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param id
	 */
	void unfreeze(long id);

}
