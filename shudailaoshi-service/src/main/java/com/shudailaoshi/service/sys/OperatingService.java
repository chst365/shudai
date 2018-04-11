package com.shudailaoshi.service.sys;

import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.pojo.query.sys.OperatingQuery;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.service.base.BaseService;

public interface OperatingService extends BaseService<Operating> {

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
	PageVO pageOperating(OperatingQuery operatingQuery, Integer start, Integer limit);

	/**
	 * 新增操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param operating
	 */
	void saveOperating(Operating operating);
	
	/**
	 * 更新操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param operating
	 */
	void updateOperating(Operating operating);

	/**
	 * 删除操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 */
	void removeOperating(long id);

	/**
	 * 冻结操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 */
	void freeze(long id);

	/**
	 * 解冻操作日志
	 * 
	 * @createAuthor liaoyifan
	 * @createDate 2018-04-08
	 * @modifyAuthor liaoyifan
	 * @modifyDate 2018-04-08
	 * @param id
	 */
	void unfreeze(long id);

}
