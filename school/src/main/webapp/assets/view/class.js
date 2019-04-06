var SectionData;
$(function(){
	
	$("#classManagementMenuId").addClass("active");
	
getClassList();
getAllSectionOfCampus();

});

var indx = 0;
function getClassList()
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
  
	$("#tblClass").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllClassesOfCampus.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"classid","bSortable": false,   "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                        
                        {"sName": "Class Name","mData":"className","bSortable": true},
                        
                        {"sName": "Action",
	                          "mData": "classid",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                                var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" classId='+data+' className='+full.className+' campusId='+full.campus_id+' class="editClassEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete" classId='+data+' className='+full.className+' class="deleteClassEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                                var add_section = ' <button href="javascript:void(0)" rel="title" title="Add Section" classId='+data+' className='+full.className+' class="addSectionEvent btn btn-sm btn-square btn-info ml10"><i class="fa fa-plus"></i></button>';
	                                var get_section = ' <button href="javascript:void(0)" rel="title" title="View Section" classId='+data+' className='+full.className+' class="getSectionEvent btn btn-sm btn-square btn-info ml10"><i class="fa fa-eye"></i></button>';
	                                return edit_button+delete_button+add_section+get_section;
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


$(document).on("click", '#newsection', function(e) {
	
	 $('#addNewSectionPopup').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#addNewSectionPopup").modal("show");
	
	
});

$(document).on("click", '#saveNewSection', function(e) {
	// save new section
	
	e.preventDefault();
	if($('#newsectionform').validate().form()){
	var newSection=$("#newSectionName").val();
	saveNewSectionOrUpdate(newSection);
	}
});


function saveNewSectionOrUpdate(newSection) {
	 $.ajax({
	 	contentType : "application/json",
		processData : false,
		data:JSON.stringify(newSection),
		async : false,
		url : "saveNewSectionOrUpdate.json",
		type : "POST",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				$("#addNewSectionPopup").modal("hide");
				$("#newSectionName").val("");
				
				getAllSectionOfCampus();
				
				
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	});
	}


$(document).on("click", '.getSectionEvent', function(e) {

	var classId = $(this).attr('classId');
	var className = $(this).attr('className');
	getAllSectionOfClass(classId,className);

});

$(document).on("click", '#refreshlist', function(e) {
	
	getAllSectionOfCampus();
});


$(document).on("click", '.addSectionEvent', function(e) {

	
	$("#classNameOfSection").val($(this).attr('classname'));
	$("#hdfClassIdSection").val($(this).attr('classid'));
	var classId = $(this).attr('classId');
	var div=$('#reload').html();
	$.ajax({
	 	contentType : "application/json",
		processData : true,
		data:{classId:classId},
		async : false,
		url : "getMapSectionByClassId.json",
		type : "GET",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				//$('#sectionMultiSelect option').remove();
				
				$.each(response.data,function(indx,data){
					
					//ids.push(data.sectionid);
					
					//alert("hello"+response.status+" "+data.sectionid);
					//$('#sectionMultiSelect option[value="'+data.sectionid+'"]').prop('selected', true);
					$('#sectionMultiSelect option[value="'+data.sectionid+'"]').addClass('greenColor');
					$('#sectionMultiSelect option[value="'+data.sectionid+'"]').prop('disabled', true);
					
				});
				
				
				//$("#sectionMultiSelect").val(ids);

				//$('#sectionMultiSelect').multiSelect('refresh');
				
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	});
	
	
	
	
	
	 $('#addSectionPopup').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#addSectionPopup").modal("show");
	
});

$(document).on("click", '#btnMapSection', function(e) {


	if($('#sectionform').validate().form()){
	var classId = $("#hdfClassIdSection").val();
	
	
	var sectionArray = $("#sectionMultiSelect").val();
	
	var sectionIds = "";
	
	$.each(sectionArray,function(indx,dd){
		sectionIds = dd+"#"+sectionIds;
	});
	console.log("sectionIds : "+sectionIds);
	$.ajax({
	 	contentType : "application/json",
		processData : true,
		data:{classId:classId,sectionIds:sectionIds},
		async : false,
		url : "mapSectionClass.json",
		type : "GET",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				getClassList();
				toastr.success(response.message);
				  $("#addSectionPopup").modal("hide");
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	});
	
	}
	
});

$(document).on("click", '#addClassEvent', function(e) {
	
    $("#btnSaveClass").html("Save");
    $("#hdfClassId").val('');
	$("#className").val('');
 
  $('#addClassPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addClassPopup").modal("show");
});


$(document).on("click", '.editClassEvent', function(e) {

$("#btnSaveClass").html("Update");

 var classId = ($(this).attr("classId")!="null")?($(this).attr("classId")):"";
 var className = ($(this).attr("className")!="null")?($(this).attr("className")):"N/A";
 
    $("#hdfClassId").val(classId);
	$("#className").val(className);
 
  $('#addClassPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addClassPopup").modal("show");
});

$(document).on("click", '.deleteClassEvent', function(e) {

var classId = $(this).attr("classId");
var className = $(this).attr("className");

$("#hdfDelClassId").val(classId);
$("#delClassName").html(className);

$('#confirmDeletePopupModal').modal({
backdrop: 'static',
keyboard: false
});
$("#confirmDeletePopupModal").modal("show");

});
$(document).on("click", '#btnDeleteConfirm', function(e) {
deleteClass();
});

$(document).on("click", '#btnSaveClass', function(e) {
e.preventDefault();
if($('#classform').validate().form()){
var classes={};
classes.classId=$("#hdfClassId").val();
classes.className=$("#className").val();
saveClassOrUpdate(classes);
}
});

$(document).on("click", '.btnClose', function(e) {
clearmsg();
});

function saveClassOrUpdate(classes) {
 $.ajax({
 	contentType : "application/json",
	processData : false,
	data:JSON.stringify(classes),
	async : false,
	url : "saveClassOrUpdate.json",
	type : "POST",
	dataType: "json",
    cache: false,
    async:false,
	success : function(response) { 
		clearmsg();
		if(response.status==1){
			$("#addClassPopup").modal("hide");
			clear();
			getClassList();
			toastr.success(response.message);
		}
		else{
				clearmsg();					
				toastr.error(response.message);
		}
	}
});
}
function deleteClass(){
var hdfDelClassId=$("#hdfDelClassId").val();
$.ajax({
	url : "deleteClass.json",
	data: {classId:hdfDelClassId},
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
			getClassList();
			$("#confirmDeletePopupModal").modal("hide");
		}
		else{
			toastr.error("Error Message : "+response.message);
		}
	}
  });
}

function clearmsg(){
$("#classform" ).validate().resetForm();
$("#sectionform" ).validate().resetForm();
$("#newsectionform" ).validate().resetForm();
$("#newSectionName").val("");
}
function clear(){

$("#hdfClassId").val("");
$("#className").val("");

}

function getAllSectionOfCampus(){
	
	$.ajax({
		url : "getAllSectionOfCampus.json",
	    contentType: "application/json",
	    crossDomain: true,
	    processData: false,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				// response of sections
				SectionData = response.data;
				
				$("#sectionMultiSelect").html("");
				
				$.each(SectionData,function(index,data){
					
					$("#sectionMultiSelect").append("<option value='"+data.sectioid+"'>"+data.sectionname+"</option>");
					
				});
				
			}
			else{
				toastr.error("Error Message : "+response.message);
				SectionData = "";
			}
		}
	  });
	
	
}

function getAllSectionOfClass(classId,className){
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
				// response of sections
				
				$("#headSection").html("Sections of "+className+" Class");
				$("#sectionHtml").html("");
				if(response.data.length>0)
					{
				
				$.each(response.data,function(indx,data){
					
					
					if(response.data.length-1>indx)
					
					$("#sectionHtml").append(data.sectionname+" | ");
					else
						$("#sectionHtml").append(data.sectionname);	
					
				});
					}
				else
					{
					$("#sectionHtml").append("No Section mapped. ");
					
					
					}
				
				
				
				$('#viewSections').modal({
				    backdrop: 'static',
				    keyboard: false
			  });
			  $("#viewSections").modal("show");
			}
			else{
				toastr.error("Error Message : "+response.message);
			}
		}
	  });
}

$(document).on("click", '#downloadclasssection', function(e) {
	window.open('http://54.202.11.57:8080/school/downloadCSV.json','_blank');
	});

