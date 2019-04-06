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
<title>Message</title>

<style>
                #msgtoselector{         
                    overflow: auto;
                    padding: 20px 10px 10px 15px;
                    margin-bottom: 10px;
                }
                #msgtoselector .eachchild{
                    font-size: 10px;
                    width: 140px;
                    float:left;
                    background: #f7f7f7;
                    margin: 5px;
                    border:1px solid #ccc;
                }
                #msgtoselector .eachchild label input[type=checkbox]{
                    position:absolute;
                    margin-top: -10px;
                    margin-left: -10px;
                }
                #msgtoselector .eachchild label{
                    cursor:pointer;
                    padding: 5px;
                    display: block;
                    box-shadow: 0px 1px 12px #999;
                    margin-bottom : 0px !important;
                }
                #msgtoselector .eachchild label:hover{
                    box-shadow: 0px 0px 1px #999;
                }
                #msgtoselector select{
                    border: 1px solid #ccc;
                    cursor: pointer;
                }
                #msgtoselector option{
                    background: #f7f7f7;
                    cursor: pointer;
                }
                #msgtoselector .selected{
                    background: #1676BC;
                    color: #fff;
                    font-weight: bold;
                }
                #msgtoselector .loading{
                    background: url('../images/38.gif') center center no-repeat;
                    min-height: 200px;
                }
                #msgtoselector #mgs{
                    float: left;
                    padding-left: 150px;
                    clear:both;
                    background: #fff;
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
							 
							 
							 <div class="form-group">
                       <label class="col-lg-3 control-label">SMS Send To</label>
                       <div class="col-lg-5">
                           <select name="ddlsmsSendTo" id="ddlsmsSendTo" class="form-control m-b required">
                           <option value="" selected="selected">-Select-</option>
                           <option value="AllClass" >All Student</option>
                           <option value="MultiClass" >Multiple Classes</option>
                           <optgroup label="Classes">
                           <c:forEach items="${classList}" var="classItem">
                            <option value="${classItem.classid}#${classItem.sectionid}">${classItem.className} > ${classItem.sectionname} </option>
                           </c:forEach>
                           </optgroup>
                           </select>
                       </div>
                   </div>
							 
							 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		 <div class="row hide" id="msgtoselector">
		
			<div class="panel panel-default">
			<div class="panel-body">
				
				
			
			<div class="col-lg-12">
			
			<div id="selectDeselectDiv" class="hide pull-right"><a class="btn btn-primary" href="javascript:void(0)" id="selectAll">Select All</a><a class="btn btn-primary ml10" href="javascript:void(0)"  id="deselectAll">Unselect All</a></div>
          
            </div>
            <div class="col-lg-12 mt20">
          <div id="mainDiv">
          </div>
          </div>
              
              
           
              
             
              
              </div>
				
					
		         </div>
			    </div>
				
				
				
			<div class="row hide" id="templateBox">
			 <div class="panel panel-default">
				<div class="panel-body">
			
			
			<div class="col-lg-12">
                      <div class="col-md-6">
                           <label class="pull-left ml10">Message Type</label>
                           
                           <label class="radio-inline c-radio pull-right templateMesg">
                           <input type="radio" name="message_type" id="Template" value="Template" checked>
						   <span class="fa fa-circle"></span>Template Message
						   </label>
						   </div>
						   <div class="col-md-6">
						   <label class="radio-inline c-radio pull-left genMesg"> 
						   <input type="radio" name="message_type" id="General" value="General"> 
						   <span class="fa fa-circle"></span>General Message
						   </label>
                      </div>
                    </div>	
            
           </div>
           <div class="panel-body" id="templateMesgBox">
           
           <div class="col-lg-12">
           
           <form id="messageForm" class="form-horizontal">
                     
                     <div class="row mb10">
				       <div class="col-xs-12">
                       <label class="col-lg-3">Event</label>
                           
                       <div class="col-lg-5">
                       <select name="ddlMesgTemplate" id="ddlMesgTemplate" class="form-control m-b required">
                           <option value="" selected="selected">Select Event</option>
                           <c:forEach items="${eventList}" var="eventListItem">
                            <option value="${eventListItem.event_id}">${eventListItem.event_name} </option>
                           </c:forEach> 
                           </select>
                       </div>
                   </div>
                    </div>
                    
                    <div class="row mb10">
				       <div class="col-xs-12">
                       <label class="col-lg-3">Template</label>
                           
                       <div class="col-lg-5">
                       <select name="ddlMesgTemplateevent" id="ddlMesgTemplateevent" class="form-control m-b required">
                          <%--  <option value="" selected="selected">Select Template</option>
                           <c:forEach items="${eventList}" var="eventListItem">
                            <option value="${eventListItem.event_id}">${eventListItem.event_name} </option>
                           </c:forEach>  --%>
                           </select>
                       </div>
                   </div>
                    </div>
                    
                    
                    <div class="row mb10">
				       
				       
				        <div class="col-xs-12">
                       <label class="col-lg-3">Sender ID</label>
                           
                       <div class="col-lg-5">
                       <select name="ddlsenderid" id="ddlsenderid" class="form-control m-b required">
                           <option value="" selected="selected">- Sender ID -</option>
                           <c:forEach items="${senderidList}" var="senderidListItem">
                            <option value="${senderidListItem.sender_id}">${senderidListItem.sender_id} </option>
                           </c:forEach> 
                           </select>
                       </div>
                       
                   </div>
				       
				       
                    </div>
                    
                  <div class="row mb10">
                  
                  
                  <div class="col-xs-12">
                       <label class="col-lg-3">Schedule Date</label>
                           
                       <div class="col-lg-5">
                       <input type="text"  class="form-control input-transparent no-margin required" readonly  name="schedule" placeholder="Schedule Date" id="schedule">
                      
                       </div>
                       
                   </div>
                  
                    </div>
                  
                  
                  
                   <div class="row mb10">
                   
                   
                   <div class="col-xs-12" id="textBoxDiv">
                       <!-- <label class="col-lg-3">Message Text</label>
                           
                       <div class="col-lg-5">
                       <textarea  id="mesgText" class="form-control required" ></textarea>
                       </div> -->
                       
                       <div class="col-lg-6">
                   
                   <button type="button" class="btn btn-info pull-right mt10" id="btnSaveSMS">Send Message</button>  
                   
                   </div>
                       
                   </div>
                   </div>
                   
                    </form>
                   </div>
                   
            
                  
           
          
           
				
				</div>
				
				
				<div class="panel-body hide" id="messageBox">
           
           <div class="col-lg-12">
           
           <form id="simplemessageForm" class="form-horizontal">
                    
                    
                    <div class="row mb10">
				       
				        <div class="col-xs-12">
                       <label class="col-lg-3">Sender ID</label>
                           
                       <div class="col-lg-5">
                       <select name="simpleddlsenderid" id="simpleddlsenderid" class="form-control m-b required">
                           <option value="" selected="selected">- Sender ID -</option>
                           <c:forEach items="${senderidList}" var="simplesenderidListItem">
                            <option value="${simplesenderidListItem.sender_id}">${simplesenderidListItem.sender_id} </option>
                           </c:forEach> 
                           </select>
                       </div>
                       
                   </div>
				       
				       
                    </div>
                    
                  <div class="row mb10">
                  
                  
                  <div class="col-xs-12">
                       <label class="col-lg-3">Schedule Date</label>
                           
                       <div class="col-lg-5">
                       <input type="text"  class="form-control input-transparent no-margin required" readonly  name="simpleschedule" placeholder="Schedule Date" id="simpleschedule">
                      
                       </div>
                       
                   </div>
                  
                    </div>
                  
                  
                  
                   <div class="row mb10">
                   
                   
                   <div class="col-xs-12" id="textBoxDiv">
                       <label class="col-lg-3">Message Text</label>
                           
                       <div class="col-lg-5">
                       <textarea  id="simplemesgText" class="form-control required" ></textarea>
                       </div>
                       
                       <div class="col-lg-6">
                   
                   <button type="button" class="btn btn-info pull-right mt10" id="simplebtnSaveSMS">Send Message</button>  
                   
                   </div>
                       
                   </div>
                   </div>
                   
                    </form>
                   </div>
          
           
				
				</div>
				
				
				</div>
				</div>
				
			
		
	     </div>
	     
