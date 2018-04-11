Ext.define('App.view.user.UserView', {
	extend : 'App.config.BasePanel',
	requires : [ 'App.controller.user.UserViewController',
			'App.view.user.UserSearch', 'App.view.user.UserList' ],
	controller : 'userViewController',
	xtype : 'userView',
	id : 'userView',
	items : [ {
		xtype : 'userSearch'
	}, {
		xtype : 'userList'
	} ]
});