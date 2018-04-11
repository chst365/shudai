Ext.define('App.store.user.UserStore', {
	extend : 'Ext.data.Store',
	storeId : 'user.UserStore',
	model : 'App.model.user.UserModel',
	pageSize : Constant.PAGESIZE,
	remoteSort: true,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/user/page',
		reader : {
			type : 'json',
			rootProperty : 'data.items',
			totalProperty : 'data.total',
			messageProperty : 'msg'
		}
	}
});