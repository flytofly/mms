package cn.mmdata.mms.analysis.hostdetail;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.mms.analysis.basic.seq.DataReader;

public class HostDetailController extends Controller {

	public void index() {
		
		HostDetail hostDetail = getModel(HostDetail.class);
		String domainhost = getPara("domain");
		int secDomainRank = 2;
		if(domainhost == null){
			domainhost = "ctrip.com";
		}
		
		
		//时段分布与周访频度
		String rank = "";
		String day_uv = "";
		String retention = "";
		String per_dura = "";
		
		String output_node_enter = "";
		String output_link_enter = "";
		String output_node_out = "";
		String output_link_out = "";
		String hourData = "";
		String weekData = "";
		List<Record> searchHour = hostDetail.searchHour(domainhost);
		if(searchHour != null && searchHour.size() > 0){
			for(int i=0;i<searchHour.size();i++){
				if(searchHour.get(i).getStr("cla").equals("site")){
					Record record = searchHour.get(i);
					if(record != null){
						rank =  record.getStr("a").split(",")[0];
						day_uv = record.getStr("a").split(",")[1];
						retention = record.getStr("b").split(",")[0];
						per_dura = record.getStr("b").split(",")[1];
					}
				}else if(searchHour.get(i).getStr("cla").equals("hour")){
					hourData = HostDetail.getHourData(searchHour.get(i));
				}else if(searchHour.get(i).getStr("cla").equals("week")){
					weekData = HostDetail.getHourData(searchHour.get(i));
				}
			}
			
			if(hourData != "" && weekData != ""){
				//时序来源与去向
				List<Record> searchTopHost_enter = hostDetail.searchTopHost(domainhost,-1);
				List<Record> searchTopHost_out = hostDetail.searchTopHost(domainhost,1);
				
				String hosts_enter = HostDetail.gethosts(searchTopHost_enter);
				String hosts_out = HostDetail.gethosts(searchTopHost_out);
				
				List<Record> searchSecHost_enter = hostDetail.searchSecHost(hosts_enter,-1);
				List<Record> searchSecHost_out = hostDetail.searchSecHost(hosts_out,1);
				Map<String, String> topDoMain_enter = HostDetail.topDoMain(searchSecHost_enter);
				Map<String, String> topDoMain_out = HostDetail.topDoMain(searchSecHost_out);
				
				Map<String, String> map_host_enter = HostDetail.map_host(searchTopHost_enter);
				Map<String, String> map_host_out = HostDetail.map_host(searchTopHost_out);

				String topDomain_enter = HostDetail.getTopDomain(map_host_enter,topDoMain_enter,secDomainRank);
				String topDomain_out = HostDetail.getTopDomain(map_host_out,topDoMain_out,secDomainRank);

				 output_node_enter = HostDetail.output_node(topDomain_enter,domainhost,-1);
				 output_link_enter = HostDetail.output_links(topDomain_enter,domainhost,-1);
				
				 output_node_out = HostDetail.output_node(topDomain_out,domainhost,1);
				 output_link_out = HostDetail.output_links(topDomain_out,domainhost,1);
			}
		}else{
			setAttr("pageVal","none");
		}
		
		String sonStr = "";
		//子域名
		List<Record> searchSonDomain = hostDetail.searchSonDomain(domainhost);
		if(searchSonDomain != null && searchSonDomain.size() > 0){
			for(int i=0;i<searchSonDomain.size();i++){
				searchSonDomain.get(i).set("id", i+1);
			}
			sonStr = "some";
		}else{
			sonStr = "none";
		}
		setAttr("nodes_enter", output_node_enter);
		setAttr("links_enter", output_link_enter);
		setAttr("nodes_out", output_node_out);
		setAttr("links_out", output_link_out);
		setAttr("rank",rank);
		setAttr("day_uv",day_uv);
		setAttr("retention",retention);
		setAttr("per_dura",per_dura);
		setAttr("hourData", hourData);
		setAttr("weekData", weekData);
		setAttr("keyword", domainhost);
		setAttr("records", searchSonDomain);
		setAttr("xuhao",0);
		setAttr("sonStr",sonStr);
		render("/WEB-INF/jsp/analysis/hostdetail/list.html");
		
	}
   
	
	public void data(PrintWriter out) {
		
		String domain = "ctrip.com";
		DataReader dr = new DataReader();
		Map<String, String> map = dr.returnVal(domain);
		JSONObject fromObject = JSONObject.fromObject(map);
		renderJson(fromObject.toString());
	}
	
	
	public void test() {
		Map<String, String> returnVal  = new HashMap<String, String>();
		returnVal.put("demo", "1233123131321312");
		renderJson(returnVal.toString());
	}
	

	
}
