<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>充值入金</title>

<link rel="stylesheet" href="static/js/weui/css/demos.css">
<link rel="stylesheet" href="static/js/weui/css/example.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<link rel="stylesheet" href="static/css/style.css" />

</head>

<style>
.xxx a {width: 53px;}
	
.charge_default {background-color: #fe9400; color: white;}

.charge {padding-left: 0px;padding-right: 0px;}

.hidden_input {display: none;}
</style>

<body>

<input type="hidden" id="amount" name="amount" value="10" />
<input type="hidden" id="channelId" name="channelId" value="1" />

<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__hd">
        	<h1 class="page__title">账户充值</h1>
    	</div>
    	<div class="page__bd">
    		<div class="weui-cells weui-cells_form">
   			    <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">充值金额</label></div>
					<div class="weui-cell__bd xxx">
						<div class="weui-flex">
							<a href="javascript:charge(10);" id="num_10" class="weui-btn weui-btn_mini charge weui-btn_default charge_default" style="margin-top:15px">10</a>
							<a href="javascript:charge(50);" id="num_50" class="weui-btn weui-btn_mini charge weui-btn_default">50</a>
							<a href="javascript:charge(100);" id="num_100" class="weui-btn weui-btn_mini charge weui-btn_default">100</a>
							<a href="javascript:charge(200);" id="num_200" class="weui-btn weui-btn_mini charge weui-btn_default">200</a>
						</div>
						<div class="weui-flex">
							<a href="javascript:charge(500);" id="num_500" class="weui-btn weui-btn_mini charge weui-btn_default" style="margin-top:15px">500</a>
							<a href="javascript:charge(1000);" id="num_1000" class="weui-btn weui-btn_mini charge weui-btn_default">1000</a>
							<a href="javascript:charge(3000);" id="num_3000" class="weui-btn weui-btn_mini charge weui-btn_default">3000</a>
							<a href="javascript:charge('x');" id="num_x" class="weui-btn weui-btn_mini charge weui-btn_default">其它</a>
						</div>
					</div>
	            </div>
    		</div>
    		
    		<div class="weui-cells weui-cells_form qt hidden_input">
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">其他金额</label></div>
    				<div class="weui-cell__bd">
    					<input id="qt_amount" class="weui-input" type="number" placeholder="请输入其它金额">
    				</div>
    			</div>
    		</div>
    		
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">充值渠道</label></div>
    				<div class="weui-cell__bd">
   						<div class="weui-flex">
    						<a id="zfb" href="javascript:channel(1);" name="bank" class="bank checked">
    							<label id="zfb_label" for="zf" class="clear"><img src="static/img/zfb.png" onclick=""/></label>
    						</a>
    						<a id="weixin" href="javascript:channel(2);" name="bank" class="bank">
								<label id="wx_label" for="zf" class="clear"><img src="static/img/weixin.jpg" onclick="" /></label>
							</a>
    						<a id="yinlian" href="javascript:channel(3);" name="bank" class="bank">
								<label id="yl_label" for="zf" class="clear"><img src="static/img/yl.png" onclick="" /></label>
							</a>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    	<div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:toCharge();" id="showTooltips">充值</a>
        </div>
	</div>
	
</div>

</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script src="static/js/weui/fastclick.js"></script>

<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>

<script type="text/javascript">
	
$(function(){
	$("#amount").val(10);
	$("#channelId").val(1);
	$("#zfb").addClass("checked");
	$("#weixin").removeClass("checked");
	$("#yinlian").removeClass("checked");
});
	
function channel(channelId){
	$("input[name='channelId']").val(channelId);
	if (channelId == 1){
		$("#zfb").addClass("checked");
		$("#weixin").removeClass("checked");
		$("#yinlian").removeClass("checked");
	} else if (channelId == 2){
		$("#zfb").removeClass("checked");
		$("#weixin").addClass("checked");
		$("#yinlian").removeClass("checked");
	} else if (channelId == 3){
		$("#zfb").removeClass("checked");
		$("#weixin").removeClass("checked");
		$("#yinlian").addClass("checked");
	}
}
	
function charge(amount){
	$("input[name='amount']").val(amount);
	$(".charge_default").removeClass("charge_default");
	$("#num_" + amount).addClass("charge_default");
	
	if (amount == 'x'){
		$(".hidden_input").removeClass("hidden_input");
	} else {
		$(".qt").addClass("hidden_input");
	}
}
	
function toCharge(){
	var amount = $("#amount").val();
	var channelId = $("#channelId").val();
	
	if (amount == 'x'){
		var atAmount = $("#qt_amount").val();
		if (atAmount == null || atAmount.trim() == ""){
			$.toptip('其他金额不能为空', 'error');return;
		}
		
		amount = amount + "," + atAmount;
	}
	
	window.location.href = "charge/" + channelId + "/" + amount;
}
</script>

</html>