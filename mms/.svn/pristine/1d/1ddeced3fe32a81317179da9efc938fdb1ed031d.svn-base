function xjrw() {
	$("#xjrw").show();
}
function xjrq() {
	$("#xjrq").show();
}
function xjrq2() {
	$("#xjrq2").show();
}
$(document).ready(function() {
	/*头部导航*/
	var shhi = 0;
	$(".tx-top-nav .le > li").click(function() {
		if($(this).find("ol").css("display") != "none") {
			$(this).find("ol").hide();
			$(this).find("ol").css("top", "0px");
		} else {
			shhi = $(this).index();
			$(this).find("ol").show();
			$(this).find("ol").animate({ "top": "50px" }, 700);
			$(this).siblings("li").find("ol").hide();
			$(this).siblings("li").find("ol").css("top", "0px")
		}
	});
	$(".tx-top-nav .le > li").hover(function(){
	},function(){
		$(this).find("ol").hide();$(this).find("ol").css("top", "0px");
	});
	/*$(".tx-top-nav .le > li").css("height",$(".tx-top-nav .le > li").height()+$(".tx-top-nav .le > li ol").height());
	console.log($(".tx-top-nav .le > li").height());*/
	/*全选*/
	$(".tx-con-xx th input").click(function() {
		if(this.checked == true) {
			$(".tx-con-xx td input").each(function() {
				this.checked = true;
			});
		} else {
			$(".tx-con-xx td input").each(function() {
				this.checked = false;
			});
		}
	});
	/*新建任务弹窗*/
	$(".xjrw-ale-con-bt em").click(function() {
		$(this).parents(".xjrw-ale").hide();
	});

	/*设定登录页面高度*/
	$(".login-con").css("height", $(window).height());
	/*设定登录页面内容区域距离顶部的高度*/
	var henum = $(window).height() - $(".login-con-con").height();
	$(".login-con-con").css("padding-top", henum / 2 - 10);
	/*设定弹出框内容区域距离顶部的高度*/
	var tophe = $(window).height() - $(".xjrw-ale-con").height();
	var topwi = $(window).width() - $(".xjrw-ale-con").width();
	$(".xjrw-ale-con").css("top",tophe/6);
	$(".xjrw-ale-con").css("left",topwi/2);
	/*弹出框的高度*/
	$(".xjrw-ale").css("height",$("html").height());
	/************ 规则列表 开始*************/
		/*判断li下是否有列表 并显示下面的条说*/
	for (var i = 0; i < $(".sel-li").length; i++) {
		$(".sel-li").eq(i).find("> span").text($(".sel-li").eq(i).find("> span").text()+" "+"("+$(".sel-li").eq(i).find("li").length+")");
	}

	for (var i = 0; $(".sel-li li").length>i; i++) {
		if ($(".sel-li li").eq(i).find("ul").length>0) {
			$(".sel-li li").eq(i).addClass("sel-li-hov");
			var tex = $(".sel-li li").eq(i).find("> span").text();
			var texnum = 0;
			for (var k = 0; $(".sel-li li").eq(i).find("li").length>k; k++) {
				if ($(".sel-li li").eq(i).find("li").eq(k).prop("class")!="sel-li-hov") {
					texnum++;
				} 
			}
			$(".sel-li li").eq(i).find("> span").text(tex+" "+"("+texnum+")");
			
		} 
	}
		/*判断li下是否有列表 并显示下面的条说  结束*/
	$("#sel li.sel-li").addClass("ico_close");	
	$("#sel li.sel-li ul").css("display","none");
	/**箭头显示隐藏**/
	$("#sel li.sel-li-hov").hover(function(){
		if ($(this).hasClass("ico_open")) {
			$(this).addClass("ico_open");
		} else {
			$(this).addClass("ico_close");
		}
	},function(){
		$(this).removeClass("ico_close");
	})
		/*点击显示事件*/
	$("#sel li span").click(function(){
		if ($(this).parent("li").find("> ul").css("display")!="block"&&$(this).parent("li").find("> ul").length!=0) {
			$(this).parent("li").find("> ul").show(300);
			$(this).parent("li").addClass("ico_open");
			$(this).parent("li").removeClass("ico_close");
		} else if($(this).parent("li").find("> ul").css("display")=="block"&&$(this).parent("li").find("> ul").length!=0){
			$(this).parent("li").find("> ul").hide(300);
			$(this).parent("li").addClass("ico_close");
			$(this).parent("li").removeClass("ico_open");
		}
	})	
		/*获取内容条数*/
	$("#sel .sel-bt font").text($("#sel .sel-li li").length);	
		/*选择文本*/
	$("#sel li").click(function(){
		if ($(this).hasClass("sel-li-hov")||$(this).hasClass("sel-li")) {
			
		} else {
			var tex = $(this).parent().parent("li.sel-li-hov").find("> span").text();
			if ($(this).hasClass("sel-li-bj")) {
				$(this).removeClass("sel-li-bj");
			} else {
				$("#sel li").removeClass("sel-li-bj");
				$(this).addClass("sel-li-bj");

			}
			$(this).attr("data",tex);
		}
	});
		/*移动*/
	$(".xjrq-con-but li").click(function(){
			/*向右移动*/
		if($(this).index()==0){
			var num = $("#sel li.sel-li-bj").length;
			
			if ($("#sel li").hasClass("sel-li-bj")&&$("#alert-sel2 li").length>0) {
				/*验证*/
				for (var i = 0; i < $("#alert-sel2 li").length; i++) {
					/*验证左右数据是否相等*/
						if ($("#alert-sel2 li").eq(i).find("span").text()==$("#sel li.sel-li-bj").find("span").text()) {
							alert("请勿重复添加");
							return;
						}else{
							/*移动数据*/
								var ht = $("#sel li.sel-li-bj").prop("outerHTML");
								$("#alert-sel2").append(ht);
								$("#alert-sel2 li").removeClass("sel-li-bj");
								return;
						}
				}
					
			}else if ($("#sel li").hasClass("sel-li-bj")) {
				/*移动数据*/		
				var ht = $("#sel li.sel-li-bj").prop("outerHTML");
				$("#alert-sel2").append(ht);
				$("#alert-sel2 li").removeClass("sel-li-bj");
			}else{
			alert("请选择一个数据");}
		
		}
			/*全部向右移动*/
		if($(this).index()==1){
			$("#alert-sel2").html(" ");
			for (var i = 0; i < $("#alert-sel li").length; i++) {
				$("#alert-sel li")[i]
			}
		}
			/*向左移动*/
		if($(this).index()==2){

		}
			/*全部向左移动*/
		if($(this).index()==3){

		}


	});
	/************ 规则列表 结束*************/
	
	

});