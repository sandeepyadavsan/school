package com.cloud.school.dao;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Campus_master;

public interface CampusDao {

	Campus_master saveCampusOrUpdate(Campus_master campus_master);

	DataTables<Object> getAllCampus(DataTablesRequest dataTablesRequest);

	String deleteCampus(Long campusId);

}
