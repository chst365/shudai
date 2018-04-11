Ext.define('App.view.role.RoleGrantWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.role.RoleViewController',
			'App.view.role.RoleGrantList' ],
	controller : 'roleViewController',
	xtype : 'roleGrantWindow',
	id : 'roleGrantWindow',
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
		xtype : 'roleGrantList'
	} ]
});