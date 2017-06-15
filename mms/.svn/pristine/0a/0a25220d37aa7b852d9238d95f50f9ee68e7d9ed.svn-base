$(function(){
  //一级菜单	   
  var menu_1 = $(".g-left-1 li");
      menu_1.click(function(){
	  var ind = $(this).index();
	  $(this).addClass("g-current").siblings().removeClass("g-current");
	  $(".g-submenu_1").eq(ind).show().siblings(".g-submenu_1").hide();
    });
	  
  //二级菜单
  var menu_2 = $(".g-left-2 li");
      menu_2.click(function(){
	  var ind2 = $(this).index();
	  $(this).addClass("g-current").siblings().removeClass("g-current");
	  $(".g-kuang").eq(ind2).show().siblings(".g-kuang").hide();			
	});
});