package cn.mmdata.mms.delivery.material;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;

public class Material extends Model<Material> {
	
	private static final long serialVersionUID = 1L;
	public static Material dao = new Material();
	
	public String whereCondition(String sql){
		if(this.get("name") != null && !"".equals(this.getStr("name").trim())){
			sql += " and a.name like '%" + this.getStr("name").trim() + "%'";
		}
		if(this.get("context") != null && !"".equals(this.getStr("context").trim())){
			sql += " and a.context like '%" + this.getStr("context").trim() + "%'";
		}
		if(this.get("m_type") != null && !"".equals(this.get("m_type"))){
			sql += " and a.`m_type` = "+this.get("m_type")+"";
		}
		if(this.get("log_id") != null && !"".equals(this.get("log_id"))){
			sql += " and a.`log_id` = "+this.get("log_id")+"";
		}
		if(this.get("status") != null && !"".equals(this.get("status"))){
			sql += " and a.`status` = "+this.get("status")+"";
		}
		
		if(this.get("shorturl") != null && !"".equals(this.getStr("shorturl").trim())){
			sql += " and a.shorturl like '%" + this.getStr("shorturl").trim() + "%'";
		}
		if(this.get("linkurl") != null && !"".equals(this.getStr("linkurl").trim())){
			sql += " and a.linkurl like '%" + this.getStr("linkurl").trim() + "%'";
		}
		
		if(this.get("custype")!=null){
			if (this.get("custype").equals(Global.USER_TYPE_BUSINESS)) {
				sql += " and a.bid= " + this.getInt("bid");
			} else if (this.get("custype").equals(Global.USER_TYPE_AGENT)) {
				sql += " and a.bid in (select bid from cy_business.cy_business_agentrel ca where ca.agent_id="
						+ this.getInt("bid") + " )";
			} else if (!this.get("custype").equals(Global.USER_TYPE_ADMIN)
					&& !this.get("custype").equals(Global.USER_TYPE_CHUANYU)) {
				sql += " and a.bid= -1 ";
			}
		}
		
		return sql;
	}
	
	public Page<Record> list(int pageNumber, int pageSize,Integer UserId) {
    	/*String sql = " from material a left join  cy_business.cy_business_project_detail b on a.mid = b.mid and b.`status` = 2 left join"
    			+ " cy_business.cy_customer  c on a.log_id = c.customer_id where a.status != 9 ";
    	sql =  whereCondition(sql,UserId);
    	sql += " group by a.mid order by a.import_time desc,a.mid desc";
    	String select ="select distinct(a.mid),a.name,a.m_type,a.linkurl,a.shorturl,a.content,a.log_id,"
    			+ " DATE_FORMAT(a.lastupdatedate,'%Y-%m-%d %H:%i:%s') lastupdatedate ,a.`status`, DATE_FORMAT(a.import_time,'%Y-%m-%d %H:%i:%s') import_time,sum(if(b.mid is null,0,1)) refernum,c.customer_name";
    	*/
		
    	String sql ="from mms.material a "
				+" left join  (select ou.mid,count(*) refernum from cy_business.cy_business_project_detail_output ou "
				+" where ou.mid is not null "
				+" group by ou.mid  ) da on a.mid=da.mid "
				+" left join cy_business.cy_business b on  a.bid=b.bid "
				+" where a.status != 9   ";
    	
    	if (!StringUtil.isNull(this.getStr("businessname"))) {
			sql += " and b.business_name like '%" + this.getStr("businessname").trim() + "%'";
		}
    	sql =  whereCondition(sql);
    	sql +=" order by a.lastupdatedate desc ";
    	String select = "select a.mid,a.name,a.m_type,a.linkurl,a.shorturl, if(a.linkurl=null,REPLACE(a.content,a.linkurl,'目标链接'),a.content) content,a.log_id,a.log_name,"
    			+ "DATE_FORMAT(a.lastupdatedate,'%Y-%m-%d %H:%i:%s') lastupdatedate ,"
    			+ "a.`status`, DATE_FORMAT(a.import_time,'%Y-%m-%d %H:%i:%s') import_time,ifnull(da.refernum,0) refernum,b.business_name";
    	return Db.paginate(pageNumber, pageSize, select,sql);
    	
    }

	public void deleteByMid(Integer mid) {
		Db.update("update material set status = 9 where mid = ?",mid);
	}

	public void insertBlackListTemp(List<Record> records, Integer regId) {
		List<String> sqlList = new ArrayList<String>();
		for(Record record : records){
			String sql = "insert into material_temp (black_info,blacklist_type,black_channel,import_user_id,status,errorInfo) values("
					+ "'"+ record.getStr("black_info").trim() +"',"+ record.get("blacklist_type") +","+ record.get("black_channel") +","
					+ ""+ regId +","+ record.get("status") +",'"+ record.getStr("errorInfo").trim() +"')";
			sqlList.add(sql);
		}
		Db.batch(sqlList, sqlList.size());
		// 验证黑名单的重复
		String sql =  "update material_temp a inner join material b on a.black_info ="
				+ " b.black_info set a.`status` = 0,a.errorInfo = '黑名单内容重复！' where"
				+ " a.black_info is not null and a.import_user_id = ? and a.status = 1;";
		Db.update(sql,regId);
	}

