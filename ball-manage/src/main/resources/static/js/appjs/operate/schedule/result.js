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

	$.ajax({
		type: "POST",
		url: prefix + "/result/save",
		data: $('#scheduleResultForm').serialize(),// 你的formid
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if (data.code == 1) {
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
				parent.reLoad();
				parent.layer.msg("操作成功",{icon: 1});
			} else {
				parent.layer.alert(data.msg,{icon: 2})
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#scheduleResultForm").validate({
		rules : {
			aScore: {
				required : true,
				number:true
			},
			bScore: {
				required : true,
				number:true
			},
			endTime: {
				required : true
			},
			gameResult: {
				required : true
			}
		},
		messages : {
			aScore: {
				required : "请输入 甲方得分",
				number: "请输入 数字类型"
			},
			bScore: {
				required : "请输入 乙方得分",
				number: "请输入 数字类型"
			},
			endTime: {
				required : "请输入 比赛结束时间"
			},
			gameResult: {
				required : "请输入 比赛结果"
			}			
		}
	})
}

//执行一个laydate实例
laydate.render({
	elem: '#endTime', //指定元素
	type: 'datetime'
});

//执行一个laydate实例
laydate.render({
	elem: '#startTime', //指定元素
	type: 'datetime'
});

