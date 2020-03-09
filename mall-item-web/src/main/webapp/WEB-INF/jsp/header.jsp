<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%
    // properties 配置文件名称 ReadSource.class.getPackage().toString()
    ResourceBundle res = ResourceBundle.getBundle("properties/jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	body {
		padding-top: 80px;
	}
	
	.title {
		font-size: 25px;
		margin-top: 7px;
	}
</style>
<title>导航条</title>
</head>
<body>

	<!-- 商城抬头 -->
	<div class="container-fluid">
		<!-- 导航条 -->
		<div class="container-fluid">
			<nav class="navbar navbar-default navbar-fixed-top">
				<div class="container-fluid">
					<div class="collapse navbar-collapse">
						<div class="row">
							<div class="col-md-3 title">
								<ul class="nav navbar-nav navbar-left">
									<li><span>购物商城</span></li>
								</ul>
							</div>
							<div class="col-md-6" style="margin-top: 8px">
								<div class="input-group">
									<input type="text" class="form-control" id="searchText"
										placeholder="Search for..."> <span
										class="input-group-btn">
										<button class="btn btn-default" type="button" id="searchBtn">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										</button>
									</span>
								</div>
							</div>
							<div class="col-md-3">
								<ul id="loginBar" class="nav navbar-nav navbar-right">
									<li><a href="<%=res.getString("MALL_SSO_URL")%>/page/login">登录</a></li>
									<li><a href="<%=res.getString("MALL_SSO_URL")%>/page/register">注册</a></li>
									<li><a href="<%=res.getString("MALL_CART_URL")%>/cart/cart.html">购物车</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<input id="mall_sso_url" type="hidden" value="<%=res.getString("MALL_SSO_URL")%>">
	<input id="mall_cart_url" type="hidden" value="<%=res.getString("MALL_CART_URL")%>">
	<input id="mall_order_url" type="hidden" value="<%=res.getString("MALL_ORDER_URL")%>">
	<input id="mall_search_url" type="hidden" value="<%=res.getString("MALL_SEARCH_URL")%>">
	
	<script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(function(){
		// 回显搜索词
		var query = "";
		var url = decodeURI(document.URL);
		var startIndex = url.indexOf("q=");
		if (startIndex > 0){
			query = url.substring(startIndex).split("&")[0].split("=")[1];
		}
		$("#searchText").val(query);
		
		// 搜索按钮
		$("#searchBtn").click(function(){
			var searchText = $("#searchText").val();
			location.href = $("#mall_search_url").val() + '/search.html?q=' + searchText; 
		});
		
		// 导航条展示
		var _ticket = $.cookie("MALL_TOKEN");// 从cookie获取数据 token
		if(!_ticket){
			return ;
		}
		var url = $("#mall_sso_url").val() + "/user/token/" + _ticket;
		$.get(url, function(data){
			var data = eval("("+data+")");//转换为json对象
			if(data.status == 200){
				var username = data.data.username;
				var html = '<li><a href="#">你好，' + username + '</a></li><li><a href="' + $("#mall_sso_url").val() + '/user/logout">退出</a></li><li><a href="' + $("#mall_cart_url").val() + '/cart/cart.html">购物车</a></li><li><a href="' + $("#mall_order_url").val() + '/order/order_list.html">我的订单</a></li>';
				$("#loginBar").html(html);
			}
		});

	});
	</script>
</body>
</html>