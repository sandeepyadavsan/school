package com.cloud.school.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.cloud.school.domain.Session;
import com.cloud.school.domain.Users;
import com.cloud.school.service.UserService;

@Controller
public class SessionUtils {

	private static final Logger logger = Logger.getLogger(SessionUtils.class);

	public static Session getSession(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Session session =null;
		
		System.out.println("httpSession : "+httpSession);
		
		if(httpSession!=null){
			Object obj = httpSession.getAttribute("userses");
			System.out.println("Obj : "+obj);
			
			if( obj instanceof Session){
				System.out.println("Yes instanceof Session");
				session=(Session) obj;
			}
		}
		return session;
	}

	public static void changeUserSession(HttpServletRequest request,Session session) {
		session.setUser(session.getUser());
		request.getSession().removeAttribute("userses");
		request.getSession().setAttribute("userses", session);

	}

	/*public static Session createNewWellnessSession(HttpServletRequest request, Users user) {
		Session session = new Session();
		session.setUser(user);
		session.setUser(session.getUser());
		request.getSession().setAttribute("userses", session);

		if (session != null)
			logger.info("UserID : " + session.getUser().getLoginId()+ " | RequestUtils | createNewWellnessSession Method.");
		
		return session;
	}*/

	public static Session createSession(HttpServletRequest request, UserService userService) {
		System.out.println("Create session");
		Session session = getSession(request);
		System.out.println("getSession : "+session);
		if (session == null) {
			
			System.out.println("Create New session");
			
			String username = request.getUserPrincipal().getName();
			
			System.out.println("createSession for : "+username);
			
			try {
				Users user = userService.findUserByUserName(username);
				
				System.out.println("User : "+user);
						
				if (user != null) {
					session = new Session();
					session.setUser(user);
				}
				request.getSession().setAttribute("userses", session);
				
				if (session != null)
					logger.info("UserID : "+ session.getUser().getLoginId()+ " | RequestUtils | createSession Method.");

			} catch (Exception e) {
				logger.error(" RequestUtils | createSession Method | unexpected error."+ e.getMessage());
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.error(sw.toString());
			}

		}
		return session;
	}

}
