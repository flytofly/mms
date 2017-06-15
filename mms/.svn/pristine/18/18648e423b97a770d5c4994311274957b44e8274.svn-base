package cn.mmdata.mms.data.rule;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class UrlHost extends Model<UrlHost> {

	private static final long serialVersionUID = 1L;

	public static UrlHost dao = new UrlHost();

	public UrlHost findByName(String host) {
		return this.use("dmp").findFirst("select * from urlhost where host = ?", host);
	}

	public boolean updateHost() {
		return Db.use("dmp").update("update urlhost set  level3Id = ?, hostName = ?,hostType = ? where hostId = ? ",
				this.get("level3Id"), this.get("hostName"), this.get("hostType"), this.get("hostId")) == 0 ? false : true;
	}
}
