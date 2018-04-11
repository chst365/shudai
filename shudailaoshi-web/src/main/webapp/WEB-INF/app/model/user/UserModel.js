Ext.define('App.model.user.UserModel', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'status', 'userName', 'email', 'mobile', 'userType',
			'createTime', 'modifyTime', 'employeeId', 'employeeName' ]
});