package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class RulePro extends Model<RulePro> {
	private static final long serialVersionUID = 1L;
	public static RulePro dao = new RulePro();

	public int deleteByRuleIdAndProId(String[] ruleProIds, String[] ruleIds) {
		if (ruleIds == null || ruleProIds == null || ruleProIds.length == 0 || ruleIds.length == 0) {
			return 0;
		}
		String ruleProId1 = "";
		for (String ruleProId : ruleProIds) {
			ruleProId1 += ruleProId + ",";
		}
		ruleProId1 = ruleProId1.length() > 1 ? ruleProId1.substring(0, ruleProId1.length() - 1) : ruleProId1;
		String ruleIds1 = "";
		for (String ruleId : ruleIds) {
			ruleIds1 += ruleId + ",";
		}
		ruleIds1 = ruleIds1.length() > 1 ? ruleIds1.substring(0, ruleIds1.length() - 1) : ruleIds1;
		return Db.update("delete from deploy_urlrule_pro where ruleid in (" + ruleIds1 + ")  and provincecode in (" + ruleProId1 + ")");
	}

	/*public String findByUrlRuleId(List<BusinessProjectSskw> sskwList) {
		if (sskwList == null || sskwList.size() == 0) {
			return "";
		}
		String ruleIds1 = "";
		for (BusinessProjectSskw sskw : sskwList) {
			ruleIds1 += sskw.get("kid") + ",";
		}
		ruleIds1 = ruleIds1.length() > 1 ? ruleIds1.substring(0, ruleIds1.length() - 1) : ruleIds1;
		return RulePro.dao.find(
				"select distinct provincecode from deploy_urlrule_pro where ruleid in (" + ruleIds1 + ") ")
				.toString();
	}*/

	public void batchInsertPros(Map<Integer, Set<String>> neededInsertPro) {
		List<String> sqlList = new ArrayList<String>();
		Map<String,String> proInfo = new HashMap<String,String>();
		proInfo.put("900000", "A公司");
		proInfo.put("910000", "D公司");
		proInfo.put("920000", "E公司");
		proInfo.put("930000", "上海电信");
		proInfo.put("940000", "G公司");
		for(Entry<Integer, Set<String>> map : neededInsertPro.entrySet()){
			int ruleid = map.getKey();
			for(String procode:map.getValue()){
				String sql = "insert into deploy_urlrule_pro (ruleid,provincecode,province) values"
						+ " ('"+ ruleid +"','"+ procode +"','"+ proInfo.get(procode) +"')";
				sqlList.add(sql);
			}
		}
		Db.batch(sqlList, sqlList.size());
	}
}
