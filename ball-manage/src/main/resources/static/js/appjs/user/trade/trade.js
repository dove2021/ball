var prefix = "/user/trade"

$(function() {
	load();
});

function load() {
	$('#tradeTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#tradeToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
//						limit : params.limit,
//						offset : params.offset,
						loginName : $('#searchName').val()
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
					    //field: 'Number',//可不加  
					    title: '序号',//标题  可不加  
					    align : 'center',
					    formatter: function (value, row, index) {  
					        return index+1;  
					    }  
					},
					{
						field : 'userCode',
						title : '编号'
					},
					{
						field : 'loginName',
						title : '用户名'
					},
					{
						field : 'creater',
						title : '创建人'
					},
					{
						field : 'createTime',
						title : '创建时间'
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '1') {
								return '<span class="label label-primary">正常</span>';
							} else if (value == '2') {
								return '<span class="label label-warning">冻结</span>';
							} else if (value == '3') {
								return '<span class="label label-success">锁定</span>';
							} else if (value == '4') {
								return '<span class="label label-info">解锁</span>';
							} else if (value == '5') {
								return '<span class="label label-danger">注销</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + e_repwd + '" href="#" mce_href="#" title="修改密码" onclick="resetPwd(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i>修改密码</a> ';
							var f = '<a  class="btn btn-primary btn-sm ' + e_role + '" href="#" mce_href="#" title="修改角色" onclick="reRole(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i>修改角色</a> ';
							var d = '<a class="btn btn-danger btn-sm ' + e_freeze + '" href="#" title="冻结"  mce_href="#" onclick="freeze(\''
								+ row.id
								+ '\')"><i class="fa fa-key"></i>冻结</a> ';
							return e + f + d;
						}
					} ]
			});
}

function reLoad() {
	$('#tradeTable').bootstrapTable('refresh');
}

$(function(){
	$('#searchName').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
});

function add() {
	layer.open({
		type : 2,
		title : '新增',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '460px' ],
		content : prefix + '/add'
	});
}

function resetPwd(id) {
	layer.open({
		type : 2,
		title : '修改密码',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function reRole(id) {
	layer.open({
		type : 2,
		title : '修改角色',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '300px' ],
		content : prefix + '/reRole/' + id // iframe的url
	});
}

function freeze(id){
	layer.confirm(
		"确定要冻结该用户？",
		{btn : [ '确定', '取消' ]}, 
		function(){
			$.ajax({
				type: "get",
				url: prefix + "/freeze/" + id,
				error : function(request) {
					layer.alert("Connection error");
				},
				success: function(r) {
					if (r.code == 1) {
						layer.msg(r.msg);
						reLoad();
					} else {
						layer.msg(r.msg);
					}
				}
			});
		}
	);
}

function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn: [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url: prefix + "/freeze/" + id,
			type: "post",
			error : function(request) {
				layer.alert("Connection error");
			},
			success: function(r) {
				if (r.code == 1) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}

function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['deptId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}

