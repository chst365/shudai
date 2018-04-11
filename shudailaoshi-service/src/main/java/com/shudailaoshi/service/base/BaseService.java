package com.shudailaoshi.service.base;

import java.util.List;

import com.shudailaoshi.pojo.vos.common.PageVO;

import tk.mybatis.mapper.entity.Example;

/**
 * BaseService
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 *
	 * @param record
	 * @return
	 */
	int save(T record);

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 *
	 * @param record
	 * @return
	 */
	int saveSelective(T record);

	/**
	 * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 *
	 * @param recordList
	 * @return
	 */
	int saveList(List<T> recordList);

	/**
	 * 保存或更新一个实体，null的属性会保存，会更新null值，不会使用数据库默认值
	 *
	 * @param record
	 * @return
	 */
	int saveOrUpdate(T record);

	/**
	 * 保存或更新一个实体，null的属性不会保存，不会更新null值，会使用数据库默认值
	 *
	 * @param record
	 * @return
	 */
	int saveOrUpdateSelective(T record);

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 *
	 * @param record
	 * @return
	 */
	int remove(T record);

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 *
	 * @param key
	 * @return
	 */
	int removeByPrimaryKey(Object key);

	/**
	 * 根据Example条件删除数据
	 *
	 * @param example
	 * @return
	 */
	int removeByExample(Example example);

	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(T record);

	/**
	 * 根据主键更新属性不为null的值
	 *
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
	 *
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExample(T entity, Example example);

	/**
	 * 根据Example条件更新实体`record`包含的不是null的属性值
	 *
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExampleSelective(T entity, Example example);

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 *
	 * @param key
	 * @return
	 */
	T getByPrimaryKey(Object key);

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 *
	 * @param record
	 * @return
	 */
	T get(T record);

	/**
	 * 根据Example条件获取一个对象,有多个的时候只取第一个
	 *
	 * @param record
	 * @return
	 */
	T getByExample(Example example);

	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 *
	 * @param record
	 * @return
	 */
	long getCount(T record);

	/**
	 * 根据Example条件进行查询总数
	 *
	 * @param example
	 * @return
	 */
	long getCountByExample(Example example);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 *
	 * @param record
	 * @return
	 */
	List<T> list(T entity);

	/**
	 * 查询全部结果
	 *
	 * @return
	 */
	List<T> listAll();

	/**
	 * 根据实体属性和RowBounds进行分页查询
	 *
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 */
	List<T> list(T record, int start, int limit);

	/**
	 * 根据Example条件进行查询
	 *
	 * @param example
	 * @return
	 */
	List<T> listByExample(Example example);

	/**
	 * 根据example条件和RowBounds进行分页查询
	 *
	 * @param example
	 * @param start
	 * @param limit
	 * @return
	 */
	List<T> listByExample(Example example, int start, int limit);

	/**
	 * 根据实体属性获取分页vo
	 * 
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 */
	PageVO page(T record, int start, int limit);

	/**
	 * 根据example条件获取分页vo
	 * 
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 */
	PageVO pageByExample(Example example, int start, int limit);

}