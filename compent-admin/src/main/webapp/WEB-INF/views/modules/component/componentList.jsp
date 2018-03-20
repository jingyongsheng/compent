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
		<li class="active"><a href="${ctx}/component?type=${compentTask.type}">配置列表</a></li>
		<li><a href="${ctx}/component/form?type=${compentTask.type}">配置添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="compentTask" action="${ctx}/component?type=${compentTask.type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标识 ：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>权限 ：</label><form:input path="card" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>类型：</label><form:select id="status" path="status" class="input-medium">
								<form:option value="" label="请选择"/>
								<form:option value="1" label="开启"/>  
			                    <form:option value="0" label="关闭"/> 
							</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>标识</th><th>说明</th><th>权限</th><th>状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="obj">
			<tr>
			<td style="width:30%">${obj.title}</td>
			<td style="width:30%">${obj.result}</td>
			<td style="width:5%">${obj.card}</td>
			<td style="width:5%">${obj.status eq '1'?'开启':'关闭'}</td>
			<td style="width:10%">
    				<a href="${ctx}/component/form?id=${obj.id}">修改</a>
			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>