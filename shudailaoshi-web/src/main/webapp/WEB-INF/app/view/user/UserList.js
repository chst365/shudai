require.sync('UserTypeEnum');
Ext.define('App.view.user.UserList', {
	extend : 'App.config.BaseGrid',
	xtype : 'userList',
	autoSizeXtype : 'userList',
	store : Ext.create('App.store.user.UserStore'),
	bbar : {
		xtype : 'pagingtoolbar',
		store : this.store,
		scrollable : true,
		displayInfo : true
	},
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		text : '用户名',
		dataIndex : 'userName'
	}, {
		text : '手机号码',
		dataIndex : 'mobile'
	}, {
		text : '电子邮箱',
		dataIndex : 'email'
	}, {
		text : '创建时间',
		dataIndex : 'createTime',
		renderer : function(value) {
			return DateUtil.timeToString(value, DateUtil.TIME);
		}
	}, {
		text : '修改时间',
		dataIndex : 'modifyTime',
		renderer : function(value) {
			return DateUtil.timeToString(value, DateUtil.TIME);
		}
	}, {
		text : '状态',
		dataIndex : 'status',
		renderer : function(value) {
			return StatusEnum.getHtml(value);
		}
	} ]
});