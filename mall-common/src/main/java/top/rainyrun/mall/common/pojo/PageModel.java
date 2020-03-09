package top.rainyrun.mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class PageModel implements Serializable {
	long total; // 有多少条结果
	long totalPage; // 有多少页
	int currentPage; // 当前页
	String url; // 翻页跳转的部分地址
	List items; // 当前页的搜索结果
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "PageModel [total=" + total + ", totalPage=" + totalPage + ", currentPage=" + currentPage + ", url="
				+ url + ", items=" + items + "]";
	}
}
