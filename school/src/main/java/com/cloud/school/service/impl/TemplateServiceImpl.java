package com.cloud.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.TemplateDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Template;
import com.cloud.school.service.TemplateService;
@Service
@Transactional(readOnly=true)
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	TemplateDao dao;
	public TemplateServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	@Transactional(readOnly=false)
	public Template saveTemplateOrUpdate(Template template) {
		// TODO Auto-generated method stub
		return dao.saveTemplateOrUpdate(template);
	}
	@Transactional(readOnly=true)
	public DataTables<Object> getAllTemplatebyCampusId(
			DataTablesRequest dataTablesRequest) {
		// TODO Auto-generated method stub
		return dao.getAllTemplatebyCampusId(dataTablesRequest);
	}
	@Transactional(readOnly=false)
	public String deleteTemplate(Long id, Long campus_id) {
		// TODO Auto-generated method stub
		return dao.deleteTemplate(id, campus_id);
	}
	@Transactional(readOnly=true)
	public Template getTemplateById(Long template_id, Long campusId) {
		// TODO Auto-generated method stub
		return dao.getTemplateById(template_id, campusId);
	}

}
