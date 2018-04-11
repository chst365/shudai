//package com.shudailaoshi.dao.util;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//
//import com.shudailaoshi.dao.hrm.EmployeeDao;
//import com.shudailaoshi.dao.hrm.TeamDao;
//import com.shudailaoshi.entity.hrm.Employee;
//import com.shudailaoshi.entity.hrm.Team;
//import com.shudailaoshi.entity.sys.User;
//import com.shudailaoshi.pojo.constants.Constant;
//import com.shudailaoshi.utils.ApplicationUtil;
//import com.shudailaoshi.utils.ConvertUtil;
//import com.shudailaoshi.utils.SessionUtil;
//
//import tk.mybatis.mapper.entity.Example;
//
//public class DataPermitUtil {
//
//	private static EmployeeDao EMPLOYEEDAO;
//	private static TeamDao TEAMDAO;
//
//	private DataPermitUtil() {
//	}
//
//	static {
//		TEAMDAO = (TeamDao) ApplicationUtil.getBean(TeamDao.class);
//		EMPLOYEEDAO = (EmployeeDao) ApplicationUtil.getBean(EmployeeDao.class);
//	}
//
//	public static String getCitySql(String columnName) {
//		Subject subject = SecurityUtils.getSubject();
//		boolean isAdmin = subject.hasRole(Constant.ADMIN);
//		if (isAdmin) {
//			return "";
//		}
//		boolean isFranchisee = subject.hasRole(Constant.FRANCHISEE);
//		if (!isFranchisee) {
//			return "";
//		}
//		long userId = ((User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER)).getId();
//		Example example = new Example(Employee.class);
//		example.createCriteria().andEqualTo("userId", userId);
//		long cityId = EMPLOYEEDAO.getByExample(example).getCityId();
//		StringBuilder sql = new StringBuilder(" and ");
//		sql.append(columnName);
//		sql.append(" = ");
//		sql.append(cityId);
//		return sql.toString();
//	}
//
//	public static String getTeamSql(String columnName, String teamIds) {
//		Subject subject = SecurityUtils.getSubject();
//		boolean isAdmin = subject.hasRole(Constant.ADMIN);
//		boolean isBlank = StringUtils.isBlank(teamIds);
//		if (isAdmin && isBlank) {
//			return "";
//		}
//		String allowTeamIds = getAllowTeamIds(teamIds, subject, isAdmin, isBlank);
//		StringBuilder sql = new StringBuilder(" and ");
//		sql.append(columnName);
//		sql.append(" in (");
//		sql.append(allowTeamIds);
//		sql.append(") ");
//		return sql.toString();
//	}
//
//	private static String getAllowTeamIds(String teamIds, Subject subject, boolean isAdmin, boolean isBlank) {
//		Set<Long> teamAllowIds = new HashSet<Long>();
//		Team root = new Team();
//		if (isAdmin) {
//			root.setId(Constant.ROOT);
//		} else {
//			long userId = ((User) SessionUtil.getSessionAttribute(Constant.CURRENT_USER)).getId();
//			long teamId = TEAMDAO.getTeamIdByUserId(userId);
//			root.setId(teamId);
//			teamAllowIds.add(teamId);
//		}
//		appendNode(root, TEAMDAO.listAll(), teamAllowIds);
//		if (isBlank) {
//			return StringUtils.join(teamAllowIds, ",");
//		}
//		// 取交集
//		teamAllowIds.retainAll(ConvertUtil.convertSet(teamIds.split(",")));
//		return StringUtils.join(teamAllowIds, ",");
//	}
//
//	private static void appendNode(Team root, List<Team> teams, Set<Long> teamAllowIds) {
//		for (Team node : teams) {
//			if (node.getParentId().longValue() == root.getId()) {
//				teamAllowIds.add(node.getId());
//				appendNode(node, teams, teamAllowIds);
//			}
//		}
//	}
//
//}
