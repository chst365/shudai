package com.shudailaoshi.pojo.exceptions;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -5032304295666446732L;

	private static final String SERVICE_EXCEPTION_DEFAULT = "service_extption_default";

	public final String code;
	public final String msg;

	public ServiceException(String msg) {
		super(msg);
		this.code = SERVICE_EXCEPTION_DEFAULT;
		this.msg = msg;
	}

	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public ServiceException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMsg();
	}

	public ServiceException(ExceptionEnum exceptionEnum, Exception e) {
		super(exceptionEnum.getMsg(), e);
		this.code = exceptionEnum.getCode();
		this.msg = exceptionEnum.getMsg();
	}

	public ServiceException(String msg, Exception e) {
		super(msg, e);
		this.code = SERVICE_EXCEPTION_DEFAULT;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
