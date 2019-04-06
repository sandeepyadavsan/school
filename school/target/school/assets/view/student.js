var isEdit = false;
$(function(){
	
	$("#studentManagementMenuId").addClass("active");
	
	
	$('#student_dob').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
	
	$('#date_of_admission').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
	
	
	
	getStudentList();
});

function getStudentList()
{
	var stringify_aoData = function (aoData) {
        var o = {};
        var modifiers = ['mDataProp_', 'sSearch_', 'iSortCol_', 'bSortable_', 'bRegex_', 'bSearchable_', 'sSortDir_'];
        jQuery.each(aoData, function(idx,obj) {
            if (obj.name) {
                for (var i=0; i < modifiers.length; i++) {
                    if (obj.name.substring(0, modifiers[i].length) == modifiers[i]) {
                        var index = parseInt(obj.name.substring(modifiers[i].length));
                        var key = 'a' + modifiers[i].substring(0, modifiers[i].length-1);
                        if (!o[key]) {
                            o[key] = [];
                        }
                        o[key][index] = obj.value;
                        return;
                    }
                }
                o[obj.name] = obj.value;
            }
            else {
                o[idx] = obj;
            }
        });
       
        return JSON.stringify(o);
    };
  
	$("#studentTable").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllStudentOfCampus.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"student_id","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                     
                {"sName": "Form No","mData":"form_number","bSortable": true },
                
                {"sName": "Name","mData":"student_name","bSortable": true },
                
                {"sName": "Father","mData":"student_father","bSortable": true },
                
                {"sName": "Class","mData":"className","bSortable": true },
                
                {"sName": "Section","mData":"sectionname","bSortable": true },
                
                {"sName": "Roll No","mData":"student_rollno","bSortable": true },
                
                {"sName": "Message Mobile No","mData":"message_mobile_number","bSortable": false },
                
                
                
                    
               /* {"sName": "S. No.","mData":"studentid","bSortable": false,   "mRender": function (data, type, full) {
                	indx++;
                    return indx;
                },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                    $("button", nTd).tooltip({container:'body'});
                }
            },
            {"sName": "S. No.","mData":"studentid","bSortable": false,   "mRender": function (data, type, full) {
            	indx++;
                return indx;
            },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                $("button", nTd).tooltip({container:'body'});
            }
        },
        {"sName": "S. No.","mData":"studentid","bSortable": false,   "mRender": function (data, type, full) {
        	indx++;
            return indx;
        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
            $("button", nTd).tooltip({container:'body'});
        }
    },
    {"sName": "S. No.","mData":"studentid","bSortable": false,   "mRender": function (data, type, full) {
    	indx++;
        return indx;
    },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
        $("button", nTd).tooltip({container:'body'});
    }
},
{"sName": "S. No.","mData":"studentid","bSortable": false,   "mRender": function (data, type, full) {
	indx++;
    return indx;
},"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
    $("button", nTd).tooltip({container:'body'});
}
},
                        
                        {"sName": "Class Name","mData":"className","bSortable": true},
                        */
                        {"sName": "Action",
	                          "mData": "student_id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                               
	                            	var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" student_id='+data+'  class="editStudentEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete" student_id='+data+' class="deleteStudentEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                               
	                            	return edit_button+delete_button;
	                            },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
	                                $("button", nTd).tooltip({container:'body'});
	                            }
	                        }
                    ],
        "fnServerData": function(sSource, aoData, fnCallback) {
        	indx = 0;
            $.ajax({
            	 dataType: 'json',
                 contentType: "application/json;charset=UTF-8",
                 type: 'POST',
                 url: sSource,
                 data: stringify_aoData(aoData),
                 async:true,
                 success: fnCallback
            });
        },
        "sPaginationType": "full_numbers",
        dom: 'Bfrtip',
        buttons: [
             'csv', 'excel', 'pdf'
        ]
    });
}

var studentPic = null;

$(document).on("change","#student_picture",function(e){

	studentPic = e.target.files;
	console.log(studentPic);
	//studentImg
	 
});


var files = null;

