var prefix = "/user/member"

$(function() {
	load();
});

function load() {
	$('#memberTable')
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
				sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
//						limit : params.limit,
//						offset : params.offset,
						memberCode : $('#searchName').val()
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
						field : 'memberCode',
						title : '会员编号'
					},
					{
						field : 'name',
						title : '名称'
					},
					{
						field : 'loginName',
						title : '用户名'
					},
					{
						field : 'createTime',
						title : '创建时间'
					},
					{
						field : 'creater',
						title : '创建人'
					},
					{
						field : 'linkman',
						title : '联系人'
					},
					{
						field : 'phoneNo',
						title : '联系人手机'
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
							var e = '<a  class="btn btn-primary btn-sm ' + d_repwd + '" href="#" mce_href="#" title="修改密码" onclick="resetPwd(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i>修改密码</a> ';
							var d = '<a class="btn btn-primary btn-sm ' + e_charge + '" href="#" title="入金"  mce_href="#" onclick="charge(\''
								+ row.id
								+ '\')"><i class="fa fa-download"></i>入金</a> ';
							var f = '<a class="btn btn-warning btn-sm ' + f_withdraw + '" href="#" title="出金"  mce_href="#" onclick="withdraw(\''
								+ row.id
								+ '\')"><i class="fa fa-share-square-o"></i>出金</a> ';
							return e + d + f;
						}
					} ]
			});
}

function reLoad() {
	$('#memberTable').bootstrapTable('refresh');
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
		title : '新增会员',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
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
		content : prefix + '/repwd/' + id // iframe的url
	});
}

function charge(id){
	layer.prompt({
		formType: 0,
		value: '100',
		title: '手工入金',
		area: ['800px', '350px'] //自定义文本域宽高
	}, function(value, index, elem){
		var reg = new RegExp("^-?\\d+$");
		if (!reg.test(value)){
			layer.msg('请输入整数金额');
		} else {
			layer.confirm("确认给该会员充值" + value + " 吗?", 
				{btn: [ '确定', '取消' ]},
				function(){
					layer.close(index);
					$.ajax({
						type: 'get',
						url: prefix + '/charge/' + id + "/" + value,
						success: function(r) {
							if (r.code == 1) {
								layer.msg(r.msg,{icon:1});
								reLoad();
							} else {
								layer.msg(r.msg,{icon:2});
							}
						}
					});
				},
				function(){}
			);
		}
	});
}

function withdraw(id){
	layer.prompt({
		formType: 0,
		value: '100',
		title: '手工出金',
		area: ['800px', '350px'] //自定义文本域宽高
	}, function(value, index, elem){
		var reg = new RegExp("^-?\\d+$");
		if (!reg.test(value)){
			layer.msg('请输入整数金额');
		} else {
			layer.confirm("确认给该会员出金" + value + " 吗?", 
				{btn: [ '确定', '取消' ]},
				function(){
					layer.close(index);
					$.ajax({
						type: 'get',
						url: prefix + '/withdraw/' + id + "/" + value,
						success: function(r) {
							if (r.code == 1) {
								layer.msg(r.msg,{icon:1});
								reLoad();
							} else {
								layer.msg(r.msg,{icon:2});
							}
						}
					});
					
				},
				function(){}
			);
		}
	});	
}

function freeze(id){
	layer.confirm(
		"确定要冻结该会员？",
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
	var rows = $('#memberTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要冻结的会员");
		return;
	}
	layer.confirm("确认要冻结选中的 " + rows.length + " 个会员吗?", {
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
	var rows = $('#memberTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要恢复的会员");
		return;
	}
	layer.confirm("确认要恢复选中的 " + rows.length + " 个会员吗?", {
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
