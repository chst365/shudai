Ext.define('App.view.role.RoleWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.role.RoleViewController'],
	controller : 'roleViewController',
	xtype : 'roleWindow',
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
			name : 'roleName',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '角色名',
			emptyText : '请输入角色名',
			allowBlank : false
		}, {
			name : 'description',
			fieldLabel : '描述'
		} ]
	} ]
});