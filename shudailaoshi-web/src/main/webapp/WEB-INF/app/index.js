require.async([ 'ExtBase', 'StatusEnum' ], function() {
	// 初始Token
	BaseUtil.getSubmitToken();
	// 初始权限
	BaseUtil.initPermits(function() {
		Ext.application({
			name : 'App',
			appFolder : '.',
			autoCreateViewport : 'App.view.main.MainView',
			listen : {
				controller : {
					'#' : {
						unmatchedroute : 'onUnmatchedRoute'
					}
				}
			},
			onUnmatchedRoute : function(hash) {
				BaseUtil.initView(BaseUtil.getPermit(hash));
			}
		});
	});
});