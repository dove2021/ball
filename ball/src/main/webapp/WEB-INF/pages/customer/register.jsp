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

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/weui.min.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/jquery-weui.min.css">

</head>
<body>
<div class="container" id="container">
	<div class="page input js_show">
		<div class="page__hd">
        	<h1 class="page__title">注册信息</h1>
    	</div>
    	<div class="page__bd">
    		<input type="hidden" id="url_signCode" name="url_signCode" value="${url_signCode}" />
    		<div class="weui-cells weui-cells_form">
    			<c:choose>
    				<c:when test="${url_signCode != '0'}">
	    			<div class="weui-cell">
		                <div class="weui-cell__hd"><label class="weui-label">会员单位</label></div>
		                <div class="weui-cell__bd">
		                    <input class="weui-input" id="memberCode" type="text" disabled="disabled" name="memberCode" value="${memberDto.memberCode }" placeholder="请输入会员单位">
		             	</div>
		            </div>
    				</c:when>
    				<c:otherwise>
	    		    <div class="weui-cell weui-cell_select weui-cell_select-after">
	    				<div class="weui-cell__hd">
	                    	<label for="" class="weui-label">会员单位</label>
	                	</div>
	                	<div class="weui-cell__bd">
	    					<select class="weui-select" id="memberCode" name="memberCode">
	    					 	<option value="">--请 选 择--</option>
	    					<c:forEach items="${memberList}" var="member">
		                        <option value="${member.memberCode}">${member.name}</option>
	    					</c:forEach>
			                </select>
	                	</div>
	    			</div>
    				</c:otherwise>
    			</c:choose>
	    			<div class="weui-cell">
		                <div class="weui-cell__hd"><label class="weui-label">邀请码</label></div>
		                <div class="weui-cell__bd">
		                	<c:choose>
		                		<c:when test="${url_signCode != '0'}">
			                    <input class="weui-input" type="text" disabled="disabled" value="${brokerDto.signCode}" placeholder="请输入代理商">
		                		</c:when>
		                		<c:otherwise>
		                		<input class="weui-input" id="signCode" name="signCode" type="text" placeholder="邀请码可不填">
		                		</c:otherwise>
		                	</c:choose>
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
    	</div>
    	<div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:register();" id="showTooltips">注册</a>
        </div>
	</div>
</div>
</body>

<script src="<%=basePath%>static/js/jquery-3.2.1.min.js"></script>
<script src="<%=basePath%>static/js/weui/jquery-weui.min.js"></script>

<script type="text/javascript">

function register(){
	var url_signCode = $("#url_signCode").val();
	var signCode = $("#signCode").val();
	var memberCode = $("#memberCode").val();
	var loginName = $("#loginName").val();
	var password = $("#password").val();
	var password1 = $("#password1").val();
	
	if (loginName == null || loginName == ""){
		$.toptip('用户名不能为空', 'error');
		return;
	}
	if (password == null || password == ""){
		$.toptip('密码不能为空', 'error');
		return;
	}
	if (password1 == null || password1 == ""){
		$.toptip('确认密码不能为空', 'error');
		return;
	}
	if (password != password1){
		$.toptip('密码和确认密码不能为空', 'error');
		return;	
	}
	if (url_signCode == '0'){
		if (memberCode != null || memberCode != ""){
			$.toptip('会员单位不能为空', 'error');
			return;	
		}
	}
	
	var jsonData = {"url_signCode":url_signCode,"signCode":signCode,"memberCode":memberCode,"loginName":loginName,password:"password"};
	
	$.confirm({
		text: '确认提交注册',
		onOK: function () {
			$.ajax({
				type : "post",
				url : "<%=basePath%>customer/register",
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