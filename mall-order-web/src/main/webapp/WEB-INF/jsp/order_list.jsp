<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">

<title>我的订单</title>

</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h3>我的订单</h3>
			</div>
		</div>

		<c:if test="${ empty orderInfoResult.items }">
			<div class="row">
				<div class="col-md-12">
					<p>您还没有订单</p>
				</div>
			</div>
		</c:if>

		<c:if test="${ not empty orderInfoResult.items }">
		<!-- 订单列表 -->
		<c:forEach items="${ orderInfoResult.items }" var="orderInfo">
			<!-- 订单详情 -->
			<div class="row">
				<div class="col-md-12">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th><fmt:formatDate value="${ orderInfo.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></th>
								<th>订单号：${ orderInfo.orderId }</th>
								<th></th>
								<th></th>
								<th>订单状态：<c:if test="${ orderInfo.status == 1 }">待付款</c:if>
									<c:if test="${ orderInfo.status == 2 }">已完成</c:if>
								</th>
							</tr>
						</thead>
						<tbody>
							<!-- 订单项信息 -->
							<c:forEach items="${ orderInfo.orderItems }" var="item">
								<tr>
									<th><img alt="${ item.title }" src="${ item.picPath }"
										width="200px" height="200px" /></th>
									<th>${ item.title }</th>
									<th>${ item.price/100 }</th>
									<th>${ item.num }</th>
									<th>${ item.totalFee/100 }</th>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</c:forEach>
		<jsp:include page="footer.jsp"></jsp:include>
		</c:if>
		
	</div>

</body>
</html>