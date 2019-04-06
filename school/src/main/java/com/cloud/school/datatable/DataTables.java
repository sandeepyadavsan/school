package com.cloud.school.datatable;

import java.util.ArrayList;
import java.util.List;

public class DataTables<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	//@JsonProperty(value = "iTotalRecords")
	public int iTotalRecords;

	//@JsonProperty(value = "iTotalDisplayRecords")
	public int iTotalDisplayRecords;

	//@JsonProperty(value = "sEcho")
	public String sEcho;

	//@JsonProperty(value = "sColumns")
	public String sColumns;

	//@JsonProperty(value = "aaData")
	public List<T> aaData = new ArrayList<T>();

	public String message;
	public Integer status;
	/*
	 * public int getStatus() { return status; }
	 * 
	 * public void setStatus(int status) { this.status = status; }
	 * 
	 * public String getMessage() { return message; }
	 * 
	 * public void setMessage(String message) { this.message = message; }
	 */

}
