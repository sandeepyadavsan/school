<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<!--


<section>

 <div class="content-wrapper">
	      <div class="row">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-lg-12">
						<div class="row">
							<div class="col-xs-12">
							<h3>
								Welcome, ${userses.user.userName}
								<a class="btn btn-info pull-right" id="addUserEvent">+ Add User</a>
							</h3>
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
		                                    <div class="table-responsive">
       <table class="table table-bordered table-hover header-top-border" id="tblUser">
         <thead>
			<tr>
				<th>LoginName</th>
				<th>CampusId</th>
				<th>Status</th>
				<th>Type</th>
				<th>Action</th>
			</tr>
	     </thead>
         <tbody>
         </tbody>
        </table>
	  </div>
		                                </div>
		                                <div class="row">
		                                    
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
	         
	     </div>

</div>
</section>
<div id="addUserPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">User Information</h4>
               <input type="hidden" id="hdfUserId" name="hdfUserId">
           </div>
           <div class="modal-body">
               <form class="form-horizontal" role="form"  id="userform" name="userform">
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Login Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Login Name" name="loginName" id="loginName" class="form-control required">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Campus Id</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Campus Id" name="campusId" id="campusId" class="form-control required number">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Emp Code</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Employee Code" name="empCode" id="empCode" class="form-control required number">
                       </div>
                   </div>
                   <div class="form-group" id="pwddiv">
                       <label class="col-lg-3 control-label">Password</label>
                       <div class="col-lg-9">
                           <input type="password" placeholder="Password" name="password" id="password" class="form-control required" minlength="4">
                       </div>
                   </div>
                   <div class="form-group" id="cpwddiv">
                       <label class="col-lg-3 control-label">Confirm</label>
                       <div class="col-lg-9">
                           <input type="password" placeholder="Confirm Password" name="cpassword" id="cpassword" class="form-control required" data-rule-equalTo="#password">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">User Type</label>
                       <div class="col-lg-9">
                           <select name="ddlUserType" id="ddlUserType" class="form-control m-b required">
                               <option value="true">Admin</option>
                               <option value="false">User</option>
                           </select>
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Status</label>
                       <div class="col-lg-9">
                           <select name="ddlStatus" id="ddlStatus" class="form-control m-b required">
                               <option value="A">Active</option>
                               <option value="I">Inactive</option>
                           </select>
                       </div>
                   </div>
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose">Close</button>
               <button type="button" class="btn btn-info" id="btnSaveUser">Save</button>
           </div>
       </div>
   </div>
</div>



   <div class="modal fade width35" id="confirmDeletePopupModal" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title">Confirmation</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row">
           <input type="hidden" id="hdfLoginId" name="hdfLoginId">
              Are you sure, you want to delete <strong id="delUserName"></strong> ?
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-danger" type="button">Cancel</button>
           <button  id="btnDeleteConfirmEmployee"  class="btn btn-primary">OK</button>
         </div>
       </div>
     </div>
   </div>
-->



<!-- Main section-->
<section>
<!-- Page content-->
 <div class="content-wrapper">
	      <div class="row">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="col-lg-12">
						<div class="row">
							<div class="col-xs-12">
							<h3>
								
								<a class="btn btn-info pull-right ml10" id="addUserEvent">+ Add User</a>
								 <a href="download.html" class="btn btn-info pull-right">Download</a>
							</h3>
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
		                                    <div class="table-responsive">
       <table class="table table-bordered table-hover header-top-border" id="tblUser">
         <thead>
			<tr>
			<th>SL No</th>
				<th>Login Name</th>
				<th>Campus Name</th>
				<th>Status</th>
				<th>Action</th>
			</tr>
	     </thead>
         <tbody>
         </tbody>
        </table>
	  </div>
		                                </div>
		                                <div class="row">
		                                    
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
	         
	     </div>

</div>
</section>


<div id="resetPwdModal" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClosePwdModal">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">Password Reset</h4>
               
           </div>
           <div class="modal-body">
               
               <form name="pwd-form" id="pwd-form" class="form-horizontal"  onsubmit="return false">
               <input type="hidden" id="pwdUserId" name="pwdUserId">
               <input type="hidden" id="campusIdHidden" name="campusIdHidden">
               
                <div class="form-group">
                      <label class="col-sm-3 control-label" for="User Name">Password</label>
                      <div class="col-sm-6">
                        <input type="password" placeholder="Password" id="password" 
                        minlength="4" name="password" class="form-control required">
                      </div>
                    </div>
                    
                     <div class="form-group">
                      <label class="col-sm-3 control-label" for="User Name">Confirm Password</label>
                      <div class="col-sm-6">
                        <input type="password" placeholder="Confirm Password" id="c_password" 
                        minlength="4" name="c_password" class="form-control required" data-rule-equalTo="#password">
                      </div>
                    </div>
                    
                  
                    
                  </form>
               
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClosePwdModal">Close</button>
               <button type="button" class="btn btn-info" id="btnResetPwd">Reset</button>
           </div>
       </div>
   </div>
</div>

