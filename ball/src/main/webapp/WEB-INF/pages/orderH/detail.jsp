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

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/jquery-weui.min.css">

<style type="text/css">
.c_default {background-color: #0772B6;color: white;}
</style>

</head>

<body>
<div class="container" id="container">
	<div class="page input js_show">
	    <div class="page__hd">
	        <h1 class="page__title">订单详情</h1>
	    </div>		
		<div class="page__bd">
			<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">订单编号</label></div>
					<div class="weui-cell__bd">
						<strong id="orderNo">${orderH.orderNo}</strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注时间</label></div>
					<div class="weui-cell__bd">
						<strong id="createTime"><fmt:formatDate value="${orderH.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></strong>
					</div>
				</div>
	        </div>
	        <div class="weui-cells">
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注方向</label></div>
					<div class="weui-cell__bd">
						<div id="stakeType" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" >&nbsp;${orderH.stakeType == '1' ? '胜' : orderH.stakeType == '2' ? '平' : orderH.stakeType == '3' ? '负' : orderH.stakeType}&nbsp;</div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注品种</label></div>
					<div class="weui-cell__bd">
						<div id="stakePrice" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" ><fmt:formatNumber value="${orderH.stakePrice}" pattern="##0"/></div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注手数</label></div>
					<div class="weui-cell__bd">
						<div id="orderNum" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default" >${orderH.orderNum}</div>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注手续费</label></div>
					<div class="weui-cell__bd">
						<strong id="fee">${orderH.tradeFee + orderH.memberFee}</strong>
					</div>
				</div>
	        </div>
	        <div class="weui-cells">
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">下注金额</label></div>
					<div class="weui-cell__bd">
						<strong id="stakeAmount">${orderH.stakeAmount}</strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">赔率</label></div>
					<div class="weui-cell__bd">
						<strong id="oddsScale">${orderH.oddsScale}</strong>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">猜对兑换</label></div>
					<div class="weui-cell__bd">
						<strong id="preCleanAmount">${orderH.preCleanAmount}</strong>
					</div>
				</div>
	        </div>
	        <div class="weui-cells">
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">比赛结果</label></div>
					<div class="weui-cell__bd">
						<c:choose>
							<c:when test="${cleanAmount > 0}">
							<div id="gameResult" class="weui-btn weui-btn_mini weui-btn_default stakeType c_default">&nbsp;${scheduleDto.gameResult == '1' ? '胜' : scheduleDto.gameResult == '2' ? '平' : scheduleDto.gameResult == '3' ? '负' : scheduleDto.gameResult}&nbsp;</div>
							</c:when>
							<c:otherwise>
							<div id="gameResult" class="weui-btn weui-btn_mini weui-btn_default stakeType" style="background-color: #1aad19;color: white;">&nbsp;${scheduleDto.gameResult == '1' ? '胜' : scheduleDto.gameResult == '2' ? '平' : scheduleDto.gameResult == '3' ? '负' : scheduleDto.gameResult}&nbsp;</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
	        	<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">比赛兑换</label></div>
					<div class="weui-cell__bd">
						<c:choose>
							<c:when test="${cleanAmount > 0}">
							<strong id="cleanAmount" style="color: #FF6600;">+${cleanAmount}</strong>
							</c:when>
							<c:otherwise>
							<strong id="cleanAmount">${cleanAmount}</strong>
							</c:otherwise>
						</c:choose>
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

<script type="text/javascript">
	
</script>

</html>