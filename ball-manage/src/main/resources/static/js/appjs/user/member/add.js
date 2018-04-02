var prefix = "/user/member"

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
		data : $('#memberAddForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 1) {
				parent.layer.msg("操作成功",{icon: 1}, function(){
					var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
					parent.layer.close(index);
					parent.reLoad();
				});
			} else {
				parent.layer.alert(data.msg, {icon: 2})
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#memberAddForm").validate({
		rules: {
			memberCode: {
				required : true,
				minlength: 5,
				maxlength: 5,
				remote : {
					type : "get", // 数据发送方式
					url : prefix + "/exist", // 后台处理程序
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						memberCode : function() {
							return $("#memberCode").val();
						}
					}
				}
			},
			name: {
				required : true
			},
			loginName: {
				required : true,
				minlength : 5,
				remote : {
					type : "get", // 数据发送方式
					url : prefix + "/exist/loginName", // 后台处理程序
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						loginName : function() {
							return $("#loginName").val();
						}
					}
				}
			},
			password: {
				required : true,
				minlength : 6
			},
			linkman: {
				required : true
			},
			phoneNo: {
				required : true
			}
		},
		messages: {
			memberCode: {
				required: icon + "请输入用户编号",
				minlength: icon + "会员编号为5位",
				maxlength: icon + "会员编号为5位",
				remote: icon + "会员编号已经存在"
			},
			name: {
				required: icon + "请输入会员名称"
			},
			loginName: {
				required: icon + "请输入用户名",
				minlength: icon + "用户名必须5个字符以上",
				remote: icon + "用户名已经存在"
			},
			password: {
				required: icon + "请再次输入密码",
				minlength: icon + "密码必须6个字符以上"
			},
			linkman: {
				required: icon + "联系人不能为空"
			},
			phoneNo: {
				required: icon + "联系人手机号不能为空"
			}
		}
	})
}
