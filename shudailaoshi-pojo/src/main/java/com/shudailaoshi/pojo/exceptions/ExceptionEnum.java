package com.shudailaoshi.pojo.exceptions;

public enum ExceptionEnum {

	/** 未知错误 **/
	UNKNOWN("未知错误,请联系管理员"),
	/** 异常测试 **/
	TEST("异常测试"),
	/** 参数不能为空 **/
	PARAMS_NOT_NULL("参数不能为空"),
	/** 资源名不能为空 **/
	TEXT_NOT_NULL("资源名不能为空"),
	/** 权限名不能为空 **/
	URL_NOT_NULL("权限名不能为空"),
	/** 用户名不能为空 **/
	USERNAME_NOT_NULL("用户名不能为空"), NULL_FILE("文件不能为空"),
	/** 已被使用无法删除 */
	USERD_NOT_ALLOW_DELETE("已被使用无法删除,可以尝试冻结,或清除使用关系后删除"),
	/** 不能提交空的订单 **/
	CAN_NOT_SUBMIT_NULL_ORDER("不能提交空的订单"),
	/** 购物车信息不能为空 **/
	CART_IDS_NOT_NULL("购物车信息不能为空"),
	/** 用户名应为2-22位(字母,数字)组成 **/
	USERNAME_FORMAT_ERROR("用户名应为2-22位(字母,数字)组成"),
	/** 短信发送失败 **/
	MESSAGE_SEND_ERROR("短信发送失败"),
	/** 密码长度应为6-22位 **/
	USERPWD_FORMAT_ERROR("密码长度应为6-22位"),
	/** 手机号不能为空 **/
	MOBILE_NOT_NULL("手机号不能为空"),
	/** 密码不能为空 **/
	USERPWD_NOT_NULL("密码不能为空"),
	/** 密码错误次数超过三次,请输入图片验证码 **/
	PWD_ERROR_TOO_MANY("密码错误次数超过三次,请输入图片验证码"),
	/** 用户密码错误 **/
	USERPWD_ERROR("密码错误"),
	/** 权限集获取错误 **/
	PERMITS_GET_ERROR("权限集获取错误"),
	/** 权限验证错误 **/
	PERMITTED_VALID_ERROR("权限验证错误"),
	/** 仅限员工登录 **/
	EMPLOYEE_ONLY("仅限服务员登录"),
	/** 仅限会员登录 **/
	CUSTOMER_ONLY("仅限会员登录"),
	/** 自己不能作为自己的父节点 **/
	PARENT_NOT_EQUAL_ID("自己不能作为自己的父节点"),
	/** 未经授权的访问 **/
	AUTHORIZATION("未经授权的访问"),
	/** 获取TOKEN错误 **/
	TOKEN_GET_ERROR("获取TOKEN错误"),
	/** 获取公钥错误 **/
	PUBLICKEY_GET_ERROR("获取公钥错误"),
	/** 获取分页错误 **/
	PAGE_GET_ERROR("获取分页错误"),
	/** 获取List错误 **/
	LIST_GET_ERROR("获取List错误"),
	/** 验证码获取失败 **/
	VALID_CODE_GET_ERROR("验证码获取失败"),
	/** 设备信息错误 **/
	APPID_INFO_ERROR("设备信息错误"),
	/** 图片验证码错误 **/
	IMAGE_VALID_CODE_ERROR("图片验证码错误"),
	/** 手机验证码错误 **/
	MOBILE_VALID_CODE_ERROR("手机验证码错误"),
	/** 图片验证码不存在 **/
	IMAGE_VALID_NOT_EXISTS("图片验证码不存在"),
	/** 手机验证码不存在 **/
	MOBILE_VALID_NOT_EXISTS("手机验证码不存在"),
	/** 手机号码格式错误 **/
	MOBILE_FORMAT_ERROR("手机号码格式错误"),
	/** 手机号码已经存在 **/
	MOBILE_EXISTS("手机号码已经存在"),
	/** 充值卡不存在 **/
	RECHARGE_NOT_EXISTS("充值卡不存在"),
	/** 洗车卡不存在 **/
	WASH_NOT_EXISTS("洗车卡不存在"),
	/** 确认密码不能为空 **/
	USERPWD_CHECK_NULL("确认密码不能为空"),
	/** 原密码错误 **/
	OLD_PWD_ERROR("原密码错误"),
	/** 密码和确认密码不相同 **/
	USERPWD_EQUALS_ERROR("密码和确认密码不相同"),
	/** 电子邮箱格式错误 **/
	EMAIL_FORMAT_ERROR("电子邮箱格式错误"),
	/** 用户不存在 **/
	USER_NOT_EXISTS("用户不存在"),
	/** 用户名已存在 **/
	USERNAME_EXISTED("用户名已存在"),
	/** 用户被冻结 **/
	USER_FREEZE("用户被冻结"),
	/** 该员工已经设置了账户 **/
	EMPLOYEE_USER_NAME_EXISTED("该员工已经设置了账户"),
	/** 资源名已存在 **/
	TEXT_EXISTED("资源名已存在"),
	/** 权限名存在 **/
	URL_EXISTED("权限名已存在"),
	/** 必须先删除该节点下的子节点 **/
	HAS_CHILD_NODE("必须先删除该节点下的子节点"),
	/** 超级管理员无须授权 **/
	ADMIN_NOT_NEED_GRANT("超级管理员无须授权"),
	/** 角色名已存在 **/
	ROLENAME_EXISTED("角色名已存在"),
	/** 团队名已经存在 **/
	TEAM_NAME_EXISTED("团队名已经存在"),
	/** 超级管理员不可删除 **/
	ADMIN_NOT_ALLOW_DELETE("超级管理员不可删除"),
	/** 超级管理员不可冻结 **/
	ADMIN_NOT_ALLOW_FREEZE("超级管理员不可冻结"),
	/** 含有已经结算的记录 */
	HAD_LIQUIDATION_ERROR("含有已经结算的记录"),
	/** 账户状态异常 */
	ACCOUNT_STATE_EXCEPTION("账户状态异常"),
	/** refresh_token不存在或已过期 */
	REFRESH_TOKEN_NOT_EXISTS("REFRESH_TOKEN不存在或已过期"),
	/** token不存在或已过期 */
	TOKEN_NOT_EXISTS("TOKEN不存在或已过期"),
	/** 账户状态异常 */
	ACCOUNT_STATAUS_EXCEPTION("账户状态异常"),
	/** ACCESS_TOKEN参数不存在 */
	ACCESS_TOKEN_NOT_EXISTS("ACCESS_TOKEN参数不存在");

	private final String code;
	private final String msg;

	private ExceptionEnum(String msg) {
		this.code = this.name().toLowerCase();
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
