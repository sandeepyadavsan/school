package com.cloud.school.controller;


import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy =new DefaultRedirectStrategy();
	@Override
	public void handle(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String info=request.getParameter("browserinfo");
		System.out.println("browserinfo : "+info);
		request.getSession().setAttribute("browserinfo",info);
        if (response.isCommitted()) {
            System.out.println("************\nCan't redirect\n***************************");
            return;
        }
        String targetUrl = "";
        boolean isUser = false;
	    User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    Iterator<GrantedAuthority> iterator=user.getAuthorities().iterator();
        while (iterator.hasNext()) {
           GrantedAuthority grantedAuthority = iterator.next();
           String roles=grantedAuthority.getAuthority();
           if(roles.contains("ROLE_USER")){
        	   isUser = true;
           }
        }
        if(!isUser){
        	targetUrl="/login.html?login_error=2";
        }else{
           request.getSession().setAttribute("remoteAddress",request.getRemoteAddr());
           request.getSession().setAttribute("user",user);
           targetUrl="/process.html";        	  
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);	
		
	}
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
	  this.redirectStrategy = redirectStrategy;
	}
	protected RedirectStrategy getRedirectStrategy() {
	  return redirectStrategy;
	}
	

}
