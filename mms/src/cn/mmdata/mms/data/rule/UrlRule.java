package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UrlRule extends Model<UrlRule> {
	private static final long serialVersionUID = 1L;
	public static UrlRule dao = new UrlRule();

	public Page<UrlRule> paginate(int pageNumber, int pageSize) {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" from (select * from urlrule where  1 = 1  ");
		if (this.get("status") != null) {
			sql.append(" and status = ? ");
			paras.add(this.get("status"));
		}
		sql.append(" ) a");
		sql.append(" order by ruleId desc ");
		return this.use("dmp").paginate(pageNumber, pageSize, "select  * ", sql.toString(), paras.toArray());
	}

	// public List<UrlRule> findAll() {
	// List<Object> paras = new ArrayList<>();
	// StringBuilder sql = new StringBuilder(
	// "select ur.* from urlrule ur left join urlhost uh on ur.hostId =
	// uh.hostId where 1=1 ");
	// if (this.get("host") != null) {
	// sql.append(" and uh.host = ? ");
	// paras.add(this.get("host"));
	// }
	// return this.use("dmp").find(sql.toString(), paras.toArray());
	// }

	// public int[] batchUpdate(List<UrlRule> list) {
	// return Db.use("dmp").batch("update urlrule set `desc` = ? where ruleId =
	// ?", "desc,ruleId", list, 1000);
	// }

	public Page<Record> findLevel3ByL1L2L3Name(int pageNumber, int pageSize, String keywords) {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder("  from v_type where  1=1 ");
		if (!StrKit.isBlank(keywords)) {
			sql.append(" and level1Name like ? or level2Name like ? or level3Name like ? ");
			paras.add("%" + keywords + "%");
			paras.add("%" + keywords + "%");
			paras.add("%" + keywords + "%");
		}
		return Db.use("dmp").paginate(pageNumber, pageSize, "select * ", sql.toString(), paras.toArray());
	}

	public UrlRule findByName(String ruleName) {
		return this.findFirst("select * from urlrule where rule = ?", ruleName);
	}

	public List<Record> selectRulesByLevel3(Set<String> isAllSet) {
		if(isAllSet.size() > 0){
		 	String sql = "select distinct(a.ruleId) ruleId from dmp.level4 a where a.`status` = 1 and a.level4Type = 2 and a.level3Id in(";
		 	for(String level3 : isAllSet){
		 		sql += level3 + ",";
		 	}
		 	sql = sql.substring(0,sql.lastIndexOf(","));
		 	sql += ")";
		 	return Db.find(sql);
		}
		return null;
	}

}
