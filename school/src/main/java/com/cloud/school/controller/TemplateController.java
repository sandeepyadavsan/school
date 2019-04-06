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
import com.cloud.school.domain.Student_records;
import com.cloud.school.domain.Template;
import com.cloud.school.domain.Users;
import com.cloud.school.service.EventService;
import com.cloud.school.service.SenderidService;
import com.cloud.school.service.TemplateService;
import com.cloud.school.service.UserService;

@Controller
public class TemplateController {

	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TemplateService templateService;
	
	private static final Logger logger = Logger.getLogger(TemplateController.class);
	
	
	@RequestMapping("/templateManagement")
	public String senderidManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		 Users uProfile=null;
		 try {
				Session sess = SessionUtils.getSession(request);
				if(sess!=null){
					 uProfile = sess.getUser();
					}
		 }catch (Exception e) {
						e.printStackTrace();
					
						// TODO: handle exception
					}
		
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
		
		List<?> eventList = eventService.getAllEventsByCampusId(uProfile.getCampusId());
		
		model.addAttribute("eventList", eventList);
		/*List<?> campusList = userService.getAllCampus();
		
		model.addAttribute("campusList", campusList);*/
		
		
		return "templateManagement";
		
	}
	
	@RequestMapping(value = "/saveTemplateOrUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveTemplateOrUpdate(@RequestBody Template template, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			System.out.println("hello");
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | saveTemplateOrUpdate | saveTemplateOrUpdate Method. ");
					template.setCampus_id(usr.getCampusId());
					//student_records.setStudent_name(student_name)
					//template.setCreate_date("2016-05-25 16:16:13");
					//classMaster.setCampusId(usr.getCampusId());
					templateService.saveTemplateOrUpdate(template); 
					//classService.saveUpdateClass(classMaster);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Template saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit this Template.");
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
	
	
	
	@RequestMapping(value = "/getAllTemplatebyCampusId", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> getAllTemplatebyCampusId(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		
		
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				request.getSession().setAttribute("datatablereqq", dataTablesRequest);
				logger.info("UserID : "+uProfile.getLoginId()+" | getAllTemplatebyCampusId | getAllTemplatebyCampusId Method. ");
				rd = templateService.getAllTemplatebyCampusId(dataTablesRequest);
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
	
	@RequestMapping(value="/deleteTemplate", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse deleteTemplate(@RequestParam Long id,@RequestParam Long campus_id,HttpServletRequest request, Model model) {
		AjaxResponse ajx=new AjaxResponse();
		try{
			Session session = SessionUtils.getSession(request);
	    	if(session!=null)
	    	{
			Users userProfile = session.getUser();
			logger.info("UserID : "+userProfile.getLoginId()+" | TemplateController | deleteTemplate Method. ");
			String objects= templateService.deleteTemplate(id,campus_id);
			if(objects.equalsIgnoreCase("Success")){
				ajx.setStatus(AjaxResponse.SUCCESS);
				ajx.setData(objects);
				ajx.setMessage("Record deleted successfully.");
			}else{
				ajx.setStatus(AjaxResponse.ERROR);
				ajx.setData(objects);
				ajx.setMessage("Template Record not deleted.");
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
	@RequestMapping(value="/getTemplateById", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getTemplateById(@RequestParam Long template_id,HttpServletRequest request, Model model) {
		AjaxResponse ajx=new AjaxResponse();
		try{
			Session session = SessionUtils.getSession(request);
	    	if(session!=null)
	    	{
			Users userProfile = session.getUser();
			logger.info("UserID : "+userProfile.getLoginId()+" | TemplateController | getTemplateById Method. ");
			Template temp= templateService.getTemplateById(template_id,userProfile.getCampusId());

			    ajx.setStatus(AjaxResponse.SUCCESS);
				ajx.setData(temp);
				ajx.setMessage("success.");
			
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
	
	public TemplateController() {
		// TODO Auto-generated constructor stub
	}
	

}
