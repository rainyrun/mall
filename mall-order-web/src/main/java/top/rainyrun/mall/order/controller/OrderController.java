package top.rainyrun.mall.order.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.style.DefaultValueStyler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallOrderItem;
import top.rainyrun.mall.dao.pojo.MallUser;

import top.rainyrun.mall.cart.service.CartService;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.pojo.OrderInfo;
import top.rainyrun.mall.common.pojo.PageModel;
import top.rainyrun.mall.order.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@Value("${MALL_ORDER_PAGE_SIZE}")
	private int MALL_ORDER_PAGE_SIZE;
	@Value("${MALL_CART_URL}")
	private String MALL_CART_URL;

	/*
	 * 页面跳转
	 */
	@RequestMapping("/page/{page}")
	public String toPage(@PathVariable String page) {
		return page;
	}

	/*
	 * 确认订单信息
	 */
	@RequestMapping("/order/confirm_order")
	public String showConfirmOrder(String selectedItemIds, HttpServletRequest request, Model model) {
		if (selectedItemIds == null || "[]".equals(selectedItemIds) || "".equals(selectedItemIds))
			return "exception";
		// 被选中的商品id
		String[] itemIds = selectedItemIds.split(",");
		// 获取用户信息
		MallUser user = (MallUser) request.getAttribute("user");
		// 获取购物车列表
		List<MallItem> cartItems = cartService.getCart(user.getId());
		// 获取选中的商品
		List<MallItem> orderItems = new ArrayList<>();
		for (String itemId : itemIds) {
			for (MallItem item : cartItems) {
				if (item.getId().toString().equals(itemId)) {
					orderItems.add(item);
					continue;
				}
			}
		}
		if (orderItems.size() == 0)
			return "";
		model.addAttribute("orderItems", orderItems);
		return "confirm_order";
	}

	/*
	 * 生成订单
	 */
	@RequestMapping("/order/add")
	public String addOrder(OrderInfo order, HttpServletRequest request, Model model) {
		// 获取订单页中的信息
		// 订单内容为空时，跳转到购物车页面
		if (order == null || order.getOrderItems() == null || order.getOrderItems().size() == 0)
			return "redirect:" + MALL_CART_URL;
		// 补充订单内容
		MallUser user = (MallUser) request.getAttribute("user");
		order.setUserId(user.getId());
		// 生成订单和订单项
		Result result = orderService.addOrder(order);
		// 删除购物车中对应的商品
		cartService.deleteItemsInCart(order.getOrderItems(), order.getUserId());
		model.addAttribute("orderId", result.getData()); // 订单id放入模型
		return "success";
	}

	/*
	 * 用户所有的订单
	 */
	@RequestMapping("/order/order_list")
	public String showOrder(HttpServletRequest request, @RequestParam(defaultValue = "1") int currentPage,
			Model model) {
		MallUser user = (MallUser) request.getAttribute("user");
		// 调用服务获得用户的订单列表
		PageModel orderInfoResult = orderService.getOrders(user.getId(), currentPage, MALL_ORDER_PAGE_SIZE);
		orderInfoResult.setUrl("/order/order_list.html?");
		// 将订单数据封装到模型
		model.addAttribute("orderInfoResult", orderInfoResult);
		return "order_list";
	}
}
