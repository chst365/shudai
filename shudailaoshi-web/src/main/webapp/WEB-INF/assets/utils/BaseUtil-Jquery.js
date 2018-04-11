/**
 * @author Liaoyifan BaseUtil工具类
 */
(function(window) {
	var BaseUtil = {
		get : function(url, success, data) {
			$.ajax({
				url : basePath + '/' + url,
				async : true,
				cache : false,
				type : 'GET',
				data : data,
				dataType : 'JSON',
				success : function(result, textStatus, jqXHR) {
					if (success)
						success(result);
				}
			});
		},
		getData : function(url, async, data) {
			var data;
			$.ajax({
				url : basePath + '/' + url,
				async : async === undefined ? false : async,
				cache : false,
				type : 'GET',
				data : data,
				dataType : 'JSON',
				success : function(result, textStatus, jqXHR) {
					data = result;
				}
			});
			return data;
		},
		getTextData : function(url, async, data) {
			var data;
			$.ajax({
				url : basePath + '/' + url,
				async : false,
				cache : false,
				type : 'GET',
				data : data,
				dataType : 'TEXT',
				success : function(result, textStatus, jqXHR) {
					data = result;
				}
			});
			return data;
		},
		post : function(url, success, data) {
			$.ajax({
				url : basePath + '/' + url,
				async : true,
				cache : false,
				type : 'POST',
				data : data,
				dataType : 'JSON',
				success : function(result, textStatus, jqXHR) {
					if (success) {
						try {
							success(result);
						} catch (e) {
							console.error(e);
						}
					}
					BaseUtil.getSubmitToken();
				}
			});
		},
		getSubmitToken : function() {
			CookieUtil.set(Constant.SUBMIT_TOKEN, this.getTextData(Constant.URL_SUBMIT_TOKEN));
		},
		setFormValues : function(dom, data) {
			$(dom).find('[name]').each(function() {
				var type = $(this)[0].nodeName.toLowerCase();
				var name = $(this).attr('name');
				$(type + "[name='" + name + "']").val(data['' + name + '']);
			});
		},
		loginRedirect : function(url) {
			if(!this.getData('isLogin').data){
				$("#hidden_main_signin").click();
				return;
			}
			location.href = basePath + '/' + url;
		},
		isLogin : function() {
			if(!this.getData('isLogin').data){
				$("#hidden_main_signin").click();
				return false;
			}
			return true;
		}
	};
	window.BaseUtil = BaseUtil;
})(window);
