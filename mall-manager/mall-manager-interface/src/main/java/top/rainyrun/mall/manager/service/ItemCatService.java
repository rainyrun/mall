package top.rainyrun.mall.manager.service;

import java.util.List;

import top.rainyrun.mall.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatListByParent(Long parentId);
}
