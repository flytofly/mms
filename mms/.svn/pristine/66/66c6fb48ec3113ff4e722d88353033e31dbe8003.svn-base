<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
String webpath = "http://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误页面</title>
<link href="<%=webpath%>/public/css/css.css" rel="stylesheet" type="text/css" />
<script language="javascript">  
    function showDetail()  
    {  
        var elm = document.getElementById('detail_system_error_msg');  
        if(elm.style.display == '') {  
            elm.style.display = 'none';  
        }else {  
            elm.style.display = '';  
        }  
    }  
</script> 
</head>
<body>
	<!--右边主页面-->
	<!--main-->
	<div class="main w20">
	<%  
        //Exception from JSP didn't log yet ,should log it here.  
        String requestUri = (String) request.getRequestURI();  
		System.out.println("errorPage:requestUri = " + requestUri);
        //LogFactory.getLog(requestUri).error(exception.getMessage(), exception);  
    %>  
    	<h1>对不起,发生系统内部错误,不能处理你的请求<br />  </h1>
        <div class="gslist">
            <table width="100%" border="0">
              <tr>
	           <th colspan="2"><button onclick="history.back();">返回</button>  </th>
              </tr>
	          <tr>
	           <td width="30%">错误信息:</td>
	           <td width="70%"></td>
	          </tr>
          </table>
        </div>   
    </div>
</body>
</html>