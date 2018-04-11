Ext.define('App.controller.resource.ResourceViewController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.resourceViewController',
	menu : Ext.widget('menu'),
	control:{
		'resourceView resourceList' : {
			beforerender : function(tree) {
				var buttons = {
					'sys/resource/refresh' : function() {
						tree.getStore().reload();
					},
					'sys/resource/saveFrist' : function(permit) {
						BaseUtil.createView('resource.ResourceWindow', permit).show();
					}
				};
				BaseUtil.createPermitTbar(tree, buttons);
			},
			itemcontextmenu : function(view, record, item, index, e) {
				var id = record.get('id');
				var menuitems = {
					'sys/resource/updateResource' : function(permit) {
						var win = BaseUtil.createView('resource.ResourceWindow',permit);
						win.down('form').loadRecord(record);
						win.show();
					},
					'sys/resource/saveChild' : function(permit) {
						var win = BaseUtil.createView('resource.ResourceWindow',permit);
						win.down('form').getForm().findField('parentId').setValue(id);
						win.show();
					},
					'sys/resource/move' : function(permit) {
						var win = BaseUtil.createView('resource.ResourceMoveWindow',permit);
						win.params.id = id;
						win.show();
					}
				};
				var status = record.get('status'),resourceName = record.get('resourceName'),selected = view.getSelection()[0];
				if (status === StatusEnum.NORMAL.getValue()) {
					menuitems['sys/resource/freeze'] = function(permit) {
						BaseUtil.statusConfirm('确认冻结<font color=red> '+resourceName+' </font>吗?',permit.url,id,null,function(){
							selected.set({status:StatusEnum.FREEZE.getValue()});
							selected.commit();
						});
					}
				}else if (status === StatusEnum.FREEZE.getValue()) {
					menuitems['sys/resource/unfreeze'] = function(permit) {
						BaseUtil.statusConfirm('确认解冻<font color=red> '+resourceName+' </font>吗?',permit.url,id,null,function(){
							selected.set({status:StatusEnum.NORMAL.getValue()});
							selected.commit();
						});
					}
				}
				if(status === StatusEnum.DELETE.getValue()){
					menuitems['sys/resource/recover'] = function(permit) {
						BaseUtil.statusConfirm('确认恢复<font color=red> '+resourceName+' </font>吗?',permit.url,id,null,function(){
							selected.set({status:StatusEnum.NORMAL.getValue()});
							selected.commit();
						});
					}
				}else{
					menuitems['sys/resource/removeResource'] = function(permit) {
						BaseUtil.statusConfirm('确认删除<font color=red> '+resourceName+' </font>吗?',permit.url,id,null,function(data){
							if(data.data){
								selected.remove();
							}else{
								selected.set({status:StatusEnum.DELETE.getValue()});
								selected.commit();
							}
						});
					}
				}
				BaseUtil.createPermitMenu(view,this.menu, e, menuitems);
			},
			render : function(tree) {
				tree.getStore().load();
			}
		},
		'resourceWindow  [action=save]' : {
			click : function(me) {
				var win = me.up('window');
				var url = win.params.url;
				BaseUtil.submit(win.down('form'), url, function(data) {
					App.controller.resource.util.refreshNode(url, data.data);
					BaseUtil.toast(data.msg);
					win.close();
				});
			}
		},
		'resourceMoveWindow resourceMoveList' : {
			render : function(tree) {
				tree.getStore().reload();
			}
		},
		'resourceMoveWindow  [action=move]' : {
			click : function(me) {
				var win = me.up('window'),params = win.params,url = params.url;
				BaseUtil.post(url, function(data) {
					if (data.success) {
						App.controller.resource.util.refreshNode(url, data.data);
						BaseUtil.toast(data.msg);
						win.close();
					} else {
						BaseUtil.toast(data.msg);
					}
				},{
					'id' : params.id,
					'parentId' : win.down('resourceMoveList').getSelection()[0].get('id')
				});
			}
		}
	},
	tbarSearch:function(){
		var tree = Ext.getCmp('resourceView').down('resourceList');
		tree.filterBy(tree.down('[name=searchName]').getValue(),'resourceName');
	}
});
Ext.ns('App.controller.resource.util');
App.controller.resource.util.refreshNode = function(url,data) {
	var tree = Ext.getCmp('resourceView').down('resourceList');
	switch (url) {
	case 'sys/resource/saveChild':
		var node = tree.getSelection()[0];
		node.appendChild(data);
		node.expand();
		tree.getSelectionModel().select(tree.getStore().getNodeById(data.id));
		break;
	case 'sys/resource/updateResource':
		var selected = tree.getSelection()[0];
		selected.set(data);
		selected.commit();
		break;
	case 'sys/resource/saveFrist':
		tree.getRootNode().appendChild(data);
		tree.getSelectionModel().select(tree.getStore().getNodeById(data.id));
		break;
	case 'sys/resource/move':
		var store = tree.getStore();
		store.getNodeById(data.id).remove();
		var node = store.getNodeById(data.parentId);
		node.appendChild(data);
		node.expand();
		tree.getSelectionModel().select(store.getNodeById(data.id));
		break;
	}
};