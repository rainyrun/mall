<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<title>footer</title>
</head>
<body>
	<div class="container-fluid">
		<!-- 分页按钮 -->
		<div class="row">
			<div class="col-md-12">
				<ul class="list-inline">
					<li><span>共有 ${ orderInfoResult.total } 条结果</span></li>
					<li><span>共有 ${ orderInfoResult.totalPage } 页</span></li>
				</ul>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12 text-center">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<!-- 首页、上一页按钮 禁用 -->
						<c:if test="${ orderInfoResult.currentPage == 1}">
							<!-- 首页 -->
							<li class="disabled"><a
								href="${ orderInfoResult.url }&currentPage=1">首页</a></li>
							<!-- 上一页 -->
							<li class="disabled"><a
								href="${ orderInfoResult.url }&currentPage=${ orderInfoResult.currentPage -1 }"
								aria-label="Previous">上一页</a></li>

						</c:if>

						<!-- 首页、上一页按钮 激活 -->
						<c:if test="${ orderInfoResult.currentPage != 1}">
							<!-- 首页 -->
							<li><a
								href="${ orderInfoResult.url }&currentPage=1">首页</a></li>
							<!-- 上一页 -->
							<li><a
								href="${ orderInfoResult.url }&currentPage=${ orderInfoResult.currentPage -1 }"
								aria-label="Previous">上一页</a></li>
						</c:if>
						<c:forEach var="i" begin="${ orderInfoResult.currentPage }" end="${ (orderInfoResult.currentPage + 5 > orderInfoResult.totalPage) ? orderInfoResult.totalPage : (orderInfoResult.currentPage + 5) }">
							<c:if test="${ i == orderInfoResult.currentPage }">
								<li class="disabled"><a
									href="${ orderInfoResult.url }&currentPage=${ i }">
										${ i } </a>
							</c:if>
							<c:if test="${ i != orderInfoResult.currentPage }">
								<li><a
									href="${ orderInfoResult.url }&currentPage=${ i }">
										${ i } </a>
								<li>
							</c:if>
						</c:forEach>
						<!-- 下一页、尾页按钮 禁用 -->
						<c:if test="${ orderInfoResult.currentPage == orderInfoResult.totalPage}">
							<!-- 下一页 -->
							<li class="disabled"><a href="" aria-label="Next">下一页 </a></li>
						</c:if>

						<!-- 下一页、尾页按钮 激活 -->
						<c:if test="${ orderInfoResult.currentPage != orderInfoResult.totalPage}">
							<!-- 下一页 -->
							<li><a
								href="${ orderInfoResult.url }&currentPage=${ orderInfoResult.currentPage + 1 }"
								aria-label="Next">下一页 </a></li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
	<!-- js -->
	<!-- <script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script> -->
</body>
</html>