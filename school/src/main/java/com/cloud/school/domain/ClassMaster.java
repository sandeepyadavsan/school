package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="class_master")
public class ClassMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long classId;
	private Long campusId;
	private String className;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "classid", unique = true, nullable = false)
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
	@Column(name="campus_id")
	public Long getCampusId() {
		return campusId;
	}
	public void setCampusId(Long campusId) {
		this.campusId = campusId;
	}
	
	@Column(name="className")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public ClassMaster(Long classId, Long campusId, String className) {
		super();
		this.classId = classId;
		this.campusId = campusId;
		this.className = className;
	}
	
	
	public ClassMaster(){}
	
	
}
