<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>提现出金</title>

<link rel="shortcut icon" href="static/img/favicon.ico">

<link rel="stylesheet" href="static/js/weui/css/demos.css">
<link rel="stylesheet" href="static/js/weui/css/example.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<link rel="stylesheet" href="static/css/style.css" />

<body>

<input type="hidden" id="wfb_times" name="wfb_times" value="${wfb_times}" />
<input type="hidden" id="wx_times" name="wx_times" value="${wx_times}" />
<input type="hidden" id="yl_times" name="yl_times" value="${yl_times}" />

<input type="hidden" id="zfb_withdrawSwitch" name="zfb_withdrawSwitch" value="${zfb.withdrawSwitch}" />
<input type="hidden" id="zfb_withdrawMin" name="zfb_withdrawMin" value="${zfb.withdrawMin}" />
<input type="hidden" id="zfb_withdrawMax" name="zfb_withdrawMax" value="${zfb.withdrawMax}" />
<input type="hidden" id="zfb_withdrawFee" name="zfb_withdrawFee" value="${zfb.withdrawFee}" />
<input type="hidden" id="zfb_withdrawTimes" name="zfb_withdrawTimes" value="${zfb.withdrawTimes}" />

<input type="hidden" id="wx_withdrawSwitch" name="wx_withdrawSwitch" value="${wx.withdrawSwitch}" />
<input type="hidden" id="wx_withdrawMin" name="wx_withdrawMin" value="${wx.withdrawMin}" />
<input type="hidden" id="wx_withdrawMax" name="wx_withdrawMax" value="${wx.withdrawMax}" />
<input type="hidden" id="wx_withdrawFee" name="wx_withdrawFee" value="${wx.withdrawFee}" />
<input type="hidden" id="wx_withdrawTimes" name="wx_withdrawTimes" value="${wx.withdrawTimes}" />

<input type="hidden" id="yl_withdrawSwitch" name="yl_withdrawSwitch" value="${yl.withdrawSwitch}" />
<input type="hidden" id="yl_withdrawMin" name="yl_withdrawMin" value="${yl.withdrawMin}" />
<input type="hidden" id="yl_withdrawMax" name="yl_withdrawMax" value="${yl.withdrawMax}" />
<input type="hidden" id="yl_withdrawFee" name="yl_withdrawFee" value="${yl.withdrawFee}" />
<input type="hidden" id="yl_withdrawTimes" name="yl_withdrawTimes" value="${yl.withdrawTimes}" />

<input type="hidden" id="channelId" name="channelId" value="1" />

<div class="container" id="container">
	<div class="page input js_show">
<!-- 		<div class="page__hd" style="padding-bottom: 5px;">
        	<h1 class="page__title">提现出金</h1>
    	</div> -->
    	<div class="demos-content-padded" style="padding-bottom: 5px;">
	    	<div class="icon-box">
	        	<i class="weui-icon-info-circle"></i>
	        	<h4 class="icon-box__title" style="display: inline-block;">提示</h4>
	        	<div class="icon-box__ctn">
	          		<p class="icon-box__desc">1、提现到账日期: T + 1</p>
	          		<p class="icon-box__desc">2、提现额度范围: <span id="withdrawMin">0</span>-<span id="withdrawMax">0</span></p>
	          		<p class="icon-box__desc">3、当日提现剩余次数: <span id="withdrawTimes">0</span></p>
	        	</div>
	        </div>
    	</div>
    	<div class="page__bd">
    		<div class="weui-cells">
	            <a class="weui-cell weui-cell_access" href="w/edit/">
	                <div class="weui-cell__bd">
	                    <p>提现必要信息</p>
	                </div>
	                <div class="weui-cell__ft"><c:choose><c:when test="${empty withdrawDto.linkPhoneNo}">请先设置</c:when><c:otherwise>联系人:${withdrawDto.linkMan}</c:otherwise></c:choose></div>
	            </a>
    		</div>
    		<div class="weui-cells">
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">提现渠道</label></div>
    				<div class="weui-cell__bd">
   						<div class="weui-flex">
    						<a id="zfb" href="javascript:channel(1);" name="bank" class="bank checked">
    							<label id="zfb_label" for="zf" class="clear"><img src="static/img/zfb.png" onclick=""/></label>
    						</a>
    						<a id="weixin" href="javascript:channel(2);" name="bank" class="bank">
								<label id="wx_label" for="zf" class="clear"><img src="static/img/weixin.jpg" onclick="" /></label>
							</a>
    						<a id="yl" href="javascript:channel(3);" name="bank" class="bank">
								<label id="wx_label" for="zf" class="clear"><img src="static/img/yl.png" onclick="" /></label>
							</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">账户余额</label></div>
    				<div class="weui-cell__bd">
    					<strong id="balance">${customerAccount.balance}</strong>
    				</div>
    			</div>
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">提现金额</label></div>
    				<div class="weui-cell__bd">
    					<input class="weui-input" id="withdrawAmount" type="number" name="withdrawAmount" value="" placeholder="提现金额">
    				</div>
    			</div>
    			<div class="weui-cell">
    				<div class="weui-cell__hd"><label class="weui-label">提现手续费</label></div>
    				<div class="weui-cell__bd">
    					<label class="weui-input" id="withdrawFee"></label>
    				</div>
    			</div>
    		</div>
    	</div>
   		<div class="weui-btn-area">
           	<a class="weui-btn weui-btn_primary" href="javascript:withdrawSubmit();" id="showTooltips">确定提现</a>
        </div>
    </div>
