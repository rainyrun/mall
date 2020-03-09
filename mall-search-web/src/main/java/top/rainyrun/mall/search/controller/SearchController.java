package top.rainyrun.mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.rainyrun.mall.common.pojo.PageModel;
import top.rainyrun.mall.search.service.SearchService;

@Controller
public class SearchController {
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;

	@Autowired
	SearchService searchService;

	// 跳转到对应页面
	@RequestMapping("/search")
	public String search(@RequestParam(value = "currentPage", defaultValue = "1") Integer page,
			@RequestParam(value="q") String query, Model model) throws Exception {
		// 处理query(可能是乱码)
		System.out.println(query);
//		query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
		// 查询搜索结果
		PageModel searchResult = searchService.search(query, page, ITEM_ROWS);
		// 加入页面
		model.addAttribute("searchResult", searchResult);
		return "search";
	}

}
