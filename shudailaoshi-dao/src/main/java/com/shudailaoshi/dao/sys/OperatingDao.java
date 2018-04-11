package com.shudailaoshi.dao.sys;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.pojo.query.sys.OperatingQuery;

public interface OperatingDao extends BaseDao<Operating> {

	/**
	 * 操作日志集合查询
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
	List<Operating> listOperating(@Param("operatingQuery") OperatingQuery operatingQuery, @Param("start") Integer start, @Param("limit") Integer limit);

	/**
	 * 操作日志总量查询
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param operatingQuery
	 * @return
	 */
	long countOperating(@Param("operatingQuery") OperatingQuery operatingQuery);

}