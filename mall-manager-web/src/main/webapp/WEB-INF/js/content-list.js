$(function() {
	var tree = $("#contentCatTree");//获取树
	var datagrid = $("#contentList");//获取表格
	// 显示内容分类的树
	tree.tree({
		url : '/content/cat/list',
		animate: true,
		method : "GET",
		// 点击时，如果是叶子节点，则显示对应的内容
		onClick: function(node){
			if (tree.tree("isLeaf", node.target)) {
				datagrid.datagrid("reload",{
					//发送请求的参数：catId :分类的id 
					catId:node.id
				});
			}
	    }
	});
});

//时间转换函数
function formatterdate(val) {
    if (val == null)
        return '';
    var date = new Date(val);
    return date.toLocaleDateString();
}

var contentToolbar = [{
	text:'添加',
	iconCls:'icon-add',
	handler:function(){
		// 获得被选中的分类内容节点
		var node = $("#contentCatTree").tree("getSelected");
		// 没有选中，或者选中的是非叶子节点
		if(!node || !$("#contentCatTree").tree("isLeaf",node.target)){
    		$.messager.alert('提示','新增内容必须选择一个内容分类!');
    		return ;
    	}
		// 打开内容编辑页面
		$("#contentEditWin").window({
			onLoad : function(){
				// 自动填充内容分类id
				var catId = node.id;
				$("#contentEditForm").form("load", {catId:catId});
			}
		}).window("open");
	}
},{
	text:'删除',
	iconCls:'icon-cancel',
	handler:function(){
		// 获得选中的内容的数据
		var data = $("#contentList").datagrid("getSelected");
		// 如果没有选中
		if(!data){
			$.messager.alert('提示','请先选中一条内容!');
			return ;
		}
		// 向后台发送删除请求
		$.messager.confirm('确认','您确认想要删除该商品吗？',function(r){
		    if (r){
		        // 确认删除
		    	$.post("content/delete", {id:data.id}, function(data){
					// 提示删除成功或失败
		    		if(data.status == 200){
		    			$.messager.alert('提示','删除商品成功!',undefined,function(){
		    				$("#contentList").datagrid("reload");
        				});
		    		} else {
		    			$.messager.alert('提示', data.msg);
		    		}
				});
		    }
		});
	}
},{
	text:'编辑',
	iconCls:'icon-edit',
	handler:function(){
		// 获得选中的内容的数据
		var data = $("#contentList").datagrid("getSelected");
		// 如果没有选中
		if(!data){
			$.messager.alert('提示','请先选中一条内容!');
			return ;
		}
		// 打开内容编辑页面
		$("#contentEditWin").window({
			onLoad : function(){
				// 将内容自动填充到表单
				$("#contentEditForm").form("load",data);
				// 将图片填充到预览
				$("#upload_pic").attr("src", data.pic);
			}
		}).window("open");
	}
}];