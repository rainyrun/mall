package top.rainyrun.mall.sso.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import top.rainyrun.mall.dao.pojo.MallUser;
import top.rainyrun.mall.dao.pojo.MallUserExample;
import top.rainyrun.mall.dao.pojo.MallUserExample.Criteria;

import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.dao.mapper.MallUserMapper;
import top.rainyrun.mall.sso.service.RegisterService;

public class RegisterServiceImpl implements RegisterService {
	@Autowired
	MallUserMapper userMapper;

	@Override
	public Result checkData(String param, String type) {
		MallUserExample userExample = new MallUserExample();
		Criteria criteria = userExample.createCriteria();
		switch(type) {
		case "username":
			if(StringUtils.isEmpty(param))
				return Result.build(400, "用户名不能为空");
			criteria.andUsernameEqualTo(param);
			break;
		case "password":
			if(StringUtils.isEmpty(param))
				return Result.build(400, "密码不能为空");
			return Result.ok();
		case "email":
			if(StringUtils.isEmpty(param))
				return Result.ok();
			criteria.andEmailEqualTo(param);
			break;
		case "phone":
			if(StringUtils.isEmpty(param))
				return Result.ok();
			criteria.andPhoneEqualTo(param);
			break;
		default:
			return Result.build(400, "非法参数");
		}
		List<MallUser> list = userMapper.selectByExample(userExample);
		if(list != null && list.size() > 0)
			return Result.build(400, "已被注册，请更换");
		return Result.ok();
	}

	@Override
	public Result register(MallUser user) {
		System.out.println(user);
		// 校验数据
		Map<String, String> checkMap = new HashMap<String, String>();
		checkMap.put("username", user.getUsername());
		checkMap.put("password", user.getPassword());
		checkMap.put("email", user.getEmail());
		checkMap.put("phone", user.getPhone());
		for (Entry<String, String> entry : checkMap.entrySet()) {
			Result result = checkData(entry.getValue(), entry.getKey());
			if(result.getStatus() != 200)
				return result;
		}
		// 校验成功后，注册用户
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		if(StringUtils.isEmpty(user.getEmail()))
			user.setEmail(null);
		if(StringUtils.isEmpty(user.getPhone()))
			user.setPhone(null);
		// 对密码进行MD5加密
		String md5password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5password);
		//5.插入数据
		userMapper.insertSelective(user);
		System.out.println("注册成功");
		return Result.ok();
	}

}
