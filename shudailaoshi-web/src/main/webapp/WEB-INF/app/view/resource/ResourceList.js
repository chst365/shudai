Ext.define('App.view.resource.ResourceList', {
	extend : 'App.config.BaseTree',
	xtype : 'resourceList',
	autoSizeXtype : 'resourceList',
	store : Ext.create('App.store.resource.ResourceStore'),
	mixins : [ 'App.lib.TreeFilter' ],
	tbar : [ {
		xtype : 'form',
		border : 0,
		layout : 'hbox',
		defaults : {
			labelWidth : 50
		},
		items : [ {
			fieldLabel : '名称',
			name : 'searchName',
			xtype : 'textfield'
		}, {
			xtype : 'button',
			text : '查询',
			style : 'margin-left:15px',
			handler : 'tbarSearch'
		} ]
	} ],
	columns : [ {
		dataIndex : 'id',
		hidden : true
	}, {
		xtype : 'treecolumn',
		text : '资源名',
		dataIndex : 'resourceName'
	}, {
		text : '权限名',
		dataIndex : 'url'
	}, {
		text : '排序值',
		dataIndex : 'sort'
	}, {
		text : '是否菜单',
		dataIndex : 'isMenu',
		renderer : function(value) {
			return value ? '是' : '否';
		}
	}, {
		text : '父节点',
		dataIndex : 'parentId'
	}, {
		text : '状态',
		dataIndex : 'status',
		renderer : function(value) {
			return StatusEnum.getHtml(value);
		}
	} ]
});