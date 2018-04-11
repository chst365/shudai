/**
 * SexEnum
 */
(function(window) {
	var SexEnum = {
		WOMAN : {
			getText : function() {
				return '女';
			},
			getValue : function() {
				return 0;
			}
		},
		MAN : {
			getText : function() {
				return '男';
			},
			getValue : function() {
				return 1;
			}
		},
		getHtml : function(value) {
			switch (value) {
			case 0:
				return '<font color=red>女</font>';
			case 1:
				return '<font color=blue>男</font>';
			}
		}
	};
	window.SexEnum = SexEnum;
})(window);