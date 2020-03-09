package top.rainyrun.mall.manager.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.common.pojo.Result;
import top.rainyrun.mall.content.service.ContentService;
import top.rainyrun.mall.dao.pojo.MallContent;
import top.rainyrun.mall.common.util.FastDFSClient;

@Controller
public class ContentController {
	@Autowired
	ContentService contentService;
	@Value("${MALL_IMAGE_SERVER_URL}")
	private String MALL_IMAGE_SERVER_URL;

	@RequestMapping("/content/list")
	@ResponseBody
	EasyUIDataGridResult getContentList(Long catId) {
		// 查询结果
		List<MallContent> list = contentService.getContentList(catId);
		// 构造返回值
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(list.size());
		result.setRows(list);
		return result;
	}

	@RequestMapping("/content/save")
	@ResponseBody
	Result saveContent(MallContent content, MultipartFile picContent) {
		MallContent oldContent = null;
		// 获取旧内容
		if (content.getId() == null) {
			oldContent = contentService.getContent(content.getId());
		}
		// 处理接收的图片
		if (!picContent.isEmpty()) {
			try {
				FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
				// 图片扩展名
				String extName = picContent.getContentType().split("/")[1];
				// 上传图片
				String uploadFile = client.uploadFile(picContent.getBytes(), extName);
				// 图片地址
				content.setPic(MALL_IMAGE_SERVER_URL + uploadFile);
				// 删除旧图片
				if (oldContent != null && oldContent.getPic() != null)
					client.deleteFile(oldContent.getPic().substring(oldContent.getPic().indexOf("group1")));
			} catch (Exception e) {
				e.printStackTrace();
				return Result.build(500, "图片上传失败!");
			}
		}
		// 设置更新时间
		content.setUpdated(new Date());
		// 判断是添加还是更新
		if (content.getId() == null) {// 添加内容
			content.setCreated(content.getUpdated());
			if (!contentService.addContent(content))
				return Result.build(500, "内容添加失败!");
		} else {// 更新内容
			if (!contentService.updateContent(content))
				return Result.build(500, "内容更新失败!");
		}
		return Result.ok();
	}

	@RequestMapping("/content/delete")
	@ResponseBody
	Result deleteContent(Long id) {
		MallContent content = contentService.getContent(id);
		String picPath = content.getPic();
		if(picPath != null && !"".equals(picPath)) {
			// 删除图片
			try {
				FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
				client.deleteFile(picPath.substring(content.getPic().indexOf("group1")));
			} catch (Exception e) {
				e.printStackTrace();
				return Result.build(500, "图片删除失败！");
			}
		}
		contentService.deleteContent(id);
		return Result.ok();
	}
}
