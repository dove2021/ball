var prefix = "/operate/charge";

$(function(){
	
	$("#btn-submit").on('click',function(){
		
		var id = $("#id").val();
		var confirmAmount = $("#confirmAmount").val();
		var info = $("#info").val();
		
		layer.confirm('确定客户入金 ' + confirmAmount + " ?", {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				type : "get",
				data : {"info":info},
				url : prefix + "/pass/" + id + "/" + confirmAmount,
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
		
		layer.confirm('确定此入金申请为无效？', {
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
						parent.layer.msg("申请更改为无效", {icon:1});
					} else {
						parent.layer.msg(data.msg, {icon: 2});
					}
				}
			});
		}, function(){});
		
	});
	
});
	
