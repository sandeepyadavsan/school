var isEdit = false;
$(function(){
	
	$("#campusManagementMenuId").addClass("active");
	
	getCampusList();
});

function getCampusList()
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
  
	$("#campusTable").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllCampus.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"campus_id","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                     
                {"sName": "Campus Name","mData":"campus_name","bSortable": true },
                
                {"sName": "Campus Address","mData":"campus_address","bSortable": true },
                
                {"sName": "Pincode","mData":"pincode","bSortable": true },
                
                {"sName": "Mobile","mData":"campus_phone","bSortable": true },
                
                {"sName": "Push URL","mData":"pushurl","bSortable": true },
                
                {"sName": "Status","mData":"status","bSortable": false },

                {"sName": "Action",
	                          "mData": "campus_id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                            	
	                            	var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" status="'+full.status+'" pushurl="'+full.pushurl+'" pincode="'+full.pincode+'" campus_logo="'+full.campus_logo+'" campus_id='+data+' campus_name="'+full.campus_name+'" campus_phone="'+full.campus_phone+'" campus_address="'+full.campus_address+'" class="editCampusEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete"  campus_name="'+full.campus_name+'" campus_id='+data+' class="deleteCampusEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                               
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

$(document).on("click", '.deleteCampusEvent', function(e) {
	
	$("#hdfDelid").val($(this).attr("campus_id"));
    $("#delcampus_name").html($(this).attr("campus_name"));
    
    $('#confirmDeletePopupModal').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#confirmDeletePopupModal").modal("show");
  
});

$(document).on("click", '.editCampusEvent', function(e) {
	isEdit = true;

	$("#btnSaveCampus").html("Update");
    $("#hdfCampusId").val($(this).attr("campus_id"));
    $("#campus_name").val($(this).attr("campus_name"));
    $("#campus_address").val($(this).attr("campus_address"));
    $("#pincode").val($(this).attr("pincode"));
    $("#campus_phone").val($(this).attr("campus_phone"));
    //$("#campus_logo").val($(this).attr("campus_logo"));
    $("#pushurl").val($(this).attr("pushurl"));
    $("#ddlstatus option[value='"+$(this).attr("status")+"']").attr("selected", "selected");
	
  $('#addCampusPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addCampusPopup").modal("show");
	
});

$(document).on("click", '#addCampusEvent', function(e) {
	isEdit = false;
    $("#btnSaveCampus").html("Save");
    
    $("#hdfCampusId").val('');
    $("#campus_name").val('');
    $("#campus_address").val('');
    $("#pincode").val('');
    $("#campus_phone").val('');
    $("#campus_logo").val('');
    $("#pushurl").val('');
   // $("#ddlstatus").val('');
   
	
  $('#addCampusPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addCampusPopup").modal("show");
});

$(document).on("click", '#btnDeleteConfirm', function(e) {
	e.preventDefault();
	
	$.ajax({
	 	contentType : "application/json",
		processData : true,
		data:{campusId:$("#hdfDelid").val()},
		async : false,
		url : "deleteCampus.json",
		type : "GET",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				$("#confirmDeletePopupModal").modal("hide");
				getCampusList();
				toastr.success(response.message);
			}
			else{
					toastr.error(response.message);
			}
		}
	});
	
});

$(document).on("click", '#btnSaveCampus', function(e) {
	e.preventDefault();
	if($('#campusform').validate().form()){
	var campus={};
	campus.campus_id=$("#hdfCampusId").val();
	campus.campus_name=$("#campus_name").val();
	
	campus.campus_address=$("#campus_address").val();
	campus.pincode=$("#pincode").val();
	campus.campus_phone=$("#campus_phone").val();
	
	
	campus.campus_logo=$("#campus_logo").val();
	campus.pushurl=$("#pushurl").val();
	campus.status=$("#ddlstatus").val();
	
	//student.session_id=$("#session_id").val();
	//student.student_status=$("#student_status").val();
	
	
	saveCampusOrUpdate(campus);
	}
	});

$(document).on("click", '.btnClose', function(e) {
	clearmsg();
	});


function saveCampusOrUpdate(campus) {
	 $.ajax({
	 	contentType : "application/json",
		processData : false,
		data:JSON.stringify(campus),
		async : false,
		url : "saveCampusOrUpdate.json",
		type : "POST",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				$("#addCampusPopup").modal("hide");
				clear();
				getCampusList();
				toastr.success(response.message);
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
	});
	}

function clearmsg(){
	$("#campusform" ).validate().resetForm();
	}
	function clear(){

		 $("#hdfCampusId").val('');
		    $("#campus_name").val('');
		    $("#campus_address").val('');
		    $("#pincode").val('');
		    $("#campus_phone").val('');
		    $("#campus_logo").val('');
		    $("#pushurl").val('');

	}
