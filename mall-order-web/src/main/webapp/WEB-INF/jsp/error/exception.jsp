<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="/css/bootstrap.min.css"
	rel="stylesheet">

<title>错误页</title>
</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<!-- 错误信息 -->
	<div class="container-fluid">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h4>出错了！</h4>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<p>请稍后重试</p>
			</div>
		</div>
	</div>
	
	
	
</body>
</html>