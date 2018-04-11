Ext.define('App.view.resource.ResourceMoveList', {
	extend : 'App.config.BaseTree',
	xtype : 'resourceMoveList',
	autoSizeXtype : 'resourceMoveList',
	rootVisible : true,
	store : Ext.create('App.store.resource.ResourceStore', {
		storeId : 'resource.ResourceMoveStore'
	}),
	columns : [ {
		text : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		xtype : 'treecolumn',
		text : '资源名',
		dataIndex : 'resourceName'
	} ]
});