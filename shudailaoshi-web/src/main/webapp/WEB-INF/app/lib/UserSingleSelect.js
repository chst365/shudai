Ext.define('App.lib.UserSingleSelectController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.userSingleSelectController',
	control : {
		'userSingleSelectWindow [action=save]' : {
			click : function(me) {
				var win = me.up('window'),id,userName;
				var selection = win.down('userSingleSelectList').getSelectionModel().getSelection();
				if(selection.length > 0){
					var record = selection[0];
					id = record.get('id');
					userName = record.get('userName');
				}
				var select = win.userSingleSelect;
				select.down('[itemId=receiveId]').setValue(id);
				select.down('[itemId=userName]').setValue(userName);
				win.close();
			}
		}
	},
	tbarSearch : function() {
		BaseUtil.loadStore(Ext.StoreMgr
				.get('user.UserSingleSelectStore'), Ext.getCmp(
				'userSingleSelectWindow').down('form').getForm()
				.getFieldValues());
	}
});
Ext.define('App.lib.UserSingleSelectList', {
	extend : 'App.config.BaseGrid',
	requires : [ 'Ext.selection.CheckboxModel' ],
	xtype : 'userSingleSelectList',
	selModel: { 
        selType: 'checkboxmodel',
        mode : 'SINGLE',
		allowDeselect : true
    },
	tbar : [ {
		xtype : 'form',
		border : 0,
		layout : 'hbox',
		defaults : {
			labelWidth : 60
		},
		items : [ {
			fieldLabel : '用户名',
			name : 'userName',
			xtype : 'textfield'
		}, {
			xtype : 'button',
			text : '查询',
			style : 'margin-left:15px',
			handler : 'tbarSearch'
		} ]
	} ],
	columns : [ {
		dataIndex : 'id',
		hidden : true
	}, {
		text : '用户名',
		dataIndex : 'userName'
	}, {
		text : '状态',
		dataIndex : 'status',
		renderer : function(value) {
			return StatusEnum.getHtml(value);
		}
	} ],
	initComponent : function() {
		this.store = Ext.create('App.store.user.UserStore', {
			storeId : 'user.UserSingleSelectStore',
			autoDestroy : true,
			autoLoad : true,
			listeners : {
				load : function() {
					var win = Ext.getCmp('userSingleSelectWindow'),list = win.down('userSingleSelectList'),store = list.getStore(),count = store.getCount(),id = win.userSingleSelect.down('[itemId=receiveId]').getValue();
					for (var i = 0; i < count; i++) {
						var record = store.getAt(i);
						if(record.get('id')==id){
							list.getSelectionModel().select(record);
							return;
						}
					}
				}
			}
		});
		this.bbar = Ext.widget('pagingtoolbar',{
			store : this.store,
			scrollable : true,
			displayInfo : true
		});
		this.callParent(arguments);
	}
});
Ext.define('App.lib.UserSingleSelectWindow', {
	extend : 'App.config.BaseWindow',
	controller : 'userSingleSelectController',
	xtype : 'userSingleSelectWindow',
	id : 'userSingleSelectWindow',
	title : '请选择用户',
	buttons : [ {
		text : '确认',
		action : 'save'
	},{
		text : '清空',
		handler : function() {
			this.up('window').down('grid').getSelectionModel().deselectAll();
		}
	},{
		text : '关闭',
		handler : function() {
			this.up('window').close();
		}
	} ],
	items : [ {
		xtype : 'userSingleSelectList'
	} ]
});
Ext.define('App.lib.UserSingleSelect', {
	extend : 'Ext.form.FieldSet',
	requires : ['App.lib.SelectField'],
	controller : 'userSingleSelectController',
	alias : 'widget.userSingleSelect',
	layout : 'fit',
	border : false,
	padding:0,
	margin:0,
	config:{
		margin : 0,
		beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
		allowBlank : false,
		submitName:'userName',
		submitValue:'receiveId',
		fieldLabel : '用户'
	},
	updateMargin: function(value){
		this.margin = value;
	},
    updateAllowBlank: function(value) {
    	this.items[1].allowBlank = value;
    },
    updateBeforeLabelTextTpl: function(value) {
    	this.items[1].beforeLabelTextTpl = value;
    },
    updateSubmitName: function(value) {
    	this.items[1].name = value;
    },
    updateSubmitValue: function(value) {
    	this.items[0].name = value;
    },
    updateFieldLabel: function(value) {
    	this.items[1].fieldLabel = value;
    },
	defaults : {
		msgTarget : 'under',
		labelWidth : 100,
		labelAlign : 'right'
	},
	items : [ {
		xtype : 'hidden',
		itemId:'receiveId'
	}, {
		xtype : 'selectfield',
		itemId:'userName',
		onClick : function(me) {
			Ext.widget('userSingleSelectWindow', {userSingleSelect : me.up('userSingleSelect')}).show();
		}
	} ]
});
