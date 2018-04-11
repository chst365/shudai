/**
 * @author Liaoyifan url编码解码
 */
(function(window) {
	var UrlUtil = {
		encode : function(obj) {
			if (!obj
					|| Object.prototype.toString.call(obj) !== '[object Object]') {
				return '';
			}
			var params = [];
			for ( var key in obj) {
				params.push(encodeURIComponent(key) + '='
						+ encodeURIComponent(obj[key]));
			}
			return params.join('&');
		},
		decode : function(str) {
			if (!str || typeof str !== 'string') {
				return '';
			}
			var parts = str.replace(/^\?/, '').split('&'), obj = {}, components, value;
			for (var i = 0; i < parts.length; i++) {
				var part = parts[i];
				if (part.length > 0) {
					components = part.split('=');
					name = decodeURIComponent(components[0]);
					value = (components[1] !== undefined) ? decodeURIComponent(components[1])
							: '';
					obj.hasOwnProperty(name) ? obj[name].push(value)
							: obj[name] = value;
				}
			}
			return obj;
		},
		getHost : function(url) { 
		        var host = "null";
		        if(typeof url == "undefined"|| null == url)
		                url = window.location.href;
		        var regex = /.*\:\/\/([^\/]*).*/;
		        var match = url.match(regex);
		        if(typeof match != "undefined"
		                        && null != match)
		                host = match[1];
		        return host;
		},
		getUrlParam : function(paras) {
			var url = location.href;
			var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
			var paraObj = {}
			for (i=0; j=paraString[i]; i++){
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
			}
			var returnValue = paraObj[paras.toLowerCase()];
			if(typeof(returnValue)=="undefined"){
			return "";
			}else{
			return returnValue;
			}
		},
		getUrlParamBase64 : function(paras) { 
			var url = location.href;  
			var params = url.substring(url.indexOf("?")+1,url.length).replace("sn=",'');
			var unicode= BASE64.decoder(params);//返回会解码后的unicode码数组。   
			var str = '';  
			for(var i = 0 , len =  unicode.length ; i < len ;++i){  
				str += String.fromCharCode(unicode[i]);  
			}   
			var paraString = str.split("&");
			var paraObj = {}
			for (i=0; j=paraString[i]; i++){
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
			}
			var returnValue = paraObj[paras.toLowerCase()];
			if(typeof(returnValue)=="undefined"){
			return "";
			}else{
			return returnValue;
			}
		}
	};
	window.UrlUtil = UrlUtil;
})(window);
