package top.rainyrun.mall.content.service;

import java.util.List;

import top.rainyrun.mall.dao.pojo.MallContentCat;

import top.rainyrun.mall.common.pojo.EasyUITreeNode;
import top.rainyrun.mall.common.pojo.Result;

public interface ContentCatService {

	List<EasyUITreeNode> getContentCatList(Long parentId);
	
	Result addContentCat(Long parentId, String name);

	Result deleteContentCat(Long id);

	void updateContentCat(Long id, String name);

}
