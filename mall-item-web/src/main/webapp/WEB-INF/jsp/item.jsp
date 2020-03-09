<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">

<title>商品详情</title>
</head>
<body>
	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">

		<div class="row">
			<div class="col-md-12">
				<!-- 页面标题 -->
				<h2>商品详情</h2>
			</div>
		</div>

		<!-- 商品详情 -->
		<div class="row">
			<div class="col-md-5">
				<img alt="商品图片" src="${item.image}" width="430" height="430">
			</div>
			<div class="col-md-7">
				<div class="row">
					<div class="col-md-12">
						<h3>${ item.title }</h3>
						<p>商品ID：${ item.id }</p>
						<p>市场价格：${ item.price }</p>
					</div>
				</div>

				<div class="row">
					<!-- 添加到购物车 -->
					<input type="hidden" name="id" id="id" value="${ item.id }" />

					<div class="col-md-6">
						<label for="num">商品数量：</label>
						<input
							type="text" name="num" id="num" value="1" />
					</div>
					<div class="col-md-2 col-md-offset-8">
						<input type="button" value="添加到购物车" id="addToCart"
							class="btn btn-default" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<h3>商品描述</h3>
				<div>${ itemDesc.itemDesc }</div>
			</div>
		</div>
	</div>

</body>
</html>