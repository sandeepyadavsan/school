package com.cloud.school.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.MessageDao;
import com.cloud.school.domain.Messageconfig;

@Repository
public class MessageDaoImpl implements MessageDao{

	@Autowired
	public SessionFactory sessionFactory;
	public MessageDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<Object> getAllStudentOfClassandSectionByCampusId(Long campusId, String classSectionId) {
		List<Object> lst;
		String classId = "";
		String sectionId = "";
		
		classId = classSectionId.split("#")[0];
		sectionId = classSectionId.split("#")[1];
		
		if(sectionId!=null && sectionId!="")
		{
		
			lst = this.sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT sr.* FROM student_records sr WHERE sr.classid=? AND sr.sectionid=? AND sr.campus_id=? ")
					.setParameter(0, classId)
					.setParameter(1, sectionId)
					.setParameter(2, campusId)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
			
		}
		else
			lst = new ArrayList<Object>();
		
		
		
		return lst;
		
	}

	public int saveMessageParticipants( Messageconfig msgData) {
		// TODO Auto-generated method stub
		
		this.sessionFactory.getCurrentSession().merge(msgData);
		
		return 1;
	}

	// This is for Backend App
	@SuppressWarnings("unchecked")
	public List<Object> saveMessageParticipants() {
		// TODO Auto-generated method stub
		List<Object> lst;
		lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT mc.id,mc.`campusid`,mc.`classSectionIds`,mc.`studentids`,mc.`type`,mc.`eventid`,mc.`message`,mc.`configdate`,mc.`schedule`,mc.`senderid`,mc.`configstatus` FROM `messageconfig` mc WHERE `configstatus`='Active' LIMIT 2 ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst;
	}

}
