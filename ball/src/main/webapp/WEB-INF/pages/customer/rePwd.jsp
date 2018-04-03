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
<title>修改密码</title>

<link rel="shortcut icon" href="static/img/favicon.ico">

<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

</head>

<body>
<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__bd">
			<div class="weui-cells weui-cells_form">
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">用户名</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" type="number" disabled="disabled" value="${customer.loginName }" placeholder="请输入用户名">
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">原密码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password" type="password" placeholder="请输入原密码">
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label for="" class="weui-label">新密码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password1" type="password" value="" placeholder="请输入新密码">
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label for="" class="weui-label">确认密码</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="password2" type="password" value="" placeholder="请输入确认密码">
	                </div>
	            </div>
	        </div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:rePwd();" id="showTooltips">确定</a>
	        </div>
		</div>
	</div>
</div>
</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">
	function rePwd(){
		var password = $("#password").val();
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();
		if (password == null || password == ""){
			$.toptip('原密码不能为空', 'error');
			return;
		}
		if (password1 == null || password1 == ""){
			$.toptip('新密码不能为空', 'error');
			return;
		}
		if (password2 == null || password2 == ""){
			$.toptip('确认密码不能为空', 'error');
			return;
		}
		if (password1 != password2){
			$.toptip('新密码和确认密码不一致', 'error');
			return;
		}
		if (password1.length < 6 || password1.length > 32){
			$.toptip('密码长度为6~32位', 'error');
			return;
		}
		var dataJson = {"password":password,"password1":password1};
		
		$.confirm({
			text: '确认修改密码',
			onOK: function () {
				$.ajax({
					type : "post",
					url : "<%=basePath%>customer/rePwd",
					data : dataJson,
					dataType : "json",
					success: function(data){
						if (data.code == 1){
							$.toast("修改成功");
						} else {
							$.toast(data.msg, "cancel");
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