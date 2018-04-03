var prefix = "/user/customer"

$(function() {
	load();
});

function load() {
	$('#customerTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
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
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						sort : params.order,
						field : params.sort,
						customerCode : $('#searchName').val(),
						signCode : $('#searchName1').val(),
						memberCode : $('#searchName2').val()
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
                        checkbox: true
                    },
					{
					    //field: 'Number',//可不加  
					    title: '序号',//标题  可不加  
					    align : 'center',
					    formatter: function (value, row, index) {  
					        return index+1;  
					    }  
					},
					{
						field : 'customerCode',
						title : '客户编号'
					},
					{
						field : 'phoneNo',
						title : '手机号'
					},
					{
						field : 'loginName',
						title : '用户名'
					},
					{
						field : 'createDate',
						title : '注册时间'
					},
					{
						field : 'memberCode',
						title : '会员编号'
					},
					{
						field : 'signCode',
						title : '代理商'
					},
					{
						field : 'balance',
						title : '余额',
						align : 'right',
						halign: 'right',
						sortable : true,
					    formatter: function (value, row, index) {  
					        return parseFloat(value).toFixed(2);  
					    }
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
						field : 'id',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + e_edit + '" href="#" mce_href="#" title="修改密码" onclick="resetPwd(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i></a> ';
							var d = '<a class="btn btn-primary btn-sm ' + d_charge + '" href="#" title="充值" mce_href="#" onclick="charge(\''
								+ row.id
								+ '\')"><i class="fa fa-download"></i>入</a> ';
							var f = '<a class="btn btn-danger btn-sm ' + f_withdraw + '" href="#" title="提现" mce_href="#" onclick="withdraw(\''
								+ row.id
								+ '\',\''
								+ row.balance
								+ '\')"><i class="fa fa-share-square-o"></i>出</a> ';
							return d + f + e;
						}
					} ]
			});
}

function reLoad() {
	$('#customerTable').bootstrapTable('refresh');
}

$(function(){
	$('#searchName').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
	$('#searchName1').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
	$('#searchName2').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
});

function resetPwd(id) {
	layer.open({
		type : 2,
		title : '修改密码',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		scrollbar: false,
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function freeze(id){
	layer.confirm(
		"确定要冻结该客户？",
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

function batchFreeze(){
	var rows = $('#customerTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要冻结的客户");
		return;
	}
	layer.confirm("确认要冻结选中的 " + rows.length + " 个客户吗?", {
		btn: [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type: 'POST',
			data: {"ids": ids},
			url: prefix + '/batch/freeze',
			success: function(r) {
				if (r.code == 1) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function(){});
}

function batchNormal(){
	var rows = $('#customerTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要恢复的客户");
		return;
	}
	layer.confirm("确认要恢复选中的 " + rows.length + " 个客户吗?", {
		btn: [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type: 'POST',
			data: {"ids": ids},
			url: prefix + '/batch/normal',
			success: function(r) {
				if (r.code == 1) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function(){});
}

function charge(id){
	layer.open({
		type : 2,
		title : '后台入金',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		scrollbar: false,
		area : [ '800px', '520px' ],
		content : prefix + '/c/' + id // iframe的url
	});
}

function withdraw(id, balance){
	layer.open({
		type : 2,
		title : '后台出金',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		scrollbar: false,
		area : [ '800px', '520px' ],
		content : prefix + '/w/' + id // iframe的url
	});
	
}


$("#customerTable").on('dbl-click-row.bs.table', function(e, arg1){
	layer.open({
		type : 2,
		title : '用户详情',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		scrollbar: false,
		content : prefix + '/detail/' + arg1.id
	});
});


