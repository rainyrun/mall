package top.rainyrun.mall.cart.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemDesc;

public class ItemChangedMessageListener implements MessageListener {
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public void onMessage(Message message) {
		try {
			Long id = Long.parseLong(new String(message.getBody()));
			// 删除redis中的商品信息
			Jedis jedis = jedisPool.getResource();
			jedis.del(id.toString());
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
