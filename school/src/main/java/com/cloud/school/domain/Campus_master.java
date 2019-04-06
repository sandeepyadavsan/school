package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="campus_master")
public class Campus_master implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long campus_id;
	private String campus_name;
	private String campus_address;
	private Long pincode;
	private String campus_phone;
	private String campus_logo;
	private String pushurl;
	private String status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "campus_id", unique = true, nullable = false)
	public Long getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(Long campus_id) {
		this.campus_id = campus_id;
	}

	@Column(name="campus_name")
	public String getCampus_name() {
		return campus_name;
	}

	public void setCampus_name(String campus_name) {
		this.campus_name = campus_name;
	}
	@Column(name="campus_address")
	public String getCampus_address() {
		return campus_address;
	}

	public void setCampus_address(String campus_address) {
		this.campus_address = campus_address;
	}
	@Column(name="pincode")
	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}
	@Column(name="campus_phone")
	public String getCampus_phone() {
		return campus_phone;
	}

	public void setCampus_phone(String campus_phone) {
		this.campus_phone = campus_phone;
	}
	@Column(name="campus_logo")
	public String getCampus_logo() {
		return campus_logo;
	}

	public void setCampus_logo(String campus_logo) {
		this.campus_logo = campus_logo;
	}
	@Column(name="pushurl")
	public String getPushurl() {
		return pushurl;
	}

	public void setPushurl(String pushurl) {
		this.pushurl = pushurl;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public Campus_master(Long campus_id,String campus_name,String campus_address,Long pincode,String campus_phone,String campus_logo,String pushurl,String status) {
		super();
		 this.campus_id=campus_id;
		this.campus_name=campus_name;
		this.campus_address=campus_address;
		this.pincode=pincode;
		this.campus_phone=campus_phone;
		this.campus_logo=campus_logo;
		this.setPushurl(pushurl);
		this.status=status;
		
	}

	public Campus_master() {
		// TODO Auto-generated constructor stub
	}

	

}
