package cn.mmdata.mms.data.crowd;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class CrowdRule extends Model<CrowdRule> {
	private static final long serialVersionUID = 1L;
	public static CrowdRule dao = new CrowdRule();

	public List<Record> getCrowdRules(int cid) {
		String sql="select * from crowd_rule where cid=?";
		return Db.find(sql, cid);
	}

	public List<Record> findRulesByCid(String cid) {
		String sql="select concat(rule,'\r\n') rule from crowd_rule    where cid=? and status=1";
		return Db.find(sql, cid);
	}

	public void updateStatus() {
		String sql="update crowd_rule set status=9";
		Db.update(sql);
		
	}
}
