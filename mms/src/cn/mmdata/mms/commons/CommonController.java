package cn.mmdata.mms.commons;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.mms.system.module.Module;
import cn.mmdata.mms.system.user.UserInfo;

public class CommonController extends Controller {
	

	@Clear
	public void index() {
		render(CommonDefined.Template_Default + "/commons/login.html");
	}

	public void showheader() {
		Global global = Global.getInstance(this);
		UserInfo userInfo = global.getSessionUser();
		setAttr("last_login_time", userInfo.get("last_login_time"));
		setAttr("last_login_ip", userInfo.get("last_login_ip"));
		render(CommonDefined.Template_Default + "/commons/header.html");
	}

	public void showleft() {
		Global global = Global.getInstance(this);
		UserInfo user = global.getSessionUser();
		List<Record> modules = Module.dao.getModuleByUid(user.getInt("customer_id"));
		List<Record> tmpList = new ArrayList<Record>();
		for (Record record : modules) {
			Integer int1 = record.getInt("parent_module_id");
			if (int1 == 0) {
				tmpList.add(record);
				for (Record record2 : modules) {
					if ((int) record2.getInt("parent_module_id") == (int) record.getInt("module_id")) {
						tmpList.add(record2);
					}
				}
			}
		}
		setAttr("modules", tmpList);
		setAttr("user_info", user);
		render(CommonDefined.Template_Default + "/commons/left.html");
	}

	public void showcontent() {
		render(CommonDefined.Template_Default + "/commons/content.html");
	}

	public void showfooter() {
		render(CommonDefined.Template_Default + "/commons/footer.html");
	}

	public void showhelp() {
//		render(commonpath + "/help.html");
		render("/WEB-INF/jsp/system/user/customer.html");
		render("/WEB-INF/jsp/system/user/customer.jsp");
	}

	@Clear
	public void show403() {
		render(CommonDefined.Template_Default + "/commons/403.html");
	}

	@Clear
	public void show404() {
		render(CommonDefined.Template_Default + "/commons/404.html");
	}

	public void show500() {
		render(CommonDefined.Template_Default + "/commons/500.html");
	}

	@Clear
	public void show503() {
		render(CommonDefined.Template_Default + "/commons/503.html");
	}

	@Clear
	public void login() {
		Global.getInstance(this).removeSession();
		render(CommonDefined.Template_Default + "/commons/login.html");
	}

	@Clear
	public void loginout() {
		Global.getInstance(this).removeSession();
		render(CommonDefined.Template_Default + "/commons/login.html");
	}

	public void register() {
		render("/WEB-INF/jsp/register.html");
	}

	public void getProvince() {
		String sql = "select * from dmp.cn_province ";
		List<Record> list = Db.find(sql);
		setAttr("cnProvinces", list);
		renderJson();
	}

	public void getCityByPid() {
		String sql = "select * from dmp.cn_city where father=" + getPara("pid");
		List<Record> list = Db.find(sql);
		setAttr("cnCitys", list);
		renderJson(list);
	}

	public void getLevel1ByBid() {
		String sql = "select * from dmp.level1 where bid = ?";
		List<Record> list = Db.find(sql, getPara("bid"));
		setAttr("level1s", list);
		renderJson();
	}

	public void getLevel2ByLevel1Id() {
		String sql = "select * from dmp.level2 where level1Id = ?";
		List<Record> list = Db.find(sql, getPara("level1Id"));
		setAttr("level2s", list);
		renderJson();
	}

	public void getLevel3ByLevel2Id() {
		String sql = "select * from dmp.level3 where level2Id = ?";
		List<Record> list = Db.find(sql, getPara("level2Id"));
		setAttr("level3s", list);
		renderJson();
	}

	/**
	 * 获取dmp中的三级分类
	 */
	public void getLevel3ByBid() {
		String sql = "select level3Id,level3Name from dmp.v_type  where bid = ? order by level3Name";
		List<Record> list = Db.find(sql, getPara("bid"));
		setAttr("level3s", list);
		renderJson();
	}

	public void show_customer() {
		render("/WEB-INF/jsp/system/juser/customer.html");
	}
}
