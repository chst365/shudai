package com.shudailaoshi.pojo.query.base;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.shudailaoshi.pojo.vos.common.SortVo;

public abstract class BaseQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String startDate;// 开始日期
	protected String endDate;// 结束日期
	protected Integer status;

	protected SortVo sort;

	public SortVo getSort() {
		return sort;
	}

	public void setSort(String sort) {
		if (StringUtils.isNotBlank(sort)) {
			this.sort = JSONArray.parseArray(sort, SortVo.class).get(0);
		}
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
