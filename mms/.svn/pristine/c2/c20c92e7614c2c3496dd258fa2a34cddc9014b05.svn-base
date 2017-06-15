package cn.mmdata.mms.system.user;

import java.util.Date;
import java.util.List;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;
import cn.mmdata.mms.system.role.Role;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

public class UserInfoController extends Controller {
	public void list() {
		UserInfo userinfo = getModel(UserInfo.class, "userinfo");
		String submit = getPara("Submit");
		if (StrKit.notBlank(submit)) {
			// 查询时将pageNumber置1
			setAttr("records", userinfo.paginate(1, 10));
		} else {
			setAttr("records", userinfo.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("userinfo", userinfo);
		render("/WEB-INF/jsp/system/juser/list.html");
	}

	public void edit() {
		String uid = getPara();
		if (!StringUtil.isNull(uid)) {
			UserInfo userInfo = UserInfo.dao.findById(uid);
			setAttr("record", userInfo);
		}else{
			setAttr("record", new UserInfo());
		}
		List<Record> roles = Role.dao.findAll();
		List<Record> rolesChecked = Role.dao.queryByUid(uid);
		for (Record role : roles) {
			if (rolesChecked.contains(role)) {
				role.set("isChecked", true);
			}
		}

		setAttr("roles", roles);

		render("/WEB-INF/jsp/system/juser/edit.html");
	}

	public void detail() {
		String uid = getPara(0);
		UserInfo user = UserInfo.dao.findById(uid);
		setAttr("record", user);
		List<Record> roles = user.findRolesByUid(uid);
		setAttr("roles", roles);
		render("/WEB-INF/jsp/system/juser/detail.html");
	}

	public void update() {
		UserInfo u = (UserInfo) getSessionAttr(Global.SESSION_USER);
		String uid = getPara("record.customer_id");
		String[] roleArr = getParaValues("role");
		UserInfo ui = getModel(UserInfo.class, "record");

		if (!StringUtil.isNull(uid)) {
			ui.update();
			new User_Role().deleteByUid(uid);

		} else {
			ui.set("password", ui.get("password"));
			ui.set("reg_date", new Date());
			ui.set("lastupdatedate", null);
			ui.set("reg_id", u.get("customerId"));
			ui.save();
		}

		if (roleArr != null) {
			for (int i = 0; i < roleArr.length; i++) {
				User_Role ur = new User_Role();
				ur.set("customer_id", ui.get("customer_id"));
				ur.set("role_id", roleArr[i]);
				ur.set("reg_id", u.get("customer_id"));
				ur.save();
			}
		}

		redirect("/system/user/list");
	}

	public void delete() {
		new UserInfo().deleteById(getParaToInt());
		redirect("/system/user/list");
	}

	public void editrole() {
		String uid = getPara();
		if (!StringUtil.isNull(uid)) {
			UserInfo userInfo = UserInfo.dao.findById(uid);
			setAttr("record", userInfo);

		}
		List<Record> roles = Role.dao.findAll();
		List<Record> rolesChecked = Role.dao.queryByUid(uid);
		for (Record role : roles) {
			if (rolesChecked.contains(role)) {
				role.set("isChecked", true);
			}
		}

		setAttr("roles", roles);
		render("/WEB-INF/jsp/system/juser/editrole.html");
	}

	public void editroleupdate() {
		UserInfo u = (UserInfo) getSessionAttr(Global.SESSION_USER);
		String customer_id = getPara("record.customer_id");
		String[] roleArr = getParaValues("role");
		if (!StringUtil.isNull(customer_id)) {
			new User_Role().deleteByUid(customer_id);
		}
		if (roleArr != null) {
			for (int i = 0; i < roleArr.length; i++) {
				User_Role ur = new User_Role();
				ur.set("customer_id", customer_id);
				ur.set("role_id", roleArr[i]);
				ur.set("reg_id", u.get("customerId"));
				ur.save();
			}
		}
		redirect("/system/user/list");
	}

	public void valUsername() {
		renderJson(UserInfo.dao.checkname(getPara("customer_name")).getLong("count"));
	}

	public void modpass() {
		render("/WEB-INF/jsp/system/juser/modpass.html");
	}

	public void modpasspost() {
		String passwordOld = getPara("passwordOld");
		String password = getPara("record.password");
		String repeat_password = getPara("repeat_password");
		UserInfo record = getModel(UserInfo.class, "record");
		UserInfo old = UserInfo.dao.findById(record.get("customer_id"));
		if (!passwordOld.equals(old.getStr("password"))) {
			setAttr("result", "原始密码错误！");
		} else if (!password.equals(repeat_password)) {
			setAttr("result", "两次密码不一致！");
		} else {
			if (old.set("password", password).update()) {
				setAttr("result", "密码修改成功！");
			}
		}
		render("/WEB-INF/jsp/system/juser/modpass.html");
	}
}
