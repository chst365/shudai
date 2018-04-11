/**
 * @author Liaoyifan BaseUtil工具类
 */
(function(window) {
	var BaseUtil = {
		get : function(url, success, data) {
			Ext.Ajax.request({
				url : basePath + '/' + url,
				params : data,
				method : 'GET',
				success : function(response) {
					if (success)
						success(Ext.decode(response.responseText));
				}
			});
		},
		getData : function(url, success, async, r, a) {
			var data = null;
			Ext.Ajax.request({
				url : basePath + '/' + url,
				method : 'GET',
				async : async === undefined ? false : async,
				success : function(response) {
					data = Ext.decode(response.responseText);
					if (success)
						success(data);
				}
			});
			return data;
		},
		post : function(url, success, data, r, a) {
			BaseUtil.mask();
			Ext.Ajax.request({
				url : basePath + '/' + url,
				params : data,
				method : 'POST',
				success : function(response) {
					BaseUtil.unmask();
					if (success) {
						try {
							success(Ext.decode(response.responseText));
						} catch (e) {
							console.error(e);
						}
					}
					BaseUtil.getSubmitToken();
				}
			});
		},
		submit : function(form, url, success, data) {
			form.getForm().submit({
				clientValidation : true,
				url : basePath + '/' + url,
				waitMsg : '提交中...',
				params : data,
				success : function(form, action) {
					if (success) {
						try {
							success(action.result);
						} catch (e) {
							console.error(e);
						}
					}
					BaseUtil.getSubmitToken();
				},
				failure : function(form, action) {
					switch (action.failureType) {
					case Ext.form.action.Action.CLIENT_INVALID:
						BaseUtil.toast('表单验证未通过,请检查表单');
						break;
					case Ext.form.action.Action.SERVER_INVALID:
						BaseUtil.errorMsg(action.result.msg);
						BaseUtil.getSubmitToken();
						break;
					}
				}
			});
		},
		getSubmitToken : function() {
			this.getData(Constant.URL_SUBMIT_TOKEN, function(data) {
				Ext.util.Cookies.set(Constant.SUBMIT_TOKEN, data, null, '/');
			});
		},
		mask : function() {
			if (!this.maskBg) {
				this.maskBg = document.createElement('div');
				var style = this.maskBg.style;
				style.display = 'none';
				style.position = 'absolute';
				style.left = 0;
				style.top = 0;
				style.width = '100%';
				style.height = '100%';
				style.zIndex = 999999999;
				document.body.appendChild(this.maskBg);
			}
			var style = document.body.style;
			style.height = '100%';
			style.overflow = 'hidden';
			this.maskBg.style.display = 'block';
			Ext.get(this.maskBg).mask('请求中...');
		},
		unmask : function() {
			if (this.maskBg) {
				var style = document.body.style;
				style.height = 'auto';
				style.overflow = 'auto';
				this.maskBg.style.display = 'none';
				Ext.get(this.maskBg).unmask();
			}
		},
		toast : function(msg) {
			Ext.toast({
				html : msg,
				closable : false,
				align : 't',
				slideInDuration : 400,
				minWidth : 400
			});
		},
		errorMsg : function(msg, fn) {
			Ext.Msg.show({
				title : '提示',
				msg : msg,
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.ERROR,
				fn : fn
			});
		},
		infoMsg : function(msg, fn) {
			Ext.Msg.show({
				title : '提示',
				msg : msg,
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.INFO,
				fn : fn
			});
		},
		initPermits : function(callback) {
			try {
				BaseUtil.getData('sys/resource/listPermit', function(data) {
					BaseUtil.PERMITS = data;
					if (callback)
						callback();
				});
			} catch (e) {
				BaseUtil.errorMsg("该用户未授权",function(){
					location = basePath + '/app/logout';
				});
			}
		},
		createAccordionTree : function(node) {
			return Ext.widget('treepanel', {
				title : node.resourceName,
				useArrows : true,
				rootVisible : false,
				animate : false,
				border : false,
				store : Ext.create('Ext.data.TreeStore', {
					fields : [ 'url', 'iconCls', 'parentId', {
						name : 'index',
						mapping : 'sort'
					}, {
						name : 'text',
						mapping : 'resourceName'
					} ],
					root : {
						expanded : true,
						children : node.children
					}
				}),
				listeners : {
					itemclick : function(view, record, item, index, e, eOpt) {
						var permit = {
							url : record.get('url'),
							iconCls : record.get('iconCls'),
							resourceName : record.get('resourceName')
						};
						BaseUtil.initView(permit);
					}
				}
			});
		},
		initView : function(permit, callback) {
			if (!permit) {
				BaseUtil.toast('不存在该界面,无法初始化');
				return;
			}
			var url = permit.url, iconCls = permit.iconCls, resourceName = permit.resourceName;
			if(url.startsWith('expand-')){
				return;
			}
			BaseUtil.mask();
			BaseUtil.get('isPermitted', function(data) {
				if (data) {
					try {
						var view = BaseUtil.addTab(url, iconCls, resourceName);
						if (callback) {
							callback(view);
						}
					} catch (e) {
						console.error(e);
						BaseUtil.errorMsg('菜单项加载出错');
					}
				} else {
					BaseUtil.errorMsg('您没有访问【' + url + '】的权限');
				}
				BaseUtil.unmask();
			}, {
				'permittedUrl' : url
			});
		},
		addTab : function(xtype, iconCls, resourceName) {
			var tab = Ext.getCmp('AppCenterTab');
			if (tab.items.containsKey(xtype)) {
				tab.setActiveTab(xtype);
				return tab.getChildByElement(xtype);
			} else {
				var requireName = 'App.view.' + xtype.replace('View', '') + '.'
						+ xtype.charAt(0).toUpperCase() + xtype.substring(1);
				var view = Ext.create(requireName);
				tab.add(view);
				tab.setActiveTab(view);
				view.setIconCls(iconCls);
				view.setTitle(resourceName);
				return view;
			}
		},
		getPermit : function(url) {
			var permits = BaseUtil.PERMITS;
			for (var i = 0; i < permits.length; i++) {
				var p = permits[i];
				if (p.url === url) {
					return p;
				}
			}
		},
		cleanParams : function(params) {
			for ( var key in params) {
				var val = params[key];
				if (!ValidUtil.trim(val))
					delete params[key];
				else if (val instanceof Date)
					params[key] = DateUtil.dateToString(val, DateUtil.DATE);
			}
			return params;
		},
		createPermitItem : function(xtype, url, handler) {
			var permits = BaseUtil.PERMITS;
			if (!permits) {
				BaseUtil.errorMsg('权限未加载成功');
				return;
			}
			for (var i = 0; i < permits.length; i++) {
				var p = permits[i];
				if (url === p.url) {
					return {
						xtype : xtype,
						text : p.resourceName,
						iconCls : p.iconCls,
						handler : function() {
							handler(p);
						}
					};
				}
			}
		},
		createPermitTbar : function(grid, params) {
			var items = [];
			for ( var key in params) {
				var btn = this.createPermitItem('button', key, params[key]);
				if (btn)
					items.push(btn, '-');
			}
			if (items.length > 0) {
				grid.addDocked(Ext.widget('toolbar', {
					items : items
				}));
			}
		},
        createPermitBar : function(panel, params) {
            var items = [];
            for ( var key in params) {
                var btn = this.createPermitItem('button', key, params[key]);
                if (btn)
                    items.push(btn, '-');
            }
            if (items.length > 0) {
                panel.addDocked(Ext.widget('toolbar', {
                    dock: 'bottom',
                    items : items
                }));
            }
        },
		createPermitMenu : function(view, menu, e, params) {
			e.preventDefault();
			menu.removeAll();
			for ( var key in params) {
				var menuitem = this.createPermitItem('menuitem', key,
						params[key]);
				if (menuitem)
					menu.add(menuitem);
			}
			if (view.getSelection().length == 1 && menu.items.length > 0)
				menu.showAt(e.getXY());
		},
        createHeaderMenu : function(view, menu, e, params) {
            e.preventDefault();
            menu.removeAll();
            for ( var key in params) {
                var menuitem = this.createPermitItem('menuitem', key,
                    params[key]);
                if (menuitem)
                    menu.add(menuitem);
            }
            if (menu.items.length > 0)
                menu.showAt(e.getXY());
        },
		statusConfirm : function(content, url, id, store, success) {
			Ext.Msg.confirm('提示', content, function(flag) {
				if (flag === 'yes') {
					BaseUtil.post(url, function(data) {
						if (data.success) {
							if (success) {
								success(data);
							} else {
								store.reload();
							}
							BaseUtil.toast(data.msg);
						} else {
							BaseUtil.toast(data.msg);
						}
					}, {
						'id' : id
					});
				}
			});
		},
        statusConfirmIds : function(content, url, ids, store, success) {
            Ext.Msg.confirm('提示', content, function(flag) {
                if (flag === 'yes') {
                    BaseUtil.post(url, function(data) {
                        if (data.success) {
                            if (success) {
                                success(data);
                            } else {
                                store.reload();
                            }
                            BaseUtil.toast(data.msg);
                        } else {
                            BaseUtil.toast(data.msg);
                        }
                    }, {
                        'ids' : ids.join(',')
                    });
                }
            });
        },
        statusPromptIds : function(content, url, ids, store, success) {
            Ext.MessageBox.prompt('提示',content,function(flag,value){
                if (flag === 'ok') {
                    BaseUtil.post(url, function(data) {
                        if (data.success) {
                            if (success) {
                                success(data);
                            } else {
                                store.reload();
                            }
                            BaseUtil.toast(data.msg);
                        } else {
                            BaseUtil.toast(data.msg);
                        }
                    }, {
                        'ids' : ids.join(','),
                        'text':value
                    });
                }
            });
        },
        statusPrompt : function(content, url, id, store, success) {
            Ext.MessageBox.prompt('提示',content,function(flag,value){
                if (flag === 'ok') {
                    BaseUtil.post(url, function(data) {
                        if (data.success) {
                            if (success) {
                                success(data);
                            } else {
                                store.reload();
                            }
                            BaseUtil.toast(data.msg);
                        } else {
                            BaseUtil.toast(data.msg);
                        }
                    }, {
                        'id' : id,
						'text':value
                    });
                }
			});
        },
		statusConfirms : function(content, url, params, store, success) {
			Ext.Msg.confirm('提示', content, function(flag) {
				if (flag === 'yes') {
					BaseUtil.post(url, function(data) {
						if (data.success) {
							if (success) {
								success(data);
							} else {
								store.reload();
							}
							BaseUtil.toast(data.msg);
						} else {
							BaseUtil.toast(data.msg);
						}
					}, 
						params
					);
				}
			});
		},
		getGridTip : function(value, metaData) {
			metaData.tdAttr = 'data-qtip="' + Ext.String.htmlEncode(value)
					+ '"';
			return value;
		},
		createView : function(path, permit, params) {
			if (!permit) {
				console.error('创建视图所需权限对象permit不能为空');
				return;
			}
			if (!params) {
				params = {};
			}
			params['url'] = permit.url;
			var view = Ext.create('App.view.' + path, {
				params : params
			});
			view.setTitle(permit.resourceName);
			view.setIconCls(permit.iconCls);
			return view;
		},
		loadStore : function(store, params) {
			store.proxy.extraParams = this.cleanParams(params);
			store.loadPage(1);
		},
		setFormReadOnly : function(form, readOnly) {
			var items = form.items.items;
			for (var i = 0; i < items.length; i++) {
				var item = items[i];
				if (item.readOnly !== undefined) {
					item.setReadOnly(true);
				}
				item.setStyle('pointer-events', 'none');
			}
		},
		compare : function(propertyName) {
			return function(obj1, obj2) {
				var v1 = obj1[propertyName], v2 = obj2[propertyName];
				if (v1 < v2) {
					return -1;
				} else if (v1 == v2) {
					return 0;
				} else {
					return 1;
				}
			}
		}
	};
	window.BaseUtil = BaseUtil;
})(window);
