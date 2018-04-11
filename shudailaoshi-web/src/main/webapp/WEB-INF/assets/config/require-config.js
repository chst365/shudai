require.config({
	baseUrl : basePath + '/assets',
	group : {
		'ExtBase' : [ 'Constant', 'DateUtil', 'ValidUtil', 'NumberUtil',
				'BaseUtil-Ext' ],
		'JqueryBase' : ['DateUtil', 'ValidUtil', 'NumberUtil']
	},
	path : {
		'BaseUtil-Ext' : 'utils/BaseUtil-Ext',
		'BaseUtil-Jquery' : 'utils/BaseUtil-Jquery',

		'DateUtil' : 'utils/DateUtil',
		'ValidUtil' : 'utils/ValidUtil',
		'NumberUtil' : 'utils/NumberUtil',
		'StorageUtil' : 'utils/StorageUtil',
		'CookieUtil' : 'utils/CookieUtil',
		'PageUtil' : 'utils/PageUtil',
		'CityUtil' : 'utils/CityUtil',
		'UrlUtil' : 'utils/UrlUtil',
		'CartUtil' : 'utils/CartUtil',
		
		'JSEncrypt' : 'lib/jsencrypt',
		'JSON' : 'lib/json2',

		'Constant' : 'constants/Constant',
		'StatusEnum' : 'enums/StatusEnum',
		'SexEnum' : 'enums/SexEnum',
		'UserTypeEnum' : 'enums/sys/UserTypeEnum',
		'UserdEnum' : 'enums/eb/UserdEnum',
	}
});
