package cn.mmdata.mms.statis.rulestatis;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import cn.mmdata.commons.util.Global;

public class RuleStatisController extends Controller {

	public void list() {
		RuleStatis record = new RuleStatis();
		String begindate = getPara("begindate");
		String enddate = getPara("enddate");
		String statistype = getPara("statistype", "0");
		String rule = getPara("rule");
		record.set("rule", rule);
		record.set("begindate", begindate);
		record.set("enddate", enddate);
		record.set("statistype", statistype);

		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		
		setAttr("record", record);
		Integer pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");
		// 按照日期进行统计
		if ("0".equals(statistype)) {
			if (StrKit.notBlank(submit)) {
				setAttr("records", record.list(1, 10));
			} else {
				setAttr("records", record.list(pageNumber, 10));
			}
			render("/WEB-INF/jsp/statis/list.html");
			return;
		} else if ("1".equals(statistype)) {
			if (StrKit.notBlank(submit)) {
				setAttr("records", record.listByRule(1, 10));
			} else {
				setAttr("records", record.listByRule(pageNumber, 10));
			}
			render("/WEB-INF/jsp/statis/rulelist.html");
		}
	}
}
