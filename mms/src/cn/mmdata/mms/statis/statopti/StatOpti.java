package cn.mmdata.mms.statis.statopti;

import java.util.ArrayList;
import java.util.List;

import cn.mmdata.commons.util.Global;
import cn.mmdata.mms.data.crowd.Crowd;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StatOpti extends Model<StatOpti> {

	private static final long serialVersionUID = 1L;

	public static Crowd dao = new Crowd();

	private String whereSql(String sql, List<Object> list) {

		
		if (this.get("detail_name") != null) {
			sql += " and detail_name like '%"
					+ this.getStr("detail_name").trim() + "%'";
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
		String sql = " FROM cy_business.v_feedbak_sumstat a "
				+ " left join cy_business.cy_business cb on a.bid=cb.bid "
				+ "where 1=1  ";
		if(this.getStr("business_name")!=null){
			sql+=" and  cb.business_name like '%"+this.getStr("business_name").trim()+"%'";
		}
		if (this.get("status") != null) {
			sql += " and a.status=" + this.get("status");
		}
		if (this.get("out_type") != null) {
			sql += " and a.out_type=" + this.get("out_type");
		}
		if (this.get("pickday") != null) {
			sql += " and a.pickday =  '" + this.get("pickday") + "'";
		}
		sql = whereSql(sql, list);
		sql += " order by detail_id desc ";
		return Db.paginate(pageNumber, pageSize, "select a.*,cb.business_name ", sql,
				list.toArray());
	}

	public Page<Record> detailPaginate(int pageNumber, int pageSize) {
		String sql = "";
		if (this.get("out_type") != null && this.getInt("out_type") == 1) {
			sql = " FROM cy_business.v_feedback_daystat afc where 1=1  ";

		} else if (this.get("out_type") != null && this.getInt("out_type") == 2) {
			sql = " FROM cy_business.v_sms_daystat afc where 1=1  ";
		}
		sql += " and detail_id=" + this.get("detail_id");
		if (this.get("pickday") != null) {
			sql += " and pickday = '" + this.get("pickday")+"'";
		}
		sql += " order by pickday desc ";

		return Db.paginate(pageNumber, pageSize, "select * ", sql);
	}
	public Page<Record> detailRulePaginate(int pageNumber, int pageSize) {
		List<Object> list = new ArrayList<Object>();
		String selStr ="SELECT pd.detail_id,pd.kid ruleId,pd.keyword rule,pd.`desc` ,"
				+"ifnull(fd.allnum,0) allnum,ifnull(fd.yxnum,0) yxnum,ifnull(fd.zhl,0.00) zhl,"
				+"ifnull(fd.yxanum,0) yxanum,ifnull(fd.yxbnum,0) yxbnum,ifnull(fd.yxcnum,0) yxcnum";
		
		
		String sql =" FROM cy_business_project_sskw pd "
				+" LEFT JOIN ( "
				+"	SELECT detail_id,ruleid,allnum,  yxnum,  zhl, yxanum,  yxbnum,  yxcnum"
				+" FROM  a_feedback_rulecount where 1=1";
				if (this.get("detail_id") != null) {
					sql += " and detail_id = " + this.get("detail_id");
				}
				sql +=" ) fd on pd.detail_id = fd.detail_id AND pd.kid = fd.ruleId";
		
		sql += " where pd.detail_id=" + this.get("detail_id");
		if (this.get("pickday") != null) {
			sql += " and pickday = '" + this.get("pickday")+"'";
		}
		Page<Record> rlist =Db.use("zhijian").paginate(pageNumber, pageSize, selStr, sql,list.toArray());
		return  rlist;
	}
	public Page<Record> detailYhjy(int pageNumber, int pageSize) {
		String sql = " FROM dmp.recommand_datas fb"	
				   + " LEFT JOIN dmp.urlrule ur ON 	fb.ruleId = ur.ruleId "	
				   + " LEFT JOIN ("	
				   + " 	SELECT l4.ruleId,sum(l4c.number) uv FROM dmp.level4devcount l4c,dmp.level4 l4"	
				   + "  WHERE l4.level4Id = l4c.level4Id AND l4.level4Type = 2 AND l4.ruleId in("
				   + "		SELECT ruleId FROM dmp.recommand_datas WHERE detail_id = " + this.get("detail_id")	
				   + "  ) GROUP BY l4.ruleId"
				   + " ) rc ON fb.ruleId = rc.ruleId "
				   + " WHERE 1 = 1 AND fb.status = 1 ";
		sql += " AND detail_id=" + this.get("detail_id") + " ORDER BY fb.type asc";

		return Db.paginate(pageNumber, pageSize, "SELECT fb.ruleId as ruleId ,ur.rule,ur.`desc` ,rc.uv,fb.uids,fb.uidTotal,fb.`type`,ifnull(format(((fb.uids * 100) /fb.uidTotal),2),0.00) zhl ", sql);
	}

	public int detailRuleAdd(int detailId, int ruleId) {
		String sql = " INSERT INTO cy_business.cy_business_project_sskw( `detail_id`, `level4type`, `kid`, `keyword`, `desc`,  `status`, `insertTime`) "
					 +" SELECT rd.detail_id,rd.ruleType,rd.ruleId,ur.rule,ur.`desc`,1,now() "
					 +" FROM dmp.recommand_datas rd,dmp.urlrule ur "
					 +" WHERE rd.ruleType = 2 AND rd.ruleId = ur.ruleId   and  detail_id = " + detailId + " AND rd.ruleId = " + ruleId;
		
		Db.update(sql);
		sql = " UPDATE dmp.recommand_datas SET status = 9 WHERE detail_id = " + detailId + " AND ruleId = " + ruleId;
		return Db.update(sql);
	}
	public int detailRuleDel(int detailId, int ruleId) {
		String sql = " UPDATE cy_business.cy_business_project_sskw SET status = 9 WHERE level4type = 2 AND detail_id = " + detailId + " AND kid = " + ruleId;
		Db.update(sql);
		sql = " UPDATE dmp.recommand_datas SET status = 9 WHERE detail_id = " + detailId + " AND ruleId = " + ruleId;
		return Db.update(sql);
	}

	
	//首页 统计使用 规则统计TOP10
	
	public List<Record>  StatOptiStatistics(){
		
		String sql="select COUNT(DISTINCT telephone) fb_num,  a.rule, SUM(CASE WHEN result_type IN (5, 6, 7) THEN	1 ELSE	0 END	) yxnum, IFNULL(FORMAT(SUM(CASE WHEN result_type IN (5, 6, 7) THEN"
				+ " 1 ELSE	0 END	) * 100 / COUNT(DISTINCT telephone),	2	),	'0.00'	) zhl from cy_business.a_feedback_data a  group by a.ruleId order by zhl desc limit 10;";
		return Db.find(sql);
	}
	
	//坐席任务统计
	public List<Record> getSeatTask(){
	  String sqlbid = bidSql();
	  String sql="select sum(zx_count) zx_count,sum(picknumber) picknumber, sum(fb_count) fb_count,sum(yx_count) yx_count from ("
			+ " SELECT count(*) zx_count,0 picknumber,0 fb_count,0 yx_count FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
			+ " WHERE a.project_type = 6 and a.`status` != 9 and  a.detail_id = b.detail_id and b.out_type = 1"
			+ sqlbid
			+ " union all"
			+ " SELECT 0 zx_count,ifnull(SUM(a.picknumber),0) picknumber, ifnull(SUM(a.fb_count),0) fb_count, ifnull(SUM(a.yx_count),0) yx_count"
			+ " FROM ("
			+ " SELECT a.detail_id"
			+ " FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
			+ " WHERE a.project_type = 6 and a.`status` != 9 AND a.detail_id = b.detail_id AND b.out_type = 1 "
			+ sqlbid
			+ ") bb,cy_business.a_feedback_count a"
			+ " WHERE bb.detail_id = a.detail_id) c";
		return Db.find(sql);
	}
	
	//短息任务统计
	public List<Record> getMessageTask(){
	    /*String sql="select sum(zx_count) mess_count,sum(picknumber) picknumber, sum(fb_count) fb_count,sum(yx_count) yx_count from ("
	    		+ " SELECT count(*) zx_count,0 picknumber,0 fb_count,0 yx_count FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
	    		+ " WHERE a.project_type = 6 and a.`status` != 9 and  a.detail_id = b.detail_id and b.out_type = 2"
	    		+ " union all"
	    		+ " SELECT 0 zx_count,ifnull(SUM(a.picknumber),0) picknumber, ifnull(SUM(a.fb_count),0) fb_count, ifnull(SUM(a.yx_count),0) yx_count"
	    		+ " FROM ("
	    		+ " SELECT a.detail_id"
	    		+ " FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
	    		+ " WHERE a.project_type = 6 and a.`status` != 9 AND a.detail_id = b.detail_id AND b.out_type = 2 ) bb,cy_business.a_feedback_count a"
	    		+ " WHERE bb.detail_id = a.detail_id) c";
	    */
		String sqlbid = bidSql();
		
	    String sql="select sum(zx_count) mess_count,sum(picknumber) picknumber, sum(fb_count) fb_count,sum(yx_count) yx_count from ("
	    		+ " SELECT count(*) zx_count,0 picknumber,0 fb_count,0 yx_count FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
	    		+ " WHERE a.project_type = 6 and a.`status` != 9 and  a.detail_id = b.detail_id and b.out_type = 2"
	    		+ sqlbid
	    		+ " union all"
	    		+ " SELECT 0 zx_count,ifnull(SUM(a.picknumber),0) picknumber, ifnull(SUM(a.fb_count),0) fb_count, ifnull(SUM(a.yx_count),0) yx_count"
	    		+ " FROM ("
	    		+ " SELECT a.detail_id"
	    		+ " FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b"
	    		+ " WHERE a.project_type = 6 and a.`status` != 9 AND a.detail_id = b.detail_id AND b.out_type = 2 "
	    		+ sqlbid
	    		+ ") bb,cy_business.a_feedback_count a"
	    		+ " WHERE bb.detail_id = a.detail_id) c";
	    
	    
	    return Db.find(sql);
	}

	private String bidSql() {
		String sqlbid="";
		if (this.get("custype").equals(Global.USER_TYPE_BUSINESS)) {
			sqlbid += " and a.bid= " + this.getInt("bid");
		} else if (this.get("custype").equals(Global.USER_TYPE_AGENT)) {
			sqlbid += " and a.bid in (select bid from cy_business.cy_business_agentrel ca where ca.agent_id="
					+ this.getInt("bid") + " )";
		} else if (!this.get("custype").equals(Global.USER_TYPE_ADMIN)
				&& !this.get("custype").equals(Global.USER_TYPE_CHUANYU)) {
			sqlbid += " and a.bid= -1 ";
		}
		return sqlbid;
	}
	
	
	//坐席统计
	public Page<Record> getSeatStatistics(int pageNumber,int pageSize){
		List<Object> list = new ArrayList<Object>();
		String bidSql  =this.bidSql();
		String select="select aa.detail_id,aa.detail_name,ifnull(aa.canpick_day,0) canpick_day, ifnull(aa.minreqnum_day,0) minreqnum_day,ifnull(sum(bb.picknumber),0) picknumber, ifnull(sum(bb.fb_count),0) fb_count,ifnull(sum(bb.yx_count),0) yx_count,ifnull(sum(bb.yxa_count),0) yxa_count, ifnull(sum(bb.yxb_count),0) yxb_count,"
				+ " FORMAT(ifnull(sum(bb.yx_count)/sum(bb.fb_count),0) * 100 ,2) zhl ";
		String  sqlExceptSelect="from (SELECT a.detail_id,a.detail_name,a.canpick_day,a.minreqnum_day FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b WHERE a.project_type = 6 and  a.detail_id = b.detail_id and b.out_type = 1 and a.`status` != 9"
				+ bidSql
				+ ") aa"
				+ " left join cy_business.a_feedback_count bb on aa.detail_id = bb.detail_id  group by aa.detail_id";
		return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect,list.toArray());
	}
	
	//短信统计
		public Page<Record> getMessageStatistics(int pageNumber,int pageSize){
			List<Object> list = new ArrayList<Object>();
			String bidSql  =this.bidSql();
			String select="select aa.detail_id,aa.detail_name,ifnull(aa.canpick_day,0) canpick_day, ifnull(aa.minreqnum_day,0) minreqnum_day,ifnull(sum(bb.picknumber),0) picknumber, ifnull(sum(bb.fb_count),0) fb_count,ifnull(sum(bb.yx_count),0) yx_count,ifnull(sum(bb.yxa_count),0) yxa_count, ifnull(sum(bb.yxb_count),0) yxb_count,"
					+ " FORMAT(ifnull(sum(bb.yx_count)/sum(bb.fb_count),0) * 100,2) zhl";
			String  sqlExceptSelect=" FROM (SELECT a.detail_id,a.detail_name,a.canpick_day,a.minreqnum_day FROM cy_business.cy_business_project_detail a,cy_business.cy_business_project_detail_output b WHERE a.project_type = 6 and  a.detail_id = b.detail_id and b.out_type = 2 and a.`status` != 9"
					+ bidSql
					+ ") aa "
					+ " left join cy_business.a_feedback_count bb on aa.detail_id = bb.detail_id  group by aa.detail_id ";
			return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect,list.toArray());
		}
}
