$(function(){
	// 文档加载时
	// 用户名、密码必填
	$(".validate").change(function(){
		$("#helpText").html(""); // 清空提示信息
		// 获取填入的内容
		var text = $(this).val().trim();
		var id = $(this).attr("id");
		var alertSpan = $(this).next();
		// 用户名密码不能为空
		if (text == "") {
			$(this).parent().addClass("has-error");
			alertSpan.html("该项不能为空");
		}
		else {
			$(this).parent().removeClass("has-error");
			alertSpan.html("");
		}
	});
	
	// 验证信息不通过时，不能注册。
	$("#loginSubmit").click(function(){
		// 筛选含有has-error的元素
		var error = $("[class*=has-error]");
		// 没有找到
		if (error == null || error.length == 0){
			// 提交表单
			$.post("/user/login", $("#loginForm").serialize(), function(data){
				var loginAlert = $("#loginAlert");
				loginAlert.html("");
				if(data.status == 200){
					loginAlert.removeClass("alert-danger");
					// 跳转链接
					var redirect = document.referrer;
					var url = decodeURI(document.URL);
					var redirectIndex = url.indexOf("redirect=");
					if(redirectIndex >= 0)
						redirect = url.substring(redirectIndex + 9);
					if(redirect != null && redirect != "" && redirect != url){ // 有跳转链接
						loginAlert.addClass("alert-success");
						loginAlert.append("<p>登录成功，稍后将自动跳转到登录前到页面...</p>");
						// 创建一个定时器，3000毫秒后执行，返回定时器的标示
						var timerId = setTimeout(function(){
							location.href = redirect; // 跳转到注册前的页面
						}, 3000);
					} else {
						loginAlert.addClass("alert-success");
						loginAlert.append("<p>登录成功</p>");
					}
				} else {
					loginAlert.removeClass("alert-success");
					loginAlert.addClass("alert-danger");
					loginAlert.append("<p>" + data.msg + "</p>");
					return false;
				}
			});
		}
		else { // 有错误信息
			var msg = null;
			error.each(function(i, e){
				var lable = $(e).children()[0];
				var errorField = $(lable).text();
				if(msg == null)
					msg = errorField;
				else
					msg = msg + ", " + errorField;
			});
			msg = msg + "不能为空";
			// 加入提示信息
			var helpText = $("#helpText");
			helpText.text(msg);
		}
		return false;
	});
});