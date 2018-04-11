Ext.define('App.view.${packageName}.${entityName}View', {
	extend : 'App.config.BasePanel',
	requires : [ 'App.controller.${packageName}.${entityName}ViewController',
			'App.view.${packageName}.${entityName}Search', 'App.view.${packageName}.${entityName}List' ],
	controller : '${packageName}ViewController',
	xtype : '${packageName}View',
	id : '${packageName}View',
	items : [ {
		xtype : '${packageName}Search'
	}, {
		xtype : '${packageName}List'
	} ]
});