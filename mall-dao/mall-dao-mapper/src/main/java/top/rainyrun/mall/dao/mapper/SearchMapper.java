package top.rainyrun.mall.dao.mapper;

import java.util.List;

import top.rainyrun.mall.dao.pojo.SearchItem;

public interface SearchMapper {
	public List<SearchItem> getSearchItemList();

	public SearchItem getSearchItemById(Long id);
}
