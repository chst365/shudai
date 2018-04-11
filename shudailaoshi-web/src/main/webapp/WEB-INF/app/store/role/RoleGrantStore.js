Ext.define('App.store.role.RoleGrantStore', {
	extend : 'Ext.data.TreeStore',
	model : 'App.model.role.RoleGrantModel',
	storeId : 'role.RoleGrantStore',
	autoLoad:false,
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/resource/listGrant'
	},
	root : {
		id : Constant.ROOT
	},
	listeners : {
		load : function() {
			Ext.getCmp('roleGrantWindow').down('roleGrantList').getRootNode().expand();
		}
	}
});