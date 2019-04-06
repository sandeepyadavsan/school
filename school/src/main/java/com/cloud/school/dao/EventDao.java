package com.cloud.school.dao;

import java.util.List;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Event_master;

public interface EventDao {
	DataTables<Object> getAllEventOfCampus(DataTablesRequest dataTablesRequest);

	Event_master saveUpdateEvent(Event_master event_master);

	String deleteEvent(Long event_id, Long campusId);

	List<?> getAllEventsByCampusId(Long campusId);

	List<?> getAllTemplateOfEvent(Long eventId, Long campusId);
}
