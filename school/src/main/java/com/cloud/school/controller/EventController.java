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
import com.cloud.school.domain.ClassMaster;
import com.cloud.school.domain.Event_master;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.AttendanceService;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.EventService;
import com.cloud.school.service.UserService;

@Controller
public class EventController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	EventService eventService;
	
	private static final Logger logger = Logger.getLogger(EventController.class);
	
	
	@RequestMapping("/eventManagement")
	public String eventManagement(HttpServletRequest request, Model model) {
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
		
		/*List<?> classList = classService.getAllClassesByCampusId(session.getUser().getCampusId());
		
		model.addAttribute("classList", classList);*/
		
		
		return "eventManagement";
		
	}
	
	// 
	
			@RequestMapping(value = "/getAllEventOfCampus", method = RequestMethod.POST)
			public @ResponseBody DataTables<Object> getAllEventOfCampus(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
				DataTables<Object> rd =  null;
				try {
					Session sess = SessionUtils.getSession(request);
					if(sess!=null){
						Users uProfile = sess.getUser();
						logger.info("UserID : "+uProfile.getLoginId()+" | EventController | getAllEventOfCampus Method. ");
						dataTablesRequest.campusId = uProfile.getCampusId();
						rd = eventService.getAllEventOfCampus(dataTablesRequest);
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
			
			@RequestMapping(value = "/saveEventOrUpdate", method = RequestMethod.POST)
			public @ResponseBody AjaxResponse saveEventOrUpdate(@RequestBody Event_master event_master, HttpServletRequest request,Model model) {
				AjaxResponse rd = new AjaxResponse();
				try {
					Session sess=SessionUtils.getSession(request);
					if(sess!=null){
						Users usr = sess.getUser();
						if(usr.getLoginId()!=null){
							System.out.println("Hello"+event_master.getEvent_name());
							logger.info("UserID : "+usr.getLoginId()+" | EventController | saveEventOrUpdate Method. ");
							event_master.setCampus_id(usr.getCampusId());
							//event_master.setEvent_status("Active");
							eventService.saveUpdateEvent(event_master);
							rd.setStatus(AjaxResponse.SUCCESS);
							rd.setMessage("Event saved successfully.");
						}else{
							rd.setData("Exception");
							rd.setStatus(AjaxResponse.ERROR);
							rd.setMessage("You don't have permission to add or edit this Event.");
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
			
			@RequestMapping(value="/deleteEvent", method = RequestMethod.GET)
			public @ResponseBody AjaxResponse deleteEvent(@RequestParam Long event_id,HttpServletRequest request, Model model) {
				AjaxResponse ajx=new AjaxResponse();
				try{
					Session session = SessionUtils.getSession(request);
			    	if(session!=null)
			    	{
					Users userProfile = session.getUser();
					logger.info("UserID : "+userProfile.getLoginId()+" | EventController | deleteEvent Method. ");
					String objects= eventService.deleteEvent(event_id,userProfile.getCampusId());
					if(objects.equalsIgnoreCase("Success")){
						ajx.setStatus(AjaxResponse.SUCCESS);
						ajx.setData(objects);
						ajx.setMessage("Event deleted successfully.");
					}else{
						ajx.setStatus(AjaxResponse.ERROR);
						ajx.setData(objects);
						ajx.setMessage("Event not deleted.");
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
			
			
}
