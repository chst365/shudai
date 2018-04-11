_websocket = {};
_websocket.init = function(onMessage, onOpen, onError, onClose) {
	if ('WebSocket' in window) {
		_websocket.socket = new WebSocket(wsPath + '/websocket.websocket');
	} else if ('MozWebSocket' in window) {
		_websocket.socket = new MozWebSocket(wsPath + '/websocket.websocket');
	} else {
		_websocket.socket = new SockJS(wsPath + '/sockjs.websocket');
	}
	_websocket.socket.onopen = function(evnt) {
		if(onOpen){
			onOpen(evnt);
		}
	}
	_websocket.socket.onmessage = function(evnt) {
		if(onMessage){
			onMessage(evnt);
		}
	}
	_websocket.socket.onerror = function(evnt) {
		if(onError){
			onError(evnt);
		}
	}
	_websocket.socket.onclose = function(evnt) {
		if(onClose){
			onClose(evnt);
		}
	}
};
_websocket.loadEvent = function(func) {
	if (window.attachEvent) {
		window.attachEvent('onload', func);
	} else {
		window.addEventListener('load', func, true);
	}
};
_websocket.connect = function(onMessage, onOpen, onError, onClose){
	_websocket.loadEvent(function(){
		_websocket.init(onMessage, onOpen, onError, onClose);
		setInterval(function() {
			if(_websocket.socket){
				if (_websocket.socket.readyState !== 1) {
					_websocket.socket.close();
					_websocket.init(onMessage, onOpen, onError, onClose);
				}
			}else{
				_websocket.init(onMessage, onOpen, onError, onClose);
			}
		},10000);
	});
};