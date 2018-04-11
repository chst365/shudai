Ext.define('App.lib.SelectField', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.selectfield',
    fieldStyle:'cursor:pointer',
    triggers:{search:{cls:Ext.baseCSSPrefix+'form-search-trigger'}},
    onClick:function(field){},
    editable:false,
	labelStyle:'pointer-events:none',
	listeners: {
	   render: function(me) {
		  var self = this;
		  me.getEl().on('click', function(){ 
			  self.onClick(me);
	      });
	  }
	}
});
