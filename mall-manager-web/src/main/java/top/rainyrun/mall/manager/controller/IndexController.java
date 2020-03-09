package top.rainyrun.mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.search.service.SearchService;

@Controller
public class IndexController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/index/importAll")
	@ResponseBody
	public Result importAll() {
		try {
			searchService.updateIndex();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(400, "商品导入索引失败");
		}
		return Result.ok();
	}
}
