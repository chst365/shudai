Ext.define('App.view.main.MainView', {
	extend : 'Ext.container.Viewport',
	requires : [ 'Ext.ux.TabReorderer', 'Ext.ux.TabCloseMenu',
		'App.controller.main.MainController',
		'App.viewmodel.main.MainViewModel' ],
	controller : 'mainController',
	viewModel : 'mainViewModel',
	xtype : 'mainView',
	layout : 'border',
	items : [ {
		id : 'AppNorthPanel',
		xtype : 'toolbar',
		region : 'north',
		items : [ {
			xtype : 'component',
			bind : {
				html : '{logoHtml}'
			}
		}, {
			xtype : 'tbspacer',
			flex : 1
		},
//		{
//			cls : 'delete-focus-bg',
//			iconCls : 'x-fa fa-search',
//			// href : '#search',
//			// hrefTarget : '_self',
//			tooltip : 'See latest search'
//		}, {
//			cls : 'delete-focus-bg',
//			iconCls : 'x-fa fa-envelope',
//			// href : '#email',
//			// hrefTarget : '_self',
//			tooltip : 'Check your email'
//		}, {
//			cls : 'delete-focus-bg',
//			iconCls : 'x-fa fa-th-large',
//			// href : '#profile',
//			// hrefTarget : '_self',
//			tooltip : 'See your profile'
//		}
		, {
			xtype : 'tbtext',
			text : "欢迎您，<font color=red>"+currentUserName+"</font>！",
			cls : 'top-user-name'
		}, {
			xtype : 'image',
			cls : 'header-right-profile-image',
			alt : 'current user image'
			// src : 'resources/images/user-profile/2.png'
		} ]
	}, {
		region : 'west',
		id : 'AppWestPanel',
		title : '主菜单',
		width : 200,
		split : true,
		collapsible : true,
		layout : 'accordion',
		listeners : {
			beforeRender : 'initMenu'
		}
	}, {
		region : 'south',
		id : 'AppSouthPanel',
		border : false,
		buttonAlign : 'center',
		bbar : [{
			text : "修改密码",
			iconCls : 'icon_computer_edit',
			handler : 'changePwd'
		}, {
			xtype : 'tbfill'
		}, {
			xtype : 'tbseparator'
		}, {
			text : "退出系统",
			iconCls : 'icon_computer_go',
			handler : 'loginOut'
		} ]
	}, {
		id : 'AppCenterTab',
		region : 'center',
		xtype : 'tabpanel',
		activeTab : 0,
		plugins : [ {
			ptype : 'tabreorderer'
		}, {
			ptype : 'tabclosemenu',
			closeTabText : '关闭当前标签',
			closeOthersTabsText : '关闭其他标签',
			closeAllTabsText : '关闭所有标签'
		} ],
//		items : [ Ext.widget('panel', {
//			title : '主页',
//			html : ''
//		}) ]
	} ]
});