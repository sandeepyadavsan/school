package com.cloud.school.dao;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.Template;

public interface TemplateDao {

	Template saveTemplateOrUpdate(Template template);

	DataTables<Object> getAllTemplatebyCampusId(
			DataTablesRequest dataTablesRequest);

	String deleteTemplate(Long id, Long campus_id);

	Template getTemplateById(Long template_id, Long campusId);

}
