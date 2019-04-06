package com.cloud.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.CampusDao;
import com.cloud.school.dao.StudentDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Campus_master;
import com.cloud.school.service.CampusService;

@Service
@Transactional(readOnly=true)
public class CampusServiceImpl implements CampusService{
	@Autowired
	CampusDao dao;

	public CampusServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	@Transactional(readOnly=false)
	public Campus_master saveCampusOrUpdate(Campus_master campus_master) {
		// TODO Auto-generated method stub
		return dao.saveCampusOrUpdate( campus_master);
	}
	@Transactional(readOnly=true)
	public DataTables<Object> getAllCampus(DataTablesRequest dataTablesRequest) {
		// TODO Auto-generated method stub
		return dao.getAllCampus( dataTablesRequest);
	}
	
	@Transactional(readOnly=false)
	public String deleteCampus(Long campusId) {
		// TODO Auto-generated method stub
		return dao.deleteCampus(campusId);
	}

}
