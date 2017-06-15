package cn.mmdata.mms.system.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.mmdata.commons.util.StringUtil;
import cn.mmdata.mms.system.role.EUTreeModule;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

public class ModuleController extends Controller {

	public void list() {
		Module module = getModel(Module.class, "record");
		String submit = getPara("Submit");
		if(StrKit.notBlank(submit)){
			// 查询时将pageNumber置1
			setAttr("records", module.paginate(1, 10));
		}else{
			setAttr("records", module.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("record", module);
		render("/WEB-INF/jsp/system/jmodule/list.html");
	}

	public void edit() {
		String mid = getPara();
		if (!StringUtil.isNull(mid)) {
			Module module = Module.dao.findById(mid);
			setAttr("record", module);
		}else{
			setAttr("record", new Module());
		}
		List<Record> list = Module.dao.getAllModule();
		setAttr("records", list);
		render("/WEB-INF/jsp/system/jmodule/edit.html");
	}

	public void update() {
		String mid = getPara("record.module_id");
		Module r = getModel(Module.class, "record");
		if (!StringUtil.isNull(mid)) {
			r.update();

		} else {
			r.save();
		}
		redirect("/system/module/list");
	}

	public void delete() {
		new Module().deleteById(getParaToInt());
		redirect("/system/module/list");
	}

	public void detail() {
		Module module = Module.dao.findById(getParaToInt(0));
		setAttr("record", module);
		render("/WEB-INF/jsp/system/jmodule/detail.html");
	}

	public void getModuleTree() {
		List<Record> list = Module.dao.getAllModule();
		Set<String> checkbox = new HashSet<String>();
		List<Record> rules = new ArrayList<Record>();
		if (getPara("roleId") != null && !"".equals(getPara("roleId"))) {
			rules = Module.dao.getModule(getParaToInt("roleId"));
		}
		Map<String, List<EUTreeModule>> mapparent = new HashMap<String, List<EUTreeModule>>();
		List<EUTreeModule> listparent = new ArrayList<EUTreeModule>();
		for (Record rul : rules) {
			checkbox.add(String.valueOf(rul.get("mid"))); 
		}
		for (Record record : list) {
			EUTreeModule eu = new EUTreeModule();

			if (mapparent.containsKey(String.valueOf(record.get("pmid")))) {
				eu.setId(String.valueOf(record.get("mid")));
				eu.setText(String.valueOf(record.get("moduleName")));
				eu.setPid(String.valueOf(record.getInt("pmid")));
				eu.setModuleLayerMark(String.valueOf(record.get("moduleLayerMark")));
				mapparent.get(String.valueOf(record.get("pmid"))).add(eu);

			} else {
				List<EUTreeModule> list1 = new ArrayList<EUTreeModule>();
				eu.setId(String.valueOf(record.get("mid")));
				eu.setText(String.valueOf(record.get("moduleName")));
				eu.setPid(String.valueOf(record.getInt("pmid")));
				eu.setModuleLayerMark(String.valueOf(record.get("moduleLayerMark")));
				list1.add(eu);
				mapparent.put(String.valueOf(record.getInt("pmid")), list1);
			}

		}

		for (Entry<String, List<EUTreeModule>> euTreeModule : mapparent.entrySet()) {
			if (euTreeModule.getKey().equals("0")) {
				listparent = euTreeModule.getValue();
				if (listparent.size() <= 0) {
					break;
				}
				for (EUTreeModule euTreeModule1 : listparent) {
					if (mapparent.get(euTreeModule1.getId()) != null) {
						euTreeModule1.getChildren().addAll(mapparent.get(euTreeModule1.getId()));
						for (EUTreeModule euTreeModule2 : euTreeModule1.getChildren()) {
							if (mapparent.get(euTreeModule2.getId()) != null) {
								euTreeModule2.getChildren().addAll(mapparent.get(euTreeModule2.getId()));
							}
						}
					}
				}

			}

		}

		for (EUTreeModule euTreeModule : listparent) {
			setcheck(euTreeModule, checkbox);
		}

		renderJson(listparent);
	}

	public void setcheck(EUTreeModule em, Set<String> checkbox) {
		if (em.getChildren().size() > 0) {
			for (EUTreeModule module : em.getChildren()) {
				setcheck(module, checkbox);
			}

		} else {
			em.setChecked(checkbox.contains(em.getId()));
		}

	}

}
