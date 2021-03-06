package cn.mmdata.mms.data.crowd;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class CrowdArea extends Model<CrowdArea> {
	private static final long serialVersionUID = 1L;
	public static CrowdArea dao = new CrowdArea();
	
	public void deleteAreasByCid(int cid) {
		String sql="delete from crowd_area where cid=?";
		Db.update(sql,cid);
	}
	public List<Record> findCheckArea(String cid) {
		String sql="select * from crowd_area where cid=? ";
		return Db.find(sql, cid);
	}
	
	public List<Record> findArea() {
		return Db.find("select provinceID id,province text,0 pid from dmp.cn_province union all"
				+ " select cityID id,city text,father pid from dmp.cn_city");
	}
	public List<Record> findArea(String cityId) {
		return Db.find("      select a.provinceID,a.province,a.cityID,a.city from dmp.v_area a  where a.cityID=?",cityId);
	}
	public List<Record> findAreas() {
		return Db.find("select provinceID id,province text,0 pid from dmp.cn_province union all"
				+ " select cityID id,city text,father pid from dmp.cn_city");
	}
	
	public List<Record> findAreas(String cids) {
		String sql="select a.cid,a.province_id,a.province,a.city_id,a.city from crowd_area a where cid in (?) "
				+ "group by a.cid,a.province_id,a.province,a.city_id,a.city";
		return Db.find(sql, cids);
	}
}
