var prefix = "/user/broker";

$(document).ready(function() {
	load();
});

var load = function() {
	$('#brokerTable')
			.bootstrapTreeTable(
					{
						code: "brokerId",
						parentCode : 'parentId',
						type : "GET", // 请求数据的ajax类型
						url : prefix + '/list', // 请求数据的ajax的url
						ajaxParams : {}, // 请求数据的ajax的data属性
						expandColumn : '1',// 在哪一列上面显示展开按钮
						striped : true, // 是否各行渐变色
						bordered : true, // 是否显示边框
						expandAll : false, // 是否全部展开
						// toolbar : '#exampleToolbar',
						columns : [
								{
									title : 'id',
									field : 'brokerId',
									visible : false,
									align : 'center',
									valign : 'middle',
									width : '50px'
								},
								{
									title : '编号',
									field : 'brokerCode'
								},
								{
									title : '邀请码',
									field : 'signCode',
									align : 'center',
									valign : 'middle'
								},
								{
									title : '用户名',
									field : 'loginName'
								},
								{
									title : '会员单位',
									field : 'memberName'
								},
								{
									title : '层级',
									field : 'levelNum'
								},
								{
									title : '状态',
									field : 'status',
									align : 'center',
									formatter: function(item, index){
										if (item.status == '1') {
											return '<span class="label label-primary">正常</span>';
										} else if (item.status == '2') {
											return '<span class="label label-warning">冻结</span>';
										} else if (item.status == '3') {
											return '<span class="label label-success">锁定</span>';
										} else if (item.status == '4') {
											return '<span class="label label-info">解锁</span>';
										} else if (item.status == '5') {
											return '<span class="label label-danger">注销</span>';
										}
									}
								},
								{
									title : '创建人',
									field : 'creater'
								},
								{
									title : '创建时间',
									field : 'createTime'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(item, index) {
										var e = '<a class="btn btn-primary btn-sm '
												+ s_edit_h
												+ '" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ item.brokerId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var p = '<a class="btn btn-primary btn-sm '
												+ s_add_h
												+ '" href="#" mce_href="#" title="添加下级" onclick="addSon(\''
												+ item.brokerId
												+ '\')"><i class="fa fa-plus"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '
												+ s_remove_h
												+ '" href="#" title="冻结"  mce_href="#" onclick="freeze(\''
												+ item.brokerId
												+ '\')"><i class="fa fa-times"></i></a> ';
										return e  + p + d;
									}
								} ]
					});
}

function reLoad() {
	load();
}

function add(pId) {
	layer.open({
		type : 2,
		title : '增加一层代理商',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/' + pId // iframe的url
	});
}

function addSon(pId) {
	layer.open({
		type : 2,
		title : '增加子代理商',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/' + pId // iframe的url
	});
}

function freeze(id) {
	layer.confirm('确定要冻结选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/freeze",
			type : "post",
			data : {
				'id' : id
			},
			success : function(data) {
				if (data.code == 1) {
					layer.msg("冻结成功");
					reLoad();
				} else {
					layer.msg(data.msg);
				}
			}
		});
	})
}

function edit(id) {
	layer.open({
		type : 2,
		title : '代理商编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function batchRemove() {
	// var rows = $('#exampleTable').bootstrapTable('getSelections');

}