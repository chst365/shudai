/**
 * @author Liaoyifan
 */
_dialog = function(type, contentMsg, titleMsg, callback) {
	if (!_dialog.bg) {
		/** 创建背景 * */
		_dialog.bg = document.createElement('div');
		_dialog.bg.style.display = 'none';
		_dialog.bg.setAttribute('id', 'window-dialog-bg');
		_dialog.bg.style.width = document.documentElement.scrollWidth + 'px';
		_dialog.bg.style.height = document.documentElement.scrollHeight + 'px';
		document.body.appendChild(_dialog.bg);
		/** 创建内容 * */
		_dialog.content = document.createElement('div');
		_dialog.content.setAttribute('id', 'window-dialog-content');
		_dialog.bg.appendChild(_dialog.content);
		/** 创建标题 * */
		_dialog.title = document.createElement('div');
		_dialog.title.setAttribute('id', 'window-dialog-title');
		_dialog.content.appendChild(_dialog.title);
		/** 创建内容消息 * */
		_dialog.contentMsg = document.createElement('div');
		_dialog.contentMsg.setAttribute('id', 'window-dialog-contentMsg');
		_dialog.content.appendChild(_dialog.contentMsg);
		/** 创建标题消息 * */
		_dialog.titleMsg = document.createElement('span');
		_dialog.titleMsg.setAttribute('id', 'window-dialog-title-titleMsg');
		_dialog.title.appendChild(_dialog.titleMsg);
		/** 创建关闭按钮 * */
		_dialog.close = document.createElement('img');
		_dialog.close.setAttribute('id', 'window-dialog-title-close');
		_dialog.close
				.setAttribute(
						'src',
						'data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
		_dialog.title.appendChild(_dialog.close);
		/** 创建确认按钮 * */
		_dialog.confirm = document.createElement('input');
		_dialog.confirm.setAttribute('type', 'button');
		_dialog.confirm.setAttribute('id', 'window-dialog-btn-confirm');
		_dialog.confirm.setAttribute('class', 'window-dialog-btn');
		_dialog.confirm.setAttribute('value', '确认');
		_dialog.content.appendChild(_dialog.confirm);
		/** 创建取消按钮 * */
		_dialog.cancel = document.createElement('input');
		_dialog.cancel.style.display = 'none';
		_dialog.cancel.setAttribute('type', 'button');
		_dialog.cancel.setAttribute('id', 'window-dialog-btn-cancel');
		_dialog.cancel.setAttribute('class', 'window-dialog-btn');
		_dialog.cancel.setAttribute('value', '取消');
		_dialog.cancel.style.marginLeft = '3px';
		_dialog.content.appendChild(_dialog.cancel);
		/** 关闭窗口FN * */
		_dialog.hide = function() {
			document.body.style.overflow = 'auto';
			_dialog.bg.style.display = 'none';
		}
		/** 显示窗口FN * */
		_dialog.show = function() {
			document.body.style.overflow = 'hidden';
			_dialog.bg.style.display = 'block';
		}
	}
	/** 显示消息 * */
	_dialog.titleMsg.innerHTML = titleMsg;
	_dialog.contentMsg.innerHTML = contentMsg;
	/** 绑定回调 * */
	_dialog.close.onclick = function() {
		_dialog.hide();
		if (callback) {
			callback('close');
		}
	};
	_dialog.confirm.onclick = function() {
		_dialog.hide();
		if (callback) {
			callback('confirm');
		}
	};
	if (type === 1) {
		_dialog.cancel.onclick = function() {
			_dialog.hide();
			if (callback) {
				callback('cancel');
			}
		};
		_dialog.cancel.style.display = 'inline';
	} else {
		_dialog.cancel.style.display = 'none';
	}
	/** 显示窗口 * */
	_dialog.show();
};
window.alert = function(contentMsg, titleMsg, callback) {
	_dialog(0, contentMsg, titleMsg || '', callback);
};
window.confirm = function(contentMsg, titleMsg, callback) {
	_dialog(1, contentMsg, titleMsg || '', callback);
};
window.onresize = function() {
	if (_dialog.bg && _dialog.bg.style.display !== 'none') {
		_dialog.bg.style.width = document.documentElement.scrollWidth + 'px';
		_dialog.bg.style.height = document.documentElement.scrollHeight + 'px';
	}
};