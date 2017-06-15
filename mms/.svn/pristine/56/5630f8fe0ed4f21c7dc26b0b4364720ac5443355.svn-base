package cn.mmdata.mms.analysis.basic.seq;

import java.util.ArrayList;

/**
* 域名对象，存取该域名前后的时间序列
*/
public class AppSeq
{
	private String _domain;
	private ArrayList<AppSeqPos> _seq_pos_units;

	public String getDomain() {
		return _domain;
	}

	public void setDomain(String _domain) {
		this._domain = _domain;
	}

	/**
	* 针对当前偏移位置，按用户量排名对域名做存储处理
	*/
	public void addOffsetDomain(int offset, String offsetDomain, int count)
	{
		// 获取偏移位置对象
		AppSeqPos asp = getSeqPosUnit(offset);
		if (asp == null) {
			asp = new AppSeqPos();
			asp.setPos(offset);
			asp.setHostDomain(getDomain());
			asp.addDomainUnit(offsetDomain, count);

			addSeqPosUnit(asp);
		} else {
			asp.addDomainUnit(offsetDomain, count);
		}
	}

	/**
	* 获取pos位置的域名序列对象
	*/
	public AppSeqPos getSeqPosUnit(int pos)
	{
		if (_seq_pos_units == null) {
			return null;
		}

		AppSeqPos[] aspArray = (AppSeqPos[]) _seq_pos_units.toArray(new AppSeqPos[0]);
		for (int i=0; i < aspArray.length; i++) {
			if (aspArray[i].getPos() == pos) {
				return aspArray[i];
			}
		}

		return null;
	}

	/**
	* 增加偏移位置对象
	*/
	private void addSeqPosUnit(AppSeqPos asp)
	{
		if (_seq_pos_units == null) {
			_seq_pos_units = new ArrayList<AppSeqPos>();
		}

		_seq_pos_units.add(asp);
	}

	/**
	* 获取AppSeq对象数组
	*/
	public AppSeqPos[] getSeqPosArray()
	{
		if (_seq_pos_units == null) {
			return null;
		} else {
			return (AppSeqPos[]) _seq_pos_units.toArray(new AppSeqPos[0]);
		}
	}
}