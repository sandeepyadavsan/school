package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messagehistory")
public class Messagehistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String message;
	private String mobile_no;
	private String create_date;
	private String status;
	private String sent_time;
	private Long campusId;
	private String senderid;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name="mobile_no")
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	@Column(name="create_date")
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="sent_time")
	public String getSent_time() {
		return sent_time;
	}
	public void setSent_time(String sent_time) {
		this.sent_time = sent_time;
	}
	
	@Column(name="campusId")
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
	public Messagehistory(Long id, String message, String mobile_no, String create_date, String status,
			String sent_time, Long campusId, String senderid) {
		super();
		this.id = id;
		this.message = message;
		this.mobile_no = mobile_no;
		this.create_date = create_date;
		this.status = status;
		this.sent_time = sent_time;
		this.campusId = campusId;
		this.senderid = senderid;
	}
	
	public Messagehistory() {
		
	}
	
	
}
