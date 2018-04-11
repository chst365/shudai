package com.shudailaoshi.dao.${moduleName};

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.${moduleName}.${entityName};
import com.shudailaoshi.pojo.query.${moduleName}.${entityName}Query;

public interface ${entityName}Dao extends BaseDao<${entityName}> {

	/**
	 * ${serviceName}集合查询
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
	List<${entityName}> list${entityName}(@Param("${packageName}Query") ${entityName}Query ${packageName}Query, @Param("start") Integer start, @Param("limit") Integer limit);

	/**
	 * ${serviceName}总量查询
	 * 
	 * @createAuthor ${generatorName}
	 * @createDate ${currentDate}
	 * @modifyAuthor ${generatorName}
	 * @modifyDate ${currentDate}
	 * @param ${packageName}Query
	 * @return
	 */
	long count${entityName}(@Param("${packageName}Query") ${entityName}Query ${packageName}Query);

}