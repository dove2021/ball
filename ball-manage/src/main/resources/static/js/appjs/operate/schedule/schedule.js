var prefix = "/operate/schedule"

$(function() {
	load();
});

function load() {
	$('#scheduleTable')
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
						radio : true
					},
/*					{
					    title: '序号',//标题  可不加  
					    align : 'center',
					    formatter: function (value, row, index) {  
					        return index+1;  
					    }  
					},*/
					{
						field : 'memberCode',
						title : '会员编号'
					},
					{
						field : 'aName',
						title : '甲方'
					},
					{
						field : 'bName',
						title : '乙方'
					},
					{
						field : 'aScore',
						title : '甲得分'
					},
					{
						field : 'bScore',
						title : '乙得分'
					},
					{
						field : 'startTime',
						title : '比赛开始'
					},
					{
						field : 'endTime',
						title : '比赛结束'
					},
					{
						field : 'gameResult',
						title : '结果',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-info">未知</span>';
							} else if (value == '1') {
								return '<span class="label label-danger">赢</span>';
							} else if (value == '2') {
								return '<span class="label label-warning">平</span>';
							} else if (value == '3') {
								return '<span class="label label-primary">输</span>';
							}
						}
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '01') {
								return '<span class="label label-info">新建</span>';
							} else if (value == '02') {
								return '<span class="label label-primary">在售</span>';
							} else if (value == '03') {
								return '<span class="label label-danger">停售</span>';
							} else if (value == '04') {
								return '<span class="label label-info">比赛结束</span>';
							} else if (value == '05') {
								return '<span class="label label-danger">正在结算</span>';
							} else if (value == '06') {
								return '<span class="label label-success">完成</span>';
							}
						}
					},
					{
						field : 'id',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var a = '<a  class="btn btn-primary btn-sm ' + a_edit + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit "></i>编辑</a> ';
							var b = '<a  class="btn btn-primary btn-sm ' + b_onSale + '" href="#" mce_href="#" title="在售" onclick="onSale(\''
								+ row.id
								+ '\')"><i class="fa fa-calendar-check-o"></i>上架</a> ';
							var c = '<a  class="btn btn-danger btn-sm ' + c_offSale + '" href="#" mce_href="#" title="停售" onclick="offSale(\''
								+ row.id
								+ '\')"><i class="fa fa-calendar-times-o"></i>下架</a> ';
							var d = '<a class="btn btn-warning btn-sm ' + d_result + '" href="#" title="录入结果"  mce_href="#" onclick="result(\''
								+ row.id
								+ '\')"><i class="fa fa-edit"></i>录入结果</a> ';
							var e = '<a class="btn btn-danger btn-sm ' + e_clean + '" href="#" title="结算"  mce_href="#" onclick="clean(\''
								+ row.id
								+ '\')"><i class="fa fa-gavel"></i>结算</a> ';
							return a + d + e;
						}
					} ]
			});
}

function reLoad() {
	$('#scheduleTable').bootstrapTable('refresh');
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
		title : '新增赛程',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
}

function edit(id){
	layer.open({
		type : 2,
		title : '编辑赛程',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id
	});
}

function onSale(){
	var rows = $('#scheduleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要上架的赛程");
		return;
	}
	layer.confirm('确定要上架该赛程？', {
		btn : [ '确定', '取消' ]
	}, function() {
		var id = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			id = row['id'];
		});
		$.ajax({
			type : "get",
			url : prefix + "/onSale/" + id,
			success : function(data) {
				if (data.code == 1) {
					reLoad();
					layer.msg("上架成功", {icon:1});
				} else {
					layer.msg(data.msg, {icon: 2});
				}
			}
		});
	}, function(){});
}

function offSale(){
	var rows = $('#scheduleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要下架的赛程");
		return;
	}
	layer.confirm('确定要下架该赛程？', {
		btn : [ '确定', '取消' ]
	}, function() {
		var id = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			id = row['id'];
		});
		$.ajax({
			type : "get",
			url : prefix + "/offSale/" + id,
			success : function(data) {
				if (data.code == 1) {
					reLoad();
					layer.msg("下架成功",{icon: 1});
				} else {
					layer.msg(data.msg, {icon: 2});
				}
			}
		});
	})
}

function result(id){
	layer.open({
		type : 2,
		title : '赛程结果',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/result/' + id
	});
}

function clean(id){
	var index;
	layer.confirm('确定要结算该赛程？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			type : "get",
			url : prefix + "/clean/" + id,
			beforeSend: function(XMLHttpRequest){
				index = layer.load();
			},
			success : function(data) {
				layer.close(index);
				if (data.code == 1) {
					reLoad();
					layer.msg(data.msg);
				} else {
					layer.msg(data.msg, {icon: 2});
				}
			},
			error: function(){
				layer.close(index);
				layer.msg("网络异常或处理超时", {icon: 2});
			}
		});
	})	
}

$('#scheduleTable').on('dbl-click-row.bs.table', function(row, element){
	layer.open({
		type : 2,
		title : '赛程详情',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/detail/' + element.id
	});
});

