package cn.mmdata.mms.analysis.basic.matrix;

public class AppNode
{
	private String _domain;
	private int _total_count;
	private int _host_count;
	private int _cross_count;
	private String _relevency;

	public String getDomain() {
		return _domain;
	}

	public void setDomain(String _domain) {
		this._domain = _domain;
	}

	public int getTotalCount() {
		return _total_count;
	}

	public void setTotalCount(int _total_count) {
		this._total_count = _total_count;
	}

	public int getHostCount() {
		return _host_count;
	}

	public void setHostCount(int _host_count) {
		this._host_count = _host_count;
	}

	public int getCrossCount() {
		return _cross_count;
	}

	public void setCrossCount(int _cross_count) {
		this._cross_count = _cross_count;
	}

	public String getRelevency() {
		return _relevency;
	}

	public void setRelevency(String _relevency) {
		this._relevency = _relevency;
	}

	/**
	* 计算关联度
	*/
	public float getComputeRelevancy()
	{
		double a1 = (double) getCrossCount() / (double) getTotalCount();
		double a2 = (double) getCrossCount() / (double) getHostCount();
		return (float) Math.sqrt(a1 * a2);
	}

	public int getIntRelevancy()
	{
		float rele = getComputeRelevancy() * 10000;
		return (int)rele;
	}
}