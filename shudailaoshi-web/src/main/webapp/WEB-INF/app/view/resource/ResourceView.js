Ext.define('App.view.resource.ResourceView', {
	extend : 'App.config.BasePanel',
	requires : [ 'App.controller.resource.ResourceViewController','App.view.resource.ResourceList' ],
	controller : 'resourceViewController',
	xtype : 'resourceView',
	id : 'resourceView',
	items : [ {
		xtype : 'resourceList'
	} ]
});