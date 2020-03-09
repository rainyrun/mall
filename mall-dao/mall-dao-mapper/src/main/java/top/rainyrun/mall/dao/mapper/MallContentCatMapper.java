package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallContentCat;
import top.rainyrun.mall.dao.pojo.MallContentCatExample;

public interface MallContentCatMapper {
    long countByExample(MallContentCatExample example);

    int deleteByExample(MallContentCatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MallContentCat record);

    int insertSelective(MallContentCat record);

    List<MallContentCat> selectByExample(MallContentCatExample example);

    MallContentCat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MallContentCat record, @Param("example") MallContentCatExample example);

    int updateByExample(@Param("record") MallContentCat record, @Param("example") MallContentCatExample example);

    int updateByPrimaryKeySelective(MallContentCat record);

    int updateByPrimaryKey(MallContentCat record);
}