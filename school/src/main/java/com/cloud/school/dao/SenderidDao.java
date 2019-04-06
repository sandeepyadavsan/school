package com.cloud.school.dao;

import java.util.List;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Senderid;

public interface SenderidDao {

	Senderid saveSenderidOrUpdate(Senderid senderid);

	DataTables<Object> getAllSenderid(DataTablesRequest dataTablesRequest);

	String deleteSenderid(Long pkid, Long campusId);

	List<?> getAllSenderidByCampusId(Long campusId);


}
