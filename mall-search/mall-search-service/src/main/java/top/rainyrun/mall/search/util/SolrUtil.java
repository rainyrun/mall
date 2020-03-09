package top.rainyrun.mall.search.util;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import top.rainyrun.mall.dao.pojo.SearchItem;

public class SolrUtil {
	
	public static SearchItem solrDocToSearchItem(SolrDocument solrDocument) {
		SearchItem item = new SearchItem();
		item.setId(Long.parseLong(solrDocument.get("mall_item_id").toString()));
		item.setTitle(solrDocument.get("mall_item_title").toString());
		item.setPrice((Long) solrDocument.get("mall_item_price"));
		item.setCatName(solrDocument.get("mall_cat_name").toString());
		item.setImage(solrDocument.get("mall_item_image").toString());
		item.setItemDesc(solrDocument.get("mall_item_desc").toString());
		return item;
	}
	
	public static SolrInputDocument searchItemToSolrDoc(SearchItem searchItem) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("mall_item_id", searchItem.getId().toString());
		doc.addField("mall_item_title", searchItem.getTitle());
		doc.addField("mall_item_price", searchItem.getPrice());
		doc.addField("mall_item_image", searchItem.getImage());
		doc.addField("mall_cat_name", searchItem.getCatName());
		doc.addField("mall_item_desc", searchItem.getItemDesc());
		return doc;
	}
	

}
