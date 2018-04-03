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
<title>提现必要信息</title>

<link rel="shortcut icon" href="<%=basePath%>static/img/favicon.ico">

<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/demos.css">
<link rel="stylesheet" href="<%=basePath%>static/js/weui/css/example.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">

<style type="text/css">

.pic_block {
	display: block;
}

</style>

</head>

<body>

<div class="container" id="container">
	<div class="page input js_show">
<!-- 		<div class="page__hd" style="padding-bottom: 5px;">
        	<h1 class="page__title">提现信息</h1>
    	</div> -->
    	<div class="demos-content-padded" style="padding-bottom: 5px;">
	    	<div class="icon-box">
	        	<i class="weui-icon-warn"></i>
	        	<h4 class="icon-box__title" style="display: inline-block;">提示</h4>
	        	<div class="icon-box__ctn" style="font-size:x-small;font-family : 微软雅黑,宋体;">
	          		<p class="icon-box__desc">1、关系您的切身利益，请慎重上传收款码</p>
	          		<p class="icon-box__desc">2、如使用银联收款码，请先自行下载银联云闪付APP</p>
	        	</div>
	        </div>
    	</div>
    	<div class="page__bd">
			<div class="weui-cells">
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">联系人</label></div>
	                <div class="weui-cell__bd">
	                    <input id="linkMan" class="weui-input" type="text" value="${withdrawDto.linkMan}" placeholder="请输入联系人">
	                </div>
	            </div>
	            <div class="weui-cell">
	                <div class="weui-cell__hd"><label class="weui-label">联系电话</label></div>
	                <div class="weui-cell__bd">
	                    <input id="linkPhoneNo" class="weui-input" id="text" type="text" value="${withdrawDto.linkPhoneNo}" placeholder="请输入联系电话">
	                </div>
	            </div>
	        </div>
			<div class="weui-cells">
	            <a class="weui-cell weui-cell_access weui-media-box_appmsg" href="/ball/pic/1">
	                <div class="weui-cell__bd"><p>支付宝收款码</p></div>
	                <div class="weui-cell__ft">
	                	<div class="weui-media-box__hd">
		                	<img class="weui-media-box__thumb" src="/ball/loadPic/1" alt="">
	                	</div>
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access weui-media-box_appmsg" href="/ball/pic/2">
	                <div class="weui-cell__bd"><p>微信收款码</p></div>
	                <div class="weui-cell__ft">
	                	<div class="weui-media-box__hd">
		                	<img class="weui-media-box__thumb" src="/ball/loadPic/2" alt="">
	                	</div>
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access weui-media-box_appmsg" href="/ball/pic/3">
	                <div class="weui-cell__bd"><p>银联收款码</p></div>
	                <div class="weui-cell__ft">
	                	<div class="weui-media-box__hd">
		                	<img class="weui-media-box__thumb" src="/ball/loadPic/3" alt="">
	                	</div>
	                </div>
	            </a>
	        </div>
    	</div>
   		<div class="weui-btn-area">
           	<a class="weui-btn weui-btn_primary" href="javascript:wSubmit();" id="showTooltips">确认提交</a>
        </div>
    </div>
</div>

<div id="pic1" class="weui-gallery">
  <span class="weui-gallery__img" style="background-image: url(/ball/loadPic/1);"></span>
  <div class="weui-gallery__opr">
    <a href="javascript:" class="weui-gallery__del">
      <i class="weui-icon-download weui-icon_gallery-delete"></i>
    </a>
  </div>
</div>
<div id="pic2" class="weui-gallery">
  <span class="weui-gallery__img" style="background-image: url(/ball/loadPic/2);"></span>
  <div class="weui-gallery__opr">
    <a href="javascript:" class="weui-gallery__del">
      <i class="weui-icon-download"></i>
    </a>
  </div>
</div>
<div id="pic3" class="weui-gallery">
  <span class="weui-gallery__img" style="background-image: url(/ball/loadPic/3);"></span>
  <div class="weui-gallery__opr">
    <a href="javascript:" class="weui-gallery__del">
      <i class="weui-icon-info weui-icon_gallery-delete"></i>
    </a>
  </div>
</div>

</body>

<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>

<script type="text/javascript">
	
function wSubmit(){
	var linkMan = $("#linkMan").val();
	var linkPhoneNo = $("#linkPhoneNo").val();
	
	if (linkMan == null || linkMan.trim() == ""){
		$.toptip('联系人不能为空', 'warning');
		return;
	}
	
	var jsonData = {"linkMan":linkMan,"linkPhoneNo":linkPhoneNo};
	
	$.confirm({
		  title: '提示',
		  text: '确定保存吗？',
		  onOK: function () {
		    //点击确认
		    $.ajax({
		    	type: 'post',
		    	url: '<%=basePath%>w/save',
		    	data: jsonData,
		    	dataType: 'json',
		    	success: function(data){
		    		if (data.code == '1'){
		    			$.toast("保存成功", 2000, function(){
		    				window.location.href = "<%=basePath%>withdraw";
		    			});
		    		} else {
		    			$.toast(data.msg, "forbidden");
		    		}
		    	}
		    });
		  },
		  onCancel: function () {
		  }
	});
	
}

</script>

</html>