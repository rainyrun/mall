package top.rainyrun.mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.common.pojo.EasyUITreeNode;
import top.rainyrun.mall.manager.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	ItemCatService catService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		return catService.getItemCatListByParent(parentId);
	}
}
