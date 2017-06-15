package cn.mmdata.mms.data.crowd;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class CrowdScene extends Model<CrowdScene> {
	private static final long serialVersionUID = 1L;
	public static CrowdScene dao = new CrowdScene();





//	public List<Record> getCrowdScenes(int cid) {
//		String sql="select * from crowd_scene where cid=?";
//		return Db.find(sql,cid);
//	}
	public void deleteByCid(int cid) {
		String sql="delete from crowd_scene where cid=?";
		Db.update(sql,cid);
	}





	public List<Record> findScenesByCid(String cid) {
		String sql="select * from crowd_scene a left join scene b on a.sid=b.sid where a.cid=? ";
		return Db.find(sql, cid);
	}
}
