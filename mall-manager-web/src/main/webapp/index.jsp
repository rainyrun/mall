<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="./css/bootstrap.min.css" rel="stylesheet">
<title>内容管理后台</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 text-center">
				<h1>欢迎使用购物商城管理后台</h1>
			</div>
		</div>
		<br>
		<!-- 登录成功or失败提示 -->
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="alert" role="alert" id="loginAlert"></div>
            </div>
        </div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<form action="" id="loginForm">
					<div class="form-group has-error">
						<label for="username">用户名</label> <input type="text"
							class="form-control validate" id="username" name="username"
							placeholder="请输入用户名（必填）" aria-describedby="usernameAlert">
						<span id="usernameAlert" class="help-block"></span>
					</div>
					<div class="form-group has-error">
						<label for="password">密码</label> <input type="password"
							class="form-control validate" id="password" name="password"
							placeholder="请输入密码（必填）" aria-describedby="passwordAlert">
						<span id="passwordAlert" class="help-block"></span>
					</div>
					<button type="button" class="btn btn-default" id="loginSubmit">登录</button>
					<p id="helpText"></p>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="./js/easyui/jquery.js"></script>
	<script type="text/javascript">
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
	            $.post("/login", $("#loginForm").serialize(), function(data){
	                var loginAlert = $("#loginAlert");
	                loginAlert.html("");
	                if(data.status == 200){
	                	location.href = "/home"; // 跳转到管理后台首页
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
	</script>
</body>
</html>