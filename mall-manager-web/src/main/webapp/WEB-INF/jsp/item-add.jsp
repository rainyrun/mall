<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style type="text/css">
	.itemPic {
		width: 200px;
		height: 200px;
	}

</style>

<div style="padding: 10px 10px 10px 10px">
	<form id="itemAddForm" method="post" action="" enctype="multipart/form-data">
		<div>
			<label for="selectCat">商品类目</label>
			<a id="selectCat"
				cid="" class="easyui-linkbutton" >选择分类</a>
			<input type="hidden" value="" name="cid">
		</div>
		<div>
			<label for="title">商品标题</label>
			<input id="title" type="text"
				class="easyui-textbox" data-options="required:true" name="title">
		</div>
		<div>
			<label for="price">商品价格</label>
			<input id="price" name="price" type="text"
				class="easyui-numberbox" data-options="required:true">
			<span>单位：分</span>
		</div>
		<div>
			<label for="num">库存数量</label> <input id="num" name="num" type="text"
				class="easyui-numberbox" data-options="required:true">
		</div>
		<!-- <div>
			<label>商品状态</label>
			<input name="status" type="radio" value="1" data-options="required:true" checked>正常
			<input name="status" type="radio" value="2" data-options="required:true">下架
		</div> -->
		<div>
			<label for="image">商品图片</label>
	        <input type="file" name="imageContent" id="picFileUpload" value="上传图片"/><br />
	        <label for="upload_image">图片预览</label>
	        <img alt="上传的商品图片" src="/img/default_pic.png" id="upload_image" class="itemPic">
		</div>
		<div>
			<label for="itemDesc">商品描述</label>
			<textarea rows="3" cols="20" name="itemDesc" id="itemDesc"></textarea>
		</div>

	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="itemAddSubmit">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="clearForm">重置</a>
	</div>
</div>


<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor/lang/zh-CN.js"></script>
<script type="text/javascript" src="/js/item-add.js"></script>
