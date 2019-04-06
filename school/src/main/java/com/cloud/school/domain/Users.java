package com.cloud.school.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "login_id"),
		@UniqueConstraint(columnNames = "user_name") })
public class Users implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long loginId;
	private String userName;
	private String password;
	private String status;
	private Long campusId;
	private String type;
	private String empCode;
	private String confirmPassword;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "login_id", unique = true, nullable = false)
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "user_name", unique = true, nullable = false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "campus_id")
	public Long getCampusId() {
		return campusId;
	}
	public void setCampusId(Long campusId) {
		this.campusId = campusId;
	}
	
    @Column(name="type")
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	 
    
	@Column(name="emp_code")
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Users()
	{}
	
	public Users(Long loginId, String userName, String password, String status, Long campusId, String type,
			String empCode) {
		super();
		this.loginId = loginId;
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.campusId = campusId;
		this.type = type;
		this.empCode = empCode;
	}
	
	@Override
	public String toString() {
		return "Users [loginId=" + loginId + ", userName=" + userName + ", password=" + password + ", status="
				+ status + ", campusId=" + campusId + ", type=" + type + ", empCode=" + empCode + "]";
	}
	
	
	
	
	
	

}
