package cn.mmdata.mms.system.role;

import java.util.List;

import cn.mmdata.commons.util.StringUtil;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Role extends Model<Role> {
	private static final long serialVersionUID = 1L;
	public static Role dao = new Role();

	public Page<Record> paginate(int pageNumber, int pageSize) {
		String sql = " from role where 1=1";
		if (!StringUtil.isNull(this.getStr("role_name"))) {
			sql += " and role_name like '%" + this.getStr("role_name").trim()
					+ "%'";
		}
		if (this.get("status") != null) {
			sql += " and status=" + this.get("status");
		}
		return Db.paginate(pageNumber, pageSize, "select *", sql);
	}

	public List<Record> queryByUid(String uid) {
		String sql = "select customer_role.role_id,role_name,role_desc,status from customer_role,role where customer_role.role_id = customer_role.role_id and  customer_id = ?";
		return Db.find(sql, uid);
	}

	public List<Record> findAll() {
		String sql = "select * from role";
		return Db.find(sql);
	}

	public int deleteByUid(String customer_id) {
		String sql = "DELETE FROM customer_role WHERE customer_id = ?";
		return Db.update(sql, customer_id);
	}

}
