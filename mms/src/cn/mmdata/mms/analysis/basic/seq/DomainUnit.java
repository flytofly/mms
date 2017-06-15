package cn.mmdata.mms.analysis.basic.seq;

import java.util.ArrayList;

/**
* 域名对象
*/
public class DomainUnit
{
	private String _domain;
	private int _count;

	public String getDomain() {
		return _domain;
	}

	public void setDomain(String _domain) {
		this._domain = _domain;
	}

	public int getCount() {
		return _count;
	}

	public void setCount(int _count) {
		this._count = _count;
	}

	/**
	* 显示的域名
	*/
	public String getDispDomain(int offset)
	{
		if (offset < 0) {
			return getDomain() + "(流入)";
		} else if (offset > 0) {
			return getDomain() + "(流出)";
		} else {
			return getDomain();
		}
	}

	/**
	* 返回指定页面的查询结果
	*/
	public static DomainUnit[] getPageUnits(DomainUnit[] duArray, int pageNo, int pageSize)
	{
		int maxPage = (duArray.length - 1) / pageSize + 1;

		if (pageNo < 1) { pageNo = 1; }
		if (pageNo > maxPage) { pageNo = maxPage; }

		// 获取子集
		int start = (pageNo - 1) * pageSize;
		int end = pageNo * pageSize;

		ArrayList<DomainUnit> results = new ArrayList<DomainUnit>();
		for (int i = start; i < duArray.length && i < end; i++) {
			results.add(duArray[i]);
		}

		return (DomainUnit[]) results.toArray(new DomainUnit[0]);
	}

	/**
	* 如果domainList有一组域名，只返回第一个域名数据
	*/
	public static String cleanDomain(String domainList)
	{
		int pos = domainList.indexOf(",");
		if (pos >= 0) {
			return domainList.substring(0, pos);
		} else {
			return domainList;
		}
	}
}