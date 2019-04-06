package com.cloud.school.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opensaml.util.resource.ClasspathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.AjaxResponse;
import com.cloud.school.domain.BulkStudent;
import com.cloud.school.domain.ClassSection;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.domain.Student_records;
import com.cloud.school.domain.Users;
import com.cloud.school.service.ClassService;
import com.cloud.school.service.StudentService;
import com.cloud.school.service.UserService;
import com.google.common.io.Resources;

import au.com.bytecode.opencsv.CSVReader;

@Controller
public class StudentController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	ClassService classService;
	
	private static final Logger logger = Logger.getLogger(StudentController.class);
	
	@RequestMapping("/studentManagement")
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
		
		List<?> classList = classService.getAllClassesByCampusId(session.getUser().getCampusId());
		
		model.addAttribute("classList", classList);
		
		
		return "student";
		
	}

	// 
	
		@RequestMapping(value = "/getAllStudentOfCampus", method = RequestMethod.POST)
		public @ResponseBody DataTables<Object> getAllClassesOfCampus(@RequestBody DataTablesRequest dataTablesRequest,HttpServletRequest request, Model model){
			DataTables<Object> rd =  null;
			try {
				Session sess = SessionUtils.getSession(request);
				if(sess!=null){
					Users uProfile = sess.getUser();
					logger.info("UserID : "+uProfile.getLoginId()+" | StudentController | getAllStudentOfCampus Method. ");
					dataTablesRequest.campusId = uProfile.getCampusId();
					rd = studentService.getAllStudentOfCampus(dataTablesRequest);
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
		@RequestMapping(value = "/saveStudentOrUpdate", method = RequestMethod.POST)
		public @ResponseBody AjaxResponse saveClassOrUpdate(MultipartHttpServletRequest mreq, HttpServletRequest request) {
			AjaxResponse rd = new AjaxResponse();
			try {
				Session sess=SessionUtils.getSession(request);
				if(sess!=null){
					String fileName = "";
					Users usr = sess.getUser();
					if(usr.getLoginId()!=null){
						logger.info("UserID : "+usr.getLoginId()+" | StudentController | saveStudentOrUpdate Method. ");
						
						try{
						Iterator<String> itr = mreq.getFileNames();
						if (itr != null && mreq != null && itr.hasNext()) {
							MultipartFile mpf = mreq.getFile(itr.next());
							fileName = mpf.getOriginalFilename();
							if (fileName.trim().toLowerCase().endsWith("png") || fileName.trim().toLowerCase().endsWith("jpg") || fileName.trim().toLowerCase().endsWith("jpeg")) {
								//String filePath = "E:/san/school/photos/"+fileName; 
								String filePath = "/home/ec2-user/devserver/webserver/webapps/school/assets/image/"+fileName;
								
								File file=new File(filePath);
								mpf.transferTo(file);
								
							}
							else
							{
								rd.setData("Exception");
								rd.setStatus(AjaxResponse.ERROR);
								rd.setMessage("Please upload png / jpeg image only.");
								return rd;
							}
						}
						
						}
						catch(Exception err)
						{
							err.printStackTrace();
							rd.setData("Exception");
							rd.setStatus(AjaxResponse.ERROR);
							rd.setMessage("Something went wrong.");
							return rd;
						}
						
						Student_records student_records = new Student_records();
						
						System.out.println("Id: "+mreq.getParameter("student_id"));
						
						if(mreq.getParameter("student_id")!=null && ! mreq.getParameter("student_id").equals(""))
						student_records.setStudent_id(Long.parseLong(mreq.getParameter("student_id")));
						student_records.setClassid(Long.parseLong(mreq.getParameter("classid"))); 
						student_records.setSectionid(Long.parseLong(mreq.getParameter("sectionid")));
						student_records.setForm_number(Long.parseLong(mreq.getParameter("form_number")));
						student_records.setStudent_rollno(Long.parseLong(mreq.getParameter("student_rollno"))); 
						student_records.setStudent_name(mreq.getParameter("student_name"));
						student_records.setStudent_father(mreq.getParameter("student_father"));
						student_records.setStudent_mother(mreq.getParameter("student_mother"));
						student_records.setStudent_dob(mreq.getParameter("student_dob")); 
						student_records.setDate_of_admission(mreq.getParameter("date_of_admission"));
						student_records.setStudent_gender(mreq.getParameter("student_gender"));
						student_records.setStudent_caste(mreq.getParameter("student_caste"));
						student_records.setStudent_current_address(mreq.getParameter("student_current_address")); 
						student_records.setStudent_home_address(mreq.getParameter("student_home_address")); 
						student_records.setStudent_email(mreq.getParameter("student_email")); 
						if(fileName!="")
						{
							student_records.setStudent_picture(fileName);	
						}
						else
						{
							Student_records std= studentService.getStudentById(student_records.getStudent_id(),usr.getCampusId());
							if(std!=null)
								student_records.setStudent_picture(std.getStudent_picture());
						}
						student_records.setMessage_mobile_number(mreq.getParameter("message_mobile_number"));
						student_records.setSession_id(Long.parseLong(mreq.getParameter("session_id")));
						student_records.setStudent_status(mreq.getParameter("student_status"));
						student_records.setCreate_date(mreq.getParameter("create_date"));
						
						student_records.setCampus_id(usr.getCampusId());
						studentService.saveStudentOrUpdate(student_records);
						rd.setStatus(AjaxResponse.SUCCESS);
						rd.setMessage("Student saved successfully.");
					}else{
						rd.setData("Exception");
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("You don't have permission to add or edit this Student.");
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
		
		//Delete Student
		@RequestMapping(value="/deleteStudent", method = RequestMethod.GET)
		public @ResponseBody AjaxResponse deleteStudent(@RequestParam Long studentId,HttpServletRequest request, Model model) {
			AjaxResponse ajx=new AjaxResponse();
			try{
				Session session = SessionUtils.getSession(request);
		    	if(session!=null)
		    	{
				Users userProfile = session.getUser();
				logger.info("UserID : "+userProfile.getLoginId()+" | StudentController | deleteStudent Method. ");
				String objects= studentService.deleteStudent(studentId,userProfile.getCampusId());
				if(objects.equalsIgnoreCase("Success")){
					ajx.setStatus(AjaxResponse.SUCCESS);
					ajx.setData(objects);
					ajx.setMessage("Record deleted successfully.");
				}else{
					ajx.setStatus(AjaxResponse.ERROR);
					ajx.setData(objects);
					ajx.setMessage("Student Record not deleted.");
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
		
		
		// 
		
		@RequestMapping(value="/getStudentById", method = RequestMethod.GET)
		public @ResponseBody AjaxResponse getStudentById(@RequestParam Long studentId,HttpServletRequest request, Model model) {
			AjaxResponse ajx=new AjaxResponse();
			try{
				Session session = SessionUtils.getSession(request);
		    	if(session!=null)
		    	{
				Users userProfile = session.getUser();
				logger.info("UserID : "+userProfile.getLoginId()+" | StudentController | getStudentById Method. ");
				Student_records std= studentService.getStudentById(studentId,userProfile.getCampusId());

				    ajx.setStatus(AjaxResponse.SUCCESS);
					ajx.setData(std);
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
		
		
		//
		
		@RequestMapping(value="/getRollNoFormNobyCampusId", method = RequestMethod.GET)
		public @ResponseBody AjaxResponse getRollNoFormNobyCampusId(@RequestParam Long classId,@RequestParam Long sectionId,HttpServletRequest request, Model model) {
			AjaxResponse ajx=new AjaxResponse();
			try{
				Session session = SessionUtils.getSession(request);
		    	if(session!=null)
		    	{
				Users userProfile = session.getUser();
				logger.info("UserID : "+userProfile.getLoginId()+" | StudentController | getRollNoFormNobyCampusId Method. ");
				
				List<?> std= studentService.getRollNoFormNobyCampusId(userProfile.getCampusId(),classId,sectionId);
					System.out.println(classId+">>>>>>>>>>>>wo >>"+sectionId);
					System.out.print(std);
				    ajx.setStatus(AjaxResponse.SUCCESS);
					ajx.setData(std); 
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
		
		
		@SuppressWarnings("resource")
		@RequestMapping(value = "/uploadStudentCSV", method = RequestMethod.POST)
		public @ResponseBody AjaxResponse uploadStudentCSV(MultipartHttpServletRequest mreq, HttpServletRequest request) {
			AjaxResponse rd = new AjaxResponse();
			System.out.println("Deepak   .... ####");
			try {
				Session session = SessionUtils.getSession(request);
			    	if(session!=null)
			    	{
			    		Users userProfile = session.getUser();
						System.out.println("UserID : "+userProfile.getLoginId()+" | StudentController | uploadStudentCSV Method. ");
						Iterator<String> itr = mreq.getFileNames();
						if (itr != null && mreq != null && itr.hasNext()) {
							MultipartFile mpf = mreq.getFile(itr.next());
							String fileName = userProfile.getLoginId() + "-"+ UUID.randomUUID().toString()+""+mpf.getOriginalFilename();
							if (fileName.trim().toLowerCase().endsWith("csv")) {
								//String filePath = "e:/"+fileName; 
								String filePath = "/home/ec2-user/devserver/BulkReg/"+fileName;
								
								
								File file=new File(filePath);
								mpf.transferTo(file);
								//
								String[] nextLine;
								String[] line;
							    try {
							    	
							    	List<ClassSection> classSecList = classService.getAllExistingClassSectionByCampusId(userProfile.getCampusId());
							    	
							    	    FileReader fileReader = new FileReader(filePath);
							            CSVReader reader = new CSVReader(fileReader, ';', '\'', 1); 
							            
							            List<BulkStudent> lst = new ArrayList<BulkStudent>();
							            String formNumberString = "";
							            String classSectionPattern = "";
							            String formNumberDBString = "";
							            
							            List<Student_records> stdList = studentService.getExistingFormNumbers(userProfile.getCampusId());
							            
							            for(ClassSection obj : classSecList)
							            {
							            	classSectionPattern += "@"+obj.getClassid()+"$"+obj.getSectionid()+"#";
							            }

							            for(Student_records obj : stdList)
							            {
							            	formNumberDBString += "@"+obj.getForm_number()+"#";
							            }
							            System.out.println("Form no. db : "+formNumberDBString);
							            
							            while ((nextLine = reader.readNext()) != null) {
							            	line = nextLine[0].split(",");
							            	
							            	System.out.println("line length: "+line.length);
							            	
							            	if(line.length>9&&line.length<=10){
							            	BulkStudent std = new BulkStudent();
							            			/*System.out.println(line[0]);
								                    System.out.println(line[1]);
								                    System.out.println(line[2]);
								                    System.out.println(line[3]);
								                    System.out.println(line[4]);
								                    System.out.println(line[5]);
								                    System.out.println(line[6]);
								                    System.out.println(line[7]);
								                    System.out.println(line[8]);
								                    System.out.println(line[9]);*/
							            	
							            	        if(classSectionPattern.contains("@"+Long.parseLong(line[0])+"$-1#") && line[1].trim().equalsIgnoreCase(""))
							            	        {
							            	        	std.setClassId(Long.parseLong(line[0]));
							            	        	std.setSectionId(-1l);
							            	        }
							            	        else
							            	        {
							            	        if(classSectionPattern.contains("@"+Long.parseLong(line[0])+"$"+Long.parseLong(line[1])+"#"))
							            	        {
							            	        	std.setClassId(Long.parseLong(line[0]));
							            	        	std.setSectionId(Long.parseLong(line[1]));
							            	        }
							            	        else
							            	        {
							            	        	rd.setStatus(AjaxResponse.ERROR);
														rd.setMessage("Class or section not found.");
								                		return rd;
							            	        	
							            	        }
							            	        }
							            	        
							            	        
								                	std.setStudentName(line[2]);
								                	std.setFatherName(line[3]);
								                	std.setGender(line[4]);
								                	std.setDateOfBirth(line[5]);
								                	std.setDateOfAdmission(line[6]);
								                	std.setMessageMobile(line[7]);
								                	std.setEmailId(line[8]);
								                	
								                	if(line.length>10){
								                		
								                	if(formNumberDBString.contains("@"+Long.parseLong(line[9])+"#"))	
								                	{
								                		rd.setStatus(AjaxResponse.ERROR);
														rd.setMessage("Form number already exist in the records.");
								                		return rd;
								                	}
								                	else
								                	{
								                		
								                		std.setFormNo(line[10]);
								                	}
								                		
								                	
								                	
								                	}
								                	else
								                		std.setFormNo("0");
								                	
								                	if(line.length>10){
								                	if(formNumberString.contains("@"+line[9]+"#"))
								                	{
								                		rd.setStatus(AjaxResponse.ERROR);
														rd.setMessage("Duplicate Form numbers are not allowed.");
								                		return rd;
								                	}
								                	
								                	formNumberString += "@"+line[9]+"#";
								                	
								                	}
								                	lst.add(std);
								                	
								                	
							            	}
							            	else
							            	{
							            		lst = new ArrayList<BulkStudent>();
							            		System.out
														.println("Error while reading student records csv file..."+line.length);
							            		
							            		rd.setStatus(AjaxResponse.ERROR);
												rd.setMessage("Invalid file content.");
						                		return rd;
						                		
							            	}
							            }
							            reader.close();
							            
							          System.out.println("calling .. "+lst);
							          
							            if(lst.size()>0)
							            {
							            	
							            	int sts = studentService.inserBulkStudentRecords(lst, userProfile.getCampusId());
							            	
							            }
							            
							            
							            
							    } catch (Exception e) {
							        e.printStackTrace();
							        rd.setStatus(AjaxResponse.ERROR);
									rd.setMessage("Class id and section id should be numberic.");
									return rd; 
							    } 
								//
								file.delete();
								rd.setStatus(AjaxResponse.SUCCESS);
								rd.setMessage("Successfully Saved.");
								
							} else if(fileName.trim().toLowerCase().endsWith("xlsx") || fileName.trim().toLowerCase().endsWith("xls")){
								
								// xlsx or xls
								
								try{
									
								String commaSepratedString = "";
								String[] line;
								
								    List<BulkStudent> lst = new ArrayList<BulkStudent>();
						            String formNumberString = "";
						            String classSectionPattern = "";
						            String formNumberDBString = "";
						            
						            List<ClassSection> classSecList = classService.getAllExistingClassSectionByCampusId(userProfile.getCampusId());
							    	
						            List<Student_records> stdList = studentService.getExistingFormNumbers(userProfile.getCampusId());
						            
						            for(ClassSection obj : classSecList)
						            {
						            	classSectionPattern += "@"+obj.getClassid()+"$"+obj.getSectionid()+"#";
						            }

						            for(Student_records obj : stdList)
						            {
						            	formNumberDBString += "@"+obj.getForm_number()+"#";
						            }
						            System.out.println("Form no. db : "+formNumberDBString);
								
								String filePath = "e:/"+fileName; 
								File file=new File(filePath);
								mpf.transferTo(file);
								
								FileInputStream inputStream = new FileInputStream(file);
								
								Workbook workbook = null;
								
								if(fileName.trim().toLowerCase().endsWith("xlsx"))
									workbook = new XSSFWorkbook(inputStream);
								else
									workbook = new HSSFWorkbook(inputStream);
								
								Sheet firstSheet = workbook.getSheetAt(0);
						        Iterator<Row> iterator = firstSheet.iterator();
						         
						        while (iterator.hasNext()) {
						            Row nextRow = iterator.next();
						            
						            if(nextRow.getRowNum()==0){
						            	   continue; 
						            	  }
						            
						            Iterator<Cell> cellIterator = nextRow.cellIterator();
						            commaSepratedString = "";
						            
						            while (cellIterator.hasNext()) {
						                Cell cell = cellIterator.next();
						                
						                switch (cell.getCellType()) {
						                    case Cell.CELL_TYPE_STRING:
						                        System.out.print("STR : "+cell.getStringCellValue());
						                        commaSepratedString += cell.getStringCellValue()+",";
						                        break;
						                    case Cell.CELL_TYPE_BOOLEAN:
						                        System.out.print("BOOL : "+cell.getBooleanCellValue());
						                        commaSepratedString += cell.getBooleanCellValue()+",";
						                        break;
						                    case Cell.CELL_TYPE_NUMERIC:
						                        System.out.print("NUM : "+cell.getNumericCellValue());
						                        commaSepratedString += (int)cell.getNumericCellValue()+",";
						                        break;
						                }
						            }
						         
						        
						            line = commaSepratedString.split(",");
						            
						            System.out.println("line : "+line);
						            
						            System.out.println("line length : "+line.length);
						        
						            System.out.println("line : "+line[1]+" and "+line[2]);
						            
						        if(line.length>9&&line.length<12){
					            	BulkStudent std = new BulkStudent();
						                    
					            	 if(classSectionPattern.contains("@"+Long.parseLong(line[0])+"$-1#") && line[1].trim().equalsIgnoreCase(""))
				            	        {
				            	        	std.setClassId(Long.parseLong(line[0]));
				            	        	std.setSectionId(-1l);
				            	        }
				            	        else
				            	        {
					            	        if(classSectionPattern.contains("@"+Long.parseLong(line[0])+"$"+Long.parseLong(line[1])+"#"))
					            	        {
					            	        	std.setClassId(Long.parseLong(line[0]));
					            	        	std.setSectionId(Long.parseLong(line[1]));
					            	        }
					            	        else
					            	        {
					            	        	rd.setStatus(AjaxResponse.ERROR);
												rd.setMessage("Class or section not found.");
						                		return rd;
					            	        	
					            	        }
						                	
				            	        }
						                	std.setStudentName(line[2]);
						                	std.setFatherName(line[3]);
						                	std.setGender(line[4]);
						                	std.setDateOfBirth(line[5]);
						                	std.setDateOfAdmission(line[6]);
						                	std.setMessageMobile(line[7]);
						                	std.setEmailId(line[8]);
						                	
						                	if(line.length>10){
						                		
						                	if(formNumberDBString.contains("@"+Long.parseLong(line[9])+"#"))	
						                	{
						                		rd.setStatus(AjaxResponse.ERROR);
												rd.setMessage("Form number already exist in the records.");
						                		return rd;
						                	}
						                	else
						                	{
						                		
						                		std.setFormNo(line[9]);
						                	}
						                		
						                	
						                	
						                	}
						                	else
						                		std.setFormNo("0");
						                	
						                	if(line.length>10){
						                	if(formNumberString.contains("@"+line[9]+"#"))
						                	{
						                		rd.setStatus(AjaxResponse.ERROR);
												rd.setMessage("Duplicate Form numbers are not allowed.");
						                		return rd;
						                	}
						                	
						                	formNumberString += "@"+line[9]+"#";
						                	
						                	}
						                	lst.add(std);
						                	
						                	
					            	}
					            	else
					            	{
					            		lst = new ArrayList<BulkStudent>();
					            		System.out
												.println("Error while reading student records csv file..."+line.length);
					            		
					            		rd.setStatus(AjaxResponse.ERROR);
										rd.setMessage("Invalid file content.");
				                		return rd;
					            		
					            		
					            	}
						        
						        
						        }
						        
						        workbook.close();
						        inputStream.close();
								
						        if(lst.size()>0)
					            {
					            	
					            	int sts = studentService.inserBulkStudentRecords(lst, userProfile.getCampusId());
					            	
					            }
						        
						        file.delete();
								rd.setStatus(AjaxResponse.SUCCESS);
								rd.setMessage("Successfully Saved xlsx.");
								
								
								 } catch (Exception e) {
								        e.printStackTrace();
								        rd.setStatus(AjaxResponse.ERROR);
										rd.setMessage("Class id and section id should be numeric.");
										return rd; 
								    } 
								
								
							} else {
								rd.setStatus(AjaxResponse.ERROR);
								rd.setMessage("Please select valid '.csv' or '.xlsx' file.");
							}
						} else {
							rd.setStatus(AjaxResponse.ERROR);
							rd.setMessage("Please Select File.");
						}
					}else{
						rd.setData("");
						rd.setStatus(AjaxResponse.ERROR);
						rd.setMessage("SessionTimeOut");
					}
			} catch (Exception ex) {
				rd.setStatus(AjaxResponse.ERROR);
				rd.setMessage("Unable to process ,Please try again");
				StringWriter sw = new StringWriter();
				ex.printStackTrace(new PrintWriter(sw));
				logger.error(sw.toString());
				ex.printStackTrace();
			}
			return rd;
		}
}
