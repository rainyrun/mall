$(function(){
	// 文档加载时
	// 用户名、密码、手机号、邮箱需要验证，用户名、密码必填
	$(".validate").change(function(){
		// 获取填入的内容
		var text = $(this).val().trim();
		var id = $(this).attr("id");
		// 定位提示信息
		var alertSpan = $(this).next();
		// 清空注册按钮的提示信息
		$("#helpText").html("");
		// 用户名密码不能为空
		if (text == "") {
			if (id == "username"){
				$(this).parent().addClass("has-error");
				alertSpan.html("用户名不能为空");
			}
			else if (id == "password"){
				$(this).parent().addClass("has-error");
				alertSpan.html("密码不能为空");
			}
			else {
				$(this).parent().removeClass("has-error");
				alertSpan.html("");
			}
			
		}
		else {// 验证数据是否可用
			var url = "/user/check/" + text + "/" + id;
			var parent = $(this).parent();
			$.get(url, function(data){
				if(data.status == 200){// 数据可用
					parent.removeClass("has-error");
					parent.addClass("has-success");
					// 清除提示信息
					alertSpan.html("");
				}
				else{// 数据重复
					parent.addClass("has-error");
					parent.removeClass("has-success");
					alertSpan.html(data.msg);
				}
			})
		}
	});
	
	// 验证信息不通过时，不能注册。
	$("#registerSubmit").click(function(){
		// 筛选含有has-error的元素
		var error = $("[class*=has-error]");
		// 没有找到
		if (error == null || error.length == 0){
			// 提交表单
			var param = $("#registerForm").serialize();
			var url = "/user/register";
			$.post(url, param, function(data){
				var registerAlert = $("#registerAlert");
				registerAlert.html("");
				if(data.status == 200){
					registerAlert.removeClass("alert-danger");
					registerAlert.addClass("alert-success");
					registerAlert.append("<p>注册成功，将自动跳转到登录页面...</p>");
					var redirect = document.referrer;
					// 创建一个定时器，3000毫秒后执行，返回定时器的标示
					var timerId = setTimeout(function(){
						location.href = "http://sso.mall.rainyrun.top/page/login?redirect=" + redirect; // 跳转到登录页面
					}, 3000);
				} else {
					registerAlert.removeClass("alert-success");
					registerAlert.addClass("alert-danger");
					registerAlert.append("<p>" + data.msg + "</p>");
					return false;
				}
			});
		} else { // 有错误信息
			var msg = null;
			error.each(function(i, e){
				var lable = $(e).children()[0];
				var errorField = $(lable).text();
				if(msg == null)
					msg = errorField;
				else
					msg = msg + ", " + errorField;
			});
			msg = msg + "存在错误";
			// 加入提示信息
			var helpText = $("#helpText");
			helpText.text(msg);
			return false;
		}
	});
});