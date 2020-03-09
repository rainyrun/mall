$(function(){
	// 更新购物车中的商品数量
	$(".num").change(function(){
		var parent = $(this).parent().parent();
		var itemId = parent.attr("id");
		var num = $(this).val();
		// 更新小记中的价格
		var price = $("#" + itemId + " .price").html();
		var subTotal = $("#" + itemId + " .subTotal").html(price * num);
		$.post("/cart/update/" + itemId + ".html", {num:num});
	});
	
	// 全选
	$("#selectAll").click(function(){
		var items = $(".selectedItems").attr("checked", this.checked);
	});
	
	// 提交订单
	$("#toOrder").click(function(){
		// 清除提示信息
		$("#orderAlert").html("");
		// 筛选出被选中的商品id
		var selectedItemIds = [];
		var selectedItems = $(".selectedItems");
		for(var i = 0; i < selectedItems.length; i++){
			if(selectedItems[i].checked == true){
				var itemId = $(selectedItems[i]).parent().parent().attr("id")
				selectedItemIds.push(itemId);
			}
		}
		if(selectedItemIds.length == 0){
			$("#orderAlert").html("请选中至少一个商品");
			return false;
		}
		location.href = $("#mall_order_url").val() + "/order/confirm_order.html?selectedItemIds=" + selectedItemIds;
	});
});