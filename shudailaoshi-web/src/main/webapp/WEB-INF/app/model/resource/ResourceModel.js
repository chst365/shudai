Ext.define('App.model.resource.ResourceModel', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'resourceName', 'url', 'iconCls', 'parentId', 'createTime',
			'modifyTime', 'status', {
				name : 'index',
				mapping : 'sort'
			} ]
});