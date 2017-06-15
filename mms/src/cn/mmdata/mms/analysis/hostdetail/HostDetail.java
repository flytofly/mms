package cn.mmdata.mms.analysis.hostdetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class HostDetail extends Model<HostDetail>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5573374761214167637L;

	public static String getHourData(Record record){
		Map<Integer,String> map = new HashMap<Integer, String>();
		String resu = "";
		resu +="[";
		String[] hourArr = record.getStr("a").split(",");
		String[] uvArr = record.getStr("b").split(",");
		for(int i=0;i<hourArr.length;i++){
			int map_key = Integer.parseInt(hourArr[i]);
			String map_val = uvArr[i];
			map.put(map_key, map_val);
		}
		List<Entry<Integer,String>> list = new ArrayList<Entry<Integer,String>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
		    public int compare(Map.Entry<Integer, String> o1,
		            Map.Entry<Integer, String> o2) {
		        return (o1.getKey() - o2.getKey());
		    }
		});
		
		for(int i=0;i<list.size();i++){
			resu +="['"+list.get(i).getKey()+"',"+list.get(i).getValue()+"],";
		}
		System.out.println(resu);
		resu += "]";
		return resu;
	}
	
	
	
	public List<Record> searchSonDomain(String domainhost){
		String sql ="select rule,daily_uv,CONCAT(round((retention/daily_uv)*100,1),'%') as day_ratio,round(pv/mth_uv,0) as uv_req,per_duration from stat_rule_data where rule like '%."+domainhost+"' order by daily_uv desc";
		List<Record> find = Db.find(sql);
		return find;
	}
	
	
	
	public List<Record> searchHour(String domainhost) {
		String sql = "select 'site' as cla,a,b,ruleid from(select CONCAT_WS(',',rank,round((daily_uv/10000),1)) as a,CONCAT_WS(',',CONCAT(round((retention/mth_act_users)*100,1),'%'),round((per_duration/60),0)) as b,ruleid from stat_rule_data where rule = '"+domainhost+"' and uptime = (select max(uptime) from stat_rule_data  where rule = '"+domainhost+"' group by rule) order by pv desc limit 1) as  aa "
				+" union "
				+" select 'hour' as cla,GROUP_CONCAT(hour) as hour_,GROUP_CONCAT(uv) as uv_,ruleid from stat_rule_users_h where rule = '"+domainhost+"' and statdate = (select max(statdate) from stat_rule_users_h where rule  = '"+domainhost+"' group by rule) "
				+" union "
				+" select 'week' as cla,GROUP_CONCAT(days) as days_,GROUP_CONCAT(uv) as uv_,ruleid from stat_rule_users_w where rule = '"+domainhost+"' and statdate = (select max(statdate) from stat_rule_users_w where rule  = '"+domainhost+"' group by rule)";
		List<Record> find = Db.find(sql);
		return find;
	}
	
	
	public List<Record> searchTopHost(String domainhost,int weight) {
		String sql = "select * from hostdetail where domainhost = '"+domainhost+"' and weight = "+weight+" and SUBSTRING_INDEX(relemainhost,'.',1) not  in (select SUBSTRING_INDEX(filterhostname,'.',1)  as namehost from filterhost) order by count desc limit 9;";
		List<Record> find = Db.find(sql);
		return find;
		
	}
	
	public List<Record> searchSecHost(String sechosts,int weight){
		String sql = "select * from hostdetail where domainhost in ("+sechosts+") and weight = "+weight+" and SUBSTRING_INDEX(relemainhost,'.',1) not in (select SUBSTRING_INDEX(filterhostname,'.',1)  as namehost from filterhost) ;";
		List<Record> find = Db.find(sql);
		return find;
	}
	
	public static String gethosts(List<Record> searchTopHost){
		String hosts = "";
		Map<String,String> mapTop = new HashMap<String,String>();
		for(Record record : searchTopHost){
			hosts += "'"+record.getStr("relemainhost")+"',";
			mapTop.put(record.getStr("relemainhost"), record.getInt("count")+"");
		}
		if(!hosts.equals("") && hosts.endsWith(",")){
			hosts = hosts.substring(0,hosts.length()-1);
		}
		return hosts;
	}
	
	public static  Map<String,String> topDoMain(List<Record> searchSecHost){
		Map<String,String> map = new HashMap<String,String>();
		for(Record record : searchSecHost){
			String key = record.getStr("domainhost");
			String value = record.getStr("relemainhost")+"|"+record.getInt("count");
			if(map.get(key) == null){
				map.put(key, value);
			}else{
				String string = map.get(key);
				String vals = string+","+value;
				map.put(key, vals);
			}	
		}
		return map;
	}
	
	public static Map<String,String> map_host(List<Record> searchTopHost){
		Map<String,String> mapTop = new HashMap<String,String>();
		for(Record record : searchTopHost){
			mapTop.put(record.getStr("relemainhost"), record.getInt("count")+"");
		}
		return mapTop;
	}
	
	public static String getTopDomain(Map<String,String> mapTop,Map<String,String> map,int secDomainRank){
		String resu = "";
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String vals = map.get(key);
			String countTop = mapTop.get(key);
			String numsDomain = HostDetail.getNumsDomain(vals,secDomainRank);
			resu += key+"|"+countTop+"#"+numsDomain+";";
		}
		if(resu.endsWith(",")){
			resu = resu.substring(0, resu.length()-1);
		}
		return resu;
	}
	public static  String getNumsDomain(String values,int secDomainRank){
		String resu = "";
		if(!values.isEmpty()){
			String[] valArrs = values.split(",");
			for(int i=0;i<secDomainRank && secDomainRank < valArrs.length;i++){
				resu += valArrs[i]+",";
			}
		}
		if(resu.endsWith(",")){
			resu = resu.substring(0, resu.length()-1);
		}
		return resu;
	}
	
	/*
	 * 流入和流出数据格式 的node  统一格式为
 	 * 	[{category:0,name:'ctrip.com(入口)',value:1000},
	 */
	public static String output_node(String topdomain,String enterhost,int pos){
		String _gate = "";
		String _name = "";
		if(pos == -1){
			_gate = "入口";
			_name = "流入";
		}else if(pos == 1){
			_gate = "出口";
			_name = "流出";
		}
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("[{category:0,name:'"+enterhost+"("+_gate+")',value:1000},").append("\n");
		if(!topdomain.isEmpty()){
			for(String everyhost:topdomain.split(";")){
				String top_ = everyhost.split("#")[0];
				String domainhost = top_.split("\\|")[0];
				String docount = top_.split("\\|")[1];
				sbBuffer.append("{category:0,name:'"+domainhost+"("+_name+")',value:"+docount+"},").append("\n");
				String twos_ = everyhost.split("#")[1];
				for(String eachSechost:twos_.split(",")){
					String relemainhost = eachSechost.split("\\|")[0];
					String relecount = eachSechost.split("\\|")[1];
					sbBuffer.append("{category:1,name:'"+relemainhost+"("+_name+")',value:"+relecount+"},").append("\n");
				}
			}
		}
		sbBuffer.append("]");
		return sbBuffer.toString();
	}
	
	/*
	 * 流入和流出数据格式 的links  统一格式为
 	 * 	[{source:'wostore.cn(流入)',target:'ctrip.com(入口)',weight:461},
	 */
	public static String output_links(String topdomain,String enterhost,int pos){
		String _gate = "";
		String _name = "";
		if(pos == -1){
			_gate = "入口";
			_name = "流入";
		}else if(pos == 1){
			_gate = "出口";
			_name = "流出";
		}
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("[");
		if(!topdomain.isEmpty()){
			for(String everyhost:topdomain.split(";")){
				String top_ = everyhost.split("#")[0];
				String domainhost = top_.split("\\|")[0];
				String docount = top_.split("\\|")[1];
				if(_gate.equals("入口")){
					sbBuffer.append("{source:'"+domainhost+"("+_name+")',target:'"+enterhost+"("+_gate+")',weight:"+docount+"},").append("\n");
					String twos_ = everyhost.split("#")[1];
					for(String eachSechost:twos_.split(",")){
						String relemainhost = eachSechost.split("\\|")[0];
						sbBuffer.append("{source:'"+relemainhost+"("+_name+")',target:'"+domainhost+"("+_name+")',weight:"+docount+"},").append("\n");
					}
				}else if(_gate.equals("出口")){
					sbBuffer.append("{source:'"+enterhost+"("+_gate+")',target:'"+domainhost+"("+_name+")',weight:"+docount+"},").append("\n");
					String twos_ = everyhost.split("#")[1];
					for(String eachSechost:twos_.split(",")){
						String relemainhost = eachSechost.split("\\|")[0];
						String relecount = eachSechost.split("\\|")[1];
						sbBuffer.append("{source:'"+domainhost+"("+_name+")',target:'"+relemainhost+"("+_name+")',weight:"+relecount+"},").append("\n");
					}
				}
			}
		}
		sbBuffer.append("]");
		return sbBuffer.toString();
	}
	
	
}
