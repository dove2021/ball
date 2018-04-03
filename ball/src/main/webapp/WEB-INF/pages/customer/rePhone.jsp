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
<title>修改手机</title>

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
	                <div class="weui-cell__hd"><label class="weui-label">原手机号</label></div>
	                <div class="weui-cell__bd">
	                <c:choose>
	                	<c:when test="${empty customer.phoneNo}">
	                		<input class="weui-input" id="phoneNo" name="phoneNo" type="text" disabled="disabled" value="未设置" placeholder="请输入原手机号">
	                	</c:when>
	                	<c:otherwise>
	                		<input class="weui-input" id="phoneNo" name="phoneNo" type="text" disabled="disabled" value="${customer.phoneNo}" placeholder="请输入原手机号">
	                	</c:otherwise>
	                </c:choose>
	                    
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label for="" class="weui-label">新手机号</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" id="phoneNo1" name="phoneNo1" type="text" value="" placeholder="请输入新手机号">
	                </div>
	            </div>
	        </div>
            <div class="weui-btn-area">
	            <a class="weui-btn weui-btn_primary" href="javascript:rePhone();" id="showTooltips">确定</a>
	        </div>
		</div>
	</div>
</div>

<div id="loadingToast" style="opacity: 0; display: none;">
	<div class="weui-mask_transparent"></div>
	<div class="weui-toast">
	    <i class="weui-loading weui-icon_toast"></i>
	    <p class="weui-toast__content">数据提交中</p>
	</div>
</div>

</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">
	
	function rePhone(){
		var phoneNo1 = $("#phoneNo1").val();
		if (phoneNo1 == null || phoneNo1 == ""){
			$.toptip('新手机号不能为空', 'error');
			return;
		}
		if (!checkMobile(phoneNo1)){
			$.toptip('新手机号格式错误', 'error');
			return;
		}
		
		var dataJson = {"phoneNo":phoneNo1};
		
		$.confirm({
			text: '确认修改手机',
			onOK: function () {
				$.ajax({
					type : "post",
					url : "<%=basePath%>customer/rePhone",
					data : dataJson,
					dataType : "json",
					beforeSend : function(){
						var $loadingToast = $('#loadingToast');
			            if ($loadingToast.css('display') == 'none') {
				            $loadingToast.fadeIn(100);
				            setTimeout(function () {
				                $loadingToast.fadeOut(100);
				            }, 2000);
			            }
					},
					success: function(data){
						if (data.code == 1){
							$.toast("修改成功");
							location.reload();
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

 	function checkMobile(phoneNo){
 		if(phoneNo == null || phoneNo=='') {
            return false;
 		}
 		var regMobile=/^1([3][0-9]|[4][0-9]|[5][0123456789]|[7][0123456789]|[8][0123456789])\d{8}$/;//手机号验证
        if(!regMobile.test(phoneNo)){	// 手机号格式不正确
            return false;
        }
        return true;
    }
</script>

</html>