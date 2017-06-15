package cn.mmdata.mms.data.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.mmdata.commons.util.DateTools;
import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.LocalURLUtil;
import cn.mmdata.commons.util.ReadExcelEls;
import cn.mmdata.commons.util.RuleUtil;

public class UrlRuleController extends Controller {

	public void pre(Level4 record) {
		if (StrKit.notBlank(getPara("level3Name"))) {
			record.put("level3Name", getPara("level3Name"));
		}
		if (StrKit.notBlank(getPara("host"))) {
			record.put("host", getPara("host"));
		}
		if (StrKit.notBlank(getPara("rule"))) {
			record.put("rule", getPara("rule"));
		}
		if (StrKit.notBlank(getPara("cname"))) {
			record.put("cname", getPara("cname"));
		}
	}

	public void list() {
		int pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");
		Level4 record = getModel(Level4.class, "record");
		pre(record);
		if (StrKit.notBlank(submit)) {
			setAttr("records", record.paginate(1, 10));
		} else {
			setAttr("records", record.paginate(pageNumber, 10));
		}
		setAttr("record", record);
		render("/WEB-INF/jsp/data/rule/list.html");
	}

	public void batchtoCheck() {
		String cname = Global.getInstance(this).getSessionUserName();
		int i = 0;
		String ids = getPara("level4Ids");
		if (ids != null && ids.length() > 0) {
			i = Level4.dao.batchUpdateStatus(1, cname, ids);
		}
		renderJson(i);
	}

	public void batchtoUnused() {
		String cname = Global.getInstance(this).getSessionUserName();
		int i = 0;
		String ids = getPara("level4Ids");
		if (ids != null && ids.length() > 0) {
			i = Level4.dao.batchUpdateStatus(2, cname, ids);
		}
		renderJson(i);
	}

	public void detail() {
		Integer level4Id = getParaToInt();
		Level4 record = new Level4();
		record.set("level4Id", level4Id);
		setAttr("record", record.findVLevel4());
		render("/WEB-INF/jsp/domain/urlrule/detail.jsp");
	}

	public void toCheck() {
		Integer level4Id = getParaToInt();
		Level4 record = new Level4();
		record.set("level4Id", level4Id);
		setAttr("record", record.findVLevel4());
		render("/WEB-INF/jsp/domain/urlrule/check.jsp");
	}

	public void check() {
		String cname = Global.getInstance(this).getSessionUserName();
		Level4 record = getModel(Level4.class, "record");
		record.set("cdate", DateTools.getDateTime(0));
		record.set("cname", cname);
		UrlRule urlrule = getModel(UrlRule.class, "urlrule");
		urlrule.update();
		record.update();
		redirect("/domain/urlrule/list");
	}

	public void edit() {
		render("/WEB-INF/jsp/domain/urlrule/edit.jsp");
	}

	/**
	 * 停用
	 */
	public void rlist() {
		int pageNumber = 1;
		if ((!"".equals(getPara("pageNumber"))) && getPara("pageNumber") != null) {
			pageNumber = Integer.valueOf(getPara("pageNumber"));
			if (pageNumber < 1) {
				pageNumber = 1;
			}
		}
		Level4 record = getModel(Level4.class, "record");
		String rule = getPara("rule");
		String desc = getPara("desc");
		if (StrKit.notBlank(rule)) {
			record.put("rule", rule);
		}
		if (StrKit.notBlank(desc)) {
			record.put("desc", desc);
		}
		record.put("level4Type", 2);
		record.put("status", 1);
		Page<Record> records = record.paginate(pageNumber, 10);
		if (StrKit.notBlank(getPara("level1Id"))) {
			record.put("level1Id", getPara("level1Id"));
		}
		if (StrKit.notBlank(getPara("level2Id"))) {
			record.put("level2Id", getPara("level2Id"));
		}
		List<Record> list = records.getList();
		if (list.size() > 0) {
			record.put("level1Id", list.get(0).get("level1Id"));
			record.put("level2Id", list.get(0).get("level2Id"));
		}
		setAttr("record", record);
		setAttr("records", records);
		render("/WEB-INF/jsp/domain/urlrule/rlist.jsp");
	}

