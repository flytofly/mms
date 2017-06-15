package cn.mmdata.mms.delivery.material.tst;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import cn.mmdata.mms.delivery.material.Material;

public class MaterialTest {

	@Test
	public void testList() {
		Material record =new Material();
		record.put("businessname", "IVR名单导出");
		record.put("custype", 1);
		record.put("bid", 10);
		Page<Record> list =record.list(1, 10,10);
		System.out.println("==="+list.toString());
		
	}

}
