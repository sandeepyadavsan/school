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
import com.cloud.school.domain.Senderid;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.SenderidService;
import com.cloud.school.service.UserService;

@Controller
public class SenderidController {

	
	@Autowired
	SenderidService senderidService;
	
	@Autowired
	UserService userService;
	
	
	
	private static final Logger logger = Logger.getLogger(StudentController.class);
	
	@RequestMapping("/senderidManagement")
	public String senderidManagement(HttpServletRequest request, Model model) {
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
		
		List<?> campusList = userService.getAllCampus();
		
		model.addAttribute("campusList", campusList);
		
		
		return "senderidManagement";
		
	}
	
	//
	@RequestMapping(value = "/saveSenderidOrUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveSenderidOrUpdate(@RequestBody Senderid senderid, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			System.out.println("hello");
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | SenderidController | saveSenderidOrUpdate Method. ");
					//senderid.setCampus_id(usr.getCampusId());
					//student_records.setStudent_name(student_name)
				    //student_records
					//classMaster.setCampusId(usr.getCampusId());
					senderidService.saveSenderidOrUpdate(senderid); 
					//classService.saveUpdateClass(classMaster);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("SenderID saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit this SenderID.");
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
	
	
	@RequestMapping(value = "/getAllSenderid", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> getAllSenderid(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				request.getSession().setAttribute("datatablereqq", dataTablesRequest);
				logger.info("UserID : "+uProfile.getLoginId()+" | SenderidController | getAllSenderid Method. ");
				rd = senderidService.getAllSenderid(dataTablesRequest);
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
	
	//Delete Student
			@RequestMapping(value="/deleteSenderid", method = RequestMethod.GET)
			public @ResponseBody AjaxResponse deleteSenderid(@RequestParam Long pkid,@RequestParam Long campus_id,HttpServletRequest request, Model model) {
				AjaxResponse ajx=new AjaxResponse();
				try{
					Session session = SessionUtils.getSession(request);
			    	if(session!=null)
			    	{
					Users userProfile = session.getUser();
					logger.info("UserID : "+userProfile.getLoginId()+" | SenderidController | deleteSenderid Method. ");
					String objects= senderidService.deleteSenderid(pkid,campus_id);
					if(objects.equalsIgnoreCase("Success")){
						ajx.setStatus(AjaxResponse.SUCCESS);
						ajx.setData(objects);
						ajx.setMessage("Record deleted successfully.");
					}else{
						ajx.setStatus(AjaxResponse.ERROR);
						ajx.setData(objects);
						ajx.setMessage("SenderId Record not deleted.");
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
	public SenderidController() {
		// TODO Auto-generated constructor stub
	}

}
