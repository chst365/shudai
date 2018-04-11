Ext.define('App.view.resource.ResourceWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.resource.ResourceViewController'],
	controller : 'resourceViewController',
	xtype : 'resourceWindow',
	buttons : [ {
		text : '保存',
		action : 'save'
	}, {
		text : '关闭',
		handler : function() {
			this.up('window').close();
		}
	} ],
	items : [ {
		xtype : 'baseWindowForm',
		items : [{
			xtype:'hidden',
			name:'id'
		},{
			xtype:'hidden',
			name:'parentId'
		},{
			name : 'resourceName',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '资源名',
			emptyText : '请输入资源名',
			validator : function(value) {
				if (ValidUtil.isBlank(value))
					return '资源名不能为空';
				return true;
			}
		}, {
			name : 'url',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '权限名',
			emptyText : '请输入权限名',
			validator : function(value) {
				if (ValidUtil.isBlank(value))
					return '权限名不能为空';
				return true;
			}
		}, {
			xtype : 'combobox',
			editable : false,
			queryMode : 'remote',
			store : Ext.create('App.store.resource.ResourceIconStore'),
			pageSize : Constant.ICON_PAGESIZE,
			displayField : 'value',
			valueField : 'value',
			name : 'iconCls',
			emptyText : '请选择图标',
			fieldLabel : '图标',
			fieldStyle : 'cursor:pointer',
			tpl:'<tpl for="."><img class="resource-icon" src="'+basePath+'{name}" onclick="App.view.resource.ResourceWindow.util.imgClick(\'{name}\',\'{value}\');" /></tpl>',
			dockedItems : [ {
				xtype : 'pagingtoolbar',
				store : this.store,
				dock : 'bottom',
				displayInfo : true
			} ]
		}, {
			name : 'sort',
			xtype : 'numberfield',
			fieldLabel : '排序值',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			allowBlank:false,
			value:1
		}, {
			xtype : 'combobox',
			fieldLabel : '是否菜单',
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldStyle : 'cursor:pointer',
			name : 'isMenu',
			editable : false,
			store : Ext.create('Ext.data.Store', {
				fields : [ 'name', 'value' ],
				data : [ {
					'name' : '否',
					'value' : false
				}, {
					'name' : '是',
					'value' : true
				} ]
			}),
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			value : false
		} ]
	} ]
});
Ext.ns('App.view.resource.ResourceWindow.util');
App.view.resource.ResourceWindow.util.imgClick = function(name,value){
	var combo = Ext.ComponentQuery.query('combobox[name=iconCls]')[0];
	combo.setFieldLabel('<img src="'+basePath+name+'" />图标');
	combo.setValue(value);
};