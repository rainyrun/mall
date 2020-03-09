package top.rainyrun.mall.order.service;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.pojo.PageModel;
import top.rainyrun.mall.common.pojo.OrderInfo;

public interface OrderService {

	Result addOrder(OrderInfo info);

	PageModel getOrders(Long userId, int currentPage, int pageSize);
	
}
