var prefix = "/user/customer";

function withdraw(){
	
	var id = $("#id").val();
	var withdrawAmount = $("#withdrawAmount").val();
	var withdrawFee = $("#withdrawFee").val();
	var channelId = $("input[name='channelId']:checked").val();
	
	if (withdrawAmount == null || withdrawAmount.trim() == ''){
		layer.msg('出金金额不能为空', {icon: 2}); 
		return;
	}
	if (parseFloat(withdrawAmount) <= 0){
		layer.msg('出金金额不能为 0或负', {icon: 2}); 
		return;
	}
	if (withdrawFee == null || withdrawFee.trim() == ''){
		layer.msg('出金手续费不能为空', {icon: 2}); 
		return;
	}
	if (channelId == null || channelId.trim() == ''){
		layer.msg('出金渠道不能为空', {icon: 2}); 
		return;
	}
	
	layer.confirm("确认给该客户出金" + withdrawAmount + " 吗?", 
		{btn: [ '确定', '取消' ]},
		function(){
			$.ajax({
				type: 'get',
				url: prefix + '/withdraw/' + id + "/" + withdrawAmount + "/" + withdrawFee + "/" + channelId,
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
	
	
/*	layer.prompt({
		formType: 0,
		value: balance,
		title: '手工出金',
		area: ['800px', '350px'] //自定义文本域宽高
	}, function(value, index, elem){
		var reg = new RegExp("^-?\\d+$");
		if (!reg.test(value)){
			layer.msg('请输入整数金额');
		} else {
			layer.confirm("确认给该客户出金" + value + " 吗?", 
				{btn: [ '确定', '取消' ]},
				function(){
					layer.close(index);
					$.ajax({
						type: 'get',
						url: prefix + '/withdraw/' + id + "/" + value,
						success: function(r) {
							if (r.code == 1) {
								layer.msg(r.msg, {icon:1});
								reLoad();
							} else {
								layer.msg(r.msg);
							}
						}
					});
					
				},
				function(){}
			);
		}
	});	
	
	*/
}

