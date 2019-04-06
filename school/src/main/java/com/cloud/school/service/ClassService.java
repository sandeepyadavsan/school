package com.cloud.school.service;

import java.util.List;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.ClassMaster;
import com.cloud.school.domain.ClassSection;
import com.cloud.school.domain.DownloadClassSection;

public interface ClassService {

	DataTables<Object> getAllClassesOfCampus(DataTablesRequest dataTablesRequest);

	ClassMaster saveUpdateClass(ClassMaster classMaster);

	String deleteClass(Long classId, Long campusId);

	List<?> getAllSectionOfCampus(Long campusId);

	int mapSectionClass(Long campusId, Long classId, String sectionIds);
	List<?> getAllSectionOfClass(Long classId, Long campusId);

	List<?> getAllClassesByCampusId(Long campusId);

	List<?> getAllClassesWithSectionByCampusId(Long campusId);

	List<?> getMapSectionByClassId(Long campusId, Long classId);

	List<ClassSection> getAllExistingClassSectionByCampusId(Long campusId);

	void deleteDumySectionFromClass(Long sectionPkId);

	Long getDumySectionOfClass(Long classId, Long campusId);

	int saveUpdateNewSection(String newSection, Long campusId);
	List<DownloadClassSection> getClassIdSectionIdForDownload(Long campusId);


	
	//getAllSectionOfClass

}
