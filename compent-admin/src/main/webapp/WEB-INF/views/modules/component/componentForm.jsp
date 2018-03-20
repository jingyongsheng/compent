<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
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
		<li><a href="${ctx}/component?type=${compentTask.type}">配置列表</a></li>
		<li class="active"><a href="${ctx}/component/form?id=${compentTask.id}">配置${not empty compentTask.id?'修改':'添加'}<shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="compentTask" action="${ctx}/component/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<div class="control-group">
			<label class="control-label">标识 :</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="50" style="width:98%" class="required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" cols="500" style="width:98%" rows="20" class="required" />
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="result" htmlEscape="false" cols="500" style="width:98%" rows="6"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge">
					<form:option value="1" label="开启"/>  
                    <form:option value="0" label="关闭"/> 
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权限:</label>
			<div class="controls">
				<form:input path="card" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>