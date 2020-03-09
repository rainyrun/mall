package top.rainyrun.mall.cart.service;

import java.util.List;
import java.util.Map;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallOrderItem;

public interface CartService {

	Result addToCart(MallItem item, Long userId);

	List<MallItem> getCart(Long userId);

	void deleteItemInCart(Long itemId, Long userId);

	void updateItemInCart(Long itemId, Integer num, Long userId);

	void deleteItemsInCart(List<MallOrderItem> orderItems, Long userId);
	
	List<MallItem> getItemList(Map<String, String> itemMap);

	void mergeCart(Map<String, String> cart, Long userId);

}
