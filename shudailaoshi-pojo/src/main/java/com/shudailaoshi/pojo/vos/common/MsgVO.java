package com.shudailaoshi.pojo.vos.common;

import java.io.Serializable;

public class MsgVO implements Serializable {

	private static final long serialVersionUID = -3560100276142938715L;

	// 业务参数
	private String param;
	// 业务类型 MsgEnum
	private Integer type;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
