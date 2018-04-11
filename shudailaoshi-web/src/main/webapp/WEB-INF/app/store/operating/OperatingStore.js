Ext.define('App.store.operating.OperatingStore', {
	extend : 'Ext.data.Store',
	model : 'App.model.operating.OperatingModel',
	storeId : 'operating.OperatingStore',
	pageSize : Constant.PAGESIZE,
	remoteSort: true,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/operating/pageOperating',
		reader : {
			type : 'json',
			rootProperty : 'data.items',
			totalProperty : 'data.total',
			messageProperty : 'msg'
		}
	}
});