/**
 * @author Liaoyifan cookie 模块
 */
(function(window) {
	var CookieUtil = {
		set : function(name, value) {
			var argv = arguments, argc = arguments.length, expires = (argc > 2) ? argv[2]
					: null, path = (argc > 3) ? argv[3] : '/', domain = (argc > 4) ? argv[4]
					: null, secure = (argc > 5) ? argv[5] : false;

			document.cookie = name
					+ '='
					+ escape(value)
					+ ((expires === null) ? '' : ('; expires=' + expires
							.toGMTString()))
					+ ((path === null) ? '' : ('; path=' + path))
					+ ((domain === null) ? '' : ('; domain=' + domain))
					+ ((secure === true) ? '; secure' : '');
		},
		get : function(name) {
			var arg = name + '=', alen = arg.length, clen = document.cookie.length, i = 0, j = 0;
			while (i < clen) {
				j = i + alen;
				if (document.cookie.substring(i, j) == arg) {
					var endstr = document.cookie.indexOf(';', j);
					if (endstr == -1) {
						endstr = document.cookie.length;
					}
					return unescape(document.cookie.substring(j, endstr));
				}
				i = document.cookie.indexOf(' ', i) + 1;
				if (i === 0) {
					break;
				}
			}
			return null;
		},
		remove : function(name, path) {
			if (this.get(name)) {
				path = path || '/';
				document.cookie = name + '='
						+ '; expires=Thu, 01-Jan-70 00:00:01 GMT; path=' + path;
			}
		}
	};
	window.CookieUtil = CookieUtil;
})(window);
