var GLOBAL = GLOBAL || {} ;
tk_room.controller('phoneController', function ($scope , $timeout , $interval ,$rootScope , $window, ServiceLiterally  , ServiceNewPptAynamicPPT  , ServiceTools , $location,$sce) {
	$(function(){
		$scope.phone = {
			func:{}
		};
		$rootScope.chinese={
		"timers": {
			"timerSetInterval": {
				"text": "计时器"
			},
			"timerBegin": {
				"text": "开始"
			},
			"timerStop": {
				"text": "暂停"
			},
			"again": {
				"text": "重新开始"
			},
		},
		"dial": {
			"turntable": {
				"text": "转盘"
			}
		},
		"answers": {
			"headerTopLeft": {
				"text": "答题器"
			},
			"headerMiddel": {
				"text": "点击字母预设正确答案"
			},
			"beginAnswer": {
				"text": "开始答题"
			},
			"tureAccuracy": {
				"text": "正确率"
			},
			"trueAnswer": {
				"text": "正确答案"
			},
			"endAnswer": {
				"text": "结束答题"
			},
			"restarting": {
				"text": "重新开始"
			},
			"myAnswer": {
				"text": "我的答案"
			},
			"changeAnswer": {
				"text": "修改答案"
			},
			"selectAnswer": {
				"text": "请至少选择一个答案"
			},
			"submitAnswer": {
				"text": "提交答案"
			},
			"numberOfAnswer": {
				"text": "答题人数"
			}
		},
		"responder": {
			"responder": {
				"text": "抢答器"
			},
			"begin": {
				"text": "开始抢答"
			},
			"restart": {
				"text": "重新开始"
			},
			"close": {
				"text": "关闭"
			},
			"update": {
				"text": "当前浏览器不支持canvas组件请升级！"
			},
			"inAnswer": {
				"text": "抢答中"
			},
			"answer": {
				"text": "抢答"
			},
			"noContest": {
				"text": "无人抢答"
			}
		}
		};
		$rootScope.complex={
			"timers":{
	        "timerSetInterval":{
	            "text":"定時器"
	        },
	        "timerBegin":{
	            "text":"開始"
	        },
	        "timerStop":{
	            "text":"暫停"
	        },
	        "again":{
	            "text":"重新開始"
	        },
		    },
		    "dial":{
		        "turntable":{
		            "text":"轉盤"
		        }
		    },
		    "answers":{
		        "headerTopLeft":{
		            "text":"答題器"
		        },
		        "headerMiddel":{
		            "text":"點擊字母預設正確答案"
		        },
		        "beginAnswer":{
		            "text":"開始答題"
		        },
		        "tureAccuracy":{
		            "text":"正確率"
		        },
		        "trueAnswer":{
		            "text":"正確答案"
		        },
		        "endAnswer":{
		            "text":"結束答題"
		        },
		        "restarting":{
		            "text":"重新開始"
		        },
		        "myAnswer":{
		            "text":"我的答案"
		        },
		        "changeAnswer":{
		            "text":"修改答案"
		        },
		        "selectAnswer":{
		            "text":"請至少選擇壹個答案"
		        },
		        "submitAnswer":{
		            "text":"提交答案"
		        },
		        "numberOfAnswer":{
		            "text":"答題人數"
		        }
		    },
		    "responder":{
		    	"responder":{
		    		"text":"搶答器"
		    	},
		    	"begin":{
		            "text":"開始搶答"
		        },
		        "restart":{
		            "text":"重新開始"
		        },
		        "close":{
		            "text":"關閉"
		        },
		        "update":{
		        	"text":"當前浏覽器不支持canvas組件請升級！"
		        },
		        "inAnswer":{
		        	"text":"搶答中"
		        },
		        "answer":{
		        	"text":"搶答"
		        },
		        "noContest":{
		        	"text":"無人搶答"
		        }
		    }
		};
		$rootScope.english={
		"timers":{
		        "timerSetInterval":{
		            "text":"Timer"
		        },
		        "timerBegin":{
		            "text":"Start"
		        },
		        "timerStop":{
		            "text":"Pause"
		        },
		        "again":{
		            "text":"Restart"
		        },
		    },
		    "dial":{
		        "turntable":{
		            "text":"Turntable"
		        }
		    },
		    "answers":{
		        "headerTopLeft":{
		            "text":"The answer is"
		        },
		        "headerMiddel":{
		            "text":"Click the default answer"
		        },
		        "beginAnswer":{
		            "text":"Begin to answer"
		        },
		        "tureAccuracy":{
		            "text":"Accuracy"
		        },
		        "trueAnswer":{
		            "text":"The right answer"
		        },
		        "endAnswer":{
		            "text":"End of the answer"
		        },
		        "restarting":{
		            "text":"Restart"
		        },
		        "myAnswer":{
		            "text":"My Answer"
		        },
		        "changeAnswer":{
		            "text":"Modify the answer"
		        },
		        "selectAnswer":{
		            "text":"Please select at least one answer"
		        },
		        "submitAnswer":{
		            "text":"Submit the answer"
		        },
		       "numberOfAnswer":{
		        	"text":"Number of answer"
		        }
		    },
		    "responder":{
		    	"responder":{
		    		"text":"Responder"
		    	},
		    	"begin":{
		            "text":"Start"
		        },
		        "restart":{
		            "text":"Restart"
		        },
		        "close":{
		            "text":"Close"
		        },
		         "update":{
		        	"text":"The current browser does not support canvas components please upgrade!"
		        },
		        "inAnswer":{
		        	"text":"In answer"
		        },
		        "answer":{
		        	"text":"Responder"
		        },
		        "noContest":{
		        	"text":"No contest"
		        }
		    }
		}
        $rootScope.hasRole = {} ;
		/*角色对象*/
        $rootScope.role = {};
        Object.defineProperties($rootScope.role, {
            //0：主讲  1：助教    2: 学员   3：直播用户 4:巡检员　10:系统管理员　11:企业管理员　12:管理员
            roleChairman: {
                value: 0,
                writable: false,
            },
            roleTeachingAssistant: {
                value: 1,
                writable: false,
            },
            roleStudent: {
                value: 2,
                writable: false,
            },
            roleAudit: {
                value: 3,
                writable: false,
            },
            rolePatrol: {
                value: 4,
                writable: false,
            }
        });
									
		$rootScope.initPageParameterFormPhone = {//手机端初始化参数
	        mClientType:null , //0:flash,1:PC,2:IOS,3:andriod,4:tel,5:h323	6:html5 7:sip
            serviceUrl:{
                address:null ,
                port:null
			} , //服务器地址
	        addPagePermission:false , //加页权限
	        deviceType:null , //0-手机 , 1-ipad 
	        role:null , //角色
	        classBegin:null , //是否上课,1-上课 , 2-下课， 0-没上课
	        playback:null , //是否是回放
	    };

		$rootScope.joinMeetingMessage = {}; //加入房间的信息
		$rootScope.isClassBegin = false ; //是否上课
        $rootScope.h5Frame = document.getElementById("h5DocumentFrame").contentWindow;
        $rootScope.h5DocumentFileId = null;
        $rootScope.h5DocumentData = null;
        $rootScope.dynamicPptActionClick = false ; 
        $rootScope.h5DocumentActionClick = false ;
        $rootScope.h5IsOnload = false;
        $rootScope.h5ActionJson = {};
        $rootScope.newPptIsOnload = false;
        $rootScope.newPptActionJson = {};


		/*操作的节点*/
	    var $participants = $("#participants");
	    var $videoChatContainer =  $("#video_chat_container") ;
	    var $videoContainer =  $("#video_container");
	    var $messageWin = $("#message_win");
	    var $headerWinMax =  $("#header_win_max");
	    var $headerWinMin =  $("#header_win_min");
	    var $allWrap = $("#all_wrap");
	    var $headerCurrColor = $("#header_curr_color");
	    var $headerColorList  = $("#header_color_list") ;
	    var $header = $("#header"); 
	    var $headerContainer = $header.find("#header_container");
	    var $toolContainer = $("#tool_container");
	    var $fileListEle =  $("#tool_course_list") ;
	    
	    
		/*-------事件绑定start----------------*/
		/*绑定事件来实现白板数据发送 [发送给所有人，除了自己]*/
		$scope.bindDocumentEvent = function(){
			/*绑定事件来实现白板数据发送 [发送给所有人，除了自己]*/
			$(document).off("sendDataToLiterallyEvent");
			$(document).on("sendDataToLiterallyEvent",function (event , testIDPrefix , testData , signallingName , assignId  , do_not_save , toID , associatedMsgID , associatedUserID) {
	            var  currPageNum = ServiceLiterally.ele.attr("data-curr-page")  ;
	            var  currFileId =  ServiceLiterally.ele.attr("data-file-id")  ;
	            var isDelMsg = false ;
	            toID = toID || "__allExceptSender"  ;
	            var testID = assignId || testIDPrefix + "###_"+signallingName+"_"+currFileId+"_"+currPageNum;
	            var eventName  = "publish-message-received" ;
	             testData = JSON.stringify(testData);
	       		var tmpData = {
	       			id:testID , 
	       			toID:toID ,
	       			data:testData ,
	       			signallingName:signallingName ,
	       			eventName:eventName ,
	       		};
	       		if(do_not_save!=undefined){
	       			tmpData.do_not_save = do_not_save;
}
	       		if(associatedMsgID != undefined){
	       			tmpData.associatedMsgID = associatedMsgID;
	       		
	       		}
	            tmpData = JSON.stringify(tmpData);
	            switch ( $rootScope.initPageParameterFormPhone.mClientType){
	                case 2://ios
	                	if(window.webkit && window.webkit.messageHandlers ){
	                		window.webkit.messageHandlers.sendBoardData.postMessage({"data":tmpData});
	                	}else{
	                		console.error("没有方法window.webkit.messageHandlers.sendBoardData.postMessage");
	                	}
	                    break;
	                case 3://android
	                	if(window.JSWhitePadInterface){
	                    	window.JSWhitePadInterface.sendBoardData( tmpData );
	                	}else{
	                		console.error("没有方法window.JSWhitePadInterface.sendBoardData");
	                	}
	                    break;
	                default:
	                	console.error("没有设备类型，无法区分发送给手机的方法");
	                    break;
	            }
	        });
			
			/*鼠标状态是否选中*/
			/*删除白板数据事件*/
			$(document).off("sendWhiteboardMarkTool");
	        $(document).on("sendWhiteboardMarkTool" , function(event , json){
	        	if( !(json && json.isRemote) ){
	        		var selectMouse = $("#tool_mouse").hasClass("active-tool") ;
	        		var testIDPrefix = undefined, testData = {selectMouse: selectMouse} , signallingName = "whiteboardMarkTool" , assignId = "whiteboardMarkTool"  , do_not_save = undefined ;
	        		$(document).trigger("sendDataToLiterallyEvent" , [testIDPrefix , testData , signallingName , assignId  , do_not_save]);
	        	}
	        });
			
			/*删除白板数据事件*/
			$(document).off("deleteLiterallyDataEvent");
	        $(document).on("deleteLiterallyDataEvent" , function(event , testIDPrefix , testData , signallingName , assignId , toID){
	            var  currPageNum = ServiceLiterally.ele.attr("data-curr-page")  ;
	            var  currFileId =  ServiceLiterally.ele.attr("data-file-id")  ;
	            var isDelMsg = true  , do_not_save = true ;
	            toID = toID || "__allExceptSender"  ;
	            var testID = assignId || testIDPrefix + "###_"+signallingName+"_"+currFileId+"_"+currPageNum ;
	            var eventName = "delete-message-received";
	            testData = JSON.stringify(testData);
	            var tmpData = {
	       			id:testID , 
	       			toID:toID ,
	       			data:testData ,
	       			signallingName:signallingName ,
	       			eventName:eventName  ,
	       		};
	       		if(do_not_save!=undefined){
	       			tmpData.do_not_save = do_not_save;
	       		}
	            tmpData = JSON.stringify(tmpData);
	            console.log("deleteLiterallyData data:",tmpData);
	            switch ( $rootScope.initPageParameterFormPhone.mClientType){
	            	case 2://ios
	            		if(window.webkit && window.webkit.messageHandlers ){
	                		window.webkit.messageHandlers.deleteBoardData.postMessage({"data":tmpData});
	                	}else{
	                		console.error("没有方法window.webkit.messageHandlers.deleteBoardData.postMessage");
	                	}
	                    break;
	                case 3://android
	                	if(window.JSWhitePadInterface){
	                    	window.JSWhitePadInterface.deleteBoardData( tmpData );
	                	}else{
	                		console.error("没有方法window.JSWhitePadInterface.deleteBoardData");
	                	}
	                default:
	                    break;
	            }
	        });
	        /*======接收手机数据事件 start ============*/
	        //发送的信息接收事件
	        $(document).off("publish-message-received");
	        $(document).on("publish-message-received", function(event , data) {
	            console.log("publish-message-received: " + JSON.stringify(data));
	            $rootScope.loginController.func.publishMessageReceivedHandler(data);
	        });
	        
	        //信息列表接收事件
	        $(document).off("message-list-received");
	        $(document).on("message-list-received", function(event , data) {
	            console.log("message-list-received: " + JSON.stringify(data));
	            $("#literally_no_write_wrap").show();
				if( $rootScope.page  &&  $rootScope.page.pageOperation && ServiceLiterally.lc ){
					$("#literally_no_write_wrap").show();
                    ServiceLiterally.clearAll(false);
					$rootScope.loginController.func.messageListReceivedHandler(data);
				};
	        });
		     
		     
		    //删除信息接收事件
		    $(document).off("delete-message-received");
	         $(document).on("delete-message-received", function(event , data) {
	            console.log("delete-message-received: " + JSON.stringify(data));
				$rootScope.loginController.func.deleteMessageReceivedHandler(data);
	        });
		            
	        /*======接收手机数据事件 end ============*/
		}
		/*-------事件绑定end----------------*/
		  
		 
	    /*登陆界面控制器参数控制*/
		$rootScope.loginController = {
			func: {},
			parameter: {},
			necessary: {}
		};
		/*call界面控制器参数控制*/
        $scope.callController = {
            func: {},
            parameter: {},
            necessary: {}
        };
                
	    /*publish-message-received事件内部数据 处理函数*/
		$scope.showPageTime = {
	    	media:null ,
	    	file:null
	    } ;
	    $rootScope.loginController.func.publishMessageReceivedHandler = function(data){
	    	console.info("publishMessageReceivedHandler" , data);
	    	if(data && typeof data == "string" ){
				data = JSON.parse( data );
			}
			if(data.params.data && typeof data.params.data == "string" ){
				data.params.data = JSON.parse( data.params.data );
			}
			if( $rootScope.page  &&  $rootScope.page.pageOperation && ServiceLiterally.lc ){
	        	switch (data.params.name){
	        		case "UpdateTime":
	        				
				    			$scope.joinRoomTime=data.params.ts;
				    			if($scope.timeSaveArry!=null){
				    				$scope.timerShowArr($scope.timeSaveArry[0])
				    				
				    			}
				    		
	        			break;
	        		case "SharpsChange":
	        			ServiceLiterally.handlerPubmsg_SharpsChange(data.params);
	        			break;
					case "ShowPage":
						$timeout(function () {
                            if(!data.params.data.isMedia) {
                                var isopenH5File = ($rootScope.h5DocumentFileId !== data.params.data.filedata.fileid);
                                $rootScope.h5DocumentFileId = data.params.data.filedata.fileid;//保存当前文档id
                                if(data.params.data.isGeneralFile){
                                	$rootScope.roomPage.flag.isShow.isCourseFile = true ;
                                    $rootScope.roomPage.flag.isShow.isH5File = false ;
                                    ServiceLiterally.saveLcStackToStorage({
                                        saveRedoStack: $rootScope.hasRole.roleChairman  ,
									});
                                    ServiceLiterally.setFileDataToLcElement(data.params.data.filedata);
                                    $rootScope.page.pageOperation(null, false);
                                    $("#prev_page_phone").show();
                                    $("#next_page_phone").show();
                                    $("#add_literally_page_phone").hide();
                                    $("#prev_page_phone_slide").hide();
                                    $("#next_page_phone_slide").hide();
                                    $("#prev_page_phone_h5").hide();
                                    $("#next_page_phone_h5").hide();
                                }else if(data.params.data.isDynamicPPT){
                                    $rootScope.roomPage.flag.isShow.isCourseFile = false ;
                                    $rootScope.roomPage.flag.isShow.isH5File = false ;
                                    var isremote = true ;
                                    var isOpenPPT  = false;
                                    var pptslide = data.params.data.filedata.pptslide ;
                                    var pptstep = data.params.data.filedata.pptstep ;
                                    var pptfileid = data.params.data.filedata.fileid ;
                                    if(data.params.data.action === "show"){
                                        isOpenPPT = true ;
                                        ServiceLiterally.saveLcStackToStorage({
                                            saveRedoStack: $rootScope.hasRole.roleChairman  ,
                                        });
                                        ServiceLiterally.setFileDataToLcElement(data.params.data.filedata);
                                        $rootScope.page.recoverLcData();
									}
                                    $(document).trigger("uploadAynamicPptProgress" , [ pptslide , pptstep  , pptfileid , isOpenPPT , isremote   ] );
                                    $("#prev_page_phone_slide").show();
                                    $("#next_page_phone_slide").show();
                                    $("#prev_page_phone").hide();
                                    $("#next_page_phone").hide();
                                    $("#add_literally_page_phone").hide();
                                    $("#prev_page_phone_h5").hide();
                                    $("#next_page_phone_h5").hide();
                                }else if (data.params.data.isH5Document) {
                                    $rootScope.roomPage.flag.isShow.isCourseFile = false ;
                                    $rootScope.roomPage.flag.isShow.isH5File = true ;
                                    $("#prev_page_phone").hide();
                                    $("#next_page_phone").hide();
                                    $("#add_literally_page_phone").hide();
                                    $("#prev_page_phone_slide").hide();
                                    $("#next_page_phone_slide").hide();
                                    $("#prev_page_phone_h5").show();
                                    $("#next_page_phone_h5").show();
                                    ServiceLiterally.saveLcStackToStorage({
                                        saveRedoStack: $rootScope.hasRole.roleChairman  ,
                                    });
                                    ServiceLiterally.setFileDataToLcElement(data.params.data.filedata);
                                    $rootScope.page.recoverLcData();

                                    $scope.callController.func.handlerReceiveShowPageSignalling(data.params.data,isopenH5File);
								}
                            } 
                        },0);           
	        			break;
					case "ClassBegin": //上课
						$rootScope.isClassBegin = true; //上课状态
                        $(document).trigger("onStartAttend");            
               			  if($rootScope.isClassBegin &&  !($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant) ){
       		
								$(".tl-tool-teacher").hide();
       					
//                      	$("#page_wrap").removeClass("no-permission");
                            //$("#ppt_next_page_slide , #ppt_prev_page_slide , #next_page , #prev_page , #add_literally_page").addClass("disabled").attr("disabled","disabled").attr("data-set-disabled" ,'no');
                        }
               			$scope.callController.func._handlerNewpptClassBeginStartOrEnd();
               	
						break;
					case "NewPptTriggerActionClick": //动态PPT动作		
					    //console.log("NewPptTriggerActionClick" , data.params);
                        if($rootScope.roomPage.flag.isShow.isCourseFile === false &&  $rootScope.hasRole.roleChairman === false && $rootScope.roomPage.flag.isShow.isH5File == false ){
                            var newSlide = data.params.data.slide;
                            var newFileid = data.params.data.fileid;
                            newSlide = newSlide+1;
                            var filedata = ServiceLiterally.getFileDataFromLcElement();//之前的文件信息
                            var currpage = filedata.currpage;
                            var fileid = filedata.fileid;
							if ((currpage !== newSlide || newFileid !== fileid) && $rootScope.initPageParameterFormPhone.playback) {
								if ($rootScope.newPptActionJson[newSlide] && $rootScope.newPptActionJson[newSlide].length > 0) {
									$rootScope.newPptActionJson[newSlide].push(data.params.data);
								} else {
									$rootScope.newPptActionJson[newSlide] = [];
									$rootScope.newPptActionJson[newSlide].push(data.params.data);
								}
								console.info('iframe加载好之前保存ppt动作：', $rootScope.newPptActionJson);
							}
							ServiceNewPptAynamicPPT.postMessage(data.params.data);
                        }
						break;
                    case "H5DocumentAction": //h5文件动作
						if ($rootScope.h5IsOnload === false && $rootScope.initPageParameterFormPhone.playback) {//iframe加载好之前保存H5文件动作
                            var filedata = ServiceLiterally.getFileDataFromLcElement();
                            var currpage = filedata.currpage;
                            if ($rootScope.h5ActionJson[currpage] && $rootScope.h5ActionJson[currpage].length > 0) {
                                $rootScope.h5ActionJson[currpage].push(data.params.data);
                            }else {
                                $rootScope.h5ActionJson[currpage] = [];
                                $rootScope.h5ActionJson[currpage].push(data.params.data);
                            }
                            console.info('iframe加载好之前保存H5文件动作：',$rootScope.h5ActionJson);
						}
                        $rootScope.h5Frame.postMessage(JSON.stringify(data.params.data),'*');
                        break;
                    case "whiteboardMarkTool": //标注工具
                    	var data = data.params.data ;
                    	var selectMouse = data.selectMouse ;
                    	var isRemote = true ;   
                    	var selectM = $("#tool_mouse").hasClass("active-tool")  ;
                    	if(typeof selectMouse === 'number'){
							selectMouse = (selectMouse !== 0) ;
						}
                    	if( selectM !== selectMouse ){
                    		if(selectMouse){
	                    		if($rootScope.initPageParameterFormPhone.deviceType === 1){
	                    			$("#tool_mouse").trigger("click" , [false ,{isRemote:isRemote}]);	
	                    		}else{
	                    			$("#tool_mouse_phone").trigger("click" , [false , {isRemote:isRemote}]);
	                    		}                    	   
	                    	}else{
                    			if($rootScope.initPageParameterFormPhone.deviceType === 1){
	                    			$("#tool_pencil").trigger("click" , [false ,{isRemote:isRemote}]);	
	                    		}else{
	                    			$("#tool_pencil_phone").trigger("click" , [false , {isRemote:isRemote}]);
	                    		}  	                    	
	                    	}
                    	}                    	
                    	break;
  					case "timer": 
  						var data = data.params ;
  						$timeout(function(){
  							$scope.timerShowArr(data);
  						},10)
  						
                        break;
                    case "answer":
                    var data = data.params.data ;
					$timeout(function() {
					   	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ||$rootScope.hasRole.rolePatrol) { 
					   		$scope.langeTYPE();
					   		$scope.initArr = data.optionalAnswers;
					   		$scope.trueSelectArry = data.rightAnswers;
					   		if(data.isShow){		
					   			$scope.clearStudetnAnswerData();
					   			
					   			$scope.initArr = [{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false}];
					   			for(var i=0;i<$scope.initArr.length;i++){
					   				$(".answer-teach-begin").css("background","#074496");
									$(".answer-teach-lis").eq(i).css("background","#1e2b48");
								}
					   			$(".answer-teach-add").css("background", "#368bcb");
								$(".answer-teach-reduce").css("background", "#368bcb");
					   			$(".answer-teach-wrapDiv").show();
					   			$(".result-teach-wrapDiv").hide();
					   		} else {
					   			if(data.isRound){
					   				$("#result-teach-mytime").html(data.quizTimes); 
					   				$interval.cancel($scope.stopAnswerTime);
					   				$scope.showTeacher = data.teacherSelect;
					   				$(".trueRel").html(data.allNumbers);
					   				$(".trueRelstu").html(data.trueLV);
					   				$("#result-teach-mytime").html("");
					   				$(".answer-teach-wrapDiv").hide();
					   				$(".result-teach-wrapDiv").show();
					   				$(".result-teach-restart-question").show();
									$(".result-teach-end-question").hide();
									$(".result-teach-close").show();
									if($scope.language=="ch"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
									if($scope.language=="en"){$(".answersPeople-div").css("width",2+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.1+"rem");$(".result-teach-end-question").css("lineHeight",0.2+"rem");}
									if($scope.language=="tw"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
					   			}else{	
					   				$scope.timeFun();
					   				$(".result-teach-restart-question").hide();
									$(".result-teach-end-question").show();
									$(".answer-teach-wrapDiv").hide();
					   				$(".result-teach-wrapDiv").show();
					   				$(".result-teach-close").hide();
					   			}
					   			
					   		}
					   	}else{
					   		$scope.langeTYPE();
					   		if(data.isShow){
					   			$scope.clearStudetnAnswerData();
					   			$(".answer-student-wrapDiv").hide();
						   		$(".result-student-wrapDiv").hide();
					   		}else{
					   			if($scope.language=="ch"){
							    	$(".answer-teach-header-left-grey").html($rootScope.chinese.answers.headerTopLeft.text);
						   			$(".answer-teach-header-left-green").html($rootScope.chinese.answers.headerMiddel.text);
						    	}if($scope.language=="en"){
										$(".answer-teach-header-left-grey").html($rootScope.english.answers.headerTopLeft.text);
						   				$(".answer-teach-header-left-green").html($rootScope.english.answers.headerMiddel.text);
										
						    		}if($scope.language=="tw"){
						    			$(".answer-teach-header-left-grey").html($rootScope.chinese.answers.headerTopLeft.text);
						   				$(".answer-teach-header-left-green").html($rootScope.chinese.answers.headerMiddel.text);
						    		}
					   			
					   		if(data.isRound) {
					   			var userId = $scope.studentAnswerID
					   			$scope.showTeacher = data.rightAnswers;
					   			$scope.showTeacher = $scope.showTeacher.sort();
					   			$("#result-teach-mytimes").html("");
					   			$("#result-teach-mytimes").html(data.quizTimes);
					   			$(".roleNum").html(data.allNumbers);
					   			$(".trueRelstu").html(data.trueLV)
					   			$scope.idA = [];
					   			$scope.idB = [];
					   			$scope.idC = [];
					   			$scope.idD = [];
					   			$scope.idE = [];
					   			$scope.idF = [];
					   			$scope.idG = [];
					   			$scope.idH = [];
					
					   			for(var i in data.dataChoose) {
					   				for(var item in data.dataChoose[i]){
					   				data.dataChoose[i][item].forEach(function(item, index) {
					   					if(item == "A") {
					   						$scope.idA.push(userId);
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-A-stu").empty();
					   						liAp.textContent = $scope.idA.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idA.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-A-stu").append(liA);
					   					}
					   					if(item == "B") {
					   						$scope.idB.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-B-stu").empty();
					   						liAp.textContent = $scope.idB.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idB.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-B-stu").append(liA);
					   					}
					   					if(item == "C") {
					   						$scope.idC.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-C-stu").empty();
					   						liAp.textContent = $scope.idC.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idC.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-C-stu").append(liA);
					
					   					}
					   					if(item == "D") {
					   						$scope.idD.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-D-stu").empty();
					   						liAp.textContent = $scope.idD.length;;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idD.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-D-stu").append(liA);
					
					   					}
					   					if(item == "E") {
					   						$scope.idE.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-E-stu").empty();
					   						liAp.textContent = $scope.idE.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idE.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-E-stu").append(liA);
					
					   					}
					   					if(item == "F") {
					   						$scope.idF.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-F-stu").empty();
					   						liAp.textContent = $scope.idF.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idF.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-F-stu").append(liA);
					
					   					}
					   					if(item == "G") {
					   						$scope.idG.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-G-stu").empty();
					   						liAp.textContent = $scope.idG.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idG.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-G-stu").append(liA);
					   					}
					   					if(item == "H") {
					   						$scope.idH.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-H-stu").empty();
					   						liAp.textContent = $scope.idH.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idH.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-H-stu").append(liA);
					   					}
					   				});
					   			}
					   			}
					   			$(".result-student-wrapDiv").show();
					   			$(".answer-student-wrapDiv").hide();
					   			if($scope.language=="ch"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
								if($scope.language=="en"){$(".answersPeople-div").css("width",2+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.1+"rem");$(".result-teach-end-question").css("lineHeight",0.2+"rem");}
								if($scope.language=="tw"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
					   		}else{
					   			for(var i = 0; i < data.optionalAnswers.length; i++) {
					   			$(".answer-student-lis").eq(i).css("background", "");
					   			$(".reuslt-submit").css("background", "");
					   			data.optionalAnswers[i].sel = false;
						   		};
						   		if($scope.language=="tw"){
						   			$(".reuslt-submit").html($rootScope.complex.answers.submitAnswer.text);
						   		}
						   		if($scope.language=="ch"){
						   			$(".reuslt-submit").html($rootScope.chinese.answers.submitAnswer.text);
						   		}
						   		if($scope.language=="en"){
						   			$(".reuslt-submit").html($rootScope.english.answers.submitAnswer.text);
						   		}
						   		$scope.teacherResult = data.optionalAnswers;
						   		$(".answer-student-wrapDiv").show();
						   		$(".result-student-wrapDiv").hide();
						   		$(".changeOneAnswer").hide();
						   	}
					   	}
					   		}
					   }, 0);
                    	break;
                    case "submitAnswers"://提交答案  
                    	var userSelect=data.params.data.mySelect;
                    	var userId=data.params.data.sendUserID;
                    	var userName = data.params.data.sendStudentName;
                    	$timeout(function(){
                    		$scope.langeTYPE();
                    		$scope.allStudentChosseAnswer[userId]={};
							$scope.allStudentChosseAnswer[userId][userName] = userSelect;	
							$scope.studentNumbers.push(userId);
							for(var i=0;i<$scope.studentNumbers.length;i++){
							if(userId==$scope.studentNumbers[i]){
								$scope.allStudentChosseAnswer[userId][userName] = userSelect;	
								}
							}
							$scope.idA=[];
						    $scope.idB=[];
						    $scope.idC=[];
						    $scope.idD=[];
						    $scope.idE=[];
						    $scope.idF=[];
						    $scope.idG=[];
						    $scope.idH=[];
						    $(".result-A").html("");
						    $(".result-B").html("");
						    $(".result-C").html("");
						    $(".result-D").html("");
						    $(".result-E").html("");
						    $(".result-F").html("");
						    $(".result-G").html("");
						    $(".result-H").html("");
							$scope.studentNums=0;
							$(".roleNums").html(0);
							$(".trueRel").html(0);
						for(var i in $scope.allStudentChosseAnswer){
							for(var a in $scope.allStudentChosseAnswer[i]){
								var namesValue = a;
							$scope.allStudentChosseAnswer[i][a].forEach(function(item,index){
								if(item=="A"){
									$scope.idA.push(namesValue);
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-A").empty();
										liAp.textContent=$scope.idA.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idA.length+"rem";
										liA.appendChild(liAp)
										$(".result-A").append(liA);
								}
								if(item=="B"){
									$scope.idB.push(namesValue)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-B").empty();
										liAp.textContent=$scope.idB.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idB.length+"rem";
										liA.appendChild(liAp)
										$(".result-B").append(liA);
								}if(item=="C"){
									$scope.idC.push(namesValue)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-C").empty();
										liAp.textContent=$scope.idC.length;
										liA.className="answer-stu-lis";
										liA.appendChild(liAp)
										liA.style.height=0.1*$scope.idC.length+"rem";
									$(".result-C").append(liA);
									
								}if(item=="D"){
									$scope.idD.push(namesValue)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-D").empty();
										liAp.textContent=$scope.idD.length;;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idD.length+"rem";
										liA.appendChild(liAp)
									$(".result-D").append(liA);
									
								}
								if(item=="E"){
									$scope.idE.push(namesValue)
										var liA=document.createElement("div");
										var liAp=document.createElement("p");
										$(".result-E").empty();
										liAp.textContent=$scope.idE.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idE.length+"rem";
										liA.appendChild(liAp)
								$(".result-E").append(liA);
									
								}if(item=="F"){
									$scope.idF.push(namesValue)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-F").empty();
										liAp.textContent=$scope.idF.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idF.length+"rem";
										liA.appendChild(liAp)
									$(".result-F").append(liA);
									
								}if(item=="G"){
									$scope.idG.push(namesValue)
								var liA=document.createElement("div");
								var liAp=document.createElement("p");
									$(".result-G").empty();
									liAp.textContent=$scope.idG.length;
									liA.className="answer-stu-lis";
									liA.appendChild(liAp)
									liA.style.height=0.1*$scope.idG.length+"rem";
								$(".result-G").append(liA);	
								}
								if(item=="H"){
									$scope.idH.push(namesValue)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
									$(".result-H").empty();
									liAp.textContent=$scope.idH.length;
									liA.className="answer-stu-lis";
									liA.style.height=0.1*$scope.idH.length+"rem";
									liA.appendChild(liAp)
								$(".result-H").append(liA);
									
								}
						})
							$scope.studentNumbers=Array.from(new Set($scope.studentNumbers));
							if($scope.allStudentChosseAnswer[i][namesValue].sort().toString()==$scope.trueSelectArry.sort().toString()){
	            				$scope.studentNums++;
	            				var studentLength=$scope.studentNumbers.length;
								$(".roleNums").html(studentLength)
	            		
	            				$scope.trueLV=$scope.studentNums/studentLength*100;
	            				$scope.trueLV=$scope.trueLV.toFixed(0)
	            				$(".trueRel").html($scope.trueLV)
	            			}else{
	            				var studentLength=$scope.studentNumbers.length;
								$(".roleNums").html(studentLength)
	            	
	            				$scope.trueLV=$scope.studentNums/studentLength*100;
	            				$scope.trueLV=$scope.trueLV.toFixed(0)
	            				$(".trueRel").html($scope.trueLV)
	            			}
	            		}
					}
                    	$scope.showStudent = data.mySelect;
                    

				},0)
                    	break;
				case "dial":
					var data = data.params.data ;
					var dialTrun = data.rotationAngle;
					if(data.isShow){
							$(".dial-teachComponent-turntable").css("transform",0);
                    		$(".dial-teachComponent-bg").show();
	                     if($rootScope.hasRole.roleStudent){
	                    	$(".dialCloseP").hide();
	                    }else{
	                    	$(".dialCloseP").show();
                     		}
	                    }else{
	                    		$(".dial-teachComponent-bg").show();
	                    		$(".dialCloseP").hide();
	                    		$(".dial-teachComponent-turntable").css("transform",dialTrun);
	                    		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
	                    		$timeout(function(){
		                    	 $(".dialCloseP").show();
		                    	 },6000)
	                    	} 
                    }
					break;
				case "qiangDaQi":
					var data = data.params.data ;
						if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ||$rootScope.hasRole.rolePatrol){
							if(data.isShow){
								$(".responder-circle").show();
								if($scope.language=="tw"){
						   			$(".responder-begin-circle").html($rootScope.complex.responder.begin.text);
						   		}
						   		if($scope.language=="ch"){
						   			$(".responder-begin-circle").html($rootScope.chinese.responder.begin.text);
						   		}
						   		if($scope.language=="en"){
						   			$(".responder-begin-circle").html($rootScope.english.responder.begin.text);
						   		}
					    		$(".responder-close-img").hide();
					    		$(".responder-restart-img").hide();
								if(data.begin){
									$timeout(function(){
						            	$(".responder-close-img").show();
						    			$(".responder-restart-img").show();
							    		if($scope.language=="ch"){
							    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
							    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
							    		}if($scope.language=="en"){
							    			$(".responder-close-img").html($rootScope.english.responder.close.text);
							    			$(".responder-restart-img").html($rootScope.english.responder.restart.text);
							    		}if($scope.language=="tw"){
							    			$(".responder-close-img").html($rootScope.complex.responder.close.text);
							    			$(".responder-restart-img").html($rootScope.complex.responder.restart.text);
							    		}
						              },6000);
						              if($scope.language=="ch"){
											$(".responder-begin-circle-student").html($rootScope.chinese.responder.answer.text)
							    		}if($scope.language=="en"){
							    			$(".responder-begin-circle-student").html($rootScope.english.responder.answer.text)
							    		}if($scope.language=="tw"){
							    			$(".responder-begin-circle-student").html($rootScope.complex.responder.answer.text)
							    		}
								}
							}
						}else{
							if(data.isShow && data.begin){
								$(".responder-circle-student").show();
							}
						}
					break;
				case "QiangDaZhe":
					var data = data.params;
						if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ||$rootScope.hasRole.rolePatrol){
							if(data.data.isClick){
								$scope.responderUserSort[data.fromID]={};
			                	$scope.responderUserSort[data.fromID][pubmsgData.ts]=data.data.userAdmin;
			                	$scope.responderUserArry=[];
			                	for(var item in $scope.responderUserSort){
			                		for(var i in $scope.responderUserSort[item]){
			                			$scope.responderUserArry.push(i);
			                			$scope.responderUserArry = $scope.responderUserArry.sort()
			                			 if($scope.responderUserSort[item][$scope.responderUserArry[0]]!=undefined){
			                			 	 $(".responder-begin-circle").html($scope.responderUserSort[item][$scope.responderUserArry[0]]);
			                			 }
			                		}
			                	}
			                }else{
									if($scope.language=="ch"){
						    			$(".responder-begin-circle").html($rootScope.chinese.responder.noContest.text);						    	
						    		}if($scope.language=="en"){
						    			$(".responder-begin-circle").html($rootScope.chinese.responder.noContest.text);						    			
						    		}if($scope.language=="tw"){
						    			$(".responder-begin-circle").html($rootScope.chinese.responder.noContest.text);
						    		}
			                	}
						}if($rootScope.hasRole.roleStudent){
							if(data.data.isClick){
								$scope.responderUserSort[data.fromID]={};
			                	$scope.responderUserSort[data.fromID][pubmsgData.ts]=data.data.userAdmin;
			                	$scope.responderUserArry=[];
			                	for(var item in $scope.responderUserSort){
			                		for(var i in $scope.responderUserSort[item]){
			                			$scope.responderUserArry.push(i);
			                			$scope.responderUserArry = $scope.responderUserArry.sort();
			                			 if($scope.responderUserSort[item][$scope.responderUserArry[0]]!=undefined){
			                			 	 $(".responder-begin-circle-student").html($scope.responderUserSort[item][$scope.responderUserArry[0]]);
			                			 }
			                		}
			                	}
			                }else{
									if($scope.language=="ch"){
						    			$(".responder-begin-circle-student").html($rootScope.chinese.responder.noContest.text);						    	
						    		}if($scope.language=="en"){
						    			$(".responder-begin-circle-student").html($rootScope.chinese.responder.noContest.text);						    			
						    		}if($scope.language=="tw"){
						    			$(".responder-begin-circle-student").html($rootScope.chinese.responder.noContest.text);
						    		}
			           			
			                	}
						}
					break;
	        	default:
	        			break;
	        	}
	    	}
	 	};
	 	/*delete-message-received事件内部数据 处理函数*/
	    $rootScope.loginController.func.deleteMessageReceivedHandler = function(data){
	    	console.info("deleteMessageReceivedHandler" , data);
			if(data && typeof data == "string" ){
				data = JSON.parse( data );
			}
			if(data.params.data && typeof data.params.data == "string" ){
				data.params.data = JSON.parse( data.params.data );
			}
			switch (data.params.name){
				case "ClassBegin": //删除上课（也就是下课了）
						$rootScope.isClassBegin = false; //下课状态
                    	$(document).trigger("onEndAttend");
						$(".tl-tool-teacher").hide();
						$(".timer-teachTool-wrap").hide();
						$(".timer-studentTool-wrap").hide();
						$(".answer-teach-wrapDiv").hide();
						$(".result-teach-wrapDiv").hide();
						$(".answer-student-wrapDiv").hide();
						$(".result-student-wrapDiv").hide();
						$(".dial-teachComponent-bg").hide();
						$(".responder-circle").hide();
						$(".responder-circle-student").hide();
						$(".responder-close-img").hide();
						$(".responder-restart-img").hide();
                    	$scope.callController.func._handlerNewpptClassBeginStartOrEnd();
						break;
				case "SharpsChange"://删除白板数据
                    ServiceLiterally.handlerDelmsg_SharpsChange(data.params);
					break;
				case "timer":
					data.params.data.sutdentTimerArry=[0,5,0,0];
					$interval.cancel($scope.stop);
					$(".timer-studentTool-wrap").hide();
					$(".timer-teachTool-wrap").hide();
					$(".timer-studentTool-num-content").eq(3).text(0);
					$(".timer-studentTool-num-content").eq(2).text(0);
					$(".timer-studentTool-num-content").eq(1).text(5);
					$(".timer-studentTool-num-content").eq(0).text(0);
					$(".timer-studentTool-num-content").css("color","black");
					break;
		
				case "dial":
					$(".dial-teachComponent-bg").hide();
					$scope.deg = 45, //旋转的默认角度360/8
				    $scope.numdeg = 0, //记录上次旋转停止时候的角度
				    $scope.num = 0;//记录旋转后数组的位置
					break;
				case "answer":
					$(".result-teach-wrapDiv").hide();
					$(".answer-teach-wrapDiv").hide();
					$scope.clearStudetnAnswerData();
					$(".answer-student-wrapDiv").hide();
					$(".result-student-wrapDiv").hide();
					$(".changeOneAnswer").hide();
					$(".result-student-wrapDiv ").hide();
					$(".answer-student-wrapDiv ").hide();
					break;
				case "qiangDaQi":
					$(".responder-circle").hide();
					$(".responder-circle-student").hide();
					$(".responder-close-img").hide();
					$(".responder-restart-img").hide();
					break;
			};
	    }
		
		/*message-list-received事件内部处理函数*/
	    $rootScope.loginController.func.messageListReceivedHandler = function(data){
	    	
	    	console.info("messageListReceivedHandler" , data);
	    	


	    	if(data && typeof data == "string" ){
				data = JSON.parse( data );
			}
			/*
			    @function     JsonSort 对json排序
			    @param        json     用来排序的json
			    @param        key      排序的键值
			*/
			function JsonSort(json,key){
			    for(var j=1,jl=json.length;j < jl;j++){
			        var temp = json[j],
			            val  = temp[key],
			            i    = j-1;
			        while(i >=0 && json[i][key]>val){
			            json[i+1] = json[i];
			            i = i-1;    
			        }
			        json[i+1] = temp;
			        
			    }
			    return json;
			}
			
			if( $rootScope.page  &&  $rootScope.page.pageOperation && ServiceLiterally.lc ){
				$rootScope.shapeJson = {};
				var resultData = data.params ;
				for (var x in resultData ) {
					if(resultData[x].data && typeof resultData[x].data == "string" ){
	        			resultData[x].data = JSON.parse( resultData[x].data );
	        		}
					if(resultData[x].name == "SharpsChange") {
						if($rootScope.shapeJson["SharpsChange"] == null || $rootScope.shapeJson["SharpsChange"] == undefined) {
							$rootScope.shapeJson["SharpsChange"] = [];
							$rootScope.shapeJson["SharpsChange"].push(resultData[x]);
						} else {
							$rootScope.shapeJson["SharpsChange"].push(resultData[x]);
						}
					} else if(resultData[x].name == "ShowPage") {
						if($rootScope.shapeJson["ShowPage"] == null || $rootScope.shapeJson["ShowPage"] == undefined) {
							$rootScope.shapeJson["ShowPage"] = [];
							$rootScope.shapeJson["ShowPage"].push(resultData[x]);
						} else {
							$rootScope.shapeJson["ShowPage"].push(resultData[x]);
						}
					}  else if(resultData[x].name == "ClassBegin") {
                        if($rootScope.shapeJson["ClassBegin"] == null || $rootScope.shapeJson["ClassBegin"] == undefined) {
                            $rootScope.shapeJson["ClassBegin"] = [];
                            $rootScope.shapeJson["ClassBegin"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["ClassBegin"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "whiteboardMarkTool") {
                        if($rootScope.shapeJson["whiteboardMarkTool"] == null || $rootScope.shapeJson["whiteboardMarkTool"] == undefined) {
                            $rootScope.shapeJson["whiteboardMarkTool"] = [];
                            $rootScope.shapeJson["whiteboardMarkTool"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["whiteboardMarkTool"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "timer") {
                        if($rootScope.shapeJson["timer"] == null || $rootScope.shapeJson["timer"] == undefined) {
                            $rootScope.shapeJson["timer"] = [];
                            $rootScope.shapeJson["timer"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["timer"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "dial") {
                        if($rootScope.shapeJson["dial"] == null || $rootScope.shapeJson["dial"] == undefined) {
                            $rootScope.shapeJson["dial"] = [];
                            $rootScope.shapeJson["dial"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["dial"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "answer") {
                        if($rootScope.shapeJson["answer"] == null || $rootScope.shapeJson["answer"] == undefined) {
                            $rootScope.shapeJson["answer"] = [];
                            $rootScope.shapeJson["answer"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["answer"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "submitAnswers") {
                        if($rootScope.shapeJson["submitAnswers"] == null || $rootScope.shapeJson["submitAnswers"] == undefined) {
                            $rootScope.shapeJson["submitAnswers"] = [];
                            $rootScope.shapeJson["submitAnswers"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["submitAnswers"].push(resultData[x]);
                        }
                    }else if(resultData[x].name == "qiangDaQi") {
                        if($rootScope.shapeJson["qiangDaQi"] == null || $rootScope.shapeJson["qiangDaQi"] == undefined) {
                            $rootScope.shapeJson["qiangDaQi"] = [];
                            $rootScope.shapeJson["qiangDaQi"].push(resultData[x]);
                        } else {
                            $rootScope.shapeJson["qiangDaQi"].push(resultData[x]);
                        }
                    }
	            }
	        	
	        	
        	 	/*数据排序*/
//	        	if(  $rootScope.initPageParameterFormPhone  &&  $rootScope.initPageParameterFormPhone.mClientType!=null  &&  $rootScope.initPageParameterFormPhone.mClientType!=undefined  &&  $rootScope.initPageParameterFormPhone.mClientType === 1  ){
        		for(var x in $rootScope.shapeJson ){
	        		if($rootScope.shapeJson[x] && $rootScope.shapeJson[x].length>0){
	        			$rootScope.shapeJson[x] = JsonSort($rootScope.shapeJson[x],"seq") ;
	        		}
				}
//	        	}


				/*上课数据*/
                var classBeginArr = $rootScope.shapeJson["ClassBegin"];
                if(classBeginArr != null && classBeginArr != undefined && classBeginArr.length > 0) {
               
                    if(classBeginArr[classBeginArr.length - 1].name == "ClassBegin") {
                        $rootScope.isClassBegin = true; //已经上课
                        $(document).trigger("onStartAttend");
                        if( $rootScope.isClassBegin &&  !($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant)  ){
                        	$(".tl-tool-teacher").hide();
//                      	$rootScope.roomPage.flag.isShow.isCourseFile = true;
//                      	$("#page_wrap").removeClass("no-permission");
                           // $("#ppt_next_page_slide , #ppt_prev_page_slide , #next_page , #prev_page , #add_literally_page").addClass("disabled").attr("disabled","disabled").attr("data-set-disabled" ,'no');
                        }
                        $scope.callController.func._handlerNewpptClassBeginStartOrEnd();
                    }
                }
                $rootScope.shapeJson["ClassBegin"] = null ;
													
				/*标注工具*/
	            var whiteboardMarkToolArr = $rootScope.shapeJson["whiteboardMarkTool"];
	            if(whiteboardMarkToolArr != null && whiteboardMarkToolArr != undefined && whiteboardMarkToolArr.length > 0) {
            	 	if(whiteboardMarkToolArr[whiteboardMarkToolArr.length - 1].name == "whiteboardMarkTool") {
	                 	var data = whiteboardMarkToolArr[whiteboardMarkToolArr.length - 1].data ;
                    	var selectMouse = data.selectMouse ;
                    	var isRemote = true ;   
                    	var selectM = $("#tool_mouse").hasClass("active-tool")  ;
                    	if(typeof selectMouse === 'number'){
							selectMouse = (selectMouse !== 0) ;
						}
                    	if( selectM !== selectMouse ){
                    		if(selectMouse){
	                    		if($rootScope.initPageParameterFormPhone.deviceType === 1){
	                    			$("#tool_mouse").trigger("click" , [false ,{isRemote:isRemote}]);	
	                    		}else{
	                    			$("#tool_mouse_phone").trigger("click" , [false , {isRemote:isRemote}]);
	                    		}                    	   
	                    	}else{
                    			if($rootScope.initPageParameterFormPhone.deviceType === 1){
	                    			$("#tool_pencil").trigger("click" , [false ,{isRemote:isRemote}]);	
	                    		}else{
	                    			$("#tool_pencil_phone").trigger("click" , [false , {isRemote:isRemote}]);
	                    		}  	                    	
	                    	}
                    	}                  
	                }
	            }
	            $rootScope.shapeJson["whiteboardMarkToolArr"] = null;


       			/*画笔数据*/
	            var sharpsChangeArr = $rootScope.shapeJson["SharpsChange"];
	            if(sharpsChangeArr != null && sharpsChangeArr != undefined && sharpsChangeArr.length > 0) {
	            	ServiceLiterally.handlerMsglist_SharpsChange(sharpsChangeArr);
	            }
	            $rootScope.shapeJson["SharpsChange"] = null;

				/*转盘数据*/ 
            	var timerShowArr = $rootScope.shapeJson["timer"];
            	if(timerShowArr != null && timerShowArr != undefined && timerShowArr.length > 0) {
            			$scope.timeSaveArry = timerShowArr;
            		$timeout(function(){
  							$scope.timerShowArr(timerShowArr[0]);
  						},10)
	            }
	            $rootScope.shapeJson["timer"] = null;
	           
	            /*答题卡开始数据*/
	            var answerArr = $rootScope.shapeJson["answer"];
	            if(answerArr != null && answerArr != undefined && answerArr.length > 0) {
	            	$timeout(function(){
	            		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ||$rootScope.hasRole.rolePatrol) { 		
					   		$scope.trueSelectArry = answerArr[0].data.rightAnswers;
	            			$scope.initArr = answerArr[0].data.optionalAnswers;
	            			var data = answerArr[0].data;
	            			$scope.langeTYPE();
					   		if(data.isShow){
					   			$(".answer-teach-wrapDiv").show();
					   			$(".result-teach-wrapDiv").hide();
					   		} else {
					   			
					   			if(answerArr[0].data.isRound){
					   				$interval.cancel($scope.stopAnswerTime);
					   				$scope.showTeacher = answerArr[0].data.rightAnswers;
					   				$(".roleNums").html(answerArr[0].data.allNumbers);
					   				$(".trueRel").html(answerArr[0].data.trueLV);
					   				$("#result-teach-mytime").html("");
					   				$("#result-teach-mytime").html(answerArr[0].data.quizTimes);
					   				$(".answer-teach-wrapDiv").hide();
					   				$(".result-teach-wrapDiv").show();
					   				$(".result-teach-restart-question").show();
									$(".result-teach-end-question").hide();
									$(".result-teach-close").show();
									if($scope.language=="ch"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
									if($scope.language=="en"){$(".answersPeople-div").css("width",2+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.1+"rem");$(".result-teach-end-question").css("lineHeight",0.2+"rem");}
									if($scope.language=="tw"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
					   			}else{	
					   				$scope.timeFun();
					   				$(".result-teach-restart-question").hide();
									$(".result-teach-end-question").show();
									$(".answer-teach-wrapDiv").hide();
					   				$(".result-teach-wrapDiv").show();
					   				$(".result-teach-close").hide();
					   			}
					   			
					   		}
					   	}else{
					   		$scope.teacherResult = answerArr[0].data.optionalAnswers;
					   		$scope.langeTYPE();
					   		if(answerArr[0].data.isShow){
					   			$(".answer-student-wrapDiv").hide();
						   		$(".result-student-wrapDiv").hide();
					   		} else {
					   		if(answerArr[0].data.isRound) {
					   			var userId = $scope.studentAnswerID
					   			$scope.showTeacher = answerArr[0].data.rightAnswers;
					   			$scope.showTeacher = $scope.showTeacher.sort();
					   			$("#result-teach-mytimes").html("");
					   			$("#result-teach-mytimes").html(answerArr[0].data.quizTimes);
					   			$(".roleNum").html(answerArr[0].data.allNumbers);
					   			$(".trueRelstu").html(answerArr[0].data.trueLV)
					   			$scope.idA = [];
					   			$scope.idB = [];
					   			$scope.idC = [];
					   			$scope.idD = [];
					   			$scope.idE = [];
					   			$scope.idF = [];
					   			$scope.idG = [];
					   			$scope.idH = [];
								
					   			for(var i in answerArr[0].data.dataChoose) {
					   				for(var item in answerArr[0].data.dataChoose[i]){
					   				answerArr[0].data.dataChoose[i][item].forEach(function(item, index) {
					   					if(item == "A") {
					   						$scope.idA.push(userId);
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-A-stu").empty();
					   						liAp.textContent = $scope.idA.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idA.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-A-stu").append(liA);
					   					}
					   					if(item == "B") {
					   						$scope.idB.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-B-stu").empty();
					   						liAp.textContent = $scope.idB.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idB.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-B-stu").append(liA);
					   					}
					   					if(item == "C") {
					   						$scope.idC.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-C-stu").empty();
					   						liAp.textContent = $scope.idC.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idC.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-C-stu").append(liA);
					
					   					}
					   					if(item == "D") {
					   						$scope.idD.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-D-stu").empty();
					   						liAp.textContent = $scope.idD.length;;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idD.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-D-stu").append(liA);
					
					   					}
					   					if(item == "E") {
					   						$scope.idE.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-E-stu").empty();
					   						liAp.textContent = $scope.idE.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idE.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-E-stu").append(liA);
					
					   					}
					   					if(item == "F") {
					   						$scope.idF.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-F-stu").empty();
					   						liAp.textContent = $scope.idF.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idF.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-F-stu").append(liA);
					
					   					}
					   					if(item == "G") {
					   						$scope.idG.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-G-stu").empty();
					   						liAp.textContent = $scope.idG.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idG.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-G-stu").append(liA);
					   					}
					   					if(item == "H") {
					   						$scope.idH.push(userId)
					   						var liA = document.createElement("div");
					   						var liAp = document.createElement("p");
					   						$(".result-H-stu").empty();
					   						liAp.textContent = $scope.idH.length;
					   						liA.className = "answer-stu-lis";
					   						liA.style.height = 0.1 * $scope.idH.length + "rem";
					   						liA.appendChild(liAp)
					   						$(".result-H-stu").append(liA);
					   					}
					   				});
					   			}
					   			}
					   			$scope.mySelectArry = answerArr[0].data.dataChoose[$scope.studentPeerid][$scope.joinRoomUserAdmin];
					   			
					   			$(".result-student-wrapDiv").show();
					   			$(".answer-student-wrapDiv").hide();
					   			if($scope.language=="ch"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
								if($scope.language=="en"){$(".answersPeople-div").css("width",2+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.1+"rem");$(".result-teach-end-question").css("lineHeight",0.2+"rem");}
								if($scope.language=="tw"){$(".answersPeople-div").css("width",1+"rem");$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");$(".result-teach-end-question").css("lineHeight",0.4+"rem");}
					   		}else{
					   			for(var i = 0; i < answerArr[0].data.optionalAnswers.length; i++) {
					   			$(".answer-student-lis").eq(i).css("background", "");
					   			$(".reuslt-submit").css("background", "");
					   			answerArr[0].data.optionalAnswers[i].sel = false;
						   		};
						   		$(".reuslt-submit").html($rootScope.chinese.answers.submitAnswer.text);
						   		$scope.teacherResult = answerArr[0].data.optionalAnswers;
						   		$(".answer-student-wrapDiv").show();
						   		$(".result-student-wrapDiv").hide();
						   	}
					   	}
					   		}
	            	},0)
	            }
	            $rootScope.shapeJson["answer"] = null;
	             				/*转盘数据*/
	            var dialShowArr = $rootScope.shapeJson["dial"];
	            if(dialShowArr != null && dialShowArr != undefined && dialShowArr.length > 0) {
	            	var dialTrun = dialShowArr[0].data.rotationAngle;
	            		if(dialShowArr[0].data.isShow){
	            			$(".dial-teachComponent-turntable").css("transform",0);
                    		$(".dial-teachComponent-bg").show();
	                     if($rootScope.hasRole.roleStudent){
	                    	$(".dialCloseP").hide();
	                    }else{
	                    	$(".dialCloseP").show();
                     		}
	                    }else{
	                    		$(".dial-teachComponent-bg").show();
	                    		$(".dialCloseP").hide();
	                    		$(".dial-teachComponent-turntable").css("transform",dialTrun);
	                    		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
	                    		$timeout(function(){
		                    	 $(".dialCloseP").show();
		                    	 },6000)
	                    	} 
                    }
	            }
	            $rootScope.shapeJson["dial"] = null;
	            				/*学生提交答案的数据*/
	            var submitAnswersArr = $rootScope.shapeJson["submitAnswers"];
	            if(submitAnswersArr != null && submitAnswersArr != undefined && submitAnswersArr.length > 0) {
	            	for(var value in submitAnswersArr){
	            	 var data = submitAnswersArr[value].data;
	            	 var userSelect=data.mySelect;
                     var userId=submitAnswersArr[value].data.sendUserID;
                     var userName = submitAnswersArr[value].data.sendStudentName;
                 	 $scope.langeTYPE();
	            			$scope.allStudentChosseAnswer[userId]={};
							$scope.allStudentChosseAnswer[userId][userName] = userSelect;	
							$scope.studentNumbers.push(userId);
							for(var i=0;i<$scope.studentNumbers.length;i++){
							if(userId==$scope.studentNumbers[i]){
								$scope.allStudentChosseAnswer[userId][userName] = userSelect;	
								}
							}
							$scope.idA=[];
						    $scope.idB=[];
						    $scope.idC=[];
						    $scope.idD=[];
						    $scope.idE=[];
						    $scope.idF=[];
						    $scope.idG=[];
						    $scope.idH=[];
							$scope.studentNums=0;		
						for(var i in $scope.allStudentChosseAnswer){
							for(var names in $scope.allStudentChosseAnswer[i]){
								var nameValue = names;
							$scope.allStudentChosseAnswer[i][names].forEach(function(item,index){
								if(item=="A"){
									$scope.idA.push(userId);
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-A").empty();
										liAp.textContent=$scope.idA.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idA.length+"rem";
										liA.appendChild(liAp)
										$(".result-A").append(liA);
								}
								if(item=="B"){
									$scope.idB.push(userId)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-B").empty();
										liAp.textContent=$scope.idB.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idB.length+"rem";
										liA.appendChild(liAp)
										$(".result-B").append(liA);
								}if(item=="C"){
									$scope.idC.push(userId)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-C").empty();
										liAp.textContent=$scope.idC.length;
										liA.className="answer-stu-lis";
										liA.appendChild(liAp)
										liA.style.height=0.1*$scope.idC.length+"rem";
									$(".result-C").append(liA);
									
								}if(item=="D"){
									$scope.idD.push(userId)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-D").empty();
										liAp.textContent=$scope.idD.length;;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idD.length+"rem";
										liA.appendChild(liAp)
									$(".result-D").append(liA);
									
								}
								if(item=="E"){
									$scope.idE.push(userId)
										var liA=document.createElement("div");
										var liAp=document.createElement("p");
										$(".result-E").empty();
										liAp.textContent=$scope.idE.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idE.length+"rem";
										liA.appendChild(liAp)
								$(".result-E").append(liA);
									
								}if(item=="F"){
									$scope.idF.push(userId)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
										$(".result-F").empty();
										liAp.textContent=$scope.idF.length;
										liA.className="answer-stu-lis";
										liA.style.height=0.1*$scope.idF.length+"rem";
										liA.appendChild(liAp)
									$(".result-F").append(liA);
									
								}if(item=="G"){
									$scope.idG.push(userId)
								var liA=document.createElement("div");
								var liAp=document.createElement("p");
									$(".result-G").empty();
									liAp.textContent=$scope.idG.length;
									liA.className="answer-stu-lis";
									liA.appendChild(liAp)
									liA.style.height=0.1*$scope.idG.length+"rem";
								$(".result-G").append(liA);	
								}
								if(item=="H"){
									$scope.idH.push(userId)
									var liA=document.createElement("div");
									var liAp=document.createElement("p");
									$(".result-H").empty();
									liAp.textContent=$scope.idH.length;
									liA.className="answer-stu-lis";
									liA.style.height=0.1*$scope.idH.length+"rem";
									liA.appendChild(liAp)
								$(".result-H").append(liA);
									
								}
						})
							$scope.studentNumbers=Array.from(new Set($scope.studentNumbers));
						
							if($scope.allStudentChosseAnswer[i][nameValue].sort().toString()==$scope.trueSelectArry.sort().toString()){
								
	            				$scope.studentNums++;
	            				var studentLength=$scope.studentNumbers.length;
								$(".roleNums").html(studentLength)
	            		
	            				$scope.trueLV=$scope.studentNums/studentLength*100;
	            				$scope.trueLV=$scope.trueLV.toFixed(0)
	            				$(".trueRel").html($scope.trueLV)
	            			}else{
	            				var studentLength=$scope.studentNumbers.length;
								$(".roleNums").html(studentLength)
	            	
	            				$scope.trueLV=$scope.studentNums/studentLength*100;
	            				$scope.trueLV=$scope.trueLV.toFixed(0)
	            				$(".trueRel").html($scope.trueLV)
	            			}
						}
							}
						}						
                    	$scope.showStudent = data.mySelect;
	            
	            }
	            $rootScope.shapeJson["submitAnswers"] = null;
				//最后打开的文档文件和媒体文件	 
        		var lastDoucmentFileData = null,
					lastMediaFileData = null;
				var showPageArr = $rootScope.shapeJson["ShowPage"];
				if(showPageArr != null && showPageArr != undefined && showPageArr.length > 0) {
					for(var i = 0; i < showPageArr.length; i++) {
						if(!showPageArr[i].data.isMedia) {
							lastDoucmentFileData = showPageArr[i].data;
						}
					}
				}
				$rootScope.shapeJson["ShowPage"] = null;

            	//打开文件列表中的一个
				if(lastDoucmentFileData != undefined && lastDoucmentFileData != null) {
					$timeout(function(){
						if(!lastDoucmentFileData.isMedia) {
                            var isopenH5File = ($rootScope.h5DocumentFileId !== lastDoucmentFileData.filedata.fileid);
                            $rootScope.h5DocumentFileId = lastDoucmentFileData.filedata.fileid;//保存当前文档id
							if(lastDoucmentFileData.isGeneralFile){
                                $("#prev_page_phone").show();
                                $("#next_page_phone").show();
                                $("#add_literally_page_phone").hide();
                                $("#prev_page_phone_slide").hide();
                                $("#next_page_phone_slide").hide();
                                $("#prev_page_phone_h5").hide();
                                $("#next_page_phone_h5").hide();
                                ServiceLiterally.saveLcStackToStorage({
                                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                                });
                                ServiceLiterally.setFileDataToLcElement(lastDoucmentFileData.filedata);
								$rootScope.page.pageOperation(null, false);
								$rootScope.roomPage.flag.isShow.isCourseFile = true ;
                                $rootScope.roomPage.flag.isShow.isH5File = false ;
							}else if(lastDoucmentFileData.isDynamicPPT){
                                $("#prev_page_phone_slide").show();
                                $("#next_page_phone_slide").show();
                                $("#prev_page_phone").hide();
                                $("#next_page_phone").hide();
                                $("#add_literally_page_phone").hide();
                                $("#prev_page_phone_h5").hide();
                                $("#next_page_phone_h5").hide();
								$rootScope.roomPage.flag.isShow.isCourseFile = false ;
                                $rootScope.roomPage.flag.isShow.isH5File = false ;
	                            var isremote = true ;
	                            var isOpenPPT  = true;
	                            var pptslide = lastDoucmentFileData.filedata.pptslide ;
	                            var pptstep = lastDoucmentFileData.filedata.pptstep ;
	                            var pptfileid = lastDoucmentFileData.filedata.fileid ;
                                ServiceLiterally.saveLcStackToStorage({
                                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                                });
                                ServiceLiterally.setFileDataToLcElement(lastDoucmentFileData.filedata);
	                            $rootScope.page.recoverLcData();
	                            $(document).trigger("uploadAynamicPptProgress" , [ pptslide , pptstep  , pptfileid , isOpenPPT , isremote ] );
							}else if (lastDoucmentFileData.isH5Document) {
                                $("#prev_page_phone").hide();
                                $("#next_page_phone").hide();
                                $("#add_literally_page_phone").hide();
                                $("#prev_page_phone_slide").hide();
                                $("#next_page_phone_slide").hide();
                                $("#prev_page_phone_h5").show();
                                $("#next_page_phone_h5").show();
                                $rootScope.roomPage.flag.isShow.isCourseFile = false ;
                                $rootScope.roomPage.flag.isShow.isH5File = true ;
                                ServiceLiterally.saveLcStackToStorage({
                                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                                });
                                ServiceLiterally.setFileDataToLcElement(lastDoucmentFileData.filedata);
                                $rootScope.page.recoverLcData();

                                $scope.callController.func.handlerReceiveShowPageSignalling(lastDoucmentFileData,isopenH5File);
							}
						}
					},0);
				}
			};
	    };

		
		/*白板处理函数*/
	    $scope.phone.func.literallyHandle = function(){
		   /*白板元素*/
		   var $bigLc = $("#big_literally_vessel") ;
		   var $smallLc = $("#small_literally_vessel") ;
		    /*白板服务初始化*/
		    ServiceLiterally.initConfig["backgroundColor"] = "#d4d8dc" ;
		    ServiceLiterally.initConfig["secondaryColor"] = "#d4d8dc" ;
		    ServiceLiterally.rolePermission["laser"] = false ;
		    ServiceLiterally.defaultBackImgSrc = './plugs/literally/lib/img/transparent_bg.png' ;
		    $scope.customLc = ServiceLiterally.lcInit( $bigLc , $smallLc ) ;
		    $scope.customLc.lc.toolStatus.eraserWidth = 120 ;
		    $scope.customLc.toolsInitBind();
	    };

	    /*h5文档相关处理方法*/
        $scope.excuteH5DocumentFunction = function () {

            $(document).off('iframeMessage');
            $(document).on("iframeMessage",function (event,iframeData) {    //给当前window建立message监听函数
                try{
                    console.log("receive remote iframe data form "+ iframeData.origin +":",  iframeData);
                    if( iframeData.data ){
                        var recvData = undefined ;
                        try{
                            recvData =  JSON.parse(iframeData.data) ;
                        }catch (e){
                            L.Logger.warning(  "iframe message data can't be converted to JSON , iframe data:" , iframeData.data ) ;
                            return ;
                        }
                        if(!recvData.source && recvData.method) {
                            switch (recvData.method) {
                                case "onPagenum"://收到总页数
                                    var h5Pagenum = recvData.totalPages;
                                    $scope.callController.func.initH5Document(h5Pagenum);
                                    break;
                                case "onFileMessage"://操作h5课件时
                                    if($rootScope.roomPage.flag.isShow.isCourseFile === false && $rootScope.roomPage.flag.isShow.isH5File == true  ){
                                        var testData = recvData  , signallingName = "H5DocumentAction" , assignId = "H5DocumentAction";
                                        $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , true]);
                                    }
                                
                                    break;
                                case "onLoadComplete"://收到iframe加载完成时
                                    $rootScope.h5IsOnload = true;
                                    var filedata = ServiceLiterally.getFileDataFromLcElement();
                                    var currpage = filedata.currpage;
                                    if ($rootScope.h5ActionJson[currpage]) {
                                        if ($rootScope.h5ActionJson[currpage].length !== 0) {
                                            $rootScope.h5ActionJson[currpage].forEach(function (item,index) {
                                                $rootScope.h5Frame.postMessage(JSON.stringify(item),'*');
                                            });
                                            $rootScope.h5ActionJson = {};
                                        }
                                    }
                                    var fileInfo = ServiceLiterally.getFileDataFromLcElement();
                                    $scope.callController.func.h5FileJumpPage(fileInfo);
                                    $scope.callController.func.updateLcScaleWhenH5File( 16 / 9  );
                                    break;
                            }
                        }
                    }
                    //}
                }catch(e3){
                    console.error("message Event form iframe's parent :" , e3);
                }
            });

            $scope.callController.func.handlerReceiveShowPageSignalling = function(showpageData,isopenH5File){//接收ShowPage信令
                var h5DocumentData = showpageData;
                $rootScope.h5DocumentFileId = h5DocumentData.filedata.fileid;//保存当前文档id
                $rootScope.h5DocumentData = h5DocumentData;
				if(h5DocumentData.isH5Document){
					if (isopenH5File == true ) {
						$scope.callController.func.openH5DocumentHandler(h5DocumentData.filedata);
					}
					$scope.callController.func.h5FileJumpPage(h5DocumentData.filedata);
					$scope.callController.func.slideChangeToLc(h5DocumentData.filedata);
				}
            };
            $scope.callController.func.h5FileJumpPage = function(filedata){//跳转到h5文档的某一页
                var data = null;
                var toPage = filedata.currpage;
                data = JSON.stringify({method:"onJumpPage","toPage":toPage});
                $rootScope.h5Frame.postMessage(data,'*');
                $scope.callController.func.updateH5Page();//更新翻页的页数和按钮的是否可点
            };

            $scope.callController.func.openH5DocumentHandler = function(){ //打开h5文档
                $rootScope.h5IsOnload = false;
                $rootScope.roomPage.flag.isShow.isCourseFile = false ;
                $rootScope.roomPage.flag.isShow.isH5File = true ;
                var swfPath = ServiceLiterally.ele.attr("data-file-swfpath");
				var h5FileSrc = $rootScope.loginController.necessary.serviceUrl+":"+ $rootScope.loginController.necessary.servicePort + swfPath;
				$("#h5DocumentFrame").attr("src",h5FileSrc);

				$("#newppt_frame").attr("src","");
                ServiceLiterally.closeLoading();
                ServiceNewPptAynamicPPT.isOpenPptFile = !$rootScope.roomPage.flag.isShow.isH5File;
                ServiceLiterally.isOpenLcFile = $rootScope.roomPage.flag.isShow.isCourseFile ;
                ServiceLiterally.resetLcDefault();
                ServiceLiterally.ele.find(".background-canvas").hide();
                $("#big_literally_vessel").removeClass("tk-filetype-h5document aynamic-ppt-lc").addClass("tk-filetype-h5document");
				$scope.callController.func.updateLcScaleWhenH5File( 16 / 9  );
                $(document).trigger("checkAynamicPptClickEvent",[]);
            };

            $scope.callController.func.initH5Document = function (h5Pagenum) {
                $("#big_literally_vessel").attr("data-total-page" ,h5Pagenum);
                $scope.callController.func.updateH5Page();
                var filedata = ServiceLiterally.getFileDataFromLcElement();
                var signallingName = "ShowPage" ;
                var assignId = "DocumentFilePage_ShowPage";
                var testData = {
                    isMedia:false ,
                    isGeneralFile:false,
                    isDynamicPPT:false,
                    isH5Document:true,
                    action: "show",
                    mediaType:"",
                    filedata:filedata
                };
                $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId]);
            };
            $scope.callController.func.updateH5Page = function (curr,all) {//更新翻页的页数和按钮的是否可点
            	var currPage = curr || ServiceLiterally.ele.attr("data-curr-page");
            	var allPage = all || ServiceLiterally.ele.attr("data-total-page");
				$("#curr_h5_page").html(currPage);
				$("#all_h5_page").html(allPage);
				if (currPage <= 1) {
					$("#h5_prev_page,#prev_page_phone_h5").addClass("disabled").attr("disabled","disabled");
				}else {
                    $("#h5_prev_page,#prev_page_phone_h5").removeClass("disabled").removeAttr("disabled");
				}
				if (currPage >= allPage) {
                    $("#h5_next_page,#next_page_phone_h5").addClass("disabled").attr("disabled","disabled");
				}else {
                    $("#h5_next_page,#next_page_phone_h5").removeClass("disabled").removeAttr("disabled");
				}
            };
            $scope.callController.func.slideChangeToLc = function(filedata){
                ServiceLiterally.saveLcStackToStorage({//保存上一页数据
                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                });
                ServiceLiterally.setFileDataToLcElement(filedata);//设置当前文档数据到白板节点上
                $rootScope.page.recoverLcData();//画当前文档当前页数据到白板上
                $scope.callController.func.updateH5Page();//更新翻页的页数和按钮的是否可点
            };
            $("#h5_prev_page,#h5_next_page,#prev_page_phone_h5,#next_page_phone_h5").off("click");
            $("#h5_prev_page,#prev_page_phone_h5").click(function () {//点击上一页
                var data = JSON.stringify({method:"onPageup"});
                $rootScope.h5Frame.postMessage(data,'*');
                $scope.callController.func.changCurrPageUp();//改变当前页数
            });
            $("#h5_next_page,#next_page_phone_h5").click(function () {//点击下一页
                var data = JSON.stringify({method:"onPagedown"});
                $rootScope.h5Frame.postMessage(data,'*');
                $scope.callController.func.changCurrPageDown();//改变当前页数
            });
            $scope.callController.func.changCurrPageUp = function() {
                var fileInfo = ServiceLiterally.getFileDataFromLcElement();
                var currpage = fileInfo.currpage;
                var pagenum = fileInfo.pagenum;
                currpage--;
                if (currpage <= 1) {
                    currpage = 1;
                }
                fileInfo.currpage = currpage;
                $scope.callController.func.slideChangeToLc(fileInfo);
                $rootScope.h5DocumentData.filedata = fileInfo;
                $rootScope.h5DocumentData.action = 'onJumpPage';
                $scope.callController.func.updateH5Page(currpage,pagenum);//更新翻页的页数和按钮的是否可点
                $scope.callController.func.sendSignallingToH5File($rootScope.h5DocumentData);//发送信令告诉其他人翻页
            };
            $scope.callController.func.changCurrPageDown = function() {
                var fileInfo = ServiceLiterally.getFileDataFromLcElement();
                var currpage = fileInfo.currpage;
                var pagenum = fileInfo.pagenum;
                currpage++;
                if (currpage >= pagenum) {
                    currpage = pagenum;
                }
                fileInfo.currpage = currpage;
                $scope.callController.func.slideChangeToLc(fileInfo);
                $rootScope.h5DocumentData.filedata = fileInfo;
                $rootScope.h5DocumentData.action = 'onJumpPage';
                $scope.callController.func.updateH5Page(currpage,pagenum);//更新翻页的页数和按钮的是否可点
                $scope.callController.func.sendSignallingToH5File($rootScope.h5DocumentData);//发送信令告诉其他人翻页
            };
            $scope.callController.func.sendSignallingToH5File = function(h5FileData) {//发送信令告诉其他人翻页
                var assignId = 'DocumentFilePage_ShowPage';
                var signallingName = 'ShowPage';
                $(document).trigger("sendDataToLiterallyEvent",[ null , h5FileData , signallingName , assignId]);
                //ServiceSignalling.sendSignallingFromShowPage(isDelMsg , id , h5FileData);
            }
            $scope.callController.func.updateLcScaleWhenH5File = function (lcLitellyScalc) {
                ServiceLiterally.lcLitellyScalc = lcLitellyScalc;
                ServiceLiterally.lc.watermarkImage = null ;
                ServiceLiterally.setBackgroundWatermarkImage("");
            };



        };


		/*动态PPT相关处理方法*/
		$scope.excuteAynamicPPTFunction = function () {
			$scope.callController.func.aynamicPPTHandler = function(options){ //创建动态PPT处理对象
				ServiceNewPptAynamicPPT.clearAll();
				ServiceNewPptAynamicPPT.newDopPresentation(options);

				/*接收IFrame框架的消息*/
				ServiceNewPptAynamicPPT.remoteIframeOrigin = $rootScope.loginController.necessary.serviceUrl+":"+ $rootScope.loginController.necessary.servicePort ;
				// ServiceNewPptAynamicPPT.remoteIframeOrigin = "https://192.168.1.182:8443" ;
				$(window).off('message');
				ServiceTools.tool.addEvent(window ,'message' , function(event){    //给当前window建立message监听函数
                    $(document).trigger("iframeMessage",[event]);
					try{
						// 通过origin属性判断消息来源地址
						console.log("receive remote iframe data form "+ event.origin +":",  event);
						//if (ServiceNewPptAynamicPPT.remoteIframeOrigin.toString().indexOf(event.origin) != -1 ) {
							if( event.data ){
								var data = undefined;
								var recvData = undefined ;
								try{
									recvData =  JSON.parse(event.data) ;
									data = recvData.data ;
								}catch (e){
									L.Logger.warning(  "iframe message data can't be converted to JSON , iframe data:" , event.data ) ;
									return ;
								}

								if(recvData.source === "tk_dynamicPPT") {
									var INITEVENT = "initEvent" ;
									var SLIDECHANGEEVENT = "slideChangeEvent" ;
									var STEPCHANGEEVENT = "stepChangeEvent" ;
									var AUTOPLAYVIDEOINNEWPPT = "autoPlayVideoInNewPpt" ;
									var CLICKNEWPPTTRIGGEREVENT = "clickNewpptTriggerEvent" ;
                                    var filedata = ServiceLiterally.getFileDataFromLcElement();
									switch (data.action){
										case INITEVENT :
											ServiceNewPptAynamicPPT.remoteData.view = data.view ;
											ServiceNewPptAynamicPPT.remoteData.slidesCount = data.slidesCount ;
											ServiceNewPptAynamicPPT.remoteData.slide = data.slide ;
											ServiceNewPptAynamicPPT.remoteData.step = data.step ;
											ServiceNewPptAynamicPPT.remoteData.stepTotal = data.stepTotal ;
											ServiceNewPptAynamicPPT.recvInitEventHandler(data.slide ,data.step , data.externalData);
											break ;
										case SLIDECHANGEEVENT :
											ServiceNewPptAynamicPPT.remoteData.slide = data.slide ;
											ServiceNewPptAynamicPPT.remoteData.step = data.step ;
											ServiceNewPptAynamicPPT.remoteData.stepTotal = data.stepTotal ;
											ServiceNewPptAynamicPPT.recvSlideChangeEventHandler( data.slide  , data.externalData );
                                            var currpage = filedata.currpage;
											if ($rootScope.newPptActionJson[currpage]) {
												if ($rootScope.newPptActionJson[currpage].length !== 0) {
                                                    $rootScope.newPptActionJson[currpage].forEach(function (item,index) {
                                                        ServiceNewPptAynamicPPT.postMessage(item);//执行当前页的动作（触发器）
                                                    });
													$rootScope.newPptActionJson = {};
												}
											}
											break ;
										case STEPCHANGEEVENT:
											ServiceNewPptAynamicPPT.remoteData.slide = data.slide ;
											ServiceNewPptAynamicPPT.remoteData.step = data.step ;
											ServiceNewPptAynamicPPT.remoteData.stepTotal = data.stepTotal ;
											ServiceNewPptAynamicPPT.recvStepChangeEventHandler(data.step , data.externalData);
                                            var currpage = filedata.currpage;
                                            if ($rootScope.newPptActionJson[currpage]) {
                                                if ($rootScope.newPptActionJson[currpage].length !== 0) {
                                                    $rootScope.newPptActionJson[currpage].forEach(function (item,index) {
                                                        ServiceNewPptAynamicPPT.postMessage(item);//执行当前页的动作（触发器）
                                                    });
                                                    $rootScope.newPptActionJson = {};
                                                }
                                            }
											break ;
										case AUTOPLAYVIDEOINNEWPPT:
											var isvideo = data.isvideo , url = data.url , fileid = data.fileid  , pptslide = data.pptslide;
											if(isvideo && !$rootScope.initPageParameterFormPhone.playback){
												var videoUrl = ServiceNewPptAynamicPPT.rPathAddress + url ;
												var pptVideoJson = {
													url:videoUrl ,
													fileid:fileid?Number(fileid):fileid ,
													isvideo:isvideo ,
                                                    pptslide:pptslide?Number(pptslide):pptslide  ,
												};
												$scope.callController.func._newpptAutoPlayVideoInNewPpt(pptVideoJson);
											}
											break ;
										case CLICKNEWPPTTRIGGEREVENT:
											if( ServiceNewPptAynamicPPT.isOpenPptFile  &&  $rootScope.sendSignallingFromDynamicPptTriggerActionClick && ServiceNewPptAynamicPPT.isInitiative(data.externalData)  ){
                                                var filedata = ServiceLiterally.getFileDataFromLcElement();
												data.fileid = filedata.fileid;
												var testData = data  , signallingName = "NewPptTriggerActionClick" , assignId = "NewPptTriggerActionClick";
												$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , false]);
											}
											break ;
									}
								}
							}
						//}
					}catch(e3){
						console.error("message Event form iframe's parent :" , e3);
					}
				} , false  );
			};
			$scope.callController.func.openAynamicPPTHandler = function(isSetUrl,isSend){ //打开动态PPT
				$rootScope.roomPage.flag.isShow.isCourseFile = false ;
                $rootScope.roomPage.flag.isShow.isH5File = false ;
				ServiceNewPptAynamicPPT.isOpenPptFile = !$rootScope.roomPage.flag.isShow.isCourseFile ;
				ServiceLiterally.isOpenLcFile = $rootScope.roomPage.flag.isShow.isCourseFile ;
				isSetUrl = isSetUrl!=undefined ? isSetUrl : true ;
				isSend = isSend!=undefined ? isSend : true ;
				ServiceLiterally.resetLcDefault();	
				ServiceLiterally.ele.find(".background-canvas").hide();
				$("#big_literally_vessel").removeClass("aynamic-ppt-lc tk-filetype-h5document").addClass("aynamic-ppt-lc");
				$(document).trigger("checkAynamicPptClickEvent",[]);

				var fileId = parseInt(  ServiceLiterally.ele.attr("data-file-id") ) ;
				var swfPath = ServiceLiterally.ele.attr("data-file-swfpath");
				var pptSlide = parseInt( ServiceLiterally.ele.attr("data-ppt-slide") );
				var pptStep = parseInt( ServiceLiterally.ele.attr("data-ppt-step") );
				var newpptVersions =  2017091401 ; //动态ppt的版本
				var remoteNewpptUpdateTime = 201710171022 ; //远程动态PPT文件更新时间
				var options = {
//					rPathAddress:   "http://192.168.1.182:80" + swfPath+"/"  ,
					rPathAddress:   $rootScope.loginController.necessary.serviceUrl+":"+ $rootScope.loginController.necessary.servicePort + swfPath+"/"  ,
					PresAddress:"newppt.html?remoteHost="+window.location.host+"&remoteProtocol="+window.location.protocol+"&mClientType="+$rootScope.initPageParameterFormPhone.mClientType
						+"&deviceType="+$rootScope.initPageParameterFormPhone.deviceType+"&versions="+newpptVersions+"&fileid="+fileId
						+"&classbegin="+$rootScope.isClassBegin+"&playback="+$rootScope.initPageParameterFormPhone.playback
						+"&publishDynamicPptMediaPermission_video="+ $rootScope.publishDynamicPptMediaPermission_video 
						+"&remoteNewpptUpdateTime="+remoteNewpptUpdateTime+"&role="+($rootScope.joinMeetingMessage ? $rootScope.joinMeetingMessage.roomrole : undefined )
						+"&dynamicPptActionClick="+$rootScope.dynamicPptActionClick+"&newpptPagingPage="+$rootScope.newpptPagingPage
						+"&dynamicPptDebug="+true+"&ts="+new Date().getTime(),
					slideIndex:pptSlide ,
					stepIndex:pptStep ,
					fileid:fileId
				};
				if ($rootScope.dynamicPptActionClick) {
					$("#ppt_supernatant").css("display" , "none");
				}else {
                    $("#ppt_supernatant").css("display" , "block");
				}
				console.log("openAynamicPPTHandler options" , JSON.stringify(options) );
				if(isSend && $rootScope.isClassBegin && $rootScope.joinMeetingMessage &&  $rootScope.hasRole.roleChairman ){
					var action = {action:"show"};
					var data  = {
						pptslide:pptSlide,
						currpage:pptSlide ,
						pptstep:pptStep ,
					};
					for(var x in action){
						data[x] = action[x];
					}
					data["pagenum"] = ServiceNewPptAynamicPPT.slidesCount;
					$(document).trigger("sendPPTMessageEvent",[data]);
				}
				if(isSetUrl){
					ServiceLiterally.closeLoading();
					$scope.callController.func.updateLcScaleWhenAynicPPTInitHandler( 16 / 9  );
					ServiceNewPptAynamicPPT.setRPathAndPres(options);
                    $("#h5DocumentFrame").attr("src","");
				}
			};
			$scope.callController.func.AynamicPPTJumpToAnim = function (slide , step) { //跳转到PPT的某一页的某一个帧
				ServiceNewPptAynamicPPT.jumpToAnim(slide , step );
			};
			$scope.callController.func.updateLcScaleWhenAynicPPTInitHandler = function (lcLitellyScalc) {
				ServiceLiterally.lcLitellyScalc = lcLitellyScalc;
				ServiceLiterally.lc.watermarkImage = null ;
				ServiceLiterally.setBackgroundWatermarkImage("");
			};
			$scope.callController.func._newpptAutoPlayVideoInNewPpt = function(pptVideoJson){
				try{
					var videoData = JSON.stringify( pptVideoJson );
					switch ( $rootScope.initPageParameterFormPhone.mClientType){
						case 2://ios
							if(window.webkit && window.webkit.messageHandlers ){
								window.webkit.messageHandlers.onJsPlay.postMessage({"data":videoData});
							}else{
								console.error("没有方法window.webkit.messageHandlers.onJsPlay.postMessage");
							}
							break;
						case 3://android
							if(window.JSWhitePadInterface){
								window.JSWhitePadInterface.onJsPlay( videoData );
							}else{
								console.error("没有方法window.JSWhitePadInterface.onJsPlay");
							}
						default:
							break;
					}
				}catch(e){
					console.error("_newpptAutoPlayVideoInNewPpt  error:" , e);
				}
			};

			$scope.callController.func._handlerNewpptClassBeginStartOrEnd = function(){ //处理上下课信令
				var data = {
					action:"changeClassBegin" ,
					classbegin:$rootScope.isClassBegin
				};
				ServiceNewPptAynamicPPT.postMessage(data);
			};

            $scope.callController.func._handlerPublishDynamicPptMediaPermission_video= function(){ //动态PPT视频播放权限更改后通知iframe
                var data = {
                    action:"changePublishDynamicPptMediaPermission_video" ,
                    publishDynamicPptMediaPermission_video:$rootScope.publishDynamicPptMediaPermission_video
                };
                ServiceNewPptAynamicPPT.postMessage(data);
            };
            $scope.callController.func._handlerDynamicPptActionClick= function(){ //动态PPT是否显示浮层
                var data = {
                    action:"changeDynamicPptActionClick" ,
            		dynamicPptActionClick:$rootScope.dynamicPptActionClick
                };
                if ($rootScope.dynamicPptActionClick) {
                    $("#ppt_supernatant").css("display" , "none");
                }else {
                    $("#ppt_supernatant").css("display" , "block");
                }
                ServiceNewPptAynamicPPT.postMessage(data);
            };
           $scope.callController.func._handlerUpdateNewpptPagingPage= function(){ //动态PPT是否能够翻页
                var data = {
                    action:"changeNewpptPagingPage" ,
            		newpptPagingPage:$rootScope.newpptPagingPage
                };
                ServiceNewPptAynamicPPT.postMessage(data);
            };           
		};

		/*发送PPT数据给其它人*/
		$(document).off("sendPPTMessageEvent");
		$(document).on("sendPPTMessageEvent",function (event , data , isInitiative ) {
			if( isInitiative && data){
				var signallingName = "ShowPage" ;
				var assignId = 'DocumentFilePage_ShowPage';
				var oldFiledata = ServiceLiterally.getFileDataFromLcElement();
				var fileId =  oldFiledata.fileid  ;
				var allPageNum =  data.pagenum!=undefined ?data.pagenum:oldFiledata.pagenum;
				var fileType =   oldFiledata.filetype   ;
				var fileName =  oldFiledata.filename   ;
				var swfPath =  oldFiledata.swfpath   ;
				var pptslide = data.pptslide!=undefined ?data.pptslide:oldFiledata.pptslide;
				var pptstep = data.pptstep!=undefined ?data.pptstep:oldFiledata.pptstep;
				var currPageNum = data.currpage!=undefined ?data.currpage:oldFiledata.currpage ;
				var action = data.action ;
				var steptotal = data.steptotal!=undefined ?  data.steptotal:oldFiledata.steptotal;
				var filedata = {
					fileid:fileId ,
					currpage:currPageNum ,
					pagenum:allPageNum ,
					filetype:fileType ? fileType:"" ,
					filename:fileName?fileName:"" ,
					swfpath:swfPath?swfPath:"" ,
					pptslide:pptslide ,
					pptstep:pptstep ,
					steptotal:steptotal
				};
				var testData = {
					isGeneralFile:false,
					isMedia:false,
					isDynamicPPT:true,
					isH5Document:false,
					mediaType:"",
					action:action ,
					filedata:filedata
				};
				ServiceNewPptAynamicPPT.recvCount = 0 ;
				$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId]);
			}
		});

		/*更新动态PPT的进度*/
		$(document).off("uploadAynamicPptProgress");
		$(document).on("uploadAynamicPptProgress" , function(event , slide , step ,  fileid  , openPPT , isRemote){ //绑定事件，更新动态PPT的进度
			// ServiceAynamicPPT.isOpenPptFile = !$rootScope.roomPage.flag.isShow.isCourseFile ;
			ServiceNewPptAynamicPPT.isOpenPptFile = !$rootScope.roomPage.flag.isShow.isCourseFile ;
			ServiceLiterally.isOpenLcFile = $rootScope.roomPage.flag.isShow.isCourseFile ;
			if(isRemote){
				ServiceNewPptAynamicPPT.recvCount ++ ;
			};
			if(openPPT){
				$scope.callController.func.openAynamicPPTHandler(true , false);
				return ;
			}
			if( ServiceLiterally.ele.attr("data-file-id") == fileid.toString() ){
				try{
					$scope.callController.func.AynamicPPTJumpToAnim(slide,step);
				}catch(e) {
					$scope.callController.func.openAynamicPPTHandler(true, false);
				}
			}
		});

		/*绑定动态PPT更新白板尺寸事件*/
		$(document).off("updateLcScaleWhenAynicPPTInit");
		$(document).on("updateLcScaleWhenAynicPPTInit" , function (event , data) {
			console.log("updateLcScaleWhenAynicPPTInit" , data);
			if(data && data.Width && data.Height){
				$scope.callController.func.updateLcScaleWhenAynicPPTInitHandler( data.Width / data.Height );
			}
		});

		/*绑定动态PPT更新白板当前页画笔数据事件*/
		$(document).off("slideChangeToLcData");
		$(document).on("slideChangeToLcData" , function (event , data) {
			if(data){
				ServiceLiterally.saveLcStackToStorage({
					saveRedoStack: $rootScope.hasRole.roleChairman  ,
				});
				$("#big_literally_vessel , #file_list_"+data.fileid )
					.attr("data-ppt-slide" , data.pptslide )
					.attr("data-curr-page" , data.currpage );
				$rootScope.page.recoverLcData();
			}
		});

		/*绑定动态PPT改变节点信息事件*/
		$(document).off("newppt_changeFileElementProperty");
		$(document).on("newppt_changeFileElementProperty" , function (event , data) {
			if(data){
				$("#big_literally_vessel")
					.attr("data-ppt-step" ,data.pptstep )
					.attr('data-ppt-step-total' , data.steptotal);
			}
		});


		/*绑定动态PPT更新节点总页数事件*/
		$(document).off("updateSlidesCountToLcElement");
		$(document).on("updateSlidesCountToLcElement" , function (event , data) {
			if(data){
				$("#big_literally_vessel")
					.attr("data-total-page" ,data.pagenum );
			}
		});


		/*检测参与者能否点击事件*/
		$(document).off("checkAynamicPptClickEvent");
		$(document).on("checkAynamicPptClickEvent",function (event,data) {
		   if( ServiceNewPptAynamicPPT.isOpenPptFile ){
//				if(  $("#tool_mouse").hasClass("active-tool") && ServiceLiterally.getIsDrawAble() ){
				if(  $("#tool_mouse").hasClass("active-tool") ){
					$("#scroll_literally_container").hide();
				}else{
					$("#scroll_literally_container").show();
					ServiceLiterally.resizeHandler(ServiceLiterally);
				}
		   }else if ($rootScope.roomPage.flag.isShow.isH5File == true ) {
//             if($("#tool_mouse,#tool_mouse_phone").hasClass("active-tool") && ServiceLiterally.getIsDrawAble() ){
               if($("#tool_mouse,#tool_mouse_phone").hasClass("active-tool") ){
                   $("#scroll_literally_container").hide();
               }else{
                   $("#scroll_literally_container").show();
                   ServiceLiterally.resizeHandler(ServiceLiterally);
               }
		   }else{
			   $("#scroll_literally_container").show();
		   }
		});

		/*更新白板的缩放比例*/
		$(document).off("updateLcScale");
		$(document).on("updateLcScale" , function(event , data){
			ServiceLiterally.eleWHPercent = data ;
			ServiceLiterally.resizeHandler(ServiceLiterally);
		});

		 /*取消绑定的事件*/
		$(document).off("cancelEvent");
		$(document).on("cancelEvent" , function(event , data){
			var execute = true ;
			if(data){
				if(data.rolePermissionNotExecute){
					switch (data.rolePermissionNotExecute){
						case "chairman":
							if($rootScope.joinMeetingMessage && ($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant) ){
								execute = false ;
							}
							break;
						default:
							execute = true ;
							break;
					}
				}
				if(data.needClassBegin){
					if($rootScope.isClassBegin !== true){
						execute = false ;
					}
				}
				if(execute){
					$(data.eventSelector).off(data.eventName) ;
				}
			}
		});
      
   		
		/*白板翻页代码*/
	    $rootScope.page = {
	        prevPage:function(){
                ServiceLiterally.saveLcStackToStorage({
                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                });
	            $rootScope.page.pageOperation(false,true);
	        },
	        nextPage:function(){
                ServiceLiterally.saveLcStackToStorage({
                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                });
	            $rootScope.page.pageOperation(true,true);
	        },
	        setLcFileAttr:function(data){
                ServiceLiterally.ele
                    .attr("data-file-id" , data.fileid)
                    .attr("data-curr-page",data.currpage )
                    .attr("data-total-page", data.pagenum )
                    .attr("data-file-type", data.filetype )
                    .attr("data-file-name", data.filename )
                    .attr("data-file-swfpath", data.swfpath)
                    .attr("data-ppt-slide", data.pptslide)
                    .attr("data-ppt-step", data.pptstep);
            },
           	defaultInitLcData:function(){ //默认初始化白板数据
                var data = ServiceLiterally.defaultFileData();
                ServiceLiterally.setFileDataToLcElement(data);
            },
	        showLiterally:function(isSend){
                ServiceLiterally.saveLcStackToStorage({
                    saveRedoStack: $rootScope.hasRole.roleChairman  ,
                });
	            isSend = (isSend !=null && isSend!=undefined ?isSend:true);
	            $rootScope.page.pageOperation(null,isSend);
	        },
	        literallyAddPage:function(){
	        	if($rootScope.initPageParameterFormPhone.addPagePermission){
                    ServiceLiterally.saveLcStackToStorage({
                        saveRedoStack: $rootScope.hasRole.roleChairman  ,
                    });
                    var lcData = ServiceLiterally.getFileDataFromLcElement();
                    lcData.pagenum += 1 ;
                    var addPageData = {
                        totalPage: lcData.pagenum ,
                        fileid:lcData.fileid
                    };
					ServiceLiterally.setFileDataToLcElement(lcData);
		            var signallingName = "WBPageCount" ;
		            var assignId = "WBPageCount";
		            $(document).trigger("sendDataToLiterallyEvent",[ null , addPageData , signallingName , assignId]);
		            $rootScope.page.pageOperation(true,true);
	        	}
	        },
            pageOperation:function(isNext,isSend , isSetPlayUrl , isRemote , isLoadRecoverLcData){
                $rootScope.roomPage.flag.isShow.isCourseFile = true ;
                $rootScope.roomPage.flag.isShow.isH5File = false ;
                isLoadRecoverLcData = isLoadRecoverLcData != undefined ? isLoadRecoverLcData : true ;
                ServiceNewPptAynamicPPT.isOpenPptFile = !$rootScope.roomPage.flag.isShow.isCourseFile ;
                ServiceLiterally.isOpenLcFile = $rootScope.roomPage.flag.isShow.isCourseFile ;
                isSend = (isSend!=null && isSend!=undefined ) ?isSend:true ;
                isSetPlayUrl = (isSetPlayUrl!=null && isSetPlayUrl!=undefined ) ?isSetPlayUrl:true ;
                $("#big_literally_vessel").removeClass("aynamic-ppt-lc tk-filetype-h5document") ;
                $("#aynamic_ppt_click").hide();
                $("#resizer").html("");
                $("#preloader").css("display" , 'none');
                ServiceLiterally.ele.find(".background-canvas").show();
                //ServiceAynamicPPT.stop();
                ServiceNewPptAynamicPPT.setNewPptFrameSrc("");
                $("#h5DocumentFrame").attr("src","");
                ServiceLiterally.resetLcDefault();
				$(document).trigger("checkAynamicPptClickEvent",[]);
				 
				var  $prevPageBtnPhone = $("#content").find("#prev_page_phone") ;
	            var  $nextPageBtnPhone = $("#content").find("#next_page_phone") ;
	            var  $addPageBtnPhone = $("#content").find("#add_literally_page_phone") ;
                var  $prevPageBtn = $("#page_wrap").find("#prev_page") ;
                var  $nextPageBtn = $("#page_wrap").find("#next_page") ;
                var  $addPageBtn = $("#page_wrap").find("#add_literally_page") ;
				var filedata = ServiceLiterally.getFileDataFromLcElement();
                var  currPageNum =  filedata.currpage  ;
                var  allPageNum =  filedata.pagenum   ;
                var  fileId = filedata.fileid ;

                $prevPageBtn.add($nextPageBtn).add($addPageBtn).removeClass("pointer-events").addClass("pointer-events");
                $timeout(function(){
                    $prevPageBtn.add($nextPageBtn).add($addPageBtn).removeClass("pointer-events");
                },500);

                 if(isNext == true){
	                currPageNum ++ ;
                     filedata.currpage++;
	            }else if(isNext == false){
	                currPageNum -- ;
                     filedata.currpage--;
	            }
	          
	            if(currPageNum == 1){
	            	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) { //ipad
						$prevPageBtn.removeClass("disabled").addClass("disabled").attr("disabled","disabled");
					}else {
						$prevPageBtnPhone.removeClass("disabled").addClass("disabled").attr("disabled","disabled");
					}
	            }else{
	            	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) { //ipad
						$prevPageBtn.removeAttr("disabled").removeClass("disabled");
					}else {
						$prevPageBtnPhone.removeAttr("disabled").removeClass("disabled");
					}
	            }
	          
	            if(currPageNum == allPageNum){
	            	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {//ipad
						$nextPageBtn.removeClass("disabled").addClass("disabled").attr("disabled","disabled");
					}else {
						$nextPageBtnPhone.removeClass("disabled").addClass("disabled").attr("disabled","disabled");
					}
	            }else{
	            	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {//ipad
						$nextPageBtn.removeClass("disabled").removeAttr("disabled");
					}else {
						$nextPageBtnPhone.removeClass("disabled").removeAttr("disabled");
					}
	            }

                if( fileId === 0){
                    ServiceLiterally.lcLitellyScalc = 16 / 9 ;
                      	if($rootScope.initPageParameterFormPhone.addPagePermission){
	            		if(currPageNum == allPageNum){
	            			if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {
								$addPageBtn.show();
		                    	$nextPageBtn.hide();
							}else {
								$addPageBtnPhone.show();
		                    	$nextPageBtnPhone.hide();
							}
		                }else{
		                	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {
								$addPageBtn.hide();
		                    	$nextPageBtn.show();
							}else {
								$addPageBtnPhone.hide();
		                    	$nextPageBtnPhone.show();
							}
		                }
	            	}else{
	            		if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {
							$addPageBtn.hide();
	                    	$nextPageBtn.show();
						}else {
							$addPageBtnPhone.hide();
	                    	$nextPageBtnPhone.show();
						}
	            	}
                    if(isSetPlayUrl) {
                        ServiceLiterally.setBackgroundWatermarkImage("");
                    }
                    ServiceLiterally.ele.find(".background-canvas").hide();
                }else{
                	if ($rootScope.initPageParameterFormPhone.deviceType === 1 ) {
						$addPageBtn.hide();
                    	$nextPageBtn.show();
					}else {
						$addPageBtnPhone.hide();
                    	$nextPageBtnPhone.show();
					}
					try{
						var swfpath = filedata.swfpath ;
	                    var index = swfpath.lastIndexOf(".") ;
	                    var imgType = swfpath.substring(index);
	                    var fileUrl = swfpath.replace(imgType,"-"+currPageNum+imgType) ;
	                    var serviceUrl = $rootScope.loginController.necessary.serviceUrl+":"+$rootScope.loginController.necessary.servicePort ;
	                    if(isSetPlayUrl) {
	                        ServiceLiterally.setBackgroundWatermarkImage(serviceUrl + fileUrl);
	                    }
					}catch(e){
						console.error("出现错误，错误信息：", e ) ;
					}                 
               }
                if($rootScope.initPageParameterFormPhone.deviceType !=null && $rootScope.initPageParameterFormPhone.mClientType !=null){
 	            	if( !$rootScope.initPageParameterFormPhone.addPagePermission && $rootScope.initPageParameterFormPhone.deviceType === 0 && allPageNum<=1 &&  ( $rootScope.initPageParameterFormPhone.mClientType === 3 || $rootScope.initPageParameterFormPhone.mClientType ===2 ) ){
						$prevPageBtnPhone.show();
						$nextPageBtnPhone.show();
						$addPageBtnPhone.hide();	
					}
	            }else{
	            	$prevPageBtnPhone.hide();
					$nextPageBtnPhone.hide();
					$addPageBtnPhone.hide();
	            }
                ServiceLiterally.setFileDataToLcElement(filedata);
                $fileListEle.find("#file_list_"+fileId).attr("data-curr-page",currPageNum );
                $scope.page.variable.pageNum = currPageNum ;
                $("#page_wrap").find("#curr_page").html(currPageNum);
                $("#page_wrap").find("#all_page").html(allPageNum);
                
                if( isSend ){
                    var signallingName = "ShowPage" ;
                    var assignId = "DocumentFilePage_ShowPage";
                    var testData = {
                        isMedia:false ,
                        isGeneralFile:true,
                        isDynamicPPT:false,
                        action: "",
                        mediaType:"",
                        filedata:filedata
                    };
                    $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId]);
                };
                //加载当前页的白板数据
          
                if(isLoadRecoverLcData){
                	$rootScope.page.recoverLcData(fileId , currPageNum);
                }               
            },
	       recoverLcData:function (fileId , currPageNum) {
               var paramsJson = {fileId:fileId , currPageNum:currPageNum , loadRedoStack:$rootScope.hasRole.roleChairman , callback:function () {               
                   if( $rootScope.isClassBegin && !($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant)  ){
                       //$("#ppt_next_page_slide , #ppt_prev_page_slide , #next_page , #prev_page , #add_literally_page").addClass("disabled").attr("disabled","disabled").attr("data-set-disabled" ,'no');
//                       $("#ppt_next_page_slide , #ppt_prev_page_slide , #next_page , #prev_page , #add_literally_page").addClass("disabled").attr("disabled","disabled").attr("data-set-disabled" ,'no');
                   }
			   } };
               ServiceLiterally.recoverCurrpageLcData(paramsJson);
            },
	       variable:{
	            pageNum:1
	        }
	    };
		
		
	    /*会议页面控制*/
	    $rootScope.roomPage = {
	        flag:{
	            isShow:{
	                graffitiTools:false ,  //涂鸦工具
                    isCourseFile:true , //是否是课件文件
	                literallyWritePermission:true , //白板写入权限
	            }
	        } ,
	        elementShow:function(hideElementIdArr , showElementIdArr){
	            if(hideElementIdArr && hideElementIdArr.length>0){
	                hideElementIdArr.forEach(function(eleId){
	                    $("#"+eleId).hide();
	                });
	            }
	            if(showElementIdArr && showElementIdArr.length>0){
	                showElementIdArr.forEach(function(eleId){
	                    $("#"+eleId).show();
	                });
	            }
	        }
	    };
		
		
	    /*执行页面功能函数*/
		$scope.phone.func.execute = function(){
			var root =  document.getElementById("all_root");
	       /*动态更改html的font-size*/
	        $(window).off("resize");
	        $(window).resize(function (event , triggerTypeJson , notTriggerTypeJson) {
	           var baseSize = 15.8 ; 
	           var defalutFontSize = window.innerWidth / baseSize;
	           root.style.fontSize = defalutFontSize+ 'px';
	            var LC = "lc" ;
                if( notTriggerTypeJson ){
                	if(!notTriggerTypeJson[LC] ){
                        ServiceLiterally.resizeHandler(ServiceLiterally);
					}
				}else{
                    ServiceLiterally.resizeHandler(ServiceLiterally);
				}
	        });
	    	/*执行白板处理函数*/
			$scope.phone.func.literallyHandle();	
			
			/*动态PPT部分 start*/
            $scope.excuteAynamicPPTFunction();

            /*h5课件*/
            $scope.excuteH5DocumentFunction();
            
            $scope.isfullLc = false ; //白板是否全屏
            $("#lc_full_btn , #ppt_lc_full_btn,#h5_lc_full_btn").click(function(){
            	$scope.isfullLc = !$scope.isfullLc ;
            	var fullClass = $scope.isfullLc ? "yes" : "no" ;
            	 $("#lc_full_btn , #ppt_lc_full_btn,#h5_lc_full_btn").removeClass("yes no").addClass(fullClass);
        	    switch ( $rootScope.initPageParameterFormPhone.mClientType){
	                case 2://ios
	                	if(window.webkit && window.webkit.messageHandlers ){
	                		window.webkit.messageHandlers.fullScreenToLc.postMessage({"data":$scope.isfullLc});
	                	}else{
	                		console.error("没有方法window.webkit.messageHandlers.fullScreenToLc.postMessage");
	                	}
	                    break;
	                case 3://android
	                	if(window.JSWhitePadInterface){
	                    	window.JSWhitePadInterface.fullScreenToLc( $scope.isfullLc );
	                	}else{
	                		console.error("没有方法window.JSWhitePadInterface.fullScreenToLc");
	                	}
	                    break;
	                default:
	                	console.error("没有设备类型，无法区分发送给手机的方法");
	                    break;
	            };           
            });
           
		}

		GLOBAL.phone = {
			flag:{
				isInit:false 
			},
			oneToMany : function(roomType){
				GLOBAL.phone.logMessage( {method:'oneToMany',roomType:roomType} , "ios");
				if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant) {
				$(".tl-tool-teacher").show();}
			},
			joinRoom:function(data){
				GLOBAL.phone.logMessage( {method:'joinRoom',data:data} , "ios");
				$scope.studentPeerid = data.peerid;
				$scope.joinRoomUserAdmin=data.nickname;
			},
			dispatchEvent:function(data){
				GLOBAL.phone.logMessage( {method:'dispatchEvent',data:data} , "ios");
				if(data.type=="room-disconnected"){
					$scope.timerAllArry=[0,5,0,0];
					$scope.stopAndStart=false; $scope.isShow=false;$scope.restarting=false;
					$scope.round = false;$scope.isShow=false;
				}
			},
			userSelector:function(data){
				GLOBAL.phone.logMessage( {method:'userSelector',data:data} , "ios");
				$scope.responderStage = data;
			},
			drawPermission:function(isDraw){ //白板可画权限控制，true-可话画 ， false-不可画			
				GLOBAL.phone.logMessage( {method:'drawPermission',isDraw:isDraw} , "ios");
				if(typeof isDraw === 'number'){
					isDraw = (isDraw !== 0) ;
				}
				ServiceLiterally.setIsDrawAble(isDraw);
                $rootScope.publishDynamicPptMediaPermission_video = isDraw ;
                $rootScope.sendSignallingFromDynamicPptTriggerActionClick = isDraw ;
                $rootScope.dynamicPptActionClick = isDraw ;
                $rootScope.h5DocumentActionClick = isDraw ;
                $scope.callController.func._handlerPublishDynamicPptMediaPermission_video();
                $scope.callController.func._handlerDynamicPptActionClick();
				if($rootScope.initPageParameterFormPhone.deviceType === 1){ //ipad
					if(isDraw){
						$("#H5FileSupernatant").hide();
						$("#role_control_draw_permission , #pad-draw-tool").removeClass("no-permission");
					}else{
                        $("#H5FileSupernatant").show();
						$("#role_control_draw_permission, #pad-draw-tool").removeClass("no-permission").addClass("no-permission");
					}	
				}else{
					if(isDraw){
                        $("#H5FileSupernatant").hide();
						$("#role_control_draw_permission , #lc_tool_container").removeClass("no-permission");
					}else{
                        $("#H5FileSupernatant").show();
						$("#role_control_draw_permission, #lc_tool_container").removeClass("no-permission").addClass("no-permission");
					}
				}
				$(document).trigger("checkAynamicPptClickEvent",[]);
				
			} , 
			pageTurningPermission:function(isPageTurning){ //翻页权限控制，true-可翻页， false-不可翻页
				GLOBAL.phone.logMessage( {method:'pageTurningPermission',isPageTurning:isPageTurning} , "ios");
				if(typeof isPageTurning === 'number'){
					isPageTurning = (isPageTurning !== 0) ;
				}	
				$rootScope.newpptPagingPage = isPageTurning ;
		        $scope.callController.func._handlerUpdateNewpptPagingPage();
				if($rootScope.initPageParameterFormPhone.deviceType === 1){ //ipad
					if(isPageTurning){
						$("#page_wrap.ipad , #ppt_page_wrap.ipad , #h5_page_wrap.ipad").removeClass("no-permission")
					}else{				
						$("#page_wrap.ipad , #ppt_page_wrap.ipad , #h5_page_wrap.ipad").addClass("no-permission");
					}
				}else{
					if(isPageTurning){//可翻页时显示
						$("#content.lc-container").find(".lc-tool-icon-wrap.page-phone").removeClass("no-permission");
					}else{//不可翻页时隐藏
						$("#content.lc-container").find(".lc-tool-icon-wrap.page-phone").addClass("no-permission");
					}
				}
			} ,
			setInitPageParameterFormPhone:function (data){ //设置初始化手机端参数
				var that = this ;
				GLOBAL.phone.logMessage( {method:'setInitPageParameterFormPhone',data:data} , "ios");
				if (data.deviceType === 0) {
					$("#ppt_page_wrap,#page_wrap,#h5_page_wrap").hide();
				}else if (data.deviceType === 1) {
					$("#page_wrap").removeClass("no-permission");
				}
				if(!GLOBAL.phone.flag.isInit){
					GLOBAL.phone.flag.isInit = true ;
					$rootScope.initPageParameterFormPhone = data ;
		
					/*执行call相关操作*/				
					if($rootScope.initPageParameterFormPhone.serviceUrl){
						$rootScope.loginController.necessary.serviceUrl = $rootScope.initPageParameterFormPhone.serviceUrl.address ;
						$rootScope.loginController.necessary.servicePort = $rootScope.initPageParameterFormPhone.serviceUrl.port ;
					}		
					
                    if($rootScope.initPageParameterFormPhone.role != undefined && $rootScope.initPageParameterFormPhone.role != null ){
                        $rootScope.joinMeetingMessage.roomrole = $rootScope.initPageParameterFormPhone.role ;
						/*当前登录对象事是否拥有指定角色*/
				
                        $rootScope.hasRole = {};
                        Object.defineProperties( $rootScope.hasRole , {
                            //0：主讲  1：助教    2: 学员   3：直播用户 4:巡检员　10:系统管理员　11:企业管理员　12:管理员
                            roleChairman: {
                                value:$rootScope.joinMeetingMessage && $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleChairman ,
                                writable: false ,
                            },
                            roleTeachingAssistant: {
                                value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleTeachingAssistant  ,
                                writable: false ,
                            },
                            roleStudent: {
                                value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleStudent ,
                                writable: false ,
                            },
                            roleAudit:{
                                value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleAudit ,
                                writable: false ,
                            } ,
                            rolePatrol:{
                                value:$rootScope.joinMeetingMessage && $rootScope.joinMeetingMessage.roomrole === $rootScope.role.rolePatrol ,
                                writable: false ,
                            }
                        });
                
                        that.roleSettingPage($rootScope.joinMeetingMessage.roomrole);
                        if(!$scope.aynamicPPT){
                            $scope.callController.func.aynamicPPTHandler();//动态PPT初始化
                            $scope.aynamicPPT = true ;
                        }
                    }
					if($rootScope.initPageParameterFormPhone.deviceType!=undefined  && $rootScope.initPageParameterFormPhone.deviceType === 1){
						/*ipad 白板工具代码*/
						$("#prev_page_phone,#next_page_phone,#add_literally_page_phone,#prev_page_phone_slide,#next_page_phone_slide,#prev_page_phone_h5,#next_page_phone_h5").hide();
						$scope.drawingBandle = function () {
							//var color = $(".t1-color button[data-color='']");
							var pencilWidth = "";
							var eraserWidth = "";
							$scope.settingSize = function () {
								$scope.customLc.lc.toolStatus.pencilWidth = parseFloat( pencilWidth ) ;//设置画笔尺寸
				    			$scope.customLc.lc.toolStatus.eraserWidth = parseFloat( eraserWidth ) ;//设置橡皮尺寸
				    			if(  $("#tool_eraser").hasClass("active-tool") ){
				                	$scope.customLc.uploadEraserWidth();
				               	}else{
				                	$scope.customLc.uploadPencilWidth();
				                }
							}
							
							$("#tool_mouse,#tool_pencil,#tool_eraser,#tool_teacher").click(function () {
								$(this).parent().siblings(".tl-tool").children("button").removeClass("active");
								$(this).addClass("active");
                            });
							
							/*选择尺寸：*/
							$("#pad-draw-tool .tool-color-top .tool-pencil-size").click(function () {
								pencilWidth = $(this).attr("data-pencil-size");/*保存画笔尺寸*/
								eraserWidth = $(this).attr("data-eraser-size");/*保存橡皮尺寸*/
                                $scope.settingSize();
                                $(this).parent().siblings(".tool-color-top").removeClass("active");
                                $(this).parent().addClass("active");
							});

							/*选择颜色：*/
                            $("#pad-draw-tool .t1-color button").click(function () {
                                $scope.customLc.uploadColor('primary' , "#"+$(this).attr("data-color") );
                                $(this).parent().siblings(".t1-color").removeClass("active");
								$(this).parent().addClass("active");
                            });
						}
						$scope.drawingBandle();

						$(".tool-color-top .tool-pencil-size[data-pencil-size='5']").trigger("click");//初始尺寸为5					
						$("#tool_mouse").trigger("click");

					}else if($rootScope.initPageParameterFormPhone.deviceType!=undefined &&  $rootScope.initPageParameterFormPhone.deviceType === 0 ){
					   /*=========Phone 涂鸦工具 start =====================*/
						$("#lc_tool_container").on("click",".tl-color button",function () { //颜色添加点击事件
				            $(this).parents(".tl-color").addClass("active").siblings(".tl-color").removeClass("active");
				            $scope.customLc.uploadColor('primary' , "#"+$(this).attr("data-color") );//设置画笔颜色
			//	            $scope.customLc.lc.setColor('primary',"#"+$(this).attr("data-color")); 
				        });
				       				       
				        $("#lc_tool_container").on("click",".tl-tool button",function () {//涂鸦工具添加点击事件
				            $(this).parents(".tl-tool").addClass("active").siblings(".tl-tool").removeClass("active");
				        });

                        $("#tool_mouse_phone").trigger("click");

						/*=========Phone 涂鸦工具 end =====================*/
					}
					$("#room").find(".opacity-init").removeClass("opacity-init");
					$rootScope.page.defaultInitLcData();
					$rootScope.page.showLiterally(false);
				}
			} , 
			changeInitPageParameterFormPhone:function(recvObj){  //修改手机初始化参数	
				GLOBAL.phone.logMessage( {method:'changeInitPageParameterFormPhone',recvObj:recvObj} , "ios");
				var that = this ;
				for (var key in recvObj) {
					var value = recvObj[key];
					$rootScope.initPageParameterFormPhone[key] = value ;			
					if(key === "role"){
						$rootScope.joinMeetingMessage.roomrole = $rootScope.initPageParameterFormPhone.role ;
						/*当前登录对象事是否拥有指定角色*/
	                    $rootScope.hasRole = {};
	                    Object.defineProperties( $rootScope.hasRole , {
	                        //0：主讲  1：助教    2: 学员   3：直播用户 4:巡检员　10:系统管理员　11:企业管理员　12:管理员
	                        roleChairman: {
	                            value:$rootScope.joinMeetingMessage && $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleChairman ,
	                            writable: false ,
	                        },
	                        roleTeachingAssistant: {
	                            value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleTeachingAssistant  ,
	                            writable: false ,
	                        },
	                        roleStudent: {
	                            value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleStudent ,
	                            writable: false ,
	                        },
	                        roleAudit:{
	                            value:$rootScope.joinMeetingMessage &&  $rootScope.joinMeetingMessage.roomrole === $rootScope.role.roleAudit ,
	                            writable: false ,
	                        } ,
	                        rolePatrol:{
	                            value:$rootScope.joinMeetingMessage && $rootScope.joinMeetingMessage.roomrole === $rootScope.role.rolePatrol ,
	                            writable: false ,
	                        }
	                    });			                  
						that.roleSettingPage($rootScope.joinMeetingMessage.roomrole);
						if(!$scope.aynamicPPT){
                            $scope.callController.func.aynamicPPTHandler();//动态PPT初始化
                            $scope.aynamicPPT = true ;
						}
						if($rootScope.initPageParameterFormPhone.deviceType!=undefined  && $rootScope.initPageParameterFormPhone.deviceType === 1){
							if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
								$("#pad-draw-tool .t1-color button.red").trigger("click");
							}else{
								$("#pad-draw-tool .t1-color button.black").trigger("click");
							}
						}else if($rootScope.initPageParameterFormPhone.deviceType!=undefined &&  $rootScope.initPageParameterFormPhone.deviceType === 0 ){
							if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
								$("#lc_tool_container").find(".tl-color button.red").trigger("click");
							}else{
								$("#lc_tool_container").find(".tl-color button.black").trigger("click");
							}
						}
				
					}else if(key == 'addPagePermission'){					
						if(ServiceLiterally.ele.attr("data-file-id") == 0){						
							$rootScope.page.pageOperation(null,false , false , false , false);							
						}						
					}
				}
			},
			receivePhoneByTriggerEvent:function(eventName, params){ //接收手机端数据
				GLOBAL.phone.logMessage( {method:'receivePhoneByTriggerEvent',eventName:eventName , params:params} , "ios");
				var obj = {params:params};
				$(document).trigger(eventName,[obj]);
			},
			phonePage:function(date){
				GLOBAL.phone.logMessage( {method:'phonePage',date:date} , "ios");
				if (typeof date == "string") {
					var date = JSON.parse(date);
				}
				if (date.isDynamicPPT == true) {
					if (date.action == 'left') {
						$("#prev_page_phone_slide").trigger("click");
					}else if(date.action == 'right'){
						$("#next_page_phone_slide").trigger("click");
					}
				} else if (date.isH5Document == true) {
                    if (date.action == 'left') {
                        $("#prev_page_phone_h5").trigger("click");
                    }else if(date.action == 'right'){
                        $("#next_page_phone_h5").trigger("click");
                    }
				} else {
					if (date.action == 'left') {
						$rootScope.page.prevPage();
					}else if(date.action == 'right'){
						$rootScope.page.nextPage();
					}
				}
			},
			logMessage:function(message,clientType){ //日志接口				
				console.info("logMessage:" , message , clientType);
				message = JSON.stringify(message);
				var typeNum  = null ;
				switch (clientType){
					case "ios":
						typeNum = 2 ;
						break;
					case "android":
						typeNum = 3 ;
						break;
					default:
						typeNum = $rootScope.initPageParameterFormPhone.mClientType ;
						break;
				}
				if(typeNum != $rootScope.initPageParameterFormPhone.mClientType){
					return ;
				}
				switch ( typeNum){
	            	case 2://ios
	            		if(window.webkit && window.webkit.messageHandlers ){
	                		window.webkit.messageHandlers.printLogMessage.postMessage({"data":{msg:message}});
	                	}else{
	                		console.error("没有方法window.webkit.messageHandlers.printLogMessage.postMessage");
	                	}
	                    break;
	                case 3://android
	                	if(window.JSWhitePadInterface){
	                    	window.JSWhitePadInterface.printLogMessage( {msg:message} );
	                	}else{
	                		console.error("没有方法window.JSWhitePadInterface.printLogMessage");
	                	}
	                default:
	                    break;
	            }
			},
			clearLcAllData:function(){ //清除白板所有数据
				GLOBAL.phone.logMessage( {method:'clearLcAllData'} , "ios");
				ServiceLiterally.clearAll(false);
			},
			roleSettingPage:function(role){ //角色决定ipad页面
				GLOBAL.phone.logMessage( {method:'roleSettingPage' , role:role} , "ios");
				if(role === $rootScope.role.roleStudent){
					$(".role-student-hide").css({"display":"none !important"});
				}
			} , 
			newPptAutoPlay:function(videoDataJson){ //动态PPT自动播放		
				GLOBAL.phone.logMessage( {method:'newPptAutoPlay' , videoDataJson:videoDataJson} , "ios");
				try{
					if(typeof videoDataJson  ==  "string"){
						videoDataJson = JSON.parse(videoDataJson);
					}
					var videoData = videoDataJson.videoData ;
					if(typeof videoData  ==  "string"){
						videoData = JSON.parse(videoData);						
					}
					if(videoData.action === "autoPlayVideoInNewPpt" && videoData.videoElementId ){				
						var $videoEle = $("#phone_video_play_newppt");
						$videoEle.show();
						if($videoEle && $videoEle.length > 0  && $videoEle.find("source").length>0 ){
							$videoEle[0].currentTime = 0 ;
							if(videoData.isPlay){
								$videoEle[0].play();
							}else{
								$videoEle[0].pause();
							}	
						}
					}					
				}catch(e){
					console.error("newPptAutoPlay error:" ,e);
				}							
			},
			closeNewPptVideo:function(){
				GLOBAL.phone.logMessage( {method:'closeNewPptVideo'} , "ios");
			    var data = {
		            action:"closeDynamicPptAutoVideo"
		        };
		        ServiceNewPptAynamicPPT.postMessage(data);
			},
			resizeNewpptHandler:function(data){ //改变动态ppt大小
				GLOBAL.phone.logMessage( {method:'resizeNewpptHandler' , data:data} , "ios");
               if(ServiceNewPptAynamicPPT.remoteData  ){
			      	if(typeof data == "string"){
			      		data = JSON.parse(data);
			      	}
			      	data.action = "resizeHandler" ;
               		ServiceNewPptAynamicPPT.postMessage(data);               
               	}else{
              		console.error("phone resizeHandler error:" , ServiceNewPptAynamicPPT.remoteData);
                }              	
			}

		};
	  	
		$scope.bindDocumentEvent();
		$scope.phone.func.execute();
  
		if(window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.onPageFinished ){//ios
			window.webkit.messageHandlers.onPageFinished.postMessage({"data":""});
		}else if(window.JSWhitePadInterface && window.JSWhitePadInterface.onPageFinished){//android
	    	window.JSWhitePadInterface.onPageFinished();
		}else{
			console.error("没有方法window.webkit.messageHandlers.onPageFinished.postMessage 和 window.JSWhitePadInterface.onPageFinished");
		}
		
		GLOBAL.phone.drawPermission(false);
		GLOBAL.phone.pageTurningPermission(false);	
	

		//倒计时	
		$scope.timerAllArry=[0,5,0,0];
		$scope.stopAndStart=false;
		$scope.receveSinlingTime="";
		$scope.joinRoomTime="";
		$scope.timeReduceArry=0;
		$scope.timeAfterReduce=0;
		$scope.timeSaveArry=null;
		$scope.arryTime=false;
		$scope.restarting=false;
		$scope.stop =null;
		$scope.stopStudent=null;
		$scope.data={
        "name":"音频",
        "url":"../../music/ring.mp3"//视频路径
		};  
		//$scope.data.url = $sce.trustAsResourceUrl($scope.data.url);//第二种处理方式     
		   /*播放音乐*/
	    $scope.playAudioToAudiooutputTeacher =function(){
				var audios = document.getElementById("ring_audio");
				audios.load();
			    audios.play();  
	    }; 
	    $scope.playAudioToAudiooutputStudetn =function(){
				var audiosStudetn = document.getElementById("ring_audio_student");
				audiosStudetn.load();
			    audiosStudetn.play();  
	    }; 
		$scope.timeReduce=function(){
			var a=$(".timer-teachTool-num-content").eq(3).text();
			var b=$(".timer-teachTool-num-content").eq(2).text();
			var c=$(".timer-teachTool-num-content").eq(1).text();
			var d=$(".timer-teachTool-num-content").eq(0).text();
			$scope.timerAllArry=[d,c,b,a];
	        	a--;
				$(".timer-teachTool-num-content").eq(3).text(a);
				$scope.secondsArry[1]=a;
				$scope.timerAllArry[3]=a;
	        if(a<0){
	            $(".timer-teachTool-num-content").eq(3).text(9);
	            $scope.secondsArry[1]=9;
	            $scope.timerAllArry[3]=9;
	            b--;
	            $(".timer-teachTool-num-content").eq(2).text(b);
	            $scope.timerAllArry[2]=b;
	            $scope.secondsArry[0]=b
	        }if(b<0){
	            $(".timer-teachTool-num-content").eq(2).text(5);
	            $scope.secondsArry[0]=5
	            $scope.timerAllArry[2]=5
	            c--;
	            $(".timer-teachTool-num-content").eq(1).text(c);
	            $scope.timerAllArry[1]=c;
	            $scope.minutesArry[1]=c
	        }if(c<0){
	           $(".timer-teachTool-num-content").eq(1).text(9);
	           $scope.minutesArry[1]=9;
	           $scope.timerAllArry[1]=9;
	            d--;
	            $(".timer-teachTool-num-content").eq(0).text(d);
	            $scope.timerAllArry[0]=d;
	            if(d<0){
	            	$scope.timerAllArry=[0,0,0,0];
	            	$(".timer-teachTool-num-content").eq(3).text(0);
	            	$(".timer-teachTool-num-content").eq(2).text(0);
	            	$(".timer-teachTool-num-content").eq(1).text(0);
	            	$(".timer-teachTool-num-content").eq(0).text(0);
	            	$(".timer-teachTool-startBtn").hide();
	            	$(".timer-teachTool-num-content").css("color","red");
	            	 $interval.cancel($scope.stop);
	            	$scope.playAudioToAudiooutputTeacher();
				}
	            
	        }if(a==0&&b==0&&c==0&&d==0){
	        	for(var i=0;i<4;i++){
	        		$(".timer-teachTool-num-content").eq(i).text(0);
	        	}
	           $scope.playAudioToAudiooutputTeacher();
	            $(".timer-teachTool-num-content").css("color","red");
	            $interval.cancel($scope.stop);
	        }
	       
		};
		$scope.studentReduceTime=function(){
			var e=$(".timer-studentTool-num-content").eq(3).text();
			var f=$(".timer-studentTool-num-content").eq(2).text();
			var g=$(".timer-studentTool-num-content").eq(1).text();
			var h=$(".timer-studentTool-num-content").eq(0).text();
	        	e--;
				$(".timer-studentTool-num-content").eq(3).text(e);
			if(e < 0) {
				$(".timer-studentTool-num-content").eq(3).text(9);
				f--;
				$(".timer-studentTool-num-content").eq(2).text(f);
			}
			if(f < 0) {
				$(".timer-studentTool-num-content").eq(2).text(5);
				g--;
				$(".timer-studentTool-num-content").eq(1).text(g);
			}
			if(g < 0) {
				$(".timer-studentTool-num-content").eq(1).text(9);
				h--;
			if(h < 0) {
				$(".timer-studentTool-num-content").eq(3).text(0);
				$(".timer-studentTool-num-content").eq(2).text(0);
				$(".timer-studentTool-num-content").eq(1).text(0);
				$(".timer-studentTool-num-content").eq(0).text(0);
				$(".timer-studentTool-num-content").css("color", "red");
				$interval.cancel($scope.stopStudent);
				$scope.playAudioToAudiooutputStudetn();
			}
			}
			if(e == 0 && f == 0 && g == 0 && h == 0) {
				for(var i = 0; i < 4; i++) {
					$(".timer-studentTool-num-content").eq(i).text(0);
				}
				$(".timer-studentTool-num-content").css("color", "red");
				$interval.cancel($scope.stopStudent);
				$scope.playAudioToAudiooutputStudetn();
			}
		}
		$scope.minutesArry=[0,5];
		$scope.secondsArry=[0,0];
		$scope.timerIsShow=false;
		//开始
	$scope.startBtnTool = function(){
			if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ){
			$(".timer-teachTool-triangle-top").css("visibility","hidden");
			$(".timer-teachTool-triangle-down").css("visibility","hidden");
			$(".timer-teachTool-num-content").css("color","black");
			$scope.timerIsShow=false;
			$scope.stopAndStart = true;
			$scope.restarting=false;
			$interval.cancel($scope.stop);
			$(".timer-teachTool-wrap").show();
			$scope.stop = $interval($scope.timeReduce,1000);
            $(".timer-teachTool-startBtn").hide();
            $(".timer-teachTool-stopBtn").show();
            $(".timer-teachTool-againBtn").show();
            $(".timer-teachTool-againBtn-unclickable").hide();
			var timerData=$scope.stopAndStart;
			var	dataTimerArry = $scope.timerAllArry
			var iconShow = $scope.isShow
			var isRestart = $scope.restarting
			var testData={
					isStatus:timerData,
	            	sutdentTimerArry:dataTimerArry,
	            	isShow:iconShow,
	            	isRestart:isRestart
					}, signallingName = "timer" , assignId = "timerMesg";
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]); 
 		}
      };
      /*暂停*/
   $scope.stopBtnHandel = function(){
   		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
      	$scope.stopAndStart =false;
      	$scope.restarting=false;
      	$interval.cancel($scope.stop);
		 $(".timer-teachTool-startBtn").show();
	     $(".timer-teachTool-stopBtn").hide();
	      $(".timer-teachTool-againBtn").show();
	      $(".timer-teachTool-againBtn-unclickable").hide();
		$scope.timerAllArry=$scope.minutesArry.concat($scope.secondsArry);
		var timerData=$scope.stopAndStart;
		var	dataTimerArry = $scope.timerAllArry
		var iconShow = $scope.timerIsShow
		var isRestart = $scope.restarting
		var testData={
					isStatus:timerData,
	            	sutdentTimerArry:dataTimerArry,
	            	isShow:iconShow,
	            	isRestart:isRestart
				}, signallingName = "timer" , assignId = "timerMesg";
		$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]); 
		}
      }
      $scope.timerShowArr = function(timerShowArr) {
			$scope.timeReduceArry = timerShowArr.ts;
			var serviceTimeData = $scope.joinRoomTime - timerShowArr.ts;
			var timeArrAdd = timerShowArr.data.sutdentTimerArry[0] * 600 +timerShowArr.data.sutdentTimerArry[1] * 60 + timerShowArr.data.sutdentTimerArry[2] * 10 +timerShowArr.data.sutdentTimerArry[3] * 1;
			var timesValue = timeArrAdd - serviceTimeData;
			$(".timer-studentTool-num-content").eq(3).text(timerShowArr.data.sutdentTimerArry[3]);
			$(".timer-studentTool-num-content").eq(2).text(timerShowArr.data.sutdentTimerArry[2]);
			$(".timer-studentTool-num-content").eq(1).text(timerShowArr.data.sutdentTimerArry[1]);
			$(".timer-studentTool-num-content").eq(0).text(timerShowArr.data.sutdentTimerArry[0]);
			
				if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant||$rootScope.hasRole.rolePatrol){
					$(".timer-teachTool-num-content").eq(3).text(timerShowArr.data.sutdentTimerArry[3]);
					$(".timer-teachTool-num-content").eq(2).text(timerShowArr.data.sutdentTimerArry[2]);
					$(".timer-teachTool-num-content").eq(1).text(timerShowArr.data.sutdentTimerArry[1]);
					$(".timer-teachTool-num-content").eq(0).text(timerShowArr.data.sutdentTimerArry[0]);
					if(timerShowArr.data.isShow){
							$(".timer-teachTool-wrap").show();
							$(".timer-teachTool-startBtn").show();
							$(".timer-teachTool-stopBtn").hide();
							$(".timer-teachTool-againBtn").hide();
							$(".timer-teachTool-againBtn-unclickable").show();
							$(".timer-teachTool-triangle-top").css("visibility","visible");
							$(".timer-teachTool-triangle-down").css("visibility","visible");
					}else{
					if(timerShowArr.data.isStatus) {
							$(".timer-teachTool-wrap").show();
							$(".timer-teachTool-startBtn").hide();
							$(".timer-teachTool-stopBtn").show();
							$(".timer-teachTool-againBtn").show();
							$(".timer-teachTool-againBtn-unclickable").hide();
							$(".timer-teachTool-triangle-top").css("visibility","hidden");
							$(".timer-teachTool-triangle-down").css("visibility","hidden");
							if(timesValue > 0 && timesValue<timeArrAdd) {
								var m = (parseInt(timesValue / 60) < 10 ? '0' + parseInt(timesValue / 60) : parseInt(timesValue / 60));
								var n = (parseInt(timesValue % 60) < 10 ? '0' + parseInt(timesValue % 60) : parseInt(timesValue % 60));
								var minuteOne = m.substring(0, 1);
								var minuteTwo = m.substring(1);
								var secondsOne = parseInt(n / 10);
								var secondsTwo = parseInt(n % 10);
								$(".timer-teachTool-num-content").eq(3).text(secondsTwo);
								$(".timer-teachTool-num-content").eq(2).text(secondsOne);
								$(".timer-teachTool-num-content").eq(1).text(minuteTwo);
								$(".timer-teachTool-num-content").eq(0).text(minuteOne);
								$interval.cancel($scope.stop);
								$scope.stop = $interval($scope.timeReduce, 1000);
							}if(timesValue>timeArrAdd){
								$interval.cancel($scope.stop);
								$scope.stop = $interval($scope.timeReduce, 1000);
							}if(timesValue<0){
								$interval.cancel($scope.stop);
								$(".timer-teachTool-startBtn").show();
								$(".timer-teachTool-stopBtn").hide();
							}
						} else {
							if(timerShowArr.data.isRestart){
								$(".timer-teachTool-startBtn").show();
								$(".timer-teachTool-stopBtn").hide();
								$(".timer-teachTool-againBtn").show();
								$(".timer-teachTool-againBtn-unclickable").hide();
								$interval.cancel($scope.stop);
		                		$scope.minutesArry=[timerShowArr.data.sutdentTimerArry[0],timerShowArr.data.sutdentTimerArry[1]];
		                    	$scope.secondsArry=[timerShowArr.data.sutdentTimerArry[2],timerShowArr.data.sutdentTimerArry[3]];
		                		$(".timer-teachTool-num-content").eq(3).text(timerShowArr.data.sutdentTimerArry[3]);
								$(".timer-teachTool-num-content").eq(2).text(timerShowArr.data.sutdentTimerArry[2]);
								$(".timer-teachTool-num-content").eq(1).text(timerShowArr.data.sutdentTimerArry[1]);
								$(".timer-teachTool-num-content").eq(0).text(timerShowArr.data.sutdentTimerArry[0]);
								$(".timer-teachTool-num-content").css("color","black");
								$(".timer-teachTool-triangle-top").css("visibility","visible");
								$(".timer-teachTool-triangle-down").css("visibility","visible");
							}else{
								$(".timer-teachTool-startBtn").show();
								$(".timer-teachTool-stopBtn").hide();
								$(".timer-teachTool-againBtn").show();
								$(".timer-teachTool-againBtn-unclickable").hide();
								$interval.cancel($scope.stop);
		                		$scope.minutesArry=[timerShowArr.data.sutdentTimerArry[0],timerShowArr.data.sutdentTimerArry[1]];
		                    	$scope.secondsArry=[timerShowArr.data.sutdentTimerArry[2],timerShowArr.data.sutdentTimerArry[3]];
		                		$(".timer-teachTool-num-content").eq(3).text(timerShowArr.data.sutdentTimerArry[3]);
								$(".timer-teachTool-num-content").eq(2).text(timerShowArr.data.sutdentTimerArry[2]);
								$(".timer-teachTool-num-content").eq(1).text(timerShowArr.data.sutdentTimerArry[1]);
								$(".timer-teachTool-num-content").eq(0).text(timerShowArr.data.sutdentTimerArry[0]);
								$(".timer-teachTool-num-content").css("color","black");
								$(".timer-teachTool-triangle-top").css("visibility","hidden");
								$(".timer-teachTool-triangle-down").css("visibility","hidden");
							}
							
	                		}
						}
				}else{
					if(timerShowArr.data.isShow){
						$(".timer-studentTool-wrap").hide();
					}else{
					if(timerShowArr.data.isStatus) {
						$(".timer-studentTool-wrap").show();
						if(timesValue > 0 && timesValue<timeArrAdd) {
							var m = (parseInt(timesValue / 60) < 10 ? '0' + parseInt(timesValue / 60) : parseInt(timesValue / 60));
							var n = (parseInt(timesValue % 60) < 10 ? '0' + parseInt(timesValue % 60) : parseInt(timesValue % 60));
							var minuteOne = m.substring(0, 1);
							var minuteTwo = m.substring(1);
							var secondsOne = parseInt(n / 10);
							var secondsTwo = parseInt(n % 10);
							$(".timer-studentTool-num-content").eq(3).text(secondsTwo);
							$(".timer-studentTool-num-content").eq(2).text(secondsOne);
							$(".timer-studentTool-num-content").eq(1).text(minuteTwo);
							$(".timer-studentTool-num-content").eq(0).text(minuteOne);
							$interval.cancel($scope.stopStudent);
							$scope.stopStudent = $interval($scope.studentReduceTime, 1000);
							$(".stop-btn-Img").hide();
						}if(timesValue>timeArrAdd){
							$interval.cancel($scope.stopStudent);
							$scope.stopStudent = $interval($scope.studentReduceTime, 1000);
							$(".stop-btn-Img").hide();
						}if(timesValue<0){
							$interval.cancel($scope.stopStudent);
							$(".stop-btn-Img").show();
						}
					} else {
						$interval.cancel($scope.stopStudent);
						$(".timer-studentTool-num-content").eq(3).text(timerShowArr.data.sutdentTimerArry[3]);
						$(".timer-studentTool-num-content").eq(2).text(timerShowArr.data.sutdentTimerArry[2]);
						$(".timer-studentTool-num-content").eq(1).text(timerShowArr.data.sutdentTimerArry[1]);
						$(".timer-studentTool-num-content").eq(0).text(timerShowArr.data.sutdentTimerArry[0]);
						$(".timer-studentTool-num-content").css("color","black");
						$(".stop-btn-Img").show();
					}
				}
			}
			$scope.timeSaveArry=null;
		}
      /*关闭*/

      $scope.timerTeacherCloseHandel= function(){
      	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
      		$scope.timerIsShow=false;
      		$scope.restarting=false;
      		$interval.cancel($scope.stop);
      		$(".timer-teachTool-num-content").eq(3).text(0);
			$(".timer-teachTool-num-content").eq(2).text(0);
			$(".timer-teachTool-num-content").eq(1).text(5);
			$(".timer-teachTool-num-content").eq(0).text(0);
      		$(".timer-teachTool-wrap").hide();
      		$(".timer-studentTool-wrap").hide();
      		$(".timer-teachTool-againBtn-unclickable").show();
      		$(".timer-teachTool-triangle-top").css("visibility","visible");
			$(".timer-teachTool-triangle-down").css("visibility","visible");
			$(".timer-teachTool-againBtn").hide();
			$scope.stopAndStart = false;
			$scope.minutesArry=[0,5];
			$scope.secondsArry=[0,0];
			$scope.timerAllArry=$scope.minutesArry.concat($scope.secondsArry);
      		var timerData=$scope.stopAndStart;
			var	dataTimerArry = $scope.timerAllArry
			var iconShow = $scope.isShow
			var isRestart = $scope.restarting
			var testData={
					isStatus:timerData,
	            	sutdentTimerArry:dataTimerArry,
	            	isShow:iconShow,
	            	isRestart:isRestart
					}, signallingName = "timer" , assignId = "timerMesg";
        	$(document).trigger("deleteLiterallyDataEvent" , [null, testData , signallingName , assignId])
        }
      };
      /*手动增加*/
    $scope.minuteAddHandel = function(index,e){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    	var e = e || window.event;
	 	var c = $(".timer-teachTool-num-content").eq(1).text();
	 	var d = $(".timer-teachTool-num-content").eq(0).text();
	
	 	if(index === 0) {
	 		d++;
	 		$(".timer-teachTool-num-content").eq(0).text(d);
	 		$scope.minutesArry[0] = d;
	 		$scope.timerAllArry[0] = d;
	 		if(d > 9) {
	 			d = 0;
	 			$(".timer-teachTool-num-content").eq(0).text(0);
	 			$scope.minutesArry[0] = 0;
	 			$scope.timerAllArry[0] = 0;
	 		}
	 	}
	 	if(index === 1) {
	 		c++;
	 		$(".timer-teachTool-num-content").eq(1).text(c);
	 		$scope.minutesArry[1] = c;
	 		$scope.timerAllArry[1] = c;
	 		if(c > 9) {
	 			c = 0
	 			$(".timer-teachTool-num-content").eq(1).text(0);
	 			$scope.minutesArry[1] = 0;
	 			$scope.timerAllArry[1] = 0;
	 		}
	 	}
	 	}
    };
    $scope.secondAddHandel = function(index,e){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    	var e=e||window.event;
    	var a=$(".timer-teachTool-num-content").eq(3).text();
		var b=$(".timer-teachTool-num-content").eq(2).text();
    	if(index===0){
    		b++;   		
            $(".timer-teachTool-num-content").eq(2).text(b);
            $scope.secondsArry[0]=b;
            $scope.timerAllArry[2]=b;
            if(b>5){
            	b=0
            	$(".timer-teachTool-num-content").eq(2).text(0)
            	$scope.secondsArry[0]=0;
            	$scope.timerAllArry[2]=0;
            }
        }if(index===1){
        	a++;
            $(".timer-teachTool-num-content").eq(3).text(a);
            $scope.secondsArry[1]=a;
            $scope.timerAllArry[3]=a;
            if(a>9){
            	a=0;
            	$(".timer-teachTool-num-content").eq(3).text(0);
            	$scope.secondsArry[1]=0;
            	$scope.timerAllArry[3]=0;
            }
        }
       }
    };
    $scope.minuteReduceHandel = function(index,e){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    	var e=e||window.event;
    	var c=$(".timer-teachTool-num-content").eq(1).text();
		var d=$(".timer-teachTool-num-content").eq(0).text();
        if(index===0){
        	d--;
           $(".timer-teachTool-num-content").eq(0).text(d);
           $scope.minutesArry[0]=d;
           $scope.timerAllArry[0]=d;
            if( d<0){
            	d=9;
                $(".timer-teachTool-num-content").eq(0).text(9);
                $scope.minutesArry[0]=9;
                $scope.timerAllArry[0]=9;
            }
        }if(index===1){
        	c--;
            $(".timer-teachTool-num-content").eq(1).text(c);
            $scope.minutesArry[1]=c;
            $scope.timerAllArry[1]=c;
            if(c<0){
            	c=9;
                $(".timer-teachTool-num-content").eq(1).text(9);
                $scope.minutesArry[1]=9;
                $scope.timerAllArry[1]=9;
            }
        }
        }
    };
    $scope.secondReduceHandel = function(index,e){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    	var e=e||window.event;
    	var a=$(".timer-teachTool-num-content").eq(3).text();
		var b=$(".timer-teachTool-num-content").eq(2).text();
    	if(index===0){
    		b--;
            $(".timer-teachTool-num-content").eq(2).text(b);
            $scope.secondsArry[0]=b;
            $scope.timerAllArry[2]=b;
            if(b<0){
            	b=5;
            	$(".timer-teachTool-num-content").eq(2).text(5);
            	$scope.secondsArry[0]=5;
            	$scope.timerAllArry[2]=5;
            }
        }if(index===1){
        	a--;
            $(".timer-teachTool-num-content").eq(3).text(a);
            $scope.secondsArry[1]=a;
            $scope.timerAllArry[2]=a;
            if(a<0){
            	a=9;
            	$(".timer-teachTool-num-content").eq(3).text(9);
            	$scope.secondsArry[1]=9;
            	$scope.timerAllArry[2]=9;
            }
        }
       }
    }
        /*重新开始*/
    $scope.againBtnHandel=function(e){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    	$scope.timerIsShow=false;
    	$scope.restarting =true;
    	$interval.cancel($scope.stop);
    	$scope.showStudent=[];
    	$scope.timerAllArry=[0,5,0,0];
    	$(".timer-teachTool-startBtn").show();
    	$(".timer-teachTool-num-content").eq(3).text(0);
		$(".timer-teachTool-num-content").eq(2).text(0);
		$(".timer-teachTool-num-content").eq(1).text(5);
		$(".timer-teachTool-num-content").eq(0).text(0);
		$(".timer-teachTool-num-content").css("color","black");
		$(".timer-studentTool-num-content").eq(3).text(0);
		$(".timer-studentTool-num-content").eq(2).text(0);
		$(".timer-studentTool-num-content").eq(1).text(5);
		$(".timer-studentTool-num-content").eq(0).text(0);
		$(".timer-studentTool-num-content").css("color","black");
		$(".timer-teachTool-againBtn").hide();
		$(".timer-teachTool-againBtn-unclickable").show();
		$(".timer-teachTool-triangle-top").css("visibility","visible");
		$(".timer-teachTool-triangle-down").css("visibility","visible");
		$(".timer-teachTool-stopBtn").hide();
		$(".result-student-allResult").children("li").remove();
		$scope.stopAndStart=false;
		var timerData=$scope.stopAndStart;
		var	dataTimerArry = $scope.timerAllArry
		var iconShow = $scope.timerIsShow
		var isRestart = $scope.restarting
		var testData={
					isStatus:timerData,
	            	sutdentTimerArry:dataTimerArry,
	            	isShow:iconShow,
	            	isRestart:isRestart
				}, signallingName = "timer" , assignId = "timerMesg";
        $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);
       }
    };