	/**
	 * 停用
	 */
	public void delrule() {
		if (StrKit.notBlank(getPara("level4Id"))) {
			new Level4().deleteById(getParaToInt("level4Id"));
		}
		if (StrKit.notBlank(getPara("ruleId"))) {
			new UrlRule().deleteById(getParaToInt("ruleId"));
		}
		rlist();
	}

	public void showLevel() {
		int pageNumber = 1;
		if ((!"".equals(getPara("pageNumber"))) && getPara("pageNumber") != null) {
			pageNumber = Integer.valueOf(getPara("pageNumber"));
			if (pageNumber < 1) {
				pageNumber = 1;
			}
		}
		String keywords = getPara("keywords", "");
		String submit = getPara("Submit");
		if (StrKit.notBlank(submit))
			pageNumber = 1;
		Page<Record> records = UrlRule.dao.findLevel3ByL1L2L3Name(pageNumber, 10, keywords);
		setAttr("keywords", keywords);
		setAttr("records", records);
		render("/WEB-INF/jsp/domain/urlrule/level.jsp");
	}

	public void update() {
		String[] shostTypeArr = getParaValues("hostTypes");
		String[] urlRuleArr = getParaValues("urlRules");
		String[] urlRuleDescArr = getParaValues("urlRuleDescs");
		String[] urlRuleRemarkArr = getParaValues("urlRuleRemarks");
		Integer level3Id = getParaToInt("level3Id");
		String level3Name = getPara("level3Name");
		Map<String, Record> ruleMap = new HashMap<String, Record>();
		if (urlRuleArr != null && urlRuleArr.length > 0) {
			for (int i = 0; i < urlRuleArr.length; i++) {
				if (StrKit.notBlank(urlRuleArr[i])) {
					String rule = RuleUtil.formatRule(urlRuleArr[i]);
					if (StrKit.notBlank(rule)) {
						Record other = new Record();
						other.set("rule", rule);
						other.set("hostType", shostTypeArr[i]);
						other.set("desc", urlRuleDescArr[i]);
						other.set("remark", urlRuleRemarkArr[i]);
						other.set("level3Id", level3Id.toString());
						other.set("level3Name", level3Name);
						ruleMap.put(rule, other);
					}
				}
			}
		}
		Iterator<String> iterator = ruleMap.keySet().iterator();
		while (iterator.hasNext()) {
			String rule = iterator.next();
			saveToLevel4(ruleMap.get(rule));
		}

		redirect("/domain/urlrule/list");
	}

	private boolean saveToLevel4(Record record) {
		if (record.get("hostType") == null) {
			record.set("hostType", 1);
		}
		boolean ifSave = false;
		String strHost = LocalURLUtil.getTopDomain(record.getStr("rule"));
		UrlRule rule = UrlRule.dao.findByName(record.getStr("rule"));
		UrlHost host = UrlHost.dao.findByName(LocalURLUtil.getTopDomain(record.getStr("rule")));
		if (StrKit.isBlank(strHost) || StrKit.isBlank(record.getStr("rule")))
			return false;
		if (rule != null) {
			if (rule.get("ruleId") != null) {
				new UrlRule().set("ruleId", rule.get("ruleId")).set("status", 1).set("desc", record.get("desc"))
						.set("remark", record.get("remark")).update();
				if (host != null) {
					host.set("hostType", record.get("hostType")).update();
				}
			}
			record.set("ruleId", rule.getInt("ruleId").toString());
		} else {
			UrlRule urlRule = new UrlRule();
			if (host == null) {
				host = new UrlHost();
				host.set("host", strHost).set("hostType", record.get("hostType")).save();
			} else {
				host.set("hostType", record.get("hostType")).update();
			}
			urlRule.set("hostId", host.get("hostId")).set("rule", record.get("rule")).set("status", 1)
					.set("desc", record.get("desc")).set("remark", record.get("remark")).save();
			record.set("ruleId", urlRule.get("ruleId").toString());
		}
		if (record.get("level3Id") != null) {
			Level4 l4 = new Level4();
			l4.set("level3Id", record.get("level3Id")).set("level4Type", 2).set("ruleId", record.get("ruleId"));
			Level4 l42 = l4.findByModel();
			if (l42 == null) {
				l4.set("status", 0).set("level3Name", record.get("level3Name")).save();
				ifSave = true;
			}
		}
		return ifSave;
	}

