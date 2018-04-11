package com.shudailaoshi.pojo.vos.base;

import java.io.Serializable;

public abstract class BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;
	protected Long createTime;
	protected Long modifyTime;
	protected Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
