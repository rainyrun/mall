package top.rainyrun.mall.sso.service;

import top.rainyrun.mall.common.pojo.Result;

public interface LoginService {

	Result login(String username, String password, String token);

	Result getUserByToken(String token);

	void logout(String token);

}
