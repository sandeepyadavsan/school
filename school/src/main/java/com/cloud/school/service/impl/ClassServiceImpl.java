package com.cloud.school.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.ClassDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.ClassMaster;
import com.cloud.school.domain.ClassSection;
import com.cloud.school.domain.DownloadClassSection;
import com.cloud.school.service.ClassService;

@Service
@Transactional(readOnly=true)
public class ClassServiceImpl  implements ClassService{

	
	@Autowired
	ClassDao dao;
	
	
	@Transactional(readOnly=true)
	public DataTables<Object> getAllClassesOfCampus(
			DataTablesRequest dataTablesRequest) {
		return dao. getAllClassesOfCampus( dataTablesRequest);
	}


	@Transactional(readOnly=false)
	public ClassMaster saveUpdateClass(ClassMaster classMaster) {
		return dao.saveUpdateClass(classMaster); 
	}

	@Transactional(readOnly=false)
	public String deleteClass(Long classId, Long campusId) {
		return dao. deleteClass( classId,  campusId);
	}


	@Transactional(readOnly=true)
	public List<?> getAllSectionOfCampus(Long campusId) {
		return dao.getAllSectionOfCampus(campusId);
	}


	@Transactional(readOnly=false)
	public int mapSectionClass(Long campusId, Long classId,
			String sectionIds) {
		return dao. mapSectionClass(campusId, classId, sectionIds);
	}

	@Transactional(readOnly=false)
	public List<?> getAllSectionOfClass(Long classId, Long campusId) {
		// TODO Auto-generated method stub
		 return dao. getAllSectionOfClass(classId, campusId);
	}


	@Transactional(readOnly=true)
	public List<?> getAllClassesByCampusId(Long campusId) {
		return dao.getAllClassesByCampusId( campusId);
	}


	@Transactional(readOnly=true)
	public List<?> getAllClassesWithSectionByCampusId(Long campusId) {
		return dao.getAllClassesWithSectionByCampusId( campusId);
	}

	@Transactional(readOnly=true)
	public List<?> getMapSectionByClassId(Long campusId, Long classId) {
		// TODO Auto-generated method stub
		return dao.getMapSectionByClassId( campusId,  classId) ;
	}

	@Transactional(readOnly=true)
	public List<ClassSection> getAllExistingClassSectionByCampusId(Long campusId) {
		// TODO Auto-generated method stub
		return dao.getAllExistingClassSectionByCampusId( campusId);
	}

	@Transactional(readOnly=false)
	public void deleteDumySectionFromClass(Long sectionPkId) {
		// TODO Auto-generated method stub
		dao.deleteDumySectionFromClass(sectionPkId);
		
	}

	@Transactional(readOnly=true)
	public Long getDumySectionOfClass(Long classId, Long campusId) {
		// TODO Auto-generated method stub
		return dao.getDumySectionOfClass( classId,  campusId);
	}

	@Transactional(readOnly=false)
	public int saveUpdateNewSection(String newSection, Long campusId) {
		// TODO Auto-generated method stub
		return dao.saveUpdateNewSection(newSection, campusId);
	}

	@Transactional(readOnly=true)
	public List<DownloadClassSection> getClassIdSectionIdForDownload(Long campusId) {
		// TODO Auto-generated method stub
		return dao.getClassIdSectionIdForDownload(campusId);
	}


}
