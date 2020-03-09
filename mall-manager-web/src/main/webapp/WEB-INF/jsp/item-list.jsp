<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="itemList" class="easyui-datagrid" title="商品列表" data-options="rownumbers:true,singleSelect:true,pagination:true,url:'/item/list',method:'get',pageSize:20,toolbar:toolbar">
    <thead>
        <tr>
            <th data-options="field:'id'">商品id</th>
            <th data-options="field:'title'">标题</th>
            <th data-options="field:'image'">图片</th>
            <th data-options="field:'price'">价格</th>
            <th data-options="field:'cid'">类别</th>
            <th data-options="field:'num'">库存</th>
            <th data-options="field:'created', formatter:formatterdate">更新时间</th>
        </tr>
    </thead>
    <tbody>
       
    </tbody>
</table>

<div id="itemEditWin" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>

<script type="text/javascript" src="/js/item-list.js"></script>
