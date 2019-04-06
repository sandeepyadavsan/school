package com.cloud.school.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.StudentDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.BulkStudent;
import com.cloud.school.domain.Student_records;


@Repository
public class StudentDaoImpl implements StudentDao {
	
	@Autowired
	public SessionFactory sessionFactory;

	public StudentDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public DataTables<Object> getAllStudentOfCampus(
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
					if (i == 1 ) {
						search = search + " and (upper(stu." + cl + ") like '%"
								+ srchText + "%'";
					} else if(i ==2 || i==3 || i==6 || i==7){
						
						
						search = search + " or upper(stu." + cl + ") like '%"
								+ srchText + "%'";
						
							/*search = search + " or upper(cm." + cl + ") like '%"
									+ srchText + "%'";	 chalan ab me aata hu chai pee kr ok */
						
					}
					else if(i==4)
					{
						search = search + " or upper(cm." + cl + ") like '%"
						+ srchText + "%'";	
						
					}
				}
			}
			search = search + " )";
		}
		
		System.out.println("search : "+search);
		
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);
		DataTables<Object> dtl = new DataTables<Object>();
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT count(*) allStudents  FROM student_records stu LEFT OUTER JOIN  class_master cm ON cm.`classid`=stu.`classid` AND cm.`campus_id`=stu.`campus_id` LEFT OUTER JOIN  section_master sm ON sm.`sectioid`=stu.`sectionid` AND sm.`campus_id` = stu.`campus_id` WHERE  (stu.campus_id=:campusId OR (stu.campus_id=:campusId AND stu.`sectionid`=-1))  "+search)
				.addScalar("allStudents",LongType.INSTANCE)
				.setParameter("campusId", dataTablesRequest.campusId)
				.uniqueResult()).intValue();
		
		
		/*@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM student_records stu INNER JOIN class_master cm ON stu.`classid`=cm.`classid` INNER JOIN section_master sm ON stu.`sectionid` = sm.`sectioid` WHERE stu.`campus_id`=?")
				// , class_master cm,section_master sm where stu.campus_id=? AND cm.classid = stu.classid  AND sm.sectioid = stu.sectionid
				.setParameter(0, dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		*/
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT stu.`campus_id`,stu.`date_of_admission`,stu.`form_number`,stu.`message_mobile_number`,stu.`student_caste`,stu.`student_current_address`,stu.`student_dob`,stu.`student_email`,stu.`student_father`,stu.`student_gender`,stu.`student_home_address`,stu.`student_id`,stu.`student_mother`,stu.`student_name`,stu.`student_picture`,stu.`student_rollno`,stu.`student_status`,cm.`className`, IFNULL(sm.`sectionname`,'NA') sectionname FROM student_records stu LEFT OUTER JOIN  class_master cm ON cm.`classid`=stu.`classid` AND cm.`campus_id`=stu.`campus_id` LEFT OUTER JOIN  section_master sm ON sm.`sectioid`=stu.`sectionid` AND sm.`campus_id` = stu.`campus_id` WHERE  (stu.campus_id=:campusId OR (stu.campus_id=:campusId AND stu.`sectionid`=-1))  "+search)
				.setParameter("campusId", dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		
		
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}

	public Student_records saveStudentOrUpdate(Student_records student_records) {
		// TODO Auto-generated method stub
		return (Student_records) this.sessionFactory.getCurrentSession().merge(student_records);
	}

	public String deleteStudent(Long studentId, Long campusId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("delete from Student_records where student_id=? and campus_id=?")
				.setParameter(0, studentId).setParameter(1, campusId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}

	public Student_records getStudentById(Long studentId, Long campusId) {
		
		Student_records std =	(Student_records) this.sessionFactory.getCurrentSession().createQuery("from Student_records where student_id=? AND campus_id=?")
		.setParameter(0, studentId)
		.setParameter(1, campusId)
		.uniqueResult();
		
		return std;
	}

	public List<?> getRollNoFormNobyCampusId(Long campusId, Long classId,
			Long sectionId) {
		
		List<?> result =  this.sessionFactory.getCurrentSession().createSQLQuery("SELECT (SELECT IFNULL((MAX(CAST(student_rollno AS UNSIGNED))+1),1)  FROM `student_records` WHERE `classid`=:classId  AND `sectionid`=:sectionId  AND `campus_id`=:campusId) rollNo ,(SELECT IF(MAX(form_number) IS NULL,0+1,MAX(form_number)+1)  FROM `student_records` WHERE `campus_id`=:campusId) formNo ")
		.addScalar("rollNo",StringType.INSTANCE)
		.addScalar("formNo",StringType.INSTANCE)
		.setParameter("classId", classId)
		.setParameter("sectionId", sectionId)
		.setParameter("campusId",campusId)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return result;
	}

	public int inserBulkStudentRecords(List<BulkStudent> lst, Long campusId) {
		int sts = 0;
		for(BulkStudent bs : lst)
		{
			
			@SuppressWarnings("unchecked")
			Map<String,String> data = (Map<String, String>) this.sessionFactory.getCurrentSession().createSQLQuery("SELECT (SELECT IFNULL((MAX(CAST(student_rollno AS UNSIGNED))+1),1)  FROM `student_records` WHERE `classid`=:classId  AND `sectionid`=:sectionId  AND `campus_id`=:campusId) rollNo ,(SELECT (MAX(IFNULL(form_number,0))+1)  FROM `student_records` WHERE `campus_id`=:campusId) formNo ")
			.addScalar("rollNo",StringType.INSTANCE)
			.addScalar("formNo",StringType.INSTANCE)
			.setParameter("classId", bs.getClassId())
			.setParameter("sectionId", bs.getSectionId())
			.setParameter("campusId",campusId)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			
			if(bs.getFormNo()==null||bs.getFormNo().trim().equalsIgnoreCase("")||bs.getFormNo().trim().equalsIgnoreCase("0"))
				bs.setFormNo(data.get("formNo"));
			
			sts = this.sessionFactory.getCurrentSession()
					.createSQLQuery("INSERT INTO student_records (campus_id, classid, sectionid, form_number, student_rollno, student_name, student_father, student_dob, date_of_admission, student_gender, student_email, message_mobile_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
					.setParameter(0, campusId)
					.setParameter(1, bs.getClassId())
					.setParameter(2, bs.getSectionId())
					.setParameter(3, bs.getFormNo())
					.setParameter(4, data.get("rollNo"))
					.setParameter(5, bs.getStudentName())
					.setParameter(6, bs.getFatherName())
					.setParameter(7, bs.getDateOfBirth())
					.setParameter(8, bs.getDateOfAdmission())
					.setParameter(9, bs.getGender())
					.setParameter(10, bs.getEmailId())
					.setParameter(11, bs.getMessageMobile())
					.executeUpdate();
			
		}
		
		return sts;
	}

	@SuppressWarnings("unchecked")
	public List<Student_records> getExistingFormNumbers(Long campusId) {
		List<Student_records> lst = null;
		
		lst = this.sessionFactory.getCurrentSession().createQuery("FROM Student_records sr WHERE sr.campus_id=?")
		.setParameter(0,campusId)
		.list();
		
		if(lst==null)
			lst = new ArrayList<Student_records>();
		
		return lst;
	}

}
