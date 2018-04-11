Ext.define('App.view.role.RoleGrantList', {
	extend : 'App.config.BaseTree',
	xtype : 'roleGrantList',
	autoSizeXtype : 'roleGrantList',
	autoScroll : true,
	store : Ext.create('App.store.role.RoleGrantStore'),
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