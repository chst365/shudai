Ext.define('App.store.user.UserGrantStore', {
	extend : 'Ext.data.Store',
	storeId : 'user.UserGrantStore',
	fields : [ 'id', 'roleName' ],
	proxy : {
		type : 'ajax',
		url : basePath + '/sys/role/list'
	},
	listeners : {
		load : function() {
			var selectId = Ext.getCmp('userView').down('userList')
					.getSelection()[0].get('id');
			BaseUtil.mask();
			BaseUtil.get('sys/role/listIdByUserId', function(data) {
				Ext.getCmp('userGrantWindow').down('[name=roleIds]').setValue(
						data);
				BaseUtil.unmask();
			}, {
				userId : selectId
			});
		}
	}
});