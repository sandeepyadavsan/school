<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title>Wellness360</title>
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="assets/css/simple-line-icons.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="assets/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="assets/css/app.css" id="maincss">
   <style type="text/css">
     .text-muted {
		    text-decoration: none !important;
		}
   </style>
</head>

<body>
   <div class="wrapper">
      <div class="abs-center wd-xl">
         <!-- START panel-->
         <div class="text-center mb-xl">
            <div class="text-md mb-lg">Something went wrong!</div>
         </div>
          <!--
         <div class="input-group mb-xl">
            <h5>Application Error, please contact support.</h5>
				<h6>Debug Information:</h6>
				Requested URL= ${url}<br><br>
				Exception= ${exception.message}<br><br>
				<strong>Exception Stack Trace</strong><br>
				<c:forEach items="${exception.stackTrace}" var="ste">
				   ${ste}
				</c:forEach>
         </div>
         -->
         <ul class="list-inline text-center text-lg mb-xl">
            <li><strong><a href="logout" class="text-muted">Login</a></strong>
            </li>
         </ul>
         <div class="p-lg text-center">
            <span>&copy;</span>
            <span>2015</span>
            <span>-</span>
            <span>Wellness360</span>
         </div>
      </div>
   </div>
   <!-- =============== VENDOR SCRIPTS ===============-->
   <!-- MODERNIZR-->
   <script src="assets/js/modernizr.js"></script>
   <!-- JQUERY-->
   <script src="assets/js/jquery.js"></script>
   <!-- BOOTSTRAP-->
   <script src="assets/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="assets/js/jquery.storageapi.js"></script>
   <!-- PARSLEY-->
   <script src="assets/js/parsley.min.js"></script>
   <!-- =============== APP SCRIPTS ===============-->
   <script src="js/app.js"></script>
</body>

</html>