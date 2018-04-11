package com.shudailaoshi.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shudailaoshi.dao.sys.OperatingDao;
import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.query.sys.OperatingQuery;
import com.shudailaoshi.pojo.vos.common.PageVO;
import com.shudailaoshi.service.base.impl.BaseServiceImpl;
import com.shudailaoshi.service.sys.OperatingService;
import com.shudailaoshi.utils.DateUtil;

@Service("operatingService")
public class OperatingServiceImpl extends BaseServiceImpl<Operating> implements OperatingService {

	@Autowired
	private OperatingDao operatingDao;

	@Override
	public PageVO pageOperating(OperatingQuery operatingQuery, Integer start, Integer limit) {
		return new PageVO(start, limit, this.operatingDao.countOperating(operatingQuery),
				this.operatingDao.listOperating(operatingQuery, start, limit));
	}

	@Override
	public void saveOperating(Operating operating) {
		long time = DateUtil.getTime();
		operating.setCreateTime(time);
		this.operatingDao.save(operating);
	}

	@Override
	public void updateOperating(Operating operating) {
		this.operatingDao.updateByPrimaryKeySelective(operating);
	}

	@Override
	public void removeOperating(long id) {
		try {
			this.operatingDao.removeByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException(ExceptionEnum.USERD_NOT_ALLOW_DELETE);
		}
	}

	@Override
	public void freeze(long id) {
		Operating operating = new Operating();
		operating.setId(id);
		this.operatingDao.updateByPrimaryKeySelective(operating);
	}

	@Override
	public void unfreeze(long id) {
		Operating operating = new Operating();
		operating.setId(id);
		this.operatingDao.updateByPrimaryKeySelective(operating);
	}

}