<div id="addUserPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">User Information</h4>
               <input type="hidden" id="hdfUserId" name="hdfUserId">
           </div>
           <div class="modal-body">
               <%-- <form class="form-horizontal" role="form"  id="userform" name="userform">
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Login Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Login Name" name="loginName" id="loginName" class="form-control required">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Campus Id</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Campus Id" name="campusId" id="campusId" class="form-control required number">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Emp Code</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Employee Code" name="empCode" id="empCode" class="form-control required number">
                       </div>
                   </div>
                   <div class="form-group" id="pwddiv">
                       <label class="col-lg-3 control-label">Password</label>
                       <div class="col-lg-9">
                           <input type="password" placeholder="Password" name="password" id="password" class="form-control required" minlength="4">
                       </div>
                   </div>
                   <div class="form-group" id="cpwddiv">
                       <label class="col-lg-3 control-label">Confirm</label>
                       <div class="col-lg-9">
                           <input type="password" placeholder="Confirm Password" name="cpassword" id="cpassword" class="form-control required" data-rule-equalTo="#password">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">User Type</label>
                       <div class="col-lg-9">
                           <select name="ddlUserType" id="ddlUserType" class="form-control m-b required">
                               <option value="true">Admin</option>
                               <option value="false">User</option>
                           </select>
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Status</label>
                       <div class="col-lg-9">
                           <select name="ddlStatus" id="ddlStatus" class="form-control m-b required">
                               <option value="A">Active</option>
                               <option value="I">Inactive</option>
                           </select>
                       </div>
                   </div>
               </form> --%>
               
               <form name="userform" id="userform" class="form-horizontal"  onsubmit="return false">
                <div class="form-group">
                       <label class="col-lg-3 control-label">Campus</label>
                       <div class="col-lg-9">
                           <select name="ddlCampus" id="ddlCampus" class="form-control m-b required">
                           <option value="" selected="selected">-Select-</option>
                           <c:forEach items="${campusList}" var="campusItem">
                            <option value="${campusItem.campus_id}">${campusItem.campus_name}</option>
                           </c:forEach>
                           
                           </select>
                       </div>
                   </div>
               
                    <div class="form-group">
                      <label class="col-sm-3 control-label" for="User Name">User Name</label>
                      <div class="col-sm-9">
                        <input type="text" placeholder="User Name" id="account_user_name" 
                        minlength="4" name="account_user_name" class="form-control required">
                      </div>
                    </div>
                     <div class="form-group">
                      <label class="col-sm-3 control-label" for="Password Name">Password</label>
                      <div class="col-sm-9">
                        <input type="password" placeholder="Password" id="upassword" minlength="4" name="upassword" class="form-control required">
                      </div>
                    </div>
                    <div class="form-group">
                       <label class="col-lg-3 control-label">User Type</label>
                       <div class="col-lg-9">
                           <select name="ddlUserType" id="ddlUserType" class="form-control m-b required">
                               <option value="0">User</option>
                               <option value="1">Admin</option>
                               
                           </select>
                       </div>
                   </div>
                    <div class="form-group">
                       <label class="col-lg-3 control-label">Status</label>
                       <div class="col-lg-9">
                           <select name="ddlStatus" id="ddlStatus" class="form-control m-b required">
                               <option value="Active">Active</option>
                               <option value="Inactive">Inactive</option>
                           </select>
                       </div>
                   </div>
                    
                   <!--  <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_gender">Gender</label>
                      <div class="col-sm-6">
                        <select class="form-control input-sm required" name="account_gender" id="account_gender" placeholder="Gender">
                          <option value="Male">Male</option>
                          <option value="Female">Female</option>
                        </select>
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_gender">Choice</label>
                      <div class="col-sm-6">
                           <label class="radio-inline c-radio">
                           <input type="radio" name="gender" value="Male" checked>
						   <span class="fa fa-circle"></span>Male
						   </label>
							
						   <label class="radio-inline c-radio"> 
						   <input type="radio" name="gender" value="Female"> 
						   <span class="fa fa-circle"></span>Female
						   </label>
                      </div>
                    </div> -->
                    
                   <!--  <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_dob">Birthday</label>
                      <div class="col-sm-6">
                        <input type="text"  class="form-control input-transparent no-margin required" readonly  name="account_dob" placeholder="Date of Birth" id="account_dob">
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_email">E-mail</label>
                      <div class="col-sm-6">
                        <input type="text" placeholder="E-mail" id="account_email" name="account_email"  class="form-control required email">
                      </div>
                    </div> -->
                    
                    <!-- <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_phone_number">Phone</label>
                      <div class="col-sm-6">
                      <input type="text" placeholder="Phone Number" id="account_primary_ph_no" name="account_primary_ph_no"  class="form-control number">
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="col-sm-3 control-label" for="account_address">Address</label>
                      <div class="col-sm-6">
                      <textarea placeholder="Address" name="account_address" id="account_address" class="form-control" style="overflow:auto;resize:none" rows="4"></textarea>
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label class="col-sm-3 control-label" for="city">photo</label>
                      <div class="col-sm-6">
                      <input type="file" id="uploadPic" class="form-control">
                      </div>
                    </div> -->
                    
                  </form>
               
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose">Close</button>
               <button type="button" class="btn btn-info" id="btnSaveUser">Save</button>
           </div>
       </div>
   </div>
</div>


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
           <input type="hidden" id="hdfLoginId" name="hdfLoginId">
              Are you sure, you want to delete <strong id="delUserName"></strong> ?
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-danger" type="button">Cancel</button>
           <button  id="btnDeleteConfirmEmployee"  class="btn btn-primary">OK</button>
         </div>
       </div>
     </div>
   </div>
   <!-- Modal Dailog Box Block Ends -->
		