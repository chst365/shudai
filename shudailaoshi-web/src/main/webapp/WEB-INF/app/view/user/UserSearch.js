Ext.define('App.view.user.UserSearch', {
	extend : 'App.config.BaseFieldSet',
	xtype : 'userSearch',
	items : [ {
		xtype : 'baseSearchForm',
		items : [ {
			items : [ {
				name : 'userName',
				fieldLabel : '用户名'
			}, {
				name : 'mobile',
				fieldLabel : '手机号码'
			}, {
				name : 'email',
				fieldLabel : '电子邮箱'
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
