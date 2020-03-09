package top.rainyrun.mall.manager.service.impl;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.manager.service.ManagerService;

public class ManagerServiceImpl implements ManagerService {
	private HashMap<String, Long> loginManager;
	@Value("${EXPIRE_TIME}")
	private long EXPIRE_TIME;

	@Override
	public Result login(String username, String password, String token) {
		for (Entry<String, Long> entry : loginManager.entrySet()) {
			System.out.println("token: " + entry.getKey() + "; loginTime: " + entry.getValue());
		}
		System.out.println("currentToken: " + token);
		if (username == null || "".equals(username) || password == null || "".equals(password))
			return Result.build(400, "用户名或密码不能为空");
		if (username.equals("rainy") && password.equals("rainyrun")) {
			String managerToken = UUID.randomUUID().toString();
			loginManager.put(managerToken, System.currentTimeMillis());
			if(token != null && !"".equals(token)) {
				loginManager.remove(token);
			}
			return Result.ok(managerToken);
		}
		return Result.build(400, "用户名或密码错误");
	}

	@Override
	public boolean isLogin(String token) {
		if(token == null || "".equals(token))
			return false;
		Long loginTime = loginManager.get(token);
		// 未登录
		if(loginTime == null)
			return false;
		// 未过期
		if ((loginTime + EXPIRE_TIME) > System.currentTimeMillis())
			return true;
		// 已过期
		loginManager.remove(token);
		return false;
	}

	public void setLoginManager(HashMap<String, Long> loginManager) {
		this.loginManager = loginManager;
	}

}
