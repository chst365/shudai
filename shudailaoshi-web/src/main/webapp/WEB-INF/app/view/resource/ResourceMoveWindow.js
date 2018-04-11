Ext.define('App.view.resource.ResourceMoveWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.resource.ResourceViewController',
			'App.view.resource.ResourceMoveList' ],
	controller : 'resourceViewController',
	xtype : 'resourceMoveWindow',
	buttons : [ {
		text : '确认',
		action : 'move'
	}, {
		text : '关闭',
		handler : function() {
			this.up('window').close();
		}
	} ],
	items : [ {
		xtype : 'resourceMoveList'
	} ]
});
