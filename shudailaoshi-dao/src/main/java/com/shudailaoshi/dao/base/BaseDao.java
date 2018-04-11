package com.shudailaoshi.dao.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * BaseDao
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 * @param <T>
 */
public interface BaseDao<T> extends BaseMapper<T>, RowBoundsMapper<T>, ExampleMapper<T>, InsertListMapper<T> {

}