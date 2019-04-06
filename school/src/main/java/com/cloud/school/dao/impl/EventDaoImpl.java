package com.cloud.school.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.EventDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Event_master;
@Repository
public class EventDaoImpl implements EventDao{
	@Autowired
	public SessionFactory sessionFactory;
	public EventDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public DataTables<Object> getAllEventOfCampus(
			DataTablesRequest dataTablesRequest) {
		
		String search = "";
		String srchText = dataTablesRequest.sSearch;
		if (srchText.length() > 0) {
			srchText = srchText.toUpperCase();
			for (int i = 0; i < dataTablesRequest.abSearchable.size()
					&& i < dataTablesRequest.amDataProp.size(); i++) {
				boolean bl = dataTablesRequest.abSearchable.get(i);
				String cl = dataTablesRequest.amDataProp.get(i);
				if (bl) {
					if (i == 0) {
						search = search + " and (upper(em." + cl + ") like '%"
								+ srchText + "%'";
					} else {
						
							search = search + " or upper(em." + cl + ") like '%"
									+ srchText + "%'";	
						
					}
				}
			}
			search = search + " )";
		}
		
		
		
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);
		DataTables<Object> dtl = new DataTables<Object>();
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) allevent FROM event_master em where em.campus_id=? "+search)
				.addScalar("allevent",LongType.INSTANCE)
				.setParameter(0, dataTablesRequest.campusId)
				.uniqueResult()).intValue();
		
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM event_master em WHERE em.campus_id=? "+search+"  order by em." + colm + " " + ord)
				.setParameter(0, dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public Event_master saveUpdateEvent(Event_master event_master) {
		// TODO Auto-generated method stub
		return (Event_master) this.sessionFactory.getCurrentSession().merge(event_master);
	}

	public String deleteEvent(Long event_id, Long campusId) {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery("delete from event_master where event_id=? and campus_id=?")
				.setParameter(0, event_id).setParameter(1, campusId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}

	public List<?> getAllEventsByCampusId(Long campusId) {

		 List<?> lst = null;
				
		 lst =  this.sessionFactory.getCurrentSession().createSQLQuery("SELECT em.`event_id`,em.`event_name` FROM event_master em WHERE em.`campus_id`= ? AND em.`event_status`='Active'")
					.setParameter(0, campusId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 
		 if(lst==null)
		 {
			 
			 lst = new ArrayList<Object>();
		 }
					
		return lst;
	}

	public List<?> getAllTemplateOfEvent(Long eventId, Long campusId) {
		// TODO Auto-generated method stub
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT temp.id,temp.template FROM  `templates` temp WHERE temp.`event_id`=? AND temp.`campus_id`=?")
				 .setParameter(0, eventId)
				 .setParameter(1, campusId)
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
				return lstData;
	}

}
