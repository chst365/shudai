package com.shudailaoshi.service.base.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.shudailaoshi.dao.base.BaseDao;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.service.base.BaseService;

import tk.mybatis.mapper.entity.Example;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private BaseDao<T> baseDao;

	@Override
	public final int save(T record) {
		return baseDao.save(record);
	}

	@Override
	public final int saveSelective(T record) {
		return baseDao.saveSelective(record);
	}

	@Override
	public final int saveList(List<T> recordList) {
		return baseDao.saveList(recordList);
	}

	@Override
	public final int saveOrUpdate(T record) {
		T t = this.getByPrimaryKey(record);
		return t != null ? this.updateByPrimaryKey(record) : this.save(record);
	}

	@Override
	public final int saveOrUpdateSelective(T record) {
		T t = this.getByPrimaryKey(record);
		return t != null ? this.updateByPrimaryKeySelective(record) : this.saveSelective(record);
	}

	@Override
	public final int remove(T record) {
		return baseDao.remove(record);
	}

	@Override
	public final int removeByPrimaryKey(Object key) {
		return baseDao.removeByPrimaryKey(key);
	}

	@Override
	public final int removeByExample(Example example) {
		return baseDao.removeByExample(example);
	}

	@Override
	public final int updateByPrimaryKey(T record) {
		return baseDao.updateByPrimaryKey(record);
	}

	@Override
	public final int updateByPrimaryKeySelective(T record) {
		return baseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public final int updateByExample(T record, Example example) {
		return baseDao.updateByExample(record, example);
	}

	@Override
	public final int updateByExampleSelective(T entity, Example example) {
		return baseDao.updateByExampleSelective(entity, example);
	}

	@Override
	public final T getByPrimaryKey(Object key) {
		return baseDao.getByPrimaryKey(key);
	}

	@Override
	public final T get(T record) {
		return baseDao.get(record);
	}

	@Override
	public final T getByExample(Example example) {
		return baseDao.getByExample(example);
	}

	@Override
	public final long getCount(T record) {
		return baseDao.getCount(record);
	}

	@Override
	public final long getCountByExample(Example example) {
		return baseDao.getCountByExample(example);
	}

	@Override
	public final List<T> list(T record) {
		return baseDao.list(record);
	}

	@Override
	public final List<T> listAll() {
		return baseDao.listAll();
	}

	@Override
	public final List<T> list(T record, int start, int limit) {
		return baseDao.listByRowBounds(record, new RowBounds(start, limit));
	}

	@Override
	public final List<T> listByExample(Example example) {
		return baseDao.listByExample(example);
	}

	@Override
	public final List<T> listByExample(Example example, int start, int limit) {
		return baseDao.listByExampleAndRowBounds(example, new RowBounds(start, limit));
	}

	@Override
	public final PageVO page(T record, int start, int limit) {
		return new PageVO(start, limit, this.getCount(record), this.list(record, start, limit));
	}

	@Override
	public final PageVO pageByExample(Example example, int start, int limit) {
		return new PageVO(start, limit, this.getCountByExample(example), this.listByExample(example, start, limit));
	}

}