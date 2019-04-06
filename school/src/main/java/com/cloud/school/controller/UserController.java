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
import org.springframework.web.servlet.ModelAndView;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.AjaxResponse;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		List<MenuMaster> mainMenu = userService.getMainMenus(userType);
		List<MenuMaster> subMenu = userService.getSubMainMenus(userType);
		
		model.addAttribute("mainMenuList", mainMenu);
		model.addAttribute("subMenuList", subMenu);
		List<?> campusList = userService.getAllCampus();
		model.addAttribute("campusList", campusList);
		
		
		return "home";
		
	}
	
	
	
	
	@RequestMapping(value = "/userManagement", method = RequestMethod.GET)
	public String userManagement(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		
        String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		List<MenuMaster> mainMenu = userService.getMainMenus(userType);
		List<MenuMaster> subMenu = userService.getSubMainMenus(userType);
		
		model.addAttribute("mainMenuList", mainMenu);
		model.addAttribute("subMenuList", subMenu);
		
		List<?> campusList = userService.getAllCampus();
		model.addAttribute("campusList", campusList);
		
		return "userManagement";
		
	}
	
	@RequestMapping(value = "/findAllUsers", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> findUserByCompanyId(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				request.getSession().setAttribute("datatablereqq", dataTablesRequest);
				logger.info("UserID : "+uProfile.getLoginId()+" | HomeController | findUserByCompanyId Method. ");
				rd = userService.findAllUsers(dataTablesRequest);
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
	
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveUser(@RequestBody Users user, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | UserController | saveUser Method. ");
					user.setUserName(user.getUserName().toLowerCase());
					userService.saveUser(user);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("User saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit user.");
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
	
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse deleteClientEmployee(@RequestParam Long userId,HttpServletRequest request, Model model) {
		AjaxResponse ajx=new AjaxResponse();
		try{
			Session session = SessionUtils.getSession(request);
	    	if(session!=null)
	    	{
			Users userProfile = session.getUser();
			logger.info("UserID : "+userProfile.getLoginId()+" | ClientEmployeeController | deleteClientEmployee Method. ");
			String objects= userService.deleteClientEmployee(userId);
			if(objects.equalsIgnoreCase("Success")){
				ajx.setStatus(AjaxResponse.SUCCESS);
				ajx.setData(objects);
				ajx.setMessage("User deleted successfully.");
			}else{
				ajx.setStatus(AjaxResponse.ERROR);
				ajx.setData(objects);
				ajx.setMessage("User not deleted.");
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
	
	
	
	// download
	
			@RequestMapping(value = "/download", method = RequestMethod.GET)
			public ModelAndView getExcel(HttpServletRequest request, Model model) {
				
				DataTablesRequest dataTablesRequest= (DataTablesRequest)request.getSession().getAttribute("datatablereqq");
				List<?> userList =  userService.findAllUserList(dataTablesRequest);
				return new ModelAndView("UserExcelListView", "userList", userList);
			}
			
			
			
			@RequestMapping(value="/resetUserPassword", method = RequestMethod.GET)
			public @ResponseBody AjaxResponse resetUserPassword(@RequestParam Long userId,@RequestParam Long campusId,@RequestParam String pwd,@RequestParam String c_pwd,HttpServletRequest request, Model model) {
				System.out.println("callllllllllllllllllll"+userId);
				AjaxResponse ajx=new AjaxResponse();
				try{
					Session session = SessionUtils.getSession(request);
			    	if(session!=null)
			    	{
					Users userProfile = session.getUser();
					
					if(userProfile!=null&&userId!=null)
					{
					logger.info("UserID : "+userProfile.getLoginId()+" | UserController | resetUserPassword Method. ");
					if(pwd.equals(c_pwd))
					{
					String objects= userService.resetUserPassword(userId,campusId,pwd);
					if(objects.equalsIgnoreCase("Success")){
						ajx.setStatus(AjaxResponse.SUCCESS);
						ajx.setData(objects);
						ajx.setMessage("Password changed successfully.");
					}else{
						ajx.setStatus(AjaxResponse.ERROR);
						ajx.setData(objects);
						ajx.setMessage("Unable to reset password.");
					}
					}
					else{
						ajx.setData("");
						ajx.setStatus(AjaxResponse.ERROR);
						ajx.setMessage("Password & confirm password do not match.");
					}
			    	}
					else{
						ajx.setData("");
						ajx.setStatus(AjaxResponse.ERROR);
						ajx.setMessage("invalid user or campus.");
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
