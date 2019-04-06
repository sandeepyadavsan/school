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

import com.cloud.school.domain.AjaxResponse;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Messageconfig;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.AttendanceService;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.EventService;
import com.cloud.school.service.MessageService;
import com.cloud.school.service.SenderidService;
import com.cloud.school.service.UserService;

@Controller
public class MessageController {

	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	SenderidService senderidService;
	@Autowired
	EventService eventService;
	
	@Autowired
	MessageService messageService;
	
	private static final Logger logger = Logger.getLogger(AttendanceController.class);
	
	
	@RequestMapping("/messageManagement")
	public String messageManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		List<MenuMaster> mainMenu = userService.getMainMenus(userType);
		List<MenuMaster> subMenu = userService.getSubMainMenus(userType);
		List<?> eventList = eventService.getAllEventsByCampusId(session.getUser().getCampusId());
		
		List<?> senderidList = senderidService.getAllSenderidByCampusId(session.getUser().getCampusId());
		
		model.addAttribute("mainMenuList", mainMenu);
		model.addAttribute("subMenuList", subMenu);
		model.addAttribute("eventList", eventList);
		model.addAttribute("senderidList", senderidList);
		
		for (MenuMaster menuMaster : mainMenu) {
			System.out.println("Main menu : "+menuMaster.getMenu_name()+" id: "+menuMaster.getMenu_id());
		}
		
		for (MenuMaster menuMaster : subMenu) {
			System.out.println(menuMaster.getParent_menu()+"Sub menu : "+menuMaster.getMenu_name());
		}
		List<?> classList = classService.getAllClassesWithSectionByCampusId(session.getUser().getCampusId());
		model.addAttribute("classList", classList);
		return "messageManagement";
	}
	
	@RequestMapping(value = "/getAllStudentOfClassandSectionByCampusId", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllStudentOfClassandSectionByCampusId(@RequestParam String classSectionId,HttpServletRequest request, Model model){
		AjaxResponse rd = new AjaxResponse();
		try {
			
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				logger.info("UserID : "+uProfile.getLoginId()+" | MessageController | getAllStudentOfClassandSectionByCampusId Method. ");
				
				if(classSectionId.equalsIgnoreCase("AllClass"))
				{
					// All Students
					
					List<Object> lst = null;
					
					rd.setData(lst);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("AllClasses");
					
				}
				else if(classSectionId.equalsIgnoreCase("MultiClass"))
				{
					
					// multiple classes
					
					
					List<?> lst = classService.getAllClassesWithSectionByCampusId(uProfile.getCampusId()); 
					
					rd.setData(lst);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("MultipleClassess");
					
				}
				else if(classSectionId.contains("#"))
				{
				List<Object> lst = messageService.getAllStudentOfClassandSectionByCampusId(uProfile.getCampusId(),classSectionId); 
				
				rd.setData(lst);
				rd.setStatus(AjaxResponse.SUCCESS);
				rd.setMessage("Success");
				
				}
				
				else
				{
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("Please Select participants.");
					
				}
			}else{
				
				rd.setStatus(AjaxResponse.ERROR);
				rd.setMessage("SessionTimeOut");
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
	
	
	@RequestMapping(value = "/saveMessageParticipants", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveMessageParticipants(@RequestBody Messageconfig msgData,HttpServletRequest request, Model model){
		AjaxResponse rd = new AjaxResponse();
		try {
			
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				logger.info("UserID : "+uProfile.getLoginId()+" | MessageController | saveMessageParticipants Method. ");
				
				   msgData.setCampusId(uProfile.getCampusId());
					int sts = messageService.saveMessageParticipants(msgData);
					
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Success");
				
				
			}else{
				
				rd.setStatus(AjaxResponse.ERROR);
				rd.setMessage("SessionTimeOut");
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
		return rd;
	}
	
	@RequestMapping(value = "/getAllTemplateOfEvent", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllTemplateOfEvent(@RequestParam Long eventId,HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | MessageController | getAllTemplateOfEvent Method. ");
					List<?> lstData = eventService.getAllTemplateOfEvent(eventId,usr.getCampusId());
					rd.setData(lstData);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Templates are as");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to get Template.");
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
	
	
	public MessageController() {
		// TODO Auto-generated constructor stub
	}

}
