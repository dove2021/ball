var prefix = "/operate/schedule"

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
		data : $('#scheduleAddForm').serialize(),// 你的formid
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
	$("#scheduleAddForm").validate({
		rules: {
			memberCode: {
				required : true
			},
			title: {
				required : true
			},
			aName: {
				required : true
			},
			bName: {
				required : true
			},
			startTime: {
				required : true
			},
			oddsWin: {
				required : true,
				number:true
			},
			oddsDraw: {
				required : true,
				number:true
			},
			oddsLose: {
				required : true,
				number:true
			}
		},
		messages: {
			memberCode: {
				required : "请输入 会员编号"
			},
			title: {
				required : "请输入  标题"
			},
			aName: {
				required : "请输入  甲方"
			},
			bName: {
				required : "请输入  乙方"
			},
			startTime: {
				required : "请输入  比赛开始时间"
			},
			oddsWin: {
				required : "请输入 甲赢赔率",
				number:true
			},
			oddsDraw: {
				required : "请输入 甲乙平赔率",
				number:true
			},
			oddsLose: {
				required : "请输入 甲输赔率",
				number:true
			}
		}
	})
}

	  
//执行一个laydate实例
laydate.render({
	elem: '#startTime', //指定元素
	type: 'datetime'
});

