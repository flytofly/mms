package cn.mmdata.mms.data.tag.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;

public class QueryClient {

    static String queryTagsUrl = "http://dmp.mmdata.cn/api/tagquery/queryTags";
    static String queryRulesUrl = "http://dmp.mmdata.cn/api/tagquery/queryRules";
    
    public static QueryRulesResult queryRulesToResult(String tag) { 
		String ruleJson = queryRules(tag);
		return  JSON.parseObject(ruleJson,QueryRulesResult.class);
    }

    public static String queryRules(String tag) {
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("tag", tag);
		HttpKit.setCharSet("UTF-8");
		return HttpKit.post(queryRulesUrl,paraMap,"");
    }
    

    public static QueryTagResult queryTagsToResult(String tag) {
		String ruleJson = queryTags(tag);
		return  JSON.parseObject(ruleJson,QueryTagResult.class);
    }

    public static String queryTags(String tag) {
		 String tagStr = (StrKit.isBlank(tag) ? "留学移民" : tag);
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("tag", tagStr);
		return HttpKit.post(queryTagsUrl,paraMap,"");
    }
    
    public static void main(String[] args) {
		String tag = "";
		System.out.println(QueryClient.queryTags(tag));
		QueryTagResult tagResult = QueryClient.queryTagsToResult(tag);
		System.out.println(tagResult);
		
		System.out.println(QueryClient.queryRules(tag));
		QueryRulesResult rulesResult = QueryClient.queryRulesToResult(tag);
		List<QueryRulesRuleInfo> rules = rulesResult.getRules();
		for(QueryRulesRuleInfo rule : rules){
			System.out.println(rule);
		}
	}
    
}
