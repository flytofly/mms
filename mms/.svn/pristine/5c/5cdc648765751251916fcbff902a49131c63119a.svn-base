package cn.mmdata.mms.data.crowd.test;

import org.junit.Test;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.common.JFinalModelCase;
import cn.mmdata.mms.data.crowd.Crowd;

public class CrowdTest extends JFinalModelCase {

	@Test
	public void testPaginateIntInt() {
		Crowd record = new Crowd();
		record.put("businessname", "杭州品缘科技 ");
		record.put("custype", 1);
		record.put("bid", 10);
		Page<Record> list =record.paginate(1, 10);
		System.out.println("==="+list.toString());
	}

}
