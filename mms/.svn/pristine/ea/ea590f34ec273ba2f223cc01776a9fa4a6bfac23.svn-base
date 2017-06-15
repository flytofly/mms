package cn.mmdata.mms.config.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import cn.mmdata.commons.util.Global;
import cn.mmdata.mms.system.user.UserInfo;

public class AuthInterceptor implements Interceptor {
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Global global = Global.getInstance(controller);
		UserInfo user = global.getSessionUser();
		if (user != null) {
			controller.setAttr(Global.SESSION_USER, user);
			inv.invoke();
		} else {
			controller.redirect("/login");
		}
	}
}