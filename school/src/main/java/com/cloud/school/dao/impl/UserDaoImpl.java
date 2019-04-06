package com.cloud.school.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloud.school.dao.UserDao;
import com.cloud.school.datatable.DataTables;
import com.cloud.school.datatable.DataTablesRequest;
import com.cloud.school.domain.MenuMaster;
import com.cloud.school.domain.Users;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	public SessionFactory sessionFactory;
	
	public Users findUserByUserName(String username) {
		Users user= (Users) this.sessionFactory.getCurrentSession()
				.createQuery("from Users u where lower(u.userName)= :username")
				.setParameter("username", username.toLowerCase()).uniqueResult();
		return user;
	}
	public Users login(String username) {
	     return (Users)	this.sessionFactory.getCurrentSession()
				.createQuery("select new Users(u.loginId, u.userName, u.password, u.status, u.campusId, u.type,u.empCode) from Users u where lower(u.userName)=:username")
			    .setParameter("username", username.toLowerCase()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public DataTables<Object> findAllUsers(DataTablesRequest dataTablesRequest) {
		
		String search = "";
		String srchText = dataTablesRequest.sSearch;
		if (srchText.length() > 0) {
			srchText = srchText.toUpperCase();
			for (int i = 0; i < dataTablesRequest.abSearchable.size()
					&& i < dataTablesRequest.amDataProp.size(); i++) {
				boolean bl = dataTablesRequest.abSearchable.get(i);
				String cl = dataTablesRequest.amDataProp.get(i);
				if (bl) {
					if (i == 1)  {
						search = search + " and (upper(u." + cl + ") like '%"
								+ srchText + "%'";
					}
					else if(i == 3)
						{
						search = search + " or (upper(u." + cl + ") like '%"
								+ srchText + "%'";
						}else if (i == 2){
						
							search = search + " or upper(cm." + cl + ") like '%"
									+ srchText + "%'";	
						
					}
				}
			}
			search = search + " ))";
		}
		
		System.out.println("search: "+search);
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);
		DataTables<Object> dtl = new DataTables<Object>();
		int totalrec = ((Long) this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) totalUsers FROM users u,campus_master cm where cm.campus_id=u.campus_id "+search).addScalar("totalUsers",LongType.INSTANCE).uniqueResult()).intValue();
		
		
		
		/*List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT u.*,cm.campus_name FROM users u,campus_master cm where cm.campus_id=u.campus_id true "+search+"  GROUP BY u.login_id  order by u." + colm + " " + ord)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		*/
		
		List<Object> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT u.*,cm.campus_name FROM users u,campus_master cm where cm.campus_id=u.campus_id "+search)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		dtl.aaData = lst;
		dtl.iTotalRecords = totalrec;
		dtl.iTotalDisplayRecords = totalrec;
		return dtl;
		
	}
	public Users saveUser(Users user) {
		return (Users) this.sessionFactory.getCurrentSession().merge(user);
	}
	public String deleteClientEmployee(Long userId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("delete from Users where loginId=?").setParameter(0, userId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}
	@SuppressWarnings("unchecked")
	public List<MenuMaster> getMainMenus(String userType) {
		
		List<MenuMaster> menuList = null;
		menuList = this.sessionFactory.getCurrentSession()
		.createSQLQuery("SELECT mm.menu_id,mm.menu_name,mm.menu_link,mm.parent_menu,mm.disable_icon,mm.menu_icon,mm.user_type,mm.sort_number FROM menu_master mm WHERE mm.parent_menu=0 AND mm.disable_icon=0 AND mm.user_type=? ORDER BY mm.sort_number ASC")
		.addScalar("menu_id", LongType.INSTANCE)
		.addScalar("menu_name",StringType.INSTANCE)
		.addScalar("menu_icon", StringType.INSTANCE)
		.addScalar("menu_link", StringType.INSTANCE)
		.addScalar("parent_menu", LongType.INSTANCE)
		.addScalar("disable_icon", LongType.INSTANCE)
		.addScalar("user_type", LongType.INSTANCE)
		.addScalar("sort_number", LongType.INSTANCE)
		.setParameter(0, userType)
		.setResultTransformer(Transformers.aliasToBean(MenuMaster.class)).list();
		
		if(menuList==null)
			menuList = new ArrayList<MenuMaster>();
		
		return menuList;
	}
	@SuppressWarnings("unchecked")
	public List<MenuMaster> getSubMainMenus(String userType) {
		List<MenuMaster> menuList = null;
		menuList = this.sessionFactory.getCurrentSession()
		.createSQLQuery("SELECT mm.menu_id,mm.menu_name,mm.menu_link,mm.parent_menu,mm.disable_icon,mm.menu_icon,mm.user_type,mm.sort_number FROM menu_master mm WHERE mm.parent_menu IN (SELECT mm1.menu_id FROM menu_master mm1 WHERE mm1.parent_menu=0 AND mm1.user_type=:userType AND mm1.disable_icon=0 ORDER BY mm1.sort_number ASC) AND mm.user_type=:userType AND mm.disable_icon=0 ORDER BY mm.sort_number ASC")
		.addScalar("menu_id", LongType.INSTANCE)
		.addScalar("menu_name",StringType.INSTANCE)
		.addScalar("menu_icon", StringType.INSTANCE)
		.addScalar("menu_link", StringType.INSTANCE)
		.addScalar("parent_menu", LongType.INSTANCE)
		.addScalar("disable_icon", LongType.INSTANCE)
		.addScalar("user_type", LongType.INSTANCE)
		.addScalar("sort_number", LongType.INSTANCE)
		.setParameter("userType", userType)
		.setResultTransformer(Transformers.aliasToBean(MenuMaster.class)).list();
		
		if(menuList==null)
			menuList = new ArrayList<MenuMaster>();
		
		return menuList;
	}
	
	public List<?> findAllUserList(DataTablesRequest dataTablesRequest) {
		String search = "";
		String srchText = dataTablesRequest.sSearch;
		if (srchText.length() > 0) {
			srchText = srchText.toUpperCase();
			for (int i = 0; i < dataTablesRequest.abSearchable.size()
					&& i < dataTablesRequest.amDataProp.size(); i++) {
				boolean bl = dataTablesRequest.abSearchable.get(i);
				String cl = dataTablesRequest.amDataProp.get(i);
				if (bl) {
					if (i == 0) {
						search = search + " and (upper(u." + cl + ") like '%"
								+ srchText + "%'";
					} else {
						
							search = search + " or upper(u." + cl + ") like '%"
									+ srchText + "%'";	
						
					}
				}
			}
			search = search + " )";
		}
		
		int currPosition = dataTablesRequest.iDisplayStart;
		int pageSize = dataTablesRequest.iDisplayLength;
		String ord = dataTablesRequest.asSortDir.get(0);
		String colm = dataTablesRequest.amDataProp.get(dataTablesRequest.iSortingCols - 1);
		
		List<?> lst = this.sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT * FROM users u where true "+search+"  GROUP BY u.login_id  order by u." + colm + " " + ord)
				.setMaxResults(pageSize).setFirstResult(currPosition).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		
		
		return lst;
		
	
	}
	public List<?> getAllCampus() {
		List<?> lstData = this.sessionFactory.getCurrentSession().createSQLQuery("SELECT campus_id,campus_name,campus_address,pincode,campus_phone,campus_logo,created_date,pushurl,status from campus_master where status='Active'")
				 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
		return lstData;
	}
	public String resetUserPassword(Long userId, Long campusId, String pwd) {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery("UPDATE users SET  PASSWORD=? WHERE login_id=? AND campus_id=? AND TYPE='0' ")
				.setParameter(0, pwd)
				.setParameter(1, userId)
				.setParameter(2, campusId);
		int res = query.executeUpdate();
		if (res >= 1) {
			return "Success";
		} else {
			return "Fail.";
		}
	}
	
}
