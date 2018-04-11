package com.shudailaoshi.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.pojo.annotation.Comment;

/**
 * 所以的数据结构都从基类派生
 * 
 * @author Liaoyifan
 */
public abstract class ReadOnlyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("主键")
	protected Long id;
	@Comment("创建时间")
	protected Long createTime;
	@Comment(value = "状态", clazz = StatusEnum.class)
	protected Integer status;

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
