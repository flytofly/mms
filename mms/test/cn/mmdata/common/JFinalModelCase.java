package cn.mmdata.common;

import org.junit.AfterClass;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import cn.mmdata.commons.util.PropertiesUtils;
import cn.mmdata.mms.data.crowd.Crowd;
import cn.mmdata.mms.data.crowd.CrowdArea;
import cn.mmdata.mms.data.crowd.CrowdRule;
import cn.mmdata.mms.data.crowd.CrowdScene;
import cn.mmdata.mms.data.scene.Scene;
import cn.mmdata.mms.delivery.material.Material;
import cn.mmdata.mms.member.ent.Business;
import cn.mmdata.mms.member.ent.BusinessProject;
import cn.mmdata.mms.system.module.Module;
import cn.mmdata.mms.system.role.Role;
import cn.mmdata.mms.system.role.Role_Module;
import cn.mmdata.mms.system.user.LoginLog;
import cn.mmdata.mms.system.user.UserInfo;
import cn.mmdata.mms.system.user.User_Role;
import cn.mmdata.mms.task.DetailArea;
import cn.mmdata.mms.task.DetailCrowd;
import cn.mmdata.mms.task.DetailOutput;
import cn.mmdata.mms.task.Task;

public class JFinalModelCase  {
	
   @AfterClass    
    public static void afterClass() {     
        System.out.println(" AfterClass action ");   
        try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(" AfterClass end ");   
    };     

	protected static C3p0Plugin c3p0;
	protected static C3p0Plugin c3p01;
	protected static ActiveRecordPlugin activeRecord;
	protected static ActiveRecordPlugin activeRecord1;
	private static final String DATABASE_TYPE = "mysql";

	static {
		PropertiesUtils utils = new PropertiesUtils();

		//C3p0数据源插件
        c3p0 = new C3p0Plugin(utils.getPropertiesValue("mms.jdbcUrl"),utils.getPropertiesValue("mms.user"),
        		utils.getPropertiesValue("mms.password"), utils.getPropertiesValue("driver"));
        c3p0.setInitialPoolSize(5);
        c3p0.setMinPoolSize(5);
        c3p0.setMaxPoolSize(130);
        c3p0.setMaxIdleTime(25000);
        c3p0.start();
		// 配置ActiveRecord插件
		ActiveRecordPlugin activeRecord = new ActiveRecordPlugin(DbKit.MAIN_CONFIG_NAME, c3p0);
		/*activeRecord.addMapping("cy_business", "bid", Business.class);*/
		activeRecord.addMapping("role", "role_id", Role.class);
		activeRecord.addMapping("role_module", "rolemodule_id", Role_Module.class);
		activeRecord.addMapping("module", "module_id", Module.class);
		activeRecord.addMapping("customer_role", "customerrole_id", User_Role.class);
		activeRecord.addMapping("crowd", "cid", Crowd.class);
		activeRecord.addMapping("scene", "sid", Scene.class);
		activeRecord.addMapping("crowd_scene", CrowdScene.class);
		activeRecord.addMapping("crowd_rule",  CrowdRule.class);
		activeRecord.addMapping("crowd_area", CrowdArea.class);
		activeRecord.addMapping("material", "mid", Material.class);
		activeRecord.setShowSql(true);
		activeRecord.start();
		
		c3p01 = new C3p0Plugin(utils.getPropertiesValue("zhijian.jdbcUrl"), utils.getPropertiesValue("zhijian.user"),
				utils.getPropertiesValue("zhijian.password"), utils.getPropertiesValue("driver"));
		c3p01.setInitialPoolSize(5);
		c3p01.setMinPoolSize(5);
		c3p01.setMaxPoolSize(130);
		c3p01.setMaxIdleTime(25000);
		c3p01.start(); // 启动数据库连接
		activeRecord1 = new ActiveRecordPlugin(DATABASE_TYPE, c3p01.getDataSource());
		activeRecord1.setDialect(new MysqlDialect()).setShowSql(true).setDevMode(true);
		// 映射数据库的表和继承与model的实体
		activeRecord1.addMapping("cy_business", "bid", Business.class);
		activeRecord1.addMapping("cy_customer", "customer_id", UserInfo.class);
		activeRecord1.addMapping("cy_business_project", "project_id", BusinessProject.class);
		activeRecord1.addMapping("cy_business_project_detail", "detail_id", Task.class);
		activeRecord1.addMapping("cy_business_project_detail_output", "id", DetailOutput.class);
		activeRecord1.addMapping("cy_business_project_detail_crowd", "id", DetailCrowd.class);
		activeRecord1.addMapping("cy_business_project_detail_area", "id", DetailArea.class);
		activeRecord1.addMapping("cy_login_log", "id", LoginLog.class);
		
		activeRecord1.start();
		
	}
	
}
