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
<title>购物车</title>
<style type="text/css">
.table {
	padding: 20px;
}
</style>
</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">

		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h4>购物车</h4>
			</div>
		</div>

		<!-- 提示信息 -->
		<div class="row">
			<div class="col-md-12">
				<c:if test="${ empty cart }">
					<span>购物车为空</span>
					<br />
				</c:if>
			</div>
		</div>

		<!-- 购物车主体 -->
		<div class="row">
			<c:if test="${ not empty cart }">
				<!-- 购物车中的商品 -->
				<div class="col-md-12">
					<!-- 商品项列表 -->
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="selectAll"><span>  全选</span></th>
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- cart.values取得的是cart对象getValues的返回值 -->
							<c:forEach items="${ cart }" var="item">
								<tr id="${ item.id }">
									<td><input type="checkbox" class="selectedItems"></td>
									<td><img alt="${ item.title }" src="${ item.image }" width="200" height="200" /></td>
									<td><a href="http://item.mall.rainyrun.top/item/${ item.id }.html"><span>${ item.title }</span></a></td>
									<td><span class="price">${ item.price / 100 }</span></td>
									<td>
										<input type="text" value="${ item.num }" name="num" class="num">
									</td>
									<td><span class="subTotal">${ item.price * item.num / 100 }</span></td>
									<td><a href="/cart/delete/${ item.id }.html">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- 清空or提交 -->
				<div class="col-md-12 text-right">
					<input id="toOrder" type="button" value="结算" class="btn btn-default">
					<div id="orderAlert"></div>
				</div>
			</c:if>
		</div>
	</div>
	
	<script type="text/javascript" src="/js/cart.js"></script>
</body>
</html>