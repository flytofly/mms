package cn.mmdata.mms.config.route;

import com.jfinal.config.Routes;

import cn.mmdata.mms.commons.CommonController;
import cn.mmdata.mms.commons.HomeController;
import cn.mmdata.mms.data.crowd.CrowdController;
import cn.mmdata.mms.data.rule.UrlRuleController;
import cn.mmdata.mms.data.scene.SceneController;
import cn.mmdata.mms.data.tag.TagController;
import cn.mmdata.mms.delivery.material.MaterialController;
import cn.mmdata.mms.delivery.task.TaskController;
import cn.mmdata.mms.member.ent.BusinessController;
import cn.mmdata.mms.statis.rulestatis.RuleStatisController;
import cn.mmdata.mms.statis.statopti.StatOptiController;
import cn.mmdata.mms.system.module.ModuleController;
import cn.mmdata.mms.system.role.RoleController;
import cn.mmdata.mms.system.user.UserInfoController;

public class BasicRoute extends Routes {
	@Override
	public void config() {
		add("/", CommonController.class);
		add("/home", HomeController.class);
		add("/system/user", UserInfoController.class);
		add("/system/role", RoleController.class);
		add("/system/module", ModuleController.class);
		
		add("/data/crowd", CrowdController.class);
		add("/data/scene", SceneController.class);
		add("/data/rule", UrlRuleController.class);
		add("/data/buscus", BusinessController.class);
		
		add("/api/tag", TagController.class);
		add("/delivery/task", TaskController.class);
		add("/delivery/material", MaterialController.class);
		add("/statis/rulestatis", RuleStatisController.class);
		add("/statis/statopti", StatOptiController.class);
		
	}
}
