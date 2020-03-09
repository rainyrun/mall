package top.rainyrun.mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.fabric.xmlrpc.base.Array;
import top.rainyrun.mall.dao.mapper.*;
import top.rainyrun.mall.dao.pojo.*;
import top.rainyrun.mall.dao.pojo.MallContentCatExample.Criteria;
import top.rainyrun.mall.common.pojo.EasyUITreeNode;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.content.service.ContentCatService;

public class ContentCatServiceImpl implements ContentCatService {
	@Autowired
	MallContentCatMapper contentCatMapper;
	MallContent contentMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		// 设置查询条件
		MallContentCatExample example = new MallContentCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 查询内容分类列表
		List<MallContentCat> contentCatList = contentCatMapper.selectByExample(example);
		// 创建返回的数据
		List<EasyUITreeNode> treeNodes = new ArrayList<EasyUITreeNode>();
		for (MallContentCat item : contentCatList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			if (item.getIsParent()) {
				node.setState("closed");
			}
			treeNodes.add(node);
		}
		return treeNodes;
	}

	@Override
	public Result addContentCat(Long parentId, String name) {
		// 创建MallContentCat对象
		MallContentCat contentCat = new MallContentCat();
		contentCat.setCreated(new Date());
		contentCat.setUpdated(contentCat.getCreated());
		contentCat.setIsParent(false);
		contentCat.setName(name);
		contentCat.setParentId(parentId);
		contentCat.setSortOrder(1);
		// 添加内容分类
		contentCatMapper.insertSelective(contentCat);
		// 更新父内容分类
		MallContentCat parent = contentCatMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCatMapper.updateByPrimaryKeySelective(parent);
		}
		// 返回节点id
		return Result.ok(contentCat);
	}

	@Override
	public Result deleteContentCat(Long id) {
		MallContentCat contentCat = contentCatMapper.selectByPrimaryKey(id);
		if(contentCat.getIsParent()) {
			return Result.build(400, "不能删除父节点");
		}
		try {
		contentCatMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			return Result.build(400, "删除失败，请确保该分类下没有广告内容后再删除。");
		}
		// 检查父节点是否还有子节点
		MallContentCatExample example = new MallContentCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(contentCat.getParentId());
		List<MallContentCat> nodes = contentCatMapper.selectByExample(example);
		if(nodes == null || nodes.size() == 0) {
			MallContentCat parent = contentCatMapper.selectByPrimaryKey(contentCat.getParentId());
			// 将parent设置为叶子节点
			parent.setIsParent(false);
			contentCatMapper.updateByPrimaryKeySelective(parent);
		}
		return Result.ok();		
	}

	@Override
	public void updateContentCat(Long id, String name) {
		MallContentCat contentCat = contentCatMapper.selectByPrimaryKey(id);
		contentCat.setName(name);
		contentCat.setUpdated(new Date());
		contentCatMapper.updateByPrimaryKeySelective(contentCat);
	}
}
