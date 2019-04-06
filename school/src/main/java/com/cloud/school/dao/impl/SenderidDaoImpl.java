package com.cloud.school.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.SenderidDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Campus_master;
import com.cloud.school.domain.Senderid;
@Repository
public class SenderidDaoImpl implements SenderidDao{
	@Autowired
	public SessionFactory sessionFactory;
	public SenderidDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public Senderid saveSenderidOrUpdate(Senderid senderid) {
		// TODO Auto-generated method stub
		return (Senderid) this.sessionFactory.getCurrentSession().merge(senderid);
	}

	public DataTables<Object> getAllSenderid(DataTablesRequest dataTablesRequest) {
		
		String search = "";
		String srchText = dataTablesRequest.sSearch;
		if (srchText.length() > 0) {
			srchText = srchText.toUpperCase();
			for (int i = 0; i < dataTablesRequest.abSearchable.size()
					&& i < dataTablesRequest.amDataProp.size(); i++) {
				boolean bl = dataTablesRequest.abSearchable.get(i);
				String cl = dataTablesRequest.amDataProp.get(i);
				if (bl) {
					if (i == 1) {
						search = search + " and (upper(cm." + cl + ") like '%"
								+ srchText + "%'";
					} else {
						
							search = search + " or upper(si." + cl + ") like '%"
									+ srchText + "%'";	
						
					}
				}
			}
			search = search + " )";
		}
		
		System.out.println("search : "+search);
		
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);
		DataTables<Object> dtl = new DataTables<Object>();
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) totalSenderid FROM senderid si,campus_master cm where cm.campus_id=si.campus_id "+search).addScalar("totalSenderid",LongType.INSTANCE).uniqueResult()).intValue();
		
		
		
		/*List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT u.*,cm.campus_name FROM users u,campus_master cm where cm.campus_id=u.campus_id true "+search+"  GROUP BY u.login_id  order by u." + colm + " " + ord)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		*/
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT si.*,cm.campus_name FROM senderid si,campus_master cm where cm.campus_id=si.campus_id "+search)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public String deleteSenderid(Long pkid, Long campusId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("delete from Senderid where pkid=? and campus_id=?")
				.setParameter(0, pkid).setParameter(1, campusId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}

	@SuppressWarnings("unchecked")
	public List<?> getAllSenderidByCampusId(Long campusId) {
		// TODO Auto-generated method stub
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM senderid si WHERE si.campus_id=?")
				.setParameter(0, campusId)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst;
	}

}
