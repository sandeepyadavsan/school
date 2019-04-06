var isEdit = false;
$(function(){
	
	$("#senderidManagementMenuId").addClass("active");
	
	getSenderidList();
});

function getSenderidList()
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
  
	$("#senderidTable").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllSenderid.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"pkid","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                     
                {"sName": "Campus Name","mData":"campus_name","bSortable": true },
                
                {"sName": "Sender Id","mData":"sender_id","bSortable": true },
                
                {"sName": "Status","mData":"status","bSortable": true },
                
               
              
                        {"sName": "Action",
	                          "mData": "pkid",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                               
	                            	//var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" pkid='+data+'  class="editSenderidEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete" pkid='+data+' campus_id='+full.campus_id+' class="deleteSenderidEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                               
	                            	//return edit_button+delete_button;
	                                return delete_button;
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

$(document).on("click", '#addSenderidEvent', function(e) {
	isEdit = false;
    $("#btnSaveSenderId").html("Save");
    $("#hdfSenderId").val('');
    $("#sender_id").val('');
   // $("#ddlstatus").val('');
  $('#addSenderidPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addSenderidPopup").modal("show");
});

$(document).on("click", '#btnSaveSenderId', function(e) {
	e.preventDefault();
	if($('#senderidform').validate().form()){
	var senderid={};
	
	senderid.campus_id=$("#ddlCampus").val();
	senderid.pkid=$("#hdfSenderId").val();
	senderid.sender_id=$("#sender_id").val();
	senderid.status=$("#ddlstatus").val();
	saveSenderidOrUpdate(senderid);
	}
	});
$(document).on("click", '.btnClose', function(e) {
	clearmsg();
	});

function clearmsg(){
	$("#senderidform" ).validate().resetForm();
	}
	function clear(){

		 $("#hdfSenderId").val('');
		    $("#sender_id").val('');
		   // $("#ddlstatus").val('');

	}
	
	function saveSenderidOrUpdate(senderid) {
		 $.ajax({
		 	contentType : "application/json",
			processData : false,
			data:JSON.stringify(senderid),
			async : false,
			url : "saveSenderidOrUpdate.json",
			type : "POST",
			dataType: "json",
		    cache: false,
		    async:false,
			success : function(response) { 
				clearmsg();
				if(response.status==1){
					$("#addSenderidPopup").modal("hide");
					clear();
					getSenderidList();
					toastr.success(response.message);
				}
				else{
						clearmsg();					
						toastr.error(response.message);
				}
			}
		});
		}
	
	$(document).on("click", '.deleteSenderidEvent', function(e) {

		var senderId = $(this).attr("pkid");
		var campusId = $(this).attr("campus_id");
		//var campus_id = $(this).attr("campus_id");

		$("#hdfSenderId").val(senderId);
		$("#hdfCampusId").val(campusId);

		$('#confirmDeletePopupModal').modal({
		backdrop: 'static',
		keyboard: false
		});
		$("#confirmDeletePopupModal").modal("show");

		});

	$(document).on("click", '#btnDeleteConfirm', function(e) {
		deleteSenderid();
		});
	
	
	function deleteSenderid(){
		var hdfDelSenderid=$("#hdfSenderId").val();
		var campusId = $("#hdfCampusId").val();
		$.ajax({
			url : "deleteSenderid.json",
			data: {pkid:hdfDelSenderid,campus_id:campusId},
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
					getSenderidList();
					$("#confirmDeletePopupModal").modal("hide");
				}
				else{
					toastr.error("Error Message : "+response.message);
				}
			}
		  });
		}