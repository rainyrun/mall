$(function() {
	$("#contentCat").tree({
		url : '/content/cat/list',
		animate: true,
		method : "GET",
		// 右击时显示菜单
		onContextMenu: function(e, node){
	        e.preventDefault();
	        // 查找节点
	        $('#contentCat').tree('select', node.target);
	        // 显示快捷菜单
	        $('#contentCatMenu').menu('show', {
	            left: e.pageX,
	            top: e.pageY
	        });
	    },
	    // 在选中的节点被编辑之后触发
        onAfterEdit : function(node){
        	// 获取树本身
        	var _tree = $(this);
        	//表示的是新增的节点
        	if(node.id == 0){
        		// 新增节点
        		//parentId:node.parentId,name:node.text  
        		//parentId：就是新增节点的父节点的Id
        		//name：新增节点的文本
        		$.post("/content/cat/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				//更新节点
        				_tree.tree("update",{
            				target : node.target,//更新哪一个节点
            				id : data.data.id//更新新增节点的id 
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/content/cat/update",{id:node.id,name:node.text});
        	}
        }
	});
});

function append(){
	//获取树
	var tree = $("#contentCat");
	//获取被选中的节点 就是右击鼠标时的所在的节点
	var node = tree.tree("getSelected");
	tree.tree('append', {
        parent: (node?node.target:null), //被添加的节点的父节点
        // 子节点
        data: [{
            text: '新建分类',//节点的内容
            id : 0,//节点的id
            parentId : node.id //新建的节点的父节点的id
        }]
    }); 
	//找到id为0的节点  添加的节点
	var _node = tree.tree('find',0);
	//选中id为0的节点 添加的节点 开启编辑
	tree.tree("select",_node.target).tree('beginEdit',_node.target);
}

function remove(){
	//获取树
	var tree = $("#contentCat");
	//获取被选中的节点 就是右击鼠标时的所在的节点
	var node = tree.tree("getSelected");
	$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
		if(r){//如果是true 表示要执行以下的逻辑
			$.post("/content/cat/delete/",{id:node.id},function(data){
				if(data.status == 200){
					//后台删除成功后，删除前端的节点
					tree.tree("remove",node.target);
				} else {
					$.messager.alert('提示', data.msg);
				}
			});	
		}
	});
}

function update(){
	//获取树
	var tree = $("#contentCat");
	//获取被选中的节点 就是右击鼠标时的所在的节点
	var node = tree.tree("getSelected");
	tree.tree('beginEdit', node.target);
}