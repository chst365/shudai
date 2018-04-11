package com.shudailaoshi.pojo.vos.common;

public enum MsgTypeEnum {

	/**
	 * N分钟后，发送短信提醒
	 */
	SMS_REMIND_MSG_TYPE(1, "短信提醒"),
	/**
	 * N分钟后，系统自动取消未支付的洗车服务订单
	 */
	BESPEAK_CANCEL_MSG_TYPE(2, "自动取消未支付洗车服务订单"),
	/**
	 * N分钟后，自动取消充值卡未支付
	 */
	CANCEL_RECHARGE_TYPE(3, "自动取消充值卡未支付"),
	/**
	 * N分钟后，消息推送
	 */
	JPUSH_REMIND_MSG_TYPE(4, "推送提醒"),
	/**
	 * N分钟后，短信、推送等 提醒会员洗车
	 */
	BESPEAK_WASH_MSG_TYPE(5, "洗车预到点提醒"),
	/**
	 * N分钟后，自动取消洗车卡未支付
	 */
	CANCEL_WASH_TYPE(6, "自动取消洗车卡未支付"),
	/**
	 * N分钟后，服务订单仍未接单，系统给后台管理员，发送短信
	 */
	BESPEAK_NOT_MEET_MSG_TYPE(7, "服务订单仍未接单，系统给后台管理员，发送短信"), EMPLOYEE_MEET_MSG(8, "洗车订单预约时间提前10分钟提醒服务人员");

	private final String text;
	private final int value;

	private MsgTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public int getValue() {
		return value;
	}
}
