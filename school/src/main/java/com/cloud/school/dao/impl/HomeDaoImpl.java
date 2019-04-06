package com.cloud.school.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloud.school.dao.HomeDao;

@Repository
public class HomeDaoImpl implements HomeDao {
	/*@Autowired
	public SessionFactory sessionFactory;*/

	/*public Map<String, Object> getDashBoardAllData(Long companyid,
			String startDate, String endDate, String actn, String timmezone) {
		Query query = null;
		Query query1 = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (actn.equalsIgnoreCase("All_Time")) {
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							" SELECT CAST(IFNULL((SELECT COUNT(r.user_id) FROM users r INNER JOIN authorities a1 ON r.username = a1.username WHERE a1.authority='ROLE_USER' AND r.company_id=:companyId ),0) AS SIGNED) Register,CAST(IFNULL(chl.AllChallenge,0) AS SIGNED) AllChallenge,CAST(IFNULL(rwrd.IncentivePoint,0) AS SIGNED) IncentivePoint "
									+ " FROM company c LEFT OUTER JOIN users u ON c.company_id = u.company_id left outer join authorities a on a.username=u.username  "
									+ " LEFT OUTER JOIN (SELECT CONVERT(COUNT(c.challenge_id), char) AllChallenge,c.Company_id  "
									+ " FROM challenge c INNER JOIN challenge_date cd ON c.challenge_id = cd.challenge_id  GROUP BY c.Company_id) chl ON chl.Company_id=c.company_id "
									+ " LEFT OUTER JOIN (SELECT  "
									+ "    CONVERT(IFNULL(SUM(IFNULL(ucp.points,0)), '0'), char) IncentivePoint , u.company_id  "
									+ "  FROM users u  "
									+ "     LEFT OUTER JOIN (SELECT pp.user_id,SUM(pp.point) points FROM  user_campaign_point pp  GROUP BY pp.user_id) ucp "
									+ "     ON ucp.user_id = u.user_id  "
									+ "  WHERE u.Company_id =:companyId) rwrd ON c.company_id=rwrd.company_id  "
									+ " WHERE c.company_id=:companyId AND a.authority='ROLE_USER' GROUP BY c.company_id ")
					.setParameter("companyId", companyid);
			query1 = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT ifnull(SUM(tousr.steps),0) steps,ROUND((ifnull(SUM(tousr.distance),0)*0.621371)) distance,ifnull(SUM(tousr.actmin),0) actmin,ROUND(ifnull(SUM(tousr.calories),0),0) calories,ifnull(COUNT(tousr.user_id),0) activityTrack "
									+ " FROM users u LEFT OUTER JOIN  "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username  "
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  GROUP BY u.company_id")
					.setParameter("companyId", companyid)
					.setParameter("tz", timmezone);
		} else if (actn.equalsIgnoreCase("Year_To_Date")) {

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							" SELECT CAST(IFNULL((SELECT COUNT(r.user_id) FROM users r INNER JOIN authorities a1 ON r.username = a1.username WHERE r.company_id=:companyId and a1.authority='ROLE_USER' AND year(date(r.created_date))= YEAR(date(:ydate)) ),0) AS SIGNED) Register,CAST(IFNULL(chl.AllChallenge,0) AS SIGNED) AllChallenge,CAST(IFNULL(rwrd.IncentivePoint,0) AS SIGNED) IncentivePoint "
									+ "  FROM company c LEFT OUTER JOIN users u ON c.company_id = u.company_id left outer join authorities a on a.username=u.username   "
									+ " LEFT OUTER JOIN (SELECT CONVERT(COUNT(c.challenge_id), char) AllChallenge,c.Company_id  "
									+ " FROM challenge c INNER JOIN challenge_date cd ON c.challenge_id = cd.challenge_id  "
									+ " where year(date(cd.Challenge_start_date))=  YEAR(date(:ydate))  GROUP BY c.Company_id) chl ON chl.Company_id=c.company_id "
									+ " LEFT OUTER JOIN (SELECT  "
									+ "     CONVERT(IFNULL(SUM(IFNULL(ucp.points,0)), '0'), char) IncentivePoint,u.company_id   "
									+ "   FROM users u  "
									+ "    LEFT OUTER JOIN (SELECT pp.user_id,SUM(pp.point) points FROM  user_campaign_point pp WHERE year( DATE(pp.log_date))= YEAR(date(:ydate)) GROUP BY pp.user_id) ucp "
									+ "    ON ucp.user_id = u.user_id  "
									+ "  WHERE u.Company_id =:companyId) rwrd ON c.company_id=rwrd.company_id  "
									+ " WHERE c.company_id=:companyId AND a.authority='ROLE_USER' GROUP BY c.company_id")
					.setParameter("companyId", companyid)
					.setParameter("ydate", startDate);
			query1 = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT ifnull(SUM(tousr.steps),0) steps,ROUND((ifnull(SUM(tousr.distance),0)*0.621371)) distance,ifnull(SUM(tousr.actmin),0) actmin,ROUND(ifnull(SUM(tousr.calories),0),0) calories,ifnull(COUNT(tousr.user_id),0) activityTrack "
									+ " FROM users u LEFT OUTER JOIN  "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1)  and YEAR(date(as1.data_date))=YEAR(date(:ydate))  GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al where YEAR(date(al.log_date))=YEAR(date(:ydate)) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'   GROUP BY u.company_id")
					.setParameter("companyId", companyid)
					.setParameter("ydate", startDate)
					.setParameter("tz", timmezone);
		} else if (actn.equalsIgnoreCase("Custom_Date_Range")) {
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							" SELECT CAST(IFNULL((SELECT COUNT(r.user_id) FROM users r INNER JOIN authorities a1 ON r.username = a1.username WHERE r.company_id=:companyId and a1.authority='ROLE_USER' AND date(r.created_date) between date(:startDate) and date(:endDate) ),0) AS SIGNED) Register,CAST(IFNULL(chl.AllChallenge,0) AS SIGNED) AllChallenge,CAST(IFNULL(rwrd.IncentivePoint,0) AS SIGNED) IncentivePoint "
									+ " FROM company c LEFT OUTER JOIN users u ON c.company_id = u.company_id left outer join authorities a on a.username=u.username   "
									+ " LEFT OUTER JOIN (SELECT CONVERT(COUNT(c.challenge_id), char) AllChallenge,c.Company_id  "
									+ " FROM challenge c INNER JOIN challenge_date cd ON c.challenge_id = cd.challenge_id  "
									+ " where date(cd.Challenge_start_date)  between date(:startDate) and date(:endDate)  GROUP BY c.Company_id) chl ON chl.Company_id=c.company_id "
									+ " LEFT OUTER JOIN (SELECT  "
									+ "     CONVERT(IFNULL(SUM(IFNULL(ucp.points,0)), '0'), char) IncentivePoint,u.company_id   "
									+ "   FROM users u  "
									+ "       LEFT OUTER JOIN (SELECT pp.user_id,SUM(pp.point) points FROM  user_campaign_point pp WHERE DATE(pp.log_date) BETWEEN DATE(:startDate) AND DATE(:endDate) GROUP BY pp.user_id) ucp "
									+ "      ON ucp.user_id = u.user_id  "
									+ "  WHERE u.Company_id =:companyId) rwrd ON c.company_id=rwrd.company_id  "
									+ " WHERE c.company_id=:companyId AND a.authority='ROLE_USER' GROUP BY c.company_id ")
					.setParameter("companyId", companyid)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);
			query1 = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT ifnull(SUM(tousr.steps),0) steps,ROUND((ifnull(SUM(tousr.distance),0)*0.621371)) distance,ifnull(SUM(tousr.actmin),0) actmin,ROUND(ifnull(SUM(tousr.calories),0),0) calories,ifnull(COUNT(tousr.user_id),0) activityTrack "
									+ " FROM users u LEFT OUTER JOIN  "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1)  and date(as1.data_date)  between date(:startDate)and date(:endDate)  GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al where date(al.log_date)  between date(:startDate) and date(:endDate) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  GROUP BY u.company_id")
					.setParameter("companyId", companyid)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("tz", timmezone);
		}
		Object summary = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		Object activity = query1.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		map.put("sumary", summary);
		map.put("activity", activity);
		return map;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, List<Object>> getDashBoardData(Long companyid,
			String startDate, String endDate, String actn, String timmezone) {
		HashMap<String, List<Object>> map = new HashMap<String, List<Object>>();
		Query query = null;
		if (actn.equalsIgnoreCase("All_Time")) {
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT "
									+ " IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname, "
									+ " IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,  "
									+ " IFNULL(DATE_FORMAT(u.created_date, '%d-%b-%Y'), 'NA') regdate "
									+ " FROM users u left outer join authorities a on a.username=u.username "
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									+ " WHERE u.Company_id = :companyid  AND a.authority='ROLE_USER' "
									+ " ORDER BY DATE(u.created_date) DESC LIMIT 100")
					.setParameter("companyid", companyid);
			List<Object> objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("Register", objCals);
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.steps,0) steps "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE al.log_date GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY steps DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("steps", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.actmin,0) actmin "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE al.log_date GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER' AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY actmin DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("actmin", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,ROUND((IFNULL(tousr.distance,0)*0.621371),2) distance "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE al.log_date GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER' AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY distance DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("distance", objCals);

		} else if (actn.equalsIgnoreCase("Year_To_Date")) {
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT "
									+ " IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname, "
									+ " IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,  "
									+ " IFNULL(DATE_FORMAT(u.created_date, '%d-%b-%Y'), 'NA') regdate "
									+ " FROM users u left outer join authorities a on a.username=u.username "
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									+ " WHERE u.Company_id = :companyid AND a.authority='ROLE_USER' and YEAR(DATE(u.created_date))=YEAR(DATE(:ydate)) "
									+ " ORDER BY DATE(u.created_date) DESC LIMIT 100")
					.setParameter("companyid", companyid)
					.setParameter("ydate", startDate);
			List<Object> objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("Register", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.steps,0) steps "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND YEAR(DATE(as1.data_date))=YEAR(DATE(:ydate)) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE YEAR(DATE(al.log_date))=YEAR(DATE(:ydate)) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username  "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER' AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY steps DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("ydate", startDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("steps", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.actmin,0) actmin "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND YEAR(DATE(as1.data_date))=YEAR(DATE(:ydate)) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE YEAR(DATE(al.log_date))=YEAR(DATE(:ydate)) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "

									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY actmin DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("ydate", startDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("actmin", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,ROUND((IFNULL(tousr.distance,0)*0.621371),2) distance "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND YEAR(DATE(as1.data_date))=YEAR(DATE(:ydate)) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE YEAR(DATE(al.log_date))=YEAR(DATE(:ydate)) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY distance DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("ydate", startDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("distance", objCals);

		} else if (actn.equalsIgnoreCase("Custom_Date_Range")) {
			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT "
									+ " IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname, "
									+ " IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,  "
									+ " IFNULL(DATE_FORMAT(u.created_date, '%d-%b-%Y'), 'NA') regdate "
									+ " FROM users u left outer join authorities a on a.username=u.username "
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									+ " WHERE u.Company_id = :companyid AND a.authority='ROLE_USER' and DATE(u.created_date) BETWEEN DATE(:sdate) and  DATE(:edate)"
									+ " ORDER BY DATE(u.created_date) DESC LIMIT 100")
					.setParameter("companyid", companyid)
					.setParameter("sdate", startDate)
					.setParameter("edate", endDate);
			List<Object> objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("Register", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.steps,0) steps "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND DATE(as1.data_date) between DATE(:sdate) and  DATE(:edate) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE DATE(al.log_date) between DATE(:sdate) and  DATE(:edate) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY steps DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("sdate", startDate)
					.setParameter("edate", endDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("steps", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,IFNULL(tousr.actmin,0) actmin "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND DATE(as1.data_date) between DATE(:sdate) and  DATE(:edate) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE DATE(al.log_date)between DATE(:sdate) and  DATE(:edate) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id  left outer join authorities a on a.username=u.username AND a.authority='ROLE_USER' "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY actmin DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("sdate", startDate)
					.setParameter("edate", endDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("actmin", objCals);

			query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"SELECT u.user_id,IF(ups.leaderboard='ANY_ONE', CONCAT(u.first_name, ' ', u.last_name),'Private') uname,IF(ups.leaderboard='ANY_ONE', u.user_pic,'default.png') photo,ROUND((IFNULL(tousr.distance,0)*0.621371),2) distance "
									+ " FROM users u LEFT OUTER JOIN "
									+ "   (SELECT SUM(rcrd.steps) steps,SUM(rcrd.distance) distance,SUM(rcrd.actmin) actmin,SUM(rcrd.calories) calories,rcrd.user_id "
									+ " FROM (SELECT IFNULL(SUM(as1.total_step), 0) steps,SUM(as1.distance) distance ,SUM(as1.exercise_time_min) actmin,SUM(as1.calories) calories,as1.user_id "
									+ " FROM activity_summary as1 WHERE DATE(as1.data_date)  AND as1.app_devices_id = (SELECT uaad.app_device_id "
									+ " FROM user_app_active_details uaad "
									+ "   LEFT OUTER JOIN apps_devices ad1 ON uaad.app_device_id = ad1.apps_devices_id "
									+ " WHERE uaad.user_id = as1.user_id AND ad1.apps_type = 'Activity' AND as1.data_date BETWEEN uaad.start_date AND IFNULL(uaad.end_date, DATE(CONVERT_TZ(NOW(),'UTC',:tz))) LIMIT 1) AND DATE(as1.data_date) between DATE(:sdate) and  DATE(:edate) GROUP BY as1.user_id "
									+ " UNION ALL "
									+ " SELECT IFNULL(SUM(al.steps), 0) steps,SUM(al.distance) distance,SUM(al.duration_mm) actmin,SUM(al.calories_burned) calories,al.user_id "
									+ " FROM activity_log al WHERE DATE(al.log_date) between DATE(:sdate) and  DATE(:edate) GROUP BY al.user_id) rcrd "
									+ " GROUP BY rcrd.user_id ORDER BY rcrd.steps) tousr "
									+ "   ON tousr.user_id = u.user_id "
									+ " LEFT OUTER JOIN user_personal_info upi "
									+ "   ON u.user_id = upi.user_id left outer join authorities a on a.username=u.username  "
									
									+ " LEFT OUTER JOIN user_privacy_settings ups ON u.user_id = ups.user_id "
									
									+ " WHERE u.Company_id= :companyId AND a.authority='ROLE_USER'  AND u.user_id IS NOT NULL GROUP BY u.user_id"
									+ " ORDER BY distance DESC LIMIT 100")
					.setParameter("companyId", companyid)
					.setParameter("sdate", startDate)
					.setParameter("edate", endDate)
					.setParameter("tz", timmezone);
			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("distance", objCals);

			objCals = query.setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			map.put("distance", objCals);

		}
		return map;
	}*/

}
