package cn.mmdata.mms.system.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class LoginLog extends Model<LoginLog> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static LoginLog dao = new LoginLog();
	
	public LoginLog selectLast(LoginLog loginLog){
		String sql ="select id,username,if(ip='0:0:0:0:0:0:0:1','暂无',ip) ip,`status`,date_format(logintime, '%Y-%m-%d') logintime,lockstatus from cy_login_log a where a.username = ? order by a.logintime desc limit 1";
		return this.findFirst(sql,loginLog.get("username"));
	}
	public int checkFailTimes1(LoginLog loginLog){
		String sql="select count(*) num from (select * from cy_login_log a where a.username = ? and a.logintime > (select IFNULL(max(b.logintime),'1997-01-01 00:00:00') from cy_login_log b where b.username =  ? and b.status = 1) and a.logintime > (select IFNULL(max(b.logintime),'1997-01-01 00:00:00') from cy_login_log b where b.username = ? and b.lockstatus = 1) order by logintime desc limit 5) c";
		Record rc =Db.findFirst(sql,loginLog.get("username"),loginLog.get("username"),loginLog.get("username"));
		int num =0;
		if(rc!=null){
			num = Integer.parseInt(rc.get("num").toString());
		}
		return num;
	}
	public int checkFailTimes2(LoginLog loginLog){
		String sql=" select count(*) num from cy_login_log a where a.logintime > now()-interval 5 minute and a.`status` = 0 and a.ip = ?";
		Record rc =Db.findFirst(sql,loginLog.get("ip"));
		int num =0;
		if(rc!=null){
			num =Integer.parseInt(rc.get("num").toString());
		}
		return num;
	}
}
