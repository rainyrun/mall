<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div style="padding: 10px 10px 10px 10px">
	<form id="contentEditForm" method="post" action="" enctype="multipart/form-data">
		<div>
			<input type="hidden" value="" name="catId" >
			<input type="hidden" value="" name="id" >
		</div>
		<div>
			<label for="title">内容标题</label>
			<input id="title" type="text" class="easyui-textbox" name="title">
		</div>
		<div>
			<label for="titleDesc">标题描述</label>
			<input id="titleDesc" type="text" class="easyui-textbox" name="titleDesc">
		</div>
		<div>
			<label for="url">跳转链接</label>
			<input id="url" type="text" class="easyui-textbox" name="url">
		</div>
		<div>
			<label for="picContent">上传图片</label>
	        <input id="picContent" type="file" name="picContent" class="upload_pic"><br>
	        <label for="upload_pic">图片预览</label>
		    <img alt="上传的商品图片" src="" id="upload_pic" class="upload_pic_img" width="300">
		</div>
	</form>
	<div style="padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="contentEditSubmit">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="clearForm">重置</a>
	</div>
</div>


<!-- <script type="text/javascript" charset="utf-8"
	src="/js/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor/lang/zh-CN.js"></script> -->
<script type="text/javascript" src="/js/content-edit.js"></script>
