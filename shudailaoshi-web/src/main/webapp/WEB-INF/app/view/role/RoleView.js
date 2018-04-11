Ext.define('App.view.role.RoleView', {
	extend : 'App.config.BasePanel',
	requires : [ 'App.controller.role.RoleViewController',
			'App.view.role.RoleSearch', 'App.view.role.RoleList' ],
	controller : 'roleViewController',
	xtype : 'roleView',
	id : 'roleView',
	items : [ {
		xtype : 'roleSearch'
	}, {
		xtype : 'roleList'
	} ]
});