/*!
 * require
 * http://git.oschina.net/cmsleo/require
 * author liaoyifan
 * Date: 2016-10-8
 */
(function(window) {
	var doc = window.document, head = doc.head
			|| doc.getElementsByTagName("head")[0] || doc.documentElement, configure;
	function config(conf) {
		configure = {
			baseUrl : conf.baseUrl || '.',
			group : conf.group || {},
			path : conf.path || {}
		};
	}
	function isArr(arr) {
		return Array.isArray ? Array.isArray(arr) : Object.prototype.toString
				.call(arr) === '[object Array]';
	}
	function isStr(str) {
		return typeof str === 'string';
	}
	function getUrls(path) {
		if (isStr(path)) {
			return getUrl(path);
		} else if (isArr(path)) {
			var urls = [];
			for (var i = 0; i < path.length; i++) {
				var url = getUrl(path[i]);
				if (isStr(url))
					urls.push(url);
				else if (isArr(url)) {
					for (var j = 0; j < url.length; j++)
						urls.push(url[j]);
				}
			}
			return urls;
		}
	}
	function getUrl(path) {
		var confUrl = configure.baseUrl + '/', confGroup = configure.group, confPath = configure.path;
		if (isStr(path)) {
			var group = confGroup[path];
			if (group && isArr(group)) {
				var urls = [];
				for (var i = 0; i < group.length; i++)
					urls.push(confUrl + confPath[group[i]] + '.js');
				return urls;
			} else
				return confUrl + confPath[path] + '.js';
		}
	}
	function asyncJs(url, callback) {
		// javascript
		var script = doc.createElement('script');
		script.src = url;
		script.async = true;
		script.onload = script.onerror = script.onreadystatechange = function() {
			if (!script.readyState || script.readyState === 'loaded'
					|| script.readyState === 'complete') {
				// Handle memory leak in IE
				script.onload = script.onreadystatechange = null
				// Remove the script
				if (script.parentNode)
					script.parentNode.removeChild(script);
				// Dereference the script
				script = null;
				if (callback)
					callback(window); 
			}
		}
		// add to head
		head.insertBefore(script, head.firstChild);
	}
	function async(path, callback) {
		var urls = getUrls(path);
		if (isStr(urls))
			asyncJs(urls, callback);
		else if (isArr(urls)) {
			var wait = urls.length;
			var fn = function() {
				wait--;
				if (!wait)
					callback(window);
			};
			for (var i = 0; i < wait; i++)
				asyncJs(urls[i], fn);
		}
	}
	function createActiveXHR() {
		try {
			return new window.ActiveXObject("Microsoft.XMLHTTP");
		} catch (e) {
		}
	}
	function createStandardXHR() {
		try {
			return new window.XMLHttpRequest();
		} catch (e) {
		}
	}
	function syncJs(url) {
		var xhr = window.ActiveXObject ? createActiveXHR()
				: createStandardXHR();
		xhr.open('GET', url, false);
		xhr.send(null);
		var script = doc.createElement('script');
		script.text = xhr.responseText;
		head.insertBefore(script, head.firstChild);
	}
	function sync(path) {
		var urls = getUrls(path);
		if (isStr(urls))
			syncJs(urls);
		else if (isArr(urls)) {
			for (var i = 0; i < urls.length; i++)
				syncJs(urls[i]);
		}
	}
	function syncCross(url){
		var script = doc.createElement('script');
		script.src = url;
		head.insertBefore(script, head.firstChild);
	}
	window.require = {
		syncCross : syncCross,
		asyncJs : asyncJs,
		syncJs : syncJs,
		async : async,
		sync : sync,
		config : config
	};
})(window);