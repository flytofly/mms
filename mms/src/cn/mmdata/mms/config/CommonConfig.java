package cn.mmdata.mms.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

import cn.mmdata.mms.config.handle.URLPathHandler;
import cn.mmdata.mms.config.interceptor.AuthInterceptor;
import cn.mmdata.mms.config.plugin.DmpDBPlugin;
import cn.mmdata.mms.config.plugin.MmsDBPlugin;
import cn.mmdata.mms.config.plugin.ZhijianDBPlugin;
import cn.mmdata.mms.config.route.AnalysisRoute;
import cn.mmdata.mms.config.route.BasicRoute;

public class CommonConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JFINAL_TEMPLATE);
		me.setBaseDownloadPath("upload");
		PropKit.use("jdbc.properties");
	}

	@Override
	public void configRoute(Routes r) {
		r.add(new BasicRoute());
		r.add(new AnalysisRoute());
	}

	@Override
	public void configPlugin(Plugins me) {
		DmpDBPlugin.addPlugin(me);
		ZhijianDBPlugin.addPlugin(me);
		MmsDBPlugin.addPlugin(me);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new AuthInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("BasePath")); //使用BasePath作为ContentPath
		me.add(new URLPathHandler()); //使用BasePath作为ContentPath
	}

	@Override
	public void afterJFinalStart() {
	}

	public static void main(String[] args) {
		JFinal.start("WebContent", 8080, "/", 5);
	}

	@Override
	public void configEngine(Engine me) {
		String template = "/manager/black";
		me.addSharedObject("tp", template);
		me.addSharedFunction(template + "/commons/layout.html");
		me.addSharedFunction(template + "/commons/header.html");
		me.addSharedFunction(template + "/commons/footer.html");
		me.addSharedFunction(template + "/commons/left.html");
		me.addSharedFunction(template + "/commons/_paginate.html");
	}
}
