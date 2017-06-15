package cn.mmdata.commons.util;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;

import cn.mmdata.mms.member.ent.Business;
import cn.mmdata.mms.system.user.LoginLog;
import cn.mmdata.mms.system.user.UserInfo;

public class Global {

	public static final int USER_TYPE_CHUANYU = 1; // 传域用户
	public static final int USER_TYPE_AGENT = 5; // 代理商
	public static final int USER_TYPE_BUSINESS = 4; // 客户使用者
	public static final int USER_TYPE_ADMIN = 9; // 超级管理员
	public static final int USER_TYPE_PROJECT = 6; // 客户项目使用者

	public static final String SESSION_USER = "suser"; // 保存登录的账户信息
	public static final String SESSION_MENU = "cmodules"; // 保存菜单信息
	public static final String SESSION_USER_LOG = "suser_log"; // 保存登录的账户上次登录详情

	// 以下常量不能修改
	public static final String SESSION_USER_ID = "customer_id"; // 用户id
	public static final String SESSION_BID = "bid"; // bid
	public static final String SESSION_BUSINESS_NAME = "business_name"; // business_name
	public static final String SESSION_USER_TYPE = "customer_type"; // 用户类型
	public static final String SESSION_USER_NAME = "customer_name"; // 用户名称
	public static final String SESSION_BUSINESS = "suserBusiness";
	
	public HttpSession session;

	private Global(Controller controller) {
		if (controller != null) {
			session = controller.getSession();
		}
	}

	public static Global getInstance(Controller controller) {
		return new Global(controller);
	}

	/**
	 * 将登陆用户信息放入session中
	 * 
	 * @param user
	 *            登陆用户信息
	 */
	public void putSessionUser(UserInfo user) {
		if (session != null)
			session.setAttribute(SESSION_USER, user);
	}
	public void putSessionBussiness(Business busi) {
		if (busi != null)
			session.setAttribute(SESSION_BUSINESS, busi);
	}
	public void putSessionLog(LoginLog loginlog) {
		if (loginlog != null)
			session.setAttribute(SESSION_USER_LOG, loginlog);
	}
	/**
	 * 得到登陆用户的所有信息
	 * 
	 * @return
	 */
	public UserInfo getSessionUser() {
		if (session == null) {
			throw new BusinessException("你未登录或者登录已过期，请重新登录！");
		}
		UserInfo user = (UserInfo) session.getAttribute(SESSION_USER);
		if (user != null) {
			return user;
		} else {
			return null;
			// throw new BusinessException("你未登录或者登录已过期，请重新登录！");
		}
	}

	/**
	 * 得到登陆用户名称
	 * 
	 * @return
	 */
	public String getSessionUserName() throws BusinessException {
		if (getSessionUser() == null) {
			return null;
		}
		return getSessionUser().getStr(SESSION_USER_NAME);
	}

	/**
	 * 得到登陆用户ID
	 * 
	 * @return
	 */
	public Integer getSessionUserId() throws BusinessException {
		if (getSessionUser() == null) {
			return null;
		}
		return getSessionUser().getInt(SESSION_USER_ID);
	}

	/**
	 * 得到登陆用户BID
	 * 
	 * @return
	 */
	public Integer getSessionBId() throws BusinessException {
		if (getSessionUser() == null) {
			return -1;
		}
		return getSessionUser().getInt(SESSION_BID);
	}

	/**
	 * 得到登陆用户BusinessName
	 * 
	 * @return
	 */
	public String getSessionBusinessName() throws BusinessException {
		if (getSessionUser() == null) {
			return null;
		}
		return getSessionUser().getStr(SESSION_BUSINESS_NAME);
	}

	/**
	 * 得到登陆用户类型
	 * 
	 * @return
	 */
	public Integer getSessionUserType() throws BusinessException {
		if (getSessionUser() == null) {
			return null;
		}
		return getSessionUser().getInt(SESSION_USER_TYPE);
	}

	/**
	 * 清除session的登录信息
	 */
	public void removeSession() {
		if (session != null) {
			session.removeAttribute(SESSION_USER);
		}
	}
	
	public HttpSession getSession(){
		if (session != null) {
			return session;
		}
		return null;
	}
}
