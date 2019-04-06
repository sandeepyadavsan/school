package com.cloud.school.domain;

import java.util.List;

public class StudentAttendance {
	private String takenBy;
	private Long campusId;
	private Long classId;
	private Long sectionId;
	private String date;
	private List<StudentList> studentList;
	
	
	public String getTakenBy() {
		return takenBy;
	}
	public void setTakenBy(String takenBy) {
		this.takenBy = takenBy;
	}
	public Long getCampusId() {
		return campusId;
	}
	public void setCampusId(Long campusId) {
		this.campusId = campusId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<StudentList> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentList> studentList) {
		this.studentList = studentList;
	}
	
	
}
