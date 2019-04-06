package com.cloud.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.school.dao.HomeDao;
import com.cloud.school.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	HomeDao homeDao;
	
	/*@Transactional(readOnly = true)
	public Map<String, Object> getDashBoardAllData(Long companyid,String startDate, String endDate, String actn, String timmezone) {
		return dashboardDao.getDashBoardAllData(companyid, startDate, endDate, actn, timmezone);
	}
 */
	
}
