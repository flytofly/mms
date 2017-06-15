package cn.mmdata.mms.data.tag.client;

import java.util.List;

public class QueryRulesResult {
	private String result;
	private List<QueryRulesRuleInfo> rules;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<QueryRulesRuleInfo> getRules() {
		return rules;
	}
	public void setRules(List<QueryRulesRuleInfo> rules) {
		this.rules = rules;
	}
	
	
}
