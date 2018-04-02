var prefix = "/account/member/detail"

$(function() {
	load();
});

function load() {
	$('#accountMemberDetailTable')
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
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						accountId : $('#accountId').val()
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
						field : 'tradeTime',
						title : '交易时间'
					},
					{
						field : 'tradeType',
						title : '交易类型',
						align : 'center',
						halign: 'center',
					    formatter: function (value, row, index) {  
							if (value == '01') {
								return '<span class="label label-primary">手续费收入</span>';
							} else if (value == '02') {
								return '<span class="label label-primary">结算收入</span>';
							} else if (value == '03') {
								return '<span class="label label-warning">保证金支出</span>';
							} else if (value == '04') {
								return '<span class="label label-warning">提现支出 </span>';
							} else if (value == '05') {
								return '<span class="label label-primary">会员入金</span>';
							}
					    }
					},
					{
						field : 'amount',
						title : '交易金额',
						align : 'right',
						halign: 'right'
					},
					{
						field : 'balance',
						title : '当前余额',
						align : 'right',
						halign: 'right'
					}
				]
			});
}

function reLoad() {
	$('#accountMemberDetailTable').bootstrapTable('refresh');
}

$(function(){
	$('#searchName').keydown(function(e){
		if(e.keyCode==13){
			reLoad();
		}
	});
	
});


