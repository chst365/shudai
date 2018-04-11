Ext.define('App.store.${packageName}.${entityName}Store', {
	extend : 'Ext.data.Store',
	model : 'App.model.${packageName}.${entityName}Model',
	storeId : '${packageName}.${entityName}Store',
	pageSize : Constant.PAGESIZE,
	remoteSort: true,
	proxy : {
		type : 'ajax',
		url : basePath + '/${moduleName}/${packageName}/page${entityName}',
		reader : {
			type : 'json',
			rootProperty : 'data.items',
			totalProperty : 'data.total',
			messageProperty : 'msg'
		}
	}
});