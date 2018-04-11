/**
 * StatusEnum
 */
(function(window) {
	var StatusEnum = {
		NORMAL : {
			getText : function() {
				return '正常';
			},
			getValue : function() {
				return 0;
			}
		},
		FREEZE : {
			getText : function() {
				return '冻结';
			},
			getValue : function() {
				return 1;
			}
		},
		DELETE : {
			getText : function() {
				return '删除';
			},
			getValue : function() {
				return 2;
			}
		},
		getHtml : function(value) {
			switch (value) {
			case 0:
				return '<font color=green>正常</font>';
			case 1:
				return '<font color=red>冻结</font>';
			case 2:
				return '<font color=gray>删除</font>';
			}
		}
	};
	window.StatusEnum = StatusEnum;
})(window);
