package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student_records")
public class Student_records implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long student_id;
	private Long campus_id;
	private Long classid;
	private Long sectionid;
	private Long form_number;
	private Long student_rollno;
	private String student_name;
	private String student_father;
	private String student_mother;
	private String student_dob;
	private String date_of_admission;
	private String student_gender;
	private String student_caste;
	private String student_current_address;
	private String student_home_address;
	private String student_email;
	private String student_picture;
	private String message_mobile_number;
	private Long session_id;
	private String student_status;
	private String create_date;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "student_id", unique = true, nullable = false)
	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}
	@Column(name="campus_id")
	public Long getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(Long campus_id) {
		this.campus_id = campus_id;
	}
	@Column(name="classid")
	public Long getClassid() {
		return classid;
	}

	public void setClassid(Long classid) {
		this.classid = classid;
	}
	@Column(name="sectionid")
	public Long getSectionid() {
		return sectionid;
	}

	public void setSectionid(Long sectionid) {
		this.sectionid = sectionid;
	}
	@Column(name="student_rollno")
	public Long getStudent_rollno() {
		return student_rollno;
	}

	public void setStudent_rollno(Long student_rollno) {
		this.student_rollno = student_rollno;
	}
	@Column(name="student_name")
	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	@Column(name="student_father")
	public String getStudent_father() {
		return student_father;
	}

	public void setStudent_father(String student_father) {
		this.student_father = student_father;
	}
	@Column(name="student_mother")
	public String getStudent_mother() {
		return student_mother;
	}

	public void setStudent_mother(String student_mother) {
		this.student_mother = student_mother;
	}
	@Column(name="student_dob")
	public String getStudent_dob() {
		return student_dob;
	}

	public void setStudent_dob(String student_dob) {
		this.student_dob = student_dob;
	}
	@Column(name="date_of_admission")
	public String getDate_of_admission() {
		return date_of_admission;
	}

	public void setDate_of_admission(String date_of_admission) {
		this.date_of_admission = date_of_admission;
	}
	@Column(name="student_gender")
	public String getStudent_gender() {
		return student_gender;
	}

	public void setStudent_gender(String student_gender) {
		this.student_gender = student_gender;
	}
	@Column(name="student_caste")
	public String getStudent_caste() {
		return student_caste;
	}

	public void setStudent_caste(String student_caste) {
		this.student_caste = student_caste;
	}
	@Column(name="student_current_address")
	public String getStudent_current_address() {
		return student_current_address;
	}

	public void setStudent_current_address(String student_current_address) {
		this.student_current_address = student_current_address;
	}
	@Column(name="student_home_address")
	public String getStudent_home_address() {
		return student_home_address;
	}

	public void setStudent_home_address(String student_home_address) {
		this.student_home_address = student_home_address;
	}
	@Column(name="student_email")
	public String getStudent_email() {
		return student_email;
	}

	public void setStudent_email(String student_email) {
		this.student_email = student_email;
	}
	@Column(name="student_picture")
	public String getStudent_picture() {
		return student_picture;
	}

	public void setStudent_picture(String student_picture) {
		this.student_picture = student_picture;
	}
	@Column(name="message_mobile_number")
	public String getMessage_mobile_number() {
		return message_mobile_number;
	}

	public void setMessage_mobile_number(String message_mobile_number) {
		this.message_mobile_number = message_mobile_number;
	}
	@Column(name="session_id")
	public Long getSession_id() {
		return session_id;
	}

	public void setSession_id(Long session_id) {
		this.session_id = session_id;
	}
	@Column(name="student_status")
	public String getStudent_status() {
		return student_status;
	}

	public void setStudent_status(String student_status) {
		this.student_status = student_status;
	}
	@Column(name="form_number")
	public Long getForm_number() {
		return form_number;
	}

	public void setForm_number(Long form_number) {
		this.form_number = form_number;
	}
	@Column(name="create_date")
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Student_records( Long student_id,Long campus_id,Long classid,Long sectionid,Long student_rollno,String student_name,String student_father,String student_mother,String student_dob,String date_of_admission,String student_gender,String student_caste,String student_current_address,String student_home_address,String student_email,String student_picture,String message_mobile_number,Long session_id,String student_status) {
		super();
						this.student_id=student_id;
				 		this.campus_id=campus_id;
				 		this.classid=classid;
				 		this.sectionid=sectionid;
				 		this.student_rollno=student_rollno;
				 		this.student_name=student_name;
						 this.student_father=student_father;
						 this.student_mother=student_mother;
						 this.student_dob=student_dob;
						 this.date_of_admission=date_of_admission;
						 this.student_gender=student_gender;
						 this.student_caste=student_caste;
						 this.student_current_address=student_current_address;
						 this.student_home_address=student_home_address;
						 this.student_email=student_email;
						 this.student_picture=student_picture;
						 this.message_mobile_number=message_mobile_number;
						 this.session_id=session_id;
						 this.student_status=student_status;
	}

	public Student_records() {
		// TODO Auto-generated constructor stub
	}

	

	

}