</div>

</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">
function channel(channelId){
	$("input[name='channelId']").val(channelId);
	if (channelId == 1){
		$("#zfb").addClass("checked");
		$("#weixin").removeClass("checked");
		$("#yl").removeClass("checked");
		zfbWithdraw();
	} else if (channelId == 2){
		$("#zfb").removeClass("checked");
		$("#weixin").addClass("checked");
		$("#yl").removeClass("checked");
		wxWithdraw();
	} else if (channelId == 3){
		$("#zfb").removeClass("checked");
		$("#weixin").removeClass("checked");
		$("#yl").addClass("checked");
		ylWithdraw();
	}
}

$(function(){
	zfbWithdraw();
});

function zfbWithdraw(){
	$("#withdrawMin").html($("#zfb_withdrawMin").val());
	$("#withdrawMax").html($("#zfb_withdrawMax").val());
	$("#withdrawFee").html($("#zfb_withdrawFee").val());
	$("#withdrawTimes").html(parseFloat($("#zfb_withdrawTimes").val()) - parseFloat($("#wfb_times").val()));
}

function wxWithdraw(){
	$("#withdrawMin").html($("#wx_withdrawMin").val());
	$("#withdrawMax").html($("#wx_withdrawMax").val());
	$("#withdrawFee").html($("#wx_withdrawFee").val());
	$("#withdrawTimes").html(parseFloat($("#wx_withdrawTimes").val()) - parseFloat($("#wx_times").val()));
}

function ylWithdraw(){
	$("#withdrawMin").html($("#yl_withdrawMin").val());
	$("#withdrawMax").html($("#yl_withdrawMax").val());
	$("#withdrawFee").html($("#yl_withdrawFee").val());
	$("#withdrawTimes").html(parseFloat($("#yl_withdrawTimes").val()) - parseFloat($("#yl_times").val()));
}

function withdrawSubmit(){
	var channelId = $("input[name='channelId']").val();
	var withdrawAmount = $("#withdrawAmount").val();
	if (null == channelId || channelId.trim() == ""){
		$.toptip('请选择提现渠道', 'error');return;
	}
	if (null == withdrawAmount || withdrawAmount.trim() == ""){
		$.toptip('提现金额不能为空', 'error');return;
	}
	if (parseFloat($("#balance").html()) < parseFloat(withdrawAmount)){
		$.toptip('账户余额不足', 'error');return;
	}
	if (parseFloat($("#withdrawMin").html()) > parseFloat(withdrawAmount)){
		$.toptip('提现金额过小', 'error');return;
	}
	if (parseFloat($("#withdrawMax").html()) < parseFloat(withdrawAmount)){
		$.toptip('提现金额过大', 'error');return;
	}
	if (parseFloat($("#withdrawTimes").html()) <= 0){
		$.toptip('当日次数已用完', 'error');return;
	}
	
	var jsonData = {"channelId":channelId,"withdrawAmount":withdrawAmount};
	
	$.confirm("确认提交提现申请？", "提交确认", function() {
		$.ajax({
			type: 'post',
			url: 'w/submit',
			data: jsonData,
			dataType: 'json',
			success: function(r){
				if (r.code == 1){
					$.toast("提现申请成功", function() {
						window.location.href = "withdraw";
					});
				} else {
					$.toast(r.msg, "forbidden");
				}
			}
		});
	});
}

</script>

</html>