$(document).on("change","#uploadStudentRecord",function(e){

	files = e.target.files;
	var oMyForm = new FormData();
	 if(files!=null && files[0]!= undefined){
		 if(files[0].size>3145728){
	          toastr.error("Please Attach '.csv' file Less Than 3 Mb.");
	          files = null;
	          return;
	      }
	   oMyForm.append("file",files[0]);
	   uploadStudentCSV(oMyForm);
	 }else{
		 toastr.info("Please select file.");
		 return;
	 }
	 
});



function uploadStudentCSV(oMyForm){
	$.ajax({
		url : "uploadStudentCSV.json",
		data: oMyForm,
	    contentType: "application/json",
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    dataType: "json",
	    cache: false,
	    async:true,
		success : function(response) { 
			if(response.status==1){
				getStudentList();
				toastr.success(response.message);
				files=null;
				$("#uploadStudentRecord").val("");
				$("#addStudentEvent").focus();
			}
			else{
				toastr.error(response.message);
				files=null;
				$("#uploadStudentRecord").val("");
				$("#addStudentEvent").focus();
			}
		}
	  });
}





////////////////////////

$(document).on("change","#ddlClass",function(e){
	
	//$("#student_rollno").val('');
	//$("#form_number").val('');
	
	var classId = $("#ddlClass").val();
	
	$.ajax({
		url : "getAllSectionOfClass.json",
		data: {classId:classId},
	    contentType: "application/json",
	    crossDomain: true,
	    processData: true,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				
				$("#ddlSection").html("");
				
				//<option value="-1">-Select-</option>
				$("#ddlSection").append("<option value=''>-Select-</option>");
				
				if(response.data.length>0)
					{
					
					$("#sectionDiv").removeClass("hide");
					
				$.each(response.data,function(indx,ddl){
					
					$("#ddlSection").append("<option value='"+ddl.sectioid+"'>"+ddl.sectionname+"</option>");
					
				});
				
					}
				else
					{
					
					$("#sectionDiv").addClass("hide");
					$("#ddlSection").append("<option value='-1' selected='selected'>A</option>");
					$("#ddlSection").change(); 
					}
				
				
			}
			else{
				
			}
		}
	  });
	
	
});


