var prefix = "/operate/schedule"

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
	$.ajax({
		type: "POST",
		url: prefix + "/update",
		data: $('#scheduleUpdForm').serialize(),// 你的formid
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if (data.code == '1') {
				parent.reLoad();
				parent.layer.msg("操作成功",{icon: 1});
			} else {
				parent.layer.alert(data.msg)
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#scheduleUpdForm").validate({
		rules : {
		},
		messages : {
		}
	})
}

//执行一个laydate实例
laydate.render({
	elem: '#createDate', //指定元素
	type: 'datetime'
});

//执行一个laydate实例
laydate.render({
	elem: '#startTime', //指定元素
	type: 'datetime'
});