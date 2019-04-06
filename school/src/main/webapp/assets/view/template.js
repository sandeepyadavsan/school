var isEdit = false;
$(function(){
	
	$("#templateManagementMenuId").addClass("active");
	
	getTemplateList();
});

function getTemplateList()
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
  
	$("#templateTable").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllTemplatebyCampusId.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"id","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                     
                {"sName": "Event Name","mData":"event_name","bSortable": true },
                
                {"sName": "Template Name","mData":"templatename","bSortable": true },
                
                {"sName": "Template","mData":"template","bSortable": true },
                
                {"sName": "Status","mData":"status","bSortable": true },
              
                        {"sName": "Action",
	                          "mData": "id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                               
	                            	var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" id='+data+'  class="editTemplateEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete" id='+data+' campus_id='+full.campus_id+' class="deleteTemplateEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                               
	                            	return edit_button+delete_button;
	                               // return delete_button;
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

$(document).on("click", '#addTemplateEvent', function(e) {
	isEdit = false;
    $("#btnSaveTemplate").html("Save");
    $("#hdfTemplateid").val('');
    $("#template").val('');
    clear();
    
  $('#addTemplatePopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addTemplatePopup").modal("show");
});

$(document).on("click", '#btnSaveTemplate', function(e) {
	e.preventDefault();
	if($('#templateform').validate().form()){
	var template={};
	template.id=$("#hdfTemplateid").val();
	template.event_id=$("#ddlEvent").val();
	//template.id=$("#hdfTemplateid").val();
	template.templatename=$("#templatename").val();
	template.template=$("#template").val();
	template.status=$("#ddlstatus").val();
	
	saveTemplateOrUpdate(template);
	}
	});
$(document).on("click", '.btnClose', function(e) {
	clearmsg();
	
	});

function clearmsg(){
	$("#templateform" ).validate().resetForm();
	}
	function clear(){

		$("#templatename").val('');
		$("#template").val('');
		$("#ddlEvent").val('');
		
		
	}
	
	function saveTemplateOrUpdate(template) {
		 $.ajax({
		 	contentType : "application/json",
			processData : false,
			data:JSON.stringify(template),
			async : false,
			url : "saveTemplateOrUpdate.json",
			type : "POST",
			dataType: "json",
		    cache: false,
		    async:false,
			success : function(response) { 
				clearmsg();
				if(response.status==1){
					$("#addTemplatePopup").modal("hide");
					clear();
					getTemplateList();
					toastr.success(response.message);
				}
				else{
						clearmsg();					
						toastr.error(response.message);
				}
			}
		});
		}
	
	$(document).on("click", '.deleteTemplateEvent', function(e) {

		var templateid = $(this).attr("id");
		var campusId = $(this).attr("campus_id");
		//var campus_id = $(this).attr("campus_id");

		$("#hdfTemplateid").val(templateid);
		$("#hdfCampusId").val(campusId);

		$('#confirmDeletePopupModal').modal({
		backdrop: 'static',
		keyboard: false
		});
		$("#confirmDeletePopupModal").modal("show");

		});

	$(document).on("click", '#btnDeleteConfirm', function(e) {
		deleteTemplate();
		});
	
	
	function deleteTemplate(){
		var hdfTemplateid=$("#hdfTemplateid").val();
		var campusId = $("#hdfCampusId").val();
		$.ajax({
			url : "deleteTemplate.json",
			data: {id:hdfTemplateid,campus_id:campusId},
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
					getTemplateList();
					$("#confirmDeletePopupModal").modal("hide");
				}
				else{
					toastr.error("Error Message : "+response.message);
				}
			}
		  });
		}
	$(document).on("click", '.editTemplateEvent', function(e) {

		isEdit = true;
		$("#hdfTemplateid").val('');
	    $("#templatename").val('');
	    $("#template").val('');
	    $("#ddlstatus").val('');
	    
		$("#btnSaveTemplate").html("Update");

		 var template_id = ($(this).attr("id")!="null")?($(this).attr("id")):"";
		
		 
		 
		 $.ajax({
				url : "getTemplateById.json",
				data: {template_id:template_id},
			    contentType: "application/json",
			    crossDomain: true,
			    processData: true,
			    type: 'GET',
			    dataType: "json",
			    cache: false,
			    async:false,
				success : function(response) { 
					if(response.status==1){
						$("#hdfTemplateid").val(response.data.id);
						$("#ddlEvent option[value='"+response.data.event_id+"']").prop("selected", "selected");
					    $("#templatename").val(response.data.templatename);
					    $("#template").val(response.data.template);
					    $("#ddlstatus").val(response.data.status);
					}
					else{
						
					}
				}
			  });
		 
		  $('#addTemplatePopup').modal({
			    backdrop: 'static',
			    keyboard: false
		  });
		  $("#addTemplatePopup").modal("show");
		});