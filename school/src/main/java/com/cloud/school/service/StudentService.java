package com.cloud.school.service;

import java.util.List;
import java.util.Map;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.BulkStudent;
import com.cloud.school.domain.Student_records;

public interface StudentService {

	DataTables<Object> getAllStudentOfCampus(DataTablesRequest dataTablesRequest);
	Student_records saveStudentOrUpdate(Student_records student_records);
	String deleteStudent(Long studentId, Long campusId);
	Student_records getStudentById(Long studentId, Long campusId);
	List<?> getRollNoFormNobyCampusId(Long campusId, Long classId,
			Long sectionId);
	int inserBulkStudentRecords(List<BulkStudent> lst, Long campusId);
	List<Student_records> getExistingFormNumbers(Long campusId);
}
