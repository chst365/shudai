Ext.define('App.view.user.UserWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.user.UserViewController',
			'App.viewmodel.user.UserWindowViewModel' ],
	controller : 'userViewController',
	xtype : 'userWindow',
	id : 'userWindow',
	viewModel : 'userWindowViewModel',
	buttons : [ {
		text : '保存',
		action : 'save'
	}, {
		text : '关闭',
		handler : function() {
			this.up('window').close();
		}
	} ],
	items : [ {
		xtype : 'baseWindowForm',
		items : [ {
			xtype : 'hidden',
			name : 'id'
		}, {
			name : 'userName',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '用户名',
			enforceMaxLength : true,
			maxLength : 22,
			emptyText : '请输入2至22位用户名',
			validator : function(value) {
				if (ValidUtil.isBlank(value))
					return '用户名不能为空';
				if (!ValidUtil.isUserName(value))
					return '用户名应为2-22位(字母,数字)组成';
				return true;
			}
		}, {
			name : 'mobile',
			fieldLabel : '手机号码',
			enforceMaxLength : true,
			maxLength : 11,
			validator : function(value) {
				if (ValidUtil.isBlank(value))
					return true;
				if (!ValidUtil.isMobile(ValidUtil.trim(value)))
					return '手机号码格式错误';
				return true;
			}
		}, {
			name : 'email',
			fieldLabel : '电子邮箱',
			validator : function(value) {
				if (ValidUtil.isBlank(value))
					return true;
				if (!ValidUtil.isEmail(ValidUtil.trim(value)))
					return '电子邮箱格式错误';
				return true;
			}
		} ]
	} ]
});