package com.cloud.school.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.CampusDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Campus_master;
import com.cloud.school.domain.Student_records;

@Repository
public class CampusDaoImpl implements CampusDao {

	@Autowired
	public SessionFactory sessionFactory;
	public CampusDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public Campus_master saveCampusOrUpdate(Campus_master campus_master) {
		// TODO Auto-generated method stub
		
		return (Campus_master) this.sessionFactory.getCurrentSession().merge(campus_master);
	}

	public DataTables<Object> getAllCampus(DataTablesRequest dataTablesRequest) {
		
		
		String search = "";
		String srchText = dataTablesRequest.sSearch;
		if (srchText.length() > 0) {
			srchText = srchText.toUpperCase();
			for (int i = 0; i < dataTablesRequest.abSearchable.size()
					&& i < dataTablesRequest.amDataProp.size(); i++) {
				boolean bl = dataTablesRequest.abSearchable.get(i);
				String cl = dataTablesRequest.amDataProp.get(i);
				if (bl) {
					if (i == 1 ) {
						search = search + " and (upper(cm." + cl + ") like '%"
								+ srchText + "%'";
					} else if(i ==2 || i==3 || i==6 || i==7){
						
						
						search = search + " or upper(cm." + cl + ") like '%"
								+ srchText + "%'";
						
							/*search = search + " or upper(cm." + cl + ") like '%"
									+ srchText + "%'";	 chalan ab me aata hu chai pee kr ok */
						
					}
					else if(i==4)
					{
						search = search + " or upper(cm." + cl + ") like '%"
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
				.createSQLQuery("SELECT count(*) allCampus  FROM campus_master cm where true "+search)
				.addScalar("allCampus",LongType.INSTANCE)
				.uniqueResult()).intValue();
		
		
		/*@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM student_records stu INNER JOIN class_master cm ON stu.`classid`=cm.`classid` INNER JOIN section_master sm ON stu.`sectionid` = sm.`sectioid` WHERE stu.`campus_id`=?")
				// , class_master cm,section_master sm where stu.campus_id=? AND cm.classid = stu.classid  AND sm.sectioid = stu.sectionid
				.setParameter(0, dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		*/
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT campus_id,campus_name,campus_address,pincode,campus_phone,campus_logo,created_date,pushurl,status from campus_master cm where true  "+search)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		
		
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public String deleteCampus(Long campusId) {
		// TODO Auto-generated method stub
	Query query = this.sessionFactory.getCurrentSession().createQuery("delete from Campus_master where campus_id=?")
		.setParameter(0, campusId);
	int res = query.executeUpdate();
	if (res >= 1) {
		return "Success";
	} else {
		return "Fail.";
	}
	}

}
