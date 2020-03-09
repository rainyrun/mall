<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">

<title>确认订单</title>

</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h4>确认订单信息</h4>
			</div>
		</div>

		<!-- 订单详细信息 -->
		<!-- 订单信息（不展示，当确定订单时，用来传递信息） -->
		<div class="row">
			<form id="orderForm" class="hide" action="/order/add.html"
				method="post">
				<input type="hidden" name="paymentType" value="1" />
				<c:forEach items="${ orderItems }" var="item" varStatus="status">
					<c:set var="totalPrice"
						value="${ totalPrice + (item.price * item.num)}" />
					<input type="hidden" name="orderItems[${status.index}].num"
						value="${item.num }" />
					<input type="hidden" name="orderItems[${status.index}].price"
						value="${item.price}" />
					<input type="hidden" name="orderItems[${status.index}].totalFee"
						value="${item.price * item.num}" />
					<input type="hidden" name="orderItems[${status.index}].title"
						value="${item.title}" />
					<input type="hidden" name="orderItems[${status.index}].picPath"
						value="${item.image}" />
					<input type="hidden" name="orderItems[${status.index}].itemId"
						value="${item.id }" />
				</c:forEach>
				<input type="hidden" name="payment" value="${totalPrice/100 }"/>" />
			</form>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
					</thead>
					<tbody>
						<!-- 订单项信息 -->
						<c:forEach items="${ orderItems }" var="item">
							<tr>
								<th><img alt="${ item.title }" src="${ item.image }"
									width="200px" height="200px" /></th>
								<th>${ item.title }</th>
								<th>${ item.price / 100 }</th>
								<th>${ item.num }</th>
								<th>${ item.price * item.num / 100 }</th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<!-- 订单金额 -->
		<div class="row">
			<div class="col-md-12">
				<p class="text-right">总金额：${ totalPrice/100 }</p>
			</div>
		</div>

		<!-- 收货信息标题 -->
		<!-- <div class="row">
			<div class="col-md-12">
				<h3>收货信息</h3>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				收货信息表单
				<form class="form-horizontal">
					<div class="form-group">
						<label for="name" class="col-md-4 control-label">收货人：</label>
						<div class="col-md-6">
							<input type="text" name="name" id="name" placeholder="请输入收货人姓名">
						</div>
					</div>

					<div class="form-group">
						<label for="address" class="col-md-4 control-label">收货地址：</label>
						<div class="col-md-6">
							<input type="text" name="address" id="address"
								placeholder="请输入收货地址">
						</div>
					</div>

					<div class="form-group">
						<label for="phone" class="col-md-4 control-label">电话：</label>
						<div class="col-md-6">
							<input type="tel" name="phone" id="phone" placeholder="请输入联系电话">
						</div>
					</div>

					
				</form>
			</div>
		</div> -->

		<!-- 提交订单 -->
		<div class="row">
			<div class="col-md-12 text-right">
				<input type="button" class="btn btn-default" id="submitOrder" value="提交订单">
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="/js/confirm_order.js"></script>
</body>
</html>