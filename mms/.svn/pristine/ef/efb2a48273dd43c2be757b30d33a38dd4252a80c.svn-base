package cn.mmdata.mms.system.user;

import java.util.List;

import cn.mmdata.commons.util.StringUtil;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserInfo extends Model<UserInfo> {
	private static final long serialVersionUID = 1L;
	public static UserInfo dao = new UserInfo();

	public UserInfo login(String name, String password) {
		String sql = "select *  from cy_business.cy_customer  where  customer_name=? and password = ?";
		return this.findFirst(sql, name, password);
	}

	public Page<UserInfo> paginate(int pageNumber, int pageSize) {
		String sql = " from cy_business.cy_customer where 1=1 ";
		if (!StringUtil.isNull(this.getStr("customer_name"))) {
			sql += " and customer_name like '%" + this.getStr("customer_name").trim() + "%'";
		}
		if (this.get("customer_type") != null) {
			sql += " and customer_type=" + this.get("customer_type");
		}
		if (this.get("status") != null) {
			sql += " and status=" + this.get("status");
		}
		if (!StringUtil.isNull(this.getStr("business_name"))) {
			sql += " and business_name like '%" + this.getStr("business_name").trim() + "%'";
		}
		return this.paginate(pageNumber, pageSize, "select *", sql);
	}

	public UserInfo checkname(String para) {
		String sql = "select count(*)  count from  cy_business.cy_customer where customer_name=?";
		return this.findFirst(sql, para);
	}

	public List<Record> findRolesByUid(String customer_id) {
		return Db.find("select * from customer_role,role where customer_role.role_id = role.role_id and status=1 and customer_id = ? ", customer_id);
	}
}
