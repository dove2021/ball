var prefix = '/sys/cash';

layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#uploaPic', 			//绑定元素
        url: prefix + '/uploadPic/1', //上传接口
        size: 1000,
        multiple: true,
        done: function (r) {
        	if (r.code == 1){
        		layer.msg(r.msg);
        		prePic();
        	} else {
        		layer.msg(r.msg);
        	}
        },
        error: function (r) {
            layer.msg(r.msg);
        }
    });
});

layui.use('upload', function () {
	var upload = layui.upload;
	//执行实例
	var uploadInst = upload.render({
		elem: '#uploaPic2', 			//绑定元素
		url: prefix + '/uploadPic/2', //上传接口
		size: 1000,
		multiple: true,
		done: function (r) {
			if (r.code == 1){
				layer.msg(r.msg);
				prePic2();
			} else {
				layer.msg(r.msg);
			}
		},
		error: function (r) {
			layer.msg(r.msg);
		}
	});
});

layui.use('upload', function () {
	var upload = layui.upload;
	//执行实例
	var uploadInst = upload.render({
		elem: '#uploaPic3', 			//绑定元素
		url: prefix + '/uploadPic/3', //上传接口
		size: 1000,
		multiple: true,
		done: function (r) {
			if (r.code == 1){
				layer.msg(r.msg);
				prePic3();
			} else {
				layer.msg(r.msg);
			}
		},
		error: function (r) {
			layer.msg(r.msg);
		}
	});
});

$(function(){
	prePic();
	prePic2();
});

function prePic(){
	$.ajax({
		type: 'get',
		url: prefix + '/files/1',
		dataType: 'json',
		success: function(data){
			if (data != null){
				$('#prePic').empty();
				$.each(data, function(i, n){
					$('#prePic').append('<img src="' + prefix + '/loadPic/' + n.id + '" alt="" class="img-pre">')
				});
			}
		}
	
	});
}

function prePic2(){
	$.ajax({
		type: 'get',
		url: prefix + '/files/2',
		dataType: 'json',
		success: function(data){
			if (data != null){
				$('#prePic2').empty();
				$.each(data, function(i, n){
					$('#prePic2').append('<img src="' + prefix + '/loadPic/' + n.id + '" alt="" class="img-pre">')
				});
			}
		}
	
	});
}

function prePic3(){
	$.ajax({
		type: 'get',
		url: prefix + '/files/3',
		dataType: 'json',
		success: function(data){
			if (data != null){
				$('#prePic3').empty();
				$.each(data, function(i, n){
					$('#prePic3').append('<img src="' + prefix + '/loadPic/' + n.id + '" alt="" class="img-pre">')
				});
			}
		}
	
	});
}

$(function(){
	
	$("#zfb_charge").on('click', function(){
		layer.confirm('确定更新支付宝入金？', {
			btn : [ '确定', '取消' ]
		}, function() {
			var chargeSwitch = $("input[name='zfb_chargeSwitch']:checked").val();
			var chargeTimes = $("input[name='zfb_chargeTimes']").val();
			var chargeFee = $("input[name='zfb_chargeFee']").val();
			
			$.ajax({
				type: 'post',
				url : prefix + "/save/1",
				data: {"chargeSwitch":chargeSwitch,"chargeTimes":chargeTimes,"chargeFee":chargeFee},
				dataType: 'json',
				success: function(data){
					if (data.code == '1'){
						layer.msg(data.msg, {icon:1});
					} else {
						layer.msg(data.msg, {icon:2});
					}
				}
			});
		});
	});
	
	$("#zfb_withdraw").on('click', function(){
		layer.confirm('确定更新支付宝出金？', {
			btn : [ '确定', '取消' ]
		}, function() {
			var withdrawSwitch = $("input[name='zfb_withdrawSwitch']:checked").val();
			var withdrawMin = $("input[name='zfb_withdrawMin']").val();
			var withdrawMax = $("input[name='zfb_withdrawMax']").val();
			var withdrawTimes = $("input[name='zfb_withdrawTimes']").val();
			var withdrawFee = $("input[name='zfb_withdrawFee']").val();
			
			$.ajax({
				type: 'post',
				url : prefix + "/save/1",
				data: {"withdrawSwitch":withdrawSwitch,"withdrawMin":withdrawMin,"withdrawMax":withdrawMax,"withdrawTimes":withdrawTimes,"withdrawFee":withdrawFee},
				dataType: 'json',
				success: function(data){
					if (data.code == '1'){
						layer.msg(data.msg, {icon:1});
					} else {
						layer.msg(data.msg, {icon:2});
					}
				}
			});
		});
	});
	
	$("#wx_charge").on('click', function(){
		layer.confirm('确定更新微信入金？', {
			btn : [ '确定', '取消' ]
		}, function() {
			var chargeSwitch = $("input[name='wx_chargeSwitch']:checked").val();
			var chargeTimes = $("input[name='wx_chargeTimes']").val();
			var chargeFee = $("input[name='wx_chargeFee']").val();
			
			$.ajax({
				type: 'post',
				url : prefix + "/save/2",
				data: {"chargeSwitch":chargeSwitch,"chargeTimes":chargeTimes,"chargeFee":chargeFee},
				dataType: 'json',
				success: function(data){
					if (data.code == '1'){
						layer.msg(data.msg, {icon:1});
					} else {
						layer.msg(data.msg, {icon:2});
					}
				}
			});		
		});
	});
	
	$("#wx_withdraw").on('click', function(){
		layer.confirm('确定更新微信出金？', {
			btn : [ '确定', '取消' ]
		}, function() {
			var withdrawSwitch = $("input[name='wx_withdrawSwitch']:checked").val();
			var withdrawMin = $("input[name='wx_withdrawMin']").val();
			var withdrawMax = $("input[name='wx_withdrawMax']").val();
			var withdrawTimes = $("input[name='wx_withdrawTimes']").val();
			var withdrawFee = $("input[name='wx_withdrawFee']").val();
			
			$.ajax({
				type: 'post',
				url : prefix + "/save/2",
				data: {"withdrawSwitch":withdrawSwitch,"withdrawMin":withdrawMin,"withdrawMax":withdrawMax,"withdrawTimes":withdrawTimes,"withdrawFee":withdrawFee},
				dataType: 'json',
				success: function(data){
					if (data.code == '1'){
						layer.msg(data.msg, {icon:1});
					} else {
						layer.msg(data.msg, {icon:2});
					}
				}
			});		
		});
	});

});
