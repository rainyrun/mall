package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallContent;
import top.rainyrun.mall.dao.pojo.MallContentExample;

public interface MallContentMapper {
    long countByExample(MallContentExample example);

    int deleteByExample(MallContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MallContent record);

    int insertSelective(MallContent record);

    List<MallContent> selectByExample(MallContentExample example);

    MallContent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MallContent record, @Param("example") MallContentExample example);

    int updateByExample(@Param("record") MallContent record, @Param("example") MallContentExample example);

    int updateByPrimaryKeySelective(MallContent record);

    int updateByPrimaryKey(MallContent record);
}