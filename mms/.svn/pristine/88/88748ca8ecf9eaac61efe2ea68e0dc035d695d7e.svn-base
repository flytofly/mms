package cn.mmdata.mms.statis.statopti;

import org.junit.Test;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.common.JFinalModelCase;

public class StatOptiControllerTest extends JFinalModelCase {

	/*@Test
	public void testDetailList() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testDetailRuleList() {
		StatOpti record = new StatOpti();
		record.put("custype", 4);
		record.put("bid", 6652);
		record.put("out_type", 1);
		record.put("detail_id", 106614);
		Page<Record> list=record.detailRulePaginate(1, 10);
		System.out.println("list info:"+list.toString());
	}

}
