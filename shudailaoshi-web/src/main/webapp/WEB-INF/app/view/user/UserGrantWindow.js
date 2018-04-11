Ext.define('App.view.user.UserGrantWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'Ext.ux.form.ItemSelector',
			'App.controller.user.UserViewController'],
	controller : 'userViewController',
	xtype : 'userGrantWindow',
	id:'userGrantWindow',
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
		xtype : 'form',
		margin : 5,
		layout : 'fit',
		items : [ {
			xtype : 'hidden',
			name : 'userId',
		}, {
			xtype : 'itemselector',
			name : 'roleIds',
			displayField : 'roleName',
			valueField : 'id',
			store : Ext.create('App.store.user.UserGrantStore')
		} ]
	} ]
});