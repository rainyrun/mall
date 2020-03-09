package top.rainyrun.mall.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import top.rainyrun.mall.common.util.CookieUtils;
import top.rainyrun.mall.manager.service.ManagerService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private ManagerService managerService;
	@Value("${MALL_COOKIE_MANAGER_TOKEN}")
	private String MANAGER_TOKEN;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, MANAGER_TOKEN);
		if(managerService.isLogin(token)) {
			return true;
		}
		response.sendRedirect("/");
		return false;
	}
}
