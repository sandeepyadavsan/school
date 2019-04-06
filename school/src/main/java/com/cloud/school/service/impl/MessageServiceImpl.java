package com.cloud.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.MessageDao;
import com.cloud.school.domain.Messageconfig;
import com.cloud.school.service.MessageService;

@Service
@Transactional(readOnly=true)
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageDao messageDao;

	public MessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	@Transactional(readOnly=true)
	public List<Object> getAllStudentOfClassandSectionByCampusId(Long campusId, String classSectionId) {
		// TODO Auto-generated method stub
		return messageDao.getAllStudentOfClassandSectionByCampusId(campusId,  classSectionId);
	}
	@Transactional(readOnly=false)
	public int saveMessageParticipants( Messageconfig msgData) {
		// TODO Auto-generated method stub
		return messageDao.saveMessageParticipants(msgData);
	}
	
	// This is for Backend App
	public List<Object> getAllActiveMessageConfig() {
		// TODO Auto-generated method stub
		return messageDao.saveMessageParticipants();
	}

}
