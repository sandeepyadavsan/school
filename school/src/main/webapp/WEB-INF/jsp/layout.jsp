<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<%
    float version = 1.0f;
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Bootstrap Admin App + jQuery">
<meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
<link rel="shortcut icon" href="assets/img/favicon.png" />
<title>School</title>

<!-- =============== VENDOR STYLES ===============-->
<!-- FONT AWESOME-->
<link rel="stylesheet" href="assets/css/font-awesome.min.css?ver=<%=version%>">
<!-- SIMPLE LINE ICONS-->
<link rel="stylesheet" href="assets/css/simple-line-icons.css?ver=<%=version%>">
<!-- ANIMATE.CSS-->
<link rel="stylesheet" href="assets/css/animate.min.css?ver=<%=version%>">
<!-- WHIRL (spinners)-->
<link rel="stylesheet" href="assets/css/whirl.css?ver=<%=version%>">
<!-- =============== PAGE VENDOR STYLES ===============-->
<!-- =============== BOOTSTRAP STYLES ===============-->
<link rel="stylesheet" href="assets/css/bootstrap.min.css?ver=<%=version%>" id="bscss">
<!-- =============== APP STYLES ===============-->
 <link rel="stylesheet" href="assets/css/custom.css?ver=<%=version%>" id="bscss">
<link rel="stylesheet" href="assets/css/app.css?ver=<%=version%>" id="maincss">
<link rel="stylesheet" href="assets/css/Styles.css?ver=<%=version%>" id="maincss">
<link rel="stylesheet" href="assets/css/theme-e.css?ver=<%=version%>" id="maincss">


<link rel="stylesheet" href="assets/js/toster/toastr.min.css">
<tiles:importAttribute name="cssitems" />
<c:forEach var="src" items="${cssitems}">
   <link rel="stylesheet" href="${src}?ver=<%=version%>"/>
</c:forEach>
<script type="text/javascript">
var username = "${userses.user.userName}";
</script>
<!-- MODERNIZR-->
<script src="assets/js/modernizr.js?ver=<%=version%>"></script>
<!-- JQUERY-->
<script src="assets/js/jquery.js?ver=<%=version%>"></script>
<!-- BOOTSTRAP-->
<script src="assets/js/bootstrap.js?ver=<%=version%>"></script>
<!-- STORAGE API-->
<script src="assets/js/jquery.storageapi.js?ver=<%=version%>"></script>
<!-- JQUERY EASING-->
<script src="assets/js/jquery.easing.js?ver=<%=version%>"></script>
<!-- ANIMO-->
<script src="assets/js/animo.js?ver=<%=version%>"></script>
<!-- =============== APP SCRIPTS ===============-->
<script src="assets/js/app.js?ver=<%=version%>"></script>
<script src="assets/js/commonMtehod.js?ver=<%=version%>"></script>
<script src="assets/js/toster/toastr.min.js"></script>
<script src="assets/js/jquery.redirect.min.js?ver=<%=version%>"></script>
<script src="assets/view/layout.js?ver=<%=version%>"></script>
<script src="assets/js/detect.js?ver=<%=version%>"></script>
<tiles:importAttribute name="jsitems" />
<c:forEach var="src" items="${jsitems}">
    <script type="text/javascript" src="${src}?ver=<%=version%>"></script>
</c:forEach>

 <style>
     
	 .s_box1{background:#FF0; min-height:300px; height:auto; padding-top:20px;}
	 .s_box2{background:#ff877c; min-height:300px; height:auto; padding-top:20px;}
	 .s_box3{background:#82eed2; min-height:300px; height:auto; padding-top:20px;}
	 
	 
	 .contact-map {
    background-clip: padding-box;
    border: 5px solid #fbfbfb;
    border-radius: 100%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    height: 400px;
    margin: 20px;
    overflow: hidden;
    width: 400px;
}
	 
    
    </style>

 <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>
    

</head>


<body class="aside-collapsed layout-fixed">



<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">School</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
				 <a class="navbar-brand" href="http://54.202.11.57:8080/school/home.html">*School*</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
        
        <li id="homeMenuId">
                    <a href="home.html">
                       Home
                    </a>
                    </li>
                    
                    <c:set var="status" scope="session" value="1"/>
                   
                   <c:if test="${userses.user.type==status}">
                   
                   <li id="campusManagementMenuId">
                    <a href="campusManagement.html">
                       Campus
                    </a>
                   </li>
                    <li id="userMenuId">
                    <a href="userManagement.html">
                       User
                    </a>
                   </li>
                    <li id="senderidManagementMenuId">
                    <a href="senderidManagement.html">
                       Sender Id
                    </a>
                   </li>
                   </c:if>
                    
                    
                    
                 <li id="classManagementMenuId">
                    <a href="classManagement.html">
                       Classes
                    </a>
                   </li>
                    <li id="studentManagementMenuId">
                    <a href="studentManagement.html">
                       Students
                    </a>
                   </li>
                   
                    <li id="attendanceManagementMenuId">
                    <a href="attendanceManagement.html">
                       Attendance
                    </a>
                   </li>
                   
                   <li id="eventManagementMenuId">
                    <a href="eventManagement.html">
                       Event
                    </a>
                   </li>
                   
                    <li id="templateManagementMenuId">
                    <a href="templateManagement.html">
                       Template
                    </a>
                   </li>
                   
                    <li id="messageManagementMenuId">
                    <a href="messageManagement.html">
                       Messages
                    </a>
                   </li>
                   
                   
                   </ul>
				<ul class="pull-right" style="list-style: none !important; margin-top:15px;">
          <li>
          <a href="logout" style="color:#fff;">
		  
		  <span style="font-size:20px;" class="glyphicon glyphicon-off white"></span>
          </a>
          </li>
        </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>


<tiles:insertAttribute name="body" />

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Cloud School 2016</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

</body>
</html>