$(document).on("change","#ddlSection",function(e){
	
	if(!isEdit)
	{ 
		
	var classId = $("#ddlClass").val();
	var sectionId = $("#ddlSection").val();
	
	
	if(classId!=null || sectionId!=null)
		{
	
	 $.ajax({
			url : "getRollNoFormNobyCampusId.json",
			data: {classId:classId,sectionId:sectionId},
		    contentType: "application/json",
		    crossDomain: true,
		    processData: true,
		    type: 'GET',
		    dataType: "json",
		    cache: false,
		    async:false,
			success : function(response) { 
				if(response.status==1){
					
					$.each(response.data,function(indx,data){
						
						//$("#student_rollno").val(data.rollNo);
						$("#form_number").val(data.formNo);
						
					});
					
					
					/*$("#ddlSection").html("");
					
					
					$.each(response.data,function(indx,ddl){
						
						$("#ddlSection").append("<option value='"+ddl.sectioid+"'>"+ddl.sectionname+"</option>");
						
					});
					*/
					
					
					
				}
				else{
					
				}
			}
		  });
	 
		}
}
	
});


	$(document).on("click", '.editStudentEvent', function(e) {

		isEdit = true;
		$("#btnSaveStudent").html("Update");

		 var student_id = ($(this).attr("student_id")!="null")?($(this).attr("student_id")):"";
		
		 
		 
		 $.ajax({
				url : "getStudentById.json",
				data: {studentId:student_id},
			    contentType: "application/json",
			    crossDomain: true,
			    processData: true,
			    type: 'GET',
			    dataType: "json",
			    cache: false,
			    async:false,
				success : function(response) { 
					if(response.status==1){
						
						fillEditForm(response.data);
						
					}
					else{
						
					}
				}
			  });
		 
		 
		// var classid = ($(this).attr("studentName")!="null")?($(this).attr("studentName")):"N/A";
		// var sectionid = ($(this).attr("studentName")!="null")?($(this).attr("studentName")):"N/A";
		/* var form_number = ($(this).attr("form_number")!="null")?($(this).attr("form_number")):"N/A";
		 alert("form_number: "+form_number);
		 var student_rollno = ($(this).attr("student_rollno")!="null")?($(this).attr("student_rollno")):"N/A";
		 var student_name = ($(this).attr("studentName")!="null")?($(this).attr("studentName")):"N/A";
		 alert("student_name: "+student_name);
		 var student_father = ($(this).attr("student_father")!="null")?($(this).attr("student_father")):"N/A";
		 var student_mother = ($(this).attr("student_mother")!="null")?($(this).attr("student_mother")):"N/A";
		 var student_dob = ($(this).attr("student_dob")!="null")?($(this).attr("student_dob")):"N/A";
		 var date_of_admission = ($(this).attr("date_of_admission")!="null")?($(this).attr("date_of_admission")):"N/A";
		 
		 var student_gender = ($(this).attr("student_gender")!="null")?($(this).attr("student_gender")):"N/A";
		 var student_caste = ($(this).attr("student_caste")!="null")?($(this).attr("student_caste")):"N/A";
		 var student_current_address = ($(this).attr("student_current_address")!="null")?($(this).attr("student_current_address")):"N/A";
		 var student_home_address = ($(this).attr("student_home_address")!="null")?($(this).attr("student_home_address")):"N/A";
		 
		 
		 var student_email = ($(this).attr("student_email")!="null")?($(this).attr("student_email")):"N/A";
		 var student_picture = ($(this).attr("student_picture")!="null")?($(this).attr("student_picture")):"N/A";
		 var message_mobile_number = ($(this).attr("message_mobile_number")!="null")?($(this).attr("message_mobile_number")):"N/A";
		 var student_status = ($(this).attr("student_status")!="null")?($(this).attr("student_status")):"N/A";
		 
		 
		 
		 
		    $("#hdfStudentId").val(student_id);
			$("#student_name").val(student_name);*/
		 
		  $('#addStudentPopup').modal({
			    backdrop: 'static',
			    keyboard: false
		  });
		  $("#addStudentPopup").modal("show");
		});
		
function fillEditForm(data)
{
	
	
	$("#hdfStudentId").val(data.student_id);
	
	
	$("#ddlClass option[value='"+data.classid+"']").attr("selected", "selected");
	$("#ddlClass").trigger('change'); 
	
	
	$("#ddlSection option[value='"+data.sectionid+"']").attr("selected", "selected");
	
    $("#form_number").val(data.form_number);
    $("#student_rollno").val(data.student_rollno);
    $("#student_name").val(data.student_name);
    $("#student_father").val(data.student_father);
    $("#student_mother").val(data.student_mother);
    $("#student_dob").val(data.student_dob);
    $("#date_of_admission").val(data.date_of_admission);
    
    
    $('input:radio[class=student_gender] [id='+data.student_gender+']').attr('checked', true);
    
    $("#ddlstudent_caste option[id='"+data.student_caste+"']").attr("selected", "selected");
    
    $("#student_current_address").val(data.student_current_address);
    
    
	$("#student_home_address").val(data.student_home_address);
	$("#student_email").val(data.student_email);
	
	$("#studentImg").attr('src','assets/photo/'+data.student_picture);
	
	
	$("#message_mobile_number").val(data.message_mobile_number);
	
	
	
}
	
	

