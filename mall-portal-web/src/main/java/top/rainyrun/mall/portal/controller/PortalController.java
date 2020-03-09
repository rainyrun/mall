package top.rainyrun.mall.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.dao.pojo.MallContent;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.manager.service.ItemService;
import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.content.service.ContentService;
import top.rainyrun.mall.portal.pojo.AdCarouselNode;

@Controller
public class PortalController {
	@Autowired
	private ContentService contentService;
	@Autowired
	private ItemService itemService;
	
	@Value("${CAROUSEL_CATEGORY_ID}")
	private Long catId;
	@Value("${LATEST_ITEM_NUM}")
	private int latestItemNum;
	
	@RequestMapping("/index")
	public String showAdCarousel(Model model){
		// 构造轮播图模型
		List<AdCarouselNode> carousels = new ArrayList<>();
		// 查询轮播图广告内容
		List<MallContent> list = contentService.getContentList(catId);
		for (MallContent content : list) {
			// 构造广告节点
			AdCarouselNode node = new AdCarouselNode();
			node.setTitle(content.getTitle());
			node.setUrl(content.getUrl());
			node.setPic(content.getPic());
			node.setTitleDesc(content.getTitleDesc());
			carousels.add(node);
		}
		// 查找最新商品
		List<MallItem> latestItem = itemService.getLatestItem(latestItemNum);
		model.addAttribute("carousels", carousels);
		model.addAttribute("latestItem", latestItem);
		return "index";
	}
}
