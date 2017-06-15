package cn.mmdata.mms.data.scene;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;

public class SceneController extends Controller {

	public void list() {
		Scene record = getModel(Scene.class, "record");
		record.put("customer_name", getPara("customer_name"));
		
		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		
		String submit = getPara("Submit");
		if (StrKit.notBlank(submit)) {
			// 查询时将pageNumber置1
			setAttr("records", record.paginate(1, 10));
		} else {
			Page<Record> page = record.paginate(getParaToInt("pageNumber", 1), 10);
			setAttr("records", page);
		}

		setAttr("record", record);
		render("/WEB-INF/jsp/data/scene/list.html");
	}

	public void edit() {
		String cid = getPara();
		if (StrKit.notBlank(cid)) {
			Scene module = Scene.dao.findById(cid);
			setAttr("record", module);
		}else{
			setAttr("record", new Scene());
		}
		render("/WEB-INF/jsp/data/scene/edit.html");
	}
	
	public void rlist() {
		Scene record = getModel(Scene.class, "record");
		
		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		
		String submit = getPara("Submit");
		record.set("status", 1);
		if(StrKit.notBlank(submit)){
			// 查询时将pageNumber置1
			setAttr("records", record.paginate(1, 10));
		}else{
			setAttr("records", record.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("record", record);
		render("/WEB-INF/jsp/data/scene/rlist.html");
	}

	/**
	 * 处理用户需求地区
	 * 
	 * @param project
	 * @return
	 */

	public void update() {
		int userid = Global.getInstance(this).getSessionUserId();
		String sid = getPara("record.sid");
		Scene r = getModel(Scene.class, "record");
		if (!StringUtil.isNull(sid)) {
			r.set("reg_id", userid).set("lastupdatedate", new Date()).update();
		} else {
			r.set("reg_id", userid).save();
		}
		redirect("/data/scene/list");
	}

	public void delete() {
		new Scene().deleteById(getParaToInt());
		redirect("/data/scene/list");
	}
	//
	// public void detail() {
	// Crowd module = Crowd.dao.findById(getParaToInt(0));
	// setAttr("record", module);
	// render("/WEB-INF/jsp/system/module/detail.html");
	// }
	//

}
