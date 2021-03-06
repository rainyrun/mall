<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../../css/bootstrap.min.css" rel="stylesheet">

<title>登录页面</title>
</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<!-- 登录模块 -->
	<div class="container-fluid">
		<!-- 登录成功or失败提示 -->
		<div class="row">
			<div class="col-md-12">
				<div class="alert" role="alert" id="loginAlert"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h3>登录</h3>
			</div>
		</div>
		<div class="row">
		<div class="col-md-8 col-md-offset-2">
		<form action="" id="loginForm">
				<div class="form-group has-error">
					<label for="username">用户名</label>
					<input type="text" class="form-control validate" id="username" name="username"
						placeholder="请输入用户名（必填）" aria-describedby="usernameAlert">
  					<span id="usernameAlert" class="help-block"></span>
				</div>
				<div class="form-group has-error">
					<label for="password">密码</label>
					<input type="password" class="form-control validate" id="password" name="password"
						placeholder="请输入密码（必填）" aria-describedby="passwordAlert">
  					<span id="passwordAlert" class="help-block"></span>
				</div>
				<button type="button" class="btn btn-default" id="loginSubmit">登录</button>
				<p id="helpText"></p>
			</form>
			<input type="hidden" id="redirect" value="${ redirect }">
		</div>
		</div>
	</div>

	<!-- js -->
	<!-- <script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script> -->
	<script type="text/javascript" src="../../js/login.js"></script>
</body>
</html>