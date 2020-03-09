<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
<div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:false" style="width:200px;padding:5px">
            <!-- 广告分类(树) -->
			<ul id="contentCatTree" class="easyui-tree">
			</ul>
        </div>
        <div data-options="region:'center'" style="padding:5px">
            <table id="contentList" class="easyui-datagrid" title="广告分类列表" data-options="rownumbers:true,singleSelect:true,pagination:true,url:'/content/list',method:'get',toolbar:contentToolbar,queryParams:{catId:0}">
			    <thead>
			        <tr>
			            <th data-options="field:'id'">内容id</th>
			            <th data-options="field:'catId'">内容分类id</th>
			            <th data-options="field:'title'">内容标题</th>
			            <th data-options="field:'titleDesc'">标题描述</th>
			            <th data-options="field:'url'">跳转链接</th>
			            <th data-options="field:'pic'">图片地址</th>
			            <th data-options="field:'created', formatter:formatterdate">创建时间</th>
			            <th data-options="field:'updated', formatter:formatterdate">更新时间</th>
			        </tr>
			    </thead>
			    <tbody>
			       
			    </tbody>
			</table>
        </div>
</div>
</div>
<div id="contentEditWin" class="easyui-window" title="编辑内容" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/content-edit'" style="width:80%;height:80%;padding:10px;">
</div>

<script type="text/javascript" src="/js/content-list.js"></script>
