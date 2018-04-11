(function(window) {
	var StorageUtil = {
		supportSession : function() {
			try {
				return 'sessionStorage' in window
						&& window['sessionStorage'] !== null;
			} catch (e) {
				return false;
			}
		},
		supportLocal : function() {
			try {
				return 'localStorage' in window
						&& window['localStorage'] !== null;
			} catch (e) {
				return false;
			}
		},
		getLocal : function(key) {
			return this.supportLocal() ? localStorage.getItem(key) : CookieUtil
					.get(key);
		},
		setLocal : function(key, value) {
			this.supportLocal() ? localStorage.setItem(key, value) : CookieUtil
					.set(key, value);
		},
		removeLocal : function(key) {
			this.supportLocal() ? localStorage.removeItem(key) : CookieUtil
					.remove(key)
		},
		getSession : function(key) {
			return this.supportSession() ? sessionStorage.getItem(key)
					: CookieUtil.get(key);
		},
		setSession : function(key, value) {
			this.supportSession() ? sessionStorage.setItem(key, value)
					: CookieUtil.set(key, value);
		},
		removeSession : function(key) {
			this.supportSession() ? sessionStorage.removeItem(key) : CookieUtil
					.remove(key);
		}
	};
	window.StorageUtil = StorageUtil;
})(window);
