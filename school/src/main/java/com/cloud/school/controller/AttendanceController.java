package com.cloud.school.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.AjaxResponse;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.StudentAttendance;
import com.cloud.school.domain.Users;
import com.cloud.school.service.AttendanceService;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.UserService;

@Controller
public class AttendanceController {

	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	AttendanceService attendanceService;
	
	private static final Logger logger = Logger.getLogger(AttendanceController.class);
	
	
	
	@RequestMapping("/attendanceManagement")
	public String attendanceManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		List<MenuMaster> mainMenu = userService.getMainMenus(userType);
		List<MenuMaster> subMenu = userService.getSubMainMenus(userType);
		
		model.addAttribute("mainMenuList", mainMenu);
		model.addAttribute("subMenuList", subMenu);
		for (MenuMaster menuMaster : mainMenu) {
			System.out.println("Main menu : "+menuMaster.getMenu_name()+" id: "+menuMaster.getMenu_id());
		}
		
		for (MenuMaster menuMaster : subMenu) {
			System.out.println(menuMaster.getParent_menu()+"Sub menu : "+menuMaster.getMenu_name());
		}
		
		List<?> classList = classService.getAllClassesByCampusId(session.getUser().getCampusId());
		
		model.addAttribute("classList", classList);
		
		
		return "attendanceManagement";
		
	}
	
	
	
	@RequestMapping("/attendanceDisupdManagement")
	public String attendanceDisupdManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		List<MenuMaster> mainMenu = userService.getMainMenus(userType);
		List<MenuMaster> subMenu = userService.getSubMainMenus(userType);
		
		model.addAttribute("mainMenuList", mainMenu);
		model.addAttribute("subMenuList", subMenu);
		for (MenuMaster menuMaster : mainMenu) {
			System.out.println("Main menu : "+menuMaster.getMenu_name()+" id: "+menuMaster.getMenu_id());
		}
		
		for (MenuMaster menuMaster : subMenu) {
			System.out.println(menuMaster.getParent_menu()+"Sub menu : "+menuMaster.getMenu_name());
		}
		
		List<?> classList = classService.getAllClassesByCampusId(session.getUser().getCampusId());
		
		model.addAttribute("classList", classList);
		
		
		return "attendanceDisupdManagement";
		
	}

	
	@RequestMapping(value = "/getAllStudentOfClass", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> getAllStudentOfClass(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				logger.info("UserID : "+uProfile.getLoginId()+" | AttendanceController | getAllStudentOfClass Method. ");
				dataTablesRequest.campusId = uProfile.getCampusId();
				System.out.println("1");
				rd = attendanceService.getAllStudentOfClass(dataTablesRequest);
				
				if(rd!=null){
					rd.sColumns=dataTablesRequest.sColumns;
					rd.sEcho=dataTablesRequest.sEcho;
				}else{
					rd=new DataTables<Object>();
				}
				rd.status=AjaxResponse.SUCCESS;
				rd.message="Success";
			}else{
				if(rd==null){
					rd=new DataTables<Object>();
				}
				rd.status=AjaxResponse.ERROR;
				rd.message="SessionTimeOut";
			}
		} catch (Exception ex) {
			rd=new DataTables<Object>();
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
	
	
	@RequestMapping(value = "/studentAttendanceUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse studentAttendanceUpdate(@RequestBody StudentAttendance studentAttendance, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | StudentController | studentAttendanceUpdate Method. ");
					studentAttendance.setCampusId(usr.getCampusId());
				    int sts = attendanceService.studentAttendanceUpdate(studentAttendance);
				    
					if(sts>0)
					{
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Attendance success.");
					}
					else
					{
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("Status not update.");
					}
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission.");
				}
			}else{
				rd.setData("Exception");
				rd.setStatus(AjaxResponse.ERROR);
				rd.setMessage("SessionTimeOut");
			}
		} catch (Exception ex) {
			rd.setStatus(AjaxResponse.ERROR);
			rd.setMessage(ex.getMessage());
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/getSearchedStudent", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> getSearchedStudent(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				logger.info("UserID : "+uProfile.getLoginId()+" | AttendanceController | getSearchedStudent Method. ");
				dataTablesRequest.campusId = uProfile.getCampusId();
				rd = attendanceService.getSearchedStudent(dataTablesRequest);
				
				if(rd!=null){
					rd.sColumns=dataTablesRequest.sColumns;
					rd.sEcho=dataTablesRequest.sEcho;
				}else{
					rd=new DataTables<Object>();
				}
				rd.status=AjaxResponse.SUCCESS;
				rd.message="Success";
			}else{
				if(rd==null){
					rd=new DataTables<Object>();
				}
				rd.status=AjaxResponse.ERROR;
				rd.message="SessionTimeOut";
			}
		} catch (Exception ex) {
			rd=new DataTables<Object>();
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
	
	@RequestMapping(value = "/updateAttendanceById", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse updateAttendanceById(@RequestParam Long attendanceId, @RequestParam String logDate, @RequestParam String status, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | StudentController | studentAttendanceUpdate Method. ");
					int sts = 0;
					if(attendanceId!=null&&attendanceId!=0)
					{
					sts = attendanceService.updateAttendanceById(usr.getCampusId(),attendanceId,logDate,status);
					}
					
					if(sts>0)
					{
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Updated successfully.");
					}
					else
					{
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("Record not updated.");
					}
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission.");
				}
			}else{
				rd.setData("Exception");
				rd.setStatus(AjaxResponse.ERROR);
				rd.setMessage("SessionTimeOut");
			}
		} catch (Exception ex) {
			rd.setStatus(AjaxResponse.ERROR);
			rd.setMessage(ex.getMessage());
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
}