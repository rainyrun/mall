// 返回被选中的行
function getSelectedId() {
	var itemList = $("#itemList");
	var id = null;
	// 选中第一个被选中的行
	var row = itemList.datagrid("getSelected");
	if(row != null){
		id = row.id;
	}
	return id;
}

// 时间转换函数
function formatterdate(val) {
    if (val == null)
        return '';
    var date = new Date(val);
    return date.toLocaleDateString();
}


var toolbar = [{
	text:'删除',
	iconCls:'icon-cancel',
	handler:function(){
		var id = getSelectedId();
		if(id == null){
			$.messager.alert("提示","请先选择一个商品");
			return ;
		}
		$.messager.confirm('确认','您确认想要删除该商品吗？',function(r){
		    if (r){
		        // 确认删除
		    	var url = "item/delete";
		    	$.post(url, {id:id}, function(data){
		    		// 回调函数
		    		if(data.status == 200){
		    			$.messager.alert('提示','删除商品成功!',undefined,function(){
        					$("#itemList").datagrid("reload");
        				});
		    		}
		    	});
		    }
		});
	}
},{
	text:'编辑',
	iconCls:'icon-edit',
	handler:function(){
		var id = getSelectedId();
		if(id == null){
			$.messager.alert("提示","请先选择一个商品");
			return ;
		}
		$("#itemEditWin").window({
			onLoad : function(){
				//回显数据
				var data = $("#itemList").datagrid("getSelected");
				$("#itemEditForm").form("load",data);
				$("#upload_image").attr("src", data.image);
				
				// 加载商品描述
				var url = "item/desc";
				var itemId = data.id;
				$.post(url, {itemId:itemId}, function(data){
					if(data.status == 200){
						$("#itemDesc").val(data.data);
					}
				});
			}
		}).window("open");
	}
}];