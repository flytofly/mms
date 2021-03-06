package cn.mmdata.mms.delivery.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.mmdata.commons.util.Global;
import cn.mmdata.mms.data.crowd.CrowdArea;
import cn.mmdata.mms.member.ent.BusinessProject;

public class TaskController extends Controller {
	public void list() {
		Task record = getModel(Task.class, "record");
		String businessname = getPara("businessname");
		record.put("businessname", businessname);
		
		int custype = Global.getInstance(this).getSessionUserType();
		Integer bid = Global.getInstance(this).getSessionBId();
		record.put("custype", custype);
		record.put("bid", bid);
		
		
		String submit = getPara("Submit");
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		if (StrKit.notBlank(submit)) {
			pageNumber = 1;
		}
		setAttr("record", record);
//		Page<Task> records = record.paginate_temp(pageNumber, pageSize);
		Page<Task> records = record.paginate(pageNumber, pageSize);
		setAttr("records", records);
		render("/WEB-INF/jsp/delivery/task/list.html");
	}

	
	public void edit() {
		int taskid = getParaToInt("taskid", -1);
		if (taskid != -1) {
			Task record = Task.dao.findByTaskId(taskid);
			List<Record> list =DetailCrowd.dao.findByDetailID(taskid);
			
			
			setAttr("record", record);
			setAttr("crowdList",list);
		}else{
			setAttr("record", new Task());
		}
		
		render("/WEB-INF/jsp/delivery/task/edit.html");
	}

	public void detail() {
		int taskid = getParaToInt("taskid", -1);
		Task record = Task.dao.findByTaskId(taskid);
		List<Record> list =DetailCrowd.dao.findByDetailID(taskid);
		if(record!=null){
			setAttr("crowdList",list);
			setAttr("record", record);
		}
		
		render("/WEB-INF/jsp/delivery/task/detail.html");
	}

	public void update() {
		String[] cids = getParaValues("cid");
		Task record = getModel(Task.class, "record");
		record.set("canpick_number", "0");
		record.set("log_id", Global.getInstance(this).getSessionUserId());
		record.set("log_name", Global.getInstance(this).getSessionUserName());
		record.set("lastupdatetime", new Date());
		Global global = Global.getInstance(this);
		int detailid = 0;
		int bid=global.getSessionBId();
		if(record.get("bid")!=null){
			bid = record.getInt("bid");
		}
		if (record.get("detail_id") != null) {
			record.update();
		} else {
			int projectType=6;
			int projectid =this.getProjectID(bid, projectType);
			record.set("project_id", projectid);
			record.set("project_type",projectType);
			record.set("rel_id", 321);
			record.set("rel_name", "未分类");
			record.set("use_status", 1);
			record.set("bid",bid);
			record.save();
		}
		detailid =record.getInt("detail_id");
		//删除需求输出位置表
		DetailOutput.dao.deleteByDetailID(record.getInt("detail_id"));
		DetailOutput dout =new DetailOutput();
		dout.set("detail_id",detailid);
		dout.set("out_type", this.getParaToInt("out_type", 0));
		dout.set("mid", this.getParaToInt("mid"));
		dout.save();
		
		// 更新人群操作
		DetailCrowd.dao.deleteTaskCrowdByTaskId(detailid);
		
	    String cidStr = "";
	    
	    if(cids!=null){
	    	for(String i : cids){
	 	       cidStr += (i + ",");
	 	    }
	 	    cidStr = cidStr.substring(0, cidStr.lastIndexOf(","));
	 	    
	 	      // 保存人群的规则和地区
	 	    for(String cid: cids){
	 	    	DetailCrowd dcrowd =new DetailCrowd();
	 		    dcrowd.set("detail_id", detailid);
	 		    dcrowd.set("cid", cid);
	 		    dcrowd.save();
	 	    }
	 	    
	 	    List<Record> areaList =CrowdArea.dao.findAreas(cidStr);
	 	    DetailArea.dao.deleteByDetailID(detailid);
	 	    DetailArea.dao.saveBatch(areaList, detailid);//保存地区
	 	    
	 	    String crowdSql="insert into cy_business.cy_business_project_sskw (detail_id,level4type,kid,keyword,`desc`,`status`,"
	 	    		+ "insertTime) select ?,2, rid,`rule`,rule_desc,1,now() "
	 	    		+ "from mms.crowd_rule a where a.`status`= 1 and a.cid in ("+cidStr+")   group by rid,`rule`,rule_desc";
	 	    Db.update(crowdSql, detailid);
	    }
	    
		   
		/*if (cids != null && cids.length > 0) {
			List<Record> taskCrowdList = new ArrayList<>();
			for (String string : cids) {
				if (StrKit.notBlank(string)) {
					Record r = new Record();
					r.set("task_id", record.get("taskid"));
					r.set("crowd_id", string);
					taskCrowdList.add(r);
				}
			}
			Task.dao.batchSaveTaskCrowd(taskCrowdList);
		}*/
		redirect("/task/list");
	}
	
	public void delete() {
		int detailid = getParaToInt("taskid", -1);
		/*Task.dao.deleteById(detailid);
		DetailCrowd.dao.deleteTaskCrowdByTaskId(detailid);
		DetailOutput.dao.deleteByDetailID(detailid);
		DetailArea.dao.deleteByDetailID(detailid);*/
		Task.dao.deleteTask(detailid);
		redirect("/task/list");
	}

	public void batchToEnabled() {
		int i = 0;
		String ids = getPara("taskids");
		if (ids != null && ids.length() > 0) {
			i = Task.dao.batchUpdateUseStatus(getParaToInt("use_status"), ids);
		}
		renderJson(i);
	}

	public void editpickrequency() {
		setAttr("taskids", getPara("taskids"));
		render("/WEB-INF/jsp/task/editpickrequency.html");
	}

	public void uppickrequency() {
		String taskids = getPara("taskids");
		if (StrKit.isBlank(taskids)) {
			renderText("false");
			return;
		}
		String[] taskarr = taskids.split(",");
		List<Record> list = new ArrayList<>();
		for (String string : taskarr) {
			if (StrKit.notBlank(string)) {
				Record r = new Record();
				r.set("task_id", string);
				r.set("day_touch_uplimit", getPara("record.day_touch_uplimit"));
				r.set("week_touch_uplimit", getPara("record.week_touch_uplimit"));
				r.set("mouth_touch_uplimit", getPara("record.mouth_touch_uplimit"));
				r.set("day_pick_starttime", getPara("record.day_pick_starttime"));
				r.set("day_pick_endtime", getPara("record.day_pick_endtime"));
				list.add(r);
			}
		}
		Db.update("delete from task_pickrequency where task_id in (" + taskids + ")");
		renderText(Db.batchSave("task_pickrequency", list, 1000).length > 0 ? "true" : "false");
	}
	private int getProjectID(int bid,int project_type){
		BusinessProject project;
		project = BusinessProject.dao.findProjectByBidAndType(bid, project_type);
		if (project == null) {
			project = new BusinessProject();
			// project.set("project_name", project_name);
			project.set("project_type", project_type);
			project.set("bid", bid);
			project.save();// 插入项目表
		}
		return project.getInt("project_id");
	}

	public LinkedList<Record> countTask() {
		return new Task().countTask();
	}
}
