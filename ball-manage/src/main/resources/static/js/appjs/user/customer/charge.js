var prefix = "/user/customer";

function charge(){
	
	var id = $("#id").val();
	var confirmAmount = $("#confirmAmount").val();
	var channelId = $("input[name='channelId']:checked").val();
	var operateInfo = $("#operateInfo").val();
	
	if (confirmAmount == null || confirmAmount.trim() == ''){
		layer.msg('入金金额不能为空', {icon: 2}); 
		return;
	}
	if (parseFloat(confirmAmount) <= 0){
		layer.msg('入金金额不能为 0或负', {icon: 2}); 
		return;
	}
	if (channelId == null || channelId.trim() == ''){
		layer.msg('入金渠道不能为空', {icon: 2}); 
		return;
	}
	
	layer.confirm("确认给该客户入金" + confirmAmount + " 吗?", 
		{btn: [ '确定', '取消' ]},
		function(){
			$.ajax({
				type: 'get',
				url: prefix + '/charge/' + id + "/" + confirmAmount + "/" + channelId + "/" + (operateInfo == null || operateInfo.trim() == "" ? "-" : operateInfo),
				dataType: 'json',
				success: function(r) {
					if (r.code == 1) {
						parent.reLoad();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);
						parent.layer.msg("操作成功",{icon: 1}, function(){
						});
					} else {
						layer.msg(r.msg, {icon: 2});
					}
				}
			});
			
		},
		function(){}
	);
	
}

