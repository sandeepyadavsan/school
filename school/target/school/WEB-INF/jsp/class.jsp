<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>

.greenColor{
background-color:#060869 !important;
color : #ffffff !important;
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
								<a class="btn btn-info pull-right" id="addClassEvent">+ Add Class</a>
								<a href="#" id="downloadclasssection">Download Ids</a>
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
       <table class="table table-bordered table-hover header-top-border" id="tblClass">
         <thead>
			<tr>
			    <th>S. No.</th>
				<th>Class Name</th>
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


<div id="addClassPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">Class details</h4>
               <input type="hidden" id="hdfClassId" name="hdfClassId"> 
           </div>
           <div class="modal-body">
               <form class="form-horizontal" role="form"  id="classform" name="classform">
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Class Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Class Name" name="className" id="className" class="form-control required">
                       </div>
                   </div>
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose">Close</button>
               <button type="button" class="btn btn-info" id="btnSaveClass">Save</button>
           </div>
       </div>
   </div>
</div>


<!--  Add Section -->


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
               <form class="form-horizontal" role="form"  id="sectionform" name="sectionform">
               <div class="form-group">
                   <span class="col-lg-12"><a href="javascript:void(0)" id="newsection" class="btn btn-success pull-right">Add Section</a></span>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Class Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Class Name" name="classNameOfSection" id="classNameOfSection" readonly class="form-control required">
                       </div>
                   </div>
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Section Name</label>
                       <div class="col-lg-9" id="reload">
                           <!-- <input type="text" placeholder="Class Name" name="className" id="className" readonly class="form-control required"> -->
                           
                           <select id="sectionMultiSelect" name="sectionMultiSelect" multiple="multiple" class="form-control required">
                           </select>
                           
                       </div>
                   </div>
                    
                   
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose" id="refreshlist">Close</button>
               <button type="button" class="btn btn-info" id="btnMapSection">Save</button>
           </div>
       </div>
   </div>
</div>

<!-- End of section  -->

<div id="addNewSectionPopup" tabindex="-1" role="dialog" aria-labelledby="UserAddUpdate" aria-hidden="true" class="modal fade" style="display: none;">
     <div class="modal-dialog">
       <div class="modal-content">
           <div class="modal-header">
               <button type="button" data-dismiss="modal" aria-label="Close" class="close btnClose">
                   <span aria-hidden="true">&times;</span>
               </button>
               <h4 id="myModalLabel" class="modal-title">Add New Section</h4>
           </div>
           <div class="modal-body">
               <form class="form-horizontal" role="form"  id="newsectionform" name="newsectionform">
                   <div class="form-group">
                       <label class="col-lg-3 control-label">Section Name</label>
                       <div class="col-lg-9">
                           <input type="text" placeholder="Section Name" name="newSectionName" id="newSectionName"  class="form-control required">
                       </div>
                   </div>
               </form>
           </div>
           <div class="modal-footer">
               <button type="button" data-dismiss="modal" class="btn btn-danger btnClose" >Close</button>
               <button type="button" class="btn btn-info" id="saveNewSection">Save</button>
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
           <input type="hidden" id="hdfDelClassId" name="hdfDelClassId">
              Are you sure, you want to delete <strong id="delClassName"></strong> ?
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


 <div class="modal fade width35" id="viewSections" tabindex="-1" role="dialog"  aria-hidden="true">
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
   </div>
   
   
</body>
</html>