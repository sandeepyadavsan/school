$(function(){
	
	$("#messageManagementMenuId").addClass("active");
	
	$('#schedule').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
	
	
	$("#schedule").val(moment().format('DD-MMM-YYYY'));
	
	$('#simpleschedule').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
	
	
	$("#simpleschedule").val(moment().format('DD-MMM-YYYY'));
	
	$("#ddlMesgTemplateevent").prop('disabled', true);
	
});

function changeallselector(){
    $('#msgtoselector input[type=checkbox]').each(function(){
        changeselected(this);
        
    });
}


$(document).on("click", '.templateMesg', function(e) {
	
	$("#templateMesgBox").removeClass("hide");
	$("#messageBox").addClass("hide");
});

$(document).on("click", '.genMesg', function(e) {
	$("#messageBox").removeClass("hide");
	$("#templateMesgBox").addClass("hide");
});

$(document).on("click", '#deselectAll', function(e) {
	toggleselection(false);
});


$(document).on("click", '#selectAll', function(e) {
	toggleselection(true);
});

function toggleselection($selectall){
    var id = $('#msgtoselector input[type=checkbox]');
    $(id).each(function(){
        if($selectall){
            $(this).attr('checked', true);
            changeselected(this);
        }else{
            $(this).attr('checked', false);
            changeselected(this);
        }
    });
}

function changeselected(id){
    var temp = $(id).closest('label');
    if($(id).attr('checked') == 'checked'){
    	{
    	$(id).prop('checked', 'checked');
        $(temp).addClass('selected');
    	}
    }else{
    	$(id).attr('checked', false);
        $(temp).removeClass('selected');
    }
}

$(document).on("click",".eachchild",function(e){

var id = $(this).children().children();

	
	$(id).click();
	
});

$(document).on("change",".changeCheckbox",function(e){
	
	
	if($(this).attr('checked')=="checked")
	{
		 $(this).attr('checked', false);
         changeselected(this);
	}
	else
		{
		
		 $(this).attr('checked', true);
         changeselected(this);
         
		}
});

var msgData = {};

$(document).on("click","#btnSaveSMS",function(e){
	
	if($('#messageForm').validate().form()){
		
		
	 var id = $('#msgtoselector input[type=checkbox]');
	 var classSectionIds = [];
	 var indx = 0;
	    $(id).each(function(){
	            indx ++;
	    	if($(this).attr('checked')=="checked")
	    		{
	    		classSectionIds.push($(this).attr("ids"));
	    		}
	    });
	    
	    if($("#ddlsmsSendTo").val()!="AllClass")
	    if(classSectionIds.length==0)
	    	{
	    	toastr.error("Please Select atleast one participants.");
	    	return;
	    	
	    	}
	    
	    var type = $("#ddlsmsSendTo").val();
	    
	    if(type!="AllClass"&&type!="MultiClass")
	    {
	    	if(indx==classSectionIds.length)
	    		type = "AllStudent";
	    	else
	    	   type = "MultiStudent";
	    	
	    	 msgData.classSectionIds = $("#ddlsmsSendTo").val();
	    }
	    	
	    
	   
	    
	    if(type=="MultiClass")
	    msgData.classSectionIds = classSectionIds.toString();
	    
	    if(type == "MultiStudent")
	    msgData.studentids = classSectionIds.toString();
	    
	    msgData.type = type;
	    msgData.eventid=$("#ddlMesgTemplate").val();
	    msgData.templateid=$("#ddlMesgTemplateevent").val();
	    
	   // msgData.message = $("#mesgText").val();
	    msgData.configstatus="Active";
	    msgData.configdate = moment().format("DD-MMM-YYYY");
	    
	    
	    msgData.senderid = $("#ddlsenderid").val();
	    msgData.schedule = moment($("#schedule").val(),'DD-MMM-YYYY').format("YYYY-MM-DD hh:mm:ss");
	    
	    
	    
	   $('#confirmsendModal').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#confirmsendModal").modal("show"); 
	    
	    
	    
	    
	}

});

var simplemsgData = {};

$(document).on("click","#simplebtnSaveSMS",function(e){
	
	if($('#simplemessageForm').validate().form()){
		
		
	 var id = $('#msgtoselector input[type=checkbox]');
	 var classSectionIds = [];
	 var indx = 0;
	    $(id).each(function(){
	            indx ++;
	    	if($(this).attr('checked')=="checked")
	    		{
	    		classSectionIds.push($(this).attr("ids"));
	    		}
	    });
	    
	    if($("#ddlsmsSendTo").val()!="AllClass")
	    if(classSectionIds.length==0)
	    	{
	    	toastr.error("Please Select atleast one participants.");
	    	return;
	    	
	    	}
	    
	    var type = $("#ddlsmsSendTo").val();
	    
	    if(type!="AllClass"&&type!="MultiClass")
	    {
	    	if(indx==classSectionIds.length)
	    		type = "AllStudent";
	    	else
	    	   type = "MultiStudent";
	    	
	    	simplemsgData.classSectionIds = $("#ddlsmsSendTo").val();
	    }
	    	
	    
	   
	    
	    if(type=="MultiClass"){
	    	simplemsgData.studentids="";
	    	simplemsgData.classSectionIds = classSectionIds.toString();
	    	
	    }
	    	
	    
	    if(type == "MultiStudent")
	    	simplemsgData.studentids = classSectionIds.toString();
	    
	    simplemsgData.type = type;
	    //simplemsgData.eventid=$("#simpleddlMesgTemplate").val();
	    simplemsgData.message = $("#simplemesgText").val();
	    simplemsgData.configstatus="Active";
	    simplemsgData.configdate = moment().format("DD-MMM-YYYY");
	    
	    
	    simplemsgData.senderid = $("#simpleddlsenderid").val();
	    simplemsgData.schedule = moment($("#simpleschedule").val(),'DD-MMM-YYYY').format("YYYY-MM-DD hh:mm:ss");
	    
	    
	    
	   $('#simpleconfirmsendModal').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#simpleconfirmsendModal").modal("show"); 
	    
	    
	    
	    
	}

});


