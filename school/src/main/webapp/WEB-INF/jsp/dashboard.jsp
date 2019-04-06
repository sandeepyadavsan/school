<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="assets/css/full-slider.css" id="maincss">
<link rel="stylesheet" href="assets/css/animate.css" id="maincss">

<header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for Slides -->
        <div class="carousel-inner">
            <div class="item active">
                <!-- Set the first background image using inline CSS below. -->
                <div class="fill" style="background-image:url(assets/image/p1.jpg)"></div>   
                <div class="carousel-caption">
                    <h2>Attention in Class</h2>
                </div>
            </div>
            <div class="item">
                <!-- Set the second background image using inline CSS below. -->
                <div class="fill" style="background-image:url(assets/image/p2.jpg)"></div>   
                <div class="carousel-caption">
                    <h2>Enjoying</h2>
                </div>
            </div>
            <div class="item">
                <!-- Set the third background image using inline CSS below. -->
                <div class="fill" style="background-image:url(assets/image/p3.jpg)"></div>  
                <div class="carousel-caption">
                    <h2>Activities for kids</h2>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>

    </header>
<!-- <div class="container-fluid">  -->
<!-- Main section-->
<div class="row s_box1">

    <div class="col-lg-6">
    <img src="assets/image/student1.png" alt="student1" class="img-responsive" />
      
    </div> 
    <div class="col-lg-6" style="margin-top:40px;">
 <div class="jumbotron animated fadeInRight" style="background:#FF0;"><h1><b>About Study</b></h1>
<h3><b>We believe that everyone has the right to an affordable, quality education.</b></h3>

<h4><i>As a society, we uphold the notion that it's a good thing – the very basis of hope and the key to the promise of upward mobility. But brutally high fees and an educational system slow to realize that everyone learns differently have created a paradox. Learning should be fun and accessible to everyone, but in many cases, it simply isn't. At Study.com, we aim to deliver a better way to learn – one that lets you learn what you want, the way you want, and in a way you can afford. We want to empower you to become that better version of yourself education is supposed to allow. </i></h4></div>
      
    </div>  

</div> 



<div class="row s_box2">

     
 <div class="col-lg-6" style="margin-top:40px;">
 <div class="jumbotron animated fadeInLeft" style="background:#ff877c;"><h1><b>Success</b></h1>
<h3><b>The achievement of something desired, planned, or attempted...</b></h3>
<h4><i>To achieve success despite predictable loss; to extricate one-self from a potentially dangerous situation; to escape failure narrowly. This popular expression usually appears in a context implying that the one who “lands on his feet” does so through undeserved luck; he repeatedly gets himself into scrapes but somehow survives. It is apparently based on the notion that one plummeting downward is unlikely to land safely, let alone feet first. </i></h4></div>
    </div> 
    
    <div class="col-lg-6">
    <img src="assets/image/student2.png" alt="student1" class="img-responsive" />
    </div> 

</div>

<div class="row s_box3">

    <div class="col-md-6">
		<div class="contact-map animated flipInY">
		<div id="googleMap"  style="width:500px;height:380px;"></div>
		</div>
    </div> 
    <div class="col-lg-6" style="margin-top:40px;">
 <div class="jumbotron" style="background:#82eed2"><h1><b>Success</b></h1>
<h3><b>The achievement of something desired, planned, or attempted...</b></h3>
<h4><i>To achieve success despite predictable loss; to extricate one-self from a potentially dangerous situation; to escape failure narrowly. This popular expression usually appears in a context implying that the one who “lands on his feet” does so through undeserved luck; he repeatedly gets himself into scrapes but somehow survives. It is apparently based on the notion that one plummeting downward is unlikely to land safely, let alone feet first. </i></h4></div>
    
	
	
	
	</div> 
    
    
</div>

 <!-- <script>
function initialize() {
  var mapProp = {
    center:new google.maps.LatLng(51.508742,-0.120850),
    zoom:5,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
}
google.maps.event.addDomListener(window, 'load', initialize);
</script> -->
