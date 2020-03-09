$(function() {
	$('#menu').tree({
		// 点击事件
		onClick : function(node) {
			// node.target 目标DOM对象
			if ($('#menu').tree("isLeaf", node.target)) {
				var tabs = $("#tabs");
				// 获取node.text指向的选项卡
				var tab = tabs.tabs("getTab", node.text);
				if (tab) {
					// 如果有该选项卡，则选中
					tabs.tabs("select", node.text);
				} else {
					// 如果没有该选项卡，就新建一个
					tabs.tabs('add', {
						title : node.text,
						// 从URL加载远程数据内容填充到选项卡面板
						href : node.attributes.url,
						closable : true,
						bodyCls : "content"
					});
				}
			}
		}
	});
});