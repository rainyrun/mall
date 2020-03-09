<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<ul id="contentCat" class="easyui-tree">
</ul>

<!-- 右键菜单定义如下 -->
<div id="contentCatMenu" class="easyui-menu" style="width:120px;">
    <div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
    <div onclick="remove()" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="update()" data-options="iconCls:'icon-edit'">编辑</div>
</div>

<script type="text/javascript" src="/js/content-cat.js"></script>