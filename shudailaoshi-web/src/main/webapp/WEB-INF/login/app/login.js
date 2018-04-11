require.async(['ExtBase','JSEncrypt'],function(){
	//初始Token
	BaseUtil.getSubmitToken();
	/** 绑定enter* */
	document.onkeydown = function(e) {
		if (e.keyCode === 13) {
			Ext.getCmp('bindEnter').fireEvent('click');
			e.preventDefault();
		}
	};
	Ext.widget('window',{
		title:'登录',
		width : '30%',
		height : '50%',
		glyph : 0xf007,
		resizable:false,
		closable:false,
		constrainHeader:true,
		style:'filter:alpha(opacity=97);-moz-opacity:0.97;-khtml-opacity: 0.97;opacity: 0.97;',
		layout : 'fit',
		buttons : [ {
			id:"bindEnter",
			text : '登录',
			glyph:0xf0c7,
		    listeners:{
		        click:function(){
					var form = this.up('window').down('form').getForm();
					if(form.isValid()){
						BaseUtil.getData(Constant.URL_PUBLICKEY, function(data) {
							var encrypt = new JSEncrypt();
							encrypt.setPublicKey(data);
							var userPwdField = form.findField('userPwd');
							var userPwd = encrypt.encrypt(ValidUtil.trim(userPwdField.getValue()));
							if (!userPwd) {
								BaseUtil.toast('您的操作频率过快，请稍后重试');
								return;
							}
							userPwdField.setValue(userPwd);
							BaseUtil.post('app/login', function(data) {
								if (data.success) {
									location = basePath + '/app/index.html';
								} else {
									userPwdField.setValue('');
									BaseUtil.toast(data.msg);
								}
							},{
								'userName' : ValidUtil.trim(form.findField('userName').getValue()),
								'userPwd' : userPwd,
								'remember':form.findField('remember').getValue()
							});
						});
					}
				}
		    }
		}],
		items : [ {
			xtype : 'form',
			scrollable : true,
			border : 0,
			layout : 'column',
			padding:10,
			defaults : {
				columnWidth : 0.98,
				margin : '8 0',
				xtype : 'textfield',
				msgTarget : 'under',
				labelWidth : 60
			},
			items : [ {
				name : 'userName',
				fieldLabel : '用户名',
				emptyText : '请输入用户名',
				validator : function(value) {
					if (ValidUtil.isBlank(value))
						return '用户名不能为空';
					return true;
				}
			}, {
				inputType : 'password',
				name : 'userPwd',
				fieldLabel : '密码',
				emptyText : '请输入密码',
				validator : function(value) {
					if (ValidUtil.isBlank(value))
						return '密码不能为空';
					return true;
				}
			}, {
		        xtype:'checkbox',
		        name: 'remember',
				fieldLabel : '记住我',
			} ]
		} ]
	}).show();
});