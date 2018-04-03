<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>历史订单</title>

<link rel="shortcut icon" href="static/img/favicon.ico">

<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<style type="text/css">

.hidden {display: none;}

</style>
</head>

<body>
<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__bd">
			<div class="weui-cells__title">订单</div>
			<div class="weui-cells" id="list">
	            <a class="weui-cell weui-cell_access" href="javascript:detail(id);">
	            	<div class="weui-cell__bd">
	            		<p>类型</p><p style="font-size: 12px;font-weight: bold;">类型</p>
	            	</div>
	            	<div class="weui-cell__ft" style="color: black;">金额</div>
	            </a>
	        </div>
		</div>
		<div class="weui-loadmore">
		    <i class="weui-loading"></i>
		    <span class="weui-loadmore__tips">正在加载</span>
		</div>	
	</div>
</div>
</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">
var loading = false;  //状态标记
$(document.body).infinite(100).on("infinite", function(){
    if(loading) return;
    loading = true;
    setTimeout(function() {
    	addFreshList();
    }, 700);
});

var freshTimes = 0;

function addFreshList(){
	freshTimes ++;
	if (freshTimes != 1){
		offset = offset + limit;
	}
	hasAdd = offset + limit;
	reFreshData();
	
	if (hasAdd >= total){
		$(document.body).destroyInfinite();
		$(".weui-loadmore").addClass("hidden");
		var html_foot = '<div class="weui-msg__extra-area"><div class="weui-footer"><p class="weui-footer__text">已全部加载</p></div></div>';
		$("#list").append(html_foot);
	}
	loading = false;
}

var hasAdd = 0;
var limit = 10;
var offset = 0;
var total = 0;

$(function(){
	$("#list").empty();
	addFreshList();
});

function reFreshData(){
	$.ajax({
		type: "get",
		url: "orderH/list",
		data: {"limit":limit,"offset":offset},
		dataType: "json",
	    cache: false, 
		async: false, 
		success: function(data){
			if (data != null){
				// 数组长度
				total = data.total;
				
				$.each(data.rows, function(index, order){
					
					var html_cell = '<a class="weui-cell weui-cell_access" href="javascript:detail('
									+ order.id + ');"><div class="weui-cell__bd"><p>' 
									+ order.orderNo + '</p><p style="font-size: 12px;">'
									+ formatDate(new Date(order.createTime)) +'</p></div>';
					
					if (order.cleanAmount > 0){
						html_cell = html_cell + '<div class="weui-cell__ft" style="color: #FF6600;font-weight: bold;">+'+ parseFloat(order.cleanAmount).toFixed(2) + '</div></a>';
					} else {
						html_cell = html_cell + '<div class="weui-cell__ft" style="color: black;font-weight: bold;">'+ parseFloat(order.cleanAmount).toFixed(2) + '</div></a>';
					}
					
					$("#list").append(html_cell);
				});
			}
		}
	});	
}

function detail(id){
	window.location.href = "orderH/detail/" + id;		
}

function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	return year+"/"+month+"/"+date+" "+(hour > 9 ? hour : "0" + hour)+":"+(minute > 9 ? minute : "0" + minute)+":"+(second > 9 ? second : "0" + second); 
}
	
</script>

</html>