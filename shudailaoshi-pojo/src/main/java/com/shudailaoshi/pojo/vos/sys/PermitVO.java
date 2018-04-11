package com.shudailaoshi.pojo.vos.sys;

import java.io.Serializable;

public class PermitVO implements Serializable {

	private static final long serialVersionUID = -7696780141753940558L;

	private String resourceName;
	private String url;
	private String iconCls;

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

}
