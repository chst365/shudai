Ext.define('App.store.role.RoleStore', {
	extend : 'Ext.data.Store',
	model : 'App.model.role.RoleModel',
	storeId : 'role.RoleStore',
	pageSize : Constant.PAGESIZE,
	remoteSort: true,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/role/page',
		reader : {
			type : 'json',
			rootProperty : 'data.items',
			totalProperty : 'data.total',
			messageProperty : 'msg'
		}
	}
});