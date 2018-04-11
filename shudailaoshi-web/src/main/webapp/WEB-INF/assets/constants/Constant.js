/**
 * Constant
 */
(function(window) {
	var Constant = {
		VERSION : 'v4.0.1',
		/** 令牌名* */
		SUBMIT_TOKEN : 'SUBMIT_TOKEN',
		/** 获取令牌URL* */
		URL_SUBMIT_TOKEN : 'getSubmitToken',
		/** 获取公钥URL* */
		URL_PUBLICKEY : 'getPublicKey',
		/** 透明图片* */
		IMG_TRANSPARENT : 'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==',
		/** 超级管理员 * */
		ADMIN : 'admin',
		CUSTOMER : 'customer',
		WAITER : 'waiter',
		FRANCHISEE : 'franchisee',
		/** 资源树root * */
		ROOT : 1,
		/** store默认一页大小 * */
		PAGESIZE : 20,
		/** 图标下拉框一页大小 * */
		ICON_PAGESIZE : 100,
		/** 重置的用户名密码 * */
		USER_RESETPWD : 123456,
		/** 城市CODE * */
		CITY_CODE : "CITY_CODE",
		/** 城市名称 * */
		CITY_NAME : "CITY_NAME",
		/** 当前经度 * */
		CITY_LON : "CITY_LON",
		/** 当前纬度 * */
		CITY_LAT : "CITY_LAT",
		/** 城市默认CODE * */
		CITY_CODE_DEFAULT : 317,
		/** 城市默认NAME * */
		CITY_NAME_DEFAULT : "无锡市",
		/** 城市默认NAME * */
		COUNTY_NAME_DEFAULT : "崇安区",
		/** 城市默认经度 * */
		CITY_LON_DEFAULT : 120.310679,
		/** 城市默认纬度 * */
		CITY_LAT_DEFAULT : 31.837425
	};
	window.Constant = Constant;
})(window);
