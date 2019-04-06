package com.cloud.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.school.dao.UserDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Users;
import com.cloud.school.service.UserService;

@SuppressWarnings("deprecation")
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
	public Users findUserByUserName(String username) {
		return userDao.findUserByUserName(username);
	}
    
    @Transactional(readOnly = true)
    public Users login(String username){
    	return userDao.login(username);
    }
    

    @Transactional(readOnly = true)
	public DataTables<Object> findAllUsers(DataTablesRequest dataTablesRequest) {
		return userDao.findAllUsers(dataTablesRequest);
	}

    @Transactional(readOnly = false)
	public Users saveUser(Users user){
    		user.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
		return userDao.saveUser(user);
	}

    @Transactional(readOnly = false)
	public String deleteClientEmployee(Long userId) {
		return userDao.deleteClientEmployee(userId);
	}

    @Transactional(readOnly = true)
	public List<MenuMaster> getMainMenus(String userType) {
		return userDao.getMainMenus(userType);
	}

    @Transactional(readOnly = true)
	public List<MenuMaster> getSubMainMenus(String userType) {
		return userDao.getSubMainMenus(userType);
	}

    @Transactional(readOnly = true)
	public List<?> findAllUserList(DataTablesRequest dataTablesRequest) {
		return userDao.findAllUserList( dataTablesRequest);
	}
    @Transactional(readOnly = true)
	public List<?> getAllCampus() {
		// TODO Auto-generated method stub
		return userDao.getAllCampus();
	}

    @Transactional(readOnly = false)
	public String resetUserPassword(Long userId, Long campusId, String pwd) {
		// TODO Auto-generated method stub
		pwd = passwordEncoder.encodePassword(pwd, null);
		return userDao.resetUserPassword( userId,  campusId,  pwd);
	}

  

    
    

}
