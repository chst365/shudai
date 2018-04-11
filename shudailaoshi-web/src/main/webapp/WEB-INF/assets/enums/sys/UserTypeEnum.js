/**
 * UserTypeEnum
 */
(function(window) {
	var UserTypeEnum = {
		EMPLOYEE : {
			getText : function() {
				return '员工';
			},
			getValue : function() {
				return 0;
			}
		},
		CUSTOMER : {
			getText : function() {
				return '客户';
			},
			getValue : function() {
				return 1;
			}
		},
		getHtml : function(value) {
			switch (value) {
			case 0:
				return '<font color=blue>员工</font>';
			case 1:
				return '<font color=darkviolet>客户</font>';
			}
		}
	};
	window.UserTypeEnum = UserTypeEnum;
})(window);