package com.cloud.school.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.ClassDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.ClassMaster;
import com.cloud.school.domain.ClassSection;
import com.cloud.school.domain.DownloadClassSection;
import com.cloud.school.domain.Student_records;

@Repository
public class ClassDaoImpl implements ClassDao{


	@Autowired
	public SessionFactory sessionFactory;

	public DataTables<Object> getAllClassesOfCampus(
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
						search = search + " and (upper(cm." + cl + ") like '%"
								+ srchText + "%'";
					} else {
						
							search = search + " or upper(cm." + cl + ") like '%"
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
				.createSQLQuery("SELECT COUNT(*) allClasses  FROM class_master cm where cm.campus_id=? "+search)
				.addScalar("allClasses",LongType.INSTANCE)
				.setParameter(0, dataTablesRequest.campusId)
				.uniqueResult()).intValue();
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM class_master cm where cm.campus_id=? "+search+"  order by cm." + colm + " " + ord)
				.setParameter(0, dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public ClassMaster saveUpdateClass(ClassMaster classMaster) {
		
		int i =this.sessionFactory.getCurrentSession().createSQLQuery("INSERT IGNORE INTO `section_master`\r\n" + 
				"    (`sectioid`, `sectionname`, `campus_id`)\r\n" + 
				"VALUES\r\n" + 
				"    (-1, ?, ?);")
				.setParameter(0, "NA")
				.setParameter(1, classMaster.getCampusId())
				.executeUpdate();
		
		return (ClassMaster) this.sessionFactory.getCurrentSession().merge(classMaster);
		
	}

	public String deleteClass(Long classId, Long campusId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("delete from ClassMaster where classId=? and campusId=?")
				.setParameter(0, classId).setParameter(1, campusId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}

	public List<?> getAllSectionOfCampus(Long campusId) {
		
	List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM section_master sm WHERE sm.campus_id=? and sm.sectioid !=-1")
		 .setParameter(0, campusId)
		 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
		return lstData;
	}

	public int mapSectionClass(Long campusId, Long classId, String sectionIds) {
		int sts =  0;
		
		String[] ary = sectionIds.split("#");
		
		for(int i=0;i<ary.length;i++)
		{
	   sts = this.sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO map_class_section( classid, sectionid, campus_id) VALUES(?,?,?)")
		 .setParameter(0, classId)
		 .setParameter(1, Integer.parseInt(ary[i]))
		 .setParameter(2, campusId)
		 .executeUpdate();
		
	   System.out.println("sts: "+sts);
	}

		return sts;
	}

	public List<?> getAllSectionOfClass(Long classId, Long campusId) {
		// TODO Auto-generated method stub
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT sm.`sectionname`,sm.`sectioid` FROM `map_class_section` mcs, section_master sm WHERE mcs.`classid`=? AND mcs.`campus_id`=? AND sm.`sectioid`=mcs.`sectionid` order by sm.`sectionname`")
				 .setParameter(0, classId)
				 .setParameter(1, campusId)
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
				return lstData;
	}

	public List<?> getAllClassesByCampusId(Long campusId) {
		
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT cm.classid classId,cm.className className FROM  class_master cm WHERE cm.campus_id=?")
				.setParameter(0, campusId)
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
		return lstData;
	}

	public List<?> getAllClassesWithSectionByCampusId(Long campusId) {
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT mcs.classid,mcs.`sectionid`,cm.`className`,sm.`sectionname` FROM `map_class_section` mcs,class_master cm, section_master sm  WHERE mcs.campus_id=? AND cm.`classid`=mcs.`classid` AND sm.`sectioid`=mcs.`sectionid` AND mcs.`campus_id`=cm.`campus_id` AND sm.`campus_id`=mcs.`campus_id` ORDER BY mcs.`classid`,sm.`sectionname` ASC")
				.setParameter(0, campusId)
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
		return lstData;
	}

	public List<?> getMapSectionByClassId(Long campusId, Long classId) {
		
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT mcs.sectionid,mcs.classid FROM map_class_section mcs WHERE mcs.classid=? AND mcs.campus_id=?")
				.setParameter(0, classId)
				.setParameter(1, campusId)
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
		return lstData;
	}

	public List<ClassSection> getAllExistingClassSectionByCampusId(Long campusId) {
		@SuppressWarnings("unchecked")
		List<ClassSection> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT mcs.classid,mcs.`sectionid`,cm.`className`,sm.`sectionname` FROM `map_class_section` mcs,class_master cm, section_master sm  WHERE mcs.campus_id=? AND cm.`classid`=mcs.`classid` AND sm.`sectioid`=mcs.`sectionid` AND mcs.`campus_id`=cm.`campus_id` AND sm.`campus_id`=mcs.`campus_id` ORDER BY mcs.`classid`,sm.`sectionname` ASC")
		.addScalar("classid", LongType.INSTANCE)
		.addScalar("sectionid", LongType.INSTANCE)
		.addScalar("className", StringType.INSTANCE)
		.addScalar("sectionname", StringType.INSTANCE)
		.setParameter(0, campusId)
				
				 .setResultTransformer(Transformers.aliasToBean(ClassSection.class)).list();
		return lstData;
	}

	public void deleteDumySectionFromClass(Long sectionPkId) {
		// TODO Auto-generated method stub
		
		this.sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM map_class_section WHERE pkid=?")
		.setParameter(0, sectionPkId)
		.executeUpdate();
	}

	public Long getDumySectionOfClass(Long classId, Long campusId) {
		// TODO Auto-generated method stub
		
			Long sectionPkId =	(Long) this.sessionFactory.getCurrentSession().createSQLQuery("SELECT IFNULL(mcs.`pkid`,'0') sectionId FROM map_class_section mcs WHERE mcs.`classid`=? AND mcs.`sectionid`=-1 AND mcs.`campus_id`=?")
				.addScalar("sectionId", LongType.INSTANCE)
				.setParameter(0, classId)
				.setParameter(1, campusId)
				.uniqueResult();
		return sectionPkId;
	}

	public int saveUpdateNewSection(String newSection, Long campusId) {
		// TODO Auto-generated method stub
		int i =this.sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO section_master  (sectionname, campus_id)SELECT * FROM (SELECT :sectionName, :campusId) AS section WHERE NOT EXISTS ( SELECT sectionname FROM section_master WHERE campus_id=:campusId AND lower(sectionname)= lower(:sectionName))")
		.setParameter("sectionName", newSection)
		.setParameter("campusId", campusId)
		.executeUpdate();
		return i;
	}

	@SuppressWarnings("unchecked")
	public List<DownloadClassSection> getClassIdSectionIdForDownload(Long campusId) {
		// TODO Auto-generated method stub
				List<DownloadClassSection> lst = null;
				List<DownloadClassSection> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT cm.classid,cm.className,sm.sectioid,sm.sectionname FROM  class_master cm, section_master sm, map_class_section mcs WHERE cm.campus_id=? AND mcs.sectionid=sm.sectioid AND mcs.classid=cm.classid ORDER BY cm.className ASC")
						.addScalar("classid", StringType.INSTANCE)
						.addScalar("className", StringType.INSTANCE)
						.addScalar("sectioid", StringType.INSTANCE)
						.addScalar("sectionname", StringType.INSTANCE)
						.setParameter(0, campusId)
								
								 .setResultTransformer(Transformers.aliasToBean(DownloadClassSection.class)).list();
						return lstData;
				
	}
	
}
