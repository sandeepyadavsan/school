package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="senderid")
public class Senderid implements Serializable{
	private static final long serialVersionUID = 1L;
	private long pkid;
	private long campus_id;
	private String sender_id;
	private String status;
	private String create_date;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "pkid", unique = true, nullable = false)
	public long getPkid() {
		return pkid;
	}

	public void setPkid(long pkid) {
		this.pkid = pkid;
	}
	@Column(name="campus_id")
	public long getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(long campus_id) {
		this.campus_id = campus_id;
	}
	@Column(name="sender_id")
	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="create_date")
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public Senderid(long pkid,long campus_id,String sender_id,String status,String create_date) {
		super();
		this.pkid=pkid;
		this.campus_id=campus_id;
		this.sender_id=sender_id;
		this.status=status;
		this.create_date=create_date;
		
	}
	
	public Senderid() {
		// TODO Auto-generated constructor stub
		
	}

}
