<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance</title>
</head>
<body>
<section>
<!-- Page content-->
 <div class="content-wrapper">
 
  <div class="row">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-lg-12">
						<div class="row">
							<div class="col-xs-12">
							 <a href="attendanceDisupdManagement.html" class="btn btn-info pull-right">
		                      Search
		                   </a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		 <div class="row">
		
			 <div class="panel panel-default">
				<div class="panel-body">
					<div>
						<div class="row">
		                    <div>
		                        <div class="ui-130">
		                            <div class="container">
		                                <div class="row">
		                                    
		                                  <form class="form-horizontal" role="form"  id="attendanceform" name="attendanceform">
		                                  
		                                  <div class="form-group">
                       <label class="col-lg-3 control-label">Attendance Taken By</label>
                       <div class="col-lg-5">
                           <input type="text" placeholder="Name.." name="takenby" id="takenby" class="form-control required">
                       </div>
                   </div>
               
               <div class="form-group">
                       <label class="col-lg-3 control-label">Class</label>
                       <div class="col-lg-5">
                           <select name="ddlClass" id="ddlClass" class="form-control m-b required">
                           <option value="" selected="selected">-Select-</option>
                           <c:forEach items="${classList}" var="classItem">
                            <option value="${classItem.classId}">${classItem.className}</option>
                           </c:forEach>
                           
                           </select>
                       </div>
                       
                   </div>
                   
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Section</label>
                       <div class="col-lg-5">
                           <select name="ddlSection" id="ddlSection" class="form-control m-b required">
                              
                           </select>
                       </div>
                   </div>
                   
                   
                   <div class="form-group">
                      <label class="col-lg-3 control-label" for="account_dob">Date</label>
                      <div class="col-lg-5">
                        <input type="text" id="c_date" name="c_date" placeholder="Date" readonly class="form-control input-transparent no-margin required"/>
                        <!--div class="input-group" id="DateDemo">
			               <input type="text" id="c_date" name="c_date" placeholder="Date" readonly class="form-control input-transparent no-margin required"/>
			               <span class="input-group-btn">
			                   <button class="btn btn-green" id="calendarClick" style="font-size: 22px !important;" type="button"><i class="icon-calendar"></i></button>
			               </span>
                          </div-->
                      </div>
                    </div>
               </form>   
		               
		  </div>
	<div class="row">
		<form id='stdRecForm' name='stdRecForm'>
		<table class="table table-bordered table-hover header-top-border" id="attstudentTable">
         <thead>
			<tr>
				<th>S No</th>
				<th>Form No</th>
				<th>Roll No</th>
				<th>Student Name</th>
				<th>Status</th>
				
			</tr>
	     </thead>
         <tbody>
         </tbody>
        </table>
		        </form>                            
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
					</div>
				</div>
			</div>
		   </div>
		
		<div class="row">   
		
		<button type="button" class="btn btn-info pull-right" id="btnSaveAttendance">Save</button>                                    
	         
	     </div>

</div>
</section>





<!-- Confirmation Modal Dailog Box Block Starts -->
   <div class="modal fade width35" id="confirmDeletePopupModal" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title">Confirmation</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row">
           <input type="hidden" id="hdfDelid" name="hdfDelid">
              Are you sure, you want to delete <strong id="delstudent_name"></strong> ?
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-danger" type="button">Cancel</button>
           <button  id="btnDeleteConfirm"  class="btn btn-primary">OK</button>
         </div>
       </div>
     </div>
   </div>
   <!-- Modal Dailog Box Block Ends -->
   
   <!-- View section -->


 <!-- <div class="modal fade width35" id="viewSections" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title" id="headSection">Sections</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row" id="sectionHtml">
           
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal"  class="btn btn-primary">OK</button>
         </div>
       </div>
     </div>
   </div> -->
   
   
</body>
</html>