package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messageconfig")
public class Messageconfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String classSectionIds;
	private String studentids;
	private String type;
	private Long eventid;
	private Long templateid;
	private String message;
	private String configdate;
	private String configstatus;
	private Long campusId;
	private String senderid;
	private String schedule;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="classSectionIds")
	public String getClassSectionIds() {
		return classSectionIds;
	}

	public void setClassSectionIds(String classSectionIds) {
		this.classSectionIds = classSectionIds;
	}
	
	
	@Column(name="studentids")
	public String getStudentids() {
		return studentids;
	}


	public void setStudentids(String studentids) {
		this.studentids = studentids;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="eventid")
	public Long getEventid() {
		return eventid;
	}

	public void setEventid(Long eventid) {
		this.eventid = eventid;
	}
	
	@Column(name="templateid")
	public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}
	
	@Column(name="message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Column(name="configdate")
	public String getConfigdate() {
		return configdate;
	}

	public void setConfigdate(String configdate) {
		this.configdate = configdate;
	}
	@Column(name="configstatus")
	public String getConfigstatus() {
		return configstatus;
	}

	public void setConfigstatus(String configstatus) {
		this.configstatus = configstatus;
	}

	
	@Column(name="campusid")
	public Long getCampusId() {
		return campusId;
	}

	public void setCampusId(Long campusId) {
		this.campusId = campusId;
	}
	@Column(name="senderid")
	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	@Column(name="schedule")
	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Messageconfig(Long id,String classSectionIds,String studentids,String type,Long eventid,String message,String configdate,String configstatus,String senderid,String schedule ){
		super();
		
		this.id = id;
		this.classSectionIds = classSectionIds;
		 this.studentids = studentids;
		 this.type = type;
		this.eventid = eventid;
		 this.message = message;
		 this.configdate = configdate;
		 this.configstatus = configstatus;
		 this.schedule = schedule;
		 this.senderid = senderid;
	
	}
	public Messageconfig() {
		// TODO Auto-generated constructor stub
	}

	

}
