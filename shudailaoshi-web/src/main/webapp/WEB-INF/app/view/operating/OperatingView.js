Ext.define('App.view.operating.OperatingView', {
	extend : 'App.config.BasePanel',
	requires : [ 'App.controller.operating.OperatingViewController',
			'App.view.operating.OperatingSearch', 'App.view.operating.OperatingList' ],
	controller : 'operatingViewController',
	xtype : 'operatingView',
	id : 'operatingView',
	items : [ {
		xtype : 'operatingSearch'
	}, {
		xtype : 'operatingList'
	} ]
});