package com.cloud.school.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloud.school.domain.Session;
import com.cloud.school.service.UserService;

@Controller
public class LoginController {

	
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger .getLogger(LoginController.class);
	
	@RequestMapping(value = "/PageNotFound")
	public  String nofound(HttpServletRequest request, Model model) {
		request.getSession().invalidate();
		logger.info("*********** 404 Page Not Found !!!!!!!!!!!!");
		return "PageNotFound";
	}
	
	@RequestMapping(value = "/accessDenied")
	public  String accessDenied(HttpServletRequest request, Model model) {
		request.getSession().invalidate();
		logger.info("*********** Access Denied !!!!!!!!!!!!");
		return "accessDenied";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model){
		Session session =SessionUtils.getSession(request);
		logger.info("*********** Login Method ************");
		if(session==null) {
		String prm = request.getParameter("login_error");
		String err_msg = "";
		String msgtitle = "Login Failed";
		if (prm != null) {
			if (prm.equals("1")) {
				err_msg = "Wrong username or password.";
				model.addAttribute("etype", 1);
				model.addAttribute("msgtitle", msgtitle);
				model.addAttribute("err_msg", err_msg);
				logger.error("Login Failed.");
			}else if (prm.equals("2")) {
				err_msg = "You are not authorized to access this module.";
				model.addAttribute("etype", 2);
				model.addAttribute("msgtitle", msgtitle);
				model.addAttribute("err_msg", err_msg);
				logger.error("You are not authorized to access this module.");
			}else if (prm.equals("4")) {
				msgtitle = "Error";
				err_msg = "Session has been expired. please Relogin.";
				model.addAttribute("etype", 4);
				model.addAttribute("msgtitle", msgtitle);
				model.addAttribute("err_msg", err_msg);
				logger.error("Session has been expired. please Relogin.");
			}else if (prm.equals("5")) {
				msgtitle = "Error";
				model.addAttribute("etype", 5);
				model.addAttribute("msgtitle", msgtitle);
				model.addAttribute("err_msg", err_msg);
				err_msg = "your account is login by another person. please login again.";
				logger.error("your account is login by another person. please login again.");
			}
		}
		
		return "login";
	 }else{
		logger.info("UserID : "+session.getUser().getLoginId()+" | Login Method session exist. | redirect to Home.");
		return "redirect:home.html";
	}
  }

}
