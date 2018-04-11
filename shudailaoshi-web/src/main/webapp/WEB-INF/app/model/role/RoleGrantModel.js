Ext.define('App.model.role.RoleGrantModel', {
	extend : 'Ext.data.Model',
	fields : [ 'resourceName', 'url', 'iconCls', 'parentId', 'checked', {
		name : 'index',
		mapping : 'sort'
	},'status' ]
});