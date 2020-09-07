<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title></title>
<link rel="stylesheet" type="text/css" href="../resources/admin/easyui/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../resources/admin/easyui/css/wu.css" />
<link rel="stylesheet" type="text/css" href="../resources/admin/easyui/css/icon.css" />
<script type="text/javascript" src="../resources/admin/easyui/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../resources/admin/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resources/admin/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<body class="easyui-layout">
	<!-- 修改密码窗口 -->
	<div id="edit-dialog" class="easyui-dialog" data-options="closed:false,iconCls:'icon-save',modal:true,title:'修改密码',buttons:[{text:'确认修改',iconCls : 'icon-ok',handler :submitEdit}]" style="width: 450px; padding: 10px;">
		<form id="edit-form" method="post">
			<input type="hidden" name="id" id="edit-id">
			<table>
				<tr>
					<td width="60" align="right">用户名:</td>
					<td>
						<input type="text" name="username" class="wu-text easyui-validatebox" readonly="readonly" value="${admin.username }" />
					</td>
				</tr>
				<tr>
					<td width="60" align="right">原密码:</td>
					<td>
						<input type="password" id="oldPassword" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写原密码'" />
					</td>
				</tr>
				<tr>
					<td width="60" align="right">新密码:</td>
					<td>
						<input type="password" id="newPassword" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写新密码'" />
					</td>
				</tr>
				<tr>
					<td width="60" align="right">重复密码:</td>
					<td>
						<input type="password" id="reNewpassword" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写新密码'" />
					</td>
				</tr>

			</table>
		</form>
	</div>
</body>

<!-- End of easyui-dialog -->
<script type="text/javascript">
	function submitEdit() {
		var validate = $("#edit-form").form("validate")
		if (!validate) {
			$.messager.alert("消息提醒", "请输入你输入的数据！", "warning");
			return;
		}
		if ($("#newPassword").val() != $("#reNewpassword").val()) {
			$.messager.alert("消息提醒", "两次修改输入密码不一致！", "warning");
			return;
		}
		$.ajax({
			url : 'edit_password',
			dataType : 'json',
			type : 'post',
			data : {
				newPassword : $("#newPassword").val(),
				oldPassword : $("#oldPassword").val()
			},
			success : function(data) {
				if (data.type == 'success') {
					$.messager.alert('信息提示', '密码成功！', 'info');
					$('#edit-dialog').dialog('close');
					$('#data-datagrid').datagrid("reload");
				} else {
					$.messager.alert('信息提示', data.msg, 'warning');
				}
			}
		});
	}
	/*
	 * 
	
	 function openEdit() {
	 $('#edit-dialog').dialog({
	 closed : false,
	 modal : true,
	 title : "修改信息",
	 buttons : [ {
	 text : '确定',
	 iconCls : 'icon-ok',
	 handler : edit
	 } ],
	 onBeforeOpen : function() {
	 }
	 });
	 }

	 $(document).ready(function() {
	 openEdit();
	 })
	 */
	/**
	 * Name 修改记录
	 */
	/*
	
	function edit() {
	var validate = $("#edit-form").form("validate")
	if (!validate) {
		$.messager.alert("消息提醒", "请输入你输入的数据！", "warning");
		return;
	}
	var data = $("#edit-form").serialize();
	
	}
	 */
</script>