var prefix = "/account/trade"

$(function() {
	load();
});

function load() {
	$('#accountTradeTable')
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
					    title: '序号',//标题  可不加  
					    align : 'center',
					    formatter: function (value, row, index) {  
					        return index + 1;  
					    }  
					},
					{
						field : 'id',
						title : 'id'
					},
					{
						field : 'balance',
						title : '账户金额'
					},
					{
						field : 'createTime',
						title : '创建时间'
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
	$('#accountTradeTable').bootstrapTable('refresh');
}

function detail(id) {
	layer.open({
		type : 2,
		title : '资金流水',
		maxmin : false,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/fund/detail/' + id
	});
}

$(function(){
	$('#searchName').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
	
});

$("#accountTradeTable").on('dbl-click-row.bs.table', function(e, arg1){
	detail(arg1.id);
});