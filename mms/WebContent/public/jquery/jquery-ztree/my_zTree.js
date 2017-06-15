/*
 * purpose:对zTree封装，实现comboTree,实现复用
 * author:王升远
 * date:2015_12_1 12:06
 * notice:
 * 		1.使用到body的mousedown事件
 * 		2.使用到的                    控件id     		  
 * 						tt_input	 	
 * 						menuContent	    
 * 						tt
 */



var zTreeObj=null;
var treeData=null;
var my_zTree_beforeClickCallBackFun=null;
var my_zTree_onClickCallBackFun=null;
var my_zTree_initCallBackFun=null;
//var my_zTree_resetCallBackFun=null;
var setting = {
			 	data:{
						simpleData: {
							enable: true
						}
		        },
				callback: {
					beforeClick: null,
					onClick: null
				}
};
/*将id=$("#treeId")控件隐藏,显示如下的图形
<input 
	type="text" 
	name="tt_input" 
	id="tt_input" 
	readonly="readonly" 
	onfocus="showTree();" 
/>
<div 
	id="menuContent" 
	style=" display:none; 
	        position: absolute;
	        background-color:white;
	        overflow-y:scroll;
	        height:300px;
	        border:3px solid #0099CC;"
>
<ul 
	id="tt" 
	class="ztree" 
	style="margin-top:0; width:160px;"
>
</ul>	
</div>
*/
function validate_tt_input(field, rules, i, options){
	if(!treeShow){
		if($("#"+tt_input).val==""){
			return "请选择父模块"
		}
	}
}
function decorateHtml(treeId){
//	var originalControl=$("#"+treeId);
//	var originalControl_offset=$("#"+treeId).offset();
//	var left=originalControl_offset.left;
//	var top=originalControl_offset.top;
	
//	$("#"+treeId).remove();
	var ul=$('#'+treeId);
	var input_string=$('<input type="text" id="tt_input" readonly="readonly" onfocus="showTree();"  />');
	$(ul).after(input_string);
	
	var input=$('#tt_input');
	var div_string=$('<div id="menuContent" style=" display:none;\
			               				            position: absolute;\
				                                    overflow-y:scroll;\
													background-color:white;\
				                                    height:300px;\
				                                    border:1px solid #0099CC;">\
			             <ul id="tt" class="ztree" style="margin-top:0; width:130px;"></ul></div>');
	$(input).after(div_string);
	
//	var inp=$();
//	inp.replaceWith(ul);
	
	
	
//	
//	var creatediv= function(){
//	    var parentdiv=$('<div></div>');        //创建一个父div
//	    parentdiv.attr('id','tt_input');        //给父div设置id
//	    parentdiv.addclass('parentdiv');    //添加css样式
//	    var childdiv=$('<div></div>');        //创建一个子div
//	    childdiv.attr('id','child');            //给子div设置id
//	    childdiv.addclass('childdiv');    //添加css样式
//	    childdiv.appendto(parentdiv);        //将子div添加到父div中
//	    parentdiv.appendto('body');            //将父div添加到body中
//	}
	
//	var createInput=document.createElement("input");
//	createInput.type="text";
//	createInput.id="tt_input";
//	createInput.readonly="readonly";
//	createInput.onfocus="showTree();";
//	createInput.style.width="100";
////	alert("1=="+left);
//	alert("2=="+createInput.style.left);
//	createInput.style.left="100";
//	alert("3=="+createInput.style.left);
//	createInput.style.top=top;
//	document.body.appendChild(createInput);
	
//	var createDiv=document.createElement("div"); 
//	createDiv.id="menuContent"; 
//	createDiv.class="combTreeDiv";
//	createDiv.style.left=originalControl_offset.left;
//	createDiv.style.top=originalControl_offset.top+createInput.height;
//	document.body.appendChild(createDiv);
//	
//	var createUl=document.createElement("ul");
//	createUl.id="tt";
//	createUl.class="ztree combTreeTree";
//	
//	createDiv.appendChild(createUl);
	//$("#menuContent").css({left:tt_inputOffset.left + "px", top:tt_inputOffset.top + tt_inputObj.outerHeight() + "px"}).slideDown("fast");
}
function loadTree(treeId,treeData,initCallBackFun,beforeClickCallBackFun,onClickCallBackFun){
	decorateHtml(treeId);
	
	my_zTree_beforeClickCallBackFun=beforeClickCallBackFun;
	my_zTree_onClickCallBackFun=onClickCallBackFun;
	my_zTree_initCallBackFun=initCallBackFun;
	//my_zTree_resetCallBackFun=resetCallBackFun;
	
	setting.callback.beforeClick=my_zTree_beforeClick;
	setting.callback.onClick=my_zTree_onClick;
	zTreeObj =$.fn.zTree.init($("#tt"), setting, treeData);
	
	//显示父模块的名字
	initCallBackFun("tt_input");
} 
function hideTree(){
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
	treeShow=false;
}
var treeShow=false;
function showTree(){
	if(treeShow){
		return;
	}
	treeShow=true;
	$("#menuContent").slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
	zTreeObj.expandAll(true);
}
function onBodyDown(event) {
	if (!(event.target.id == "tt_input" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideTree();
	}
}
function my_zTree_beforeClick(treeId, treeNode){
	var result=my_zTree_beforeClickCallBackFun(treeId,treeNode);
	return result;
}
function my_zTree_onClick(e, treeId, treeNode){
	my_zTree_onClickCallBackFun(treeId, treeNode);
	hideTree();
}
function my_zTree_getNodeByID(id){
	return zTreeObj.getNodeByParam("id", id);
}
function my_zTree_getAllChildren(father){
	return father.children;;
}
function my_zTree_reset(){
	hideTree();
	zTreeObj =$.fn.zTree.init($("#tt"), setting, treeData);
	my_zTree_initCallBackFun("tt_input");
}
function my_zTree_getSelectedNodes(){
	return zTreeObj.getSelectedNodes();
}



