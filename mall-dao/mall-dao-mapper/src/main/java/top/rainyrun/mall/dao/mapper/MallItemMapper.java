package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallItem;
import top.rainyrun.mall.dao.pojo.MallItemExample;

public interface MallItemMapper {
    long countByExample(MallItemExample example);

    int deleteByExample(MallItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MallItem record);

    int insertSelective(MallItem record);

    List<MallItem> selectByExample(MallItemExample example);

    MallItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MallItem record, @Param("example") MallItemExample example);

    int updateByExample(@Param("record") MallItem record, @Param("example") MallItemExample example);

    int updateByPrimaryKeySelective(MallItem record);

    int updateByPrimaryKey(MallItem record);
}