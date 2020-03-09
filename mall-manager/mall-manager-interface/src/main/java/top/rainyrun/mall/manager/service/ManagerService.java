package top.rainyrun.mall.manager.service;

import top.rainyrun.mall.common.pojo.Result;

public interface ManagerService {
	Result login(String username, String password, String token);

	boolean isLogin(String token);
}
