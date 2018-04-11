Ext.define('App.store.resource.ResourceStore', {
	extend : 'Ext.data.TreeStore',
	storeId : 'resource.ResourceStore',
	model : 'App.model.resource.ResourceModel',
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/resource/list'
	},
	root : {
		id : Constant.ROOT,
		expanded : true
	}
});