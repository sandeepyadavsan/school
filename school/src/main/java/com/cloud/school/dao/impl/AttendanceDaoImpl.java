package com.cloud.school.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.AttendanceDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.StudentAttendance;
import com.cloud.school.domain.StudentList;


@Repository
public class AttendanceDaoImpl implements AttendanceDao {

	@Autowired
	public SessionFactory sessionFactory;
	public DataTables<Object> getAllStudentOfClass(DataTablesRequest dataTablesRequest) {
		
		/*String search = "";
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
		
		System.out.println("search :"+search);*/
		
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		/*String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);*/
		DataTables<Object> dtl = new DataTables<Object>();
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) allstudent  FROM student_records st where st.campus_id=? and st.classid=? and st.sectionid=?")
				.addScalar("allstudent",LongType.INSTANCE)
				.setParameter(0, dataTablesRequest.campusId)
				.setParameter(1, dataTablesRequest.classId)
				.setParameter(2, dataTablesRequest.sectionId)
				.uniqueResult()).intValue();
		
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("CALL getStudentForAttendance(?,?,?,?,?,?)")
				.setParameter(0, dataTablesRequest.campusId)
				.setParameter(1, dataTablesRequest.classId)
				.setParameter(2, dataTablesRequest.sectionId)
				.setParameter(3, dataTablesRequest.day)
				.setParameter(4, dataTablesRequest.month)
				.setParameter(5, dataTablesRequest.year)
				
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		
		
	/*	@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM student_records st where st.campus_id=? and st.classid=? and st.sectionid=?")
				.setParameter(0, dataTablesRequest.campusId)
				.setParameter(1, dataTablesRequest.classId)
				.setParameter(2, dataTablesRequest.sectionId)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();*/
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}
	public int studentAttendanceUpdate(StudentAttendance studentAttendance) {
		int status = 0;
		int day = 0,month=0,year=0;
		
		day = Integer.parseInt(studentAttendance.getDate().split("-")[2]);
		month = Integer.parseInt(studentAttendance.getDate().split("-")[1]);
		year = Integer.parseInt(studentAttendance.getDate().split("-")[0]);
		
		    for(StudentList stdLst : studentAttendance.getStudentList())
		    {
		    	if(stdLst.getAttendanceId()==null)
		    	{
		    		status = this.sessionFactory.getCurrentSession()
					.createSQLQuery("INSERT INTO student_attendance (campus_id, form_number, Roll_no, student_name, classid, sectionid, takenby, `"+day+"`, att_date,att_month,att_year) VALUES (?,?,?,?,?,?,?,?,NOW(),EXTRACT(month FROM (NOW())),EXTRACT(year FROM (NOW())))")
					.setParameter(0, studentAttendance.getCampusId())
					.setParameter(1, stdLst.getFormNo())
					.setParameter(2, stdLst.getRollNo())
					.setParameter(3, stdLst.getStudentName())
					.setParameter(4, studentAttendance.getClassId())
					.setParameter(5, studentAttendance.getSectionId())
					.setParameter(6, studentAttendance.getTakenBy())
					.setParameter(7, stdLst.getAttendanceStatus())
					.executeUpdate();
		    		System.out.println("Insert for rollNo : "+stdLst.getRollNo()+"  Status : "+status);
		    	}
		    	else
		    	{
		    		status = this.sessionFactory.getCurrentSession()
							.createSQLQuery("UPDATE student_attendance set `"+day+"`=? WHERE campus_id=? AND form_number=? AND classid=? AND sectionid=? AND Roll_no=? AND att_month=? AND att_year=? ")
							.setParameter(0, stdLst.getAttendanceStatus())
							.setParameter(1, studentAttendance.getCampusId())
							.setParameter(2, stdLst.getFormNo())
							.setParameter(3, studentAttendance.getClassId())
							.setParameter(4, studentAttendance.getSectionId())
							.setParameter(5, stdLst.getRollNo())
							.setParameter(6, month)
							.setParameter(7, year)
							.executeUpdate();
		    		System.out.println("Update for rollNo : "+stdLst.getRollNo() +" attendance id : "+stdLst.getAttendanceId());
		    		
		    	}
		    }
		
		
		return status;
	}
	public DataTables<Object> getSearchedStudent(DataTablesRequest dataTablesRequest) {

		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		
		DataTables<Object> dtl = new DataTables<Object>();
		
		String query = "SELECT * FROM student_attendance sa WHERE sa.campus_id = ?";
		String searchQuery = "SELECT COUNT(*) allstudent FROM student_attendance sa WHERE sa.campus_id = ?";
		String condition = "";
		
		if(dataTablesRequest.month!=null && !dataTablesRequest.month.equalsIgnoreCase("0"))
		    condition += " AND sa.att_month="+dataTablesRequest.month+" ";
		
		if(dataTablesRequest.year!=null && !dataTablesRequest.year.equalsIgnoreCase("0"))
		    condition += " AND sa.att_year="+dataTablesRequest.year+" ";
			
		
		if(dataTablesRequest.classId!=null && dataTablesRequest.classId!=0)
			condition += " AND sa.classid="+dataTablesRequest.classId+" ";
		
		if(dataTablesRequest.sectionId!=null && dataTablesRequest.sectionId!=0)
			condition += " AND sa.sectionid="+dataTablesRequest.sectionId+" ";
		
		if(dataTablesRequest.formNumber!=null && dataTablesRequest.formNumber!="")
			condition += " AND sa.form_number="+dataTablesRequest.formNumber+" ";
		
		if(dataTablesRequest.studentName!=null && dataTablesRequest.studentName!="")
			condition += " AND sa.student_name='"+dataTablesRequest.studentName+"' ";
		
		System.out.println("Condition :"+condition);
		
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery(searchQuery+condition)
				.addScalar("allstudent",LongType.INSTANCE)
				.setParameter(0, dataTablesRequest.campusId)
				.uniqueResult()).intValue();
		
		
		@SuppressWarnings("unchecked")
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery(query+condition)
				.setParameter(0, dataTablesRequest.campusId)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	
	}
	public int updateAttendanceById(Long campusId, Long attendanceId, String logDate, String status) {
		
		int day = Integer.parseInt(logDate.split("-")[2]);
		
    	int	sts = this.sessionFactory.getCurrentSession()
					.createSQLQuery("UPDATE student_attendance set `"+day+"`=? WHERE id=? AND campus_id=? ")
					.setParameter(0, status)
					.setParameter(1, attendanceId)
					.setParameter(2, campusId)
					.executeUpdate();
    		
		return sts;
	}

	

}
