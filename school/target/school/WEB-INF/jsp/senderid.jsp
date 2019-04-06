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
<title>SenderID</title>
<style>

.js .inputfile {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
}

.inputfile + label {
    max-width: 80%;
    font-size: 1.25rem;
    /* 20px */
    font-weight: 700;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
    display: inline-block;
    overflow: hidden;
    padding: 0.625rem 1.25rem;
    /* 10px 20px */
}

.no-js .inputfile + label {
    display: none;
}

.inputfile:focus + label,
.inputfile.has-focus + label {
    outline: 1px dotted #000;
    outline: -webkit-focus-ring-color auto 5px;
}

.inputfile + label * {
    /* pointer-events: none; */
    /* in case of FastClick lib use */
}



/* style 1 */

.inputfile-1 + label {
    color: #f1e5e6;
    background-color: #d3394c;
}

.inputfile-1:focus + label,
.inputfile-1.has-focus + label,
.inputfile-1 + label:hover {
    background-color: #722040;
}


</style>

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
							    <!-- <a href="assets/files/StudentRegistration.csv" target="_blank">Download Sample</a> -->
							   <!--  <form>
							    
								<input type="file" name="uploadStudentRecord" id="uploadStudentRecord" class="inputfile inputfile-1" accept="" />
								<label for="uploadStudentRecord"><i class="fa fa-upload"></i> <span> Choose a file&hellip;</span></label>
							    
							    </form>  -->
								<a href="javascript:void(0)" class="btn btn-info pull-right" id="addSenderidEvent">+ Add SenderID</a>
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
      <table class="table table-bordered table-hover header-top-border" id="senderidTable">
         <thead>
			<tr>
				  <th>S. No.</th>
				<th>Campus Name</th>
				<th>Sender ID</th>
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


<div id="addSenderidPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">SenderID Details</h4>
               <input type="hidden" id="hdfSenderId" name="hdfSenderId"> 
               <input type="hidden" id="hdfCampusId" name="hdfCampusId">  
           </div>
           <div class="modal-body">
               <form class="form-horizontal" role="form"  id="senderidform" name="senderidform">
               
               
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
                       <label class="col-lg-3 control-label">Sender ID</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Sender ID" name="sender_id" id="sender_id" class="form-control required">
                       </div>
                   </div>
                   
                   
                    
                    <div class="form-group">
                       <label class="col-lg-3 control-label">Status</label>
                       <div class="col-lg-9">
                           <select name="ddlstatus" id="ddlstatus" class="form-control m-b required">
                               <option value="Active" >Active</option>
                               <option value="Inactive" selected="selected">Inactive</option>
                           </select>
                       </div>
                   </div>
                   
                   
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose">Close</button>
               <button type="button" class="btn btn-info" id="btnSaveSenderId">Save</button>
           </div>
       </div>
   </div>
</div>


<%-- <!--  Add Section -->


<div id="addSectionPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">Add Section</h4>
               <input type="hidden" id="hdfClassIdSection" name="hdfClassIdSection"> 
           </div>
           <div class="modal-body">
               <form class="form-horizontal" role="form"  id="classform" name="classform">
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Class Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Class Name" name="classNameOfSection" id="classNameOfSection" readonly class="form-control required">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Section Name</label>
                       <div class="col-lg-9">
                           <!-- <input type="text" placeholder="Class Name" name="className" id="className" readonly class="form-control required"> -->
                           
                           <select id="sectionMultiSelect" name="sectionMultiSelect" multiple="multiple" class="form-control required">
                           </select>
                           
                       </div>
                   </div>
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnCloseSection">Close</button>
               <button type="button" class="btn btn-info" id="btnMapSection">Save</button>
           </div>
       </div>
   </div>
</div>

<!-- End of section  --> --%>


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
              Are you sure, you want to delete <strong id="delsenderid"></strong> ?
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