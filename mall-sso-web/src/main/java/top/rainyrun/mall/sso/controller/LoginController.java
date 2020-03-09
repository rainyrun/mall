package top.rainyrun.mall.sso.controller;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallUser;

import top.rainyrun.mall.cart.service.CartService;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.CookieUtils;
import top.rainyrun.mall.common.util.JsonUtils;
import top.rainyrun.mall.sso.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private CartService cartService;
	@Value("${MALL_COOKIE_TOKEN}")
	private String MALL_COOKIE_TOKEN;
	@Value("${MALL_COOKIE_CART}")
	private String MALL_COOKIE_CART;
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;

	@RequestMapping("/user/login")
	@ResponseBody
	public Result login(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		// 检查用户是否已登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		Result result = loginService.login(username, password, token);
		if (result.getStatus() == 200) {// 登录成功
			// 将key存入cookie返回给用户。过期时间是30min(1800s)
			token = result.getData().toString();
			CookieUtils.setCookie(request, response, MALL_COOKIE_TOKEN, token, EXPIRE_TIME);
			// 查看cookie中是否有购物车信息
			String cartByJson = CookieUtils.getCookieValue(request, MALL_COOKIE_CART, true);
			if (cartByJson != null && !"".equals(cartByJson)) {
				// 有则和redis中的购物车合并
				Map<String, String> cart = JsonUtils.jsonToMap(cartByJson, String.class, String.class);
				Result userByToken = loginService.getUserByToken(token);
				MallUser user = (MallUser) userByToken.getData();
				cartService.mergeCart(cart, user.getId());
				// 清空cookie中的购物车
				CookieUtils.setCookie(request, response, MALL_COOKIE_CART, "", 0);
			}
		}
		return result;
	}

	@RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET, produces = {"text/plain;charset=UTF-8"})
	@ResponseBody
	public String getUserByToken(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) {
		assert(token != null && !"".equals(token)); // token不为空
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // 允许跨域请求
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许接收cookie
		// 重新设置cookie的过期时间
		CookieUtils.setCookie(request, response, MALL_COOKIE_TOKEN, token, EXPIRE_TIME);
		Result result = loginService.getUserByToken(token);
		MallUser user = (MallUser)result.getData();
		user.setPassword(null); // 清空密码
		result.setData(user);
		return JsonUtils.objectToJson(result);
	}

	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		loginService.logout(token);
		CookieUtils.setCookie(request, response, MALL_COOKIE_TOKEN, "", 0);
		String url = request.getHeader("Referer");
		return "redirect:" + url;
	}
}
