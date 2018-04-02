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
<title>主页</title>

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/jquery-weui.min.css">

</head>

<body>
<div class="page input js_show">
    <div class="page__hd">
        <h1 class="page__title">个人信息</h1>
    </div>
    <div class="page__bd">
    	<div class="weui-cells">
			<div class="weui-cell">
				<div class="weui-cell__bd">
				    <p>用户名</p>
				</div>
				<div class="weui-cell__ft">${customer.loginName }</div>
	        </div>
            <a class="weui-cell weui-cell_access" href="<%=basePath%>customer/rePhone">
                <div class="weui-cell__bd">
                    <p>手机号</p>
                </div>
                <div class="weui-cell__ft">
                <c:choose>
                	<c:when test="${empty customer.phoneNo}">未设置</c:when>
                	<c:otherwise>${customer.phoneNo}</c:otherwise>
                </c:choose>
                </div>
            </a>
        </div>
		<div class="weui-cells">
            <a class="weui-cell weui-cell_access" href="<%=basePath%>customer/rePwd">
                <div class="weui-cell__bd">
                    <p>账号密码</p>
                </div>
                <div class="weui-cell__ft">已设置</div>
            </a>
        </div>
    </div>
  	<div class="page__ft">
        <a href="javascript:home()"><img src="<%=basePath%>static/img/favicon.ico" /></a>
    </div>
</div>
</body>

<script src="<%=basePath%>static/js/jquery-3.2.1.min.js"></script>
<script src="<%=basePath%>static/js/weui/jquery-weui.min.js"></script>

</html>