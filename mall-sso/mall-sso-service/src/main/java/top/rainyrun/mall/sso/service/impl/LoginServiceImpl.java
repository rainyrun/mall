package top.rainyrun.mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import top.rainyrun.mall.dao.pojo.MallUser;
import top.rainyrun.mall.dao.pojo.MallUserExample;
import top.rainyrun.mall.dao.pojo.MallUserExample.Criteria;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.CookieUtils;
import top.rainyrun.mall.common.util.JsonUtils;
import top.rainyrun.mall.dao.mapper.MallUserMapper;
import top.rainyrun.mall.sso.service.LoginService;

public class LoginServiceImpl implements LoginService {
	@Autowired
	private MallUserMapper userMapper;
	@Autowired
	private JedisPool jedisPool;
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;
	@Value("${USER_INFO}")
	private String USER_INFO;

	@Override
	public Result login(String username, String password, String token) {
		Jedis jedis = jedisPool.getResource();
		if (token != null && !"".equals(token)) {
			// 注销掉原来的token
			jedis.del(USER_INFO + ":" + token);
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			jedis.close();
			return Result.build(400, "登录失败，用户名或密码错误");
		}
		// 检查用户名，密码是否存在
		MallUserExample example = new MallUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
		List<MallUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {// 登录成功
			// 生成key(随机生成)
			token = UUID.randomUUID().toString();
			// key和value(用户名)，存入redis，代表session。
			jedis.set(USER_INFO + ":" + token, JsonUtils.objectToJson(list.get(0)));
			jedis.expire(USER_INFO + ":" + token, EXPIRE_TIME);
			jedis.close();
			return Result.ok(token);
		}
		jedis.close();
		return Result.build(400, "登录失败，用户名或密码错误");
	}

	@Override
	public Result getUserByToken(String token) {
		if (token == null)
			return Result.build(400, "用户未登录");
		Jedis jedis = jedisPool.getResource();
		String userByJson = jedis.get(USER_INFO + ":" + token);
		if (userByJson == null || "".equals(userByJson)) {
			jedis.close();
			return Result.build(400, "用户未登录");
		}
		// 有，则重新设置过期时间
		jedis.expire(USER_INFO + ":" + token, EXPIRE_TIME);
		// 注1
		MallUser user = JsonUtils.jsonToPojo(userByJson, MallUser.class);
		jedis.close();
		return Result.ok(user);
	}

	@Override
	public void logout(String token) {
		if (token != null && !"".equals(token)) {
			Jedis jedis = jedisPool.getResource();
			jedis.del(USER_INFO + ":" + token);
			jedis.close();
		}
	}
}
/*
 * 注1：json串需要转成MallUser对象，否则将MytaoResult转成json串时，从前端无法取出user的信息。暂时不清楚原因，
 * 猜测可能是在pojo转成json串时，Jackson做了某些调整。
 */
