package cn.mmdata.mms.system.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.StringUtil;
import cn.mmdata.mms.system.module.Module;

public class RoleController extends Controller {

	public void list() {
		Role role = getModel(Role.class, "record");
		setAttr("records", role.paginate(getParaToInt("pageNumber", 1), 10));
		setAttr("record", role);
		render("/WEB-INF/jsp/system/jrole/list.html");
	}

	public void edit() {
		String role_id = getPara();
		if (!StringUtil.isNull(role_id)) {
			Role role = Role.dao.findById(role_id);
			setAttr("record", role);

		}else{
			setAttr("record", new Role());
		}
		render("/WEB-INF/jsp/system/jrole/edit.html");
	}

	public void detail() {
		String role_id = getPara(0);
		Role role = Role.dao.findById(role_id);
		List<Record> rolemodules = Module.dao.getModule(Integer.valueOf(role_id));
		setAttr("record", role);
		setAttr("rolemodules", rolemodules);
		render("/WEB-INF/jsp/system/jrole/detail.html");
	}

	public void update() {
		String role_id = getPara("record.role_id");
		Role r = getModel(Role.class, "record");
		if (!StringUtil.isNull(role_id)) {
			r.update();
		} else {
			r.save();
		}
		redirect("/system/role/list");
	}

	public void delete() {
		new Role_Module().deleteByRid(getPara());
		new Role().deleteById(getParaToInt());
		redirect("/system/role/list");
	}

	public void editmodule() {
		String role_id = getPara();
		if (!StringUtil.isNull(role_id)) {
			Role role = Role.dao.findById(role_id);
			setAttr("record", role);
		}
		render("/WEB-INF/jsp/system/jrole/editmodule.html");
	}

	public void getModuleTree() {
		List<Record> list = Module.dao.getAllModule();
		Set<String> checkbox = new HashSet<String>();
		List<Record> rules = new ArrayList<Record>();
		if (getPara("role_id") != null && !"".equals(getPara("role_id"))) {
			rules = Module.dao.getModule(getParaToInt("role_id"));
		}
		Map<String, List<EUTreeModule>> mapparent = new HashMap<String, List<EUTreeModule>>();
		List<EUTreeModule> listparent = new ArrayList<EUTreeModule>();
		for (Record rul : rules) {
			checkbox.add(String.valueOf(rul.get("module_id")));
		}

		for (Record record : list) {
			EUTreeModule eu = new EUTreeModule();

			if (mapparent.containsKey(String.valueOf(record.get("parent_module_id")))) {
				eu.setId(String.valueOf(record.get("module_id")));
				eu.setText(String.valueOf(record.get("module_name")));
				eu.setPid(String.valueOf(record.getInt("parent_module_id")));
				eu.setModuleLayerMark(String.valueOf(record.get("module_layer_mark")));
				mapparent.get(String.valueOf(record.get("parent_module_id"))).add(eu);

			} else {
				List<EUTreeModule> list1 = new ArrayList<EUTreeModule>();
				eu.setId(String.valueOf(record.get("module_id")));
				eu.setText(String.valueOf(record.get("module_name")));
				eu.setPid(String.valueOf(record.getInt("parent_module_id")));
				eu.setModuleLayerMark(String.valueOf(record.get("module_layer_mark")));
				list1.add(eu);
				mapparent.put(String.valueOf(record.getInt("parent_module_id")), list1);
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

	private void setcheck(EUTreeModule em, Set<String> checkbox) {
		if (em.getChildren().size() > 0) {
			for (EUTreeModule module : em.getChildren()) {
				setcheck(module, checkbox);
			}

		} else {
			em.setChecked(checkbox.contains(em.getId()));
		}

	}

	public void editmoduleupdate() {
		String role_id = getPara("record.role_id");
		String modules = getPara("modules");
		if (!StringUtil.isNull(role_id)) {
			new Role_Module().deleteByRid(role_id);
		}
		if (StringUtil.isNull(modules)) {
			redirect("/system/role/list");
			return;
		}
		String[] moduleArr = modules.split(",");
		Set<String> moduleSet = new TreeSet<>();
		for (int i = 0; i < moduleArr.length; i++) {
			moduleSet.add(moduleArr[i].split("_")[0]);
		}
		for (int i = 0; i < moduleArr.length; i++) {
			moduleSet.add(moduleArr[i].split("_")[1]);
		}

		if (!StringUtil.isNull(role_id)) {
			new Role_Module().deleteByRid(role_id);
		}

		Iterator<String> i = moduleSet.iterator();
		String mi = "";
		while (i.hasNext()) {
			mi = i.next();
			if ("0".equals(mi)) {
				continue;
			}
			Role_Module ur = new Role_Module();
			ur.set("role_id", role_id);
			ur.set("module_id", mi);
			ur.set("reg_date", new Date());
			ur.save();
		}

		redirect("/system/role/list");
	}
}
