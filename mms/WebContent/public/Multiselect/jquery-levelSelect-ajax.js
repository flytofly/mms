/*
通用数据水平层级选择控件
作者：素材火www.sucaihuo.com
版本：v0.70
修改时间：2010年11月22日
要求数据格式：纯文本，数据项之间以","分隔，数据项数值和描述文本之间以":"分隔，可以在参数中自定义分隔符。
*/
;(function($){
//弹出层
$.openLayer = function(p){
	var param = $.extend({
		maxItems : 5,					//最多选取项数字限制
		showLevel : 2,					//显示级别
		oneLevel : false,				//是否限制选择相同级别的数据，可以不为同一个父节点，
										//false为不限制，可以同时选择不同级别的数据，true为限制。
		onePLevel : false,				//是否限制选择相同级别,并且是同一个父节点的数据，
										//false为不限制，可以同时选择不同级别的数据，true为限制。
										//此参数只有在oneLevel:true时才有效
		splitChar : ",:",				//数据分隔符，第一个字符为各项之间的分隔符，第二个为每项中id和显示字符串的分隔符。
		returnValue : "",				//以，分隔的选取结果id存放的位置id，默认为一个input。
		returnText : "",				//以，分隔的选取结果文字存放的位置id，可以为span，div等容器。
		title : "选择地区",				//弹出窗口标题
		width : 850,					//弹出窗口宽度
		span_width : {d1:70,d3:150},	//可以自定义每一层级数据项显示宽度，用来对其排版。
		url : "",						//ajax请求url
		pid : "0",						//父id
		shared : true,					//如果页面中有多于1个的弹出选择,是否共享缓存数据
		index : 1,						//如果页面中有多于1个的弹出选择,如果不共享之前的操作界面则必须设置不同的index值，否则不同的弹出选择共享相同的操作界面。
		cacheEnable : true,				//是否允许缓存
		dragEnable : true,				//是否允许鼠标拖动
		pText : ""
	},p||{});
   var count=0;
	var fs = {
		init_Container : function(){	//初始化头部和内容容器
			//标题
			var TITLE = param.title ;
			var CLOSE = "<span id='_cancel' style='cursor:pointer;'>[取消]</span>&nbsp;&nbsp;<span id='_ok' style='cursor:pointer;'>[确定]</span>";
			//头部
			var htmlDiv = "<div id='heads'><div id='headdiv'><span id='title'>" + TITLE + "</span><span id='close'>" + CLOSE + "</span></div>";
			//内容容器创建部分
			htmlDiv += "<div id='container'></div></div>";
			return htmlDiv;
		},
		init_area : function(){			//初始化数据容器
			var _container = $("#container");
			//已选择项容器
			var selArea = $("<div id='selArea'><div>已选择项目：</div></div>");
			_container.append(selArea); 
			if (param.maxItems == 1){ selArea.hide(); }

			//初始化第一层级数据容器，以后各级容器都clone本容器
			var d1 = $("<div id='d1'></div>");
			var dc = $("<div id='dc'></div>");
			_container.append(dc).append(d1);//加入数据容器中
			dc.hide();
			fs.add_data(d1);//添加数据
			
		},
		add_data : function(targetid){					//添加数据到容器，添加事件，初始化下一层次容器
			targetid.nextAll().remove();				//删除目标容器之后的所有同级别容器
       
			var pid = param.pid;						//查询数据的参数，父id
			var url = param.url;						//ajax查询url
			var data = "";								//返回数据变量
			count++;
			if(param.cacheEnable){ data = _cache[pid];}	//如果cache开启则首先从cache中取得数据
			
			//如果cache中没有数据并且url和pid都设置了,发起ajax请求
			if ((data == null || data == "") &&  url != ""){
				$.ajax({
					type : "post",						//post方式
					url : url,							//ajax查询url
					data : {pid:pid},					//参数
					async : false,						//同步方式，便于拿到返回数据做统一处理
					beforeSend : function (){ },		//ajax查询请求之前动作，比如提示信息……
					success : function (d) {			//ajax请求成功后返回数据
						data = d;
						if(param.cacheEnable){ _cache[pid] = data;}		//cache允许,保存数据到cache
					}
				});
			}

			//cache和ajax都没有数据或者错误,添加提示信息返回
			if(data == "" || data == null){
				targetid.empty().show().append($("<span style='color:red;'>没有下级数据！</span>"));
				return;
			}

			var span_width = eval("param.span_width."+targetid.attr("id"));			//每个数据显示项的宽度
			span_width = (span_width == undefined ? param.span_width.d1:span_width );//没有设置的话，就使用第一个数据容器的值
			var inspan_width = 3;								//内部文字和checkbox之间的距离
	
			var dat = data.split(param.splitChar.charAt(0));						//根据设定分隔符对数据做第一次分隔，获得数据项数组
			var html = [];															//格式化数据存放容器，为了提高效率，使用了数组
			var ss = [];
			//循环获得格式化的显示字符串
			if(count==1){
				html.push("<span   style='width:"+span_width+"px;white-space:nowrap;float:left;'>");
				html.push("<input type='checkbox' value='全国 ' >");
				html.push("<span  style='margin-left:"+inspan_width+"px;'>全国</span>");
				html.push("</span>");
				 	
			}
			
			for(var i = 0 ; i < dat.length ; i++){
				ss = dat[i].split(param.splitChar.charAt(1));		//第二次分隔，获得每个数据项中的数据值和显示字符串
				html.push("<span title='"+dat[i]+"' name='"+pid+"' style='width:"+span_width+"px;white-space:nowrap;float:left;'>");
				html.push("<input type='checkbox' value='" + ss[0] + "'>");
				html.push("<span name='"+targetid.attr("id")+"' style='margin-left:"+inspan_width+"px;'>" + ss[1] + "</span>");
				html.push("</span>");
			}
			targetid.empty().show().append($(html.join("")));		//格式化的html代码放入目标容器
			if(param.maxItems > 1){fs.change_status(targetid);}		//同步状态,单选状态无必要
				
			fs.add_input_event(targetid);							//加入input的事件绑定
			fs.add_span_event(targetid);							//加入span的事件绑定
		},
		init_event : function(){		//绑定已选择框中checkbox的事件，确定，取消事件响应
			// $("#selArea").find(":input").live("click",function(){
			$("#selArea").find(":input").on("click",function(){
				$(this).parent().remove();
				$("#container > div").find(":input[value="+this.value+"]").prop("checked",false);
			});
			$("#_cancel").click(function(){
				fs.set_returnVals(param.returnValue,"");
				fs.set_returnVals(param.returnText,"");
				
				$("#bodybg").remove();
				$("#popupAddr").remove();
			});
			$("#_ok").click(function(){
				var vals = "";
				var txts = "";
				$("#selArea").find(":input").each(function(i){
					vals += ("," + this.value);
					txts += ("," + $(this).next().text());
				});
				fs.set_returnVals(param.returnValue,vals);
				fs.set_returnVals(param.returnText,txts);
		
				$("#bodybg").hide();
				$("#popupAddr").fadeOut();
			});
		},
		change_status : function(targetid){ //切换不同元素，形成不同下级列表时候，同步已选择区的元素和新形成区元素的选中状态
			var selArea = $("#selArea");
			var selinputs = selArea.find(":input");
			var vals =[];

			if(selinputs.length > 0){
				selinputs.each(function(){ vals.push(this.value); });
			}
			targetid.find(":input").each(function(){
				if($.inArray(this.value,vals) != -1){ this.checked = true; }
			});
		},
		add_input_event : function(targetid){	//新生成的元素集合添加input的单击事件响应
			var selArea = $("#selArea");
			targetid.find(":input").click(function(){
				if (param.maxItems == 1){
					selArea.find("span").remove();
					$("#container > div").find(":checked:first").not($(this)).attr("checked",false);
					$(this).css("color","white");
					selArea.append($(this).parent().clone());
					$("#_ok").click();
				}else {
					//原有的判断流程
//					if(this.checked && fs.check_level(this) && fs.check_num(this)){
//						selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
//					}else{
//						selArea.find(":input[value="+this.value+"]").parent().remove();
//					}	
					
					var thislevel = parseInt(targetid.attr("id").substring(1));
					if(thislevel==1){
						if( fs.check_level(this) && fs.check_num(this)){
							$(this).next().click();
							
							if(this.checked){ 
								if($.trim($(this).val())=="全国"){
									// 选中省份则选中所有省
									$("#d1 input[type='checkbox']").each(function(){
											$(this).prop("checked", true); 
											//selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
											$(this).next().click();
											$("#d2 input[type='checkbox']").each(function(){
												$(this).prop("checked", true); 
												selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
										     });
									});
								}else{
									// 选中省份则选中所有地级市
									$("#d2 input[type='checkbox']").each(function(){
											$(this).prop("checked", true); 
											selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
									});
								}
								
							}else{
								
										if($.trim($(this).val())=="全国"){
											// 选中省份则选中所有省
											$("#d1 input[type='checkbox']").each(function(){
													$(this).prop("checked", false); 
													//selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
													$(this).next().click();
													$("#d2 input[type='checkbox']").each(function(){
														$(this).prop("checked", false); 
														selArea.find(":input[value="+this.value+"]").parent().remove();
												     });
											});
										}else{
												// 取消选中省份则取消所有选中地级市
											$("#d2 input[type='checkbox']").each(function(){
												$(this).prop("checked", false);
												selArea.find(":input[value="+this.value+"]").parent().remove();
											});
										}
							 }
						}
						
					}else if(thislevel=2){
						if(this.checked ){
							selArea.append($(this).parent().clone().css({"width":"","background":"","border":""}));
						}else{
							selArea.find(":input[value="+this.value+"]").parent().remove();
						}
					}
				}
			});
		},
		add_span_event : function(targetid){	//新生成的元素集合添加span的单击事件响应
			var maxlev = param.showLevel;
			var thislevel = parseInt(targetid.attr("id").substring(1));
	
			var spans = targetid.children("span");
			spans.children("span").click(function(e){
				if (maxlev > thislevel){
					var next=$("#dc").clone();
					next.attr("id","d"+(thislevel+1));
					targetid.after(next);
			
					spans.css({"background":"","border":"","margin":""});
					$(this).parent().css({"background":"orange","border":"0px ridge","margin":"-1"});
					param.pid = $(this).prev().val();
					fs.add_data(next,param);
				}else{
					//alert("当前设置只允许显示" +  maxlev + "层数据！");
				}
			});
		},
		check_num : function(obj){	//检测最多可选择数量
			if($("#selArea").find(":input").size() < param.maxItems){
				return true;
			}else{
				obj.checked = false;
				alert("最多只能选择"+param.maxItems+"个选项");
				return false;
			}
		},
		check_level : function(obj){	//检测是否允许选取同级别选项或者同父id选项
			var selobj = $("#selArea > span");
			if(selobj.length ==0) return true;

			var oneLevel = param.oneLevel;
			if(oneLevel == false){
				return true;
			}else{
				var selLevel = selobj.find("span:first").attr("name");		//已选择元素的级别
				var thislevel = $(obj).next().attr("name");					//当前元素级别
				if(selLevel != thislevel) {
					obj.checked = false;
					alert("当前设定只允许选择同一级别的元素！");
					return  false;
				}else{
					var onePLevel = param.onePLevel;		//是否设定只允许选择同一父id的同级元素
					if (onePLevel == false) {
						return true;
					}else{
						var parentId = selobj.attr("name");					//已选择元素的父id
						var thisParentId = $(obj).parent().attr("name");	//当前元素父id
						if (parentId != thisParentId){
							obj.checked = false;
							alert("当前设定只允许选择同一级别并且相同上级的元素！");
							return false;
						}
						return true;
					}
				}
			}
		},
		set_returnVals : function(id,vals) {	//按"确定"按钮时处理、设置返回值
			if(id != ""){
				var Container = $("#" + id);
				if(Container.length > 0){
					if(Container.is("input")){
						Container.val(vals.substring(1));
					}else{
						Container.text(vals.substring(1));
					}
				}
			}	
		},
		init_style : function() {	//初始化css
			var _margin = 4;
			var _width = param.width-_margin*5;

			var css = [];
			var aotu = "border:2px groove";
			css.push("#popupAddr {position:absolute;border:3px ridge;width:"+param.width+"px;height:auto;background-color:#e3e3e3;z-index:99;-moz-box-shadow:5px 5px 5px rgba(0,0,0,0.5);box-shadow:5px 5px 5px rgba(0,0,0,0.5);filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=5,OffY=5,Color=gray);-ms-filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=5,OffY=5,Color='gray');}");
			css.push("#bodybg {width:100%;z-index:98;position:absolute;top:0;left:0;background-color:#fff;opacity:0.1;filter:alpha(opacity =10);}");
			css.push("#heads {width:100%;font-size:12px;margin:0 auto;}");
			css.push("#headdiv {color:white;background-color:green;font-size:13px;height:25px;margin:1px;" +aotu+"}");
			css.push("#title {line-height:30px;padding-left:20px;float:left;}");
			css.push("#close {float:right;padding-right:12px;line-height:30px;}");
			css.push("#container {width:100%;height:auto;}");
			css.push("#selArea {width:"+_width+"px;height:auto;margin:"+_margin+"px;padding:5px;background-color:#f4f4f4;float:left;"+aotu+"}");
			css.push("#pbar {width:"+_width+"px;height:12px;margin:4px;-moz-box-sizing: border-box;display:block;overflow: hidden;font-size:1px;border:1px solid red;background:#333333;float:left;}");
	        
			var d_css = "{width:"+_width+"px;margin:"+_margin+"px;padding:5px;height:auto;background-color:khaki;float:left;"+aotu+"}";
			css.push("dc "+d_css);
			for (i = 1; i <=param.showLevel; i++) { css.push("#d" + i + " " + d_css); }
			$("head").append($("<style>"+css.join(" ")+"</style>"));
		}
	};

	if (window._cache == undefined || !param.shared ){ _cache = {}; }
	if (window._index == undefined) { _index = param.index; }

	fs.init_style();//初始化样式

	var popupDiv = $("#popupAddr");	//创建一个div元素
	if (popupDiv.length == 0 ) {
		popupDiv = $("<div id='popupAddr'></div>");
		$("body").append(popupDiv);
	}
	var yPos = ($(window).height()-popupDiv.height()) / 2;
	var xPos = ($(window).width()-popupDiv.width()) / 2;
	popupDiv.css({"top": yPos,"left": xPos}).show();
	
	var bodyBack = $("#bodybg");  //创建背景层
	if (bodyBack.length == 0 ) {
		bodyBack = $("<div id='bodybg'></div>");
		bodyBack.height($(window).height());
		$("body").append(bodyBack);
		popupDiv.html(fs.init_Container());	//弹出层内容
		fs.init_area();
		fs.init_event();
	}else {
		if (_index != param.index) {
			popupDiv.html(fs.init_Container(param));
			fs.init_area();
			fs.init_event();
			_index = param.index;
		}
	}

	if (param.dragEnable) {		//允许鼠标拖动
		var _move=false;		//移动标记
		var _x,_y;				//鼠标离控件左上角的相对位置
		popupDiv.mousedown(function(e){
			_move=true;
			_x=e.pageX-parseInt(popupDiv.css("left"));
			_y=e.pageY-parseInt(popupDiv.css("top"));
		}).mousemove(function(e){
			if(_move){
				var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置
				var y=e.pageY-_y;
				popupDiv.css({top:y,left:x});//控件新位置
		}}).mouseup(function(){ _move=false; });
	}
	bodyBack.show();
	popupDiv.fadeIn();
}

})(jQuery)

