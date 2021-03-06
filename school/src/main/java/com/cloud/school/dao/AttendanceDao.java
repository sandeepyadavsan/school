package com.cloud.school.dao;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.StudentAttendance;

public interface AttendanceDao {

	DataTables<Object> getAllStudentOfClass(DataTablesRequest dataTablesRequest);

	int studentAttendanceUpdate(StudentAttendance studentAttendance);

	DataTables<Object> getSearchedStudent(DataTablesRequest dataTablesRequest);

	int updateAttendanceById(Long campusId, Long attendanceId, String logDate, String status);
}
