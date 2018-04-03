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
<title>注册</title>

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
        	<h1 class="page__title">注册信息</h1>
    	</div>
    	<div class="page__bd">
    		<input type="hidden" id="signCode" name="signCode" value="${brokerDto.signCode}" />
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">会员单位</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="memberCode" disabled="disabled" type="text" value="${memberDto.memberCode}" />
	             	</div>
	            </div>
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">邀请码</label></div>
	                <div class="weui-cell__bd">
		            	<input class="weui-input" id="o_signCode" name="o_signCode" disabled="disabled" value="${brokerDto.signCode}" />
	             	</div>
	            </div>
    		</div>
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">用户名</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="loginName" name="loginName" type="text" placeholder="请输入用户名">
	             	</div>
	            </div>
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">密 码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password" name="password" type="password" placeholder="请输入密码">
	             	</div>
	            </div>
    			<div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">确认密码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password1" name="password1" type="password" placeholder="请输入确认密码">
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
    	</div>
    	<div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:register();" id="showTooltips">注册</a>
        </div>
	</div>
</div>
</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">

function changeKaptcha(){
	$("img")[0].src = '<%=basePath%>kaptcha';
}


function register(){
	var signCode = $("#signCode").val();
	var loginName = $("#loginName").val();
	var password = $("#password").val();
	var password1 = $("#password1").val();
	var kaptcha = $("#kaptcha").val();
	
	if (loginName == null || loginName == ""){
		$.toast('用户名不能为空', 'cancel');
		return;
	}
	if (loginName.length < 2){
		$.toast('用户名最小2位', 'cancel');
		return;
	}
	if (loginName.length > 32){$.toast('用户名最大32位', 'cancel');return;}
	if (password == null || password == ""){
		$.toast('密码不能为空', 'cancel');
		return;
	}
	if (password.length < 6){$.toast('密码最小6位', 'cancel');return;}
	if (password.length > 32){$.toast('密码最大32位', 'cancel');return;}
	if (password1 == null || password1 == ""){
		$.toast('确认密码不能为空', 'cancel');
		return;
	}
	if (password != password1){
		$.toast('密码和确认密码不能为空', 'cancel');
		return;	
	}
	if (kaptcha == null || kaptcha.trim() == ""){
		$.toast("验证码不能为空", "cancel");
		return;
	}
	
	var jsonData = {"signCode":signCode,"loginName":loginName,"password":password,"kaptcha":kaptcha};
	
	$.confirm({
		text: '确认提交注册',
		onOK: function () {
			$.ajax({
				type : "post",
				url : "<%=basePath%>customer/register",
				data : jsonData,
				dataType : "json",
				success: function(data){
					if (data.code == 1){
						$.toast("注册成功", function() {
							window.location.href = "<%=basePath%>index";
						});
					} else {
						$.toast(data.msg, "forbidden");
						changeKaptcha();
					}
				},
				error: function(){
					$.toast("网络异常", "forbidden");
				}
			});
			},
		onCancel: function (){}
	});
}

</script>

</html>