package top.rainyrun.mall.manager.service;

import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemDesc;

import java.util.List;

import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;

public interface ItemService {
	EasyUIDataGridResult getItemList(Integer page, Integer rows);

	boolean deleteItem(Long id);

	MallItem queryItem(Long id);

	MallItemDesc queryItemDesc(Long itemId);

	MallItem getItemById(Long itemId);

	MallItemDesc getItemDescById(Long itemId);

	void addItemAndDesc(MallItem item, MallItemDesc itemDesc);

	void updateItemAndDesc(MallItem item, MallItemDesc itemDesc);

	List<MallItem> getLatestItem(int latestItemNum);
}
