$(function() {
	// 使普通表单成为ajax提交方式的表单。
	$("#contentEditForm").form({
		url : "/content/save",
		onSubmit : function(){
			// 校验信息
			if(!$('#contentEditForm').form('validate')){
				$.messager.alert('提示','表单还未填写完成!');
				return false;
			}
		},
		success : function(data) {
			var dataObj = eval("("+data+")");//转换为json对象
			// 成功后提示添加成功
			$.messager.alert('提示', dataObj.msg,undefined,function(){
				// 关闭窗口
    			$("#contentEditWin").window("close");
    			// 刷新表格
    			$("#contentList").datagrid("reload");
			});
			
		}
	});

	// 提交表单，添加商品
	$("#contentEditSubmit").click(function() {
		$('#contentEditForm').submit();
	});

	// 清除表单数据
	$("#clearForm").click(function() {
		$('#contentEditForm').form("clear");
	});
	
	// 预览图片
	$(".upload_pic").change(function() {
		var $upload_pic = $(this);
		// 定位到img元素(用来展示图片)
		$upload_pic.nextAll(".upload_pic_img").each(function(){
			$(this).attr("src",URL.createObjectURL($upload_pic[0].files[0]));
		});
	});

});