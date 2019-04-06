package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="event_master")
public class Event_master implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long event_id;
	private Long campus_id;
	private String event_name;
	private String create_date;
	private String event_status;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "event_id", unique = true, nullable = false)
	public Long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}
	@Column(name="campus_id")
	public Long getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(Long campus_id) {
		this.campus_id = campus_id;
	}
	@Column(name="event_name")
	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	@Column(name="create_date")
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	@Column(name="event_status")
	public String getEvent_status() {
		return event_status;
	}

	public void setEvent_status(String event_status) {
		this.event_status = event_status;
	}

	

	public Event_master(Long event_id, Long campus_id, String event_name, String create_date, String event_status) {
		// TODO Auto-generated constructor stub
		super();
		
		 this.event_id = event_id;
		  this.campus_id = campus_id;
		  this.event_name = event_name;
		  this.create_date = create_date;
		  this.event_status = event_status;
	}
	
	public Event_master(){}

}
