$(document).ready(function(){
	//load_user();
	//$("#username").focus();
	
	//$('#ForgotPasswordForm').validate_popover({onsubmit: false, popoverPosition: 'right'});
	
	$(document).bind('contextmenu', function (e) {
		  e.preventDefault();
		});
	
	document.onkeypress = function (event) {
		  event = (event || window.event);
		  if (event.keyCode == 123) {
		      return false;
		  }
		};
		document.onmousedown = function (event) {
		  event = (event || window.event);
		  if (event.keyCode == 123) {
		      return false;
		  }
		};
		document.onkeydown = function (event) {
		  event = (event || window.event);
		  if (event.keyCode == 123) {
		      return false;
		  }
		};

	  
	/*$('#loginform').submit(function() {
		
		
		 var c = $("#remember_me"); //INPUT CHECKBOX
		   //IF CHECKBOX IS SET, COOKIE WILL BE SET
		 
		   if(c.is(":checked")){
		     var user = $("#username").val(); //VALUE OF USERNAME
		     var pwd = $("#password").val(); //VALUE OF PASSWORD
		     var encryptedUser = CryptoJS.AES.encrypt(user, "Secret Passphrase");
		     var encryptedPWD = CryptoJS.AES.encrypt(pwd, "Secret Passphrase");
		     
		     $.cookie("username", encryptedUser, { expires: 3650 }); //SETS IN DAYS (10 YEARS)
		     $.cookie("password", encryptedPWD, { expires: 3650 }); //SETS IN DAYS (10 YEARS)
		   }		
	});
	*/
	
//	msieversion();
	
	
});

$(document).on("click","#lnkButtonForgotPassword",function(){
	$("#ForgotPasswordModal").modal("show");
});

$(document).on("click",".popupForgotPasswordClose",function(){
	$("#txtForgotPasswordEmail").val("");
	//$.validator.hide_validate_popover($("#txtForgotPasswordEmail"));
	$("#ForgotPasswordModal").modal("hide");
});

/*$(document).on('click', function (e) {
	var styl=$(e.target).attr("style");
	if (styl=="display: block;") {
		$.validator.hide_validate_popover($("#txtForgotPasswordEmail"));
    }
});*/

function isValidEmailAddress(email) {
    var pattern = new RegExp( /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);
    return pattern.test(email);
};

$(document).on("click","#btnForgotPasswordSend",function(e){
    	var email=$("#txtForgotPasswordEmail").val();
    	if(email.length<0 || email ==0){
    		toastr.error("Please enter your emailId !"); 	
    		return;   		
    	}else if(!isValidEmailAddress(email)){  		
    		toastr.error("Please enter valid registered emailId !"); 	
    		return;
    	}
    	$.ajax({
		     type: 'GET',
		     dataType: "json",
		     cache: false,
		     async:false,
		     url: 'sendpwdemail.json',
		     data:{email:email},
		     success: function(response) {
		     if(response.status==1)  {
		    	 toastr.success(response.message);
		    	$("#ForgotPasswordModal").modal("hide");
		    }else {
		    	if(response.errorCode=="201"){
		    		toastr.error(response.message,"Error"); 
		    	}else{
		    	//	var msg = localData['messages']['LoginMesg']['notRegister'];
			    	//msg = msg.replaceAll('%s',email);
			    	toastr.error("your emailId is not registerd !"); 
		    	}
		      }		    	 
		     },
		     error : function(error) {
		    	  //  var msg = localData['messages']['LoginMesg']['notRegister'];
			    	//msg = msg.replaceAll('%s',email);
			    	toastr.error("your emailId is not registerd !"); 	
		     }
	  });		    	
});

function load_user(){
	   var user = $.cookie("username"); //"USERNAME" COOKIE
	   var pwd = $.cookie("password"); //"PASSWORD" COOKIE
	   
	   var browserInfo = detect.parse(navigator.userAgent);
	   console.log(browserInfo);
		
		var osName=browserInfo.os.name;
		var browser=browserInfo.browser.family;
		var version=browserInfo.browser.version;
		
	    var browserName;
	    if(osName == null || browser == null || version== null){
	    	browserName=browserInfo.source;
	    }else{
	    	browserName="OS:"+osName+";   Browser:"+browser+";   Version:"+version;
	    }
	    $("#browserinfo").val(browserName);
	    
	   if(user!=null)
		   {
		   $("#remember_me").attr('checked',true);
		   $(".icheckbox_square-blue").addClass('checked');
		   var decryptedUser = CryptoJS.AES.decrypt(user, "Secret Passphrase");
		   var decryptedPWD = CryptoJS.AES.decrypt(pwd, "Secret Passphrase");
		   $("#j_username").val(decryptedUser.toString(CryptoJS.enc.Utf8)); //FILLS WITH "USERNAME" COOKIE
		   $("#j_password").val(decryptedPWD.toString(CryptoJS.enc.Utf8)); //FILLS WITH "PASSWORD" COOKIE
		   }
	   
}

function msieversion() { 
	
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE "); 
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
     {	    	
	    	toastr.info(localData['messages']['LoginMesg']['IEnotSupported']);
	    	
	 }
}

String.prototype.replaceAll = function (find, replace) {
	var str = this;
	return str.replace(new RegExp(find, 'g'), replace);
};
