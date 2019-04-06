package com.cloud.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.EventDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Event_master;
import com.cloud.school.service.EventService;

@Service
@Transactional(readOnly=true)
public class EventServiceImpl implements EventService {

	@Autowired
	EventDao dao;
	@Transactional(readOnly=true)
	public DataTables<Object> getAllEventOfCampus(
			DataTablesRequest dataTablesRequest) {
		// TODO Auto-generated method stub
		return dao.getAllEventOfCampus (dataTablesRequest);
	}
	@Transactional(readOnly=false)
	public Event_master saveUpdateEvent(Event_master event_master) {
		// TODO Auto-generated method stub
		return dao.saveUpdateEvent(event_master);
	}
	@Transactional(readOnly=false)
	public String deleteEvent(Long event_id, Long campusId) {
		// TODO Auto-generated method stub
		return dao.deleteEvent( event_id,  campusId);
	}
	@Transactional(readOnly=false)
	public List<?> getAllEventsByCampusId(Long campusId) {
		// TODO Auto-generated method stub
		return dao.getAllEventsByCampusId(campusId);
	}
	@Transactional(readOnly=true)
	public List<?> getAllTemplateOfEvent(Long eventId, Long campusId) {
		// TODO Auto-generated method stub
		return dao.getAllTemplateOfEvent(eventId, campusId);
	}

}
