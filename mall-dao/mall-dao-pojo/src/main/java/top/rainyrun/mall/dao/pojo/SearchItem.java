package top.rainyrun.mall.dao.pojo;

import java.io.Serializable;
import java.util.Date;

public class SearchItem implements Serializable {
	private Long id;//商品的id 
	private String title;//商品标题
	private Long price;//价格
	private String image;//商品图片的路径
	private String catName;//商品分类名称
	private String itemDesc;//商品的描述
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	@Override
	public String toString() {
		return "SearchItem [id=" + id + ", title=" + title + ", price=" + price + ", image=" + image + ", catName=" + catName
				+ ", itemDesc=" + itemDesc + "]";
	}
}
