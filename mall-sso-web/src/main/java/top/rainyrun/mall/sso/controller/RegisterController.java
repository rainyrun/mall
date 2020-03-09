package top.rainyrun.mall.sso.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.rainyrun.mall.dao.pojo.MallUser;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.sso.service.RegisterService;

@Controller
public class RegisterController {
	@Autowired
	RegisterService registerService;

	// 跳转到注册页
	@RequestMapping("/page/{page}")
	String toPage(@PathVariable String page, String redirect, HttpServletRequest request, Model model) {
		if(redirect == null || redirect.equals("")) {
			redirect = request.getHeader("Referer");
			System.out.println("redirect: " + redirect);
		}
		model.addAttribute("redirect", redirect);
		return page;
	}

	// 校验输入的内容，用户名、邮箱、手机号不能重复
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	Result checkData(@PathVariable String param, @PathVariable String type) {
		System.out.println("验证"+type);
		return registerService.checkData(param, type);
	}

	// 注册用户
	@RequestMapping("/user/register")
	@ResponseBody
	Result register(MallUser user) {
		return registerService.register(user);
	}

}