	public void templist() {
		Global global = Global.getInstance(this);
		String regId = global.getSessionUserName();
		UrlRuleTemp record = getModel(UrlRuleTemp.class, "record");
		record.put("regIdNoLike", regId);
		if (StrKit.notBlank(getPara("ifCanLoad"))) {
			record.put("ifCanLoad", getParaToInt("ifCanLoad"));
			setAttr("ifCanLoad", getParaToInt("ifCanLoad"));
		}
		String submit = getPara("Submit");
		if (submit != null) {
			setAttr("records", record.paginate(1, 10));
		} else {
			setAttr("records", record.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("record", record);
		setAttr("importResult", getPara("importResult"));
		render("/WEB-INF/jsp/data/rule/templist.html");
	}

	public void importUrlRule() {
		UploadFile uploadFile = getFile("uploadFile");
		Global global = Global.getInstance(this);
		String regId = global.getSessionUserName();
		List<UrlRuleTemp> records = new ArrayList<UrlRuleTemp>();
		ReadExcelEls rce = new ReadExcelEls();
		try {
			/*records = rce.readImportRules(uploadFile.getFile());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder urlRuleBuild = new StringBuilder();
		String urlRuleStr = "";
		for (UrlRuleTemp urlRuleTemp : records) {
			urlRuleBuild.append(",'" + RuleUtil.formatRule(urlRuleTemp.getStr("urlRule")) + "'");
		}
		urlRuleStr = urlRuleBuild.toString();
		if (urlRuleStr.startsWith(",")) {
			urlRuleStr = urlRuleStr.substring(1);
		}
		List<Record> level4His = Level4.dao.findByRules(urlRuleStr);
		List<Record> level3List = Db.use("dmp").find("select * from level3");
		Map<String, Integer> level3Map = new HashMap<>();
		for (Record record : level3List) {
			level3Map.put(record.getStr("level3Name"), record.getInt("level3Id"));
		}
		Set<String> urlSet = new HashSet<>();
		for (UrlRuleTemp temp : records) {
			String rule = RuleUtil.formatRule(temp.getStr("urlRule"));
			String host = LocalURLUtil.getTopDomain(rule);
			String level3Name = temp.getStr("level3Name");
			Integer level3Id = level3Map.get(level3Name);
			temp.set("regId", regId);
			temp.set("status", 11);
			temp.set("level3Id", level3Id);
			if (StrKit.isBlank(host)) {
				temp.set("status", 12);// 域名错误
			}
			if (StrKit.isBlank(rule)) {
				temp.set("status", 17);// 规则错误
			} else {
				temp.set("urlRule", rule);
			}
			if (level3Id == null) {
				temp.set("status", 13);// 行业不存在
			}
			if (urlSet.contains(rule)) {
				temp.set("status", 15);// 和本次重复
			}
			if (ifContainHis(level4His, temp)) {
				temp.set("status", 14);// 历史已存在
			}
			if (!StrKit.isBlank(host)) {
				temp.set("host", host);
			}
			urlSet.add(rule);

		}
		UrlRuleTemp.dao.BatchSave(records);
		redirect("/domain/urlrule/templist");
	}

	public void certainImport() {
		Global global = Global.getInstance(this);
		String regId = global.getSessionUserName();
		List<Record> canSaveList = UrlRuleTemp.dao.findCanSaveListByRegId(regId);
		int importResult = 0;
		for (Record r : canSaveList) {
			r.set("rule", r.getStr("urlRule"));
			boolean b = saveToLevel4(r);
			if (b) {
				importResult++;
			}
		}
		UrlRuleTemp.dao.deleteUnImport(regId);
		redirect("/domain/urlrule/templist?importResult=" + importResult);
	}

	public void clearImport() {
		Global global = Global.getInstance(this);
		String regId = global.getSessionUserName();
		UrlRuleTemp.dao.deleteUnImport(regId);
		redirect("/domain/urlrule/templist");
	}

	private static boolean ifContainHis(List<Record> level4His, UrlRuleTemp temp) {
		for (Record record : level4His) {
			if (record.getStr("rule").equals(temp.getStr("urlRule"))) {
				return true;
			}
		}
		return false;
	}

	public void del() {
		Integer id = getParaToInt();
		if (id != null) {
			new Level4().deleteById(id);
		}
		redirect("/domain/urlrule/list");
	}
}
