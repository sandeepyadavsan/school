var allList = [];
$(function(){
	
	$("#attendanceManagementMenuId").addClass("active");
	
	/*$('#DateDemo').datetimepicker({
	     pickTime: false,
	     format: 'DD-MMM-YYYY',
	     showToday: false
	});
*/
	$("#c_date").val(moment().format('DD-MMM-YYYY'));
	
});

$(document).on("click","#btnSaveAttendance",function(e){
	
	//console.log(allList);
	if($('#attendanceform').validate().form()){
	var attendanceObj = {};
	
	attendanceObj.takenBy= $("#takenby").val();
	attendanceObj.campusId=0;
	attendanceObj.classId=$("#ddlClass").val();
	attendanceObj.sectionId=$("#ddlSection").val();
	attendanceObj.date=moment($("#c_date").val(),'DD-MMM-YYYY').format('YYYY-MM-DD');
	
	var studentListArray = [];
	
	$.each(allList,function(indx,data){
		
		var attendance_sts = $('input[name="attendance'+data.student_id+'"]:checked').val();
		if(attendance_sts=="A")
		{
	    var studentAttendanceObj = {};
		studentAttendanceObj.attendanceId=data.id;
		studentAttendanceObj.studentId=data.student_id;
		studentAttendanceObj.studentName=data.student_name;
		studentAttendanceObj.rollNo=data.student_rollno;
		studentAttendanceObj.formNo=data.form_number;
		studentAttendanceObj.attendanceStatus=attendance_sts;
		studentListArray.push(studentAttendanceObj);
		}
	});
	
	
	attendanceObj.studentList = studentListArray;
	
	$.ajax({
	 	contentType : "application/json",
		processData : false,
		data:JSON.stringify(attendanceObj),
		async : false,
		url : "studentAttendanceUpdate.json",
		type : "POST",
		dataType: "json",
	    cache: false,
	    async:false,
		success : function(response) {
			toastr.success("Attendance Saved Successfully.");
			getStudentList();
		}
	});
	}
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

            o.classId = $("#ddlClass").val(); 
           	o.sectionId = $("#ddlSection").val();
            o.day = moment($("#c_date").val(),'DD-MMM-YYYY').format('DD');
           	o.month = moment($("#c_date").val(),'DD-MMM-YYYY').format('MM')*1;
           	o.year = moment($("#c_date").val(),'DD-MMM-YYYY').format('YYYY');

        }
        else {
            o[idx] = obj;
        }
    });
   
    return JSON.stringify(o);
};

function getStudentList()
{
	allList = [];
	$("#attstudentTable").dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "iDisplayLength": 10,
        "bDestroy": true,
        "displayStart": 0,
        "bPaginate": false,
        "lengthChange": true,
        "sAjaxSource": "getAllStudentOfClass.json",
        "aoColumns":[
                        {"sName": "S. No.","mData":"student_id","bSortable": false, "bSearchable": false,  "mRender": function (data, type, full) {
                        	indx++;
                            return indx;
                        },"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                            allList.push(oData);
                        }
                    },
                     
                {"sName": "Form No","mData":"form_number","bSortable": false,  "bSearchable": false },
                
                {"sName": "Roll No","mData":"student_rollno","bSortable": false,  "bSearchable": false },
                
                {"sName": "Name","mData":"student_name","bSortable": false,  "bSearchable": false },
                
                        {"sName": "Status",
	                          "mData": "student_id",
	                            "bSearchable": false,
	                            "sClass":"text-center",
	                            "bSortable": false,
	                            "mRender": function (data, type, full) {
	                            	var htmlStatus = '';
	                            	htmlStatus = '<label class="radio-inline c-radio"><input type="radio" name="attendance'+data+'" id="present'+data+'" value="P" checked><span class="fa fa-circle"></span>Present</label>';
	     							htmlStatus += ' <label class="radio-inline c-radio"><input type="radio" name="attendance'+data+'" id="absent'+data+'" value="A"> <span class="fa fa-circle"></span>Absent </label>';
	                            	return htmlStatus;
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
	
	$("#attstudentTable_filter").addClass("hide");
}

$(document).on("change","#ddlSection",function(e){
	getStudentList();
});