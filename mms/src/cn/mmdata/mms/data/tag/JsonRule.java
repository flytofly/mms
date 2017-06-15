package cn.mmdata.mms.data.tag;

import java.util.List;

public class JsonRule {
	private String  page="1";
	private String total="2";
	private  String records="13";
	private List<JsonRuleInfo> rows;
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	
	public List<JsonRuleInfo> getRows() {
		return rows;
	}
	public void setRows(List<JsonRuleInfo> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "JsonRule [page:" + page + ", total:" + total + ", records:" + records + ", rows:" + rows + "]";
	}
}
