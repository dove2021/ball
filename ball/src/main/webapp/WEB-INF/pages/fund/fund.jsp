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
<title>资金流水</title>
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
			<div class="weui-navbar" style="position: fixed;">
			    <div class="weui-navbar__item weui_bar__item_on">
			        <h3>收入: <strong id="in_come" style="color: #FF6600;">0.00</strong></h3>
			    </div>
			    <div class="weui-navbar__item weui_bar__item_on">
			        <h3>支出: <strong id="out_go" style="color: black;">0.00</strong></h3>
			    </div>
			</div>
		</div>
		<div class="page__bd" style="margin-top: 60px;">
			<div class="weui-cells__title">流水</div>
			<div class="weui-cells" id="list">
	            <a class="weui-cell weui-cell_access" href="javascript:detail(id);"><div class="weui-cell__bd"><p>类型</p><p style="font-size: 12px;font-weight: bold;">类型</p></div><div class="weui-cell__ft" style="color: black;">金额</div></a>
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
			url: "fund/list",
			data: {"limit":limit,"offset":offset},
			dataType: "json",
		    cache: false, 
			async: false, 
			success: function(data){
				if (data != null){
					// 数组长度
					total = data.total;
					
					$.each(data.rows, function(index, dto){
						var tradeTypeStr = "";
						if (dto.tradeType == '01'){
							tradeTypeStr = "充值收入";
						} else if (dto.tradeType == '02'){
							tradeTypeStr = "提现支出";
						} else if (dto.tradeType == '03'){
							tradeTypeStr = "下注金额";
						} else if (dto.tradeType == '04'){
							tradeTypeStr = "兑奖收入";
						} else if (dto.tradeType == '05'){
							tradeTypeStr = "下注手续费";
						} else if (dto.tradeType == '11'){
							tradeTypeStr = "提现手续费";
						} else if (dto.tradeType == '12'){
							tradeTypeStr = "提现手续费回退";
						} else if (dto.tradeType == '13'){
							tradeTypeStr = "提现金额回退回退";
						} else {
							tradeTypeStr = '未知';
						}
						
						var html_cell = '<a class="weui-cell weui-cell_access" href="javascript:detail('
										+ dto.id + ');"><div class="weui-cell__bd"><p>' 
										+ tradeTypeStr + '</p><p style="font-size: 12px;">'
										+ formatDate(new Date(dto.tradeTime)) +'</p></div>';
						
						if (parseFloat(dto.amount) > 0){
							html_cell = html_cell + '<div class="weui-cell__ft" style="color: #FF6600;font-weight: bold;">+'+ parseFloat(dto.amount).toFixed(2) + '</div></a>';
						} else {
							html_cell = html_cell + '<div class="weui-cell__ft" style="color: black;font-weight: bold;">'+ parseFloat(dto.amount).toFixed(2) + '</div></a>';
						}
						
						$("#list").append(html_cell);
					});
					
					var inCome = data.params.inCome;
					var outGo = data.params.outGo;
					
					if (inCome != null && inCome != 0){
						var old_inCome = $("#in_come").html();
						var new_in_come = inCome + parseFloat(old_inCome);
						$("#in_come").css('display', 'none');
						$("#in_come").html(new_in_come.toFixed(2));
						$("#in_come").fadeIn(2000);
					}
					
					if (outGo != null && outGo != 0){
						var old_outGo = $("#out_go").html();
						var new_out_go = outGo + parseFloat(old_outGo);
						$("#out_go").css('display', 'none');
						$("#out_go").html(new_out_go.toFixed(2));
						$("#out_go").fadeIn(2000);
					}
				}
			}
		});	
	}
	
	function detail(id){
		window.location.href = "fund/detail/" + id;		
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