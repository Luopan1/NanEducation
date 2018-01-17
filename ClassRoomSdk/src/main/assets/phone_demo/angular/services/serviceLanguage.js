/*
 * @author Raquel Díaz González
 */

wy_room.service('ServiceLanguage', function () {
	var languageName = "chinese" ;
	var eventName = "languageSwitch" ;
	var languageData = {} ;
	this.SupportedLanguage = ["chinese" , "english" , "complex"] ;
	
	this.setLanguageName = function(name){
		languageName = name ;
	};
	this.getLanguageName = function(){
		return languageName  ;
	};
	this.getLanguageData = function(){
		return languageData  ;
	};
	

	function bindLanguageEvent(callback){
		/*callback  -- 向本地代码发请求获取当前语言种类*/ 
		$(document).on(eventName,callback);
	}
	this.triggerLanguageEvent = function(){
		$(document).trigger(eventName) ;
	}
	this.getSupportedLanguage = function(){
		return this.SupportedLanguage ;
	}
	
	bindLanguageEvent(function(){
		var i18FileLink = "plugs/language/i18_"+languageName+".json?date="+new Date().getTime();
        $.ajax({
        	type:"get",
        	url:i18FileLink,
        	async:false
        }).done(function(data){
        	languageData = data ;
        }).fail(function(err){
        	console.error("get language data failure:",err);
//      	alert("upload language  failure , reason:get language file failure!")
        });
	});
	
});
