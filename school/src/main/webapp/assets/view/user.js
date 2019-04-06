
$(function(){
	
	$("#userMenuId").addClass("active");
	
	$('#account_dob').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
	
	
	$("#account_dob").on("dp.change",function (e) {
	    $('#account_email').focus();
	 });
	
	$("#account_dob").val(moment().format('DD-MMM-YYYY'));
	
	getUserList();
	
});

function getUserList()
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
    
	$("#tblUser").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": true,
        "lengthChange": true,
        "sAjaxSource": "findAllUsers.json",
        "aoColumns":[
                     {"sName": "S. No.","mData":"login_id","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                     	indx++;
                         return indx;
                     },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                         $("button", nTd).tooltip({container:'body'});
                     }
                 },
                        {"sName": "LoginName","mData":"user_name","bSortable": true},
                        {"sName": "CampusId","mData":"campus_name","bSortable": true},
                        
                        {"sName": "Status","mData":"status","bSortable": true},
                        
                        /*{"sName": "Type","mData":"type",
                        	"bSearchable": false,
                        	 "bSortable": false,
                        	"mRender": function (data, type, full) {
                        	var uType="Admin";
                        	if(data=="0"){
                        		uType="User";
                        	}
                            return uType;
                        }},*/
                        
                        {"sName": "Action",
	                          "mData": "login_id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                                var edit_button = '<button href="javascript:void(0)" rel="title" title="Edit" userId='+data+' userName='+full.user_name+' campusId='+full.campus_id+' status='+full.status+' type='+full.type+' password='+full.password+' class="editUserEvent btn btn-sm btn-square btn-info"><i class="fa fa-edit"></i></button>';
	                                var delete_button = '<button href="javascript:void(0)" rel="title" title="Delete" userId='+data+' userName='+full.user_name+' class="deleteUserEvent btn btn-sm btn-square btn-danger ml10"><i class="fa fa-trash"></i></button>';
	                                var rst_pwd = '<button href="javascript:void(0)" rel="title" title="Reset Password" userId='+data+' campusId='+full.campus_id+' class="resetPasswordevt btn btn-sm btn-square btn-purple ml10"><i class="fa fa-lock"></i></button>';
	                                return edit_button+delete_button+rst_pwd;
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
            'copy', 'csv', 'excel', 'pdf', 'print'
            /*{
                extend: 'collection',
                text: 'Export',
                buttons: [
                 //   'copy',
                    'excel',
                    'csv',
                    'pdf'
                   // 'print'
                ]
            }*/
        ]
    });
	
}

$(document).on("click", '#btnResetPwd', function(e) {
	
	if($('#pwd-form').validate().form()){
		
		var userId=$("#pwdUserId").val();
			var campusId=$("#campusIdHidden").val();
				var pwd =$("#password").val();
					var c_pwd =$("#c_password").val();
	 $.ajax({
		 url : "resetUserPassword.json",
		 data:{userId:userId,campusId:campusId,pwd:pwd,c_pwd:c_pwd},
		    contentType: "application/json",
		    crossDomain: true,
		    processData: true,
		    type: 'GET',
		    dataType: "json",
		    cache: false,
		    async:false,
			success : function(response) { 
				clearpwdMesg();
				if(response.status==1){
					$("#resetPwdModal").modal("hide");
					toastr.success(response.message);
				}
				else{
					    clearpwdMesg();					
						toastr.error(response.message);
				}
			}
	   });
	}
});

$(document).on("click", '#addUserEvent', function(e) {
	    $("#btnSaveUser").html("Save");
	    $("#hdfUserId").val('');
		$("#account_user_name").val('');
		$("#upassword").val('');
		//$("#cpassword").val('');
		//$("#campusId").val('');
		//$("#empCode").val('');
		$("#ddlCampus option:selected").removeAttr("selected");
		//$("#ddlUserType option:selected").removeAttr("selected");
		//$("#ddlStatus option[value='A']").attr("selected", "selected");
	 
	  $('#addUserPopup').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#addUserPopup").modal("show");
});