	public Page<Record> tempList(Integer pageNum, Integer numPerPage, Integer regId) {
		String sql = " from material_temp bl where import_user_id = ?";
    	return Db.paginate(pageNum, numPerPage, "select *",sql,regId);
    }

	public void clearTempData(Integer regId) {
		String sql = "delete from material_temp where import_user_id = ?";
    	Db.update(sql,regId);
	}

	public void certainImport(Integer regId, Integer deptId) {
		String sql = "insert into material (black_info,blacklist_type,black_channel,`status`,dept_id) select"
				+ " a.black_info,a.blacklist_type,a.black_channel,a.`status`,"+ deptId  +" from material_temp a left"
				+ " join material b on a.black_info = b.black_info where a.`status` = 1 and b.black_info"
				+ " is null and a.import_user_id = ?;";
    	Db.update(sql,regId);
	}

	public List<Record> statis() {
		List<Object> paraList = new ArrayList<Object>();
		String sql = "select ifnull(sum(case a.black_channel when 1 then 1 end),0) appNum,"
				+ "ifnull(sum(case a.black_channel when 2 then 1 end),0) messNum,ifnull"
				+ "(sum(case a.black_channel when 3 then 1 end),0) wechatNum,ifnull(sum("
				+ "case a.black_channel when 4 then 1 end),0) mailNum,ifnull(sum(case"
				+ " a.black_channel when 5 then 1 end),0) yiXinNum,ifnull(sum(case"
				+ " a.black_channel when 6 then 1 end),0) webNum,ifnull(sum(case a.black_channel"
				+ " when 7 then 1 end),0) weiBoNum,a.blacklist_type  from material a"
				+ " where a.`status` = 1";
		if(this.getInt("customer_type") == 1 || this.getInt("customer_type") == 5){
			sql += " and a.dept_id IN (select ? dept_id union select a.dept_id from dx_dept a where"
					+ " a.parent_dept_id = ? and a.`status` = 1  union select b.dept_id from"
					+ " dx_dept a left join dx_dept b on a.dept_id   = b.parent_dept_id where"
					+ " a.parent_dept_id = ? and  b.parent_dept_id is not null and"
					+ " a.`status` = 1 and b.`status` = 1 union select c.dept_id from dx_dept"
					+ " a left join dx_dept b on  a.dept_id = b.parent_dept_id left join dx_dept"
					+ " c on b.dept_id = c.parent_dept_id where  a.parent_dept_id = ?"
					+ " and b.parent_dept_id is not null and c.parent_dept_id is not null and"
					+ " a.`status` = 1 and b.`status` = 1 and c.`status` = 1)";
			  paraList.add( this.getInt("dept_id"));
			  paraList.add( this.getInt("dept_id"));
			  paraList.add( this.getInt("dept_id"));
			  paraList.add( this.getInt("dept_id"));
			}
		sql += " group by a.blacklist_type";
		return Db.find(sql,paraList.toArray());
	}

	//统计
	public List<Record> MaterialStatistics(){
		String sql="SELECT count(*) Allmaterial,"
				+ " ifnull(SUM(CASE WHEN status = 0 THEN 1 ELSE 0 END),0) WaitingAudit, "
				+ " ifnull(SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END),0) ThroughAudit, "
				+ "  ifnull(SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END),0) NotAudit,"
				+ " ifnull(SUM(CASE WHEN ifnull(d.usednum,0) > 0  THEN 1 ELSE 0 END),0) cusednum,"
				+ " ifnull(SUM(CASE WHEN ifnull(d.usednum,0) = 0  THEN 1 ELSE 0 END),0) cunusednum"
				+ " FROM  mms.material a"
				+ " LEFT JOIN ("
				+ " SELECT pc.mid,"
				+ " count(bpd.detail_id) cnum,"
				+ " ifnull(SUM(CASE WHEN bpd.status = 1 AND bpd.begindate < now() AND bpd.enddate > now() THEN 1 ELSE 0 END),0) usednum"
				+ " FROM cy_business.cy_business_project_detail bpd,cy_business.cy_business_project_detail_output bpdo,mms.material pc"
				+ " WHERE bpd.detail_id = bpdo.detail_id  "
				+ " AND pc.mid = bpdo.mid"
				+ " AND bpd.project_type = 6 and bpd.`status` != 9"
				+ " GROUP BY pc.mid	) d ON a.mid = 	d.mid WHERE 1 = 1 ";
		sql = whereCondition(sql);
		
	   return Db.find(sql);
	}
	public Material findByMid(int mid){
		String sql="select a.`*`,b.business_name from material a,cy_business.cy_business b where a.bid=b.bid and a.mid=?";
		return this.findFirst(sql,mid);
	}
	
	
	
	
	
	//统计
	public int MaterialCount(){
		String sql="select count(*)"
				+ "from mms.material a  left join  (select ou.mid,count(*) refernum from cy_business.cy_business_project_detail_output ou  where ou.mid is not null  group by ou.mid  ) da on a.mid=da.mid   "
				+ "left join cy_business.cy_business b on  a.bid=b.bid  where a.status != 9    order by a.lastupdatedate desc  ";
		sql = whereCondition(sql);
		
	   return Db.queryInt(sql);
	}
	
	
	
	
	
	
}