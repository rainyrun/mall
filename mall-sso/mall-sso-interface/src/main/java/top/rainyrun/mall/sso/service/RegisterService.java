package top.rainyrun.mall.sso.service;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.dao.pojo.MallUser;

public interface RegisterService {

	Result checkData(String param, String type);

	Result register(MallUser user);

}
