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
<style type="text/css">
	.item-title{
		display: inline-block;
		white-space: nowrap;
		width: 100%;
		overflow: hidden;
		text-overflow:ellipsis;
	}
</style>
<title>搜索结果页</title>
</head>
<body>

	<!-- 导航条(搜索框) -->
	<jsp:include page="header.jsp"></jsp:include>

	<!-- 搜索结果 -->
	<div class="container-fluid">
		<div class="row">
			<c:forEach var="item" items="${ searchResult.items }">
				<div class="col-md-3">
					<a href="http://item.mall.rainyrun.top/item/${ item.id }.html"><img alt="${ item.title }" src="${ item.image }" width="200px" height="200px"></a>
					<h5 class="item-title">${ item.title }</h5>
					<p>${ item.price / 100 }</p>
					<p>${ item.catName }</p>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<!-- 页脚(分页模块) -->
	<jsp:include page="footer.jsp"></jsp:include>

	<!-- js -->
	<!-- <script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script> -->
</body>
</html>