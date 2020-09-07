<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" flush="true" />
<div class="easyui-layout" data-options="fit:true">
	<!-- Begin of toolbar -->
	<div id="wu-toolbar">
		<div class="wu-toolbar-button">
			<jsp:include page="../common/menus.jsp" flush="true" />
		</div>
		<div class="wu-toolbar-search">
			<label>日志内容：</label>
			<input class="wu-text" id="search-name" style="width: 100px">

			<a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	<!-- End of toolbar -->
	<table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<style>
.selected {
	background: red;
}
</style>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width: 450px; padding: 10px;">
	<form id="add-form" method="post">
		<table>
			<tr>
				<td align="right">日记内容:</td>
				<td>
					<textarea name="content" id="add-content" rows="6" class="wu-textarea" style="width: 260px"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width: 450px; padding: 10px;">
	<form id="edit-form" method="post">
		<input type="hidden" name="id" id="edit-id">
		<table>
			<tr>
				<td align="right">日记内容:</td>
				<td>
					<textarea id="edit-content" name="content" rows="6" class="wu-textarea" style="width: 260px"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="../common/footer.jsp" flush="true" />
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	 * Name 添加记录
	 */
	function add() {
		var validate = $("#add-form").form("validate")
		if (!validate) {
			$.messager.alert("消息提醒", "请输入你输入的数据！", "warning");
			return;
		}
		var data = $("#add-form").serialize();
		$.ajax({
			url : 'add',
			dataType : 'json',
			type : 'post',
			data : data,
			success : function(data) {
				if (data.type == 'success') {
					$.messager.alert('信息提示', '添加成功！', 'info');
					$('#add-content').val('');
					$('#add-dialog').dialog('close');
					$('#data-datagrid').datagrid("reload");
				} else {
					$.messager.alert('信息提示', data.msg, 'warning');
				}
			}
		});
	}

	/**
	 * Name 删除记录
	 */
	function remove() {
		$.messager.confirm('信息提示', '确定要删除该记录？', function(result) {
			if (result) {
				var item = $('#data-datagrid').datagrid('getSelections');
				if (item == null || item.length == 0) {
					$.messager.alert('信息提示', '请选择要删除的数据！', 'info');
					return;
				}
				var ids = '';
				for (var i = 0; i < item.length; i++) {
					ids += item[i].id + ',';
				}

				$.ajax({
					url : 'delete',
					dataType : 'json',
					type : 'post',
					data : {
						id : ids
					},
					success : function(data) {
						if (data.type == 'success') {
							$.messager.alert('信息提示', '删除成功！', 'info');
							$('#data-datagrid').datagrid("reload");
						} else {
							$.messager.alert('信息提示', data.msg, 'warning');
						}
					}
				});
			}
		});
	}

	/** 

	/**
	 * Name 打开添加窗口
	 */
	function openAdd() {
		$('#add-dialog').dialog({
			closed : false,
			modal : true,
			title : "添加用户信息",
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : add
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#add-dialog').dialog('close');
				}
			} ],
			onBeforeOpen : function() {
				//$("#add-form input").val('');
			}
		});
	}

	//搜索按钮监听
	$("#search-btn").click(function() {
		var option = {
			content : $("#search-name").val()
		};
		$('#data-datagrid').datagrid('reload', option);
	});

	function add0(m){
		return m<10?'0'+m:m;
	}
	function format(value){
		var time = new Date(value);
		var y=time.getFullYear();
		var m=time.getMonth()+1;
		var d=time.getDate();
		var h=time.getHours();
		var mm=time.getMinutes();
		var s=time.getSeconds();
		return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
	}
	
	/**
	 * Name 载入数据
	 */
	$('#data-datagrid').datagrid({
		url : 'list',
		rownumbers : true,
		singleSelect : false,
		pageSize : 20,
		pagination : true,
		multiSort : true,
		fitColumns : true,
		idField : 'id',
		treeField : 'name',
		fit : true,
		columns : [ [ {
			field : 'chk',
			checkbox : true
		}, {
			field : 'content',
			title : '日志内容',
			width : 100,
			sortable : true
		}, {
			field : 'createTime',
			title : '时间',
			width : 200,
			formatter : function(value, row, index) {
				return format(value);
				
			}
		} ] ]

	});
</script>