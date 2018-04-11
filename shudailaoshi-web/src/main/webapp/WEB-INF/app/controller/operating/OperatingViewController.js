Ext.define('App.controller.operating.OperatingViewController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.operatingViewController',
	menu : Ext.widget('menu'),
	control : {
		'operatingView operatingList' : {
			beforerender : function(grid) {
				var buttons = {
					'sys/operating/saveOperating' : function(permit) {
						BaseUtil.createView('operating.OperatingWindow',permit,{storeId:'operating.OperatingStore'}).show();
					}
				};
				BaseUtil.createPermitTbar(grid, buttons);
			},
			itemcontextmenu : function(view, record, item, index, e) {
				var id = record.get('id');
				var menuitems = {
					'sys/operating/updateOperating' : function(permit) {
						var win = BaseUtil.createView('operating.OperatingWindow',permit,{storeId:'operating.OperatingStore'});
						win.down('form').loadRecord(record);
						win.show();
					},
					'sys/operating/removeOperating' : function(permit) {
						BaseUtil.statusConfirm('确认删除此记录吗?',permit.url,id,store);
					}
				};
				var status = record.get('status'),store = Ext.StoreMgr.get('operating.OperatingStore');
				if (status === StatusEnum.NORMAL.getValue()) {
					menuitems['sys/operating/freeze'] = function(permit) {
						BaseUtil.statusConfirm('确认冻结此记录吗?',permit.url,id,store);
					}
				}else if (status === StatusEnum.FREEZE.getValue()) {
					menuitems['sys/operating/unfreeze'] = function(permit) {
						BaseUtil.statusConfirm('确认解冻此记录吗?',permit.url,id,store);
					}
				}
				BaseUtil.createPermitMenu(view, this.menu, e, menuitems);
			},
			render : function() {
				BaseUtil.loadStore(Ext.StoreMgr.get('operating.OperatingStore'),Ext.getCmp('operatingView').down('operatingSearch form').getForm().getFieldValues());
			}
		},
		'operatingView operatingSearch [action=search] ' : {
			click : function() {
				BaseUtil.loadStore(Ext.StoreMgr.get('operating.OperatingStore'),Ext.getCmp('operatingView').down('operatingSearch form').getForm().getFieldValues());
			}
		},
		'operatingWindow [action=save]': {
			click : function(btn) {
				var win = btn.up('window'),params = win.params;
				BaseUtil.submit(win.down('form'), params.url,function(data) {
					BaseUtil.toast(data.msg);
					Ext.StoreMgr.get(params.storeId).reload();
					win.close();
				});
			}
		}
	}
});