package cn.mmdata.mms.config.plugin;

import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import cn.mmdata.mms.data.rule.Level4;
import cn.mmdata.mms.data.rule.RuleTemp;

public class DmpDBPlugin {
	public static void addPlugin(Plugins me) {
        //C3p0数据源插件
        C3p0Plugin c3p0 = new C3p0Plugin(PropKit.get("dmp.jdbcUrl"),PropKit.get("dmp.user"),PropKit.get("dmp.password"));
		// 初始化连接池数量大小,默认10
        c3p0.setInitialPoolSize(PropKit.getInt("initialSize"));
		// 初始化最小空闲连接数,默认10
        c3p0.setMinPoolSize(PropKit.getInt("minIdle"));
		// 初始化最大活跃连接数,默认100
        c3p0.setMaxPoolSize(PropKit.getInt("maxIdle"));
		// 配置获取连接等待超时的时间
        c3p0.setMaxIdleTime(25000);
        me.add(c3p0);
		// 配置ActiveRecord插件
		ActiveRecordPlugin activeRecord = new ActiveRecordPlugin("dmp", c3p0);
		activeRecord.addMapping("level4", "hostId", Level4.class);
		activeRecord.addMapping("urlrule_temp", "rid", RuleTemp.class);
		activeRecord.setShowSql(true);
		me.add(activeRecord);
	}

}
