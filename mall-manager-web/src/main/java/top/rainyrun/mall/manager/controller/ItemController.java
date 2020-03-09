package top.rainyrun.mall.manager.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.common.util.IDUtils;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemDesc;
import top.rainyrun.mall.manager.service.ItemService;
import top.rainyrun.mall.common.util.FastDFSClient;

@Controller
public class ItemController {
	@Value("${MALL_IMAGE_SERVER_URL}")
	private String MALL_IMAGE_SERVER_URL;
	@Value("${FDFS_CLIENT_CONF}")
	private String FDFS_CLIENT_CONF;

	@Autowired
	ItemService itemService;

	@RequestMapping("/item/add")
	@ResponseBody
	public Result addItem(MallItem item, MallItemDesc itemDesc, MultipartFile imageContent) {
		if(item == null)
			return Result.build(400, "请先填写商品信息");
		try {
			// 获取图片的扩展名
			String file_ext_name = imageContent.getContentType().split("/")[1];
			// 创建fastDFS客户端
			FastDFSClient dfsClient = new FastDFSClient(FDFS_CLIENT_CONF);
			// 上传图片
			String imageUrl = dfsClient.uploadFile(imageContent.getBytes(), file_ext_name);
			// 配置商品信息
			item.setImage(MALL_IMAGE_SERVER_URL + imageUrl);
			item.setId(IDUtils.genItemId());
			item.setStatus((byte) 1);
			item.setCreated(new Date());
			item.setUpdated(item.getCreated());
			// 设置商品描述信息
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(itemDesc.getCreated());
			itemDesc.setItemId(item.getId());
			// 存入数据库
			itemService.addItemAndDesc(item, itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(400, "添加商品失败");
		}
		return Result.ok();
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		return itemService.getItemList(page, rows);
	}

	@RequestMapping("/item/delete")
	@ResponseBody
	public Result deleteItem(Long id) {
		// 获取商品
		MallItem item = itemService.queryItem(id);
		if (item == null) {
			return Result.build(404, "没有该商品，请刷新页面");
		}

		if (!StringUtils.isEmpty(item.getImage())) {
			try {
				FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
				// 删除图片
				int index = item.getImage().indexOf("group1");
				if (index != -1) {
					String fileId = item.getImage().substring(index);
					client.deleteFile(fileId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return Result.build(500, "删除图片失败，请重试");
			}
		}
		// 删除商品
		if (itemService.deleteItem(id)) {
			return Result.ok();
		} else {
			return Result.build(500, "删除商品失败，请重试");
		}
	}

	@RequestMapping("/item/update")
	@ResponseBody
	public Result updateItem(MallItem item, String itemDesc, MultipartFile imageContent) {
		if(item == null)
			return Result.build(400, "请先填写商品信息");
		try {
			// 获取原商品
			MallItem oldItem = itemService.queryItem(item.getId());
			if(oldItem == null)
				return Result.build(400, "商品不存在");
			String oldItemImage = oldItem.getImage();
			if (imageContent.getSize() != 0) { // 图片被更新，则删除原图，替换新图
				// 创建fastDFS客户端
				FastDFSClient dfsClient = new FastDFSClient("classpath:properties/client.conf");
				// 获取图片的扩展名
				String file_ext_name = imageContent.getContentType().split("/")[1];
				// 上传图片
				String imageUrl = dfsClient.uploadFile(imageContent.getBytes(), file_ext_name);
				// 配置商品信息
				item.setImage(MALL_IMAGE_SERVER_URL + imageUrl);
				// 删除原图
				String fileId = oldItemImage.substring(oldItemImage.indexOf("group1"));
				dfsClient.deleteFile(fileId);
			}
			item.setUpdated(new Date());
			// 设置商品描述信息
			MallItemDesc newItemDesc = new MallItemDesc();
			newItemDesc.setItemId(item.getId());
			newItemDesc.setItemDesc(itemDesc);
			newItemDesc.setUpdated(new Date());
			// 更新数据库
			itemService.updateItemAndDesc(item, newItemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(500, "添加商品失败");
		}
		return Result.ok();
	}

	/**
	 * 获取商品描述
	 */
	@RequestMapping("/item/desc")
	@ResponseBody
	public Result getItemDesc(Long itemId) {
		return Result.ok(itemService.queryItemDesc(itemId).getItemDesc());
	}

}
