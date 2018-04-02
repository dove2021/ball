var prefix = "/account/member"

$(function() {
	load();
});

function load() {
	$('#accountMemberTable')
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
				sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
//						limit : params.limit,
//						offset : params.offset,
						aName : $('#searchName').val()
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
						checkbox : true
					},
					{
					    title: '序号',//标题  可不加  
					    align : 'center',
					    formatter: function (value, row, index) {  
					        return index+1;  
					    }  
					},
					{
						field : 'memberCode',
						title : '会员编号'
					},
					{
						field : 'memberName',
						title : '会员名称'
					},
					{
						field : 'balance',
						title : '账户余额',
						align : 'right',
						halign: 'right'
					},
					{
						field : 'createDate',
						title : '创建日期'
					},
					{
						field : 'status',
						title : '状态',
						formatter : function(value, row, index) {
							if (value == '1') {
								return '<span class="label label-info">正常</span>';
							} else if (value == '2') {
								return '<span class="label label-danger">冻结</span>';
							} else if (value == '3') {
								return '<span class="label label-warning">锁定</span>';
							} else if (value == '4') {
								return '<span class="label label-primary">解锁</span>';
							} else if (value == '5') {
							return '<span class="label label-primary">注销</span>';
							}

						}
					},
					{
						field: 'id',
						title : '操作',
						formatter: function(value, row, index){
							var d =  '<a  class="btn btn-primary btn-sm" href="#" mce_href="#" title="明细" onclick="detail(\''
							+ value
							+ '\')"><i class="fa fa-reorder"></i> 明细</a> ';
							
							return d;
						}
					}
					]
			});
}

function reLoad() {
	$('#accountMemberTable').bootstrapTable('refresh');
}

$(function(){
	$('#searchName').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
});

function freeze(){
	var rows = $('#accountMemberTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要冻结的数据");
		return;
	}
	layer.confirm("确认要冻结选中的 " + rows.length + " 条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'get',
			url : prefix + '/freeze/' + ids,
			success : function(r) {
				if (r.code == 1) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}

function unfreeze(){
	var rows = $('#accountMemberTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要解冻的数据");
		return;
	}
	layer.confirm("确认要冻结选中的 " + rows.length + " 条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'get',
			url : prefix + '/unfreeze/' + ids,
			success : function(r) {
				if (r.code == 1) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}

function detail(id) {
	layer.open({
		type : 2,
		title : '资金流水',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/detail/' + id
	});
}

$("#accountMemberTable").on('dbl-click-row.bs.table', function(e, arg1){
	detail(arg1.id);
});
