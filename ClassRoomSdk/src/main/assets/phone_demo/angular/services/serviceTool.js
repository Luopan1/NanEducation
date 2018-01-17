tk_room.service('ServiceTools', function () {
	return {
		/*所需工具*/
        tool:{
        /**启动全屏
         @method launchFullscreen
         @param {element} element 全屏元素
         */
        launchFullscreen:function(element) {
            if(element.requestFullscreen) {
                element.requestFullscreen();
            } else if(element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
            } else if(element.webkitRequestFullscreen) {
                element.webkitRequestFullscreen();
            } else if(element.msRequestFullscreen) {
                element.msRequestFullscreen();
            }
        },
        /**退出全屏
         @method exitFullscreen
         */
        exitFullscreen:function() {
            console.info("getFullscreenElement",this.getFullscreenElement());
            if(document.exitFullScreen) {
                document.exitFullScreen();
            } else if(document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if(document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
            } else if(element.msExitFullscreen) {
                element.msExitFullscreen();
            }
        } ,
		/*是否处于全屏状态
		 @method isFullScreenStatus
		 * */
        isFullScreenStatus:function(element){
            return document.fullscreen ||
                document.mozFullScreen ||
                document.webkitIsFullScreen ||
                document.webkitFullScreen ||
                document.msFullScreen ||
                false;
        },
        /**返回正处于全屏状态的Element节点，如果当前没有节点处于全屏状态，则返回null。
         @method getFullscreenElement
         */
        getFullscreenElement:function () {
            var fullscreenElement =
                document.fullscreenElement ||
                document.mozFullScreenElement ||
                document.webkitFullscreenElement;
            return fullscreenElement;
        },
        /**返回一个布尔值，表示当前文档是否可以切换到全屏状态
         @method isFullscreenEnabled
         */
        isFullscreenEnabled:function () {
            var fullscreenEnabled =
                document.fullscreenEnabled ||
                document.mozFullScreenEnabled ||
                document.webkitFullscreenEnabled ||
                document.msFullscreenEnabled;
            return fullscreenEnabled;
        },
		/*添加前缀---方法执行（如果是方法），又是属性判断（是否支持属性）
		 @method runPrefixMethod
		 TODO 暂时没有测试能否可用
		 */
        runPrefixMethod:function(element, method) {
            var usablePrefixMethod;
            ["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
                if (usablePrefixMethod) return;
                if (prefix === "") {
                    // 无前缀，方法首字母小写
                    method = method.slice(0,1).toLowerCase() + method.slice(1);

                }

                var typePrefixMethod = typeof element[prefix + method];

                if (typePrefixMethod + "" !== "undefined") {
                    if (typePrefixMethod === "function") {
                        usablePrefixMethod = element[prefix + method]();
                    } else {
                        usablePrefixMethod = element[prefix + method];
                    }
                }
            });

            return usablePrefixMethod;
        },
        /**为全屏添加全屏事件fullscreenchange
         @method addFullscreenchange
         @param   {Function} handle 事件处理器
         */
        addFullscreenchange:function(handle){
            this.addEvent(document,"fullscreenchange",handle,false);
            this.addEvent(document,"webkitfullscreenchange",handle,false);
            this.addEvent(document,"mozfullscreenchange",handle,false);
            this.addEvent(document,"MSFullscreenChange",handle,false);
            this.addEvent(document,"msfullscreenchange",handle,false);
            this.addEvent(document,"fullscreeneventchange",handle,false);
        },
        /**为全屏添加全屏事件fullscreenerror
         @method addFullscreenerror
         @param   {Function} handle 事件处理器
         */
        addFullscreenerror :function(handle){
            this.addEvent(document,"fullscreenerror",handle,false);
            this.addEvent(document,"webkitfullscreenerror",handle,false);
            this.addEvent(document,"mozfullscreenerror",handle,false);
            this.addEvent(document,"MSFullscreenError",handle,false);
            this.addEvent(document,"msfullscreenerror",handle,false);
            this.addEvent(document,"fullscreenerroreventchange",handle,false);
        },
        /**绑定事件
         @method addEvent
         @param   {element} element 添加事件元素
         {string} eType 事件类型
         {Function} handle 事件处理器
         {Bollean} bol false 表示在事件第三阶段（冒泡）触发，true表示在事件第一阶段（捕获）触发。
         */
        addEvent:function(element, eType, handle, bol){
            bol = (bol!=undefined && bol!=null)?bol:false ;
            if(element.addEventListener){           //如果支持addEventListener
                element.addEventListener(eType, handle, bol);
            }else if(element.attachEvent){          //如果支持attachEvent
                element.attachEvent("on"+eType, handle);
            }else{                                  //否则使用兼容的onclick绑定
                element["on"+eType] = handle;
            }
        },
        /**事件解绑
         @method addEvent
         @param   {element} element 添加事件元素
         {string} eType 事件类型
         {Function} handle 事件处理器
         {Bollean} bol false 表示在事件第三阶段（冒泡）触发，true表示在事件第一阶段（捕获）触发。
         */
        removeEvent:function(element, eType, handle, bol) {
            if(element.addEventListener){
                element.removeEventListener(eType, handle, bol);
            }else if(element.attachEvent){
                element.detachEvent("on"+eType, handle);
            }else{
                element["on"+eType] = null;
            }
        },
        /**自动元素定位--中间定位
         @method autoElementPositionCneter
         @param {element} $ele 定位元素
         */
        autoElementPositionCneter:function ($ele) {
            $ele.css({
                "margin-left":(-$ele.width()/2)+"px" ,
                "margin-top":(-$ele.height()/2)+"px"
            });
        } ,
        /**清除元素定位--中间定位
         @method clearElementPositionCneter
         @param {element} $ele 定位元素
         */
        clearElementPositionCneter:function ($ele) {
            $ele.css({
                "margin-left":"" ,
                "margin-top":""
            });
        }
    },
		getLocalMediaDevice:function(){
			//window.RTCPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
			//window.RTCSessionDescription = window.RTCSessionDescription || window.mozRTCSessionDescription || window.webkitRTCSessionDescription;
			//navigator.mediaDevices  = navigator.mediaDevices  || navigator.webkitMediaDevices  || navigator.mozMediaDevices  || navigator.msMediaDevices  ;
			//navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia ;
			var LocalMediaDevices = function (){
			}
			LocalMediaDevices.prototype = {
				constructor:LocalMediaDevices,
				enumerateDevices:function(callback ){
					// List cameras and microphones.
					navigator.mediaDevices.enumerateDevices()
					.then(function(devices) {
						 callback(devices);
						 /*devices.forEach(function(device) {
						    console.info(device.kind + ": " + device.label +
						                " id = " + device.deviceId , device);
						  });*/
					})
					.catch(function(err) {
					  console.error(err.name + ": " + err.message);
					  throw err ;
					});
				} ,
				getMediaDevices:function(){
					return navigator.mediaDevices;
				} ,
				getEnumerateDevices:function(){
					if (!navigator.mediaDevices || !navigator.mediaDevices.enumerateDevices) {
						console.log("enumerateDevices() not supported.");
						return;
					}
					// List cameras and microphones.
					return  navigator.mediaDevices.enumerateDevices();
				}, 
				getUserMedia:function(constraints,onSuccess,onError){
			//		if (!navigator.mediaDevices || !navigator.mediaDevices.enumerateDevices) {
			//			console.log("enumerateDevices() not supported.");
			//			return;
			//		}
					if(!onError){
						onError = function(err){
							/* handle the error */
						    console.error(err.name + ": " + err.message);
						}
					}
					if(navigator.getUserMedia){
						getUserMedia(constraints,onSuccess,onError);
					}else{
						console.log("getUserMedia not supported");
					}
					
				} , 
				switchCameras:function (track, camera) {
				  var constraints = track.getConstraints();
				  constraints.facingMode = camera;
				  track.applyConstriants(constraints);
				},
				ondevicechange:function(callback){
					navigator.mediaDevices.ondevicechange = function(event) {
					   callback(event);
					}
				},
				getSupportedConstraints:function(){
					var supportedConstraints = navigator.mediaDevices.getSupportedConstraints();
					return supportedConstraints ;
				} , 
				isHasAudioAndVideoDevice:function(){
					var obj = null ;
					var constraints =  {
			            audio: true,
			            video: true
			        };
					this.getUserMedia(constraints,function(stream){
						obj = {};
						obj.video = stream.getVideoTracks() && stream.getVideoTracks().length>0 ? true : false ;
						obj.audio = stream.getAudioTracks() && stream.getAudioTracks().length>0 ? true : false ;
					},function(err){
						console.error(err);
						obj = {};
						obj.video = false ;
						obj.audio = false ;
					});
				    return { //闭包的使用 
				       getResult : function(){    
				           return obj;    
				       }  
				    } 
				}
			}
			return new LocalMediaDevices();
		}
	}
});
