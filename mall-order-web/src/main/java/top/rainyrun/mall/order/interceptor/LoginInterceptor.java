package top.rainyrun.mall.order.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import top.rainyrun.mall.sso.service.LoginService;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private LoginService loginService;
	@Value("${MALL_COOKIE_TOKEN}")
	private String MALL_COOKIE_TOKEN;
	@Value("${MALL_SSO_URL}")
	private String MALL_SSO_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, MALL_COOKIE_TOKEN);
		if(!StringUtils.isEmpty(token)) {
			Result result = loginService.getUserByToken(token);
			// 已登录，则放行
			if(result.getStatus() == 200) {
				request.setAttribute("user", result.getData());
				return true;
			}
		}
		// 未登录，跳转到登录页面，并保存拦截页面的url
//		// 构造跳转url的参数
//		String params = "";
//		Set keySet = request.getParameterMap().keySet();
//		for (Object object : keySet) {
//			String[] parameterValues = request.getParameterValues(object.toString());
//			String value = Arrays.toString(parameterValues);
//			if(!"".equals(params)) {
//				params += "&";
//			}
//			params += object.toString() + "=" + value.substring(1, value.length() - 1);
//		}
		// 设置跳转链接
		String host = request.getHeader("host");
		String uri = request.getHeader("request_uri");
		response.sendRedirect(MALL_SSO_URL + "?redirect=http://" + host + uri);
		return false;
	}

}
