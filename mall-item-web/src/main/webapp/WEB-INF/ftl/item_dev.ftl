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
<title>商品详情</title>
</head>
<body>
	<#-- 导航条 -->
	
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
									<input type="text" class="form-control"
										placeholder="Search for..."> <span
										class="input-group-btn">
										<button class="btn btn-default" type="button">
											<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
										</button>
									</span>
								</div>
							</div>
							<div class="col-md-3">
								<ul id="loginbar" class="nav navbar-nav navbar-right">
									<li><a href="http://localhost:8088/page/login">登录</a></li>
									<li><a href="http://localhost:8088/page/register">注册</a></li>
									<li><a href="http://localhost:8091/cart/cart.html">购物车</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</nav>
		</div>
	</div>
	
	<#-- 商品详情 -->
	<div class="container-fluid">

		<div class="row">
			<div class="col-md-10 col-md-offset-2">
				<#-- 页面标题 -->
				<h3>商品详情</h3>
			</div>
		</div>

		<#-- 商品详情 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-2">
				<img alt="商品图片" src="${item.image}" width="430" height="430">
			</div>
			<div class="col-md-4">
				<div class="row">
					<div class="col-md-12">
						<h4>${ item.title }</h4>
						<p>商品ID：${ item.id?c }</p>
						<p>市场价格：${ (item.price/100) }</p>
					</div>
				</div>

				<div class="row">
					<input type="hidden" name="id" id="id" value="${ item.id?c }" />

					<div class="col-md-12">
						<label for="num">商品数量：</label>
						<input type="text" name="num" id="num" value="1" />
					</div>
					<div class="col-md-12 text-right">
						<input type="button" value="添加到购物车" id="addToCart" class="btn btn-default" />
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 text-right">
						<p id="addToCartHelpText"></p>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-10 col-md-offset-2">
				<h3>商品描述</h3>
				<div>${ itemDesc.itemDesc }</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="/js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(function(){
		// 搜索按钮
		$("#searchBtn").click(function(){
			var searchText = $("#searchText").val();
			location.href = 'http://search.mall.rainyrun.top/search.html?q=' + searchText; 
		});

		// 购物车按钮
		$("#addToCart").click(function(){
			var num = $("#num").val();
			var id = $("#id").val();
			var url = "http://localhost:8091/cart/add/" + id + ".html?num=" + num;
			$.ajax({
				type: "GET",
				url: url,
				dataType: 'json',
				xhrFields: { // 允许携带Cookie
			       withCredentials: true
			    },
			    crossDomain: true, // 允许跨域
			    success: function(data){
			    	if(data.status == 200){
			    		var addToCartHelpText = $("#addToCartHelpText");
			    		addToCartHelpText.html("成功添加到购物车");
			    		var timerId = setTimeout(function(){
						  addToCartHelpText.html("");
						}, 3000);
			    	} else {
			    		var addToCartHelpText = $("#addToCartHelpText");
			    		addToCartHelpText.html(data.msg);
			    		var timerId = setTimeout(function(){
						  addToCartHelpText.html("");
						}, 3000);
			    	}
			    },
			    error: function(){
			    	console.log("item: addToCart: error");
			    }
			});
		});
		
		// 导航条展示
		var _ticket = $.cookie("MALL_TOKEN");// 从cookie获取数据 token
		if(!_ticket){
			return ;
		}
		var url = "http://localhost:8088/user/token/" + _ticket;
		$.get(url, function(data){
			var data = eval("("+data+")");//转换为json对象
			if(data.status == 200){
				var username = data.data.username;
				var html = '<li><a href="#">你好，' + username + '</a></li><li><a href="http://localhost:8088/user/logout">退出</a></li><li><a href="http://localhost:8091/cart/cart.html">购物车</a></li><li><a href="http://localhost:8093/order/order_list.html">我的订单</a></li>';
				$("#loginBar").html(html);
			}
		});

	});
	</script>
	
</body>
</html>