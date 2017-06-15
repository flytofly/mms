package cn.mmdata.mms.data.crowd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;

public class Crowd extends Model<Crowd> {
	private static final long serialVersionUID = 1L;
	public static Crowd dao = new Crowd();
	
	private String whereSql(String sql, List<Object> list) {
		
		
		if (this.get("status") != null) {
			sql += " and a.status=" + this.get("status");
		}
		
		if (this.get("custype").equals(Global.USER_TYPE_BUSINESS)) {
			sql += " and a.bid= " + this.getInt("bid");
		} else if (this.get("custype").equals(Global.USER_TYPE_AGENT)) {
			sql += " and a.bid in (select bid from cy_business.cy_business_agentrel ca where ca.agent_id="
					+ this.getInt("bid") + " )";
		} else if (!this.get("custype").equals(Global.USER_TYPE_ADMIN)
				&& !this.get("custype").equals(Global.USER_TYPE_CHUANYU)) {
			sql += " and a.bid= -1 ";
		}
		return sql;

	}
	public Page<Record> paginate(int pageNumber, int pageSize) {
		List<Object> list = new ArrayList<Object>();
		String sql = " from crowd a,cy_business.cy_business b where a.bid=b.bid ";
		if (!StringUtil.isNull(this.getStr("cname"))) {
			sql += " and cname like '%" + this.getStr("cname").trim() + "%'";
		}
		if (!StringUtil.isNull(this.getStr("log_user"))) {
			sql += " and a.log_user like '%" + this.getStr("log_user").trim() + "%'";
		}
		if (!StringUtil.isNull(this.getStr("businessname"))) {
			sql += " and b.business_name like '%" + this.getStr("businessname").trim() + "%'";
		}
		sql = whereSql(sql, list);
		sql += " order by import_time desc";
		return Db.paginate(pageNumber, pageSize, "select cid, cname, ctype, pv_up, pv_down, group_type, a.log_id, a.log_user,a.status, DATE_FORMAT(import_time,'%Y-%m-%d %H:%i:%s') import_time,b.business_name ", sql,list.toArray());
	}

	public List<Record> findArea() {
		return Db.find("select provinceID id,province text,0 pid from dmp.cn_province union all select cityID id,city text,father pid from dmp.cn_city");
	}
    
	public List<Record> findProvince() {
		return Db.find("select provinceID id,province text,0 pid from dmp.cn_province ");
	}
	
	public List<Record> findAreaList() {
		return Db.find("   select * from (     SELECT 0 as pid ,  group_concat( provinceID , ':',  province) as text FROM  dmp.cn_province   union all   SELECT  provinceID as pid, group_concat( cityID , ':',  city) as text   FROM  dmp.v_area vr group by provinceID ) tt ");
	}
	
	public List<Record> findRulesByLevel3Id(Integer level3Id,String rule) {
		List<Object> list= new ArrayList<Object>();
		String sql = "select b.level1Id,b.level1Name,b.level2Id,b.level2Name,b.level3Id,b.level3Name,"
				+ "b.level3Desc,a.level4Id,a.ruleId,c.rule,a.`status`  from ( select * from dmp.level4"
				+ " d where d.status != 2 and d.level4Type = 2 ) a left join dmp.v_type b on a.level3Id"
				+ " = b.level3Id left join (select * from dmp.urlrule e where e.status != 2) c on a.ruleId"
				+ " = c.ruleId where 1=1";
		if(StrKit.notBlank(rule)){
			sql+= "	and c.rule like ?  ";
			list.add("%"+rule+"%");
		}
		if(level3Id>0){
			sql+= "	and b.level3Id = ?  ";
			list.add(level3Id);
		}
		return Db.find(sql,list.toArray());
	}

	public List<Record> getAllRules() {
		String sql="select b.level3Id, b.level3Name, b.ruleId, a.`desc`,a.rule from dmp.urlrule a,dmp.level4 b where a.`status` = 1 and b.level4Type = 2 and b.`status` = 1 and a.ruleId = b.ruleId";
		return Db.find(sql);
	}

	public List<Record> getThreeLevels(String type) {
		//String sql = "select * from dmp.v_type";
		String sql="";
		if(type.equals("retailers")){
		 sql ="select `l1`.`bid` AS `bid`,`l1`.`level1Id` AS `level1Id`,(case when (`l1`.`type` = 2) then concat('电商-',`l1`.`level1Name`) else `l1`.`level1Name` end) AS `level1Name`,`l2`.`level2Id` AS `level2Id`,`l2`.`level2Name` AS `level2Name`,`l3`.`level3Id` AS `level3Id`,`l3`.`level3Name` AS `level3Name`,`l3`.`level3Desc` AS `level3Desc`,`l1`.`type` AS `eb_type` from (( dmp.`level1` `l1` left join  dmp.`level2` `l2` on((`l1`.`level1Id` = `l2`.`level1Id`))) left join  dmp.`level3` `l3` on((`l2`.`level2Id` = `l3`.`level2Id`)))  "
		 		+ "where 1=1  and  `l1`.`type`='2'";
		}else{
			 sql ="select `l1`.`bid` AS `bid`,`l1`.`level1Id` AS `level1Id`,(case when (`l1`.`type` = 2) then concat('电商-',`l1`.`level1Name`) else `l1`.`level1Name` end) AS `level1Name`,`l2`.`level2Id` AS `level2Id`,`l2`.`level2Name` AS `level2Name`,`l3`.`level3Id` AS `level3Id`,`l3`.`level3Name` AS `level3Name`,`l3`.`level3Desc` AS `level3Desc`,`l1`.`type` AS `eb_type` from (( dmp.`level1` `l1` left join  dmp.`level2` `l2` on((`l1`.`level1Id` = `l2`.`level1Id`))) left join  dmp.`level3` `l3` on((`l2`.`level2Id` = `l3`.`level2Id`)))  "
				 		+ "where 1=1  and  `l1`.`type`='1'";
		}
		return Db.find(sql);
	}

