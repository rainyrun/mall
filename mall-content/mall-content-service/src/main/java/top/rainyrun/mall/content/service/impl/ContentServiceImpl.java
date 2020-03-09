package top.rainyrun.mall.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.gson.JsonObject;
import top.rainyrun.mall.dao.mapper.MallContentMapper;
import top.rainyrun.mall.dao.pojo.*;
import top.rainyrun.mall.dao.pojo.MallContentExample.Criteria;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.JsonUtils;
import top.rainyrun.mall.content.service.ContentService;

public class ContentServiceImpl implements ContentService {
	@Autowired
	private MallContentMapper contentMapper;
	@Autowired
	private JedisPool jedisPool;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	@Override
	public List<MallContent> getContentList(Long catId) {
		// 检查redis缓存中是否有数据
		Jedis client = jedisPool.getResource();
		String jsonValue = client.hget(CONTENT_KEY, catId.toString());
		if (!StringUtils.isEmpty(jsonValue)) {// 有缓存
			// 将缓存中的json字符串转成对象，并返回
			List<MallContent> list = JsonUtils.jsonToList(jsonValue, MallContent.class);
			client.close();
			return list;
		}
		// 无缓存，从数据库中查询内容
		MallContentExample example = new MallContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCatIdEqualTo(catId);
		List<MallContent> list = contentMapper.selectByExample(example);
		// 添加缓存
		String json = JsonUtils.objectToJson(list);
		client.hset(CONTENT_KEY, catId.toString(), json);
		client.close();
		return list;
	}

	@Override
	public boolean addContent(MallContent content) {
		int result = contentMapper.insert(content);
		if (result != 1)
			return false;
		// 添加成功，需要清除redis里对应内容分类的缓存
		Jedis client = jedisPool.getResource();
		client.hdel(CONTENT_KEY, content.getCatId().toString());
		client.close();
		return true;
	}

	@Override
	public void deleteContent(Long id) {
		MallContent content = contentMapper.selectByPrimaryKey(id);
		contentMapper.deleteByPrimaryKey(id);
		// 删除成功，需要清除redis里对应内容分类的缓存
		Jedis client = jedisPool.getResource();
		client.hdel(CONTENT_KEY, content.getCatId().toString());
		client.close();
	}

	@Override
	public boolean updateContent(MallContent content) {
		int i = contentMapper.updateByPrimaryKeySelective(content);
		if (i != 1)
			return false;
		// 更新成功，需要清除redis里对应内容分类的缓存
		Jedis client = jedisPool.getResource();
		client.hdel(CONTENT_KEY, content.getCatId().toString());
		client.close();
		return true;
	}

	@Override
	public MallContent getContent(Long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

}
