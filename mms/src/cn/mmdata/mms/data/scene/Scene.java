package cn.mmdata.mms.data.scene;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;

public class Scene extends Model<Scene> {
	private static final long serialVersionUID = 1L;
	public static Scene dao = new Scene();

	private String whereSql(String sql, List<Object> list) {
		if (this.get("stype") != null) {
			sql += " and a.stype=?";
			list.add(this.get("stype"));
		}
		if (!StringUtil.isNull(this.getStr("sname"))) {
			sql += " and a.sname like ?";
			list.add("%"+this.get("sname")+"%");
		}
		if (this.get("status") != null) {
			sql += " and a.status=?";
			list.add(this.get("status"));
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
		List<Object> list=new ArrayList<Object>();
		String sql = " from scene a left join cy_business.cy_customer b  on a.reg_id=b.customer_id where 1=1";
		if (!StringUtil.isNull(this.getStr("customer_name"))) {
			sql += " and b.customer_name like ?";
			list.add("%"+ this.getStr("customer_name").trim() +"%");
		}
		sql = whereSql(sql, list);
		sql += " order by a.reg_time desc";
		return Db.paginate(pageNumber, pageSize, "select a.sid, a.sname, a.sdesc, a.stype, a.condition, a.reg_id, DATE_FORMAT(a.lastupdatedate,'%Y-%m-%d %H:%i:%s') lastupdatedate, a.status,DATE_FORMAT(a.reg_time,'%Y-%m-%d %H:%i:%s')  reg_time,b.customer_name", sql,list.toArray());
	}





}
