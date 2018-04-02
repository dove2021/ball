var prefix = "/user/member"

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	$.ajax({
		type: "POST",
		url: prefix + "/repwd",
		data: $('#memberPwdForm').serialize(),// 你的formid
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if (data.code == 1) {
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("操作成功",{icon: 1}, function(){
					parent.reLoad();
				});
			} else {
				parent.layer.alert(data.msg)
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#memberPwdForm").validate({
		rules : {
		},
		messages : {
		}
	})
}