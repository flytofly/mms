package cn.mmdata.mms.member.ent;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class BusinessProject extends Model<BusinessProject> {

	private static final long serialVersionUID = 1L;
	public static BusinessProject dao = new BusinessProject();

	public BusinessProject findProjectByBidAndType(int bid, int project_type) {
		String sql = "select * from cy_business_project  where project_type =?  and bid = ?";
		return BusinessProject.dao.findFirst(sql, project_type, bid);
	}

	public Page<BusinessProject> list(int pageNumber, int pageSize) {
		List<Object> list = new ArrayList<Object>();
		String sql = "from cy_business_project where 1=1  and status!=9 and  project_type in (3,4,6) ";
		if (get("business_name") != null && !"".equals(get("business_name"))) {
			sql += " and  business_name like ? ";
			list.add("%" + getStr("business_name").trim() + "%");
		}
		if (get("project_type") != null && !"".equals(get("project_type"))) {
			sql += " and  project_type=? ";
			list.add(get("project_type"));
		}
		return this.paginate(pageNumber, pageSize, "select *", sql, list.toArray());

	}

	public void updatebybid(int project_id) {

		List<Object> list = new ArrayList<Object>();
		String sql = "update cy_business_project ";
		if (get("canpick_number") != null && !"".equals(get("canpick_number"))) {
			sql += "set canpick_number=IFNULL(canpick_number,0)+?";
			list.add(get("canpick_number"));
		}
		if (get("recharge") != null && !"".equals(get("recharge"))) {
			sql += ", recharge=IFNULL(recharge,0)+?";
			list.add(get("recharge"));
		}
		sql += " where project_id=?";
		list.add(project_id);
		Db.update(sql, list.toArray());

	}

	public Page<Record> list2(int pageNumber, int pageSize) {
		String bids = "";
		if (this.get("agentId") != null) {
			List<Record> bidList = Db.find("SELECT bid FROM cy_business_agentrel WHERE agent_id = ?",
					this.get("agentId"));
			for (Record r : bidList) {
				bids += r.getInt("bid") + ",";
			}
			bids = bids.length() > 1 ? bids.substring(0, bids.length() - 1) : bids;
		}
		String sql = "  " + "from ( select * from cy_business_project  where project_type=6 ";
		if (this.get("agentId") != null) {
			sql += " AND bid IN (" + bids + ")";
		}
		if (this.get("business_name") != null) {
			sql += " AND business_name like '%" + this.get("business_name") + "%'";
		}
		sql += " group by bid)a left join (";
		sql +=  "	SELECT COUNT(kw.kid) rulecount,bid "
				+ "	FROM cy_business_project_detail d,cy_business_project_sskw kw"
				+ "	WHERE 1=1 and d.detail_id = kw.detail_id and kw.level4type = 2 and kw.status =1 " 
				+ "	GROUP BY bid ";
		sql += ") b on a.bid = b.bid  ";
		sql += "left join (select sum(picknumber) picknum,bid from v_bid_pick_day "
				+ " where  pickday >= '" + this.getStr("picktimestartdate") + "'"
					  + " and pickday <= '"+ this.getStr("picktimestartdate") + "' group by bid"
				+ ") c  on a.bid = c.bid ";
		sql +=  "left join ("
				+ " select bid,ifnull(sum(day_num),0) day_num  from ("
				+ "		SELECT distinct bid,kid "
				+ "		FROM cy_business_project_detail d,cy_business_project_sskw kw "
				+ "		WHERE 1=1 and d.detail_id = kw.detail_id and kw.level4type = 2 and kw.status =1 "
				+ " ) a  "
				+ " left join ( "
				+ "		select ruleid,sum(uv) day_num  from  a_rulesource_count b  "
				+ "		where 1=1 	and  b.countdate >= '" + this.getStr("picktimestartdate") + "'"
				+ " 	and b.countdate <= '"+ this.getStr("picktimestartdate") + "' group by ruleid"
				+ ") b2 on a.kid = b2.ruleid    group by a.bid ) d on a.bid = d.bid  ";
		return Db.paginate(pageNumber, pageSize,
				"select  a.bid,a.business_name,a.status,b.rulecount,ifnull(c.picknum,0) picknum,d.day_num,(ifnull(day_num,0)-ifnull(picknum,0) )  surplusnum   ",
				sql);

	}

	public BusinessProject selectBusinessProject() {
		String sql = " FROM cy_business_project WHERE 1=1";
		List<Object> list = new ArrayList<Object>();
		if(this.get("bid") != null && !"".equals(this.get("bid"))){
			sql += " and  bid=? ";
			list.add(get("bid"));
		}
		if (this.get("project_type") != null && !"".equals(this.get("project_type"))) {
			sql += " and  project_type=? ";
			list.add(get("project_type"));
		}
		return dao.findFirst(sql);
	}

	public String repeatVali() {
		String projectId = this.get("project_id");
		String sql = "";
		if(StrKit.notBlank(projectId)){
			sql = "select count(*) count from cy_business_project a where a.bid = ? and a.project_type = ? and a.project_id != '"+ projectId +"'";
		}else{
			sql = "select count(*) count from cy_business_project a where a.bid = ? and a.project_type = ?";
		}
		return Db.findFirst(sql,this.get("bid"),this.get("project_type")).get("count").toString();
	}

	public void insertProject() {
		String sql = "insert into cy_business_project (project_type,bid,`status`) values(?,?,?);";
		Db.update(sql,this.get("project_type"),this.get("bid"),this.get("bid"),this.get("status"));
	}

	public void deleteProjectById() {
		String sql = "update cy_business_project a set a.`status` = 9 where a.project_id = ?";
		Db.update(sql,this.get("project_id"));
	}

	public Page<Record> renewlist(int pageNumber, int pageSize) {
		List<Object> list = new ArrayList<Object>();
		String sql = " FROM cy_business_project_recharge a LEFT JOIN cy_business_project b ON a.bid=b.bid"
				+ " AND a.project_type=b.project_type where 1=1";
		if (get("business_name") != null && !"".equals(get("business_name"))) {
			sql += " and  business_name like ? ";
			list.add("%" + getStr("business_name").trim() + "%");
		}
		if (get("project_type") != null && !"".equals(get("project_type"))) {
			sql += " and  b.project_type=? ";
			list.add(get("project_type"));
		}
		if (!StrKit.isBlank(this.getStr("searchstartdate"))) {
			sql += " AND reg_time >= ? ";
			list.add(this.getStr("searchstartdate") + " 00:00:00");
		}
		if (!StrKit.isBlank(this.getStr("searchenddate"))) {
			sql += " AND reg_time <= ? ";
			list.add(this.getStr("searchenddate") + " 23:59:59");
		}
		sql += " ORDER BY reg_time DESC";
		String select = "SELECT a.r_id, a.bid, a.project_id, a.project_type,IFNULL(a.recharge,0.0) recharge ,"
				+ " IFNULL(a.canpick_number,0) canpick_number, a.reg_id, a.reg_name, a.reg_time ,b.business_name"
				+ " ,b.project_name";
		return Db.paginate(pageNumber, pageSize, select, sql, list.toArray());
	}

	public Record count() {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT ifnull(SUM(a.recharge),0) rechargeTotal ,ifnull(SUM( a.canpick_number),0) canpickNumberTotal"
				+ " FROM cy_business_project_recharge a LEFT JOIN cy_business_project b ON a.bid=b.bid"
				+ " AND a.project_type=b.project_type where 1=1 ";
		if (get("business_name") != null && !"".equals(get("business_name"))) {
			sql += " and  b.business_name like ? ";
			list.add("%" + getStr("business_name").trim() + "%");
		}
		if (get("project_type") != null && !"".equals(get("project_type"))) {
			sql += " and  b.project_type=? ";
			list.add(get("project_type"));
		}
		if (!StrKit.isBlank(this.getStr("searchstartdate"))) {
			sql += " AND reg_time >= ? ";
			list.add(this.getStr("searchstartdate") + " 00:00:00");
		}
		if (!StrKit.isBlank(this.getStr("searchenddate"))) {
			sql += " AND reg_time <= ? ";
			list.add(this.getStr("searchenddate") + " 23:59:59");
		}
		return Db.findFirst(sql,list.toArray());
	}
}
