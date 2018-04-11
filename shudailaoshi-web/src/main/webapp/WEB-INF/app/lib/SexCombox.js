require.sync('SexEnum');
Ext.define('App.lib.SexCombox', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.sexCombox',
	fieldLabel : '性别',
	allowBlank : false,
	beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
	fieldStyle : 'cursor:pointer',
	name : 'sex',
	editable : false,
	store : Ext.create('Ext.data.Store', {
		fields : [ 'name', 'value' ],
		data : [ {
			'name' : SexEnum.MAN.getText(),
			'value' : SexEnum.MAN.getValue()
		}, {
			'name' : SexEnum.WOMAN.getText(),
			'value' : SexEnum.WOMAN.getValue()
		} ]
	}),
	queryMode : 'local',
	displayField : 'name',
	valueField : 'value',
	value : SexEnum.MAN.getValue()
});
