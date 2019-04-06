$(function(){
	
	$("#eventManagementMenuId").addClass("active");
	
		getEventList();

});

var indx = 0;
function getEventList()
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
  
	$("#tblEvent").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "getAllEventOfCampus.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"event_id","bSortable": false,   "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            $("button", nTd).tooltip({container:'body'});
                        }
                    },
                        
                        {"sName": "Event Name","mData":"event_name","bSortable": true},
                        {"sName": "Staus","mData":"event_status","bSortable": true},
                        
                        {"sName": "Action",
	                          "mData": "event_id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                                var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" event_id='+data+' eventName=\''+full.event_name+'\' status='+full.event_status+' class="editEventEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = ' <button href="javascript:void(0)" rel="title" title="Delete" event_id='+data+' eventName='+full.event_name+' class="deleteEventEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
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



$(document).on("click", '#addEventEvent', function(e) {
	
    $("#btnSaveEvent").html("Save");
    $("#hdfEventId").val('');
	$("#eventName").val('');
 
  $('#addEventPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addEventPopup").modal("show");
});


$(document).on("click", '.editEventEvent', function(e) {

$("#btnSaveEvent").html("Update");

 var event_id = ($(this).attr("event_id")!="null")?($(this).attr("event_id")):"";
 var event_name = ($(this).attr("eventName")!="null")?($(this).attr("eventName")):"N/A";
 var event_status = ($(this).attr("status")!="null")?($(this).attr("status")):"N/A";
 
    $("#hdfEventId").val(event_id);
	$("#eventName").val(event_name);
	
	$("#ddlStatus option[value='"+event_status+"']").attr("selected", "selected");
 
  $('#addEventPopup').modal({
	    backdrop: 'static',
	    keyboard: false
  });
  $("#addEventPopup").modal("show");
});

$(document).on("click", '.deleteEventEvent', function(e) {

var event_id = $(this).attr("event_id");
//var event_name = $(this).attr("eventName");

$("#hdfEventId").val(event_id);
//$("#delClassName").html(className);

$('#confirmDeletePopupModal').modal({
backdrop: 'static',
keyboard: false
});
$("#confirmDeletePopupModal").modal("show");

});
$(document).on("click", '#btnDeleteConfirm', function(e) {
deleteEvent();
});

$(document).on("click", '#btnSaveEvent', function(e) {
e.preventDefault();
if($('#eventform').validate().form()){
var events={};
events.event_id=$("#hdfEventId").val();
events.event_name=$("#eventName").val();
if(events.event_id==null || events.event_id=="")
events.event_status="Active";
else
	events.event_status=$("#ddlStatus").val();
saveEventOrUpdate(events);
}
});

$(document).on("click", '.btnClose', function(e) {
clearmsg();
});

function saveEventOrUpdate(events) {
 $.ajax({
 	contentType : "application/json",
	processData : false,
	data:JSON.stringify(events),
	async : false,
	url : "saveEventOrUpdate.json",
	type : "POST",
	dataType: "json",
    cache: false,
    async:false,
	success : function(response) { 
		clearmsg();
		if(response.status==1){
			$("#addEventPopup").modal("hide");
			clear();
			getEventList();
			toastr.success(response.message);
		}
		else{
				clearmsg();					
				toastr.error(response.message);
		}
	}
});
}
function deleteEvent(){
var hdfEventId=$("#hdfEventId").val();
$.ajax({
	url : "deleteEvent.json",
	data: {event_id:hdfEventId},
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
			getEventList();
			$("#confirmDeletePopupModal").modal("hide");
		}
		else{
			toastr.error("Error Message : "+response.message);
		}
	}
  });
}

function clearmsg(){
$("#eventform" ).validate().resetForm();
}
function clear(){

$("#hdfEventId").val("");
$("#eventName").val("");

}
