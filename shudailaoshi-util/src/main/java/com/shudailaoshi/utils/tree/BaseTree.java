package com.shudailaoshi.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liu
 *
 */
public class BaseTree {
	private String id;// 编号
	private String parentId;// 父节点
	private String text;// 文本
	private int hierarchy;// 层级
	private boolean leaf;// 是否叶子
	private String url;// 地址
	private String icon;// 图标
	private String xtype;// 类型
	private boolean expanded;
	private String qtip;// 节点提示信息
	private String qtitle;// 提示标题
	private String description;
	private String cls;
	private String iconCls;

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getQtitle() {
		return qtitle;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	private List<BaseTree> children = new ArrayList<BaseTree>();

	public BaseTree() {
	}

	public BaseTree(String id, String parentId, String text) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
	}

	public BaseTree(String id, String parentId, String text, int hierarchy, boolean leaf, String url,
			String icon) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.hierarchy = hierarchy;
		this.leaf = leaf;
		this.url = url;
		this.icon = icon;
	}

	public void addChild(BaseTree treeNode) {
		getChildren().add(treeNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<BaseTree> getChildren() {
		return children;
	}

	public void setChildren(List<BaseTree> children) {
		this.children = children;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", parentId=" + parentId + ", text=" + text + ", hierarchy=" + hierarchy
				+ ", leaf=" + leaf + ", url=" + url + ", icon=" + icon + ", xtype=" + xtype + ", expanded=" + expanded
				+ ", qtip=" + qtip + ", qtitle=" + qtitle + ", children=" + children + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