$(document).on("click",".closeBtn",function(e){
	$("#confirmsendModal").modal("hide");
	$("#simpleconfirmsendModal").modal("hide");
	
});

$(document).on("click","#btnSendConfirm",function(e){
$.ajax({
	url : "saveMessageParticipants.json",
	contentType : "application/json",
	processData : false,
	data:JSON.stringify(msgData),
	async : false,
	type : "POST",
	dataType: "json",
    cache: false,
    async:false,
	success : function(response) { 
		if(response.status==1){
			clearFields();
			$("#confirmsendModal").modal("hide");
			toastr.success("Messages are being sent.");
		
		}
	},
	error : function(error){
		clearFields();
		toastr.error(error);
		$("#confirmsendModal").modal("hide");
	 
	}

 });
});


$(document).on("click","#simplebtnSendConfirm",function(e){
	$.ajax({
		url : "saveMessageParticipants.json",
		contentType : "application/json",
		processData : false,
		data:JSON.stringify(simplemsgData),
		async : false,
		type : "POST",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				clearFields();
				$("#simpleconfirmsendModal").modal("hide");
				toastr.success("Messages are being sent.");
			
			}
		},
		error : function(error){
			clearFields();
			toastr.error(error);
			$("#simpleconfirmsendModal").modal("hide");
		 
		}

	 });
	
	});

$(document).on("change","#ddlsmsSendTo",function(e){
	var classSectionId = $("#ddlsmsSendTo").val();
	$.ajax({
		url : "getAllStudentOfClassandSectionByCampusId.json",
		data: {classSectionId:classSectionId},
	    contentType: "application/json",
	    crossDomain: true,
	    processData: true,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				$("#mainDiv").html("");
				
				var htmlText = "";
				
				if(response.data!=null && response.data.length>0)
					{
					
					$("#msgtoselector").removeClass("hide");
					
			  if(response.message=="MultipleClassess")
				   {
				   // multiple clss
				  
				  $("#selectDeselectDiv").removeClass("hide");	
					$.each(response.data,function(indx,data){
						indx=indx+1;
						htmlText += '<div class="eachchild"><label for="class'+data.classid+'" class="selected">';
						htmlText += '<input type="checkbox" checked="checked" class="changeCheckbox" ids="'+data.classid+'#'+data.sectionid+'" name="class'+data.classid+'#'+data.sectionid+'" id="class'+data.classid+'#'+data.sectionid+'" value="">';
						htmlText += indx+'. '+data.className+' >> '+data.sectionname+'</label></div>';
						
					});
					
					$("#mainDiv").append(htmlText);
					
					$("#templateBox").removeClass("hide");
				  
				  
				   }
			   else
				   {
				   
				   $("#selectDeselectDiv").removeClass("hide");	
					$.each(response.data,function(indx,data){
						indx=indx+1;
						htmlText += '<div class="eachchild"><label for="class'+data.student_id+'" class="selected">';
						htmlText += '<input type="checkbox" checked="checked"  class="changeCheckbox" name="class'+data.student_id+'" ids="'+data.student_id+'" value="">';
						htmlText += indx+'. '+data.student_name+'</label></div>';
						
					});
					
					$("#mainDiv").append(htmlText);
					
					$("#templateBox").removeClass("hide");
				   
				   }
					
				
				
					}
				else
					{
					
					 if(response.message=="AllClasses")
					   {
					   // all stdudents
						 $("#templateBox").removeClass("hide");
						 $("#msgtoselector").addClass("hide");
						 
					   }
				   else  
					   {
					$("#selectDeselectDiv").addClass("hide");
					$("#templateBox").addClass("hide");
					$("#mainDiv").html('<span class="center">No Record Found !</span>');
					   }
					}
				
			}
			else{
				
			}
		}
	  });
});

function clearFields()
{

	    $("#ddlMesgTemplate").val("");
	    $("#ddlMesgTemplateevent").val("");
	    $("#mesgText").val("");
	    $("#ddlsenderid").val("");
	    $("#schedule").val(moment().format('DD-MMM-YYYY'));
	    $("#ddlsmsSendTo").val("");
	    $("#msgtoselector").addClass("hide");
	    
	    
	    $("#simpleddlsenderid").val("");
	    $("#simplemesgText").val("");
	    
}

$(document).on("change","#ddlMesgTemplate",function(e){
	
	
	var eventId = $("#ddlMesgTemplate").val();
	
	$.ajax({
		url : "getAllTemplateOfEvent.json",
		data: {eventId:eventId},
	    contentType: "application/json",
	    crossDomain: true,
	    processData: true,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				
				$("#ddlMesgTemplateevent").html("");
				
				//<option value="-1">-Select-</option>
				$("#ddlMesgTemplateevent").append("<option value=''>-Select-</option>");
				
				if(response.data.length>0)
					{
					
					$("#ddlMesgTemplateevent").prop("disabled", false);
					
				$.each(response.data,function(indx,ddl){
					
					$("#ddlMesgTemplateevent").append("<option value='"+ddl.id+"'>"+ddl.template+"</option>");
					
				});
				
					}
				else
					{
					
					$("#ddlMesgTemplateevent").prop('disable', true);
					//$("#ddlSection").append("<option value='-1' selected='selected'>A</option>");
					//$("#ddlSection").change(); 
					}
				
				
			}
			else{
				
			}
		}
	  });
	
	
});
