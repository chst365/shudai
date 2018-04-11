package com.shudailaoshi.pojo.vos.common;

import java.io.Serializable;
import java.util.List;

/**
 * 分页功能中的一页的信息
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public class PageVO implements Serializable {

	private static final long serialVersionUID = -1989502047097419546L;

	private Integer start; // 开始条数
	private Integer limit; // 结束条数
	private Long total; // 总记录数
	private List<?> items; // 本页的数据列表

	public PageVO(Integer start, Integer limit, Long total, List<?> items) {
		this.start = start;
		this.limit = limit;
		this.total = total;
		this.items = items;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

}
