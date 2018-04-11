package com.shudailaoshi.entity.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.shudailaoshi.entity.base.BaseEntity;
import com.shudailaoshi.pojo.annotation.Comment;

/**
 * 资源
 * 
 * @author Liaoyifan
 *
 */
@Table(name = "sys_resource")
public class Resource extends BaseEntity {

	private static final long serialVersionUID = 7386769959503270784L;

	@Comment("资源名")
	private String resourceName;
	@Comment("权限路径")
	private String url;
	@Comment("图标")
	private String iconCls;
	@Comment("排序值")
	private Double sort;
	@Comment("是否菜单")
	private Boolean isMenu;
	@Comment("父Id")
	private Long parentId;

	@Transient
	@Comment("是否选中")
	private Boolean checked;
	@Transient
	@Comment("子节点")
	private Set<Resource> children = new HashSet<Resource>();

	public Resource() {
	}

	public Resource(Long id) {
		super.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Double getSort() {
		return sort;
	}

	public void setSort(Double sort) {
		this.sort = sort;
	}

	public Boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Set<Resource> getChildren() {
		return children;
	}

	public void setChildren(Set<Resource> children) {
		this.children = children;
	}

}
