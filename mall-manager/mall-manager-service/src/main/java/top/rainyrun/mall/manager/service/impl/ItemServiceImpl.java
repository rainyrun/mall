package top.rainyrun.mall.manager.service.impl;

import java.awt.event.ItemEvent;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemDesc;
import top.rainyrun.mall.dao.pojo.MallItemExample;
import top.rainyrun.mall.dao.pojo.MallItemExample.Criteria;

import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.dao.mapper.MallItemDescMapper;
import top.rainyrun.mall.dao.mapper.MallItemMapper;
import top.rainyrun.mall.manager.service.ItemService;

public class ItemServiceImpl implements ItemService {
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	private MallItemMapper itemMapper;
	@Autowired
	private MallItemDescMapper itemDescMapper;

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 20;
		// 设置分页
		PageHelper.startPage(page, rows);
		// 创建 example 对象，不需要设置查询条件
		MallItemExample example = new MallItemExample();
		// 查询数据
		List<MallItem> list = itemMapper.selectByExample(example);
		// 获取分页的信息
		PageInfo<MallItem> info = new PageInfo<>(list);
		// 封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		return result;
	}

	@Override
	public void addItemAndDesc(MallItem item, MallItemDesc itemDesc) {
		itemMapper.insert(item);
		itemDescMapper.insert(itemDesc);
		// 发送消息更新索引、缓存
		amqpTemplate.convertAndSend(item.getId().toString());
	}

	@Override
	public boolean deleteItem(Long id) {
		if (itemMapper.deleteByPrimaryKey(id) > 0) {
			amqpTemplate.convertAndSend(id.toString());
			return true;
		}
		return false;
	}

	@Override
	public MallItem queryItem(Long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateItemAndDesc(MallItem item, MallItemDesc itemDesc) {
		itemMapper.updateByPrimaryKeySelective(item);
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		amqpTemplate.convertAndSend(item.getId().toString());
	}

	@Override
	public MallItemDesc queryItemDesc(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public MallItem getItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public MallItemDesc getItemDescById(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public List<MallItem> getLatestItem(int latestItemNum) {
		// 设置分页
		PageHelper.startPage(1, latestItemNum);
		// 创建 example 对象，按时间降序排
		MallItemExample itemExample = new MallItemExample();
		itemExample.setOrderByClause("created DESC");
		// 查询数据
		List<MallItem> latestItem = itemMapper.selectByExample(itemExample);
		return latestItem;
	}

//	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
//		AmqpTemplate bean = context.getBean(AmqpTemplate.class);
//		System.out.println("发送消息-----------");
//		bean.convertAndSend("1123456L");
//	}
}
