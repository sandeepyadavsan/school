<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%
    float version = 1.0f;
%>
  <head>
    <title>School</title>
    <link rel="shortcut icon" href="assets/img/favicon.png" />
    
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    
    <link rel="stylesheet" href="assets/css/normalize.css">
    <link href="assets/js/toster/toastr.min.css" rel="stylesheet">
	
   <script type="text/javascript">
       var errmsg="${err_msg}";
       var etype="${etype}";
       var msgtitle = "${msgtitle}";
	</script>
	   
 <style>
 body {
  font-family: "Open Sans", sans-serif;
  height: 100vh;
  background: url("assets/img/bg.jpg") 50% fixed;
  background-size: cover;
}

@keyframes spinner {
  0% {
    transform: rotateZ(0deg);
  }
  100% {
    transform: rotateZ(359deg);
  }
}
* {
  box-sizing: border-box;
}

.wrapper {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  min-height: 100%;
  padding: 20px;
  background: rgba(4, 40, 68, 0.85);
}

.login {
  border-radius: 2px 2px 5px 5px;
  padding: 10px 20px 20px 20px;
  width: 90%;
  max-width: 320px;
  background: #ffffff;
  position: relative;
  padding-bottom: 80px;
  box-shadow: 0px 1px 5px rgba(0, 0, 0, 0.3);
}
.login.loading button {
  max-height: 100%;
  padding-top: 50px;
}
.login.loading button .spinner {
  opacity: 1;
  top: 40%;
}
.login.ok button {
  background-color: #8bc34a;
}
.login.ok button .spinner {
  border-radius: 0;
  border-top-color: transparent;
  border-right-color: transparent;
  height: 20px;
  animation: none;
  transform: rotateZ(-45deg);
}
.login .input {
  display: block;
  padding: 15px 10px;
  margin-bottom: 10px;
  width: 100%;
  border: 1px solid #ddd;
  transition: border-width 0.2s ease;
  border-radius: 2px;
  color: #ccc;
}
.login .input + i.fa {
  color: #fff;
  font-size: 1em;
  position: absolute;
  margin-top: -47px;
  opacity: 0;
  left: 0;
  transition: all 0.1s ease-in;
}
.login .input:focus {
  outline: none;
  color: #444;
  border-color: #2196F3;
  border-left-width: 35px;
}
.login .input:focus + i.fa {
  opacity: 1;
  left: 30px;
  transition: all 0.25s ease-out;
}


.login a {
  font-size: 0.8em;
  color: #2196F3;
  text-decoration: none;
}
.login .title {
  color: #444;
  font-size: 1.2em;
  font-weight: bold;
  margin: 10px 0 30px 0;
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
}
.login button {
  width: 100%;
  height: 100%;
  padding: 10px 10px;
  background: #2196F3;
  color: #fff;
  display: block;
  border: none;
  margin-top: 20px;
  position: absolute;
  left: 0;
  bottom: 0;
  max-height: 60px;
  border: 0px solid rgba(0, 0, 0, 0.1);
  border-radius: 0 0 2px 2px;
  transform: rotateZ(0deg);
  transition: all 0.1s ease-out;
  border-bottom-width: 7px;
}

.login:not(.loading) button:hover {
  box-shadow: 0px 1px 3px #2196F3;
}
.login:not(.loading) button:focus {
  border-bottom-width: 4px;
}

.toast-title
{
font-size: 16px !important;
}
.toast-message
{
font-size: 15px !important;
}