/*_cache ={"120000":"120100:天津市","450000":"450400:梧州市,450800:贵港市,451200:河池市,450300:桂林市,450700:钦州市,451100:贺州市,450200:柳州市,450600:防城港市,451000:百色市,451400:崇左市,450100:南宁市,450500:北海市,450900:玉林市,451300:来宾市","140000":"140300:阳泉市,140700:晋中市,141100:吕梁市,140200:大同市,140600:朔州市,141000:临汾市,140100:太原市,140500:晋城市,140900:忻州市,140400:长治市,140800:运城市","630000":"632200:海北藏族自治州,632700:玉树藏族自治州,630200:海东市,632600:果洛藏族自治州,630100:西宁市,632500:海南藏族自治州,632300:黄南藏族自治州,632800:海西蒙古族藏族自治州","440000":"440100:广州市,440500:汕头市,440900:茂名市,441500:汕尾市,441900:东莞市,445300:云浮市,440400:珠海市,440800:湛江市,441400:梅州市,441800:清远市,445200:揭阳市,440300:深圳市,440700:江门市,441300:惠州市,441700:阳江市,445100:潮州市,440200:韶关市,440600:佛山市,441200:肇庆市,441600:河源市,442000:中山市","430000":"430300:湘潭市,430700:常德市,431100:永州市,430200:株洲市,430600:岳阳市,431000:郴州市,433100:湘西土家族苗族自治州,430100:长沙市,430500:邵阳市,430900:益阳市,431300:娄底市,430400:衡阳市,430800:张家界市,431200:怀化市","620000":"620100:兰州市,620500:天水市,620900:酒泉市,622900:临夏回族自治州,620400:白银市,620800:平凉市,621200:陇南市,620300:金昌市,620700:张掖市,621100:定西市,620200:嘉峪关市,620600:武威市,621000:庆阳市,623000:甘南藏族自治州","640000":"640300:吴忠市,640200:石嘴山市,640100:银川市,640500:中卫市,640400:固原市","230000":"230200:齐齐哈尔市,230600:大庆市,231000:牡丹江市,230100:哈尔滨市,230500:双鸭山市,230900:七台河市,232700:大兴安岭地区,230400:鹤岗市,230800:佳木斯市,231200:绥化市,230300:鸡西市,230700:伊春市,231100:黑河市","410000":"410200:开封市,410600:鹤壁市,411000:许昌市,411400:商丘市,419001:济源市,410100:郑州市,410500:安阳市,410900:濮阳市,411300:南阳市,411700:驻马店市,410400:平顶山市,410800:焦作市,411200:三门峡市,411600:周口市,410300:洛阳市,410700:新乡市,411100:漯河市,411500:信阳市","330000":"330200:宁波市,330600:绍兴市,331000:台州市,330100:杭州市,330500:湖州市,330900:舟山市,330400:嘉兴市,330800:衢州市,330300:温州市,330700:金华市,331100:丽水市","510000":"510500:泸州市,510900:遂宁市,511400:眉山市,511800:雅安市,513300:甘孜藏族自治州,510400:攀枝花市,510800:广元市,511300:南充市,511700:达州市,513200:阿坝藏族羌族自治州,510300:自贡市,510700:绵阳市,511100:乐山市,511600:广安市,512000:资阳市,510100:成都市,510600:德阳市,511000:内江市,511500:宜宾市,511900:巴中市,513400:凉山彝族自治州","530000":"530300:曲靖市,530700:丽江市,532500:红河哈尼族彝族自治州,533100:德宏傣族景颇族自治州,530100:昆明市,530600:昭通市,532300:楚雄彝族自治州,532900:大理白族自治州,530500:保山市,530900:临沧市,532800:西双版纳傣族自治州,533400:迪庆藏族自治州,530400:玉溪市,530800:普洱市,532600:文山壮族苗族自治州,533300:怒江傈僳族自治州","210000":"210400:抚顺市,210800:营口市,211300:朝阳市,210300:鞍山市,210700:锦州市,211200:铁岭市,210200:大连市,210600:丹东市,211000:辽阳市,211100:盘锦市,210100:沈阳市,210500:本溪市,210900:阜新市,211400:葫芦岛市","130000":"130200:唐山市,130600:保定市,131000:廊坊市,130100:石家庄市,130500:邢台市,130900:沧州市,130400:邯郸市,130800:承德市,130300:秦皇岛市,130700:张家口市,131100:衡水市","340000":"340300:蚌埠市,340700:铜陵市,341200:阜阳市,341700:池州市,340200:芜湖市,340600:淮北市,341100:滁州市,341600:亳州市,340100:合肥市,340500:马鞍山市,341000:黄山市,341500:六安市,340400:淮南市,340800:安庆市,341300:宿州市,341800:宣城市","500000":"500100:重庆市","350000":"350300:莆田市,350700:南平市,350200:厦门市,350600:漳州市,350100:福州市,350500:泉州市,350900:宁德市,350400:三明市,350800:龙岩市","320000":"320300:徐州市,320700:连云港市,321100:镇江市,320200:无锡市,320600:南通市,321000:扬州市,321300:宿迁市,320100:南京市,320500:苏州市,320900:盐城市,320400:常州市,320800:淮安市,321200:泰州市","220000":"220300:四平市,220700:松原市,220200:吉林市,220600:白山市,220100:长春市,220500:通化市,222400:延边朝鲜族自治州,220400:辽源市,220800:白城市","310000":"310100:上海市","650000":"650200:克拉玛依市,652700:博尔塔拉蒙古自治州,653100:喀什地区,654300:阿勒泰地区,659004:五家渠市,650100:乌鲁木齐市,652300:昌吉回族自治州,653000:克孜勒苏柯尔克孜自治州,654200:塔城地区,659003:图木舒克市,652200:哈密地区,652900:阿克苏地区,654000:伊犁哈萨克自治州,659002:阿拉尔市,652100:吐鲁番地区,652800:巴音郭楞蒙古自治州,653200:和田地区,659001:石河子市","150000":"150400:赤峰市,150800:巴彦淖尔市,152900:阿拉善盟,150300:乌海市,150700:呼伦贝尔市,152500:锡林郭勒盟,150200:包头市,150600:鄂尔多斯市,152200:兴安盟,150100:呼和浩特市,150500:通辽市,150900:乌兰察布市","610000":"610300:宝鸡市,610700:汉中市,610200:铜川市,610600:延安市,611000:商洛市,610100:西安市,610500:渭南市,610900:安康市,610400:咸阳市,610800:榆林市","540000":"540200:日喀则市,542500:阿里地区,540100:拉萨市,542400:那曲地区,542200:山南地区,542100:昌都地区,542600:林芝地区","360000":"360200:景德镇市,360600:鹰潭市,361000:抚州市,360100:南昌市,360500:新余市,360900:宜春市,360400:九江市,360800:吉安市,360300:萍乡市,360700:赣州市,361100:上饶市","0":"110000:北京市,120000:天津市,130000:河北省,140000:山西省,150000:内蒙古自治区,210000:辽宁省,220000:吉林省,230000:黑龙江省,310000:上海市,320000:江苏省,330000:浙江省,340000:安徽省,350000:福建省,360000:江西省,370000:山东省,410000:河南省,420000:湖北省,430000:湖南省,440000:广东省,450000:广西壮族自治区,460000:海南省,500000:重庆市,510000:四川省,520000:贵州省,530000:云南省,540000:西藏自治区,610000:陕西省,620000:甘肃省,630000:青海省,640000:宁夏回族自治区,650000:新疆维吾尔自治区","420000":"420500:宜昌市,420900:孝感市,421300:随州市,429006:天门市,420300:十堰市,420800:荆门市,421200:咸宁市,429005:潜江市,420200:黄石市,420700:鄂州市,421100:黄冈市,429004:仙桃市,420100:武汉市,420600:襄阳市,421000:荆州市,422800:恩施土家族苗族自治州,429021:神农架林区","520000":"520300:遵义市,522300:黔西南布依族苗族自治州,520200:六盘水市,520600:铜仁市,520100:贵阳市,520500:毕节市,522700:黔南布依族苗族自治州,520400:安顺市,522600:黔东南苗族侗族自治州","370000":"370300:淄博市,370700:潍坊市,371100:日照市,371500:聊城市,370200:青岛市,370600:烟台市,371000:威海市,371400:德州市,370100:济南市,370500:东营市,370900:泰安市,371300:临沂市,371700:菏泽市,370400:枣庄市,370800:济宁市,371200:莱芜市,371600:滨州市","110000":"110100:北京市","460000":"460200:三亚市,460100:海口市,460300:三沙市"};
*/
_cache ={"120000":"120100:天津市","450000":"450400:梧州市,450800:贵港市,451200:河池市,450300:桂林市,450700:钦州市,451100:贺州市,450200:柳州市,450600:防城港市,451000:百色市,451400:崇左市,450100:南宁市,450500:北海市,450900:玉林市,451300:来宾市","140000":"140300:阳泉市,140700:晋中市,141100:吕梁市,140200:大同市,140600:朔州市,141000:临汾市,140100:太原市,140500:晋城市,140900:忻州市,140400:长治市,140800:运城市","630000":"632200:海北藏族自治州,632700:玉树藏族自治州,630200:海东市,632600:果洛藏族自治州,630100:西宁市,632500:海南藏族自治州,632300:黄南藏族自治州,632800:海西蒙古族藏族自治州","440000":"440100:广州市,440500:汕头市,440900:茂名市,441500:汕尾市,441900:东莞市,445300:云浮市,440400:珠海市,440800:湛江市,441400:梅州市,441800:清远市,445200:揭阳市,440300:深圳市,440700:江门市,441300:惠州市,441700:阳江市,445100:潮州市,440200:韶关市,440600:佛山市,441200:肇庆市,441600:河源市,442000:中山市","430000":"430300:湘潭市,430700:常德市,431100:永州市,430200:株洲市,430600:岳阳市,431000:郴州市,433100:湘西土家族苗族自治州,430100:长沙市,430500:邵阳市,430900:益阳市,431300:娄底市,430400:衡阳市,430800:张家界市,431200:怀化市","620000":"620100:兰州市,620500:天水市,620900:酒泉市,622900:临夏回族自治州,620400:白银市,620800:平凉市,621200:陇南市,620300:金昌市,620700:张掖市,621100:定西市,620200:嘉峪关市,620600:武威市,621000:庆阳市,623000:甘南藏族自治州","640000":"640300:吴忠市,640200:石嘴山市,640100:银川市,640500:中卫市,640400:固原市","230000":"230200:齐齐哈尔市,230600:大庆市,231000:牡丹江市,230100:哈尔滨市,230500:双鸭山市,230900:七台河市,232700:大兴安岭地区,230400:鹤岗市,230800:佳木斯市,231200:绥化市,230300:鸡西市,230700:伊春市,231100:黑河市","410000":"410200:开封市,410600:鹤壁市,411000:许昌市,411400:商丘市,419001:济源市,410100:郑州市,410500:安阳市,410900:濮阳市,411300:南阳市,411700:驻马店市,410400:平顶山市,410800:焦作市,411200:三门峡市,411600:周口市,410300:洛阳市,410700:新乡市,411100:漯河市,411500:信阳市","330000":"330200:宁波市,330600:绍兴市,331000:台州市,330100:杭州市,330500:湖州市,330900:舟山市,330400:嘉兴市,330800:衢州市,330300:温州市,330700:金华市,331100:丽水市","510000":"510500:泸州市,510900:遂宁市,511400:眉山市,511800:雅安市,513300:甘孜藏族自治州,510400:攀枝花市,510800:广元市,511300:南充市,511700:达州市,513200:阿坝藏族羌族自治州,510300:自贡市,510700:绵阳市,511100:乐山市,511600:广安市,512000:资阳市,510100:成都市,510600:德阳市,511000:内江市,511500:宜宾市,511900:巴中市,513400:凉山彝族自治州","530000":"530300:曲靖市,530700:丽江市,532500:红河哈尼族彝族自治州,533100:德宏傣族景颇族自治州,530100:昆明市,530600:昭通市,532300:楚雄彝族自治州,532900:大理白族自治州,530500:保山市,530900:临沧市,532800:西双版纳傣族自治州,533400:迪庆藏族自治州,530400:玉溪市,530800:普洱市,532600:文山壮族苗族自治州,533300:怒江傈僳族自治州","210000":"210400:抚顺市,210800:营口市,211300:朝阳市,210300:鞍山市,210700:锦州市,211200:铁岭市,210200:大连市,210600:丹东市,211000:辽阳市,211100:盘锦市,210100:沈阳市,210500:本溪市,210900:阜新市,211400:葫芦岛市","130000":"130200:唐山市,130600:保定市,131000:廊坊市,130100:石家庄市,130500:邢台市,130900:沧州市,130400:邯郸市,130800:承德市,130300:秦皇岛市,130700:张家口市,131100:衡水市","340000":"340300:蚌埠市,340700:铜陵市,341200:阜阳市,341700:池州市,340200:芜湖市,340600:淮北市,341100:滁州市,341600:亳州市,340100:合肥市,340500:马鞍山市,341000:黄山市,341500:六安市,340400:淮南市,340800:安庆市,341300:宿州市,341800:宣城市","500000":"500100:重庆市","350000":"350300:莆田市,350700:南平市,350200:厦门市,350600:漳州市,350100:福州市,350500:泉州市,350900:宁德市,350400:三明市,350800:龙岩市","320000":"320300:徐州市,320700:连云港市,321100:镇江市,320200:无锡市,320600:南通市,321000:扬州市,321300:宿迁市,320100:南京市,320500:苏州市,320900:盐城市,320400:常州市,320800:淮安市,321200:泰州市","220000":"220300:四平市,220700:松原市,220200:吉林市,220600:白山市,220100:长春市,220500:通化市,222400:延边朝鲜族自治州,220400:辽源市,220800:白城市","310000":"310100:上海市","650000":"650200:克拉玛依市,652700:博尔塔拉蒙古自治州,653100:喀什地区,654300:阿勒泰地区,659004:五家渠市,650100:乌鲁木齐市,652300:昌吉回族自治州,653000:克孜勒苏柯尔克孜自治州,654200:塔城地区,659003:图木舒克市,652200:哈密地区,652900:阿克苏地区,654000:伊犁哈萨克自治州,659002:阿拉尔市,652100:吐鲁番地区,652800:巴音郭楞蒙古自治州,653200:和田地区,659001:石河子市","150000":"150400:赤峰市,150800:巴彦淖尔市,152900:阿拉善盟,150300:乌海市,150700:呼伦贝尔市,152500:锡林郭勒盟,150200:包头市,150600:鄂尔多斯市,152200:兴安盟,150100:呼和浩特市,150500:通辽市,150900:乌兰察布市","610000":"610300:宝鸡市,610700:汉中市,610200:铜川市,610600:延安市,611000:商洛市,610100:西安市,610500:渭南市,610900:安康市,610400:咸阳市,610800:榆林市","540000":"540200:日喀则市,542500:阿里地区,540100:拉萨市,542400:那曲地区,542200:山南地区,542100:昌都地区,542600:林芝地区","360000":"360200:景德镇市,360600:鹰潭市,361000:抚州市,360100:南昌市,360500:新余市,360900:宜春市,360400:九江市,360800:吉安市,360300:萍乡市,360700:赣州市,361100:上饶市","0":"110000:北京市,120000:天津市,130000:河北省,140000:山西省,150000:内蒙古自治区,210000:辽宁省,220000:吉林省,230000:黑龙江省,310000:上海市,320000:江苏省,330000:浙江省,340000:安徽省,350000:福建省,360000:江西省,370000:山东省,410000:河南省,420000:湖北省,430000:湖南省,440000:广东省,450000:广西壮族自治区,460000:海南省,500000:重庆市,510000:四川省,520000:贵州省,530000:云南省,540000:西藏自治区,610000:陕西省,620000:甘肃省,630000:青海省,640000:宁夏回族自治区,650000:新疆维吾尔自治区","420000":"420500:宜昌市,420900:孝感市,421300:随州市,429006:天门市,420300:十堰市,420800:荆门市,421200:咸宁市,429005:潜江市,420200:黄石市,420700:鄂州市,421100:黄冈市,429004:仙桃市,420100:武汉市,420600:襄阳市,421000:荆州市,422800:恩施土家族苗族自治州,429021:神农架林区","520000":"520300:遵义市,522300:黔西南布依族苗族自治州,520200:六盘水市,520600:铜仁市,520100:贵阳市,520500:毕节市,522700:黔南布依族苗族自治州,520400:安顺市,522600:黔东南苗族侗族自治州","370000":"370300:淄博市,370700:潍坊市,371100:日照市,371500:聊城市,370200:青岛市,370600:烟台市,371000:威海市,371400:德州市,370100:济南市,370500:东营市,370900:泰安市,371300:临沂市,371700:菏泽市,370400:枣庄市,370800:济宁市,371200:莱芜市,371600:滨州市","110000":"110100:北京市","460000":"460200:三亚市,460100:海口市,460300:三沙市"};

	
	