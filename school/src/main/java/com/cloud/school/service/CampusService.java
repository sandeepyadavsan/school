package com.cloud.school.service;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Campus_master;
import com.cloud.school.domain.Student_records;

public interface CampusService {
	
	Campus_master saveCampusOrUpdate(Campus_master campus_master);

	DataTables<Object> getAllCampus(DataTablesRequest dataTablesRequest);

	String deleteCampus(Long campusId);

}
