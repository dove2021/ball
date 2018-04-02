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
<title>资金详情</title>

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/jquery-weui.min.css">

</head>

<body>
<div class="container" id="container">
	<div class="page input js_show">
	    <div class="page__hd">
	        <h1 class="page__title">资金详情</h1>
	    </div>
	    <div class="page__bd">
	    	<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">流水号</label></div>
					<div class="weui-cell__bd">
						<strong id="stakeFee_c">${flow.flowNo}</strong>
					</div>
				</div>
	        </div>
			<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">交易类型</label></div>
					<div class="weui-cell__bd">
						<strong id="stakeFee_c">${flow.tradeType == '01' ? '充值收入' : 
												  flow.tradeType == '02' ? '提现支出' :
												  flow.tradeType == '03' ? '下注金额' :
												  flow.tradeType == '04' ? '兑奖收入' :
												  flow.tradeType == '05' ? '下注手续费' : flow.tradeType}</strong>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">交易时间</label></div>
					<div class="weui-cell__bd">
						<strong id="stakeFee_c"><fmt:formatDate value="${flow.tradeTime}" pattern="yyyy/MM/dd HH:mm:ss"/></strong>
					</div>
				</div>
	        </div>
			<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">交易金额</label></div>
					<div class="weui-cell__bd">
						<c:choose>
							<c:when test="${flow.amount > 0}"><strong id="stakeFee_c" style="color: #FF6600;">+${flow.amount}</strong></c:when>
							<c:otherwise><strong id="stakeFee_c">${flow.amount}</strong></c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">账户余额</label></div>
					<div class="weui-cell__bd">
						<label class="weui-label"><strong id="stakeFee_c">${flow.balance}</strong></label>
					</div>
				</div>
	        </div>
	    </div>
	  	<div class="page__ft">
	        <a href="javascript:home()"><img src="<%=basePath%>static/img/favicon.ico" /></a>
	    </div>
	</div>
</div>
</body>

<script src="<%=basePath%>static/js/jquery-3.2.1.min.js"></script>
<script src="<%=basePath%>static/js/weui/jquery-weui.min.js"></script>

</html>