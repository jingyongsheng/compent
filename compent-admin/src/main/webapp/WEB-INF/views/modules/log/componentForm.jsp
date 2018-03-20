<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>配置管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#value").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
			});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/log?type=${compentLog.type}">日志列表</a></li>
		<li class="active"><a href="${ctx}/log/form?id=${compentLog.id}">日志${not empty compentLog.id?'查看':'查看'}<shiro:lacksPermission
					name="sys:dict:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="compentLog" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="type" />
		<div class="control-group">
			<label class="control-label">标题 :</label>
			<div class="controls">${compentLog.title}</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间 :</label>
			<div class="controls">
				<fmt:formatDate value="${compentLog.createTime}"
					pattern="yyyy-MM-dd hh:mm:ss" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态 :</label>
			<div class="controls">${compentLog.status}</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">${compentLog.content}</div>
		</div>
		<div class="control-group">
			<label class="control-label">结果:</label>
			<div class="controls">${compentLog.result}</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>