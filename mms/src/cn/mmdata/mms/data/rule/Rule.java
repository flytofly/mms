package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Rule extends Model<Rule> {

	private static final long serialVersionUID = 1L;
	public static Rule dao = new Rule();

	public Page<Rule> list(int pageNumber, int pageSize) {
		String sql = " from ( select * from dmp.level4 d where d.status != 2 and d.level4Type = 2 ) a"
				+ " left join dmp.v_type b on a.level3Id = b.level3Id left join (select * from dmp.urlrule"
				+ " e where e.status != 2) c on a.ruleId = c.ruleId where 1=1";
		if (this.get("level3Name") != null && !"".equals(this.getStr("level3Name").trim())) {
			sql += " AND b.level3Name LIKE '%" + this.getStr("level3Name").trim() + "%'";
		}
		if (this.get("rule") != null && !"".equals(this.getStr("rule").trim())) {
			sql += " AND c.rule LIKE '%" + this.getStr("rule").trim() + "%'";
		}
		if (this.get("level3Desc") != null && !"".equals(this.getStr("level3Desc").trim())) {
			sql += " AND b.level3Desc LIKE '%" + this.getStr("level3Desc").trim() + "%'";
		}
		if (this.get("status") != null && !"".equals(this.get("status"))) {
			sql += " AND a.status = '" + this.get("status") + "'";
		}
		String select = "select b.level1Id,b.level1Name,b.level2Id,b.level2Name,b.level3Id,b.level3Name,b.level3Desc,a.level4Id,a.ruleId,c.rule,a.`status`";
		return this.paginate(pageNumber, pageSize, select, sql);
	}

	public Record findById() {
		String sql = "SELECT a.id,a.rid, a.urlrule,a.ruledesc,a.`status`,a.usestatus,a.level3Id,c.level3Name,a.inserttime"
				+ " FROM rule a left join dmp.level3 c on a.level3Id=c.level3Id where a.rid= ?";
		return Db.findFirst(sql,this.get("rid"));
	}

	public List<Record> findProvinceById() {
		String sql = "SELECT a.id,a.ruleid,a.provincecode,a.province FROM rule_pro a WHERE 1=1 AND a.ruleid = ?";
		return Db.find(sql,this.get("rid"));
	}

	public List<Rule> selectAllRules() {
		return this.find("SELECT * FROM rule");
	}

	public void update(Rule aUrlRule) {
		String sql = "UPDATE rule SET catid = '" + aUrlRule.getStr("catid") + "', urlrule = '"
				+ aUrlRule.getStr("urlrule") + "'," + " ruledesc =  '" + aUrlRule.getStr("ruledesc") + "', status = '"
				+ aUrlRule.getStr("status") + "', " + "usestatus = '" + aUrlRule.getStr("usestatus") + "' WHERE id = '"
				+ aUrlRule.getStr("id") + "' ";
		Db.update(sql);
	}

	/**
	 * 批量审批规则
	 * @param rids 审批规则的ids
	 * @return 总共审批的数量
	 * 
	 */
	public int batchtoCheck(String[] rids) {
		String sql1 = "UPDATE rule SET `status` = '1' WHERE rid IN (";
		for (int i = 0; i < rids.length; i++) {
			if (i == (rids.length - 1)) {
				sql1 += (rids[i] + ")");
			} else {
				sql1 += (rids[i] + ",");
			}
		}
		
		/*String sql2 = "UPDATE dmp.urlrule SET `status` = '1' WHERE rid IN (";
		for (int i = 0; i < rids.length; i++) {
			if (i == (rids.length - 1)) {
				sql2 += (rids[i] + ")");
			} else {
				sql2 += (rids[i] + ",");
			}
		}
		 Db.update(sql2);*/
		 return Db.update(sql1);
	}

	/**
	 * 批量使用
	 * 
	 * @param rids
	 * @return 影响的行数
	 */
	public int batchtoUsed(String[] rids) {
		String sql = "UPDATE rule SET usestatus = '1' WHERE rid in (";
		for (int i = 0; i < rids.length; i++) {
			if (i == (rids.length - 1)) {
				sql += (rids[i] + ")");
			} else {
				sql += (rids[i] + ",");
			}
		}
		return Db.update(sql);
	}

	/**
	 * 批量不使用
	 * 
	 * @param rids
	 * @return 影响的行数
	 */
	public int batchtoUnused(String[] rids) {
		String sql = "UPDATE rule SET usestatus = '0' WHERE rid in (";
		for (int i = 0; i < rids.length; i++) {
			if (i == (rids.length - 1)) {
				sql += (rids[i] + ")");
			} else {
				sql += (rids[i] + ",");
			}
		}
		return Db.update(sql);
	}

	/**
	 * 查找省份信息的Map
	 * 
	 * @return Map<String,String> 省份信息的Map
	 */
	public Map<String, String> selectProList() {
		Map<String, String> proMap = new HashMap<String, String>();
		String sql = "SELECT * FROM cn_province";
		List<Record> proList = Db.find(sql);
		for (Record record : proList) {
			proMap.put(record.getStr("provinceID"), record.getStr("province"));
		}
		proMap.put("900000", "A");
		proMap.put("910000", "D");
		proMap.put("920000", "E");
		proMap.put("940000", "G");
		return proMap;
	}

	/**
	 * 插入选择的省份信息
	 * @param proArray 
	 */
	public void updatePro(String[] proArray) {
		List<String> sqlList = new ArrayList<String>();
		Map<String,String> proMap = new HashMap<String,String>();
		proMap.put("900000", "A公司");
		proMap.put("910000", "D公司");
		proMap.put("920000", "E公司");
		proMap.put("930000", "上海电信");
		proMap.put("940000", "G公司");
		for (String str : proArray) {
			String provincecode = str;
			String province = proMap.get(str);
			String sql = "INSERT INTO rule_pro (ruleid,provincecode,province) VALUES ('" + this.getInt("rid") + "', '"
					+ provincecode + "', '" + province + "')";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
	}

	/**
	 * 根据ruleid 删除原有的部署省份
	 * 
	 * @param ruleid
	 *            规则id
	 */
	public void deleteProsHistory() {
		String sql = "DELETE FROM rule_pro WHERE ruleid = ?";
		Db.update(sql,this.get("rid"));
	}

	/**
	 * 新建规则
	 * 
	 * @param aUrlRule
	 *            规则的相关信息
	 */
	public void insert(Rule aUrlRule) {
		String sql = "INSERT INTO rule (urlrule,ruledesc,catid,status,usestatus) VALUES ('"
				+ aUrlRule.getStr("urlrule") + "','" + aUrlRule.getStr("ruledesc") + "','" + aUrlRule.getStr("catid")
				+ "'," + "'" + aUrlRule.getStr("status") + "','" + aUrlRule.getStr("usestatus") + "')";
		Db.update(sql);
	}

	/**
	 * 根据urlRule 查找对应的id
	 * 
	 * @param urlRule
	 * @return String 规则id
	 */
	public String selectByUrlRule(String urlRule) {
		String sql = "SELECT * FROM rule WHERE urlrule = '" + urlRule + "'";
		return String.valueOf(dao.findFirst(sql).get("id"));
	}

	/**
	 * 查询输入规则的重复性
	 * @return 查询到的条数
	 */
	public String selectRepeat() {
		String sql = "";
		if(this.get("id") == null || "".equals(this.get("id"))){
			sql = "select count(*) count from rule a where a.urlrule = ?";
			return String.valueOf(Db.findFirst(sql,this.get("urlrule")).getLong("count"));
		}else{
			sql = "select count(*) count from rule a where a.urlrule = ? and a.id != ?";
			return String.valueOf(Db.findFirst(sql,this.get("urlrule"),this.get("id")).getLong("count"));
		}
	}

	/**
	 * 添加新的规则和部署省份
	 * 
	 * @param records
	 *            新的规则
	 * @param catMap
	 *            所有行业的Map
	 * @return int 导入规则的数量
	 */
	public int addNewRulesAndPros(List<Record> records, Map<String, String> catMap) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Record> dmpRuleRecords = Db.use("dmp").find("select * from urlrule");
		for (Record record2 : dmpRuleRecords) {
			map.put(record2.getStr("rule"), record2.getInt("hostId"));
		}
		List<String> sqlList = new ArrayList<String>();
		for (Record record : records) {
			String sql = "INSERT INTO rule (urlrule,ruledesc,catid)  VALUES ('" + record.getStr("urlrule") + "'"
					+ ",'" + record.getStr("ruledesc") + "','" + catMap.get(record.getStr("cat")) + "')";
			sqlList.add(sql);
			if (!map.containsKey(record.getStr("urlrule"))) {
				saveUrlrule(record.getStr("urlrule"));
				dmpRuleRecords = Db.use("dmp").find("select * from urlrule");
			}
		}
		int[] times = Db.batch(sqlList, sqlList.size());
		String sql2 = "UPDATE rule t LEFT JOIN dmp.urlrule c ON t.urlrule = c.rule SET t.rid = c.rid WHERE t.rid IS NULL";
		Db.update(sql2);
		sqlList.clear();
		// 获取最新所有规则的Map
		Map<String, String> ruleMap = new HashMap<String, String>();
		List<Rule> ruleRecords = Rule.dao.selectAllRules();
		for (Rule ruleRecord : ruleRecords) {
			ruleMap.put(ruleRecord.getStr("urlrule"), String.valueOf(ruleRecord.get("id")));
		}
		Map<String, String> proMap = new HashMap<String, String>();
		proMap.put("A", "900000");
		proMap.put("D", "910000");
		proMap.put("E", "920000");
		proMap.put("G", "940000");
		for (Record record : records) {
			String sql = "INSERT INTO rule_pro (ruleid,provincecode,province) VALUES ('"
					+ ruleMap.get(record.get("urlrule")) + "'," + "'" + proMap.get(record.get("province")) + "','"
					+ record.get("province") + "')";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
		return times.length;
	}

	public void batchInsertUrlRule(List<RuleTemp> aurlRuleTemps, Map<String, Integer> catsMap) {
		List<String> sqlList = new ArrayList<String>();
		for (RuleTemp deployUrlRuleTemp : aurlRuleTemps) {
			String sql = "INSERT INTO rule (urlrule,ruledesc,catid) VALUES ('" + deployUrlRuleTemp.getStr("urlrule")
					+ "'," + "'" + deployUrlRuleTemp.getStr("ruledesc") + "','" + catsMap.get(deployUrlRuleTemp.getStr("cat"))
					+ "') ";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
	}

	public Record saveUrlrule(String urlrule) {
		/*Record rule = new Record();
		String host = LocalURLUtil.getTopDomain(urlrule);
		if (StrKit.isBlank(host)) {
			return null;
		}
		Record host1 = BusRule.dao.findUrlHostByHost(host);
		if (host1 == null) {
			host1 = new Record();
			host1.set("host", host);
			host1.set("hostend", host.substring(host.lastIndexOf(".")));
			Db.use("dmp").save("urlhost", host1);
			host1.set("hostId", host1.get("id"));
		}
		rule.set("hostId", host1.get("hostId"));
		rule.set("rule", urlrule);
		rule.set("status", 0);
		Db.use("dmp").save("urlrule", rule);
		rule.set("rid", rule.get("rid"));*/
		return null;
	}
	
	public void deleteById(String rid) {
		String sql = "DELETE FROM rule WHERE rid = ?";
		Db.update(sql,rid);
	}

	/**
	 * 根据ruleid删除对应的部署省份信息
	 * 
	 * @param ruleid
	 *            规则id
	 */
	public void deleteProById(String ruleid) {
		String sql = "DELETE FROM rule_pro WHERE ruleid = '" + ruleid + "'";
		Db.update(sql);
	}

	/**
	 * 根据省份编码查找规则
	 * 
	 * @param provinceCode
	 *            省份编码
	 * @return List<Record> 规则
	 */
	public List<Record> selectByPro(String provinceCode) {
		String sql = "SELECT a.catid,CASE d.provincecode WHEN '900000' THEN REPLACE(a.urlrule,'&','|') WHEN '910000' THEN "
				+ "REPLACE(a.urlrule,'&','&&') WHEN '940000' THEN REPLACE(a.urlrule,'&','\t') "
				+ "ELSE a.urlrule END urlrule,c.cat,a.ruledesc, DATE_FORMAT(a.inserttime,'%Y-%m-%d %h:%i:%s') inserttime "
				+ "FROM rule a LEFT JOIN dmp.level3 c ON a.level3Id = c.level3Id LEFT JOIN rule_pro d ON a.id = d.ruleid "
				+ "WHERE d.provincecode = '" + provinceCode + "' ORDER BY c.catid DESC,a.id DESC";
		return Db.find(sql);
	}

	public List<Record> selectRulePro() {
		String sql = "select a.urlrule,b.province,b.provincecode from rule a left join rule_pro b on a.rid = b.ruleid";
		return Db.find(sql);
	}

	public void insertIntoTemp(List<Record> records, int regId) {
		List<String> sqlList = new ArrayList<String>();
		for (Record record : records) {
			String sql = "INSERT INTO dmp.urlrule_temp (urlRule,regId,`desc`,checkMsg,status) VALUES"
					+ " ('" + record.getStr("urlRule").trim() + "','" + regId + "','" + record.getStr("desc")
					+ "', '"+record.getStr("checkMsg")+"', '"+ record.getInt("status") + "')";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
	}

	public String selectRuleRepeat(String urlrule, String procince) {
		String sql1 = "select count(*) count from rule a where a.urlrule = ? ";
		int count1 = Integer.valueOf(Db.findFirst(sql1, urlrule).getLong("count").toString());
		if(count1 == 0){
			return "1";
		}
		String sql2 = "select count(*) count from rule a JOIN rule_pro b on a.rid = b.ruleid WHERE a.urlrule = ? AND b.province = ?";
		int count2 = Integer.valueOf(Db.findFirst(sql2, urlrule, procince).getLong("count").toString());
		if(count2 == 0){
			return "2";
		}else{
			return "3";
		}
	}

	public Record selectCatRepeat() {
		String sql = "select a.rid,b.level3Id,b.level3Name from dmp.urlrule a inner join dmp.level4 b on"
				+ " a.rid = b.rid and a.rule = ? and b.`status` = 1 and b.level4Type = 2 limit 1";
		return Db.findFirst(sql, this.get("urlrule"));
	}

	/*public int[] batchInsertRulePro(List<DeployUrlRuleTemp> aurlRuleTemps, Map<String, Integer> ruleMap) {
		List<String> sqlList = new ArrayList<String>();
		for (DeployUrlRuleTemp deployUrlRuleTemp : aurlRuleTemps) {
			String sql = "INSERT INTO rule_pro (ruleid,province) values ('"
					+ ruleMap.get(deployUrlRuleTemp.getStr("urlrule")) + "','" + deployUrlRuleTemp.getStr("province") + "公司"
					+ "')";
			sqlList.add(sql);
		}
		return Db.batch(sqlList, sqlList.size());
	}*/

	public int insertFormal(int regId) {
		String sql = "insert into rule (rid,urlrule,ruledesc,level3Id) select dmpruleid,urlrule,"
				+ "ruledesc,catid from rule_temp b where b.`status` = 1 and b.regId = ?";
		return Db.update(sql,regId);
	}

	public void updateProvinceCode() {
		String sql = "update rule_pro set provincecode = case when province = 'A公司' then  '900000' when province ='D公司' "
				+ "then '910000' when province = 'E公司' then '920000' when province = '上海电信' then '930000' when province = 'G公司' then '940000' else null end where provincecode is null";
		Db.update(sql);
	}

	public Rule findByDmpRuleId(Integer rid) {
		String sql = "select * from rule a left join dmp.level3 b on a.level3Id = b.level3Id where rid = ?";
		return this.findFirst(sql, rid);
	}
	
	public List<Record> FindExportList(){
		String sql = "select concat_ws('\\t',level3Name,level3Desc,urlrule) ruleInfo  from (select CASE ?  WHEN '900000' THEN REPLACE(a.urlrule,'&','|') WHEN '910000' "
				+ "THEN REPLACE(a.urlrule,'&','&&') WHEN '940000' THEN REPLACE(a.urlrule,'&','\\t') ELSE a.urlrule "
				+ "END urlrule,a.ruledesc,c.level3Name,c.level3Desc  from "
				+ "rule a ,dmp.level3 c where a.level3Id=c.level3Id";
		if (this.get("cat") != null && !"".equals(this.getStr("cat").trim())) {
			sql += " AND c.level3Name LIKE '%" + this.getStr("cat").trim() + "%'";
		}
		if (this.get("urlrule") != null && !"".equals(this.getStr("urlrule").trim())) {
			sql += " AND a.urlrule LIKE '%" + this.getStr("urlrule").trim() + "%'";
		}
		if (this.get("ruledesc") != null && !"".equals(this.getStr("ruledesc").trim())) {
			sql += " AND a.ruledesc LIKE '%" + this.getStr("ruledesc").trim() + "%'";
		}
		if (this.get("status") != null && !"".equals(this.get("status"))) {
			sql += " AND a.status = '" + this.get("status") + "'";
		}
		if (this.get("usestatus") != null && !"".equals(this.get("usestatus"))) {
			sql += " AND a.usestatus = '" + this.get("usestatus") + "'";
		}
		sql += " AND a.rid in (select distinct ruleid from rule_pro where provincecode = ? ) ORDER BY c.level3Id DESC,a.id DESC) tb";
	    return Db.find(sql,this.get("province"),this.get("province"));
	}

	public void updateRuleId() {
		Record record = Db.use("dmp").findFirst("select * from urlrule where rule = ?",this.get("urlrule"));
		int st = Integer.valueOf(this.get("status").toString());
		if(record == null){
			Record record2 = saveUrlrule(this.get("urlrule").toString());
			Db.update("update rule set rid = ? where id = ?",record2.get("id"),this.get("id"));
			if(st == 1){
				Db.update("update dmp.urlrule set `status` = 1 where rid = ?",record2.get("id"));
			}else if(st == 0 || st == 2){
				Db.update("update dmp.urlrule set `status` = 0 where rid = ?",record2.get("id"));
			}
			return;
		}
		Db.update("update rule set rid = ? where id = ?",record.get("rid"),this.get("id"));
		if(st == 1){
			Db.update("update dmp.urlrule set `status` = 1 where rid = ?",record.get("rid"));
		}else if(st == 0 || st == 2){
			Db.update("update dmp.urlrule set `status` = 0 where rid = ?",record.get("rid"));
		}
	}

	/**
	 * 在部署规则表添加规则， 插入到dmp.urlrule,dmp.level4,rule
	 * @param urlrule 
	 * @param status 
	 * @param level3Id 
	 */
	public int insertRule(String urlrule, String status, int level3Id) {
		Record record1 = Db.findFirst("select * from dmp.urlrule a where a.rule = ?", urlrule);
		Record rule = new Record();
		if(record1 == null){
			rule = new Rule().saveUrlrule(urlrule);
		}
		/*Record rule = Db.findFirst("select * from dmp.urlrule a where a.rule = ?",urlrule);*/
		Record level4Record = Db.findFirst("select * from dmp.level4 where rid = ? and status = 1",rule.get("rid"));
		if(level4Record == null){
			String sql = "insert into dmp.level4 (level3Id,level3Name,level4Type,rid) select"
					+ " level3Id,level3Name,2," + rule.get("rid") + " from dmp.level3 where level3Id = ?";
			Db.update(sql,level3Id);
		}
		if("1".equals(status)){
			Db.update("update dmp.urlrule a set a.`status` = 1 where a.rid = ?",rule.get("rid"));
		    Db.update("update dmp.level4 a set a.`status` = 1 where a.rid = ? and a.level3Id = ?",rule.get("rid"),level3Id);
		}
		
		// 插入到部署表中
		Db.update("insert into rule(rid,urlrule,ruledesc,level3Id,status,usestatus) values('"+ rule.get("rid").toString() +"','"+ urlrule +"',"
						+ "'"+ this.getStr("ruledesc") +"','"+ String.valueOf(level3Id) +"','"+ status +"','"+ this.get("usestatus").toString() +"')");
	    return rule.getInt("rid");
	}

	public void updateExamed(String[] rids) {
		String sql = "update rule a set a.status = 1 where a.rid in (";
		for(int i=0; i < rids.length; i++){
			if(i == (rids.length - 1)){
				sql += rids[i] + ")";
			}else{
				sql += rids[i] + ",";
			}
		}
		Db.update(sql);
	}

	public void updateFromDeployedLevel3(String[] rids) {
		String sql = "select distinct(rid) rid from dmp.level4 a where `status` != 1 and level4Type = 2 and a.rid in (";
		for(int i=0; i < rids.length; i++){
			if(i == (rids.length - 1)){
				sql += rids[i] + ")";
			}else{
				sql += rids[i] + ",";
			}
		}
		List<Record> unexamedRecords = Db.find(sql);
		List<String> unexamedList = new ArrayList<String>();
		for(Record record : unexamedRecords){
			unexamedList.add(record.get("rid").toString());
		}
		if(unexamedList.size() > 0){
		String sql2 = "update dmp.level4 a left join rule b on a.rid = b.rid left join dmp.level3"
				+ " c on b.level3Id = c.level3Id set a.`status` = 1,a.level3Name = c.level3Name where a.status != 1"
				+ " and a.level4Type = 2 and a.level3Id = b.level3Id and a.rid in (";
		for(int i=0; i < unexamedList.size(); i++){
			if(i == (unexamedList.size() - 1)){
				sql2 += unexamedList.get(i) + ")";
			}else{
				sql2 += unexamedList.get(i) + ",";
			}
		}
			Db.update(sql2);
		}
	}
}