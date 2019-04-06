package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="templates")
public class Template implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long campus_id;
	private long event_id;
	private String template;
	private String templatename;
	private String create_date;
	private String status;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column(name="campus_id")
	public long getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(long campus_id) {
		this.campus_id = campus_id;
	}
	@Column(name="event_id")
	public long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(long event_id) {
		this.event_id = event_id;
	}
	@Column(name="template")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	@Column(name="templatename")
	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
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

	

	public Template() {
		// TODO Auto-generated constructor stub
	}

}
