jQuery.extend({
    getGUID:function() { //获取GUID
    	function GUID(){
    		this.date = new Date();   /* 判断是否初始化过，如果初始化过以下代码，则以下代码将不再执行，实际中只执行一次 */
		    if (typeof this.newGUID != 'function') {   /* 生成GUID码 */
		        GUID.prototype.newGUID = function () {
		            this.date = new Date(); 
		            var guidStr = '';
		            sexadecimalDate = this.hexadecimal(this.getGUIDDate(), 16);
		            sexadecimalTime = this.hexadecimal(this.getGUIDTime(), 16);
		            for (var i = 0; i < 9; i++) {
		                guidStr += Math.floor(Math.random() * 16).toString(16);
		            }
		            guidStr += sexadecimalDate;
		            guidStr += sexadecimalTime;
		            while (guidStr.length < 32) {
		                guidStr += Math.floor(Math.random() * 16).toString(16);
		            }
		            return this.formatGUID(guidStr);
		        }
		        /* * 功能：获取当前日期的GUID格式，即8位数的日期：19700101 * 返回值：返回GUID日期格式的字条串 */
		        GUID.prototype.getGUIDDate = function () {
		            return this.date.getFullYear() + this.addZero(this.date.getMonth() + 1) + this.addZero(this.date.getDay());
		        }
		        /* * 功能：获取当前时间的GUID格式，即8位数的时间，包括毫秒，毫秒为2位数：12300933 * 返回值：返回GUID日期格式的字条串 */
		        GUID.prototype.getGUIDTime = function () {
		            return this.addZero(this.date.getHours()) + this.addZero(this.date.getMinutes()) + this.addZero(this.date.getSeconds()) + this.addZero(parseInt(this.date.getMilliseconds() / 10));
		        }
		        /* * 功能: 为一位数的正整数前面添加0，如果是可以转成非NaN数字的字符串也可以实现 * 参数: 参数表示准备再前面添加0的数字或可以转换成数字的字符串 * 返回值: 如果符合条件，返回添加0后的字条串类型，否则返回自身的字符串 */
		        GUID.prototype.addZero = function (num) {
		            if (Number(num).toString() != 'NaN' && num >= 0 && num < 10) {
		                return '0' + Math.floor(num);
		            } else {
		                return num.toString();
		            }
		        }
		        /*  * 功能：将y进制的数值，转换为x进制的数值 * 参数：第1个参数表示欲转换的数值；第2个参数表示欲转换的进制；第3个参数可选，表示当前的进制数，如不写则为10 * 返回值：返回转换后的字符串 */GUID.prototype.hexadecimal = function (num, x, y) {
		            if (y != undefined) { return parseInt(num.toString(), y).toString(x); }
		            else { return parseInt(num.toString()).toString(x); }
		        }
		        /* * 功能：格式化32位的字符串为GUID模式的字符串 * 参数：第1个参数表示32位的字符串 * 返回值：标准GUID格式的字符串 */
		        GUID.prototype.formatGUID = function (guidStr) {
		            var str1 = guidStr.slice(0, 8) + '-', str2 = guidStr.slice(8, 12) + '-', str3 = guidStr.slice(12, 16) + '-', str4 = guidStr.slice(16, 20) + '-', str5 = guidStr.slice(20);
		            return str1 + str2 + str3 + str4 + str5;
		        }
		    }
    	}
	    return new GUID();
	} ,
	getNewGUID:function(){ //获取初始化
		if(!this.guidObj){
			this.guidObj = new this.getGUID();
		}
		var guid = this.guidObj.newGUID();
		this.guidObj = null ; 
		return guid ;
	} , 
	video:{ //视频函数处理对象
		calcBitRate:function(w,h,fr){ //获取video马力
			var dw = 128;
			if (w <= 176) //176*144
	        {
	            if( fr < 20 )
	                dw = 128;
	            else
	                dw = 192;
	        }
	        else if (w <= 320) //320*240
			{
				if( fr  <  15   )
					dw = 256;
				else if( fr >=15 &&  fr <=20 )
					dw = 256;
				else if( fr > 20 && fr<=30  )
					dw = 256+128 ;	
			}
			else if (w <=640)//640*480
			{
				if( fr  <  15   )
					dw =  384 ;		
				else if( fr >=15 &&  fr <=20 )
					dw = 512 ;	
				else if( fr > 20 && fr<=30  )
					dw = 512+256;
			}
			else if (w <=1280)//1280*720
			{
				if( fr  <  5 )
					dw = 512;
				else if( fr  <  15   )
					dw = 1024 ;		
				else if( fr >=15 &&  fr <=20 )
					dw = 1024+256 ;		
				else if( fr > 20 && fr<=30  )
					dw = 1024+512 ;	
			}
			else//1920*1080
			{
				if( fr  <  5 )
					dw = 512;
				else if( fr  <  15   )
					dw =  1024+256 ;		
				else
					dw =  1024+1024 ;		
				
			}
			return dw;
		}
	} ,
    getBrowserInfo:function () {  //获取浏览器基本信息
        //判断访问终端
        var browser={
            versions:function(){
                var u = navigator.userAgent, app = navigator.appVersion;
                return {
                    trident: u.indexOf('Trident') > -1, //IE内核
                    presto: u.indexOf('Presto') > -1, //opera内核
                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                    iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                    iPad: u.indexOf('iPad') > -1, //是否iPad
                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                };
            }(),
            language:(navigator.browserLanguage || navigator.language).toLowerCase() ,
            info:{
                appCodeName:navigator.appCodeName , //返回浏览器的代码名。
                appMinorVersion:navigator.appMinorVersion , //返回浏览器的次级版本。
                appName:navigator.appName , //返回浏览器的名称。
                appVersion:navigator.appVersion ,  //	返回浏览器的平台和版本信息。
                browserLanguage:navigator.browserLanguage , //	返回当前浏览器的语言。
                cookieEnabled: navigator.cookieEnabled , //	返回指明浏览器中是否启用 cookie 的布尔值。
                cpuClass:navigator.cpuClass , //	返回浏览器系统的 CPU 等级。
                onLine:navigator.onLine , //	返回指明系统是否处于脱机模式的布尔值。
                platform:navigator.platform , //	返回运行浏览器的操作系统平台。
                systemLanguage:navigator.systemLanguage ,  //返回 OS 使用的默认语言。
                userAgent:navigator.userAgent , //返回由客户机发送服务器的 user-agent 头部的值。
                userLanguage:navigator.userLanguage , //	返回 OS 的自然语言设置。

            }
        }
        return browser ;
    },
    isEmpty:function(obj){
        var name;
        for ( name in obj ) {
            return false;
        }
        return true;
	}
});
