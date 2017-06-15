package cn.mmdata.mms.data.rule;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class RuleTemp extends Model<RuleTemp> {

	private static final long serialVersionUID = 1L;
	public static RuleTemp dao = new RuleTemp();
	
	public Page<Record> paginate(int pageNumber, int pageSize) {
		String sql = "  FROM mms.urlrule_temp WHERE regId = '"+ this.getInt("regId") +"'";
		/*if(StrKit.notBlank(this.getStr("cat"))){
			sql += " AND cat LIKE '%"+ this.getStr("cat").trim() +"%'";
		}*/
		if(StrKit.notBlank(this.getStr("rule"))){
			sql += " AND rule LIKE '%"+ this.getStr("rule").trim() +"%'";
		}
		if(this.getInt("status") != null){
			if(this.getInt("status") == 1){
				sql += " AND status IN (1,2)";
			}else{
				sql += " AND status IN (8,9)";
			}
		}
	    String sql2 = " ORDER BY status DESC";
		return Db.paginate(pageNumber, pageSize, "SELECT id,ruleId, urlRule, level3Id, level3Name, regId, `desc`, remark, checkMsg, status, DATE_FORMAT(createDate,'%Y-%m-%d %H:%i:%s') createDate ", sql + sql2);
	}
    
	public int deleteUnImport(String regId) {
		StringBuilder sql = new StringBuilder(" delete from urlrule_temp where regId = ? ");
		return Db.use("dmp").update(sql.toString(), regId);
	}
	
	/**
	 * 查找该用户所有状态为1的规则记录
	 * @return
	 */
	public List<RuleTemp> selectAll1() {
		String sql = "SELECT * FROM deploy_urlrule_temp a WHERE a.`status` = 1 AND a.regId = ?";
		return this.find(sql,this.getInt("regId"));
	}
	
	/**
	 * 查找该用户所有状态为2的规则记录
	 * @return
	 */
	public List<RuleTemp> selectAll2() {
		String sql = "SELECT * FROM deploy_urlrule_temp a WHERE a.`status` = 2 AND a.regId = ?";
		return this.find(sql,this.getInt("regId"));
	}
	
	public void BatchSave(List<RuleTemp> list) {
		Db.batchSave(list, 1000);
	}
	
	public List<Record> findCanSaveListByRegId(String regId) {
		return Db.use("dmp").find("select * from urlrule_temp  where status =11 and regId = ?", regId);
	}
    
	/**
	 * 更新 dmpruleid
	 * @param regId
	 */
	public void upateTable(int regId) {
        String sql = "update dmp.urlrule_temp a inner join dmp.urlrule b on a.urlRule = b.rule and a.regId = ?"
        		+ " and b.`status` != 2 inner join dmp.level4 c on b.ruleId = c.ruleId and c.`status` != 2"
        		+ "  set a.ruleId = b.ruleId,a.`status` = 14 ,a.checkMsg = '历史已存在'";
        Db.update(sql,regId);
	}
    
	/**
	 * 将status为 '1'和ststus为 '2'的更新到部署省份表中
	 * @param regId
	 */
	public int batchInsertRulePro() {
		String sql = "INSERT INTO deploy_urlrule_pro (ruleid,provincecode,province) SELECT dmpruleid ruleid,case when a.province = 'A'"
				+ " then '900000' when a.province = 'D' then '910000' when a.province = 'E' then '920000' when a.province = 'G'"
				+ " then '940000' when a.province = 'SHDX' then '930000' else null end provincecode,case when a.province = 'SHDX'"
				+ " then '上海电信' else concat(a.province,'公司') end province FROM deploy_urlrule_temp a WHERE a.`status` in (1,2) AND regId = ?";
		return Db.update(sql,this.get("regId"));
	}

	public void updateRuleId() {
		String sql = "UPDATE deploy_urlrule_temp a left JOIN dmp.urlrule b ON a.urlrule = b.rule SET a.dmpruleid ="
				+ " b.ruleId WHERE a.regId = ? and a.`status` in (1,2) and a.dmpruleid IS NULL";
		Db.update(sql,this.get("regId"));
	}

	public List<RuleTemp> selectImportData(int regId) {
		String sql = "select * from deploy_urlrule_temp where status = 1 and regId = ?";
		return this.find(sql,regId);
	}

	public void deleteByRegId(int regId) {
        String sql = "delete from dmp.urlrule_temp where regId = ?";
        Db.update(sql,regId);
	}
	
	public List<Record> findLackRules() {
		String sql = "select d.* from deploy_urlrule_temp d left join dmp.urlrule e on d.urlrule = e.rule"
				+ " where d.regId = ? and e.rule is null and d.`status` = 1;";
		return Db.find(sql,this.get("regId"));
	}

	public void insertLevel4() {
		String sql = "insert into dmp.level4 (level3Id,level3Name,level4Type,ruleId,`status`) select"
				+ " a.catid,a.cat,2,a.dmpruleid,1 from deploy_urlrule_temp a where a.`status` = 1 and a.regId"
				+ " = ? and a.dmpruleid not in (select distinct(ruleId) ruleId from dmp.level4  b where"
				+ " b.level4Type = 2);";
		Db.update(sql,this.get("regId"));
	}

	public void updateCat() {
		// 更新 status = 1 的行业
		String sql = "update deploy_urlrule_temp a,dmp.level4 b set a.catid = b.level3Id where a.dmpruleid"
				+ " = b.ruleId and b.level4Type = 2 and a.regId = ? and a.`status` = 1 ";
		Db.update(sql,this.get("regId"));
	}

}