$(function() {
	// 选择分类
	$("#selectCat").click(
			function() {
				// 创建一个div元素
				$("<div>").css({
					padding : "5px"
				}).html("<ul>")
				// 将div元素标记为窗口，并打开
				.window(
						{
							width : '500',
							height : "450",
							modal : true,
							closed : true,
							iconCls : 'icon-save',
							title : '选择类目',
							// 在窗口被打开的时候触发以下的逻辑
							onOpen : function() {
								// 获取当前的窗口
								var _win = this;
								// 在当前的窗口中的ul标签下创建一课树
								$("ul", _win).tree(
										{
											// 异步发送请求 从服务端获取数据的URL
											url : '/item/cat/list',
											animate : true,
											onClick : function(node) {
												if ($(this).tree("isLeaf",
														node.target)) {
													// 填写到cid中
													$("#selectCat").parent()
															.find("[name=cid]")
															.val(node.id);
													// 显示分类文本
													$("#selectCat").text(
															node.text).attr(
															"cid", node.id);
													$(_win).window('close');
												}
												// 子节点的加载依赖于父节点的状态。当展开一个封闭的节点，如果节点没有加载子节点，它将会把节点id的值作为http请求参数并命名为'id'，通过URL发送到服务器上面检索子节点。
											}
										});
							},
							onClose : function() {
								$(this).window("destroy");
							}
						}).window('open');
			});

	// 使普通表单成为ajax提交方式的表单。
	$("#itemAddForm").form({
		url : "/item/add",
		onSubmit : function(){
			if(!$('#itemAddForm').form('validate')){
				$.messager.alert('提示','表单还未填写完成!');
				return false;
			}
		},
		success : function(data) {
			var dataObj = eval("("+data+")");//转换为json对象
			if(dataObj.status == 200){
				$.messager.alert('提示', "添加成功！");
			} else {
				$.messager.alert('提示', dataObj.msg);
			}
			// 关闭当前tab
			$("#tabs").tabs("close", "添加商品");
			// 刷新页面
			$("#itemList").datagrid("reload");
		}
	});

	// 提交表单，添加商品
	$("#itemAddSubmit").click(function() {
		$('#itemAddForm').submit();
	});

	// 清除表单数据
	$("#clearForm").click(function() {
		$('#itemAddForm').form("clear");
	});

	// 预览图片
	$("#picFileUpload").change(
			function() {
				// 定位到img元素(用来展示图片)
				$("#upload_image").attr("src",
						URL.createObjectURL($(this)[0].files[0]));
			});
});