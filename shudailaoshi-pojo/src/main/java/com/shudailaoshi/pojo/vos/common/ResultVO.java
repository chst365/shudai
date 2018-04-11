package com.shudailaoshi.pojo.vos.common;

import java.io.Serializable;

public class ResultVO implements Serializable {

	private static final long serialVersionUID = -4197220673915438442L;

	private boolean success;
	private Object data;
	private String code;
	private String msg;

	public ResultVO() {
	}

	public ResultVO(boolean success, Object data, String code, String msg) {
		this.success = success;
		this.data = data;
		this.code = code;
		this.msg = msg;
	}

	public ResultVO(boolean success, String code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public ResultVO(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultVO(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
