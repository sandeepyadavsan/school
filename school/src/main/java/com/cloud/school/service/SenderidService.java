package com.cloud.school.service;

import java.util.List;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Senderid;

public interface SenderidService {
	
	Senderid saveSenderidOrUpdate(Senderid senderid);

	DataTables<Object> getAllSenderid(DataTablesRequest dataTablesRequest);

	String deleteSenderid(Long pkid, Long campusId);

	List<?> getAllSenderidByCampusId(Long campusId);

}
