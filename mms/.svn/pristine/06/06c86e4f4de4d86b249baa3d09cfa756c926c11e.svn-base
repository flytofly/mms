package cn.mmdata.mms.commons;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Clear;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.MD5Util;
import cn.mmdata.commons.util.StringUtil;
import cn.mmdata.mms.config.interceptor.AuthInterceptor;
import cn.mmdata.mms.data.crowd.Crowd;
import cn.mmdata.mms.delivery.material.Material;
import cn.mmdata.mms.delivery.task.Task;
import cn.mmdata.mms.member.ent.Business;
import cn.mmdata.mms.statis.statopti.StatOpti;
import cn.mmdata.mms.system.user.LoginLog;
import cn.mmdata.mms.system.user.UserInfo;

public class HomeController extends Controller {
	@Clear(AuthInterceptor.class)
	public void index() {
		if (Global.getInstance(this).getSessionUser() != null) {
			this.mainContent();
			render(CommonDefined.Template_Default + "/commons/main.html");
			return;
		}
		String UserAdminname = getPara("fk_UserAdminname");
		String pwd = getPara("fk_password");
		String code = getPara("code");
		if (code != null && !code.equals("")) {
			boolean validate = CaptchaRender.validate(this, code);
			if (validate) {
				if (StrKit.notBlank(UserAdminname) && StrKit.notBlank(pwd)) {
					dologin();
				} else {
					render(CommonDefined.Template_Default + "/commons/login.html");
				}
			} else {
				setAttr("result", "验证码错误!");
				render(CommonDefined.Template_Default + "/commons/login.html");
			}
		} else {
			render(CommonDefined.Template_Default + "/commons/login.html");
		}
	}

	@Clear(AuthInterceptor.class)
	public void dologin() {
		UserInfo user = new UserInfo();
		String UserAdminname = getPara("fk_UserAdminname");
		String pwd = getPara("fk_password");
		if (StringUtil.isNull(UserAdminname) || StringUtil.isNull(pwd)) {
			render(CommonDefined.Template_Default + "/commons/login.html");
			return;
		}
		UserInfo usermodule = user.login(UserAdminname, MD5Util.encode(pwd));
		if (usermodule != null) {
			Global global = Global.getInstance(this);
			global.putSessionUser(usermodule);

			int bid = usermodule.getInt("bid");
			if (bid > 0) {
				Business busi = Business.dao.findById(bid);
				if (busi != null) {
					global.putSessionBussiness(busi);
				}
			}
			// 存储登录日志
			String ip = getIpAddr(this.getRequest());

			LoginLog loginLog = new LoginLog();
			loginLog.set("username", UserAdminname);
			loginLog.set("ip", ip);
			loginLog.set("lockstatus", 0); // 默认未锁定
			loginLog.set("status", 1); // 默认登录失败
			loginLog.save();

			LoginLog lastLog = LoginLog.dao.selectLast(loginLog); // 查找登录日志中的最后一条记录
			global.putSessionLog(lastLog);

			// 加载菜单项
			/*
			 * List<Record> modules =
			 * Module.dao.getModuleByUid(usermodule.getInt("customer_id"));
			 * List<Record> tmpList = new ArrayList<Record>(); for (Record
			 * record : modules) { Integer int1 =
			 * record.getInt("parent_module_id"); if (int1 == 0) {
			 * tmpList.add(record); for (Record record2 : modules) { if ((int)
			 * record2.getInt("parent_module_id") == (int)
			 * record.getInt("module_id")) { tmpList.add(record2); } } } }
			 * global.getSession().setAttribute("modules", tmpList);
			 */
			this.mainContent();
			render(CommonDefined.Template_Default + "/commons/main.html");

			// redirect("/data/crowd/list");
		} else {
			setAttr("result", "帐号或密码错误!");
			render(CommonDefined.Template_Default + "/commons/login.html");
		}
	}

	private void mainContent() {
		this.getSeatTaskStatistics(); // 坐席任务统计
		this.getMessageTaskStatistics(); // 短信任务统计
		this.getSeatStatistics(); // 坐席统计
		this.getMessageStatistics();// 短息统计
		this.detailStatistics(); // 任务统计
		this.CrowdStatistics(); // 人群统计
		this.MaterialStatistics(); // 素材统计
	}

	@Clear
	public void createImg() throws ServletException, IOException {
		CaptchaRender capt = new CaptchaRender();
		render(capt);
	}

	// 任务统计
	public void detailStatistics() {
		// 获取任务统计数
		Task task = new Task();
		setCustype(task);
		List<Record> list = task.tongji();
		Record taskRecord = null;
		if (list.size() != 0) {
			taskRecord = list.get(0);
		}
		setAttr("taskRecord", taskRecord);
	}

	public void CrowdStatistics() {
		Crowd crowd = new Crowd();
		setCustype(crowd);

		List<Record> list = crowd.CrowdStatistics();
		Record crowdRecord = null;
		if (list.size() != 0) {
			crowdRecord = list.get(0);
		}
		setAttr("crowdRecord", crowdRecord);

	}

	private void setCustype(Model<?> record) {
		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
	}

	// 素材统计
	public void MaterialStatistics() {
		Material material = new Material();
		setCustype(material);
		List<Record> list = material.MaterialStatistics();
		Record marerialRecord = list.get(0);
		setAttr("marerialRecord", marerialRecord);
	}

	// 规则统计TOP10
	public void StatOptiStatistics() {
		StatOpti statOpti = new StatOpti();
		List<Record> list = statOpti.StatOptiStatistics();// zhl fb_num yxnum
		renderJson(list);
	}

	// 坐席任务统计
	public void getSeatTaskStatistics() {
		StatOpti statOpti = new StatOpti();
		setCustype(statOpti);
		List<Record> list = statOpti.getSeatTask();
		Record seatTSRecord = null;
		if (list.size() != 0) {
			seatTSRecord = list.get(0);
		}
		this.setAttr("seatTSRecord", seatTSRecord);
	}

	// 短息任务统计
	public void getMessageTaskStatistics() {
		StatOpti statOpti = new StatOpti();
		setCustype(statOpti);
		List<Record> list = statOpti.getMessageTask();
		Record messTSRecord = null;
		if (list.size() != 0) {
			messTSRecord = list.get(0);
		}
		this.setAttr("messTSRecord", messTSRecord);
	}

	// 坐席统计
	public void getSeatStatistics() {
		int pageSize = 5;
		int pageNumber = this.getPara("pageNumber") == null ? 1 : this.getParaToInt("pageNumber");

		StatOpti statOpti = new StatOpti();
		setCustype(statOpti);
		Page<Record> list = statOpti.getSeatStatistics(pageNumber, pageSize);

		this.setAttr("seatList", list);
		renderJson(list);
	}

	// 短息统计
	public void getMessageStatistics() {
		int pageSize = 5;
		int pageNumber = this.getPara("pageNumber") == null ? 1 : this.getParaToInt("pageNumber");

		StatOpti statOpti = new StatOpti();
		setCustype(statOpti);
		Page<Record> list = statOpti.getMessageStatistics(pageNumber, pageSize);
		this.setAttr("messageList", list);
		renderJson(list);

	}

	private String getIpAddr(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

}
