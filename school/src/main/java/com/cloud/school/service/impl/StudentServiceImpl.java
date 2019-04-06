package com.cloud.school.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.StudentDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.BulkStudent;
import com.cloud.school.domain.Student_records;
import com.cloud.school.service.StudentService;

@Service
@Transactional(readOnly=true)
public class StudentServiceImpl implements StudentService{

	@Autowired
	StudentDao dao;
	
	@Transactional(readOnly=true)
	public DataTables<Object> getAllStudentOfCampus(
			DataTablesRequest dataTablesRequest) {
		return dao.getAllStudentOfCampus(
				 dataTablesRequest);
	}
	@Transactional(readOnly=false)
	public Student_records saveStudentOrUpdate(Student_records student_records) {
		// TODO Auto-generated method stub
		return dao.saveStudentOrUpdate(student_records);
	}
	@Transactional(readOnly=false)
	public String deleteStudent(Long studentId, Long campusId) {
		// TODO Auto-generated method stub
		return dao.deleteStudent( studentId,  campusId);
	}
	
	@Transactional(readOnly=true)
	public Student_records getStudentById(Long studentId, Long campusId) {
		return dao.getStudentById(studentId, campusId);
	}
	
	@Transactional(readOnly=true)
	public List<?> getRollNoFormNobyCampusId(Long campusId, Long classId,
			Long sectionId) {
		return dao.getRollNoFormNobyCampusId( campusId,  classId,
				 sectionId);
	}
	
	@Transactional(readOnly=false)
	public int inserBulkStudentRecords(List<BulkStudent> lst, Long campusId) {
		// TODO Auto-generated method stub
		return dao.inserBulkStudentRecords( lst,  campusId);
	}
	
	@Transactional(readOnly=true)
	public List<Student_records> getExistingFormNumbers(Long campusId) {
		// TODO Auto-generated method stub
		return dao.getExistingFormNumbers(campusId);
	}

	
}
