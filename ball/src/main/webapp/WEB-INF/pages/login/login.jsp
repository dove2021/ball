<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page import="com.tbug.ball.utils.CookieUtils"%>

<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>

<%
Cookie cookie = CookieUtils.getCookieByName(request, "userName");
String userName = "";
if (cookie != null){
	userName = cookie.getValue();
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>登陆</title>

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/jquery-weui.min.css">
</head>

<body>
<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__hd">
        	<h1 class="page__title">用户登录</h1>
    	</div>
    	<div class="page__bd">
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">用 户 名</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="username" name="username" value="<%= userName %>" type="text" placeholder="请输入用户名">
	             	</div>
	            </div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">密 码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password" name="password" type="password" placeholder="请输入密码">
	             	</div>
	            </div>
    		</div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:login();" id="showTooltips">登 录</a>
	        </div>
    	</div>
	</div>
</div>

</body>

<script src="<%=basePath%>static/js/jquery-3.2.1.min.js"></script>
<script src="<%=basePath%>static/js/weui/jquery-weui.min.js"></script>

<script type="text/javascript">

$(function(){
	$('#password').keydown(function(e){
		if(e.keyCode==13){
			login();
		}
	});
});

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	
	if (username == null || username == ""){
		$.toptip('用户名不能为空', 'error');
		return;
	}
	if (password == null || password == ""){
		$.toptip('密码不能为空', 'error');
		return;
	}
	
	var jsonData = {"username":username,"password":password};
	
	$.ajax({
		type: "post",
		url: "login",
		data: jsonData,
		dateType: "json",
		success: function(data){
			if (data.code == '1'){
				 window.location.href = "index";
			} else {
				$.toast(data.msg, "cancel");
			}
		},
		error: function(){
			$.toast('网络异常', 'forbidden');
		},
	});
}

</script>

</html>