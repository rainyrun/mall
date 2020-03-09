package top.rainyrun.mall.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import top.rainyrun.mall.dao.pojo.MallItemCat;
import top.rainyrun.mall.dao.pojo.MallItemCatExample;
import top.rainyrun.mall.dao.pojo.MallItemCatExample.Criteria;

import top.rainyrun.mall.common.pojo.EasyUITreeNode;
import top.rainyrun.mall.dao.mapper.MallItemCatMapper;
import top.rainyrun.mall.manager.service.ItemCatService;

public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	MallItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatListByParent(Long parentId) {
//		设置查询条件
		MallItemCatExample itemCatExample = new MallItemCatExample();
		Criteria itemCatCriteria = itemCatExample.createCriteria();
		itemCatCriteria.andParentIdEqualTo(parentId);
//		查询
		List<MallItemCat> itemCatList = itemCatMapper.selectByExample(itemCatExample);
//		配置返回的树节点数据
		List<EasyUITreeNode> treeNodeList = new ArrayList<EasyUITreeNode>();
		for (MallItemCat itemCat : itemCatList) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(Long.valueOf(itemCat.getId()));
			treeNode.setText(itemCat.getName());
			if(itemCat.getIsParent()) {
				treeNode.setState("closed");
			}
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}

}
