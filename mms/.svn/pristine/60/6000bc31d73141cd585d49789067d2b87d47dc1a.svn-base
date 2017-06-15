package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.DateTools;

public class Level4 extends Model<Level4> {
	private static final long serialVersionUID = 1L;
	public static Level4 dao = new Level4();

	public Level4 findByModel() {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from level4 where 1=1 ");
		if (this.get("level3Id") != null) {
			sql.append(" and level3Id = ? ");
			paras.add(this.get("level3Id"));
		}
		if (this.get("level4Type") != null) {
			sql.append(" and level4Type = ? ");
			paras.add(this.get("level4Type"));
		}
		if (this.get("ruleId") != null) {
			sql.append(" and ruleId = ? ");
			paras.add(this.get("ruleId"));
		}
		return this.findFirst(sql.toString(), paras.toArray());
	}

	public Page<Record> paginate(int pageNumber, int pageSize) {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"SELECT l4.level1Name,l4.level2Name,l4.level3Name,l4.ruleId,l4.`desc`,ifnull(t2.pv,0)"
				+ " pv,l4.`status` from dmp.v_level4 l4 left join (select t3.level4id,sum(t3.pv) pv  from"
				+ " dmp.level4devcountday t3  where t3.regDate = (select regDate from"
				+ " dmp.level4devcountday t1 order by t1.regDate desc limit 1) group by t3.level4id)"
				+ " t2  on l4.level4Id=t2.level4Id where l4.level4Type=2 and l4.bid = 1");
		if (this.get("status") != null) {
			sql.append(" and l4.status = ? ");
			paras.add(this.get("status"));
		}
		if (this.get("rule") != null) {
			sql.append(" and rule like ? ");
			paras.add("%" + this.getStr("rule").trim() + "%");
		}
		if (this.get("level3Name") != null) {
			sql.append(" and (level3Name like ? ");
			sql.append(" or `desc` like ? )");
			paras.add("%" + this.getStr("level3Name").trim() + "%");
			paras.add("%" + this.getStr("level3Name").trim() + "%");
		}
		sql.append(" limit " + (pageNumber - 1) * pageSize + "," + pageSize);
		int count = count();
		int totalPage = count % pageSize == 0 ? count : (count / pageSize) + 1;
		return new Page<Record>(Db.find(sql.toString(), paras.toArray()),
				pageNumber, pageSize, totalPage, count);
	}

	public int count() {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select count(*) cot  from dmp.level4 l4");
		
		if (this.get("rule") != null) {
			sql.append(" and rule like ? ");
			paras.add("%" + this.getStr("rule").trim() + "%");
		}
		String sql2 = " where l4.level4Type= 2";
		if (this.get("level3Name") != null) {
			sql.append(" left join dmp.urlrule b on l4.ruleId = b.ruleId");
			sql.append(sql2);
			sql.append(" and (l4.level3Name like ? ");
			sql.append(" or b.`desc` like ? )");
			paras.add("%" + this.getStr("level3Name").trim() + "%");
			paras.add("%" + this.getStr("level3Name").trim() + "%");
		}else{
			sql.append(sql2);
		}
		if (this.get("status") != null) {
			sql.append(" and l4.status = ? ");
			paras.add(this.get("status"));
		}
		return Db.findFirst(sql.toString(), paras.toArray()).getLong("cot")
				.intValue();
	}

	public int batchUpdateStatus(int status, String cname, String level4Ids) {
		return Db.use("dmp").update(
				"update level4 set status = ?,cname=?,cdate=? where 1=1 and level4Id in (" + level4Ids + ")",
				status, cname, DateTools.getDateTime(0));
	}

	public Record findVLevel4() {
		List<Object> paras = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from v_level4 where 1=1 ");
		if (this.get("level4Id") != null) {
			sql.append(" and level4Id = ? ");
			paras.add(this.get("level4Id"));
		}
		if (this.get("bid") != null) {
			sql.append(" and bid = ? ");
			paras.add(this.get("bid"));
		}
		if (this.get("level4Type") != null) {
			sql.append(" and level4Type = ? ");
			paras.add(this.get("level4Type"));
		}
		return Db.use("dmp").findFirst(sql.toString(), paras.toArray());
	}

	public List<Record> findByRules(String rules) {
		return Db.use("dmp")
				.find("select * from v_level4  l4 where l4.bid=1 and l4.level4Type=2 and l4.ruleId in (select ruleId from urlrule where rule  in ( "
						+ rules + " ) )  ");
	}
}
