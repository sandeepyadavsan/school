package com.cloud.school.service;

import java.util.List;

import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Users;

public interface UserService {
	Users findUserByUserName(String username);
	Users login(String username);
	DataTables<Object> findAllUsers(DataTablesRequest dataTablesRequest);
	Users saveUser(Users user);
	String deleteClientEmployee(Long userId);
	List<MenuMaster> getMainMenus(String userType);
	List<MenuMaster> getSubMainMenus(String userType);
	List<?> findAllUserList(DataTablesRequest dataTablesRequest);
	List<?> getAllCampus();
	String resetUserPassword(Long userId, Long campusId, String pwd);
	
}
