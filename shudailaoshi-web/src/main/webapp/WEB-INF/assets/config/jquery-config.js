$.ajaxSetup({
	timeout : 50000,// 50秒
	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
	error : function(xhr, status, e) {
		switch (xhr.status) {
		case 999:
			layer.msg('您的操作频率过快，请稍后重试');
			BaseUtil.getSubmitToken();
			break;
		case 403:
			layer.msg('您没有访问[' + xhr.getResponseHeader("RequestUrl")
					+ ']的权限，请联系管理员');
			break;
		case 401:
			location.href = basePath + '/login.html?referer=' + xhr.getResponseHeader("Referer");
			break;
		case 404:
			layer.msg('404 Not Found，请联系管理员');
			break;
		case 405:
			layer.msg('405 Method Not Allowed，请联系管理员');
			break;
		case 500:
			layer.msg('500 Internal Server Error，请联系管理员');
			break;
		case 0:
			layer.msg('服务器连接错误，请检查网络后重试');
			break;
		default:
			layer.msg('未知错误，请联系管理员');
			break;
		}
	},
	complete : function(xhr, status) {
		if (status === 'timeout') {
			alert('您的请求已超时，请稍后重试');
		}
	}
});
/**
 * 初始化token
 */
require.sync(['BaseUtil-Jquery','Constant', 'CookieUtil']);
BaseUtil.getSubmitToken();
/**
 * 序列化form
 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}