package cn.mmdata.mms.statis.rulestatis;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class RuleStatis extends Model<RuleStatis> {
	private static final long serialVersionUID = 1L;
	public static RuleStatis dao = new RuleStatis();
	
	/*private String whereSql(String sql, List<Object> list) {
		
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
	*/
	public Page<Record> list(int pageNumber, int pageSize) {
		StringBuilder mergeSql = new StringBuilder();
		List<Object> paraList = new ArrayList<>();
		String begindate = this.get("begindate");
		String enddate = this.get("enddate");
		String sql1 = "SELECT aa.pickday,aa.total_num,cc.feedback_num,bb.use_num,cc.tj_num,cc.gj_num,cc.wjt_num,cc.wyx_num,cc.yxa_num,cc.yxb_num,cc.yxc_num,cc.kh_num,cc.qt_num";
	    String sql2 = " FROM ( SELECT a.pickday, SUM(a.picknumber) total_num FROM cy_business.a_url_bdata_count a";
		String sql3 = " GROUP BY a.pickday ORDER BY a.pickday ASC) aa LEFT JOIN ( SELECT DATE(a.reg_time) send_time, SUM(a.number) use_num FROM cy_business.a_urltask_mail a";
		String sql4 = " GROUP BY DATE(a.send_time) ORDER BY DATE(a.send_time) ASC) bb ON aa.pickday = bb.send_time"
				+ " LEFT JOIN ( SELECT DATE(a.picktime) picktime, COUNT(a.fb_data_id) feedback_num, SUM(IF(a.result_type = 1,1,0))"
				+ " AS tj_num, SUM(IF(a.result_type = 2,1,0)) AS gj_num, SUM(IF(a.result_type = 3,1,0)) AS wjt_num,"
				+ " SUM(IF(a.result_type = 4,1,0)) AS wyx_num, SUM(IF(a.result_type = 5,1,0)) AS yxa_num, SUM(IF(a.result_type = 6,1,0)) AS yxb_num, SUM(IF(a.result_type = 7,1,0)) AS yxc_num, SUM(IF(a.result_type = 8,1,0)) AS kh_num, SUM(IF(a.result_type = 9,1,0)) AS qt_num FROM ";
		if(this.get("bid") != null && !"".equals(this.get("bid"))){
			sql4 += " (select * from cy_business.a_feedback_data where bid = "+ this.get("bid") +") a";
		}else{
			sql4 += " cy_business.a_feedback_data a";
		}
		
		String sql5 = " GROUP BY DATE(a.picktime) ORDER BY DATE(a.picktime) ASC) cc ON aa.pickday = cc.picktime ORDER BY aa.pickday DESC";
		if(StrKit.notBlank(begindate) && StrKit.notBlank(enddate)){
			mergeSql.append(sql2);
			mergeSql.append(" where pickday >= ? and pickday <= ?");
			mergeSql.append(sql3);
			mergeSql.append(" where DATE(a.reg_time) >= ? and DATE(a.reg_time) <= ?");
			mergeSql.append(sql4);
			mergeSql.append(" where DATE(a.picktime) >= ? and DATE(a.picktime) <= ?");
			mergeSql.append(sql5);
			paraList.add(begindate);
			paraList.add(enddate);
			paraList.add(begindate);
			paraList.add(enddate);
			paraList.add(begindate);
			paraList.add(enddate);
			return Db.paginate(pageNumber, pageSize, sql1, mergeSql.toString(),paraList.toArray());
		}else{
			mergeSql.append(sql2).append(sql3).append(sql4).append(sql5);
			return Db.paginate(pageNumber, pageSize, sql1, mergeSql.toString(),paraList.toArray());
		}
   }

	public Page<Record> listByRule(Integer pageNumber, int pageSize) {
		String rule = this.get("rule");
		List<Object> paraList = new ArrayList<>();
		String sql1 = "select aa.ruleid,aa.rule,aa.total_num,bb.feedback_num,tj_num,gj_num,wjt_num,wyx_num,yxa_num,yxb_num,yxc_num,kh_num,qt_num ";
		String sql2 = " from ( select a.ruleid,a.rule,count(a.id) total_num from cy_business.a_url_bdata a where a.importtime"
				+ " >= concat(date_sub(curdate(),interval 1 day),' 00:00:00') and a.importtime <= concat("
				+ "date_sub(curdate(),interval 1 day),' 23:59:59') group by a.ruleid) aa left join ( SELECT"
				+ " a.ruleId,COUNT(a.fb_data_id) feedback_num, SUM(IF(result_type = 1,1,0)) AS tj_num, SUM(IF(result_type = 2,1,0))"
				+ " AS gj_num, SUM(IF(result_type = 3,1,0)) AS wjt_num, SUM(IF(result_type = 4,1,0)) AS wyx_num,"
				+ " SUM(IF(result_type = 5,1,0)) AS yxa_num, SUM(IF(result_type = 6,1,0)) AS yxb_num, SUM(IF(result_type = 7,1,0))"
				+ " AS yxc_num, SUM(IF(result_type = 8,1,0)) AS kh_num, SUM(IF(result_type = 9,1,0)) AS qt_num FROM cy_business.a_feedback_data"
				+ " a where a.calltime >= concat(date_sub(curdate(),interval 1 day),' 00:00:00') and concat(date_sub(curdate(),"
				+ "interval 1 day),' 23:59:59') GROUP BY a.ruleId ORDER BY a.ruleId asc) bb on aa.ruleid = bb.ruleId";
		if(StrKit.notBlank(rule)){
			sql2 += " where aa.rule = ?";
			paraList.add(rule);
		}
		return Db.paginate(pageNumber, pageSize, sql1, sql2,paraList.toArray());
	}
}
