package top.rainyrun.mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.JsonUtils;
import top.rainyrun.mall.dao.mapper.MallItemMapper;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallOrderItem;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.rainyrun.mall.cart.service.CartService;

public class CartServiceImpl implements CartService {
	@Autowired
	private JedisPool jedisPool;
	@Value("${CART_INFO}")
	private String CART_INFO;
	@Autowired
	private MallItemMapper itemMapper;
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;

	@Override
	public Result addToCart(MallItem item, Long userId) {
		Jedis jedisClient = jedisPool.getResource();
		// 查看商品是否在购物车内
		String key = CART_INFO + userId;
		String itemNum = jedisClient.hget(key, item.getId().toString());
		// 商品在购物车中
		if(itemNum != null && !"".equals(itemNum)) {
			// 更新购物车中的商品数量
			int newItemNum = Integer.parseInt(itemNum) + item.getNum();
			jedisClient.hset(key, item.getId().toString(), String.valueOf(newItemNum));
		}
		else { // 商品不在购物车中，直接放入购物车
			jedisClient.hset(key, item.getId().toString(), item.getNum().toString());
		}
		jedisClient.close();
		return Result.ok();
	}

	@Override
	public List<MallItem> getCart(Long userId) {
		// 构造结果
		List<MallItem> itemList = null;
		// 从redis中查出购物车的信息
		Jedis jedisClient = jedisPool.getResource();
		Map<String, String> itemMap = jedisClient.hgetAll(CART_INFO + userId);
		if(itemMap != null && itemMap.size() > 0) { // 有购物车信息
			itemList = getItemList(itemMap);
		}
		jedisClient.close();
		return itemList;
	}

	public List<MallItem> getItemList(Map<String, String> itemMap) {
		if(itemMap == null || itemMap.size() == 0)
			return null;
		// 构造结果
		List<MallItem> itemList = new ArrayList<>();
		Jedis jedisClient = jedisPool.getResource();
		for (Entry<String, String> entry : itemMap.entrySet()) {
			// 获取商品id
			Long itemId = Long.parseLong(entry.getKey());
			int itemNum = Integer.parseInt(entry.getValue());
			MallItem item = null;
			// 查看redis中是否有商品信息
			String itemByJson = jedisClient.get(itemId.toString());
			if( itemByJson != null && "".equals(itemByJson)) { // 有，直接获取
				item = JsonUtils.jsonToPojo(entry.getValue(), MallItem.class);
			} else { // 没有，则从数据库中获取，设置商品数量，并存入redis
				item = itemMapper.selectByPrimaryKey(itemId);
				if(item != null) {
					jedisClient.set(itemId.toString(), JsonUtils.objectToJson(item));
					jedisClient.expire(itemId.toString(), EXPIRE_TIME); // 防止商品信息大量堆积
				}
			}
			// 商品加入到结果列表中
			if(item != null) {
				item.setNum(itemNum);
				itemList.add(item);
			}
		}
		jedisClient.close();
		return itemList;
	}

	@Override
	public void deleteItemInCart(Long itemId, Long userId) {
		Jedis jedisClient = jedisPool.getResource();
		jedisClient.hdel(CART_INFO + userId, itemId.toString());
		jedisClient.close();
	}
	
	@Override
	public void updateItemInCart(Long itemId, Integer num, Long userId) {
		Jedis jedisClient = jedisPool.getResource();
		String itemNum = jedisClient.hget(CART_INFO + userId, itemId.toString());
		if(itemNum != null && !"".equals(itemNum)) {// 商品在购物车中，更新商品数量
			jedisClient.hset(CART_INFO + userId, itemId.toString(), num.toString());
		}
		jedisClient.close();
	}

	@Override
	public void mergeCart(Map<String, String> cart, Long userId) {
		Jedis jedisClient = jedisPool.getResource();
		for (String itemId : cart.keySet()) {
			// 检查商品是否在redis的购物车中
			String itemNum = jedisClient.hget(CART_INFO + userId, itemId);
			if(itemNum != null && !"".equals(itemNum)) {
				// 有，则合并
				int num = Integer.parseInt(cart.get(itemId));
				int newItemNum = num + Integer.parseInt(itemNum);
				jedisClient.hset(CART_INFO + userId, itemId.toString(), String.valueOf(newItemNum));
			}
			else {
				// 没有，则添加
				jedisClient.hset(CART_INFO + userId, itemId, cart.get(itemId));
			}
		}
		jedisClient.close();
	}

	@Override
	public void deleteItemsInCart(List<MallOrderItem> orderItems, Long userId) {
		Jedis jedisClient = jedisPool.getResource();
		for (MallOrderItem item : orderItems) {
			jedisClient.hdel(CART_INFO + userId, item.getItemId().toString());
		}
		jedisClient.close();
	}
}
