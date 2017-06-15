package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UrlRuleTemp extends Model<UrlRuleTemp> {
	private static final long serialVersionUID = 1L;
	public static UrlRuleTemp dao = new UrlRuleTemp();

	public void whereSql(StringBuilder sql, List<Object> paras) {
		if (this.get("status") != null) {
			sql.append(" and status = ? ");
			paras.add(this.get("status"));
		}
		if (this.get("host") != null) {
			sql.append(" and host like ? ");
			paras.add("%" + this.get("host") + "%");
		}
		if (this.get("urlRule") != null) {
			sql.append(" and urlRule like ? ");
			paras.add("%" + this.get("urlRule") + "%");
		}
		if (this.get("level3Name") != null) {
			sql.append(" and level3Name like ? ");
			paras.add("%" + this.get("level3Name") + "%");
		}
		if (this.get("level3Id") != null) {
			sql.append(" and level3Id = ? ");
			paras.add(this.get("level3Id"));
		}
		if (this.get("regId") != null) {
			sql.append(" and regId like ? ");
			paras.add("%" + this.get("regId") + "%");
		}
		if (this.get("regIdNoLike") != null) {
			sql.append(" and regId = ? ");
			paras.add(this.get("regIdNoLike"));
		}

		if (this.get("ifCanLoad") != null) {
			if (this.getInt("ifCanLoad").equals(1)) {
				sql.append(" and status = 11 ");
			} else {
				sql.append(" and status > 11 ");
			}

		}
	}

	public void BatchSave(List<UrlRuleTemp> list) {
		Db.use("dmp").batchSave(list, 1000);
	}

	public void BatchUpdateRuleId(String[] urlRule) {
		if (urlRule == null || urlRule.length == 0) {
			return;
		}
		String urlRule1 = "";
		for (String rule : urlRule) {
			if (StrKit.notBlank(rule)) {
				urlRule1 += "'" + rule + "'" + ",";
			}
		}
		urlRule1 = urlRule1.length() > 1 ? urlRule1.substring(0, urlRule1.length() - 1) : urlRule1;
		String sql = "update urlrule_temp a inner join (select * from v_level4 where rule in (" + urlRule1
				+ ") ) b on a.urlRule = b.rule set a.ruleId = b.ruleId";
		Db.use("dmp").update(sql);
	}

	public Page<UrlRuleTemp> paginate(int pageNumber, int pageSize) {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder("   from urlrule_temp where 1=1 ");
		whereSql(sql, paras);
		sql.append(" order by id   ");
		return this.use("dmp").paginate(pageNumber, pageSize, "select * ", sql.toString(), paras.toArray());
	}

	public Page<UrlRuleTemp> paginate_import(int pageNumber, int pageSize) {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" from urlrule_temp where status >=11 ");
		whereSql(sql, paras);
		sql.append(" order by id   ");
		return this.use("dmp").paginate(pageNumber, pageSize, "select * ", sql.toString(), paras.toArray());
	}

	public UrlRuleTemp findRuleTempById(Integer id) {
		String sql = "select * from (select * from urlrule_temp where id = ?) ut left join v_type vt on ut.level3Id = vt.level3Id";
		return this.use("dmp").findFirst(sql, id);
	}

	public UrlRuleTemp findRuleTempByUrlRule(String urlRule) {
		String sql = "select * from urlrule_temp where urlRule = ?";
		return this.use("dmp").findFirst(sql, urlRule);
	}

	public List<UrlRuleTemp> findAll() {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" select * from urlrule_temp where 1=1 ");
		whereSql(sql, paras);
		return this.use("dmp").find(sql.toString(), paras.toArray());
	}

	public int deleteUnImport(String regId) {
		StringBuilder sql = new StringBuilder(" delete from urlrule_temp where regId = ? ");
		return Db.use("dmp").update(sql.toString(), regId);
	}

	public void batchUpdateLevel3(String regId) {
		Db.use("dmp").update(
				"update urlrule_temp t inner join level3 l3 set t.level3Id = l3.level3Id where t.level3Name = l3.level3Name and t.regId = ? ",
				regId);
	}

	public List<Record> findCanSaveListByRegId(String regId) {
		return Db.use("dmp").find("select * from urlrule_temp  where status =11 and regId = ?", regId);
	}
}
