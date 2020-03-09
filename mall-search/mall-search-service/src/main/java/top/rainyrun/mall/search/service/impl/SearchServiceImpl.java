package top.rainyrun.mall.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import top.rainyrun.mall.dao.pojo.SearchItem;
import top.rainyrun.mall.dao.pojo.MallItem;

import top.rainyrun.mall.common.pojo.PageModel;
import top.rainyrun.mall.dao.mapper.SearchMapper;
import top.rainyrun.mall.search.service.SearchService;
import top.rainyrun.mall.search.util.SolrUtil;

public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private HttpSolrClient solrClient;
	@Value("${SOLR_CORE_NAME}")
	private String SOLR_CORE_NAME;
	
	// 更新索引
	public void updateIndex() throws Exception {
		// 获得商品列表
		List<SearchItem> items = searchMapper.getSearchItemList();
		// 封装成Document，并加入到索引
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (SearchItem searchItem : items) {
			SolrInputDocument doc = SolrUtil.searchItemToSolrDoc(searchItem);
			docs.add(doc);
		}
		solrClient.add(SOLR_CORE_NAME, docs);
		// 提交
		solrClient.commit(SOLR_CORE_NAME);
	}

	@Override
	public PageModel search(String query, Integer page, Integer rows) throws Exception {
		// 构造搜索条件
		SolrQuery solrQuery = new SolrQuery();
		// 主搜索词
		if(StringUtils.isEmpty(query))
			solrQuery.setQuery("*:*");
		else
			solrQuery.setQuery(query);
		// 默认搜索字段
		solrQuery.set("df", "mall_item_keywords");
		// 第几条开始，搜索多少条
		if (page == null) page = 1;
		if (rows == null) rows = 12;
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		// 返回搜索结果
		QueryResponse queryResponse = solrClient.query(SOLR_CORE_NAME, solrQuery);
		SolrDocumentList results = queryResponse.getResults();
		List<SearchItem> items = new ArrayList<SearchItem>();
		for (SolrDocument doc : results) {
			SearchItem item = SolrUtil.solrDocToSearchItem(doc);
			items.add(item);
		}
		// 构造返回值
		PageModel result = new PageModel();
		result.setCurrentPage(page);
		result.setItems(items);
		long total = results.getNumFound();
		result.setTotal(total);
		long totalPage = (total % rows) == 0 ? (total / rows) : (total / rows + 1);
		result.setTotalPage(totalPage);
		return result;
	}

	@Override
	public void updateSearchItemById(Long id) throws Exception {
		SearchItem item = searchMapper.getSearchItemById(id);
		if(item == null) {
			// 删除文档
			solrClient.deleteById(SOLR_CORE_NAME, id.toString());
		}
		else {
			// 转成文档
			SolrInputDocument doc = SolrUtil.searchItemToSolrDoc(item);
			solrClient.add(SOLR_CORE_NAME, doc);
		}
		// 提交
		solrClient.commit(SOLR_CORE_NAME);
	}
	
//	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
//	}
}
