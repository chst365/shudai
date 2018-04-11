(function(window){
	var DateUtil = {
		DATE : "yyyy-MM-dd",
		TIME : "yyyy-MM-dd HH:mm:ss",
		MILLISECOND : "yyyy-MM-dd HH:mm:ss.SSS",
		SECOND:"yyyy-MM-dd HH:mm",
		getDate : function() {
			return this.dateToString(new Date(), this.DATE);
		},
		getTime:function() {
			return this.dateToString(new Date(), this.TIME);
		},
		getSecond:function() {
			return this.dateToString(new Date(), this.SECOND);
		},
		getMillisecond:function() {
			return this.dateToString(new Date(), this.MILLISECOND);
		},
		timeToString:function(millisecond,pattern){
			return this.dateToString(millisecond, pattern);
		},
		dateToString:function(millisecond,pattern){
			if(!pattern){
				pattern = this.DATE;
			}
			var t = millisecond;
			if (Object.prototype.toString.call(t) !== '[object Date]') {
				t = new Date(t);
			}
			var tf = function(i) {
				return (i < 10 ? '0' : '') + i
			};
			return pattern.replace(/yyyy|MM|dd|HH|mm|ss|SSS/g, function(a) {
				switch (a) {
				case 'yyyy':
					return tf(t.getFullYear());
				case 'MM':
					return tf(t.getMonth() + 1);
				case 'mm':
					return tf(t.getMinutes());
				case 'dd':
					return tf(t.getDate());
				case 'HH':
					return tf(t.getHours());
				case 'ss':
					return tf(t.getSeconds());
				case 'SSS':
					return tf(t.getMilliseconds());
				}
			});
		},
		stringToDate : function(str) {
			return eval('new Date('
					+ str.replace(/\d+(?=-[^-]+$)/, function(a) {
						return parseInt(a, 10) - 1;
					}).match(/\d+/g) + ')');
		},
		// 获取下周？的日期 date：new Date() or new Date("2011-11-11") week: 0 - 6 周日至周六
		nextWeekDate : function(date, week) {
			var date = date || new Date(), timestamp, newDate;
			if (!(date instanceof Date)) {
				date = new Date(date.replace(/-/g, '/'));
			}
			var addDay = week - date.getDay();
			if (addDay <= 0)
				addDay += 7;
			date.setDate(addDay + date.getDate());
			return [ [ date.getFullYear(), date.getMonth() + 1, date.getDate() ]
					.join('-') ].join(' ');
		},
		daysBefore : function(date, day) {
			var date = date || new Date(), timestamp, newDate;
			if (!(date instanceof Date)) {
				date = new Date(date.replace(/-/g, '/'));
			}
			timestamp = date.getTime();
			newDate = new Date(timestamp - day * 24 * 3600 * 1000);
			return [ [ newDate.getFullYear(), newDate.getMonth() + 1,
					newDate.getDate() ].join('-') ].join(' ');
		},
		addCountDate : function(AddDayCount) {
			var day = new Date();
			day.setDate(day.getDate() + AddDayCount);// 获取AddDayCount天后的日期
			var tf = function(i) {
				return (i < 10 ? '0' : '') + i
			};
			return tf(day.getFullYear()) + "-" + tf(day.getMonth() + 1) + "-"
					+ tf(day.getDate());
		}, 
		//时间字符串
		getWeek : function(str) {  
			d = this.stringToDate(str);
			if(this.isToday(d)){ 
				return "今天";   
			} 
			if(this.isTomorrow(d)){  
				return "明天";   
			} 
			if(this.isTothree(d)){   
				return "后天";   
			} 
			var weekday=new Array(7);  
			weekday[0]="周日";
			weekday[1]="周一";
			weekday[2]="周二";
			weekday[3]="周三";
			weekday[4]="周四";
			weekday[5]="周五";
			weekday[6]="周六"; 
			if(d)     
				return weekday[d.getDay()]; 
			else   
				return ''; 
		}, 
		isTothree : function(d) {//是后天?
			
			var now = this.stringToDate(this.getDate()).getTime();    
			d = d.getTime(); 
			var nd = (d - now)/ 1000 / 60 /60 / 24; 
			if(1 < nd && nd <= 2)   
				return true;
			else 
				return false;
		},
		isTomorrow : function(d) {//是明天?
			
			var now = this.stringToDate(this.getDate()).getTime();    
			d = d.getTime(); 
			var nd = (d - now)/ 1000 / 60 /60 / 24; 
			if(0 < nd && nd <= 1)  
				return true;
			else 
				return false;
		},
		isToday : function(d) {//是今天?
			var now = new Date();    
			if(now.getFullYear() == d.getFullYear() && now.getMonth() == d.getMonth() && now.getDate() == d.getDate()){
				return true;   
			}  else {
				return false;
			}
		}
	};
	window.DateUtil = DateUtil;
})(window);