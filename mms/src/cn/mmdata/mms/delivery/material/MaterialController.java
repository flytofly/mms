package cn.mmdata.mms.delivery.material;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;

public class MaterialController extends Controller {
	
		public void list(){
			Material material = getModel(Material.class,"record");
			String businessname = getPara("businessname");
			material.put("businessname", businessname);
			
			int custype = Global.getInstance(this).getSessionUserType();
			Integer bid = Global.getInstance(this).getSessionBId();
			Integer UserId=Global.getInstance(this).getSessionUserId();
			material.put("custype", custype);
			material.put("bid", bid);
			
			Integer pageNumber = getParaToInt("pageNumber",1);
			pageNumber = pageNumber < 1 ? 1 : pageNumber;
			Page<Record> recList = material.list(pageNumber,10,UserId);
			setAttr("records",recList );
		
			material.put("pageNumber",pageNumber);
			setAttr("record", material);
			render("/WEB-INF/jsp/delivery/material/list.html");
		}
		
		public void edit() {
			String mid = getPara("mid");
			String type = getPara("type");
			if(StrKit.isBlank(mid)){
				mid="0";
			}
			
			if("update".equals(type)){
				Material material = Material.dao.findByMid(Integer.parseInt(mid));
				setAttr("record", material);
			}else {
				setAttr("record", new Material());
			}
			setAttr("UserId",Global.getInstance(this).getSessionUserId());
			render("/WEB-INF/jsp/delivery/material/edit.html");
		}

		public void detail() {
			String mid = getPara("mid");
			Material material = Material.dao.findById(mid);
			setAttr("record", material);
			render("/WEB-INF/jsp/delivery/material/detail.html");
		}

		public void update() {
			Material material = getModel(Material.class,"record");
			Global global = Global.getInstance(this);
			int userId = global.getSessionUserId();
            String log_name=global.getSessionUserName();
            material.set("lastupdatedate", new Date());
            int bid=global.getSessionBId();
    		if(material.get("bid")!=null){
    			bid = material.getInt("bid");
    		}
			if(material.get("mid") != null && !"".equals(material.get("mid"))){
					
				material.update();
			}else{
			       //添加操作			     								
						//用户新增不允许审核 默认状态为0
						if(userId!=1){
							material.set("status", 0);
					     }																		
					material.set("log_id", userId);
					material.set("log_name",log_name );
					material.set("bid",bid);
					material.save();
			}
			redirect("/delivery/material/list");
		}

		public void delete() {
			Material.dao.deleteByMid(getParaToInt("mid"));
			redirect("/delivery/material/list");
		}
		public void rlist(){
			Material material = getModel(Material.class,"record");
			Integer otype=this.getParaToInt("outtype");
			if(otype!=null){
				material.set("m_type", otype);
			}
			
			int custype = 4;//表示弹出框只查指定的客户素材
			Integer UserId=Global.getInstance(this).getSessionUserId();
			
			material.put("custype", custype);
			Integer pageNum = getParaToInt("pageNum",1);
			pageNum = pageNum < 1 ? 1 : pageNum;
			setAttr("records", material.list(pageNum,10,UserId));
			material.put("pageNum",pageNum);
			setAttr("record", material);
			render("/WEB-INF/jsp/delivery/material/rlist.html");
		}
}