</section>





<!-- Confirmation Modal Dailog Box Block Starts -->
   <div class="modal fade width35" id="confirmsendModal" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title">Confirmation</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row">
           <input type="hidden" id="hdfDelid" name="hdfDelid">
              Are you sure, you want to send sms ?
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-danger closeBtn" type="button">Cancel</button>
           <button  id="btnSendConfirm"  class="btn btn-primary">Ok</button>
         </div>
       </div>
     </div>
   </div>
   <!-- Modal Dailog Box Block Ends -->
   
   
   <!-- Confirmation Modal Dailog Box Block Starts -->
   <div class="modal fade width35" id="simpleconfirmsendModal" tabindex="-1" role="dialog"  aria-hidden="true">
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
           <button type="button" class="close closeBtn" data-dismiss="modal" aria-hidden="true">&times;</button>
           <h4 class="modal-title">Confirmation</h4>
         </div>
         <div class="modal-body text-center">
           <div class="row">
           <input type="hidden" id="hdfDelid" name="hdfDelid">
              Are you sure, you want to send sms ?
           </div>
         </div>
         <div class="modal-footer">
           <button data-dismiss="modal" class="btn btn-danger closeBtn" type="button">Cancel</button>
           <button  id="simplebtnSendConfirm"  class="btn btn-primary">Ok</button>
         </div>
       </div>
     </div>
   </div>
   <!-- Modal Dailog Box Block Ends -->
   
   <!-- View section -->
   
   
</body>
</html>