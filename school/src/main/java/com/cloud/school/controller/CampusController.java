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
import com.cloud.school.domain.Campus_master;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Student_records;
import com.cloud.school.domain.Users;
import com.cloud.school.service.CampusService;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.StudentService;
import com.cloud.school.service.UserService;

@Controller
public class CampusController {
	private static final Logger logger = Logger.getLogger(CampusController.class);
	
	
	
	@Autowired
	CampusService campusService;
	
	
	@RequestMapping("/campusManagement")
	public String campusManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		/*List<MenuMaster> mainMenu = userService.getMainMenus(userType);
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
		
		model.addAttribute("classList", classList);*/
		
		
		return "campusManagement";
		
	}
	
	// 
	
			@RequestMapping(value = "/getAllCampus", method = RequestMethod.POST)
			public @ResponseBody DataTables<Object> getAllCampus(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
				DataTables<Object> rd =  null;
				try {
					Session sess = SessionUtils.getSession(request);
					if(sess!=null){
						Users uProfile = sess.getUser();
						logger.info("UserID : "+uProfile.getLoginId()+" | CampusController | getAllCampus Method. ");
						dataTablesRequest.campusId = uProfile.getCampusId();
						rd = campusService.getAllCampus(dataTablesRequest);
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
	
	//
	@RequestMapping(value = "/saveCampusOrUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveCampusOrUpdate(@RequestBody Campus_master campus_master, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | CampusController | saveCampusOrUpdate Method. ");
					
					//student_records.setCampus_id(usr.getCampusId());
					//student_records.setStudent_name(student_name)
				    //student_records
					//classMaster.setCampusId(usr.getCampusId());
					campusService.saveCampusOrUpdate(campus_master);
					//classService.saveUpdateClass(classMaster);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Campus saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit this Campus.");
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
	
	
	@RequestMapping(value="/deleteCampus", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse deleteCampus(@RequestParam Long campusId,HttpServletRequest request, Model model) {
		AjaxResponse ajx=new AjaxResponse();
		try{
			Session session = SessionUtils.getSession(request);
	    	if(session!=null)
	    	{
			Users userProfile = session.getUser();
			logger.info("UserID : "+userProfile.getLoginId()+" | CampusController | deleteCampus Method. ");
			String objects= campusService.deleteCampus(campusId);
			if(objects.equalsIgnoreCase("Success")){
				ajx.setStatus(AjaxResponse.SUCCESS);
				ajx.setData(objects);
				ajx.setMessage("Record deleted successfully.");
			}else{
				ajx.setStatus(AjaxResponse.ERROR);
				ajx.setData(objects);
				ajx.setMessage("Campus Record not deleted.");
			}
	    	}else{
				ajx.setData("");
				ajx.setStatus(AjaxResponse.ERROR);
				ajx.setMessage("SessionTimeOut");
			}
		}catch(Exception e){
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		}
		return ajx;
	}

	public CampusController() {
		// TODO Auto-generated constructor stub
	}

}
