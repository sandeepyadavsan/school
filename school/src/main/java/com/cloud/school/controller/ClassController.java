package com.cloud.school.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.cloud.school.domain.DownloadClassSection;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.UserService;

@Controller
public class ClassController {

	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	private static final Logger logger = Logger.getLogger(ClassController.class);
	
	@RequestMapping("/classManagement")
	public String classMethod(HttpServletRequest request, Model model) {
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
		
		return "class";
		
	}

	
	// 
	
	@RequestMapping(value = "/getAllClassesOfCampus", method = RequestMethod.POST)
	public @ResponseBody DataTables<Object> getAllClassesOfCampus(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
		DataTables<Object> rd =  null;
		try {
			Session sess = SessionUtils.getSession(request);
			if(sess!=null){
				Users uProfile = sess.getUser();
				logger.info("UserID : "+uProfile.getLoginId()+" | ClassController | getAllClassesOfCampus Method. ");
				dataTablesRequest.campusId = uProfile.getCampusId();
				rd = classService.getAllClassesOfCampus(dataTablesRequest);
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
	
	@RequestMapping(value = "/saveClassOrUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveClassOrUpdate(@RequestBody ClassMaster classMaster, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | saveClassOrUpdate Method. ");
					classMaster.setCampusId(usr.getCampusId());
					ClassMaster obj = classService.saveUpdateClass(classMaster);
					if(classMaster.getClassId()==null)
						classService.mapSectionClass(usr.getCampusId(), obj.getClassId(), "-1#");
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Class saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit this class.");
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
	
	
	@RequestMapping(value="/deleteClass", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse deleteClass(@RequestParam Long classId,HttpServletRequest request, Model model) {
		AjaxResponse ajx=new AjaxResponse();
		try{
			Session session = SessionUtils.getSession(request);
	    	if(session!=null)
	    	{
			Users userProfile = session.getUser();
			logger.info("UserID : "+userProfile.getLoginId()+" | CLassController | deleteClass Method. ");
			String objects= classService.deleteClass(classId,userProfile.getCampusId());
			if(objects.equalsIgnoreCase("Success")){
				ajx.setStatus(AjaxResponse.SUCCESS);
				ajx.setData(objects);
				ajx.setMessage("Class deleted successfully.");
			}else{
				ajx.setStatus(AjaxResponse.ERROR);
				ajx.setData(objects);
				ajx.setMessage("Class not deleted.");
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
	
	
	
	
	@RequestMapping(value = "/getAllSectionOfCampus", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllSectionOfCampus(HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | getAllSectionOfCampus Method. ");
					List<?> lstData = classService.getAllSectionOfCampus(usr.getCampusId());
					rd.setData(lstData);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Class saved successfully.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to get sections.");
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
	
	
	
	@RequestMapping(value = "/mapSectionClass", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse mapSectionClass(@RequestParam Long classId,@RequestParam String sectionIds,HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | mapSectionClass Method. ");
					
					Long dumySectionPkId = classService.getDumySectionOfClass(classId, usr.getCampusId());
					
					
					System.out.println("dumySectionPkId: "+dumySectionPkId);
					
					if(dumySectionPkId==null)
						dumySectionPkId=(long) 0;
					
					if(dumySectionPkId!=0)
							classService.deleteDumySectionFromClass(dumySectionPkId);
						
					
					int status = classService.mapSectionClass(usr.getCampusId(),classId,sectionIds);
					if(status>0)
					{
					rd.setData(status);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Section mapped successfully.");
					}
					else
					{
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("Something Went Wrong !");
						return rd;
						
					}
					
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to map sections.");
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
	
	
	@RequestMapping(value = "/getAllSectionOfClass", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getAllSectionOfClass(@RequestParam Long classId,HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | getAllSectionOfClass Method. ");
					List<?> lstData = classService.getAllSectionOfClass(classId,usr.getCampusId());
					rd.setData(lstData);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("Sections are as");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to get sections.");
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
	
	
	/****** For Mapped Sections ****/
	@RequestMapping(value = "/getMapSectionByClassId", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse getMapSectionByClassId(@RequestParam Long classId,HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | getMapSectionByClassId Method. ");
					List<?> lstData = classService.getMapSectionByClassId(usr.getCampusId(),classId);
					rd.setData(lstData);
					rd.setStatus(AjaxResponse.SUCCESS);
					rd.setMessage("###.");
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to get sections.");
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
	
	@RequestMapping(value = "/saveNewSectionOrUpdate", method = RequestMethod.POST)
	public @ResponseBody AjaxResponse saveNewSectionOrUpdate(@RequestBody String newSection, HttpServletRequest request,Model model) {
		AjaxResponse rd = new AjaxResponse();
		try {
			Session sess=SessionUtils.getSession(request);
			if(sess!=null){
				Users usr = sess.getUser();
				if(usr.getLoginId()!=null){
					logger.info("UserID : "+usr.getLoginId()+" | ClassController | saveNewSectionOrUpdate Method. ");
					//classMaster.setCampusId(usr.getCampusId());
					int obj = classService.saveUpdateNewSection(newSection.toUpperCase(),usr.getCampusId()); //fast hello
					if(obj==1){
						rd.setStatus(AjaxResponse.SUCCESS);
						rd.setMessage("Section saved successfully.");
					}else{
						rd.setData("Exception");
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("Section name should be unique & in upper case.");
						return rd;
					}
					
				}else{
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("You don't have permission to add or edit this Section.");
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
	@RequestMapping(value = "/downloadCSV",method = RequestMethod.GET)
	public void downloadCSV(HttpServletRequest request,HttpServletResponse response) throws IOException {
 
		AjaxResponse rd = new AjaxResponse();
	      
	      try {
	    	  Long campusId=0L;
	    	  Session sess=SessionUtils.getSession(request);
				if(sess!=null){
					Users usr = sess.getUser();
					if(usr.getLoginId()!=null){
						logger.info("UserID : "+usr.getLoginId()+" | ClassController | downloadCSV Method. ");
						campusId = usr.getCampusId();
						response.setContentType("text/csv");
						String reportName = "CSV_Report_Name.csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						List<DownloadClassSection> lstData = classService.getClassIdSectionIdForDownload(usr.getCampusId());
						String heading="ClassName,ClassId,SectionName,SectionId\n";
						String filedata="";
						System.out.println("Size of List "+lstData.size());
						response.getOutputStream().print(heading);
						if(lstData !=null) {
							for(DownloadClassSection data : lstData) {
								filedata=data.getClassName()+","+data.getClassid()+","+data.getSectionname()+","+data.getSectioid()+"\n";
								System.out.println("Data: "+filedata);
								response.getOutputStream().print(filedata);
							}
							
						}
						
						
						
						
						
						
						
						ArrayList<String> rows = new ArrayList<String>();
						rows.add("Name,Result");
						rows.add("\n");
						
						
						
						
				 
						/*for (int i = 0; i < 10; i++) {
							rows.add("Java Honk,Success");
							rows.add("\n");
						}
				 
						Iterator<?> iter = lstData.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}*/
						//response.getOutputStream().print();
						response.getOutputStream().flush();
						
					      response.flushBuffer();
					}
				}else {
				
					rd.setData("Exception");
					rd.setStatus(AjaxResponse.ERROR);
					rd.setMessage("SessionTimeOut");
					logger.error("Session is not open !!");
				}
	    	  
		} catch (Exception ex) {
			rd.setStatus(AjaxResponse.ERROR);
			rd.setMessage(ex.getMessage());
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
			ex.printStackTrace();
		}
	      
 
	}
}
