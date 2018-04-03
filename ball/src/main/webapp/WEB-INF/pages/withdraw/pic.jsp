<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>收款码</title>

<link rel="shortcut icon" href="<%=basePath%>static/img/favicon.ico">

<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<link rel="stylesheet" href="<%=basePath%>static/js/layui-v2.2.5/layui/css/layui.css">

<style type="text/css">

.pic_block {
	display: block;
}

</style>

</head>

<body>
	<input type="hidden" id="channelId" name="channelId" value="${channelId}" />

	<div id="pic1" class="weui-gallery pic_block">
		<span class="weui-gallery__img"
			style="background-image: url(/ball/loadPic/${channelId});"></span>
		<div class="weui-gallery__opr">
<!-- 			<a id="uploaPic" href="javascript:;" class="weui-gallery__del">
				<i class="weui-icon-info weui-icon_gallery-delete">上传图片</i>
			</a> -->
			<button type="button" class="layui-btn" id="uploaPic">上传收款码</button>
		</div>
	</div>
</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
<script src="<%=basePath%>static/js/layui-v2.2.5/layui/layui.js" ></script>

<script type="text/javascript">

	layui.use('upload', function () {
	    var upload = layui.upload;
	    //执行实例
	    var uploadInst = upload.render({
	        elem: '#uploaPic', 			//绑定元素
	        url: '<%=basePath%>uploadPic/' + $("#channelId").val(), //上传接口
	        size: 1000,
	        multiple: true,
	        done: function (r) {
	        	if (r.code == 1){
	        		layer.msg(r.msg);
	        		window.location.reload();
	        	} else {
	        		layer.msg(r.msg);
	        	}
	        },
	        error: function (r) {
	            layer.msg(r.msg);
	        }
	    });
	});	

</script>

</html>