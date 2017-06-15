package cn.mmdata.mms.delivery.task;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class DetailOutput extends Model<DetailOutput>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static DetailOutput dao = new DetailOutput();
	public void deleteByDetailID(int detailid){
		String sql ="delete from cy_business_project_detail_output where detail_id= ?";
		Db.use("zhijian").update(sql,detailid);
	}
}
