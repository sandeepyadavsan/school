package com.cloud.school.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.TemplateDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Student_records;
import com.cloud.school.domain.Template;
@Repository
public class TemplateDaoImpl implements TemplateDao{
	@Autowired
	public SessionFactory sessionFactory;
	public TemplateDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public Template saveTemplateOrUpdate(Template template) {
		// TODO Auto-generated method stub
		return (Template) this.sessionFactory.getCurrentSession().merge(template);
	}

	public DataTables<Object> getAllTemplatebyCampusId(
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
					if (i == 1) {
						search = search + " and (upper(em." + cl + ") like '%"
								+ srchText + "%'";
					} else {
						
							search = search + " or upper(temp." + cl + ") like '%"
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
				.createSQLQuery("SELECT COUNT(*) totalTemplate FROM templates temp,event_master em,campus_master cm where temp.`event_id`=em.`event_id` AND cm.campus_id=temp.campus_id "+search).addScalar("totalTemplate",LongType.INSTANCE).uniqueResult()).intValue();
		
		
		
		/*List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT u.*,cm.campus_name FROM users u,campus_master cm where cm.campus_id=u.campus_id true "+search+"  GROUP BY u.login_id  order by u." + colm + " " + ord)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		*/
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT temp.*,em.`event_name`,cm.`campus_name` FROM templates temp,`event_master` em,`campus_master` cm WHERE temp.`event_id`=em.`event_id` AND cm.campus_id=temp.campus_id "+search)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public String deleteTemplate(Long id, Long campus_id) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("delete from Template where id=? and campus_id=?")
				.setParameter(0, id).setParameter(1, campus_id);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}

	public Template getTemplateById(Long template_id, Long campusId) {
		
		Template temp =	(Template) this.sessionFactory.getCurrentSession().createQuery("from Template where id=? AND campus_id=?")
		.setParameter(0, template_id)
		.setParameter(1, campusId)
		.uniqueResult();
		
		return temp;
	}

}
