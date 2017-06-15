package cn.mmdata.mms.statis.statopti;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import cn.mmdata.commons.util.Global;

public class StatOptiController extends Controller {
	public void index() {
		list(); 
	}

	public void list() {
		StatOpti record = new StatOpti();
		if (StrKit.notBlank(getPara("status"))) {
			record.put("status", getPara("status"));
		}
		if (StrKit.notBlank(getPara("out_type"))) {
			record.put("out_type", getPara("out_type"));
		}
		if (StrKit.notBlank(getPara("detail_name"))) {
			record.put("detail_name", getPara("detail_name"));
		}if(StrKit.notBlank(getPara("business_name"))){
			record.put("business_name",getPara("business_name"));
		}
		
		// if (StrKit.isBlank(getPara("pickday"))) {
		// record.put("pickday", DateTools.getDate(-1));
		// } else {
		// }
		if (StrKit.notBlank(getPara("pickday"))) {
			record.put("pickday", getPara("pickday"));
		}
		Integer pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");

		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);

		if (StrKit.notBlank(submit)) {
			setAttr("records", record.paginate(1, 10));
		} else {
			setAttr("records", record.paginate(pageNumber, 10));
		}
		setAttr("record", record);
		render("/WEB-INF/jsp/statis/statopti/list.html");
	}

	public void detailList() {
		int out_type = getParaToInt("out_type", -1);
		int detail_id = getParaToInt("detail_id", -1);
		String pickday = getPara("pickday");
		StatOpti record = new StatOpti();
		Integer pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");

		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		record.put("out_type", out_type);
		record.put("detail_id", detail_id);
		if (StrKit.notBlank(pickday)) {
			record.put("pickday", pickday);
		}
		if (StrKit.notBlank(submit)) {
			setAttr("records", record.detailPaginate(1, 10));
		} else {
			setAttr("records", record.detailPaginate(pageNumber, 10));
		}
		setAttr("record", record);
		if (out_type == 1) {
			render("/WEB-INF/jsp/statis/statopti/detaillistzx.html");
		} else if (out_type == 2) {
			render("/WEB-INF/jsp/statis/statopti/detaillistsms.html");
		}
	}

	public void detailYhjy() {
		int out_type = getParaToInt("out_type", -1);
		int detail_id = getParaToInt("detail_id", -1);
		StatOpti record = new StatOpti();
		Integer pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");

		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		record.put("out_type", out_type);
		record.put("detail_id", detail_id);
		if (StrKit.notBlank(submit)) {
			setAttr("records", record.detailYhjy(1, 10));
		} else {
			setAttr("records", record.detailYhjy(pageNumber, 10));
		}
		setAttr("record", record);
		render("/WEB-INF/jsp/statis/statopti/detailyhjy.html");
	}
	public void detailRuleList() {
		int out_type = getParaToInt("out_type", -1);
		int detail_id = getParaToInt("detail_id", -1);
		
		StatOpti record = new StatOpti();
		Integer pageNumber = getParaToInt("pageNumber", 1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");

		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		record.put("out_type", out_type);
		record.put("detail_id", detail_id);
		
		if (StrKit.notBlank(submit)) {
			setAttr("records", record.detailRulePaginate(1, 10));
		} else {
			setAttr("records", record.detailRulePaginate(pageNumber, 10));
		}
		setAttr("record", record);
		render("/WEB-INF/jsp/statis/statopti/detailrulezx.html");
	}
	
	//建议删除
	  public void del(){
		int ruleId=  this.getParaToInt("ruleId");
		int detail_id=this.getParaToInt("detail_id");		
			StatOpti record = new StatOpti();
			String message="删除成功";
			try{
			record.detailRuleDel(detail_id, ruleId);
			}catch(Exception e){
				message="删除失败";
			}
			renderJson(message);
	  }
	  
	//建议增加
	  public void add(){
			int ruleId=  this.getParaToInt("ruleId");
			int detail_id=this.getParaToInt("detail_id");
		    StatOpti record=  new StatOpti();
		    String message="增加成功";
			  try{
			  record.detailRuleAdd(detail_id, ruleId);
			  }catch( Exception e){
				  message="增加失败";
			  }
		   renderJson(message);

	  }
	
	
}
