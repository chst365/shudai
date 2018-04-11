Ext.define('App.view.operating.OperatingWindow', {
	extend : 'App.config.BaseWindow',
	requires : [ 'App.controller.operating.OperatingViewController'],
	controller : 'operatingViewController',
	xtype : 'operatingWindow',
	id : 'operatingWindow',
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
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '用户名',
			name : 'userName'
		},{
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '用户ID',
			name : 'userId'
		},{
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '日志',
			name : 'logText'
		},{
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : 'IP',
			name : 'ipAddress'
		},{
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '类名',
			name : 'clazzName'
		},{
			allowBlank : false,
			beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
			fieldLabel : '方法名',
			name : 'methodName'
		} ]
	} ]
});