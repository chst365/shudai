package com.shudailaoshi.pojo.vos.sys;

import java.io.Serializable;

public class IconVO implements Serializable {

	private static final long serialVersionUID = 2214569845672911369L;

	private String name;
	private String value;

	public IconVO(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
