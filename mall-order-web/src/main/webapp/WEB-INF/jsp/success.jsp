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

<title>订单提交成功</title>

</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h4>订单提交成功</h4>
				<p>订单号：<span id="orderId">${ orderId }</span></p>
			</div>
			<div class="col-md-12">
				<button id="toPay">支付</button>
			</div>
		</div>
	</div>
	
	<script>
		$(function(){
			// 支付按钮
			$("#toPay").click(function(){
				var orderId = $("#orderId").text();
				alert(orderId);
				var url = "/pay/" + orderId + ".html";
				$.get(url, function(data){
					if(data.status == 200){
						alert("支付成功");
					}
					else{
						alert("支付失败");
					}
				});
			});
		});
	
	</script>

</body>
</html>