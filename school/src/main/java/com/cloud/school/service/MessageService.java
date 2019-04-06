package com.cloud.school.service;

import java.util.List;

import com.cloud.school.domain.Messageconfig;

public interface MessageService {

	List<Object> getAllStudentOfClassandSectionByCampusId(Long campusId, String classSectionId);

	int saveMessageParticipants(Messageconfig msgData);
	// this is for backend App
	List<Object> getAllActiveMessageConfig();
}
