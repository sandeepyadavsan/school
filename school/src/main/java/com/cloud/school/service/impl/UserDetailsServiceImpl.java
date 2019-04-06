package com.cloud.school.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.UserDao;
import com.cloud.school.domain.Users;

@Service
@Transactional(readOnly = false)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	public UserDao userDao;

	@Transactional(readOnly = false)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userDao.login(username); 
		if (user != null) {
			String password = user.getPassword();
			
			boolean enabled = false;
			
			if(user.getStatus().equalsIgnoreCase("Active"))
				enabled = true;
			
			
			boolean accountNonExpired = enabled;
			boolean credentialsNonExpired = enabled;
			boolean accountNonLocked =  enabled;
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			org.springframework.security.core.userdetails.User securUser = new org.springframework.security.core.userdetails.User(
					username, password, enabled, accountNonExpired,credentialsNonExpired, accountNonLocked, authorities);
			
			
			return securUser;
		} else {
			//System.out.println("**************\nUserDetailsServiceImpl User Not Found\n*************************");
			throw new UsernameNotFoundException("User Not Found!!!");
		}
	}

}
