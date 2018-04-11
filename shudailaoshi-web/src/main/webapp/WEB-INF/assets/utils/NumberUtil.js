(function(window) {
	var NumberUtil = {
		// 保留两位小数 四舍五入
		round : function(x) {
			var f = parseFloat(x);
			if (isNaN(f)) {
				return;
			}
			f = Math.round(x * 100) / 100;
			return f;
		},
		// 强制2位小数(返回字符串)
		roundString : function(x) {
			var f = parseFloat(x);
			if (isNaN(f)) {
				return false;
			}
			var f = Math.round(x * 100) / 100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + 2) {
				s += '0';
			}
			return s;
		},
		// 加
		add : function(d1, d2) {
			var r1, r2;
			try {
				r1 = d1.toString().split('.')[1].length;
			} catch (e) {
				r1 = 0;
			}
			try {
				r2 = d2.toString().split('.')[1].length;
			} catch (e) {
				r2 = 0;
			}
			var m = Math.pow(10, Math.max(r1, r2));
			return (d1 * m + d2 * m) / m;
		},
		// 减
		sub : function(d1, d2) {
			return this.add(d1, -d2);
		},
		// 乘
		mul : function(d1, d2) {
			var m = 0, s1 = d1.toString(), s2 = d2.toString();
			try {
				m += s1.split('.')[1].length;
			} catch (e) {
			}
			try {
				m += s2.split('.')[1].length;
			} catch (e) {
			}
			return Number(s1.replace('.', '')) * Number(s2.replace('.', ''))
					/ Math.pow(10, m);
		},
		// 除
		div : function(d1, d2) {
			var t1 = 0, t2 = 0, r1, r2;
			try {
				t1 = d1.toString().split('.')[1].length;
			} catch (e) {
			}
			try {
				t2 = d2.toString().split('.')[1].length;
			} catch (e) {
			}
			with (Math) {
				r1 = Number(d1.toString().replace('.', ''));
				r2 = Number(d2.toString().replace('.', ''));
				return this.round((r1 / r2) * pow(10, t2 - t1));
			}
		},
		// 格式化金额
		fmoney : function(s, n) {
			n = n > 0 && n <= 20 ? n : 2;
			f = s < 0 ? "-" : "";
			s = parseFloat((Math.abs(s) + "").replace(/[^\d\.-]/g, ""))
					.toFixed(n)
					+ "";
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
			t = "";
			for (i = 0; i < l.length; i++) {
				t += l[i]
						+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			return f + t.split("").reverse().join("") + "." + r.substring(0, 2);
		},
		// 还原金额
		rmoney : function(s) {
			return parseFloat(s.replace(/[^\d\.-]/g, ''));
		}
	};
	window.NumberUtil = NumberUtil;
})(window);