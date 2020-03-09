<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/easyui/themes/icon.css">
<title>内容管理后台</title>
</head>
<body class="easyui-layout">
	<!-- 侧边栏 -->
	<div data-options="region:'west', title:'菜单', split:true"
		style="width: 20%;">
		<!-- 菜单 -->
		<ul id="menu" class="easyui-tree"
			style="margin-top: 10px; margin-left: 5px;">
			<li><span>商品管理</span>
				<ul>
					<li data-options="attributes:{'url':'item-add'}">添加商品</li>
					<li data-options="attributes:{'url':'item-list'}">查询商品</li>
					<!-- <li data-options="attributes:{'url':'item-param-list'}">规格参数</li> -->
				</ul>
			</li>
			<li><span>首页内容管理</span>
				<ul>
					<li data-options="attributes:{'url':'content-cat'}">广告分类管理</li>
					<li data-options="attributes:{'url':'content-list'}">广告内容管理</li>
				</ul>
			</li>

			<li><span>搜索索引管理</span>
				<ul>
					<li data-options="attributes:{'url':'indexManager'}">一键导入索引</li>
				</ul>
			</li>
		</ul>
	</div>
	<!-- 内容区 -->
	<div data-options="region:'center', title:''">
		<div id="tabs" class="easyui-tabs">
			<div title="首页" style="padding: 20px;"></div>
		</div>
	</div>

	<script type="text/javascript" src="/js/easyui/jquery.js"></script>
	<script type="text/javascript" src="/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/home.js"></script>
</body>
</html>