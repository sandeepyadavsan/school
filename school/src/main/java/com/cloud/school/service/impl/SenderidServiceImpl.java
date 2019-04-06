package com.cloud.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.SenderidDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Senderid;
import com.cloud.school.service.SenderidService;

@Service
@Transactional(readOnly=true)
public class SenderidServiceImpl implements SenderidService{

	@Autowired
	SenderidDao dao;
	public SenderidServiceImpl(){
		// TODO Auto-generated constructor stub
	}
	@Transactional(readOnly=false)
	public Senderid saveSenderidOrUpdate(Senderid senderid) {
		// TODO Auto-generated method stub
		return dao.saveSenderidOrUpdate( senderid);
	}
	@Transactional(readOnly=true)
	public DataTables<Object> getAllSenderid(DataTablesRequest dataTablesRequest) {
		// TODO Auto-generated method stub
		return dao.getAllSenderid( dataTablesRequest);
	}
	@Transactional(readOnly=false)
	public String deleteSenderid(Long pkid, Long campusId) {
		// TODO Auto-generated method stub
		return dao.deleteSenderid( pkid,  campusId);
	}
	@Transactional(readOnly=true)
	public List<?> getAllSenderidByCampusId(Long campusId) {
		// TODO Auto-generated method stub
		return dao.getAllSenderidByCampusId(campusId);
	}

}
