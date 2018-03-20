<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/log?type=${compentLog.type}">日志列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="compentLog" action="${ctx}/log" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>类型：</label><form:select id="type" path="type" class="input-medium">
								<form:option value="" label="请选择"/>
								<form:option value="pay" label="支付"/>  
			                    <form:option value="push" label="推送"/> 
							</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标题</th><th>时间</th><th>状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="obj">
			<tr>
			<td>${obj.title}</td>
				<td><fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${obj.status}</td>
			    <td>
    				<a href="${ctx}/log/form?id=${obj.id}">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>