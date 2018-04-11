/**
 * @author Liaoyifan Ext requirejs 配置
 */
Ext.Loader.setConfig({
    enabled: true,
    disableCaching: true,
    paths: {
        'Ext.ux': basePath + '/assets/lib/ext-6.2.0/packages/ux/classic/src'
    }
});
Ext.QuickTips.init();
Ext.setGlyphFontFamily('FontAwesome');
Ext.Ajax.timeout = 50000; // 50秒
Ext.Ajax.on('requestexception', function (conn, response, options, eOpts) {
    BaseUtil.unmask();
    switch (response.status) {
        case 999:
            alert('您的操作频率过快，请稍后重试');
            BaseUtil.getSubmitToken();
            break;
        case 403:
            alert('您没有访问[' + options.url + ']的权限，请联系管理员');
            break;
        case 401:
            alert('您未登录或者登录超时，请重新登录');
            location.href = basePath + '/app/login.html'
            break;
        case 404:
            alert('404 Not Found，请联系管理员');
            break;
        case 405:
            alert('405 Method Not Allowed，请联系管理员');
            break;
        case 500:
            alert('500 Internal Server Error，请联系管理员');
            break;
        case 0:
            alert('服务器连接错误，请检查网络后重试');
            break;
        default:
            alert('未知错误，请联系管理员');
            break;
    }
});
//base 控件
Ext.define('App.config.BaseGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.baseGrid',
    columnLines: true,
    // forceFit : true,
    flex: 1,
    margin: '0 1 0 0',
    viewConfig: {
        enableTextSelection: true,
        emptyText: '<div style="text-align:center;padding:20px;font-size:14px;">没有数据</div>',
        deferEmptyText: false
    },
    autoSizeXtype: '',
    listeners: {
        afterrender: function (grid) {
        		var storeListener = grid.getStore().on({
                load: {
                    fn: function (store, records, successful, eOpts) {
                    		if(successful){
                    			var cols = Ext.ComponentQuery.query(grid.autoSizeXtype)[0].getColumns();
                            for (var i = 0; i < cols.length; i++)
                                cols[i].autoSize();
                    		}else{
                    			BaseUtil.toast(eOpts.getError());
                    			Ext.destroy(storeListener);
                    		}
                    }
                },destroyable :true
            });
        }
    }
});
Ext.define('App.config.BaseTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.baseTree',
    useArrows: false,
    animate: false,
    rootVisible: false,
    border: false,
    columnLines: true,
    sortableColumns: false,
    flex: 1,
    forceFit: true,
    margin: '0 1 0 0',
    viewConfig: {
        enableTextSelection: true,
        emptyText: '<div style="text-align:center; padding:20px">没有数据</div>',
        deferEmptyText: false
    }
});
Ext.define('App.config.BaseWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.baseWindow',
    width: '60%',
    height: '80%',
    modal: true,
    maximizable: true,
    constrainHeader: true,
    layout: 'fit',
    buttonAlign: 'center'
});
Ext.define('App.config.BasePanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.basepanel',
    padding: 1,
    closable: true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    }
});
Ext.define('App.config.BaseFieldSet', {
    extend: 'Ext.form.FieldSet',
    alias: 'widget.baseFieldSet',
    title: '折叠',
    collapsible: true,
    animCollapse: false,
    margin: '0 2 2 0'
});
Ext.define('App.config.BaseSearchForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.baseSearchForm',
    border: false,
    defaults: {
        xtype: 'fieldset',
        layout: 'column',
        border: false,
        defaults: {
            style: 'margin-bottom:2px',
            columnWidth: 0.3,
            xtype: 'textfield',
            labelWidth: 100,
            labelAlign: 'right'
        }
    }
});
Ext.define('App.config.BaseWindowForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.baseWindowForm',
    scrollable: true,
    border: 1,
    margin: 5,
    layout: 'column',
    defaults: {
        columnWidth: 0.99,
        margin: '8 0',
        xtype: 'textfield',
        labelAlign: 'right',
        msgTarget: 'under'
    }
});
Ext.define('App.config.BaseStartDate', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.baseStartDate',
    fieldLabel: '创建日期',
    name: 'startDate',
    anchor: '100%',
    format: 'Y-m-d',
    editable: false,
    listeners: {
        select: function (me, value) {
            me.up('form').getForm().findField('endDate').setMinValue(value);
        }
    }
});
Ext.define('App.config.BaseEndDate', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.baseEndDate',
    name: 'endDate',
    margin: '0 0 0 4',
    labelSeparator: '',
    fieldLabel: '~',
    anchor: '100%',
    format: 'Y-m-d',
    editable: false,
    listeners: {
        select: function (me, value) {
            me.up('form').getForm().findField('startDate').setMaxValue(value);
        }
    }
});