Ext.define('App.view.operating.OperatingSearch', {
	extend : 'App.config.BaseFieldSet',
	xtype : 'operatingSearch',
	items : [ {
		xtype : 'baseSearchForm',
		items : [ {
			items : [ {
				fieldLabel : '用户ID',
				name : 'id'
			}, {
				fieldLabel : '用户名',
				name : 'userName'
			}, {
				fieldLabel : '日志',
				name : 'logText'
			}, {
				fieldLabel : 'IP',
				name : 'ipAddress'
			}, {
				fieldLabel : '类名',
				name : 'clazzName'
			}, {
				fieldLabel : '方法名',
				name : 'methodName'
			} ]
		}, {
			items : [ {
				xtype : 'baseStartDate',
				columnWidth : .25,
			}, {
				xtype : 'baseEndDate',
				columnWidth : .2,
				labelWidth : 10
			}, {
				xtype : 'button',
				text : '查询',
				action : 'search',
				columnWidth : .15,
				style : 'margin-left:15px'
			}, {
				xtype : 'button',
				text : '重置',
				columnWidth : .15,
				style : 'margin-left:15px',
				handler : function(me) {
					me.up('form').getForm().reset();
				}
			} ]
		} ]
	} ]
});