	public List<Record> getRuleList(String cid) {
		return Db.find("select * from crowd_rule a where a.cid = ? and a.`status` = 1",cid);
	}

	public List<Record> getRulesByLevel3Id(Record record) {
		String sql = "select a.level3Id,a.level3Name,a.ruleId,a.`desc`,b.rule from dmp.v_level4 a inner join"
				+ " dmp.urlrule b on a.ruleId = b.ruleId where a.`status` = 1 and b.`status`=1  and a.level4Type = 2 and a.level3Id = ?";
		return Db.find(sql,record.get("level3Id"));
	}
    
	// 删除任务对应的所有规则 
	public void deleteRulesByCid(String cid) {
		Db.update("update crowd_rule set status = 9 where cid = ?",cid);
	}

	public void insertNewRulesByLevel3Id(String cid) {
		String sql = "insert into crowd_rule(rid,cid,level3Id,`rule`,rule_desc,`status`) select b.ruleId,'"+ cid +"',"
				+ "level3Id,b.rule,a.desc,1 from dmp.v_level4 a inner join dmp.urlrule b on a.ruleId = b.ruleId"
				+ " where a.`status` = 1 and b.`status` = 1 and a.level4Type = 2 and a.level3Id in (";
		Set<Integer> set = this.get("addLevel3IdSet1");
		Object[] setArray = set.toArray();
		for(int i=0; i < setArray.length ; i++){
		   if(i == (setArray.length - 1)){
			   sql += setArray[setArray.length - 1] + ")";
		   }else{
			   sql += setArray[i] + ",";  
		   }
		}
		Db.update(sql);
	}

	public void deleteRulesByLevel3Id(List<Integer> delLevel3IdList1) {
		String sql = "update crowd_rule set status = 9 where level3Id in(";
        if(delLevel3IdList1.size() > 0){
		for(int i=0; i < delLevel3IdList1.size() ; i++){
			   if(i == (delLevel3IdList1.size() - 1)){
				   sql += delLevel3IdList1.get(delLevel3IdList1.size() - 1) + ")";
			   }else{
				   sql += delLevel3IdList1.get(i) + ",";  
			   }
			}
		Db.update(sql);
	  }
	}
	
	public void deleteRulesByRid(List<Integer> delLevel3IdList1) {
		String sql = "update crowd_rule set status = 9 where rid in(";
        if(delLevel3IdList1.size() > 0){
		for(int i=0; i < delLevel3IdList1.size() ; i++){
			   if(i == (delLevel3IdList1.size() - 1)){
				   sql += delLevel3IdList1.get(delLevel3IdList1.size() - 1) + ")";
			   }else{
				   sql += delLevel3IdList1.get(i) + ",";  
			   }
			}
		Db.update(sql);
	  }
	}

