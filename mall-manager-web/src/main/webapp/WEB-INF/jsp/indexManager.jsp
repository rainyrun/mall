<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div>
	<input type="button" value="一键导入所有商品到索引库" id="importItems" class="easyui-linkbutton" />
</div>

<script type="text/javascript">
$("#importItems").click(function(){
	var url = "/index/importAll";
	$.get(url,function(data){
		if(data.status == 200){
			$.messager.alert("提示", "导入成功");
		}
		else{
			$.messager.alert("提示", "导入失败");
		}
	});
});
</script>