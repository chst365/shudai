Ext.define('App.view.role.RoleList', {
	extend : 'App.config.BaseGrid',
	xtype : 'roleList',
	autoSizeXtype : 'roleList',
	store : Ext.create('App.store.role.RoleStore'),
	bbar : {
		xtype : 'pagingtoolbar',
		store : this.store,
		scrollable : true,
		displayInfo : true
	},
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		text : '角色名',
		dataIndex : 'roleName'
	}, {
		text : '描述',
		dataIndex : 'description'
	}, {
		text : '状态',
		dataIndex : 'status',
		renderer : function(value) {
			return StatusEnum.getHtml(value);
		}
	} ]
});