package top.rainyrun.mall.manager.controller;

import java.util.Date;
import java.util.List;

import org.apache.dubbo.config.support.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.common.pojo.EasyUITreeNode;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.content.service.ContentCatService;
import top.rainyrun.mall.dao.pojo.MallContentCat;

@Controller
public class ContentCatController {
	@Autowired
	ContentCatService contentCatService;
	
	@RequestMapping("/content/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue = "0") Long parentId){
		return contentCatService.getContentCatList(parentId);
	}
	
	@RequestMapping("/content/cat/create")
	@ResponseBody
	public Result addContentCat(Long parentId, String name) {
		return contentCatService.addContentCat(parentId, name);
	}

	@RequestMapping("/content/cat/delete")
	@ResponseBody
	public Result deleteContentCat(Long id) {
		return contentCatService.deleteContentCat(id);
	}
	
	@RequestMapping("/content/cat/update")
	@ResponseBody
	private void updateContentCat(Long id, String name) {
		contentCatService.updateContentCat(id, name);
	}
}
