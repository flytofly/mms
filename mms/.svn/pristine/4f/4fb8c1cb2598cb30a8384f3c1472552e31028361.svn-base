package cn.mmdata.mms.system.role;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class Role_Module extends Model<Role_Module> {
	private static final long serialVersionUID = 1L;
	public static Role_Module dao = new Role_Module();

	public int deleteByRid(String rid) {
		String sql = "DELETE FROM role_module WHERE role_id = ?";
		return Db.update(sql, rid);
	}

}
