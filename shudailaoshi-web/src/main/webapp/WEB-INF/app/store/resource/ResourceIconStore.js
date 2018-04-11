Ext.define('App.store.resource.ResourceIconStore', {
	extend : 'Ext.data.Store',
	storeId : 'resource.ResourceIconStore',
	model : 'App.model.resource.ResourceIconModel',
	autoLoad : false,
	pageSize : Constant.ICON_PAGESIZE,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/resource/pageIcon',
		reader : {
			type : 'json',
			rootProperty : 'items',
			totalProperty : 'total',
			messageProperty : 'msg'
		}
	}
});