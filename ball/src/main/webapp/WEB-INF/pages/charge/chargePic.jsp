<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>支付收款码</title>

<link rel="stylesheet" href="/ball/static/js/weui/css/demos.css">
<link rel="stylesheet" href="/ball/static/js/weui/css/example.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<link rel="stylesheet" href="/ball/static/css/style.css" />

<style type="text/css">
img {width: 232px; margin: 10px auto;display: block;}
</style>
</head>

<body>

<input id="amount" name="amount" value="${amount}" />
<input id="channelId" name="channelId" value="${channelId}" />

<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__hd" style="padding-bottom: 0px;padding-top: 5px;">
        	<h1 class="page__title">付款码</h1>
    	</div>
    	<div class="page__bd">
    		<div class="weui-cells weui-cells_form">
    			<img alt="" src="/ball/charge/loadPic/${url}" />
    		</div>
	   		<div class="weui-cells weui-cells_form">
	   			<div class="weui-cell">
		   			<div class="weui-cell__hd"><label class="weui-label">备注</label></div>
		   			<div class="weui-cell__bd">
		   				<input id="remark" class="weui-input" type="text" value="" placeholder="请输入备注" />
		   			</div>
	   			</div>
	   		</div>
    	</div>
    	<div class="demos-content-padded" style="padding-bottom: 5px;">
	    	<div class="icon-box">
	        	<i class="weui-icon-info-circle"></i>
	        	<h4 class="icon-box__title" style="display: inline-block;">提示</h4>
	        	<div class="icon-box__ctn">
	          		<p class="icon-box__desc">请付款成功后点击此按钮</p>
	        	</div>
	        </div>
    	</div>
    	<div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:chargeFinish();" id="showTooltips">已完成充值</a>
        </div>
    	
	</div>
</div>

</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">

function chargeFinish(){
	
	var amount = $("#amount").val();
	var remark = $("#remark").val();
	var channelId = $("#channelId").val();
	
	$.confirm("确定已付款" + amount + "？", function() {
		$.alert("我们确认收款后，会尽最快给您入金，谢谢", function() {
			$.ajax({
				type: 'get',
				url: '/ball/charge/submit/' + amount + "/" + channelId + '/' + (remark == null || remark == '' ? "-" : remark),
				dataType: 'json',
				success: function(r){
					if (r.code == '1'){
						window.location.href = "/ball/index";
					} else {
						$.toast(r.msg, "forbidden");
					}
				}
			});
			
		});
	}, function() {
		//点击取消后的回调函数
	});
	
}

</script>

</html>