$(document).on("click", '.editUserEvent', function(e) {
	$("#btnSaveUser").html("Update");
	 var userId = ($(this).attr("userid")!="null")?($(this).attr("userid")):"";
	 var userName = ($(this).attr("userName")!="null")?($(this).attr("userName")):"";
	 var campusId = ($(this).attr("campusId")!="null")?($(this).attr("campusId")):"";
	 var status = ($(this).attr("status")!="null")?($(this).attr("status")):"";
	 var type = ($(this).attr("type")!="null")?($(this).attr("type")):"1";
	 if(type=="1")
		 type="1";
	 else
		 type="0";
	 var password = ($(this).attr("password")!="null")?($(this).attr("password")):"";
	 
	// alert(userId+">"+userName+">"+campusId+">"+status+">"+type);
	 
	 
	    $("#hdfUserId").val(userId);
		$("#account_user_name").val(userName);
		$("#upassword").val(password);
		$("#ddlCampus").val(campusId);
		
		
		$("#ddlUserType").val(type);
		$("#ddlStatus").val(status);
		
		/*$("#empCode").val(empCode);
		$("#ddlUserType option:selected").removeAttr("selected");
		$("#ddlStatus option:selected").removeAttr("selected");
		$("#ddlStatus option[value="+status+"]").attr("selected", "selected");
		$("#ddlUserType option[value="+type+"]").attr("selected", "selected");*/
	 
	  $('#addUserPopup').modal({
		    backdrop: 'static',
		    keyboard: false
	  });
	  $("#addUserPopup").modal("show");
});


$(document).on("click", '.resetPasswordevt', function(e) {

	 var userId = $(this).attr("userId");
	 var campusId = $(this).attr("campusId");

	 $("#pwdUserId").val(userId);
	 $("#campusIdHidden").val(campusId);
	$('#resetPwdModal').modal({
	    backdrop: 'static',
	    keyboard: false
	});
	$("#resetPwdModal").modal("show");

	});


$(document).on("click", '.btnClosePwdModal', function(e) {
	clearpwdMesg();
});


$(document).on("click", '.deleteUserEvent', function(e) {

 var userId = $(this).attr("userid");
 var userName = $(this).attr("username");

 $("#hdfLoginId").val(userId);
 $("#delUserName").html(userName);
 
$('#confirmDeletePopupModal').modal({
    backdrop: 'static',
    keyboard: false
});
$("#confirmDeletePopupModal").modal("show");

});
$(document).on("click", '#btnDeleteConfirmEmployee', function(e) {
   deleteUser();
});

$(document).on("click", '#btnSaveUser', function(e) {
	e.preventDefault();

	/*alert(moment($("#account_dob").val(),'DD-MMM-YYYY').format('YYYY-MM-DD'));
	
	alert("Choice : "+$('input[name="gender"]:checked').val()+" && Gender : "+ $("#account_gender").val()+" && DOB: "+$("#account_dob").val()+" && Address : "+$("#account_address").val()+" && pic name : "+$("#uploadPic").val());
	*/
	if($('#userform').validate().form()){

	var users={};
	users.loginId=$("#hdfUserId").val();
	users.userName=$("#account_user_name").val();
	users.password=$("#upassword").val();
	users.campusId=$("#ddlCampus").val();
	users.status=$("#ddlStatus").val();
	users.type=$("#ddlUserType").val();
	SaveUser(users);
  }
});

$(document).on("click", '.btnClose', function(e) {
	clearmsg();
});

function SaveUser(users) {
	 $.ajax({
	 	contentType : "application/json",
		processData : false,
		data:JSON.stringify(users),
		async : false,
		url : "saveUser.json",
		type : "POST",
		dataType: "json",
        cache: false,
        async:false,
		success : function(response) { 
			clearmsg();
			if(response.status==1){
				$("#addUserPopup").modal("hide");
				clear();
				getUserList();
				toastr.success(response.message);
			}
			else{
					clearmsg();					
					toastr.error(response.message);
			}
		}
   });
}
function deleteUser(){
	var userId=$("#hdfLoginId").val();
	$.ajax({
		url : "deleteUser.json",
		data: {userId:userId},
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
				getUserList();
				$("#confirmDeletePopupModal").modal("hide");
			}
			else{
				toastr.error("Error Message : "+response.message);
			}
		}
	  });
}

function clearmsg(){
	$("#userform" ).validate().resetForm();
}


function clearpwdMesg(){
	$("#pwd-form" ).validate().resetForm();
}

function clear(){
	$("#hdfUserId").val("");
	$("#loginName").val("");
	$("#password").val("");
	$("#cpassword").val("");
	$("#campusId").val("");
	$("#empCode").val("");
	$("#ddlUserType option:selected").removeAttr("selected");
	$("#ddlStatus option:selected").removeAttr("selected");
	$("#ddlStatus option[value='A']").attr("selected", "selected");
	$("#ddlUserType option[value='true']").attr("selected", "selected");
}