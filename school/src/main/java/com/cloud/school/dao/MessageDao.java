package com.cloud.school.dao;

import java.util.List;

import com.cloud.school.domain.Messageconfig;

public interface MessageDao {
	List<Object> getAllStudentOfClassandSectionByCampusId(Long campusId, String classSectionId);

	int saveMessageParticipants(Messageconfig msgData);
	// This is for BackendApp
	List<Object> saveMessageParticipants();

}
