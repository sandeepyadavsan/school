package com.cloud.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.AttendanceDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.StudentAttendance;
import com.cloud.school.service.AttendanceService;

@Service
@Transactional(readOnly=true)
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceDao dao;
	
	@Transactional(readOnly=true)
	public DataTables<Object> getAllStudentOfClass(
			DataTablesRequest dataTablesRequest) {
		return dao.getAllStudentOfClass(dataTablesRequest);
	}

	@Transactional(readOnly=false)
	public int studentAttendanceUpdate(StudentAttendance studentAttendance) {
		return dao.studentAttendanceUpdate(studentAttendance);
	}

	@Transactional(readOnly=true)
	public DataTables<Object> getSearchedStudent(DataTablesRequest dataTablesRequest) {
		return dao.getSearchedStudent(dataTablesRequest);
	}

	@Transactional(readOnly=false)
	public int updateAttendanceById(Long campusId, Long attendanceId, String logDate, String status) {
		return dao.updateAttendanceById(campusId, attendanceId, logDate, status);
	}

}
