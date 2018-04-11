(function(window){
	var ValidUtil = {
		//trim
		trim : function(text) {
			return text == null ? '' : (text + '').replace(
					/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
		},
		// 是空?
		isBlank : function(value) {
			return this.trim(value) ? false : true;
		},
		// 不为空?
		isNotBlank : function(value) {
			return !this.isBlank(value);
		},
		// 是手机?
		isMobile : function(value) {
			var reg = /^1[0-9]{10}$/;
			return reg.test(value);
		},
		// 是电话?
		isTel : function(value) {
			var reg = /^(0[1-9]{2})-\d{8}$|^(0[1-9]{3}-(\d{7,8}))$/;
			return reg.test(value);
		},
		// 是邮箱?
		isEmail : function(value) {
			var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			return reg.test(value);
		},
		// 是用户名(字母,数字2-22位)?
		isUserName : function(value) {
			var reg = /^[A-Za-z0-9]{2,22}$/;
			return reg.test(value);
		},
		// 是密码(任意字符6-22位)?
		isPwd : function(value) {
			var reg = /^.{6,22}$/;
			return reg.test(value);
		},
		// 是中文?
		isChinese : function(value) {
			var reg = /^[\u4e00-\u9fa5]+$/;
			return reg.test(value);
		},
		// 是数值?
		isNumber : function(value) {
			return !isNaN(value);
		},
		// 是整数?
		isInt : function(value) {
			var reg = /^\d*$/;
			return reg.test(value);
		},
	    isVehicleNumber : function(value) {
	      var result = false;
	      if (value.length == 7){
	        var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
	        result = express.test(value);
	      }
	      return result;
	    },
		// input只能输入Double
		onlyDouble : function(dom) {
			this.addEvent(dom, 'input', function() {
				var reg = this.value.match(/\d+\.?\d{0,2}/);
				reg ? this.value = reg[0] : this.value = '';
			});
		},
		// input只能输入Int
		onlyInt : function(dom) {
			this.addEvent(dom, 'input', function() {
				var reg = this.value.match(/\d*/);
				reg ? this.value = reg[0] : this.value = '';
			});
		},
		// 添加事件
		addEvent : function(object, event, handler, remove) {
			if (typeof object != 'object' || typeof handler != 'function')
				return;
			try {
				object[remove ? 'removeEventListener' : 'addEventListener'](event,
						handler, false);
			} catch (e) {
				var xc = '_' + event;
				object[xc] = object[xc] || [];
				if (remove) {
					var l = object[xc].length;
					for (var i = 0; i < l; i++) {
						if (object[xc][i].toString() === handler.toString())
							object[xc].splice(i, 1);
					}
				} else {
					var l = object[xc].length;
					var exists = false;
					for (var i = 0; i < l; i++) {
						if (object[xc][i].toString() === handler.toString())
							exists = true;
					}
					if (!exists)
						object[xc].push(handler);
				}
				object['on' + event] = function() {
					var l = object[xc].length;
					for (var i = 0; i < l; i++) {
						object[xc][i].apply(object, arguments);
					}
				}
			}
		},
		// 移除事件
		removeEvent : function(object, event, handler) {
			this.addEvent(object, event, handler, true);
		}
	};
	window.ValidUtil = ValidUtil;
})(window);