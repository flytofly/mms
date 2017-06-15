package cn.mmdata.mms.config.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

import cn.mmdata.mms.commons.CommonDefined;

public class URLPathHandler  extends Handler {
	
	private String requestURL;
	private String requestInfo;
	private String requestTitle;
	public URLPathHandler() {
		requestURL = "URLPath";
		requestInfo ="URLInfo";
		requestTitle ="URLTitle";
	}
	
	public URLPathHandler(String requestURL,String URLInfo,String URLTitle) {
		if (StrKit.isBlank(requestURL)) {
			throw new IllegalArgumentException("requestURL can not be blank.");
		}
		this.requestURL = requestURL;
		this.requestInfo = URLInfo;
		this.requestTitle = URLTitle;
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		String urlPath =request.getRequestURL().toString();
		System.out.println("url path:"+urlPath);
		
		String apath ="basic";
		String ainfo = "上海技维信息科技有限公司";
		String atitle = "美美数大数据营销系统";
		if(urlPath.contains("lingjing.citysdk.cn")){
			apath ="lingjing";//图片路径
			ainfo = "智慧神州（北京）科技有限公司 京ICP备15054427号-2";//公司备注案信息
			atitle ="灵境大数据营销系统";//头部标题信息
		}
		request.setAttribute(requestURL, apath);
		request.setAttribute(requestInfo, ainfo);
		request.setAttribute(requestTitle, atitle);
		String content = request.getContextPath();
		String template = CommonDefined.Template_Default;
		request.setAttribute("uri", urlPath);
		request.setAttribute("tp", template);
		request.setAttribute("tpi", content + template);
		
		next.handle(target, request, response, isHandled);
	}
}