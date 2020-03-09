package top.rainyrun.mall.search.listener;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import top.rainyrun.mall.search.service.SearchService;

public class ItemChangedMessageListener implements MessageListener {
	@Autowired
	SearchService searchService;

	@Override
	public void onMessage(Message message) {
		try {
			Long id = Long.parseLong(new String(message.getBody()));
			System.out.println(id);
			// 更新索引
			searchService.updateSearchItemById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
