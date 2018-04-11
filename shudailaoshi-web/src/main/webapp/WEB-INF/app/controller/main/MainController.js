Ext.define('App.controller.main.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.mainController',
	initMenu : function(me) {
		try {
			var menus = BaseUtil.getData('sys/resource/listMenu');
			var nodes = menus.children[0].children;
			nodes.sort(BaseUtil.compare("sort"));
			for (var i = 0; i < nodes.length; i++)
				me.add(BaseUtil.createAccordionTree(nodes[i]));
		} catch (e) {
			console.log(e);
			BaseUtil.errorMsg('主菜单加载出错');
		}
	},
	loginOut : function() {
		Ext.Msg.confirm('提示', '确认要退出系统吗?', function(flag) {
			if (flag === 'yes')
				location = basePath + '/app/logout';
		});
	},
	changePwd : function() {
		Ext.widget('basewindow',{
			title:'修改密码',
			buttons : [ {
				text : '保存',
				action : 'save',
				handler : function() {
					var win = this.up('window');
					BaseUtil.submit(win.down('form'), 'sys/user/changePwd', function(data) {
						BaseUtil.toast(data.msg);
						win.close();
					});
				}
			}, {
				text : '关闭',
				handler : function() {
					this.up('window').close();
				}
			} ],
			items:[{
				xtype:'basewindowform',
				items:[{
					inputType:'password',
					allowBlank : false,
					beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
					fieldLabel : '原密码',
					name:'oldPwd'
				},{
					inputType:'password',
					beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
					fieldLabel : '新密码',
					name:'newPwd',
					validator : function(value) {
						if (ValidUtil.isBlank(value))
							return '密码不能为空';
						if (!ValidUtil.isPwd(value))
							return '密码长度应为6-22位';
						return true;
					}
				},{
					inputType:'password',
					beforeLabelTextTpl : [ '<font color=red data-qtip=必填选项>*</font>' ],
					fieldLabel : '确认新密码',
					name:'newPwdRe',
					validator : function(value) {
						if (ValidUtil.isBlank(value))
							return '确认密码不能为空';
						if(value!==this.up('form').getForm().findField('newPwd').getValue())
							return '密码和确认密码不相同';
						return true;
					}
				}]
			}]
		}).show();
	}
});