var prefix = "/user/trade"

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function getCheckedRoles() {
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}

function update() {
	$("#roleIds").val(getCheckedRoles());
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
	$.ajax({
		type: "POST",
		url: prefix + "/update",
		data: $('#tradeUpdForm').serialize(),// 你的formid
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if (data.code == 1) {
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
	$("#tradeUpdForm").validate({
		rules : {
			password : {
				required : true,
				minlength : 6
			},
			password1 : {
				required : true,
				minlength : 6
			},
			password2 : {
				required : true,
				minlength : 6,
				equalTo : "#password1"
			}
		},
		messages : {
			password : {
				required : icon + "请输入您的密码",
				minlength : icon + "密码必须6个字符以上"
			},
			password1 : {
				required : icon + "请输入新密码",
				minlength : icon + "密码必须6个字符以上"
			},
			password2 : {
				required : icon + "请再次输入新密码",
				minlength : icon + "密码必须6个字符以上",
				equalTo : icon + "两次输入的密码不一致"
			}
		}
	})
}