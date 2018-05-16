var TK=TK||{};
TK.EventDispatcher=function(b){var a={};b.dispatcher={};b.dispatcher.eventListeners={};b.dispatcher.backupListerners={};a.addEventListener=function(a,e,d){void 0!==a&&null!==a&&(void 0===b.dispatcher.eventListeners[a]&&(b.dispatcher.eventListeners[a]=[]),b.dispatcher.eventListeners[a].push(e),d&&(void 0===b.dispatcher.backupListerners[d]&&(b.dispatcher.backupListerners[d]=[]),b.dispatcher.backupListerners[d].push({eventType:a,listener:e})))};a.removeEventListener=function(a,e){b.dispatcher.eventListeners[a]?(e=
b.dispatcher.eventListeners[a].indexOf(e),-1!==e&&b.dispatcher.eventListeners[a].splice(e,1)):L.Logger.info("[tk-mobile-sdk]not event type: "+a)};a.removeAllEventListener=function(c){if(c&&"object"===typeof c&&"number"===typeof c.length&&"function"===typeof c.splice&&!c.propertyIsEnumerable("length"))for(var e in c){var d=c[e];delete b.dispatcher.eventListeners[d]}else if("string"===typeof c)delete b.dispatcher.eventListeners[c];else if("object"===typeof c)for(var f in c)d=f,a.removeEventListener(d,
c[f])};a.dispatchEvent=function(a,e){var d;(void 0!=e?e:1)&&L.Logger.debug("[tk-mobile-sdk]dispatchEvent , event type: "+a.type);for(d in b.dispatcher.eventListeners[a.type])if(b.dispatcher.eventListeners[a.type].hasOwnProperty(d))b.dispatcher.eventListeners[a.type][d](a)};a.removeBackupListerner=function(c){if(c&&b.dispatcher.backupListerners[c]){for(var e=0;e<b.dispatcher.backupListerners[c].length;e++){var d=b.dispatcher.backupListerners[c][e];a.removeEventListener(d.eventType,d.listener)}b.dispatcher.backupListerners[c].length=
0;delete b.dispatcher.backupListerners[c]}};return a};TK.TalkEvent=function(b){var a={};a.type=b.type;return a};TK.RoomEvent=function(b,a){var c=TK.TalkEvent(b);c.streams=b.streams;c.message=b.message;c.user=b.user;if(a&&"object"===typeof a)for(var e in a)c[e]=a[e];return c};TK.StreamEvent=function(b,a){var c=TK.TalkEvent(b);c.stream=b.stream;c.message=b.message;c.bandwidth=b.bandwidth;c.attrs=b.attrs;if(a&&"object"===typeof a)for(var e in a)c[e]=a[e];return c};
TK.PublisherEvent=function(b,a){b=TK.TalkEvent(b);if(a&&"object"===typeof a)for(var c in a)b[c]=a[c];return b};TK.mobileSdkEventManager=TK.EventDispatcher({});TK.mobileUICoreEventManager=TK.EventDispatcher({});TK=TK||{};TK.SDKTYPE="mobile";TK.PUBLISH_STATE_NONE=0;TK.PUBLISH_STATE_AUDIOONLY=1;TK.PUBLISH_STATE_VIDEOONLY=2;TK.PUBLISH_STATE_BOTH=3;TK.PUBLISH_STATE_MUTEALL=4;
TK.Room=function(){L.Logger.info("[tk-sdk-version]sdk-version:"+(window.__SDKVERSIONS__||"2.1.0")+" , sdk-time: "+(window.__SDKVERSIONSTIME__||"2017120622"));var b=TK.EventDispatcher({}),a={},c={},e={};b.getRoomProperties=function(){return e};b.getUsers=function(){return c};b.getUser=function(b){return c[b]};b.getMySelf=function(){return a};b.getSpecifyRoleList=function(b,a){a={};for(var d in c){var f=c[d];f.role===b&&(a[d]=f)}return a};b.logMessage=function(b,a){MOBILETKSDK.sendInterface.logMessage(b,
a)};b.dynamicPptVideoAutoPlay=function(b){MOBILETKSDK.sendInterface.dynamicPptVideoAutoPlay(b)};b.changeWebPageFullScreen=function(b){MOBILETKSDK.sendInterface.changeWebPageFullScreen(b)};b.onPageFinished=function(){MOBILETKSDK.sendInterface.onPageFinished()};b.pubMsg=function(b,a,c,e,k,l,m,n,p){MOBILETKSDK.sendInterface.pubMsg(b,a,c,e,k,l,m,n,p)};b.delMsg=function(b,a,c,e){MOBILETKSDK.sendInterface.delMsg(b,a,c,e)};b.changeUserProperty=function(b,a,c){MOBILETKSDK.sendInterface.changeUserProperty(b,
a,c)};b.handleMobileSdk_dispatchEvent={"room-userproperty-changed":function(a){a=a.message;L.Logger.debug("[tk-mobile-sdk]room-userproperty-changed info:"+L.Utils.toJsonStringify(a));var d=a.userid;if(a.hasOwnProperty("properties")){var e=a.properties,g=c[d];if(void 0===g)return L.Logger.error("[tk-mobile-sdk]room-userproperty-changed user is not exist , userid is "+d+"!"),!0;for(var k in e)"id"!=k&&"watchStatus"!=k&&(g[k]=e[k]);k=TK.RoomEvent({type:"room-userproperty-changed",user:g,message:e},{fromID:a.fromID});
b.dispatchEvent(k)}return!0},"room-participant_leave":function(a){a=a.userid;L.Logger.debug("[tk-mobile-sdk]room-participant_leave userid:"+a);var d=c[a];if(void 0===d)return L.Logger.error("[tk-mobile-sdk]participantLeft user is not exist , userid is "+a+"!"),!0;TK.global.initPageParameterFormPhone.playback?c[a]&&(c[a].playbackLeaved=!0):delete c[a];a=TK.RoomEvent({type:"room-participant_leave",user:d});b.dispatchEvent(a);return!0},"room-participant_join":function(a){a=a.user;L.Logger.debug("[tk-mobile-sdk]room-participant_join user:"+
L.Utils.toJsonStringify(a));c[a.id]=a;TK.global.initPageParameterFormPhone.playback&&c[a.id]&&delete c[a.id].playbackLeaved;a=TK.RoomEvent({type:"room-participant_join",user:a});b.dispatchEvent(a);return!0},"room-connected":function(d){L.Logger.debug("[tk-mobile-sdk]room-connected info:"+L.Utils.toJsonStringify(d));var f=d.userlist,h=d.msglist,g=d.myself;d=d.roomProperties;g&&(a=g);d&&(e=d);c={};c[a.id]=a;for(var k in f)f.hasOwnProperty(k)&&(g=f[k],void 0!==g&&(c[g.id]=g,TK.global.initPageParameterFormPhone.playback&&
c[g.id]&&delete c[g.id].playbackLeaved));f=[];h&&"string"==typeof h&&(h=L.Utils.toJsonParse(h));for(k in h)h.hasOwnProperty(k)&&f.push(h[k]);f.sort(function(b,a){return void 0!==b&&b.hasOwnProperty("seq")&&void 0!==a&&a.hasOwnProperty("seq")?b.seq-a.seq:0});h=TK.RoomEvent({type:"room-connected",message:f});b.dispatchEvent(h);return!0},"room-disconnected":function(d){L.Logger.debug("[tk-mobile-sdk]room-disconnected");if(c)if(d=c,TK.global.initPageParameterFormPhone.playback)for(e in d)d[e].playbackLeaved=
!0;else for(var e in d)delete d[e];a&&(a.publishstate=TK.PUBLISH_STATE_NONE);e=TK.RoomEvent({type:"room-disconnected"});b.dispatchEvent(e);return!0},"room-playback-clear_all":function(a){if(TK.global.initPageParameterFormPhone.playback)return L.Logger.debug("[tk-mobile-sdk]room-playback-clear_all"),a=TK.RoomEvent({type:"room-playback-clear_all"}),b.dispatchEvent(a),!0;L.Logger.warning("[tk-mobile-sdk]room-playback-clear_all:No playback environment!")},"room-pubmsg":function(a){a=TK.RoomEvent({type:"room-pubmsg",
message:a.message});b.dispatchEvent(a);return!0},"room-delmsg":function(a){a=TK.RoomEvent({type:"room-delmsg",message:a.message});b.dispatchEvent(a);return!0},"room-msglist":function(a){a=TK.RoomEvent({type:"room-msglist",message:a.message});b.dispatchEvent(a);return!0}};b.changeExtendSendInterfaceName=function(a){TK.extendSendInterfaceName=a};TK.mobileSdkEventManager&&TK.mobileSdkEventManager.addEventListener("mobileSdk_dispatchEvent",function(a){a=a.message;var c=a.type,d=!1;b.handleMobileSdk_dispatchEvent[c]&&
"function"===typeof b.handleMobileSdk_dispatchEvent[c]&&(d=b.handleMobileSdk_dispatchEvent[c](a));d||b.dispatchEvent(a)});return b};
(function(){var b=!1;if(void 0!==window.__SDKDEV__&&null!==window.__SDKDEV__&&"boolean"===typeof window.__SDKDEV__)try{b=window.__SDKDEV__}catch(c){b=!1}if(!b){b=decodeURIComponent(window.location.href);var a=b.indexOf("?");b=b.substring(a+1).match(/(^|&)debug=([^&]*)(&|$)/i);b=null!=b?b[2]:""}window.__TkSdkBuild__=!b;window.localStorage&&window.localStorage.setItem("debug",b?"*":"none")})();var L=L||{};TK=TK||{};
L.Logger=function(b){var a="",c=!1;var e=function(a,e){try{switch(e){case b.Logger.DEBUG:c?console.warn.apply(console,a):console.debug.apply(console,a);break;case b.Logger.TRACE:console.trace.apply(console,a);break;case b.Logger.INFO:c?console.warn.apply(console,a):console.info.apply(console,a);break;case b.Logger.WARNING:console.warn.apply(console,a);break;case b.Logger.ERROR:console.error.apply(console,a);break;case b.Logger.NONE:console.warn("log level is none!");break;default:c?console.warn.apply(console,
a):console.log.apply(console,a)}}catch(h){console.log.apply(console,a)}};return{DEBUG:0,TRACE:1,INFO:2,WARNING:3,ERROR:4,NONE:5,setLogDevelopment:function(a){c=a},enableLogPanel:function(){b.Logger.panel=document.createElement("textarea");b.Logger.panel.setAttribute("id","licode-logs");b.Logger.panel.setAttribute("style","width: 100%; height: 100%; display: none");b.Logger.panel.setAttribute("rows",20);b.Logger.panel.setAttribute("cols",20);b.Logger.panel.setAttribute("readOnly",!0);document.body.appendChild(b.Logger.panel)},
setLogLevel:function(a){a>b.Logger.NONE?a=b.Logger.NONE:a<b.Logger.DEBUG&&(a=b.Logger.DEBUG);b.Logger.logLevel=a},setOutputFunction:function(a){e=a},setLogPrefix:function(b){a=b},print:function(c){var d=a;if(!(c<b.Logger.logLevel)){c===b.Logger.DEBUG?d=d+"DEBUG("+(new Date).toLocaleString()+")":c===b.Logger.TRACE?d=d+"TRACE("+(new Date).toLocaleString()+")":c===b.Logger.INFO?d=d+"INFO("+(new Date).toLocaleString()+")":c===b.Logger.WARNING?d=d+"WARNING("+(new Date).toLocaleString()+")":c===b.Logger.ERROR&&
(d=d+"ERROR("+(new Date).toLocaleString()+")");d+=":";for(var h=[],g=0;g<arguments.length;g++)h[g]=arguments[g];h=h.slice(1);h=[d].concat(h);if(void 0!==b.Logger.panel){d="";for(g=0;g<h.length;g++)d+=h[g];b.Logger.panel.value=b.Logger.panel.value+"\n"+d}else e.apply(b.Logger,[h,c])}},debug:function(){for(var a=[],c=0;c<arguments.length;c++)a[c]=arguments[c];b.Logger.print.apply(b.Logger,[b.Logger.DEBUG].concat(a))},trace:function(){for(var a=[],c=0;c<arguments.length;c++)a[c]=arguments[c];b.Logger.print.apply(b.Logger,
[b.Logger.TRACE].concat(a))},info:function(){for(var a=[],c=0;c<arguments.length;c++)a[c]=arguments[c];b.Logger.print.apply(b.Logger,[b.Logger.INFO].concat(a))},warning:function(){for(var a=[],c=0;c<arguments.length;c++)a[c]=arguments[c];b.Logger.print.apply(b.Logger,[b.Logger.WARNING].concat(a))},error:function(){for(var a=[],c=0;c<arguments.length;c++)a[c]=arguments[c];b.Logger.print.apply(b.Logger,[b.Logger.ERROR].concat(a))}}}(L);
TK.tkLogPrintConfig=function(b,a,c){a=a||{};b=b||{};c=c||{};TK&&TK.global&&TK.global.initPageParameterFormPhone&&TK.global.initPageParameterFormPhone.debugLog&&(b.debug=!0,a.development=!0,a.logLevel=0,c.webrtcLogDebug=!0);var e=void 0!=a.logLevel?a.logLevel:0;b=void 0!=b.debug?b.debug:!0;c=void 0!=c.webrtcLogDebug?c.webrtcLogDebug:!0;L.Logger.setLogDevelopment(void 0!=a.development?a.development:!0);L.Logger.setLogLevel(e);window.localStorage&&window.localStorage.setItem("debug",b?"*":"none");window.webrtcLogDebug=
c};L=L||{};L.Constant=function(){return{mClientType:{flash:0,pc:1,ios:2,andriod:3},IOS:"ios",ANDRIOD:"andriod"}}(L);L=L||{};L.hex64Instance=void 0;
(function(){var b=function(a){this._key=[];this._tbl={};for(var b=0;64>b;++b)this._key[b]="JKijklmnoz$_01234ABCDEFGHI56789LMNOPQRpqrstuvwxySTUVWXYZabcdefgh~".charAt(a[b]),this._tbl[this._key[b]]=b;this._pad="~"};b.prototype.enc=function(a){for(var c="",d,f,h,g,k=0,l=b._2to1(a),m=l.length%3,n=l.length-m;k<n;)d=l[k++],f=l[k++],h=l[k++],a=d>>2,d=(d&3)<<4|f>>4,g=(f&15)<<2|h>>6,f=h&63,c+=this._key[a]+this._key[d]+this._key[g]+this._key[f];0<m&&(d=l[k++],f=1<m?l[k]:0,g=(f&15)<<2,c+=this._key[d>>2]+this._key[(d&
3)<<4|f>>4]+(g?this._key[g]:this._pad)+this._pad);return c.replace(/.{76}/g,function(a){return a+"\n"})};b.prototype.dec=function(a){var c=[],d=0,f=0;for(a=a.replace(/[^0-9A-Za-z$_~]/g,"");d<a.length;){var h=this._tbl[a.charAt(d++)];var g=this._tbl[a.charAt(d++)];var k=this._tbl[a.charAt(d++)];var l=this._tbl[a.charAt(d++)];c[f++]=h<<2|g>>4;c[f++]=(g&15)<<4|k>>2;c[f++]=(k&3)<<6|l}a=a.slice(-2);a.charAt(0)==this._pad?c.length-=2:a.charAt(1)==this._pad&&--c.length;return b._1to2(c)};b._2to1=function(a){var b=
!1,c=0,f=[];255<a.charCodeAt(0)&&(b=!0,f[c++]=29);for(var h=0;h<a.length;++h){var g=a.charCodeAt(h);29!=g&&(255>=g?(b&&(f[c++]=29,b=!1),f[c++]=g):(b||(f[c++]=29,b=!0),f[c++]=g>>8,f[c++]=g&255))}return f};b._1to2=function(a){for(var b=!1,c="",f=0;f<a.length;++f){var h=a[f];29==h?b=!b:c=b?c+String.fromCharCode(256*h+a[++f]):c+String.fromCharCode(h)}return c};var a=[15,40,46,43,13,0,51,35,63,36,50,6,32,4,31,62,5,24,8,53,59,41,20,7,37,38,48,18,11,26,19,55,58,10,33,34,49,14,25,44,52,61,16,2,56,23,29,45,
9,3,12,39,30,42,47,22,21,60,1,54,28,57,17,27];64!==a.length?L.Logger.error("\u4e92\u65a5\u503c\u6570\u7ec4\u957f\u5ea6\u5fc5\u987b\u662f65\u4f4d\uff0c\u5f53\u524d\u957f\u5ea6\u4e3a:"+a.length):L.hex64Instance=new b(a)})();
L.Utils=function(){var b=void 0;b={handleMediaPlayOnEvent:function(a,c){try{if(L.Utils.removeEvent(a,"canplay",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadedmetadata",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadeddata",b.handleMediaPlayOnEvent.bind(null,a,c)),a&&a.play&&"function"===typeof a.play){var e=a.play();e&&e.catch&&"function"===typeof e.catch&&e.catch(function(b){L.Logger.error("[tk-sdk]media play err:",L.Utils.toJsonStringify(b),c?" , media element id is "+
c:" media element:",c?"":a)})}}catch(d){L.Logger.error("[tk-sdk]media play error:",L.Utils.toJsonStringify(d),c?" , media element id is "+c:" media element:",c?"":a)}},handleMediaPauseOnEvent:function(a,c){try{if(L.Utils.removeEvent(a,"canplay",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadedmetadata",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadeddata",b.handleMediaPauseOnEvent.bind(null,a,c)),a&&a.pause&&"function"===typeof a.pause){var e=a.pause();
e&&e.catch&&"function"===typeof e.catch&&e.catch(function(b){L.Logger.error("[tk-sdk]media pause err:",L.Utils.toJsonStringify(b),c?" , media element id is "+c:" media element:",c?"":a)})}}catch(d){L.Logger.error("[tk-sdk]media pause error:",L.Utils.toJsonStringify(d),c?" , media element id is "+c:" media element:",c?"":a)}}};return{addEvent:function(a,b,e,d){a.addEventListener?a.addEventListener(b,e,void 0!=d&&null!=d?d:!1):a.attachEvent?a.attachEvent("on"+b,e):a["on"+b]=e},removeEvent:function(a,
b,e,d){a.addEventListener?a.removeEventListener(b,e,d):a.attachEvent?a.detachEvent("on"+b,e):a["on"+b]=null},toJsonStringify:function(a,b){if(void 0!=b&&!b||!a)return a;try{if("object"!==typeof a)return a;var c=JSON.stringify(a);c?a=c:L.Logger.debug("[tk-sdk]toJsonStringify:data is not json!")}catch(d){L.Logger.debug("[tk-sdk]toJsonStringify:data is not json!")}return a},toJsonParse:function(a,b){if(void 0!=b&&!b||!a)return a;try{if("string"!==typeof a)return a;var c=JSON.parse(a);c?a=c:L.Logger.debug("[tk-sdk]toJsonParse:data is not json string!")}catch(d){L.Logger.debug("[tk-sdk]toJsonParse:data is not json string!")}return a},
encrypt:function(a,b){if(!a)return a;b=void 0!=b?b:"talk_2017_@beijing";a=L.hex64Instance.enc(a);return b+a+b},decrypt:function(a,b){if(!a)return a;a=a.replace(new RegExp(void 0!=b?b:"talk_2017_@beijing","gm"),"");return L.hex64Instance.dec(a)},mediaPlay:function(a){var c=void 0;a&&"string"===typeof a?a=document.getElementById(a):a&&/(audio|video)/g.test(a.nodeName.toLowerCase())&&a.getAttribute&&"function"===typeof a.getAttribute&&(c=a.getAttribute("id"));a&&/(audio|video)/g.test(a.nodeName.toLowerCase())&&
(0!==a.readyState?b.handleMediaPlayOnEvent(a,c):(L.Utils.removeEvent(a,"canplay",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadedmetadata",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadeddata",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"canplay",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"loadedmetadata",b.handleMediaPlayOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"loadeddata",b.handleMediaPlayOnEvent.bind(null,a,c))))},
mediaPause:function(a){var c=void 0;a&&"string"===typeof a?a=document.getElementById(a):a&&/(audio|video)/g.test(a.nodeName.toLowerCase())&&a.getAttribute&&"function"===typeof a.getAttribute&&(c=a.getAttribute("id"));a&&/(audio|video)/g.test(a.nodeName.toLowerCase())&&(0!==a.readyState?b.handleMediaPauseOnEvent(a,c):(L.Utils.removeEvent(a,"canplay",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadedmetadata",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.removeEvent(a,"loadeddata",
b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"canplay",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"loadedmetadata",b.handleMediaPauseOnEvent.bind(null,a,c)),L.Utils.addEvent(a,"loadeddata",b.handleMediaPauseOnEvent.bind(null,a,c))))}}}(L);TK=TK||{};TK.extendSendInterfaceName="";var MOBILETKSDK=MOBILETKSDK||{};(function(){var b=!1;if(void 0!==window.__SDKDEV__&&null!==window.__SDKDEV__&&"boolean"===typeof window.__SDKDEV__)try{b=window.__SDKDEV__}catch(c){b=!1}if(!b){b=decodeURIComponent(window.location.href);var a=b.indexOf("?");b=b.substring(a+1).match(/(^|&)debug=([^&]*)(&|$)/i);b=null!=b?b[2]:""}TK.__TkMobileBuild__=!b})();TK.temporary=TK.temporary||{callbackMap:{}};TK.constant=TK.constant||{};
TK.global=TK.global||{initPageParameterFormPhone:{mClientType:null,serviceUrl:{address:null,port:null},deviceType:null,role:null,playback:!1,isSendLogMessageToProtogenesis:!1,debugLog:!1,appType:void 0}};TK.variable=TK.variable||{};
MOBILETKSDK.receiveInterface=MOBILETKSDK.receiveInterface||{dispatchEvent:function(b){MOBILETKSDK.sendInterface.logMessage({method:"dispatchEvent",recvEventData:b});b=L.Utils.toJsonParse(b);TK.mobileSdkEventManager.dispatchEvent({type:"mobileSdk_dispatchEvent",message:b},!1)},setInitPageParameterFormPhone:function(b){MOBILETKSDK.sendInterface.logMessage({method:"setInitPageParameterFormPhone",data:b});b=L.Utils.toJsonParse(b);if(!TK.global.isLoadInitPageParameterFormPhone){TK.global.isLoadInitPageParameterFormPhone=
!0;for(var a in b)"playback"!==a&&"debugLog"!==a||"number"!==typeof b[a]||(b[a]=0!==b[a]),TK.global.initPageParameterFormPhone[a]=b[a];TK&&TK.global&&TK.global.initPageParameterFormPhone&&TK.global.initPageParameterFormPhone.debugLog&&TK.tkLogPrintConfig();TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_setInitPageParameterFormPhone",message:b})}},changeInitPageParameterFormPhone:function(b){MOBILETKSDK.sendInterface.logMessage({method:"changeInitPageParameterFormPhone",changeInitJson:b});
b=L.Utils.toJsonParse(b);for(var a in b)TK.global.initPageParameterFormPhone[a]=b[a];TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_changeInitPageParameterFormPhone",message:b})},changeDynamicPptSize:function(b){MOBILETKSDK.sendInterface.logMessage({method:"changeDynamicPptSize",data:b});b=L.Utils.toJsonParse(b);TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_changeDynamicPptSize",message:b})},closeDynamicPptWebPlay:function(){MOBILETKSDK.sendInterface.logMessage({method:"closeDynamicPptWebPlay"});
TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_closeDynamicPptWebPlay"})},executeWebCallback:function(b){MOBILETKSDK.sendInterface.logMessage({method:"executeWebCallback",callbackJson:b});b=L.Utils.toJsonParse(b);var a=b.callbackId;a?TK.temporary.callbackMap[a]&&"function"===typeof TK.temporary.callbackMap[a]?(b.data&&"string"===typeof b.data&&(b.data=L.Utils.toJsonParse(b.data)),TK.temporary.callbackMap[a](b.data)):L.Logger.info("[tk-mobile-sdk]executeWebCallback:callbackMap[callbackId]  is not exist or not's function , callbackJson:"+
L.Utils.toJsonStringify(b)+"!"):L.Logger.info("[tk-mobile-sdk]executeWebCallback:callbackId is not exist , callbackJson:"+L.Utils.toJsonStringify(b)+"!")},fullScreenChangeCallback:function(b){MOBILETKSDK.sendInterface.logMessage({method:"fullScreenChangeCallback",isFullScreen:b});"number"===typeof b&&(b=0!==b);TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_fullScreenChangeCallback",message:{isFullScreen:b}})},playbackPlayAndPauseController:function(b){TK.global.initPageParameterFormPhone.playback&&
(MOBILETKSDK.sendInterface.logMessage({method:"playbackPlayAndPauseController",play:b}),"number"===typeof b&&(b=0!==b),TK.mobileUICoreEventManager.dispatchEvent({type:"mobileSdk_playbackPlayAndPauseController",message:{play:b}}))}};
MOBILETKSDK.sendInterface=MOBILETKSDK.sendInterface||{interface:function(b,a,c){if(TK.global.initPageParameterFormPhone)switch(c=c||TK.global.initPageParameterFormPhone.mClientType,c){case L.Constant.mClientType.ios:window.webkit&&window.webkit.messageHandlers&&window.webkit.messageHandlers[b]?(a=a||"",L.Logger.debug("[tk-mobile-sdk]interface:window.webkit.messageHandlers."+b+".postMessage has been performed!"),window.webkit.messageHandlers[b].postMessage({data:a})):L.Logger.error("[tk-mobile-sdk]interface:window.webkit.messageHandlers."+
b+".postMessage is not exist!");break;case L.Constant.mClientType.andriod:"_videoWhiteboardPage"===TK.extendSendInterfaceName?window.JSVideoWhitePadInterface&&window.JSVideoWhitePadInterface[b]?(L.Logger.debug("[tk-mobile-sdk]interface:window.JSVideoWhitePadInterface."+b+" has been performed!"),window.JSVideoWhitePadInterface[b](a)):L.Logger.error("[tk-mobile-sdk]interface:window.JSVideoWhitePadInterface."+b+" is not exist!"):window.JSWhitePadInterface&&window.JSWhitePadInterface[b]?(L.Logger.debug("[tk-mobile-sdk]interface:window.JSWhitePadInterface."+
b+" has been performed!"),window.JSWhitePadInterface[b](a)):L.Logger.error("[tk-mobile-sdk]interface:window.JSWhitePadInterface."+b+" is not exist!");break;default:L.Logger.error("[tk-mobile-sdk]clientType not in sdk , will not be able to execute method(window.JSWhitePadInterface."+b+")")}},getInfoBaseInterface:function(b,a,c){if(a&&"function"===typeof a){var e=b+"_"+(new Date).getTime()+"_"+Math.round(1E6*Math.random());TK.temporary.callbackMap[e]=a;a={callbackId:e};if(c&&"object"===typeof c)for(var d in c)a[d]=
c[d];a=L.Utils.toJsonStringify(a);MOBILETKSDK.sendInterface.interface(b,a)}else L.Logger.error("[tk-mobile-sdk]"+b+" callback is not exist or not's function!")},logMessage:function(b,a){if(TK.global.initPageParameterFormPhone){b=L.Utils.toJsonStringify(b);switch(a){case L.Constant.IOS:var c=L.Constant.mClientType.ios;break;case L.Constant.ANDRIOD:c=L.Constant.mClientType.andriod;break;default:c=TK.global.initPageParameterFormPhone.mClientType,a=c===L.Constant.mClientType.andriod?L.Constant.ANDRIOD:
c===L.Constant.mClientType.ios?L.Constant.IOS:"unknown"}L.Logger.debug("[tk-mobile-sdk]logMessage info ,  clientType:"+a+" , mClientType:"+c+" , message:"+b);c===TK.global.initPageParameterFormPhone.mClientType&&TK.global.initPageParameterFormPhone.isSendLogMessageToProtogenesis&&MOBILETKSDK.sendInterface.interface("printLogMessage"+TK.extendSendInterfaceName,L.Utils.toJsonStringify({msg:b}),c)}},dynamicPptVideoAutoPlay:function(b){b=L.Utils.toJsonStringify(b);MOBILETKSDK.sendInterface.interface("onJsPlay"+
TK.extendSendInterfaceName,b)},changeWebPageFullScreen:function(b){MOBILETKSDK.sendInterface.interface("changeWebPageFullScreen"+TK.extendSendInterfaceName,b)},onPageFinished:function(){var b=void 0;window.JSWhitePadInterface||window.JSVideoWhitePadInterface?b=L.Constant.mClientType.andriod:window.webkit&&window.webkit.messageHandlers&&(b=L.Constant.mClientType.ios);MOBILETKSDK.sendInterface.interface("onPageFinished"+TK.extendSendInterfaceName,void 0,b)},pubMsg:function(b,a,c,e,d,f,h,g,k){var l=
{};e&&"object"===typeof e&&(e=L.Utils.toJsonStringify(e));l.signallingName=b;l.id=a;l.toID=c;l.data=e;d||(l.do_not_save=!0);if(void 0!==k){if("number"!==typeof k){L.Logger.error("[tk-mobile-sdk]pubMsg params expires must is number!");return}l.expires=k}if(void 0!==f){if("number"!==typeof f){L.Logger.error("[tk-mobile-sdk]pubMsg params expiresabs must is number!");return}l.expiresabs=f}void 0!==h&&(l.associatedMsgID=h);void 0!==g&&(l.associatedUserID=g);l=L.Utils.toJsonStringify(l);MOBILETKSDK.sendInterface.interface("pubMsg"+
TK.extendSendInterfaceName,l)},delMsg:function(b,a,c,e){e&&"object"===typeof e&&(e=L.Utils.toJsonStringify(e));var d={};d.signallingName=b;d.id=a;d.toID=c;d.data=e;d=L.Utils.toJsonStringify(d);MOBILETKSDK.sendInterface.interface("delMsg"+TK.extendSendInterfaceName,d)},changeUserProperty:function(b,a,c){if(void 0===c||void 0===b)L.Logger.error("[tk-mobile-sdk]changeUserProperty properties or id is not exist!");else{var e={};e.id=b;e.toID=a||"__all";c&&"object"===typeof c?(e.properties=c,e=L.Utils.toJsonStringify(e),
MOBILETKSDK.sendInterface.interface("setProperty"+TK.extendSendInterfaceName,e)):L.Logger.error("[tk-mobile-sdk]properties must be json , user id: "+b+"!")}}};