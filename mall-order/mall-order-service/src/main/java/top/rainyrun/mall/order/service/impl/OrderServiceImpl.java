package top.rainyrun.mall.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.pojo.OrderInfo;
import top.rainyrun.mall.common.pojo.PageModel;
import top.rainyrun.mall.dao.mapper.MallOrderItemMapper;
import top.rainyrun.mall.dao.mapper.MallOrderMapper;
import top.rainyrun.mall.dao.pojo.MallOrder;
import top.rainyrun.mall.dao.pojo.MallOrderExample;
import top.rainyrun.mall.dao.pojo.MallOrderExample.Criteria;
import top.rainyrun.mall.dao.pojo.MallOrderItem;
import top.rainyrun.mall.dao.pojo.MallOrderItemExample;
import top.rainyrun.mall.order.service.OrderService;

public class OrderServiceImpl implements OrderService{
	@Autowired
	private MallOrderMapper orderMapper;
	@Autowired
	private MallOrderItemMapper orderItemMapper;
	@Autowired
	private JedisPool jedisPool;
	@Value("${MALL_ORDER_ID_KEY}")
	private String MALL_ORDER_ID_KEY;
	@Value("${MALL_ORDER_ID_KEY_INITIAL}")
	private String MALL_ORDER_ID_KEY_INITIAL;
	@Value("${MALL_ORDER_ITEM_ID_KEY}")
	private String MALL_ORDER_ITEM_ID_KEY;
	
	/*
	 * 订单、订单项的生成，需要使用事务。
	 */
	@Override
	public Result addOrder(OrderInfo order) {
		// 生成itemId，itemId存在redis中，通过incr命令获取下一个itemId
		Jedis jedis = jedisPool.getResource();
		// 检查redis中是否有订单id
		if(!jedis.exists(MALL_ORDER_ID_KEY)) {
			// 没有，则初始化一个订单id
			jedis.set(MALL_ORDER_ID_KEY, MALL_ORDER_ID_KEY_INITIAL);
		}
		// 获得最新的订单id
		String orderId = jedis.incr(MALL_ORDER_ID_KEY).toString();
		// 完善订单
		order.setOrderId(orderId);
		order.setCreateTime(new Date());
		order.setStatus(1);
		// 生成订单
		orderMapper.insert(order);
//		int i = 10/0; // 测试事务是否生效
		// 生成订单项
		List<MallOrderItem> items = order.getOrderItems();
		for (MallOrderItem item : items) {
			// 完善订单项属性
			String id = jedis.incr(MALL_ORDER_ITEM_ID_KEY).toString();
			item.setId(id);
			item.setOrderId(orderId);
			// 生成订单项
			orderItemMapper.insert(item);
		}
		jedis.close();
		return Result.ok(orderId);
	}

	@Override
	public PageModel getOrders(Long userId, int currentPage, int pageSize) {
		// 查出用户对应的订单
		MallOrderExample example = new MallOrderExample();
		// 设置按时间逆序
		example.setOrderByClause("create_time DESC");
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		// 设置分页信息
		PageHelper.startPage(currentPage, pageSize);
		List<MallOrder> orders = orderMapper.selectByExample(example);
		System.out.println(orders.size());
		// 将订单封装成OrderInfo对象
		List<OrderInfo> orderInfos = new ArrayList<>();
		for (MallOrder order : orders) {
			// 查询订单对应的订单项
			MallOrderItemExample itemExample = new MallOrderItemExample();
			top.rainyrun.mall.dao.pojo.MallOrderItemExample.Criteria itemCriteria = itemExample.createCriteria();
			itemCriteria.andOrderIdEqualTo(order.getOrderId());
			List<MallOrderItem> orderItems = orderItemMapper.selectByExample(itemExample);
			// 封装成OrderInfo对象
			OrderInfo orderInfo = new OrderInfo(order);
			orderInfo.setOrderItems(orderItems);
			// 加入结果列表
			orderInfos.add(orderInfo);
		}
		// 构造返回结果
		PageModel orderInfoResult = new PageModel();
		orderInfoResult.setItems(orderInfos);
		orderInfoResult.setCurrentPage(currentPage);
		PageInfo<MallOrder> page = new PageInfo<MallOrder>(orders);
		orderInfoResult.setTotal(page.getTotal());
		orderInfoResult.setTotalPage(page.getPages());
		return orderInfoResult;
	}	
}
