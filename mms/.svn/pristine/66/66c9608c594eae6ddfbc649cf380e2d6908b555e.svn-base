package cn.mmdata.mms.system.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class User_Role extends Model<User_Role> {
	private static final long serialVersionUID = 1L;
	public static User_Role dao = new User_Role();

	public int deleteByUid(String uid) {
		String sql = "DELETE FROM customer_role WHERE customer_id = ?";
		return Db.update(sql, uid);
	}

}
