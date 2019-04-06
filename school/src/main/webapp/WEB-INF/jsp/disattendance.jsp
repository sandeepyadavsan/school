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
							 <a href="attendanceManagement.html" class="btn btn-xs btn-info pull-right">
			                      Back
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
		                                    
		       <form class="form-horizontal" role="form"  id="searchattendanceform" name="searchattendanceform">
		                  
               <div class="form-group">
                       <label class="col-lg-2 control-label">Class</label>
                       <div class="col-lg-3">
                           <select name="ddlClass" id="ddlClass" class="form-control m-b required">
                           <option value="" selected="selected">-Select-</option>
                           <c:forEach items="${classList}" var="classItem">
                            <option value="${classItem.classId}">${classItem.className}</option>
                           </c:forEach>
                           </select>
                       </div>
                       
                       <label class="col-lg-2 control-label">Section</label>
                       <div class="col-lg-3">
                           <select name="ddlSection" id="ddlSection" class="form-control m-b required">
                           
                           </select>
                       </div>
                   </div>
                
                    <div class="form-group">
                       <label class="col-lg-2 control-label">Form No</label>
                       <div class="col-lg-3">
                           <input type="text" placeholder="Form Number" name="form_number" id="form_number" class="form-control required">
                       </div>
                       <label class="col-lg-2 control-label">Student Name</label>
                       <div class="col-lg-3">
                           <input type="text" placeholder="Student Name" name="student_name" id="student_name" class="form-control required">
                       </div>
                   </div>
                   
                    <div class="form-group">
                      <label class="col-lg-2 control-label" for="from_dob">Month</label>
                      <div class="col-lg-3">
                      <input type="text" id="month_date" name="month_date" placeholder="Month" readonly class="form-control input-transparent no-margin "/>
                      </div>
                    </div>
                    
                     <div class="form-group">
                     <div class="col-lg-5">
                     </div>
                     <div class="col-lg-5">
                     <button id="search" class="btn btn-xs btn-success pull-right"> search </button>
                     </div>
                     </div>
                    
               </form>   
		               
		  </div>
	<div class="row tiny-size">
		<table class="table " id="attstudentTable">
         <thead>
			<tr>
				<th>Form No</th>
				<th>Student Name</th>
				<!-- <th>Class</th>
				<th>Section</th> -->
				<th>1</th>
				<th>2</th>
				<th>3</th>
				<th>4</th>
				<th>5</th>
				<th>6</th>
				<th>7</th>
				<th>8</th>
				<th>9</th>
				<th>10</th>
				<th>11</th>
				<th>12</th>
				<th>13</th>
				<th>14</th>
				<th>15</th>
				<th>16</th>
				<th>17</th>
				<th>18</th>
				<th>19</th>
				<th>20</th>
				<th>21</th>
				<th>22</th>
				<th>23</th>
				<th>24</th>
				<th>25</th>
				<th>26</th>
				<th>27</th>
				<th>28</th>
				<th>29</th>
				<th>30</th>
				<th>31</th>
				<th>Total Abs</th>
				<th>Action</th>
				
			</tr>
	     </thead>
         <tbody>
         </tbody>
        </table>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
					</div>
				</div>
			</div>
		   </div>
		
		

</div>
</section>


<!-- Modal Dailog Box Block Starts -->
   <div class="modal fade width45" id="updatePopupModal" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title">Edit Attendance</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row">
           <form class="form-horizontal" role="form">
           <input type="hidden" id="hdfId" name="hdfId">
           
                    <div class="form-group">
                      <label class="col-lg-3 control-label" for="from_dob">Student Name</label>
                      <div class="col-lg-9">
                      <input type="text" id="std_name" name="std_name" placeholder="Name" class="form-control input-transparent no-margin "/>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-lg-3 control-label" for="from_dob">Date</label>
                      <div class="col-lg-9">
                      <input type="text" id="c_date" name="c_date" placeholder="Date" readonly class="form-control input-transparent no-margin "/>
                      </div>
                    </div>
                    
                    <div class="form-group">
                       <label class="col-lg-3 control-label">Status</label>
                       <div class="col-lg-9">
                       <select id="status" class="form-control input-transparent">
                       <option value="A">Absent</option>
                       <option value="P">Present</option>
                       </select>
                       
                       </div>
                   </div>
                   
                   </form>

           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-xs btn-danger" type="button">Cancel</button>
           <button  id="btnUpdate"  class="btn btn-xs btn-primary">Update</button>
         </div>
       </div>
     </div>
   </div>
   <!-- Modal Dailog Box Block Ends -->
   
</body>
</html>