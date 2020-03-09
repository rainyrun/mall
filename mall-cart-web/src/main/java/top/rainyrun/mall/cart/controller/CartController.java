package top.rainyrun.mall.cart.controller;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.netty.handler.codec.http.Cookie;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.CookieUtils;
import top.rainyrun.mall.common.util.JsonUtils;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallUser;
import top.rainyrun.mall.manager.service.ItemService;
import top.rainyrun.mall.sso.service.LoginService;

import top.rainyrun.mall.cart.service.CartService;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ItemService itemService;
	@Value("${MALL_COOKIE_TOKEN}")
	private String MALL_COOKIE_TOKEN;
	@Value("${MALL_COOKIE_CART}")
	private String MALL_COOKIE_CART;
	
	@RequestMapping(value = "/cart/add/{itemId}", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addToCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://item.mall.rainyrun.top"); // 允许跨域请求
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许接收cookie
		// 参数有效性检查
		if (num == null || num < 0)
			return JsonUtils.objectToJson(Result.build(400, "商品数量有误"));
		// 获取商品
		MallItem item = itemService.getItemById(itemId);
		if (item == null)
			return JsonUtils.objectToJson(Result.build(400, "商品不存在"));
		item.setNum(num);// 复用了item.sum，此处表示购物车中的商品数量。
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		System.out.println("token: " + token);
		Result result = loginService.getUserByToken(token);
		if (result.getStatus() == 200) {
			// 已登录，调用cartService服务，将商品加入到购物车
			MallUser user = (MallUser) result.getData();
			return JsonUtils.objectToJson(cartService.addToCart(item, user.getId()));
		}
		System.out.println("未登录");
		// 未登录，从cookie中获取购物车列表，调用cartService服务，重新设置cookie
		Map<Long, Integer> cart = getCartFromCookie(request);
		Map<Long, Integer> newCart = addToCart(cart, item);
		String cartByJson = JsonUtils.objectToJson(newCart);
		System.out.println(cartByJson);
		CookieUtils.setCookie(request, response, MALL_COOKIE_CART, cartByJson,true);
		return JsonUtils.objectToJson(Result.ok());
	}

	@RequestMapping("/cart/cart")
	public String getCart(Model model, HttpServletRequest request) {
		List<MallItem> cart = null;
		// 根据cookie，判断用户是否登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		Result result = loginService.getUserByToken(token);
		if (result.getStatus() == 200) {
			// 已登录，从redis中获取购物车信息
			MallUser user = (MallUser) result.getData();
			cart = cartService.getCart(user.getId());
		} else {
			// 未登录，从cookie中获取购物车信息
			String cartByJson = CookieUtils.getCookieValue(request, MALL_COOKIE_CART, true);
			if (cartByJson != null && !"".equals(cartByJson)) {
				Map<String, String> itemMap = JsonUtils.jsonToMap(cartByJson, String.class, String.class);
				cart = cartService.getItemList(itemMap);
			}
		}
		// 将商品列表放入模型中
		model.addAttribute("cart", cart);
		return "cart";
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteItemInCart(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		Result result = loginService.getUserByToken(token);
		if (result.getStatus() == 200) {
			// 已登录，调用cartService服务
			MallUser user = (MallUser) result.getData();
			cartService.deleteItemInCart(itemId, user.getId());
		} else {
			// 未登录，从cookie中获取购物车信息
			deleteItemInCartFromCookie(itemId, request, response);
		}
		return "redirect:http://cart.mall.rainyrun.top/cart/cart.html";
	}

	@RequestMapping("/cart/update/{itemId}")
	public void updateItemInCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 参数有效性检查
		if (num == null || num < 0)
			return ;
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		Result result = loginService.getUserByToken(token);
		if (result.getStatus() == 200) {
			// 已登录，调用cartService服务
			MallUser user = (MallUser) result.getData();
			cartService.updateItemInCart(itemId, num, user.getId());
		} else {
			// 未登录，从cookie中获取购物车信息
			updateInCartFromCookie(itemId, num, request, response);
		}
	}

//	--------------------- 工具方法 ----------------------
	private Map<Long, Integer> addToCart(Map<Long, Integer> cart, MallItem item) {
		if (cart == null)
			cart = new HashMap<Long, Integer>();
		boolean flag = false;
		for (Long itemId : cart.keySet()) {
			if (itemId.equals(item.getId())) {
				// 商品在购物车中，则增加数量
				int itemNum = cart.get(itemId);
				cart.put(itemId, itemNum + item.getNum());
				flag = true;
				break;
			}
		}
		// 商品不在购物车中，则增加商品
		if (!flag) {
			cart.put(item.getId(), item.getNum());
		}
		return cart;
	}

	private Map<Long, Integer> getCartFromCookie(HttpServletRequest request) {
		String cartByJson = CookieUtils.getCookieValue(request, MALL_COOKIE_CART, true);
		System.out.println("cookie中的购物车");
		System.out.println(cartByJson);
		Map<Long, Integer> cartMap = null;
		if (!StringUtils.isEmpty(cartByJson)) {
			cartMap = JsonUtils.jsonToMap(cartByJson, Long.class, Integer.class);
		}
		return cartMap;
	}

	private void deleteItemInCartFromCookie(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		String cartByJson = CookieUtils.getCookieValue(request, MALL_COOKIE_CART, true);
		if (cartByJson != null && !"".equals(cartByJson)) {
			Map<Long, Integer> cartMap = JsonUtils.jsonToMap(cartByJson, Long.class, Integer.class);
			Integer removed = cartMap.remove(itemId);
			if (removed > 0) { // 有删除商品，则重新设置cookie
				CookieUtils.setCookie(request, response, MALL_COOKIE_CART, JsonUtils.objectToJson(cartMap), true);
			}
		}
	}

	private void updateInCartFromCookie(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		String cartByJson = CookieUtils.getCookieValue(request, MALL_COOKIE_CART, true);
		if (cartByJson != null && !"".equals(cartByJson)) {// 有购物车数据
			Map<Long, Integer> cart = JsonUtils.jsonToMap(cartByJson, Long.class, Integer.class);
			cart.put(itemId, num);
			CookieUtils.setCookie(request, response, MALL_COOKIE_CART, JsonUtils.objectToJson(cart), true);
		}
	}

}
