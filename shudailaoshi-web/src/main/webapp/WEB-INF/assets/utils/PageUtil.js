/**
 * @param total
 *            总记录数
 * @param current
 *            当前页
 * @param number
 *            显示分页按钮数
 * @param limit
 *            每页显示条数
 * @returns
 */
(function(window) {
	var PageUtil = {
		getHtml : function(total, current, number, limit, funcName) {
//			if (total <= number) {
//				return "";
//			}
			// 总页数
			var totalPage = parseInt((total + limit - 1) / limit);
			// 开始页码
			var starPage = current - 4 < 1 ? 1 : current - 4;
			starPage = starPage > (totalPage - 9) ? (totalPage - 9) : starPage;
			starPage = starPage < 1 ? 1 : starPage;
			// 结束页码
			var endPage = starPage + 9 > totalPage ? totalPage : starPage + 9;
			// 返回代码
			var html = [ '<a class="page-util-a" href="javascript:' + (funcName || 'page') + '('
					+ 1 + ');">首页</a>' ];
			if (parseInt(current) === 1) {
				html.push('<a class="page-util-a" href="javascript:void(0);">上一页</a>');
			} else {
				html.push('<a class="page-util-a" href="javascript:' + (funcName || 'page') + '('
						+ (parseInt(current) - 1) + ');">上一页</a>');
			}
			for (var i = starPage; i <= endPage; i++) {
				if (i === parseInt(current))
					html.push('<a class="page-util-a-current" href="javascript:' + (funcName || 'page')
							+ '(' + i + ');" class="current">' + i + '</a>');
				else
					html.push('<a class="page-util-a" href="javascript:' + (funcName || 'page')
							+ '(' + i + ');">' + i + '</a>');
			}
			if (parseInt(current) === endPage) {
				html.push('<a class="page-util-a" href="javascript:void(0);">下一页</a>');
			} else {
				html.push('<a class="page-util-a" href="javascript:' + (funcName || 'page') + '('
						+ (parseInt(current) + 1) + ');">下一页</a>');
			}
			html.push('<a class="page-util-a" href="javascript:' + (funcName || 'page') + '('
					+ totalPage + ');">尾页</a>');
			return html.join("");
		}
	};
	window.PageUtil = PageUtil;
})(window);
