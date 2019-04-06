var flag=true;
var ajaxRequestedBy=null;
var eventText=null;
$(function(){
	/*$.ajaxSetup({
		beforeSend: function(jqXHR, settings) {
			console.log("beforeSend  Start : "+$(ajaxRequestedBy).attr("id"));
			
			var fullURL=settings.url;   
			//console.log("fullURL : "+fullURL);
			var resource =fullURL;
			
			if(fullURL.indexOf("?_") != -1){
			    resource =fullURL.substring(0, fullURL.indexOf("?_"));
			}else if(fullURL.indexOf("&_") != -1){
				resource =fullURL.substring(0, fullURL.indexOf("&_"));
			}
			//console.log("resource : "+resource);
			
			var action="GET";
			if(resource.toLowerCase().indexOf("save") != -1 || resource.toLowerCase().indexOf("update") != -1 || resource.toLowerCase().indexOf("set") != -1){
			    action="SAVE";
			}else if(resource.toLowerCase().indexOf("delete") != -1){
			    action="DELETE";
			}
			
			var browserInfo = detect.parse(navigator.userAgent);
			//console.log(browserInfo);
			
			var osName=browserInfo.os.name;
			var browser=browserInfo.browser.family;
			var version=browserInfo.browser.version;
			
		    var browserName;
		    if(osName == null || browser == null || version== null){
		    	browserName=browserInfo.source;
		    }else{
		    	browserName="OS:"+osName+";   Browser:"+browser+";   Version:"+version;
		    }
			
			var userAnalytics={};
			userAnalytics.resource=resource;
			userAnalytics.action=action;
			userAnalytics.browser=browserName;
		//	console.log(userAnalytics);
			
			if(resource.toLowerCase().indexOf("saveuseranalytics") == -1  &&  resource.toLowerCase().indexOf("getalluseranalytics") == -1){
				saveUserAnalytics(userAnalytics);
			}
			
		},complete: function(request, xhr, settings){
			try{
				var data=JSON.parse(request.responseText);
				console.log("complete END "+data.message);
				//toastr.error('data.message : '+data.message);
				if (data.message==="SessionTimeOut") {
					toastr.error('Your Session is expired please login again.');
					$().redirect("login.html?login_error=4");
				}
			}catch(e){
				console.log("JSON Parsing Error "+e);
			}
			//$body.removeClass("loading");
	    },error: function(jqXHR, exception) {
	    	$('#ajxldrdiv').addClass("hide"); 
     	 console.log("ajaxSetup : "+exception);
         if (jqXHR.status === 0) {
         	//toastr.error('Not connect.\n Verify Network.');
         	console.log('Not connect.\n Verify Network.');
         } else if (jqXHR.status == 404) {
         	//toastr.error('Requested page not found. [404]');
        	 console.log('Requested page not found. [404]');
         }else if (jqXHR.status == 403) {
         	toastr.error('Requested page not found. [403]');
         } else if (jqXHR.status == 500) {
         	//toastr.error('Internal Server Error [500].');
         	console.log('Internal Server Error [500].');
         } else if (exception === 'parsererror') {
         	 //toastr.error('Requested JSON parse failed.');
        	 $().redirect("login.html?login_error=4");
         } else if (exception === 'timeout') {
         	///toastr.error('Time out error.');
        	 console.log('Time out error.');
         } else if (exception === 'abort') {
         	//toastr.error('Ajax request aborted.');
        	 console.log('Ajax request aborted.');
         } else {
         	//toastr.error('Uncaught Error.\n' + jqXHR.responseText);
         	console.log('Uncaught Error.\n' + jqXHR.responseText);
         }
     }
 });*/
 $(document ).ajaxStart(function(e) {
	if(ajaxRequestedBy!=undefined && ajaxRequestedBy!=null){
		var txt=$(ajaxRequestedBy).text();
	    eventText=txt;
		$(ajaxRequestedBy).attr("disabled", true);
		$(ajaxRequestedBy).html('<img src="assets/img/ajloader.gif" />');
	}
 });
 $( document ).ajaxStop(function() {
	 console.log('Uncaught Error.sss\n');
	if(ajaxRequestedBy!=undefined && ajaxRequestedBy!=null){
		$(ajaxRequestedBy).removeAttr("disabled");
		if(eventText +": contains('Update')"){
			eventText = eventText.replace("Update", "Save");
		}
		$(ajaxRequestedBy).html(eventText);	
	}
	ajaxRequestedBy=null;
	eventText=null;
 });
/* setInterval(function() { 
  $.ajax({
	contentType : "application/json",
	url : "updateserverdate.json",
	type : "GET",
	dataType: "json",
	cache: false,
	async:true,
	success: function(response){
	}
  });
 }, 60000);
 setInterval(function() { 
	 getRmbrNotification()
	 }, 300000);*/
 
});



