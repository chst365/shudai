Ext.define('App.view.${packageName}.${entityName}Search', {
	extend : 'App.config.BaseFieldSet',
	xtype : '${packageName}Search',
	items : [ {
		xtype : 'baseSearchForm',
		items : [ {
			items : [ ${columns} ]
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
