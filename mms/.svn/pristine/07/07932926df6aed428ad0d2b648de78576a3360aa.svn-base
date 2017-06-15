$(function(){
	//页面加载完成之后执行
	pageInit('');
	pageInit2();
	
	var Rowdata;
	//td 点击事件
	$("#list td[role=gridcell]").live("click", function(){
	    selectID=$("#list").getGridParam("selrow");
		var arrays=$("#list2").getDataIDs();
		if(selectID!=null){
			var str="目标不存在";
			for(var i=0; i<arrays.length ; i++){
				if(selectID==arrays[i]){
					alert("目标已存在")
					str="目标已存在";
					 break;
				}
			};	
			if(str=="目标不存在"){
			  var Rowdata=$("#list").getRowData(selectID);
			  $("#list2").jqGrid("addRowData",selectID,Rowdata, "first"); 
			 }   
		}
    });
	
	$("#list2 td[role=gridcell]").live("click", function(){
     
		  selectID=$("#list2").getGridParam("selrow"); 
        $("#list2").jqGrid("delRowData", selectID); 
    });
});

//规则数据的加载
function pageInit(tag){
	  jQuery("#list").jqGrid(
	      {
	        url : '/mms/tagquery/queryRules?tag='+ tag,
	        datatype : "json",
	        autoencode : "true",
	        colNames : ['规则ID','规则预估数据量' ],
	        colModel : [ 
	                     {name : 'id',index : 'id',width : '400px',align:'center',sorttype : "int"}, 
	                     {name : 'rulenum',index : 'rulenum',width : '400px',align:'center',sorttype : "int"} 
	                   ],
	        rowNum : 20,
	        rowList : [ 10, 20, 30 ],
	        pager : '#pager',
	        sortname : 'id',
			mtype : "post",//向后台请求数据的ajax的类型。可选post,get
	        viewrecords : true,
	        sortorder : "desc",
	        multiselect: true,  //checkbox
	        loadonce : true,
  });
}


function pageInit2(){
	//创建jqGrid组件
	jQuery("#list2").jqGrid(
			{
				url : '',// 组件创建完成之后请求数据的url
				datatype : "json",// 请求数据返回的类型。可选json,xml,txt
				colNames : [ '规则ID', '预估日用户数' ],//jqGrid的列显示名字
				colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
				             {name : 'id',index : 'id',align : "center",width : 100}, 
				             {name : 'rulenum',index : 'rulenum',align : "center",width : 130}, 
				           ],
				           
				rowNum : 10,// 一页显示多少条
				pager : '#pager2',// 表格页脚的占位符(一般是div)的id
				sortname : 'id',//初始化的时候排序的字段
				sortorder : "desc",//排序方式,可选desc,asc
				mtype : "post",//向后台请求数据的ajax的类型。可选post,get
				viewrecords : true,
				height : 400,
				pginput : false,
			    multiselect: true,  //checkbox
			});
	/*创建jqGrid的操作按钮容器*/
	/*可以控制界面上增删改查的按钮是否显示*/
	jQuery("#list2").jqGrid('navGrid', '#pager2', {edit : false,add : false,del : false});
}

