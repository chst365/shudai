Ext.define('App.view.${packageName}.${entityName}Window', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.${packageName}.${entityName}ViewController'],
	controller : '${packageName}ViewController',
	xtype : '${packageName}Window',
	id : '${packageName}Window',
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
		items : [{
			xtype:'hidden',
			name:'id'
		},${columns} ]
	} ]
});