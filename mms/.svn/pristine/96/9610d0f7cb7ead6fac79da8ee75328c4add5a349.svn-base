<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>URL规则管理</title>
<link href="<%=request.getContextPath() %>/resource/templates/basic/css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/templates/basic/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/templates/basic/css/mainframe.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/bootstrap/select/select2.css"/>
<link href="<%=request.getContextPath()%>/resource/jquery/jquery-validation/css/validationEngine.jquery.css" rel="stylesheet" type="text/css"/>
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-1.9.1.js"></script>
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-validation/localization/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-validation/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=request.getContextPath() %>/resource/bootstrap/select/select2.js"></script>
</head>
<body>
<!--main-->
<div id="mainpage" class="glist_main">
    <h1>数据源管理-URL规则管理-URL规则编辑</h1>
    <div class="detail" style="width:80%;">
   	<form name="cform" id="cform" action="./updateTemp" method="post"  >
   	  <input type="hidden" name="deployUrlRuleTemp.id" value = "${deployUrlRuleTemp.id}" id="id"/>
   	  <input type="hidden" name="deployUrlRuleTemp.status" value = "" id="status"/>
      <table width="100%" border="0" >
        <tr>
          <td colspan="2" class="bule">URL规则编辑</td>
        </tr>
        <tr>
		    <td width="16%" align="right">行业名称：</td>
		    <td width="84%">
		        <input type="hidden" name="catid" value = "${deployUrlRuleTemp.catid}" id="catid"/>
		        <input type="text" readonly="readonly" size="32"  maxlength="128" class="validate[required] re_input" name="deployUrlRuleTemp.cat" value = "${deployUrlRuleTemp.cat}" id="cat"/>
		        <!-- <input type="button" id="searchcat" value="查找行业"/> -->
		    </td>
        </tr>
        <tr>
		    <td width="16%" align="right">规则名称：</td>
		    <td width="84%">
		        <input type="text" size="32"  maxlength="128" class="validate[required,funcCall[RepeatVerifiAjax]] re_input" name="deployUrlRuleTemp.urlrule" value = "${deployUrlRuleTemp.urlrule}" id="urlrule" maxlength="80"/>
		    </td>
        </tr> 
        <tr>
		    <td width="16%" align="right">规则说明：</td>
		    <td width="84%">
		        <input type="text" size="32"  maxlength="128" class="" name="deployUrlRuleTemp.urlrule" value = "${deployUrlRuleTemp.ruledesc}" id="urlrule" maxlength="80"/>
		    </td>
        </tr> 
        <tr>
		    <td align="center" colspan="2">
		    	<input type="button" value="提交" onclick="return clickSubmit();"/>
		    	<input type="reset" value="重置" />
		    	<input type="button" class="tianjia" value="返回" onclick="history.go(-1)" />
		    </td>
        </tr>
     </table>
     </form>
     <div class="clearfix w20"></div>
    </div>
</div>
<!--main--end-->

<script language="javascript">
// 查找企业
 jQuery(document).ready(function(){
	$("#searchcat").click(function() {
		window.open("<%=request.getContextPath()%>/ds/rulecat/list3?businessType=2","","width=800,height=600");
	});
 	$("#cform").validationEngine('attach');
});


//对新url规则进行重复性验证
 function RepeatVerifiAjax(field){
 	var result;
 	$.ajax({
 		type : "post",
 		async : false,
 		url : "ruleRepeatVerifiAjax",
 		dataType : "json",
 		data : {"urlrule":field.val(),"province":"${deployUrlRuleTemp.province}"},
 		success:function(data){
 			if(data == '4'){
             	result="*规则不正确 !";
             }else if(data == '1'){
             	$("#status").val(1);
             }else if(data == '2'){
             	$("#status").val(2);
             }else if(data == '3'){
            	 result="*规则已部署 !";
             }
 		}
 	})
 	if(result!= null){
     	return result;
       }
 }

//对新url规则进行重复性验证
 function catVerifiAjax(field){
 	var result;
 	$.ajax({
 		type : "post",
 		async : false,
 		url : "catVerifiAjax",
 		dataType : "json",
 		data : {"cat":field.val()},
 		success:function(data){
 			if(data == '1'){
             	result="*规则不正确 !";
             }else if(data == '2'){
             	result= "*在该省份已部署  !";
             }
 		}
 	})
 	if(result!= null){
     	return result;
       }
 }
 
 

// 重置
function clickReset(){
	$("#cform").validationEngine('hide');
	$('#cform').reset();
}
// 提交
function clickSubmit(){
	if(jQuery('#cform').validationEngine('validate')){
		$('#cform').submit();
	}
}
</script>
</body>
</html>