	public void insertRules(Set<Integer> addLevel4IdSet, HashMap<Integer, Integer> ridLevel3IdMap, Map<Integer, String> selectedLevel4Map, HashMap<Integer, Object> ridDescMap, String cid) {
		Object[] setArray = addLevel4IdSet.toArray();
		List<String> sqlList = new ArrayList<String>();
		for(int i=0; i < setArray.length ; i++){
			String desc = ridDescMap.get(setArray[i]) == null ? null : "'"+ridDescMap.get(setArray[i])+"'";			
			String sql = "insert into crowd_rule(rid,cid,level3Id,rule,rule_desc,status) values ('"+ setArray[i] +"','"+ cid +"','"+ ridLevel3IdMap.get(setArray[i]) +"','"+ selectedLevel4Map.get(setArray[i]) +"',"+ desc +",1);";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
	}

	public Page<Record> findCustomizeRules(int pageNumber,int pageSize,String cid) {
		String sql = "FROM crowd_rule cr LEFT JOIN(   select t2.ruleid,sum(t2.pv) pv  from cy_business.a_rulesource_count"
				+ " t2  where t2.countdate =   (select max(t1.countdate) from cy_business.a_rulesource_count t1)"
				+ " group by t2.ruleid ) t3 on cr.rid = t3.ruleId  LEFT JOIN dmp.v_type vt on 	cr.level3Id ="
				+ " vt.level3Id where cr.status = 1 and cid = ?";
		String select = "SELECT vt.level1Name,vt.level2Name,vt.level3Name,cr.rid,rule_desc,ifnull(t3.pv,0) pv";
		return Db.paginate(pageNumber, pageSize, select, sql,cid);
	}

	public Page<Record> findCustomizeScenes(int pageNumber,int pageSize,String cid) {
		String sql = " from crowd_scene a left join scene b on a.sid = b.sid where b.`status` = 1 and a.cid = ?";
		String select  = "select a.sid,a.cid,b.*";
		return Db.paginate(pageNumber, pageSize, select, sql,cid);
	}

	public Page<Record> statis(int pageNumber, int pageSize) {
		String sql = " from (select * from crowd_rule aa where aa.cid = ? and aa.`status` = 1) a"
				+ " left join cy_business.a_rulesource_count b on a.rid = b.ruleid and b.ruletype = 2"
				+ " left join crowd c on a.cid = c.cid  "
				+ " where b.countdate is not null "
				+ " group by b.countdate order by b.countdate desc";
		String select = "select a.cid,c.cname,sum(ifnull(b.uv,0)) usernum,ifnull(b.countdate,'') countdate";
		return Db.paginate(pageNumber, pageSize, select, sql,this.get("cid"));
	}

	public int batchUpdateUseStatus(Integer status, String paraStr) {
		return Db.update("update crowd a set a.`status`  = ? where a.cid in (" + paraStr + ")",status);
	}
	
	
		public void insetCrowdGroup(String group_type,String group_num,String group_ratio  ){
			String sql=" INSERT  INTO crowd_group (group_type,group_num,group_ratio)  VALUES( "+group_type+","+group_num+","+group_ratio+");";
			
			Db.update(sql);
			
			
		}
		public List<Record> findCustomizeAreas(String cid) {
			return Db.find("select a.province,group_concat(a.city separator '	 ') citystr from crowd_area a where a.cid = ? group by a.province_id",cid);
		}
		
		public void deleteGroupByCid(int cid) {
			Db.update("delete from crowd_group where cid = ?", cid);
		}
		
		public void insertGroupInfo(int cid,List<String> list) {
			List<String> sqlList = new ArrayList<String>();
			for(String str : list){
				String sql = "insert into crowd_group(cid,group_num) values('"+ cid +"','"+ str +"')";
				sqlList.add(sql);
			}
			Db.batch(sqlList, sqlList.size());
		}
		
		public List<Record> findCrowdGroups(String cid) {
			return Db.find("select group_num from crowd_group where cid = ?", cid);
		}
		
		
		// 统计
		public  List<Record> CrowdStatistics(){
			List<Object> list = new ArrayList<Object>();
			String sql="SELECT count(*) AllCrowd,"
					+ " ifnull(SUM(CASE WHEN status = 0 THEN 1 ELSE 0 END),0) WaitingAudit,"
					+ " ifnull(SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END),0) ThroughAudit,"
					+ " ifnull(SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END),0) NotAudit,"
					+ " ifnull(SUM(CASE WHEN ifnull(d.usednum,0) > 0  THEN 1 ELSE 0 END),0) cusednum,"
					+ " ifnull(SUM(CASE WHEN ifnull(d.usednum,0) = 0  THEN 1 ELSE 0 END),0) cunusednum"
					+ " FROM  mms.crowd a"
					+ " LEFT JOIN (" 
					+ " SELECT pc.cid,"
					+ " ifnull(count(bpd.detail_id),0) cnum,"
					+ " ifnull(SUM(CASE WHEN status = 1 AND bpd.begindate < now() AND bpd.enddate > now() THEN 1 ELSE 0 END),0) usednum"
					+ " FROM cy_business.cy_business_project_detail bpd,cy_business.cy_business_project_detail_output bpdo,cy_business.cy_business_project_detail_crowd pc"
					+ " WHERE bpd.detail_id = bpdo.detail_id  "
					+ " AND bpd.detail_id = pc.detail_id"
					+ " AND bpd.project_type = 6 and bpd.`status` != 9"
					+ " GROUP BY pc.cid	) d ON a.cid = 	d.cid WHERE 1 = 1 	";
			sql = whereSql(sql, list);
			
			return Db.find(sql,list.toArray());
		}
		
		public int CrowdCount(){
			String sql =" select count(*) from crowd a,cy_business.cy_business b where a.bid=b.bid";
			return Db.queryInt(sql);
		}
		
		public Crowd  findByCid(int cid){
			String sql="select a.`*`,b.business_name from crowd a,cy_business.cy_business b where a.bid=b.bid and a.cid=? ";
			return this.findFirst(sql,cid);
		}
		
		public Record dailyUsers(Set<String> ruleIdSet) {
			String sql = "select sum(t3.pv) pv  from dmp.level4devcountday t3  where t3.level4Id in (";
			for(String ruleId : ruleIdSet){
				sql += ruleId + ",";
			}
			sql = sql.substring(0,sql.lastIndexOf(","));
			sql += ")";
			sql += " and t3.regDate = (select regDate from dmp.level4devcountday t1 order by t1.regDate desc limit 1)";
			return Db.findFirst(sql);
		}
}
