package cn.mmdata.mms.member.ent;

import java.util.ArrayList;
import java.util.List;

import cn.mmdata.commons.util.StringUtil;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Business extends Model<Business> {
	private static final long serialVersionUID = 1L;
	public static Business dao = new Business();

	public Page<Business> paginate(int pageNumber, int pageSize) {

		String sql = " from cy_business where  status != 9  ";
		if (this.get("business_name") != null && !"".equals(this.getStr("business_name").trim())) {
			sql += " and business_name like '%" + this.getStr("business_name").trim() + "%'";
		}
		if (this.get("business_type") != null && !"".equals(this.get("business_type"))) {
			sql += " and business_type=" + this.get("business_type");
		}
		if (this.get("agentId") != null && !"".equals(this.get("agentId"))) {
			sql += " and bid in(select distinct bid from cy_business_agentrel where agent_id = " +  this.get("agentId") + ")";
		}
		if (this.get("bid") != null && !"".equals(this.get("bid"))) {
			sql += " and bid = " +  this.get("bid") ;
		}
		if (this.get("business_sale") != null) {
			if("-1".equals(this.get("business_sale").toString())){
				sql += " and business_sale in (2,3)";
			}else if(!"-1".equals(this.get("business_sale").toString())){
				sql += " and business_sale = '"+ this.get("business_sale") +"'";
			}
		}
		if (this.get("status") != null && !"".equals(this.get("status"))) {
			sql += " and status=" + this.get("status");
		}
		sql += " order by update_time desc";
		return this.paginate(pageNumber, pageSize, "select *", sql);
	}

	public List<Business> queryByNameAndType(String business_name,
			String business_type) {
		ArrayList<String> paraList = new ArrayList<String>();
		String sql = "SELECT bid id ,business_name text FROM business where 1 = 1  ";
		if (!StringUtil.isNull(business_type)) {
			sql += " and  business_type = ?";
			paraList.add(business_type);
		}
		if (!StringUtil.isNull(business_name)) {
			sql += " and  business_name  LIKE ? ";
			paraList.add("%" + business_name + "%");
		}
		return this.find(sql, paraList.toArray());
	}

	public List<Business> queryCustomerByName(String business_name) {
		String sql = "SELECT bid id ,business_name text FROM business where business_type <>3 and  business_name  LIKE '%"
				+ business_name + "%'";
		return this.find(sql);
	}

	public Page<Business> AgentPaginate(int pageNumber, int pageSize,Business business,String submit) {
		String sql = " from cy_business where business_type=3 AND status != 9 ";
		if(!StrKit.isBlank(business.getStr("business_name"))){
			sql += " AND business_name LIKE '%"+ business.getStr("business_name") +"%'";
		}
		if(business.getInt("status")!= null){
			sql += " AND status = '"+ String.valueOf(business.get("status")) +"'";
		}
		if(!StrKit.isBlank(submit)){
			 pageNumber = 1;
		}
		return this.paginate(pageNumber, pageSize, "select *", sql);
	}

	/**
	 * 根据代理商id查询所有客户
	 * 
	 * @param agentId
	 * @return
	 */
	public List<Business> queryCustomerByAgentId(String agentId) {
		String sql = "select distinct business.bid id ,business_name text from  business,business_agentrel where business.bid = business_agentrel.bid and agent_id = ?";
		return this.find(sql, agentId);
	}

	public int deleteBusinessAgentrelByAgentId(String agentId) {
		return Db.update("delete from cy_business_agentrel where agent_id = ?",agentId);
	}

	public int[] insertBusinessAgentrel(String agentId, String[] bids) {
		List<String> sqlList = new ArrayList<String>();
		  String sql = "";
		  for(String bid : bids){
			  sql = "insert into cy_business_agentrel values(null,'"+ agentId +"','"+ bid +"')";
			  sqlList.add(sql);
		  }
		return Db.batch(sqlList, sqlList.size());
	}

	/*
	 * public List<Record> getAllFields() { return Db .find(
	 * "select fieldno,fieldname,fielddesc,price,price1,price2,price3 from b_field"
	 * ); }
	 */
	public List<Record> getAllFields() {
		return Db
				.find("select b_field.bmid,bmName,fieldno,fielddesc,price,price1,price2,price3 from b_module,b_field where b_module.bmid = b_field.bmid and status = '1' order by bmid");
	}

	public List<Record> getBusinessFieldsByBid(String bid) {
		return Db.find("select * from b_bus_field_price where bid = ?", bid);
	}

	public List<Business> findByAgentId(String agentId) {
		String sql = "select * from cy_business where bid IN(SELECT bid FROM (SELECT bid FROM cy_business_agentrel WHERE agent_id = ?) as tbt)";
		return dao.find(sql, agentId);
	}

	public void deleteBusById(String bid) {
		String sql = "UPDATE cy_business SET status = '9' WHERE bid = ?";
		Db.update(sql, bid);
	}

	public Record findByBid(Integer bid) {
		String sql = "SELECT a.*,d.agent_id sale_bid,d.business_name sale_business_name FROM cy_business a left join"
				+ " (select b.bid,c.bid agent_id,c.business_name from cy_business_agentrel b inner JOIN cy_business"
				+ " c ON b.agent_id = c.bid and c.`status` = 1 and c.business_sale IN (2,3)) d on"
				+ " a.bid = d.bid where a.bid = ?";
		return Db.findFirst(sql,bid);
	}

	public void deleteBusinessAgentrelByBid(String bid) {
		Db.update("delete from cy_business_agentrel where bid = ?",bid);
	}
}
