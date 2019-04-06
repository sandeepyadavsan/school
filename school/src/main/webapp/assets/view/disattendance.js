$(function(){
	
	$("#attendanceManagementMenuId").addClass("active");
	
	$('#c_date').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     maxDate : moment(),
	     showToday: false
	});
	
	$('#c_date').val(moment().format('DD-MMM-YYYY'));
	
	$("#c_date").on("dp.change",function (e) {
	    $('#status').focus();
	 });
	
	$('#month_date').val(moment().format('MMM-YYYY'));
	
	$('#month_date').datetimepicker({
	     pickTime: false,
	     format: "MMM-YYYY",
	     showToday: false,
	     maxDate : moment(),
	     viewMode: "months", 
	     minViewMode: 1
	});

	$("#month_date").on("dp.change",function (e) {
		$('#month_date').val(moment(e.date).format('MMM-YYYY'));
	    $('#search').focus();
	 });
	
	getSearchedStudent();
	
});

$(document).on("click","#search",function(e){
	e.preventDefault();
   getSearchedStudent();
});


$(document).on("click","#btnUpdate",function(e){
	e.preventDefault();
	
	var attendanceId = $("#hdfId").val();
	var logDate = moment($("#c_date").val(),'DD-MMM-YYYY').format('YYYY-MM-DD');
	var status = $("#status").val();
	
	$.ajax({
		url : "updateAttendanceById.json",
		data: {attendanceId:attendanceId, logDate:logDate, status:status},
	    contentType: "application/json",
	    crossDomain: true,
	    processData: true,
	    type: 'GET',
	    dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) { 
			if(response.status==1){
				$("#hdfId").val("");
				getSearchedStudent();
				$("#updatePopupModal").modal("hide");
				resetAll();
				toastr.success("Updated successfully.");
			}
			else{
				toastr.error(response.message);
			}
		}
	  });
	
});

$(document).on("click",".update",function(e){
	
	$("#hdfId").val($(this).attr('attendanceId'));
	$("#std_name").val($(this).attr('sname'));
	
	$('#updatePopupModal').modal({
	    backdrop: 'static',
	    keyboard: false
	});
	$("#updatePopupModal").modal("show");
	
});

$(document).on("change","#ddlClass",function(e){
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
				$("#ddlSection").append("<option value=''>-Select-</option>");
				if(response.data.length>0)
					{
				$.each(response.data,function(indx,ddl){
					$("#ddlSection").append("<option value='"+ddl.sectioid+"'>"+ddl.sectionname+"</option>");
				});
					}
				else
					{
					
					$("#ddlSection").append("<option value='1'>A</option>");
					
					}
				
			}
			else{
				
			}
		}
	  });
});


