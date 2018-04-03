var prefix = "/operate/withdraw";

$(function(){
	
	$("#btn-submit").on('click',function(){
		
		var id = $("#id").val();
		var info = $("#info").val();
		
		layer.confirm('确定出金申请已打款？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				type : "get",
				data : {"info":info},
				url : prefix + "/pass/" + id,
				success : function(data) {
					if (data.code == 1) {
						parent.reLoad();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);
						parent.layer.msg("操作成功", {icon:1});
					} else {
						parent.layer.msg(data.msg, {icon: 2});
					}
				}
			});
		}, function(){});
		
	});
	
	$("#btn-back").on('click',function(){
		
		var id = $("#id").val();
		var info = $("#info").val();
		
		if (info == null || info.trim() == ""){
			layer.msg("审核意见不能为空", {icon: 2});
			return;
		}
		
		layer.confirm('确定驳回此出金申请？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				type : "get",
				data : {"info":info},
				url : prefix + "/back/" + id,
				success : function(data) {
					if (data.code == 1) {
						parent.reLoad();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);
						parent.layer.msg("申请已驳回", {icon:1});
					} else {
						parent.layer.msg(data.msg, {icon: 2});
					}
				}
			});
		}, function(){});
		
	});
	
});
	

function picShow(){
/*	layer.open({
		type : 2,
		title: false,
		closeBtn: 0,
		area : [ '350px', '420px' ],
		shadeClose : true, // 点击遮罩关闭层
		content: [document.getElementById("skm").src,'no']
	});*/
	
	layer.open({
		type : 1,
		title: false,
		closeBtn: 0,
		area : [ '360px', '450px' ],
		shadeClose : true, // 点击遮罩关闭层
		scrollbar: false,
		content: $("#skm")
	});
	$("#skm").removeClass("hide");
}
