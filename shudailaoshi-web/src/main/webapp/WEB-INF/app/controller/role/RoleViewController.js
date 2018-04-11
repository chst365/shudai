Ext.define('App.controller.role.RoleViewController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.roleViewController',
	menu : Ext.widget('menu'),
	control : {
		'roleView roleList' : {
			beforerender : function(grid) {
				var buttons = {
					'sys/role/save' : function(permit) {
						BaseUtil.createView('role.RoleWindow',permit,{storeId:'role.RoleStore'}).show();
					}
				};
				BaseUtil.createPermitTbar(grid, buttons);
			},
			itemcontextmenu : function(view, record, item, index, e) {
				var roleName = record.get('roleName');
				if (roleName !== Constant.ADMIN) {
					var id = record.get('id');
					var menuitems = {
						'sys/role/grant' : function(permit) {
							var win = BaseUtil.createView('role.RoleGrantWindow',permit,{
								id : id
							});
							Ext.StoreMgr.get('role.RoleGrantStore').load({
								params : {
									roleId : id
								}
							});
							win.show();
						}
					};
					if(roleName !== Constant.CUSTOMER && roleName !== Constant.WAITER && roleName !== Constant.FRANCHISEE){
						menuitems['sys/role/updateRole'] = function(permit) {
							var win = BaseUtil.createView('role.RoleWindow',permit,{storeId:'role.RoleStore'});
							win.down('form').loadRecord(record);
							win.show();
						}
					}
					var status = record.get('status'),store = Ext.StoreMgr.get('role.RoleStore');
					if (status === StatusEnum.NORMAL.getValue()) {
						menuitems['sys/role/freeze'] = function(permit) {
							BaseUtil.statusConfirm('确认冻结<font color=red> '+roleName+' </font>吗?',permit.url,id,store);
						}
					}else if (status === StatusEnum.FREEZE.getValue()) {
						menuitems['sys/role/unfreeze'] = function(permit) {
							BaseUtil.statusConfirm('确认解冻<font color=red> '+roleName+' </font>吗?',permit.url,id,store);
						}
					}
					if(status === StatusEnum.DELETE.getValue()){
						menuitems['sys/role/recover'] = function(permit) {
							BaseUtil.statusConfirm('确认恢复<font color=red> '+roleName+' </font>吗?',permit.url,id,store);
						}
					}else{
						menuitems['sys/role/removeRole'] = function(permit) {
							BaseUtil.statusConfirm('确认删除<font color=red> '+roleName+' </font>吗?',permit.url,id,store);
						}
					}
					BaseUtil.createPermitMenu(view, this.menu, e, menuitems);
				}
			},
			render : function() {
				BaseUtil.loadStore(Ext.StoreMgr.get('role.RoleStore'),Ext.getCmp('roleView').down('roleSearch form').getForm().getFieldValues());
			}
		},
		'roleView roleSearch [action=search] ' : {
			click : function() {
				BaseUtil.loadStore(Ext.StoreMgr.get('role.RoleStore'),Ext.getCmp('roleView').down('roleSearch form').getForm().getFieldValues());
			}
		},
		'roleWindow [action=save]' : {
			click : function(me) {
				var win = me.up('window'),params = win.params;
				BaseUtil.submit(win.down('form'), params.url,function(data) {
					BaseUtil.toast(data.msg);
					Ext.StoreMgr.get(params.storeId).reload();
					win.close();
				});
			}
		},
		'roleGrantWindow roleGrantList' : {
			checkchange : function(node, checked, obj) {
				node.cascadeBy(function(n) {
					n.set('checked', checked);
				});
				// App.controller.role.util.checkParent(node);
			}
		},
		'roleGrantWindow [action=save]' : {
			click : function(me) {
				var win = me.up('roleGrantWindow');
				var checkeds = win.down('roleGrantList').getView().getChecked();
				var resourceIds = [];
				Ext.each(checkeds, function(c) {
					resourceIds.push(c.get('id'));
				});
				var params = win.params;
				BaseUtil.post(params.url, function(data) {
					if(data.success){
						win.close();
						BaseUtil.toast(data.msg);
					}else{
						BaseUtil.toast(data.msg);
					}
				},{
					id : params.id,
					resourceIds : resourceIds
				});
			}
		}
	}
});
Ext.ns('App.controller.role.util');
App.controller.role.util.checkParent = function(node){
	node = node.parentNode;
	if (!node)
		return;
	var checkP = false;
	node.cascadeBy(function(n) {
		if (n != node) {
			if (n.get('checked')) {
				checkP = true;
			}
		}
	});
	node.set('checked', checkP);
	App.controller.role.util.checkParent(node);
};
