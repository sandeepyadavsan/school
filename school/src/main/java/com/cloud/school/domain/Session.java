package com.cloud.school.domain;

import com.cloud.school.domain.Users;

public class Session {
	private Users user;
	private String serviceDate;
	private String department;
	private Long activeAppId;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getActiveAppId() {
		return activeAppId;
	}

	public void setActiveAppId(Long activeAppId) {
		this.activeAppId = activeAppId;

	}

	@Override
	public String toString() {
		return "Session [user=" + user + ", serviceDate=" + serviceDate + ", department=" + department
				+ ", activeAppId=" + activeAppId + "]";
	}
	
	
}
