package top.rainyrun.mall.content.service;

import java.util.List;

import top.rainyrun.mall.dao.pojo.MallContent;

import top.rainyrun.mall.common.pojo.EasyUIDataGridResult;
import top.rainyrun.mall.common.pojo.Result;

public interface ContentService {

	List<MallContent> getContentList(Long catId);

	boolean addContent(MallContent content);

	void deleteContent(Long id);

	boolean updateContent(MallContent content);

	MallContent getContent(Long id);

}
