Ext.define('App.view.${packageName}.${entityName}List', {
	extend : 'App.config.BaseGrid',
	xtype : '${packageName}List',
	autoSizeXtype : '${packageName}List',
	store : Ext.create('App.store.${packageName}.${entityName}Store'),
	bbar : {
		xtype : 'pagingtoolbar',
		store : this.store,
		scrollable : true,
		displayInfo : true
	},
	columns : [ {
		xtype : 'rownumberer'
	},${columns}]
});