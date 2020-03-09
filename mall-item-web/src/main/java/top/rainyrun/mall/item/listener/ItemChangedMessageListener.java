package top.rainyrun.mall.item.listener;

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

import freemarker.template.Configuration;
import freemarker.template.Template;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemDesc;
import top.rainyrun.mall.manager.service.ItemService;

public class ItemChangedMessageListener implements MessageListener {
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer config;
	@Value("${MALL_FILE_STORE_PATH}")
	private String MALL_FILE_STORE_PATH;
	@Value("${MALL_ITEM_FTL}")
	private String MALL_ITEM_FTL;
	
	@Override
	public void onMessage(Message message) {
		try {
			Long id = Long.parseLong(new String(message.getBody()));
			// 更新静态页面
			generateItem(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据商品id生成静态页面
	 */
    public void generateItem(Long itemId) throws Exception {
		if(itemId == null)
			return;
		// 获取数据
    	MallItem item = itemService.getItemById(itemId);
		MallItemDesc itemDesc = itemService.getItemDescById(itemId);
		// 文件路径
		File dir = new File(MALL_FILE_STORE_PATH);
		if(!dir.exists()) {
			Files.createDirectories(dir.toPath());
		}
		File path = new File(MALL_FILE_STORE_PATH + itemId + ".html");
		if(item == null) {// 商品不存在或被删除
			Files.deleteIfExists(path.toPath());
			return;
		}
        // 生成静态页面
        Configuration cfg = config.getConfiguration(); // 1.根据config  获取configuration对象
        Template template = cfg.getTemplate(MALL_ITEM_FTL); // 2.设置模板文件 加载模板文件 /WEB-INF/ftl/相对路径
        // 3.创建数据集
        Map<Object, Object> model = new HashMap<>();
        model.put("item", item);
        model.put("itemDesc", itemDesc);
        // 4.创建writer
        Writer writer = new FileWriter(path);
        // 5.调用方法输出
        template.process(model, writer);
        // 6.关闭流
        writer.close();
    }

}
