package com.cloud.school.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Session;
import com.cloud.school.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;

	//private static final Logger logger = Logger
			//.getLogger(LoginController.class);

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String process(HttpServletRequest request, Model model) {
		System.out.println("In process ...");
		Session session = SessionUtils.createSession(request, userService);
		try{
			
			
			 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
				
			 System.out.println("userType: "+userType+" and : "+session.getUser().getType());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:home.html";
	}

	@RequestMapping("/home")
	public String dashboard(HttpServletRequest request, Model model) {
		Session session = SessionUtils.getSession(request);
		if (session == null) {
			return "redirect:login.html?login_error=4";
		}
		 String userType = (session.getUser().getType().equalsIgnoreCase("1"))?"1":"0";
		
		 System.out.println("userType: "+userType+" and : "+session.getUser().getType());
		 
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
		
		return "home";
	}


}
