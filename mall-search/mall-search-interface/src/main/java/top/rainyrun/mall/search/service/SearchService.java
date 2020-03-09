package top.rainyrun.mall.search.service;

import top.rainyrun.mall.common.pojo.PageModel;

public interface SearchService {

	void updateIndex () throws Exception;

	PageModel search(String query, Integer page, Integer rows) throws Exception;

	void updateSearchItemById(Long id) throws Exception;

}
