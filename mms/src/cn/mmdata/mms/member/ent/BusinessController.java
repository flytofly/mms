package cn.mmdata.mms.member.ent;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import cn.mmdata.commons.util.Global;
import cn.mmdata.commons.util.StringUtil;

public class BusinessController extends Controller {

	/*
	 * 查找企业
	 */
	public void rlist() {
		String businessName = getPara("search");
		
		Business business = getModel(Business.class);
		SetUserType(business);
		if (!"".equals(businessName) && businessName != null) {
			business.set("business_name", businessName);
		}

		
		int pageNumber = getParaToInt("pageNumber",1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");
		if(StrKit.notBlank(submit)){
			setAttr("records", business.paginate(1, 10));
		}else{
			setAttr("records", business.paginate(pageNumber, 10));
		}
		setAttr("business", business);
		/*render("/WEB-INF/jsp/member/ent/rlist.jsp");*/
		render("/WEB-INF/jsp/business/business/rlist.html");
	}
	

	
	/*
	 * 查找企业
	 */
	public void clist() {
		Business business = getModel(Business.class);
		SetUserType(business);
		String submit = getPara("Submit");
		if (submit != null) {
			setAttr("records", business.paginate(1, 10));
		} else {
			setAttr("records", business.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("business", business);
		render("/WEB-INF/jsp/member/ent/clist.jsp");
	}
	
	public void searchList(){
		Business business = getModel(Business.class);
		int pageNumber = getParaToInt("pageNumber",1);
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		String submit = getPara("Submit");
		SetUserType(business);
		if(StrKit.notBlank(submit)){
			setAttr("records", business.paginate(1, 10));
		}else{
			setAttr("records", business.paginate(pageNumber, 10));
		}
		setAttr("business", business);
		render("/WEB-INF/jsp/member/agent/searchlist.jsp");
	}
	
	public void list() {
		Business business = getModel(Business.class);
		String submit = getPara("Submit");
		SetUserType(business);
		if (submit != null) {
			setAttr("records", business.paginate(1, 10));
		} else {
			setAttr("records", business.paginate(getParaToInt("pageNumber", 1), 10));
		}
		setAttr("business", business);
		render("/WEB-INF/jsp/member/ent/list.jsp");
	}



	private void SetUserType(Business business) {
		Global global = Global.getInstance(this);
		int userType = global.getSessionUserType();
		if (userType == Global.USER_TYPE_AGENT) {
			business.put("agentId", global.getSessionBId());
		}
		if (userType == Global.USER_TYPE_BUSINESS) {
			business.put("bid", global.getSessionBId());
		}
	}

	public void delete() {
		new Business().deleteBusById(getPara("bid"));
		redirect("/business/bsn/list?business.business_type=2");
	}

	public void queryByNameAndType() {
		String businessName = getPara("q");
		String businesstype = getPara("businesstype");
		List<Business> businessList = Business.dao.queryByNameAndType(businessName, businesstype);
		renderJson(businessList);
	}

	public void queryCustomerByName() {
		String businessName = getPara("q");
		List<Business> businessList = Business.dao.queryCustomerByName(businessName);
		renderJson(businessList);
	}

	public void agentList() {
		Business business = getModel(Business.class);
		String submit = getPara("Submit");
		setAttr("records", business.AgentPaginate(getParaToInt("pageNumber", 1), 10, business, submit));
		setAttr("business", business);
		render("/WEB-INF/jsp/member/agent/list.jsp");
	}

	public void agentEdit() {
		String bid = getPara("bid");
		if (!StrKit.isBlank(bid)) {
			Business business = Business.dao.findById(bid);
			List<Business> businesses = Business.dao.findByAgentId(bid);
			String bids = "";
			for (Business bus : businesses) {
				bids += (bus.getInt("bid") + ",");
			}
			setAttr("business", business);
			setAttr("records", businesses);
			setAttr("bids", bids);
		}
		render("/WEB-INF/jsp/member/agent/edit.jsp");
	}

	public void editCustomer() {
		String bid = getPara();
		if (!StringUtil.isNull(bid)) {
			List<Business> customerList = Business.dao.queryCustomerByAgentId(bid);
			renderJson(customerList);
		}
	}

	public void agentUpdate() {
		String bid = getPara("business.bid");
		Business business = getModel(Business.class, "record");
		if (!StrKit.isBlank(bid)) {
			business.set("bid", bid);
			business.update();
		} else {
			business.set("business_type", 3).save();
			bid = business.get("bid").toString();
		}
		new Business().deleteBusinessAgentrelByAgentId(bid);
		String[] busStrs = getPara("bids").split(",");
		if (!"".equals(busStrs[0])) {
			new Business().insertBusinessAgentrel(bid, busStrs);
		}
		redirect("/business/agent/agentList");
	}

	public void agentDetail() {
		Business agent = Business.dao.findById(getPara("bid"));
		String businessNames = "";
		List<Business> businesses = Business.dao.findByAgentId(getPara("bid"));
		for (int i = 0; i < businesses.size(); i++) {
			if (i == (businesses.size() - 1)) {
				businessNames += businesses.get(i).getStr("business_name");
			} else {
				businessNames += (businesses.get(i).getStr("business_name") + ",   ");
			}
		}
		setAttr("businessNames", businessNames);
		setAttr("agent", agent);
		renderJsp("/WEB-INF/jsp/member/agent/detail.jsp");
	}

	public void agentDelete() {
		new Business().deleteBusById(getPara("bid"));
		redirect("/business/agent/agentList");
	}

}
