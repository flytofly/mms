package cn.mmdata.mms.system.module;

import java.util.List;

import cn.mmdata.commons.util.StringUtil;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Module extends Model<Module> {
	private static final long serialVersionUID = 1L;
	public static Module dao = new Module();

	public Page<Record> paginate(int pageNumber, int pageSize) {
		String sql = " from module where 1=1";
		if (!StringUtil.isNull(this.getStr("module_name"))) {
			sql += " and module_name like '%" + this.getStr("module_name").trim() + "%'";
		}
		if (this.get("status") != null) {
			sql += " and status=" + this.get("status");
		}
		return Db.paginate(pageNumber, pageSize, "select *", sql);
	}

	public List<Record> getAllModule() {
		String sql = " select * from module  order by module_layer_mark asc";
		return Db.find(sql);
	}

	public List<Record> getModule(int role_id) {
		String sql = " SELECT md.module_id,md.module_name FROM role_module rm,module md WHERE rm.module_id=md.module_id AND rm.role_id=? order by module_layer_mark asc";
		List<Record> list = Db.find(sql, role_id);
		return list;
	}

	public List<Record> getModuleByUid(int uid) {
		String sql = "SELECT distinct * FROM v_customer_module where customer_id=?  order by module_layer_mark";
		return Db.find(sql, uid);
	}

}
