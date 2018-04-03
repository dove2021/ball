<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page import="com.tbug.ball.utils.CookieUtils"%>
<%@ page import="com.tbug.ball.utils.AESUtil"%>

<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>

<%
Cookie cookie = CookieUtils.getCookieByName(request, "userName");
String userName = "";
if (cookie != null){
	userName = cookie.getValue() == null ? "" : AESUtil.deccryptStr(cookie.getValue());
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>登陆</title>

<link rel="shortcut icon" href="static/img/favicon.ico">

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
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
    		<div class="weui-cells weui-cells_form">
	    		<div class="weui-cell">
				    <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
				    <div class="weui-cell__bd">
				        <input class="weui-input" id="kaptcha" type="text" placeholder="请输入验证码">
				    </div>
				    <div class="weui-cell__ft">
				        <img class="weui-vcode-img" onclick="changeKaptcha()" src="<%=basePath%>kaptcha">
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

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>


<script type="text/javascript">

$(function(){
	$('#username').keydown(function(e){
		if(e.keyCode==13){
			$('#password').focus();
		}
	});
	$('#password').keydown(function(e){
		if(e.keyCode==13){
			$('#kaptcha').focus();
		}
	});
	$('#kaptcha').keydown(function(e){
		if(e.keyCode==13){
			login();
		}
	});
});

function changeKaptcha(){
	
	$("img")[0].src = '<%=basePath%>kaptcha';
}

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var kaptcha = $("#kaptcha").val();
	
	if (username == null || username == ""){
		$.toast("用户名不能为空", "cancel");
		return;
	}
	if (password == null || password == ""){
		$.toast("密码不能为空", "cancel");
		return;
	}
	if (kaptcha == null || kaptcha.trim() == ""){
		$.toast("验证码不能为空", "cancel");
		return;
	}
	
	var jsonData = {"username":username,"password":password,"kaptcha":kaptcha};
	
	$.ajax({
		type: "post",
		url: "login",
		data: jsonData,
		dateType: "json",
		success: function(data){
			if (data.code == '1'){
				 window.location.href = "index";
			} else {
				$.toast(data.msg, "forbidden");
				changeKaptcha();
			}
		},
		error: function(){
			$.toast('网络异常', 'forbidden');
		},
	});
}

</script>

</html>