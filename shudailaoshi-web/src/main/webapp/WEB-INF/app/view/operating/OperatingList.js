Ext.define('App.view.operating.OperatingList', {
	extend : 'App.config.BaseGrid',
	xtype : 'operatingList',
	autoSizeXtype : 'operatingList',
	store : Ext.create('App.store.operating.OperatingStore'),
	bbar : {
		xtype : 'pagingtoolbar',
		store : this.store,
		scrollable : true,
		displayInfo : true
	},
	columns : [ {
		xtype : 'rownumberer'
	}, {
		text : '用户ID',
		dataIndex : 'userId'
	}, {
		text : '用户名',
		dataIndex : 'userName'
	}, {
		text : '日志',
		dataIndex : 'logText'
	}, {
		text : '类名',
		dataIndex : 'clazzName'
	}, {
		text : '方法名',
		dataIndex : 'methodName'
	}, {
		text : 'IP',
		dataIndex : 'ipAddress'
	}, {
		text : '创建时间',
		dataIndex : 'createTime',
		renderer : function(value) {
			return DateUtil.timeToString(value, DateUtil.TIME);
		}
	} ]
});