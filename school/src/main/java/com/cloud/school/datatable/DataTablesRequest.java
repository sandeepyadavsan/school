package com.cloud.school.datatable;

import java.io.Serializable;
import java.util.List;

public class DataTablesRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Boolean> abRegex;
	public List<Boolean> abSearchable;
	public List<Boolean> abSortable;
	public List<Integer> aiSortCol;
	public List<String> amDataProp;
	public List<String> asSearch;
	public List<String> asSortDir;

	public boolean bRegex;
	public int iColumns;
	public int iDisplayLength;
	public int iDisplayStart;
	public int iSortingCols;
	public String sColumns;
	public String sEcho;
	public String sSearch;
	
	public Long campusId;
	public Long classId;
	public Long sectionId;

	public String day;
	public String month;
	public String year;
	
	public String formNumber;
	public String studentName;

	
	
	
}