$(document).on("click", '#addStudentEvent', function(e) {
	isEdit = false;
    $("#btnSaveStudent").html("Save");
    
    $("#hdfStudentId").val('');
   // $("#ddlClass").val('');
    $("#ddlSection").val('');
    $("#ddlSection").trigger('change'); 
    $("#form_number").val('');
    $("#student_rollno").val('');
    $("#student_name").val('');
    $("#student_father").val('');
    $("#student_mother").val('');
    $("#student_dob").val('');
    $("#date_of_admission").val('');
    $("#student_gender").val('');
    $("#ddlstudent_caste").val('');
    $("#student_current_address").val('');
    
	$("#student_home_address").val('');
	$("#student_email").val('');
	
	$("#student_picture").val('');
	$("#message_mobile_number").val('');
	
  $('#addStudentPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addStudentPopup").modal("show");
});

$(document).on("click", '.deleteStudentEvent', function(e) {

	var studentId = $(this).attr("student_id");
	//var campus_id = $(this).attr("campus_id");

	$("#hdfStudentId").val(studentId);
	//$("#hdfCampusId").html(campus_id);

	$('#confirmDeletePopupModal').modal({
	backdrop: 'static',
	keyboard: false
	});
	$("#confirmDeletePopupModal").modal("show");

	});

$(document).on("click", '#btnDeleteConfirm', function(e) {
	deleteStudent();
	});

$(document).on("click", '#btnSaveStudent', function(e) {
	e.preventDefault();
	if($('#studentform').validate().form()){
		
		var oMyForm = new FormData();
		 if(studentPic!=null && studentPic[0]!= undefined){
			 if(studentPic[0].size>3145728){
		          toastr.error("Please attach file less than 3 Mb.");
		          studentPic = null;
		          return;
		      }
		   oMyForm.append("studentPic",studentPic[0]);
		   oMyForm.append("student_picture",$("#student_picture").val());
		   
		 }
		 else
			 {
			 oMyForm.append("studentPic",null);
			 oMyForm.append("student_picture","");
			 }
			
		
		 oMyForm.append("student_id",$("#hdfStudentId").val());
		 oMyForm.append("classid",$("#ddlClass").val());
		 oMyForm.append("sectionid",$("#ddlSection").val());
		 oMyForm.append("form_number",$("#form_number").val());
		 oMyForm.append("student_rollno",$("#student_rollno").val());
		 oMyForm.append("student_name",$("#student_name").val());
		 oMyForm.append("student_father",$("#student_father").val());
		 oMyForm.append("student_mother",$("#student_mother").val());
		 oMyForm.append("student_dob",$("#student_dob").val());
		 oMyForm.append("date_of_admission",$("#date_of_admission").val());
		 oMyForm.append("student_gender",$('input[name="student_gender"]:checked').val());
		 oMyForm.append("student_caste",$("#ddlstudent_caste").val());
		 oMyForm.append("student_current_address",$("#student_current_address").val());
		 oMyForm.append("student_home_address",$("#student_home_address").val());
		 oMyForm.append("student_email",$("#student_email").val());
		 oMyForm.append("message_mobile_number",$("#message_mobile_number").val());
		 oMyForm.append("session_id","4");
		 oMyForm.append("student_status","Active");
		 
	
	saveStudentOrUpdate(oMyForm);
	studentPic = null;
	
	}
	});

$(document).on("click", '.btnClose', function(e) {
	clearmsg();
	});

function saveStudentOrUpdate(oMyForm) {
	
	
	
	//
	
	$.ajax({
		url : "saveStudentOrUpdate.json",
		data: oMyForm,
	    contentType: "application/json",
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    dataType: "json",
	    cache: false,
	    async:true,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				$("#addStudentPopup").modal("hide");
				clear();
				getStudentList();
				toastr.success(response.message);
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	  });
	
	///
	
	/* $.ajax({
	 	contentType : "application/json",
		processData : false,
		data:JSON.stringify(student),
		async : false,
		url : "saveStudentOrUpdate.json",
		type : "POST",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				$("#addStudentPopup").modal("hide");
				clear();
				getStudentList();
				toastr.success(response.message);
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	}); */
	}


function deleteStudent(){
	var hdfDelStudentId=$("#hdfStudentId").val();
	$.ajax({
		url : "deleteStudent.json",
		data: {studentId:hdfDelStudentId},
	    contentType: "application/json",
	    crossDomain: true,
	    processData: true,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				toastr.success(response.message);
				getStudentList();
				$("#confirmDeletePopupModal").modal("hide");
			}
			else{
				toastr.error("Error Message : "+response.message);
			}
		}
	  });
	}

function clearmsg(){
	$("#studentform" ).validate().resetForm();
	$("#student_picture").val("");
	}
	function clear(){

	$("#hdfStudentId").val("");
	$("#student_name").val("");

	}