/*答题卡*/
	$scope.studentAnswerID=null;
	$scope.studentPeerid=null;
	$scope.studentSendArry=[];
	$scope.idA=[];
    $scope.idB=[];
    $scope.idC=[];
    $scope.idD=[];
    $scope.idE=[];
    $scope.idF=[];
    $scope.idG=[];
    $scope.idH=[];
	$scope.studentNumbers=[];
	$scope.trueLV=0;
	$scope.allStudentChosseAnswer={};
	$scope.joinRoomUserAdmin=null;
	
	$scope.trueSelectArry=[];
	$scope.mySelectArry=[];
	$scope.teacherResult=[];
	$scope.showStudent=[];
	$scope.showTeacher=[];
	$scope.lisStyle=false;
	$scope.studentNums=0;
	$scope.round = false;
	$scope.answerIsShow=false;
	$scope.stopAnswerTime =null;
	$scope.initArr = [{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false}];
	$scope.allArr=[{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false},{id:4,"name":"E","sel":false},{id:5,"name":"F","sel":false},{id:6,"name":"G","sel":false},{id:7,"name":"H","sel":false}];
/*答题卡的计时器*/
    $scope.twoChar = function(n) {
            return n >= 10 ? n : "0" + n;
        }
    $scope.timeFun = function() {
            var sec=0;
            var that=this;
            $scope.stopAnswerTime=$interval(function () {
                sec++;
                var date = new Date(0, 0);
                date.setSeconds(sec);
                var h = date.getHours(), m = date.getMinutes(), s = date.getSeconds();
                var answerTimeText = $scope.twoChar(h) + ":" + $scope.twoChar(m) + ":" + $scope.twoChar(s);
                $("#result-teach-mytime").html(answerTimeText) 
            }, 1000);
        };
	/*开始答题*/
	$scope.answerBegin = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
			$scope.langeTYPE();
			if($(".answer-teach-begin").css("background")=="rgb(7, 68, 150) none repeat scroll 0% 0% / auto padding-box border-box"){
				return false;
			};
			for(var i=0;i<$scope.initArr.length;i++){
				if($scope.initArr[i].sel){
					$scope.trueSelectArry.push($scope.initArr[i].name);
					Array.from(new Set($scope.trueSelectArry.sort()));
					}
			}
			
			if($scope.language=="ch"){
    			$(".result-teach-header-left-grey").html($rootScope.chinese.answers.headerTopLeft.text);
    			$(".answersPeople-div").css("width",1+"rem");
    			$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");
    			$(".result-teach-end-question").css("lineHeight",0.4+"rem");
				$(".answersPeople").eq(0).html($rootScope.chinese.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.chinese.answers.numberOfAnswer.text);
				$(".result-teach-accuracy-right").html($rootScope.chinese.answers.tureAccuracy.text);
				$(".result-teach-text").html($rootScope.chinese.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.chinese.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.chinese.answers.restarting.text)
    		}if($scope.language=="en"){
				$(".result-teach-header-left-grey").html($rootScope.english.answers.headerTopLeft.text);
				$("answersPeople-div").css("width",2+"rem");
				$(".result-teach-accuracy-right").css("lineHeight",0.1+"rem");
				$(".result-teach-end-question").css("lineHeight",0.2+"rem");
				$(".answersPeople").eq(0).html($rootScope.english.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.english.answers.numberOfAnswer.text);
				$(".result-teach-accuracy-right").html($rootScope.english.answers.tureAccuracy.text);
				$(".result-teach-text").html($rootScope.english.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.english.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.english.answers.restarting.text)
    		}if($scope.language=="tw"){
    			$(".result-teach-header-left-grey").html($rootScope.complex.answers.headerTopLeft.text);
    			$(".answersPeople-div").css("width",1+"rem");
    			$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");
    			$(".result-teach-end-question").css("lineHeight",0.4+"rem");
				$(".answersPeople").eq(0).html($rootScope.complex.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.complex.answers.numberOfAnswer.text);
				$(".result-teach-accuracy-right").html($rootScope.complex.answers.tureAccuracy.text);
				$(".result-teach-text").html($rootScope.complex.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.complex.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.complex.answers.restarting.text)
    		}
			var answerTimeText = "00" + ":" + "00" + ":" + "00";
            $("#result-teach-mytime").html(answerTimeText) 
			$scope.timeFun();
			 $scope.answerIsShow = false ;
			 $scope.trueLV = 0;
			 $scope.studentNums = 0;
			$(".trueRel").html(0);
			$(".roleNums").html(0);
			$(".result-teach-wrapDiv").show();
			$(".answer-teach-wrapDiv").hide();
			$(".result-teach-end-question").show();
			$(".result-teach-restart-question").hide();
			$(".result-teach-close").hide();
			var iconShow = $scope.answerIsShow;
			var quizTime = $("#result-teach-mytime").html() 
			var showTrue = $scope.trueSelectArry;
			var answerStudentArry = $scope.initArr;
			var answerStudentTime = $("#result-teach-mytime").html();
			var studentSels = $scope.mySelectArry
			var trueLV = $scope.trueLV;
			var allNumbers = $scope.studentNums;
			var dataChoose = $scope.allStudentChosseAnswer;
			var idAS = $scope.idA;
		    var idBS = $scope.idB;
		    var idCS = $scope.idC;
		    var idDS = $scope.idD;
		    var idES = $scope.idE;
		    var idFS = $scope.idF;
		    var idGS = $scope.idG;
		    var idHS = $scope.idH;
		    var rounds = $scope.round;
			var testData={
	            optionalAnswers:answerStudentArry,
	            quizTimes:quizTime,
	            rightAnswers:showTrue,
	            isRound:rounds,
	            studentSelect:studentSels,
	            trueLV:trueLV,
	            allNumbers:allNumbers,
	            dataChoose:dataChoose,
	            idAS:idAS,
	            idBS:idBS,
	            idCS:idCS,
	            idDS:idDS,
	            idES:idES,
	            idFS:idFS,
	            idGS:idGS,
	            idHS:idHS,
	            isShow:iconShow
			}, signallingName = "answer" , assignId = "answerMesg" ;
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);   
		for(var key in $scope.initArr){
				var value = $scope.initArr[key] ;
				value.sel=false;
			}
		}
				
	}
	
	/*提交答案*/

	$scope.resulSubmit = function(){
		
		$scope.langeTYPE();
		if($scope.language=="ch"){
    			$(".changeOneAnswer").html($rootScope.chinese.answers.selectAnswer.text);
				$(".reuslt-submit").html($rootScope.chinese.answers.submitAnswer.text);
    	}if($scope.language=="en"){
				$(".changeOneAnswer").html($rootScope.english.answers.selectAnswer.text);
				$(".reuslt-submit").html($rootScope.english.answers.submitAnswer.text);
				
    		}if($scope.language=="tw"){
    			$(".reuslt-submit").html($rootScope.complex.answers.submitAnswer.text);
				$(".changeOneAnswer").html($rootScope.complex.answers.selectAnswer.text);
    		}
		if($scope.mySelectArry.length==0){
			
			$(".changeOneAnswer").show();
			return false;
		}if($scope.mySelectArry.length>0){
			$(".changeOneAnswer").hide();
		}
		$scope.lisStyle=!$scope.lisStyle;
		
		if($scope.lisStyle){
			$scope._initSelectArr();
			var mySelectArr=$scope.mySelectArry;
			var sendUserID =$scope.studentPeerid
			var sendStudentName = $scope.joinRoomUserAdmin;
			var testData={
				mySelect:mySelectArr,sendStudentName:sendStudentName,sendUserID:sendUserID
			}, signallingName = "submitAnswers" , assignId ="submitAnswers_"+$scope.studentPeerid, toID="__allExceptSender" ,associatedMsgID="answerMesg";
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , undefined , toID, associatedMsgID,undefined]);
        	if($scope.language=="ch"){
    			$(".reuslt-submit").html($rootScope.chinese.answers.changeAnswer.text);
    		}if($scope.language=="en"){
				$(".reuslt-submit").html($rootScope.english.answers.changeAnswer.text);
    		}if($scope.language=="tw"){
    			$(".reuslt-submit").html($rootScope.complex.answers.changeAnswer.text);
    		}
		}else{
			if($scope.language=="ch"){
    			$(".reuslt-submit").html($rootScope.chinese.answers.submitAnswer.text);
    		}if($scope.language=="en"){
				$(".reuslt-submit").html($rootScope.english.answers.submitAnswer.text);
    		}if($scope.language=="tw"){
    			$(".reuslt-submit").html($rootScope.complex.answers.submitAnswer.text);
    		}
    		$scope.mySelectArry=[];
    		 for(var i=0;i<$scope.teacherResult.length;i++){
    		 	$scope.teacherResult[i].sel=false;
    		 	$(".answer-student-lis").eq(i).css("background","#1e2b48");
    		 }
    		$(".reuslt-submit").css("background","#074496");
		}
		
	}
	$scope._initSelectArr= function(){
		$scope.mySelectArry=[];
		$scope.mySelectArry.length=0;
	for(var i=0;i<$scope.teacherResult.length;i++){
		if($scope.teacherResult[i].sel==true){
			$scope.mySelectArry.push($scope.teacherResult[i].name);
					}
			}
	}
	/*结束答题*/
	$scope.answerEndFun = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
		$interval.cancel($scope.stopAnswerTime);
		$scope.langeTYPE();
		$(".result-teach-restart-question").show();
		$(".result-teach-end-question").hide();
		$(".result-teach-close").show();
		$scope.answerIsShow=false;
		$scope.round = true;
		$scope.studentNumbers=[];
		var iconShow = $scope.answerIsShow;
		var quizTime = $("#result-teach-mytime").html() 
		var showTrue = $scope.trueSelectArry;
		var answerStudentArry = $scope.initArr;
		var studentSels = $scope.mySelectArry
		var trueLV = $scope.trueLV;
		var allNumbers = $scope.studentNums;
		var dataChoose = $scope.allStudentChosseAnswer;
		var idAS = $scope.idA;
	    var idBS = $scope.idB;
	    var idCS = $scope.idC;
	    var idDS = $scope.idD;
	    var idES = $scope.idE;
	    var idFS = $scope.idF;
	    var idGS = $scope.idG;
	    var idHS = $scope.idH;
	    var rounds = $scope.round;
		var testData={
            optionalAnswers:answerStudentArry,
            quizTimes:quizTime,
            rightAnswers:showTrue,
            isRound:rounds,
            studentSelect:studentSels,
            trueLV:trueLV,
            allNumbers:allNumbers,
            dataChoose:dataChoose,
            idAS:idAS,
            idBS:idBS,
            idCS:idCS,
            idDS:idDS,
            idES:idES,
            idFS:idFS,
            idGS:idGS,
            idHS:idHS,
            isShow:iconShow
			}, signallingName = "answer" , assignId = "answerMesg" ;
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]); 
        	$("#result-teach-mytime").html(quizTime) ;
        }
	}
	/*重新开始*/
	$scope.answerAgainFun = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
			$scope.langeTYPE();
		$scope.studentNums=0;
		$scope.answerIsShow=true;
		$scope.studentNumbers=[];
		$interval.cancel($scope.stopAnswerTime);
		var answerTimeText = "00" + ":" + "00" + ":" + "00";
        $("#result-teach-mytime").html(answerTimeText) 
		$(".roleNums").html($scope.studentNums);
		$(".answer-teach-add").css("background", "#368bcb");
		$(".answer-teach-reduce").css("background", "#368bcb");
		$(".result-teach-restart-question").hide();
		for(var i=0;i<$scope.initArr.length;i++){
			$(".answer-teach-lis").eq(i).css("background","");
			$(".answer-teach-begin").css("background","");
			$(".answer-student-lis").eq(i).css("background","");
			$(".reuslt-submit").css("background","");
		}
		$(".result-student-wrapDiv").hide();
		$(".result-teach-end-question").show();
		$(".result-teach-wrapDiv").hide();
		$(".answer-teach-wrapDiv").show();
		$("#result-teach-mytime").html("");
		for(var key in $scope.initArr){
			var value = $scope.initArr[key] ;
			value.sel=false;
		};
		$scope.initArr = [{
				id: 0,
				"name": "A",
				"sel": false
			}, {
				id: 1,
				"name": "B",
				"sel": false
			}, {
				id: 2,
				"name": "C",
				"sel": false
			}, {
				id: 3,
				"name": "D",
				"sel": false
			}];
			$scope.mySelectArry = [];
			$scope.trueSelectArry = [];
			$(".result-A").empty();
			$(".result-B").empty();
			$(".result-C").empty();
			$(".result-D").empty();
			$(".result-E").empty();
			$(".result-F").empty();
			$(".result-G").empty();
			$(".result-H").empty();
			$scope.allStudentChosseAnswer = {};
			$(".trueRel").html(0);
			$scope.round = false;
			$scope.trueLV = 0;
			$scope.studentNums = 0;
			var iconShow = $scope.answerIsShow;
			var quizTime = $("#result-teach-mytime").html() 
			var showTrue = $scope.trueSelectArry;
			var answerStudentArry = $scope.initArr;
			var studentSels = $scope.mySelectArry
			var trueLV = $scope.trueLV;
			var allNumbers = $scope.studentNums;
			var dataChoose = $scope.allStudentChosseAnswer;
			var idAS = $scope.idA;
		    var idBS = $scope.idB;
		    var idCS = $scope.idC;
		    var idDS = $scope.idD;
		    var idES = $scope.idE;
		    var idFS = $scope.idF;
		    var idGS = $scope.idG;
		    var idHS = $scope.idH;
		    var rounds = $scope.round;
			var testData={
	            optionalAnswers:answerStudentArry,
	            quizTimes:quizTime,
	            rightAnswers:showTrue,
	            isRound:rounds,
	            studentSelect:studentSels,
	            trueLV:trueLV,
	            allNumbers:allNumbers,
	            dataChoose:dataChoose,
	            idAS:idAS,
	            idBS:idBS,
	            idCS:idCS,
	            idDS:idDS,
	            idES:idES,
	            idFS:idFS,
	            idGS:idGS,
	            idHS:idHS,
	            isShow:iconShow
				}, signallingName = "answer" , assignId = "answerMesg" ;
				$(document).trigger("deleteLiterallyDataEvent",[ null , testData , signallingName , assignId ]); 
	        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId]); 
	        	}
	};
	/*增加*/
	$scope.addAnswerSelect = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
		var initLength=$scope.initArr.length;
			if(initLength>=2){
				 $(".answer-teach-reduce").css("background","#368bcd");
               $(".answer-teach-add").css("background","#368bcd");
			}if(initLength>7){
				$(".answer-teach-add").css("background","#1e2b48");
                return false;
			}
			$scope.initArr.push($scope.allArr[initLength]);
			}
	};
	/*减少*/
	$scope.reduceAnswerSelect = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
		var initLength=$scope.initArr.length;
            if(initLength>=3){
            	$(".answer-teach-add").css("background","#368bcd");
                $scope.initArr.pop();
                 $(".answer-teach-reduce").css("background","#368bcd");
			}else{
                $(".answer-teach-reduce").css("background","#1e2b48");
                return false;
			}
			for(var i=0;i<$scope.initArr.length;i++){
				if($scope.initArr[i].sel==false){
					$(".reuslt-submit").css("background","#074496");
					}
			}
			}
	}
	/*改变颜色*/
	$scope.changeColorStu = function($index){
		if($rootScope.hasRole.roleStudent){
		if($scope.lisStyle){
			return false;
		}
		$scope.mySelectArry=[];
		$scope.mySelectArry.length=0;
		$scope.teacherResult[$index].sel=!$scope.teacherResult[$index].sel;
			if($scope.teacherResult[$index].sel==false){
				$(".reuslt-submit").css("background","#074496");
				$(".answer-student-lis").eq($index).css("background","#1e2b48");
			}else{
				$(".answer-student-lis").eq($index).css("background","#368bcb");
			}
		for(var key in $scope.teacherResult){
				var value = $scope.teacherResult[key] ;
				if(value.sel){
					$(".reuslt-submit").css("background","#4293d0");
				}
			}
		for(var i=0;i<$scope.teacherResult.length;i++){
				if($scope.teacherResult[i].sel){
					$scope.mySelectArry.push($scope.teacherResult[i].name);
                    $scope.mySelectArry.sort();
				}
			}
		}
	};
	/*老师端改变颜色*/
	$scope.changeColor = function($index){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
		$scope.initArr[$index].sel=!$scope.initArr[$index].sel;
			if($scope.initArr[$index].sel==false){
				$(".answer-teach-begin").css("background","#074496");
				$(".answer-teach-lis").eq($index).css("background","#1e2b48");
			}else{
				$(".answer-teach-lis").eq($index).css("background","#368bcb");
			}
		for(var key in $scope.initArr){
				var value = $scope.initArr[key];
				if(value.sel){
					$(".answer-teach-begin").css("background","#4293d0");
				}
			}
		
		}
	};
	/*关闭答题页面*/
	$scope.closeAnswer = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
			$scope.langeTYPE();
		$(".answer-teach-wrapDiv").hide();
		$scope.studentNums="";
		$scope.studentNumbers=[];
		$(".roleNums").html($scope.studentNums);
		$(".trueRel").html(0);
		for(var i=0;i< $scope.initArr.length;i++){
			$(".answer-teach-lis").eq(i).css("background","#1e2b48");
		}
		$(".answer-teach-begin").css("background","#074496");
		$(".answer-teach-reduce").css("background","#368bcd");
        $(".answer-teach-add").css("background","#368bcd");
		for(var key in $scope.initArr){
			var value = $scope.initArr[key];
			value.sel=false;
		}
		$scope.round = false;
		$scope.initArr = [{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false}];
		$scope.trueSelectArry=[];
		$scope.mySelectArry=[];
					$(".result-A").empty();
					$(".result-B").empty();
					$(".result-C").empty();
					$(".result-D").empty();
					$(".result-E").empty();
					$(".result-F").empty();
					$(".result-G").empty();
					$(".result-H").empty();
					$scope.allStudentChosseAnswer={};
		var testData={
				AnswerSHOW:false,
			}, signallingName = "answer" , assignId = "answerMesg" ;
    	$(document).trigger("deleteLiterallyDataEvent" , [null, testData , signallingName , assignId])
    }
	};
	/*关闭结果页面*/
	$scope.closeResult = function(){
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
			$scope.langeTYPE();
			$scope.answerIsShow = false;
		$(".result-teach-wrapDiv").hide();
		$scope.studentNumbers=[];
		$scope.round = false;
		$interval.cancel($scope.stopAnswerTime);
		var answerTimeText = "00" + ":" + "00" + ":" + "00";
        $("#result-teach-mytime").html(answerTimeText) 
		$scope.studentNums=0;
		$(".roleNums").html($scope.studentNums);
		$(".trueRel").html(0);
		for(var i=0;i< $scope.initArr.length;i++){
			$(".answer-teach-lis").eq(i).css("background","#1e2b48");
		}
		$(".answer-teach-begin").css("background","#074496");
		$(".answer-teach-reduce").css("background","#368bcd");
        $(".answer-teach-add").css("background","#368bcd");
		for(var key in $scope.initArr){
			var value = $scope.initArr[key];
			value.sel=false;
		}
		$scope.initArr = [{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false}];
		$scope.trueSelectArry=[];
		$scope.mySelectArry=[];
					$(".result-A").empty();
					$(".result-B").empty();
					$(".result-C").empty();
					$(".result-D").empty();
					$(".result-E").empty();
					$(".result-F").empty();
					$(".result-G").empty();
					$(".result-H").empty();
		$scope.allStudentChosseAnswer={};
		var testData={
				AnswerSHOW:false,
			}, signallingName = "answer" , assignId = "answerMesg" ;
    	$(document).trigger("deleteLiterallyDataEvent" , [null, testData , signallingName , assignId]);
    }
	};
	/*字体*/
	$scope.langeTYPE=function(){
		if($scope.language=="ch"){
    			$(".answer-teach-header-left-grey").html($rootScope.chinese.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.chinese.answers.headerMiddel.text);
				$(".answersPeople-div").css("width",1+"rem");
				$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");
				$(".result-teach-end-question").css("lineHeight",0.4+"rem");
				$(".result-teach-restart-question").css("lineHeight",0.4+"rem");
				$(".answer-teach-begin").css("lineHeight",0.3+"rem");
				$(".trueRel").css("lineHeight",0.2+"rem");
				$(".roleNums").css("lineHeight",0.2+"rem");
				$(".roleNum").css("lineHeight",0.2+"rem");
				$(".trueRelstu").css("lineHeight",0.2+"rem");
				$(".per-cent").css("lineHeight",0.2+"rem");
				$(".answer-teach-begin").html($rootScope.chinese.answers.beginAnswer.text);
				$(".changeOneAnswer").html($rootScope.chinese.answers.selectAnswer.text);
				$(".reuslt-submit").html($rootScope.chinese.answers.submitAnswer.text);
				$(".result-teach-text").html($rootScope.chinese.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.chinese.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.chinese.answers.restarting.text);
				$(".result-teach-accuracy-right").html($rootScope.chinese.answers.tureAccuracy.text);
				$(".answersPeople").eq(0).html($rootScope.chinese.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.chinese.answers.numberOfAnswer.text);
				$(".result-teach-true-result-myText").html($rootScope.chinese.answers.myAnswer.text);
				$(".result-teach-true-result-trueText").html($rootScope.chinese.answers.trueAnswer.text);
    		}if($scope.language=="en"){
    			$(".answer-teach-header-left-grey").html($rootScope.english.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.english.answers.headerMiddel.text);
				$(".answer-teach-begin").html($rootScope.english.answers.beginAnswer.text);
				$(".answersPeople-div").css("width",2+"rem");
				$(".result-teach-accuracy-right").css("lineHeight",0.15+"rem");
				$(".result-teach-end-question").css("lineHeight",0.2+"rem");
				$(".result-teach-restart-question").css("lineHeight",0.2+"rem");
				$(".answer-teach-begin").css("lineHeight",0.2+"rem");
				$(".trueRel").css("lineHeight",0.1+"rem");
				$(".trueRelstu").css("lineHeight",0.1+"rem");
				$(".per-cent").css("lineHeight",0.1+"rem");
				$(".roleNums").css("lineHeight",0.15+"rem");
				$(".roleNum").css("lineHeight",0.15+"rem");
				$(".changeOneAnswer").html($rootScope.english.answers.selectAnswer.text);
				$(".reuslt-submit").html($rootScope.english.answers.submitAnswer.text);
				$(".result-teach-text").html($rootScope.english.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.english.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.english.answers.restarting.text);
				$(".result-teach-accuracy-right").html($rootScope.english.answers.tureAccuracy.text);
				$(".answersPeople").eq(0).html($rootScope.english.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.english.answers.numberOfAnswer.text);
				$(".result-teach-true-result-myText").html($rootScope.english.answers.myAnswer.text);
				$(".result-teach-true-result-trueText").html($rootScope.english.answers.trueAnswer.text);
    		}if($scope.language=="tw"){
    			$(".answer-teach-header-left-grey").html($rootScope.complex.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.complex.answers.headerMiddel.text);
				$(".answersPeople-div").css("width",1+"rem");
				$(".result-teach-accuracy-right").css("lineHeight",0.2+"rem");
				$(".result-teach-end-question").css("lineHeight",0.4+"rem");
				$(".result-teach-restart-question").css("lineHeight",0.4+"rem");
				$(".answer-teach-begin").css("lineHeight",0.3+"rem");
				$(".trueRel").css("lineHeight",0.2+"rem");
				$(".trueRelstu").css("lineHeight",0.2+"rem");
				$(".per-cent").css("lineHeight",0.2+"rem");
				$(".roleNums").css("lineHeight",0.2+"rem");
				$(".roleNum").css("lineHeight",0.2+"rem");
				$(".answer-teach-begin").html($rootScope.complex.answers.beginAnswer.text);
				$(".changeOneAnswer").html($rootScope.complex.answers.selectAnswer.text);
				$(".reuslt-submit").html($rootScope.complex.answers.submitAnswer.text);
				$(".result-teach-text").html($rootScope.complex.answers.trueAnswer.text);
				$(".result-teach-end-question").html($rootScope.complex.answers.endAnswer.text);
				$(".result-teach-restart-question").html($rootScope.complex.answers.restarting.text);
				$(".result-teach-accuracy-right").html($rootScope.complex.answers.tureAccuracy.text);
				$(".answersPeople").eq(0).html($rootScope.complex.answers.numberOfAnswer.text);
				$(".answersPeople").eq(1).html($rootScope.complex.answers.numberOfAnswer.text);
				$(".result-teach-true-result-myText").html($rootScope.complex.answers.myAnswer.text);
				$(".result-teach-true-result-trueText").html($rootScope.complex.answers.trueAnswer.text);
		}
	}
	$scope.clearStudetnAnswerData = function (){
		 $scope.lisStyle = false;
		 $scope.studentNums = 0;
		 $scope.studentAnswerID = null;
		 $scope.studentSendArry = [];
		 $scope.idA = [];
		 $scope.idB = [];
		 $scope.idC = [];
		 $scope.idD = [];
		 $scope.idE = [];
		 $scope.idF = [];
		 $scope.idG = [];
		 $scope.idH = [];
		 $(".result-A").empty();
		 $(".result-B").empty();
		 $(".result-C").empty();
		 $(".result-D").empty();
		 $(".result-E").empty();
		 $(".result-F").empty();
		 $(".result-G").empty();
		 $(".result-H").empty();
		 $scope.studentNumbers = [];
		 $scope.trueLV = 0;
		 $(".result-A-stu").empty();
		 $(".result-B-stu").empty();
		 $(".result-C-stu").empty();
		 $(".result-D-stu").empty();
		 $(".result-E-stu").empty();
		 $(".result-F-stu").empty();
		 $(".result-G-stu").empty();
		 $(".result-H-stu").empty();
		 $scope.allStudentChosseAnswer = {};
		for(var i=0;i<$scope.initArr.length;i++){
			$(".answer-student-lis").eq(i).css("background","#1e2b48");
			$(".reuslt-submit").css("background","#074496");
						};
		$scope.mySelectArry=[];
		$scope.trueSelectArry=[];
		$scope.initArr = [{id:0,"name":"A","sel":false},{id:1,"name":"B","sel":false},{id:2,"name":"C","sel":false},{id:3,"name":"D","sel":false}];
		for(var key in $scope.initArr){
			var value =  $scope.initArr[key];
			value.sel=false;
		};			
	}
	$scope.answerShowHandel = function(e){
		var e=e||window.event;
		e.stopPropagation();
		if($(".answer-teach-wrapDiv").css("display")=="block" || $(".result-teach-wrapDiv").css("display")=="block")
    	{
    		return false
    	}
		if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant ){
			$scope.langeTYPE();
			if($scope.language=="ch"){
    			$(".answer-teach-header-left-grey").html($rootScope.chinese.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.chinese.answers.headerMiddel.text);
				$(".answer-teach-begin").html($rootScope.chinese.answers.beginAnswer.text)
    		}if($scope.language=="en"){
    			$(".answer-teach-header-left-grey").html($rootScope.english.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.english.answers.headerMiddel.text);
				$(".answer-teach-begin").html($rootScope.english.answers.beginAnswer.text)
    		}if($scope.language=="tw"){
    			$(".answer-teach-header-left-grey").html($rootScope.complex.answers.headerTopLeft.text);
				$(".answer-teach-header-left-green").html($rootScope.complex.answers.headerMiddel.text);
				$(".answer-teach-begin").html($rootScope.complex.answers.beginAnswer.text)
    		}
			$scope.answerIsShow=true;
			$(".answer-teach-wrapDiv").show();
			for(var i=0;i< $scope.initArr.length;i++){
			$(".answer-teach-lis").eq(i).css("background","#1e2b48");
			}
			$(".answer-teach-begin").css("background","#074496");
			$(".answer-teach-reduce").css("background","#368bcd");
	        $(".answer-teach-add").css("background","#368bcd");
			$(".teach-box-left").toggle();
			var iconShow=$scope.answerIsShow;
			var quizTime = $("#result-teach-mytime").html() 
			var showTrue = $scope.trueSelectArry;
			var answerStudentArry = $scope.initArr;
			var answerStudentTime = $("#result-teach-mytime").html();
			var studentSels = $scope.mySelectArry
			var trueLV = $scope.trueLV;
			var allNumbers = $scope.studentNums;
			var dataChoose = $scope.allStudentChosseAnswer;
			var idAS = $scope.idA;
		    var idBS = $scope.idB;
		    var idCS = $scope.idC;
		    var idDS = $scope.idD;
		    var idES = $scope.idE;
		    var idFS = $scope.idF;
		    var idGS = $scope.idG;
		    var idHS = $scope.idH;
		    var rounds = $scope.round;
			var testData={
	           optionalAnswers:answerStudentArry,
	            quizTimes:quizTime,
	            rightAnswers:showTrue,
	            isRound:rounds,
	            studentSelect:studentSels,
	            trueLV:trueLV,
	            allNumbers:allNumbers,
	            dataChoose:dataChoose,
	            idAS:idAS,
	            idBS:idBS,
	            idCS:idCS,
	            idDS:idDS,
	            idES:idES,
	            idFS:idFS,
	            idGS:idGS,
	            idHS:idHS,
	            isShow:iconShow
			}, signallingName = "answer" , assignId = "answerMesg" ;
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);   
		}if($scope.answerIsShow){
			return false;
		}	
	}
	/*转盘组件代码*/
	$scope.dialShow=false;
	$scope.nameArr=[];
	$scope.language=window.location.href.substr(-2);	
	$scope.deg = 45, //旋转的默认角度360/8
    $scope.numdeg = 0, //记录上次旋转停止时候的角度
    $scope.num = 0;//记录旋转后数组的位置
	
    /*转盘旋转*/
    $scope.dialTurnHandel = function(){
  
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant) {
    		$(".dialCloseP").hide();
    		var index = Math.floor(Math.random() * 5+1); //得到0-7随机数
            $scope.num = index + $scope.num; //得到本次位置
            $scope.numdeg += index * $scope.deg + Math.floor(Math.random() * 2 + 3) * 360;
			var turn='rotate('+ $scope.numdeg +'deg)';
			$scope.dialShow=false;
    		var iconShow=$scope.dialShow;
			var testData = {
				isShow:iconShow,
				rotationAngle:'rotate('+ $scope.numdeg +'deg)'
			};
			var signallingName = "dial",
				assignId = "dialMesg",
				toID = "__all";
				
				$(".dial-teachComponent-turntable").css("transform",turn);
			$(document).trigger("sendDataToLiterallyEvent", [null, testData, signallingName, assignId]);
			$timeout(function(){
				$(".dialCloseP").show();
			},4000)
			}
        }
    /*关闭转盘*/
    $scope.dialCloseFun = function(){
    	$(".dial-teachComponent-turntable").css("transform",0);
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant) {
    		$scope.deg = 45, //旋转的默认角度360/8
		    $scope.numdeg = 0, //记录上次旋转停止时候的角度
		    $scope.num = 0;//记录旋转后数组的位置
			$(".dial-teachComponent-turntable").css("transform", 0);
			$(".dial-teachComponent-bg").hide();
			$scope.dialShow=false;
			var testData = {
					dialClose: true,
				},
				signallingName = "dial",
				assignId = "dialMesg",
				toID = "__all";
			$(document).trigger("deleteLiterallyDataEvent", [null, testData, signallingName, assignId])
		}
    }
    /*抢答器代码*/
   	$scope.responderShow=false;
    $scope.beginIsStatus=false;
	$scope.userAdmin="";
	$scope.startAngle = -(1 / 2 * Math.PI);
    $scope.tmpAngle = -(1 / 2 * Math.PI);
    $scope.mH = 300;
    $scope.mW =  300;
    $scope.r = $scope.mW / 2; //中间位置
    $scope.cR = $scope.r - 2 * 5; //圆半径
    $scope.endAngle = $scope.startAngle + 2 * Math.PI; //结束角度
    $scope.xAngle = 0.8 * (Math.PI / 180); //偏移角度量
    $scope.fontSize = 35; //字号大小
    $scope.responderStop = null;
    $scope.responderStage = null;
    $scope.studentQiangDaClick = false;
    $scope.responderUserSort={}
    $scope.responderUserArry=[]
    /*显示抢答器组件*/
    $scope.responderShowHandel = function(){
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){		
    		$(".responder-circle").show();
    		if($scope.language=="ch"){
    			$(".responder-begin-circle").html($rootScope.chinese.responder.begin.text);
    		}if($scope.language=="en"){
    			$(".responder-begin-circle").html($rootScope.english.responder.begin.text);
    		}if($scope.language=="tw"){
    			$(".responder-begin-circle").html($rootScope.complex.responder.begin.text);
    		}
    		
    		$(".responder-close-img").hide();
    		$(".responder-restart-img").hide();
    		$scope.responderShow = true;
    		var iconShow=$scope.responderShow;
			var begin = $scope.beginIsStatus;
	        var userAdmin = $scope.userAdmin;
			var testData ={
				isShow:iconShow,
				begin:begin,
	            userAdmin:userAdmin
			}, signallingName = "qiangDaQi" , assignId = "qiangDaQiMesg";
	        $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);  
    	}
    };
     //渲染函数
     $scope.canvasDraw = function(){
        	window.cancelAnimationFrame($scope.responderStop);
        	$scope.c = document.getElementById('myCanvas');
            $scope.ctx = $scope.c.getContext('2d');
            $scope.c.width = 300;
            $scope.c.height = 300;
            $scope.ctx.clearRect(0,0,300,300);
            if($scope.tmpAngle >= $scope.endAngle){
            	
              return;
            }else if($scope.tmpAngle + $scope.xAngle > $scope.endAngle){
                $scope.tmpAngle = $scope.endAngle;
            }else{
                $scope.tmpAngle += $scope.xAngle;
            }
            $scope.ctx.clearRect(0, 0, $scope.mW, $scope.mH);
            //画圈
            $scope.ctx.beginPath();
            $scope.ctx.lineWidth = 6;
            $scope.ctx.strokeStyle = 'blue';
            $scope.ctx.arc($scope.r, $scope.r, $scope.cR,$scope.startAngle, $scope.tmpAngle);
            $scope.ctx.stroke();
            $scope.ctx.closePath();
            $scope.responderStop = requestAnimationFrame($scope.canvasDraw);
       };
       /*开始*/
    $scope.beginResponder= function(){
       		 $scope.canvasDraw();
       		 $scope.responderShow = true;
       		 $scope.beginIsStatus = true;
       		 if($scope.language=="ch"){
	    			$(".responder-begin-circle").html($rootScope.chinese.responder.inAnswer.text);
	    		}if($scope.language=="en"){
	    			$(".responder-begin-circle").html($rootScope.english.responder.inAnswer.text);
	    		}if($scope.language=="tw"){
	    			$(".responder-begin-circle").html($rootScope.complex.responder.inAnswer.text);
	    		}
       		  setTimeout( function(){
            	$(".responder-close-img").show();
    			$(".responder-restart-img").show();
    			$(".responder-circle").show();
	    		if($scope.language=="ch"){
	    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
	    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
	    		}if($scope.language=="en"){
	    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
	    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
	    		}if($scope.language=="tw"){
	    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
	    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
	    		}
              },6000);
              var iconShow = $scope.responderShow;
              var begin = $scope.beginIsStatus;
        	  var userAdmin = $scope.userAdmin
              var testData = {
              	isShow:iconShow,
				begin:begin,
	            userAdmin:userAdmin
              }, signallingName = "qiangDaQi" , assignId = "qiangDaQiMesg";
	        $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);  	  
        };
        /*关闭抢答器*/
    $scope.closeResponder =function(){
       		 $scope.responderShow = false;
       		 $scope.beginIsStatus = false;
       		 $scope.delCanvasData();
       		 $(".responder-close-img").hide();
    		 $(".responder-restart-img").hide();
    		 $(".responder-circle").hide();
    		 if($scope.language=="ch"){
    			$(".responder-begin-circle").html($rootScope.chinese.responder.begin.text);
    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
    		}if($scope.language=="en"){
    			$(".responder-begin-circle").html($rootScope.english.responder.begin.text);
    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
    		}if($scope.language=="tw"){
    			$(".responder-begin-circle").html($rootScope.complex.responder.begin.text);
    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
    		}
              var iconShow = $scope.responderShow;
              var begin = $scope.beginIsStatus;
        	  var userAdmin = $scope.userAdmin
              var testData = {
              	isShow:iconShow,
				begin:begin,
	            userAdmin:userAdmin
              }, signallingName = "qiangDaQi" , assignId = "qiangDaQiMesg";
	        $(document).trigger("deleteLiterallyDataEvent",[ null , testData , signallingName , assignId ]);  	  
       };
       /*重新开始*/
    $scope.restartResponder= function(){
       		$scope.delCanvasData();
		    $(".responder-close-img").hide();
    		$(".responder-restart-img").hide();
            $scope.beginIsStatus = false;
            $scope.responderShow = true;
            if($scope.language=="ch"){
    			$(".responder-begin-circle").html($rootScope.chinese.responder.begin.text);
    			$(".responder-close-img").html($rootScope.chinese.responder.close.text);
    			$(".responder-restart-img").html($rootScope.chinese.responder.restart.text);
    		}if($scope.language=="en"){
    			$(".responder-begin-circle").html($rootScope.english.responder.begin.text);
    			$(".responder-close-img").html($rootScope.english.responder.close.text);
    			$(".responder-restart-img").html($rootScope.english.responder.restart.text);
    		}if($scope.language=="tw"){
    			$(".responder-begin-circle").html($rootScope.complex.responder.begin.text);
    			$(".responder-close-img").html($rootScope.complex.responder.close.text);
    			$(".responder-restart-img").html($rootScope.complex.responder.restart.text);
    		}
            var iconShow = $scope.responderShow;
            var begin = $scope.beginIsStatus;
        	var userAdmin = $scope.userAdmin
            var testData = {
              	isShow:iconShow,
				begin:begin,
	            userAdmin:userAdmin
              }, signallingName = "qiangDaQi" , assignId = "qiangDaQiMesg";
	        $(document).trigger("deleteLiterallyDataEvent",[ null , testData , signallingName , assignId ]);
	        $(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]); 
       };
       /*删除canvas画板数据*/
    $scope.delCanvasData = function(){
      		var c = document.getElementById('myCanvas');
		  	var ctx = c.getContext('2d');
		  	var mW = c.width = 300;
		  	var mH = c.height = 300;
       		ctx.clearRect(0, 0, mW, mH);
       		window.cancelAnimationFrame($scope.responderStop);
       		$scope.startAngle = -(1 / 2 * Math.PI); //开始角度
		    $scope.endAngle = $scope.startAngle + 2 * Math.PI; //结束角度
		    $scope.xAngle = 0.8 * (Math.PI / 180); //偏移角度量
		    $scope.fontSize = 35; //字号大小
		    $scope.tmpAngle = $scope.startAngle; //临时角度变量
      };
      /*学生抢答*/
    $scope.studentQiangDa = function(){
    		
    		if($rootScope.hasRole.roleStudent){
    			for(var item in $scope.responderStage){
    				if($scope.studentPeerid == item &&$scope.studentQiangDaClick==false){
    					 if($scope.language=="ch"){			
			    			if($(".responder-begin-circle-student").html()==$rootScope.chinese.responder.answer.text){
			    				$scope.studentQiangDaClick = true;
		    					var userAdmin = $scope.joinRoomUserAdmin;
		    					var isClick = $scope.studentQiangDaClick;
		    					var testData = {
					              	userAdmin:userAdmin,
					              	isClick:isClick
					              },signallingName = "QiangDaZhe" , assignId ="QiangDaZhe_"+$scope.studentPeerid, toID="__all" ,associatedMsgID="qiangDaQiMesg";
		        				$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , undefined , toID, associatedMsgID,undefined]);
			    			}
			    		}if($scope.language=="en"){
			    			if($(".responder-begin-circle-student").html()==$rootScope.english.responder.answer.text){
			    				$scope.studentQiangDaClick = true;
		    					var userAdmin = $scope.joinRoomUserAdmin;
		    					var isClick = $scope.studentQiangDaClick;
		    					var testData = {
					              	userAdmin:userAdmin,
					              	isClick:isClick
					              },signallingName = "QiangDaZhe" , assignId ="QiangDaZhe_"+$scope.studentPeerid, toID="__all" ,associatedMsgID="qiangDaQiMesg";
		        				$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , undefined , toID, associatedMsgID,undefined]);
			    			}
			    		}if($scope.language=="tw"){
			    			if($(".responder-begin-circle-student").html()==$rootScope.complex.responder.answer.text){
			    				$scope.studentQiangDaClick = true;
		    					var userAdmin = $scope.joinRoomUserAdmin;
		    					var isClick = $scope.studentQiangDaClick;
		    					var testData = {
					              	userAdmin:userAdmin,
					              	isClick:isClick
					              },signallingName = "QiangDaZhe" , assignId ="QiangDaZhe_"+$scope.studentPeerid, toID="__all" ,associatedMsgID="qiangDaQiMesg";
		        				$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId , undefined , toID, associatedMsgID,undefined]);
			    			}
			    		}
    					
    				}
    			}
    		}
    };
    /*显示倒计时组件*/
    $scope.timerShowHandel = function(e){
    	var e=e|| window.event;
    	e.stopPropagation();
    	if($(".timer-teachTool-wrap").css("display")=="block")
    	{
    		return false
    	}
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    		if($scope.language=="ch"){
    			$(".timer-header-text").html($rootScope.chinese.timers.timerSetInterval.text)
    		}if($scope.language=="en"){
    			$(".timer-header-text").html($rootScope.english.timers.timerSetInterval.text)
    		}if($scope.language=="tw"){
    			$(".timer-header-text").html($rootScope.complex.timers.timerSetInterval.text)
    		}
    		$scope.timerIsShow=true;
    		$scope.restarting=false;
			$(".timer-teachTool-wrap").show();
			$(".timer-teachTool-startBtn").show();
			$(".timer-teachTool-againBtn").hide();
			$(".timer-teachTool-stopBtn").hide();
			$(".teach-box-left").hide();
			$(".timer-teachTool-againBtn-unclickable").show();
			$(".timer-teachTool-num-content").css("color","black");
			var timerData=$scope.stopAndStart;
			var	dataTimerArry = $scope.timerAllArry
			var iconShow = $scope.timerIsShow;
			var isRestart = $scope.restarting
				var testData={
					isStatus:timerData,
	            	sutdentTimerArry:dataTimerArry,
	            	isShow:iconShow,
	            	isRestart:isRestart
				}, signallingName = "timer" , assignId = "timerMesg";
	        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);  
		}if($rootScope.hasRole.roleStudent){
			$(".timer-teachTool-wrap").hide();
		}
    }
    /*显示转盘组件*/
    $scope.turnplateShowHandel = function(e){
    	var e=e||window.event;
    	e.stopPropagation();
    	$(".dial-teachComponent-turntable").css("transform",0);
    	if($(".dial-teachComponent-bg").css("display")=="block")
    	{
    		return false
    	}
    	if($rootScope.hasRole.roleChairman || $rootScope.hasRole.roleTeachingAssistant){
    		$scope.dialShow=true;
    		var iconShow=$scope.dialShow;
    		var i=0;
    		var testData={
				 isShow:iconShow,
                rotationAngle:'rotate('+ i * 950 +'deg)',
			} 
			$(".dial-teachComponent-bg").show();
			$(".teach-box-left").hide();
			var signallingName = "dial" , assignId = "dialMesg" , toID="__all";
        	$(document).trigger("sendDataToLiterallyEvent",[ null , testData , signallingName , assignId ]);
			
		}if($rootScope.hasRole.roleStudent){
			$(".dial-teachComponent-bg").hide();
			$(".dialCloseP").hide();
		}
    };

	$(document).on("click",function(){
		$(".teach-box-left").hide();
	})
    $scope.teacherToolChoose = function(e){
    	var e=e||window.event;
    	e.stopPropagation();
    	$(".teach-box-left").toggle();
    	
    }

	});
});
tk_room.filter("trustUrl", ['$sce', function ($sce) {
    return function (recordingUrl) {
        return $sce.trustAsResourceUrl(recordingUrl);
    };
}]);
