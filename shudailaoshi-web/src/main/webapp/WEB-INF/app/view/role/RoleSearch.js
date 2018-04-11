Ext.define('App.view.role.RoleSearch', {
	extend : 'App.config.BaseFieldSet',
	xtype : 'roleSearch',
	items : [ {
		xtype : 'baseSearchForm',
		items : [ {
			items : [ {
				name : 'roleName',
				fieldLabel : '角色名'
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