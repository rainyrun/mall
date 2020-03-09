package top.rainyrun.mall.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.CookieUtils;
import top.rainyrun.mall.manager.service.ManagerService;

@Controller
public class PageController {
	@Autowired
	private ManagerService service;
	@Value("${MALL_COOKIE_MANAGER_TOKEN}")
	private String MANAGER_TOKEN;

	// 跳转到指定页面
	@RequestMapping("/{page}")
	public String toPage(@PathVariable String page) {
		return page;
	}

	// 登录验证
	@RequestMapping("/login")
	@ResponseBody
	public Result login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		String token = CookieUtils.getCookieValue(request, MANAGER_TOKEN);
		Result result = service.login(username, password, token);
		if(result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, MANAGER_TOKEN, (String)result.getData());
		}
		return result;
	}

}