function getSearchedStudent()
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

	            if($("#ddlClass").val()!=null && $("#ddlClass").val() !="")
	            o.classId = $("#ddlClass").val();
	            else
	            	o.classId = 0;
	            
	            if($("#ddlSection").val()!=null && $("#ddlSection").val() !="")
	           	o.sectionId = $("#ddlSection").val();
	            else
	            	o.sectionId = 0;
	            
	           	o.day = 0;
	           	if($("#month_date").val()!=null && $("#month_date").val()!="")
	           	{
	           	o.month = moment($("#month_date").val(),'MMM-YYYY').format('MM')*1;
	           	o.year = moment($("#month_date").val(),'MMM-YYYY').format('YYYY');
	           	}
	           	else
	           	{
	           		o.month = 0;
	           		o.year = 0;
	           	}		
	           	
	        	if($("#form_number").val()!=null && $("#form_number").val()!="")
	           	{
	        	o.formNumber = $("#form_number").val();
	           	}
	           	else
	           	{
	           	o.formNumber = "";
	           	}
	        	
	        	if($("#student_name").val()!=null && $("#student_name").val()!="")
	           	{
	        	o.studentName = $("#student_name").val();
	           	}
	           	else
	           	{
	           	o.studentName = "";
	           	}
	           	

	        }
	        else {
	            o[idx] = obj;
	        }
	    });
	   
	    return JSON.stringify(o);
	};

	
	$("#attstudentTable").dataTable({
		 "oLanguage": {
		        "sEmptyTable": "Record not found for this query."
		    },
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": false,
        "lengthChange": true,
        "sAjaxSource": "getSearchedStudent.json",
        "aoColumns":[
                {"sName": "Form No","mData":"form_number","bSortable": false,  "bSearchable": false },
                {"sName": "Student Name","mData":"student_name","bSortable": false,  "bSearchable": false },
                {"sName": "1","mData":"1","bSortable": false,  "bSearchable": false, "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "2","mData":"2","bSortable": false,  "bSearchable": false, "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "3","mData":"3","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "4","mData":"4","bSortable": false,  "bSearchable": false, "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "5","mData":"5","bSortable": false,  "bSearchable": false, "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "6","mData":"6","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "7","mData":"7","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "8","mData":"8","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "9","mData":"9","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "10","mData":"10","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "11","mData":"11","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "12","mData":"12","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "13","mData":"13","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "14","mData":"14","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "15","mData":"15","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "16","mData":"16","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "17","mData":"17","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "18","mData":"18","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "19","mData":"19","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "20","mData":"20","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "21","mData":"21","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "22","mData":"22","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "23","mData":"23","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "24","mData":"24","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "25","mData":"25","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "26","mData":"26","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "27","mData":"27","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "28","mData":"28","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "29","mData":"29","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "30","mData":"30","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "31","mData":"31","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                	if(data!=null)return data;else return "";
                } },
                {"sName": "Total Abs","mData":"id","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                   var totalAbs = calculateTotalAbs(full);
                	return totalAbs;
                } },
                {"sName": "Action","mData":"id","bSortable": false,  "bSearchable": false , "mRender": function (data, type, full) {
                 	return "<button class='btn btn-xs btn-primary update' attendanceId='"+data+"' sname='"+full.student_name+"' >Edit</button>";
                 } },
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
       /* dom: 'Bfrtip',
        buttons: [
             'csv', 'excel', 'pdf'
        ]*/
    });
	
	$("#attstudentTable_filter").addClass("hide");
}

function resetAll()
{

	$('#c_date').val(moment().format('DD-MMM-YYYY'));
	$("#status option:selected").removeAttr("selected");
	$("#status option[value=A]").attr("selected", "selected");
	
}

function calculateTotalAbs(data)
{
	var count = 0;
	if(data["1"]=="A")
	count++;	
	if(data["2"]=="A")
		count++;	
	if(data["3"]=="A")
		count++;	
	if(data["4"]=="A")
		count++;	
	if(data["5"]=="A")
		count++;	
	if(data["6"]=="A")
		count++;	
	if(data["7"]=="A")
		count++;	
	if(data["8"]=="A")
		count++;	
	if(data["9"]=="A")
		count++;	
	if(data["10"]=="A")
		count++;	
	if(data["11"]=="A")
		count++;	
	if(data["12"]=="A")
		count++;	
	if(data["13"]=="A")
		count++;	
	if(data["14"]=="A")
		count++;	
	if(data["15"]=="A")
		count++;	
	if(data["16"]=="A")
		count++;	
	if(data["17"]=="A")
		count++;	
	if(data["18"]=="A")
		count++;	
	if(data["19"]=="A")
		count++;	
	if(data["20"]=="A")
		count++;	
	if(data["21"]=="A")
		count++;	
	if(data["22"]=="A")
		count++;	
	if(data["23"]=="A")
		count++;	
	if(data["24"]=="A")
		count++;	
	if(data["25"]=="A")
		count++;	
	if(data["26"]=="A")
		count++;	
	if(data["27"]=="A")
		count++;	
	if(data["28"]=="A")
		count++;	
	if(data["29"]=="A")
		count++;	
	if(data["30"]=="A")
		count++;	
	if(data["31"]=="A")
		count++;	
	return count;
	
}