footer {
  display: block;
  padding-top: 50px;
  text-align: center;
  color: #ddd;
  font-weight: normal;
  text-shadow: 0px -1px 0px rgba(0, 0, 0, 0.2);
  font-size: 0.8em;
}
footer a, footer a:link {
  color: #fff;
  text-decoration: none;
}

    </style>
  </head> 
  <body>
  <div class="wrapper">
  <c:url var="loginUrl" value="/login" />
   <form class="login" method="post" role="form" id="loginform" name="loginform"  action="${loginUrl}">
    <p class="title">Log in</p>
    <input id="username" name="username" class="input" type="text" placeholder="Username" onChange="this.value=this.value.toLowerCase();" autocomplete="off" required  autofocus/>
    <i class="fa fa-user"></i>
    <input id="password" name="password" class="input" type="password" placeholder="Password" required>
   <!--  <i class="fa fa-key"></i> -->
    <!-- <select id="session" name="session" class="input"  placeholder="Session" required>
    <option value="">-Select Session-</option>
    <option value="1">2015-2016</option>
    <option value="2">2016-2017</option>
    <option value="3">2017-2018</option>
    </select> -->
    <i class="fa fa-retweet"></i>
    <a href="#">Forgot your password?</a>
    <button>
      <span class="state">Log in</span>
    </button>
  </form>
  <footer><a target="blank" href="javascript:void(0)">© 2016 All rights reserved.</a></footer>
  </div>
  
    <script src="assets/js/jquery.js"></script>
    
   <script src="assets/js/toster/toastr.min.js"></script>
   <script src="assets/js/cookie-plugin.js"></script>
   <script src="assets/js/aes.js"></script>
   <script src="assets/js/detect.js"></script>
    
    <script type="text/javascript">
	$(document).ready(function(){
		toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-top-right",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				};
		if(errmsg.length>0){
			if(etype==2 || etype==4 || etype==5){
				toastr.info(errmsg, msgtitle);
			}else{
				toastr.error(errmsg,msgtitle);
			}
		}
	});
	</script>
	

    <script>

   (function(window, document, $, undefined){

	    var preferredLang = 'en';
	    var pathPrefix    = 'i18n'; // folder of json files
	    var packName      = 'site';
	    var storageKey    = 'jq-appLang';
	    try{
	    if (language != undefined && language != null && language != '')
	        preferredLang = language.toLowerCase();
	    }catch (e) {
			
		}
	    $(function(){

	        if ( ! $.fn.localize ) return;

	        // detect saved language or use default
	        // var currLang = $.localStorage.get(storageKey) || preferredLang;
	        // set initial options
	        var opts = {
	            language: preferredLang,
	            pathPrefix: pathPrefix,
	            callback: function(data, defaultCallback){
	                localData = data;
	                $("[data-tooltip]").relocalize(localData);
	                defaultCallback(data);
	            }
	        };

	        // Set initial language
	        setLanguage(opts);

	        // Listen for changes
	        $('[data-set-lang]').on('click', function(){

	            currLang = $(this).data('setLang');

	            if ( currLang ) {

	                opts.language = currLang;

	                setLanguage(opts);

	                activateDropdown($(this));
	            }

	        });


	        function setLanguage(options) {
	            $("[data-localize]").localize(packName, options);

	        }// Set the current clicked text as the active dropdown text
	        function activateDropdown(elem) {
	            var menu = elem.parents('.dropdown-menu');
	            if ( menu.length ) {
	                var toggle = menu.prev('button, a');
	                toggle.text ( elem.text() );
	            }
	        }
	    });

	})(window, document, window.jQuery);

   </script>
   
   
   <script src="assets/js/jquery.localize.js?ver=<%=version%>"></script>
	<script src="assets/js/page/login.js"></script>
   
  </body>
</html>

<%--
    <!-- ForgotPasswordModal Popup Dialog -->
    <div class="modal fade width30" id="ForgotPasswordModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close popupForgotPasswordClose" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Forgot Password</h4>
                </div>
                <form class="mb-lg" method="post" role="form" id="ForgotPasswordForm" name="ForgotPasswordForm" >
                    <div class="modal-body">
                        <div class="form-group has-feedback">
                            <input id="txtForgotPasswordEmail" name="txtForgotPasswordEmail" type="text" placeholder="Enter e-mail to recover your password!" onChange="this.value=this.value.toLowerCase();" autocomplete="off" required class="form-control">
                            <span class="icon-envelope form-control-feedback text-muted"></span>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-sm btn-danger popupForgotPasswordClose" data-dismiss="modal">Cancel</button>
                        <button type="button" id="btnForgotPasswordSend" class="btn btn-sm btn-primary">Send</button>
                    </div>
                </form>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /End.modal -->
 --%>
