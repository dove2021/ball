var prefix = "/user/broker"

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/save",
		data : $('#brokerAddForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 1) {
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.reLoad();
				parent.layer.msg("操作成功",{icon: 1});
			} else {
				parent.layer.alert(data.msg, {icon: 2})
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#brokerAddForm").validate({
		rules: {
			nickName: {
				required : true,
				maxlength: 32
			},
			memberCode: {
				required : true
			},
			phoneNo: {
				required : true,
				remote : {
					type : "get", // 数据发送方式
					url : prefix + "/exist/loginName", // 后台处理程序
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						loginName : function() {
							return $("#phoneNo").val();
						}
					}
				}
			},
			password: {
				required : true,
				minlength : 6
			}
		},
		messages: {
			nickName: {
				required: icon + "请输入 代理商名称",
				maxlength: icon + "代理商名称最大不能超过32位",
			},
			memberCode: {
				required: icon + "请选择会员单位",
			},
			phoneNo: {
				required: icon + "请输入 手机号",
				remote: icon + "手机号已经存在"
			},
			loginName: {
				required: icon + "请输入用户名",
				minlength: icon + "用户名必须5个字符以上"
			},
			password: {
				required: icon + "请 输入密码",
				minlength: icon + "密码必须6个字符以上"
			}
		}
	})
}
