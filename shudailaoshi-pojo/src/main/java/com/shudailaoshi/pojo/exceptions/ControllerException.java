package com.shudailaoshi.pojo.exceptions;

public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = 9037367946419199794L;

	private static final String CONTROLLER_EXCEPTION_DEFAULT = "controller_exception_default";

	public final String code;
	public final String msg;

	public ControllerException(String msg) {
		super(msg);
		this.code = CONTROLLER_EXCEPTION_DEFAULT;
		this.msg = msg;
	}

	public ControllerException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public ControllerException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMsg();
	}

	public ControllerException(ExceptionEnum exceptionEnum, Exception e) {
		super(exceptionEnum.getMsg(), e);
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMsg();
	}

	public ControllerException(String msg, Exception e) {
		super(msg, e);
		this.code = CONTROLLER_EXCEPTION_DEFAULT;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
