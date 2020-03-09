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

<title>商城首页</title>
</head>
<body>

	<!-- 导航条 -->
	<jsp:include page="header.jsp"></jsp:include>

	<!-- 轮播图模块 -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<span>侧边导航</span>
			</div>
			<div class="col-md-6">
				<!-- 轮播图 -->
				<div id="carousel" class="carousel slide" data-ride="carousel"
					data-interval="2000">
					<!-- Indicators 指示器 -->
					<ol class="carousel-indicators">
						<li data-target="#carousel" data-slide-to="0" class="active"></li>
						<c:forEach var="i" items="${ carousels }" varStatus="status">
							<c:if test="${ status.count - 1 != 0 }">
								<li data-target="#carousel"
									data-slide-to="${ status.count - 1 }"></li>
							</c:if>
						</c:forEach>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<!-- 图片 -->
							<a href="${ carousels[0].url }"> <img
								src="${ carousels[0].pic }" alt="${ carousels[0].title }"
								width="1130px" height="500px">
							</a>
							<!-- 字幕 -->
							<%-- <div class="carousel-caption">
								<h3>${ carousels[0].title }</h3>
								<p>${ carousels[0].titleDesc }</p>
							</div> --%>
						</div>
						<c:forEach var="carousel" items="${ carousels }"
							varStatus="status">
							<c:if test="${ status.count - 1 != 0 }">
								<div class="item">
									<!-- 图片 -->
									<a href="${ carousel.url }"> <img src="${ carousel.pic }"
										alt="${ carousel.title }" width="1130px" height="500px">
									</a>
									<!-- 字幕 -->
									<%-- <div class="carousel-caption">
										<h3>${ carousel.title }</h3>
										<p>${ carousel.titleDesc }</p>
									</div> --%>
								</div>
							</c:if>
						</c:forEach>
					</div>

					<!-- Controls 控制器 -->
					<a class="left carousel-control" href="#carousel" role="button"
						data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#carousel" role="button"
						data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			<div class="col-md-3 text-right">
				<span>右侧广告</span>
			</div>
		</div>

		<!-- 最新商品 -->
		<div class="row">
			<div class="col-md-12">
				<h4>最新商品</h4>
			</div>

			<c:forEach var="item" items="${ latestItem }">
				<div class="col-md-3">
					<a href="http://item.mall.rainyrun.top/item/${ item.id }.html"><img
						alt="${ item.title }" src="${ item.image }" width="200px"
						height="200px"></a>
					<h5 class="item-title">${ item.title }</h5>
					<p>${ item.price / 100 }</p>
				</div>
			</c:forEach>
		</div>
	</div>


	<!-- js -->
	<!-- <script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script> -->
</body>
</html>