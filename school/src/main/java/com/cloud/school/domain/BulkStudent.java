package com.cloud.school.domain;

public class BulkStudent {
	
	private Long ClassId;
	private Long SectionId;
	private String StudentName;
	private String FatherName;
	private String Gender;
	private String DateOfBirth;
	private String DateOfAdmission;
	private String MessageMobile;
	private String EmailId;
	private String FormNo;

	public Long getClassId() {
		return ClassId;
	}

	public void setClassId(Long classId) {
		ClassId = classId;
	}

	public Long getSectionId() {
		return SectionId;
	}

	public void setSectionId(Long sectionId) {
		SectionId = sectionId;
	}

	public String getStudentName() {
		return StudentName;
	}

	public void setStudentName(String studentName) {
		StudentName = studentName;
	}

	public String getFatherName() {
		return FatherName;
	}

	public void setFatherName(String fatherName) {
		FatherName = fatherName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getDateOfAdmission() {
		return DateOfAdmission;
	}

	public void setDateOfAdmission(String dateOfAdmission) {
		DateOfAdmission = dateOfAdmission;
	}

	public String getMessageMobile() {
		return MessageMobile;
	}

	public void setMessageMobile(String messageMobile) {
		MessageMobile = messageMobile;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public String getFormNo() {
		return FormNo;
	}

	public void setFormNo(String formNo) {
		FormNo = formNo;
	}

	public BulkStudent() {
		// TODO Auto-generated constructor stub
	}

}
