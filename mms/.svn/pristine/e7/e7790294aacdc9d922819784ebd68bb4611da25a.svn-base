<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
var totalRow = ${records.totalRow}; 
var pageSize = ${records.pageSize};
function gotoPage(page){
	setPage(page);
	document.getElementById("form").submit();
}
function setPage(page){
	var pageCount = parseInt(totalRow/pageSize) + 1;
	if(totalRow%pageSize==0){
		pageCount=parseInt(totalRow/pageSize);
	}
	if(page <= 0){
		page = 1;
	}
	if(page > pageCount){
		page = pageCount;
	}
	document.getElementById("pageNumber").value = page;
}
</script>

  <div id="fanye">
 	 <input type="hidden" id="pageNumber" name="pageNumber" value="${records.pageNumber}" />
    <div class="left">
    	共<font>
		<c:if test="${records.totalRow == 0}">1</c:if>
		<c:if test="${records.totalRow != 0}">
	     <fmt:formatNumber type='number' value='${(records.totalRow + records.pageSize - 1 - (records.totalRow + records.pageSize - 1)%records.pageSize)/records.pageSize}' pattern="0"></fmt:formatNumber> 
		</c:if>
		</font>页 | 第<font><fmt:formatNumber type='number' value='${records.pageNumber}' pattern="0"/></font>页 | 总<font>${records.totalRow}</font>条记录
	</div>
	
    <div class="right">
      <div class="sy"><a href="#" onClick="gotoPage(1)">首页</a></div>
      <div class="sy"><a href="#" onClick="gotoPage(${records.pageNumber}-1)">上一页</a></div>
 	  <div class="sy"><a href="#" onClick="gotoPage(${records.pageNumber}+1)">下一页</a></div>
 	  <div class="sy"><a href="#" onClick="gotoPage(<fmt:formatNumber type='number' value='${(records.totalRow + records.pageSize - 1 - (records.totalRow + records.pageSize - 1)%records.pageSize)/records.pageSize}' pattern="0"/>)">末页</a></div>
  	
  	 <div class="ts">
  	 	  第<input id="gotoPageNumber" name="gotoPageNumber" value="${records.pageNumber}" type="text" class="input19" size="1" onChange="setPage(this.value)"/>页
	  	 <span>
	  	 	<a href="#" onclick="gotoPage(document.getElementById('gotoPageNumber').value)">跳转</a>
	  	 </span>
  	 </div> 
  	    
    </div>
  </div>
   	    <!--分页 END-->