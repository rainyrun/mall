package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallItemCat;
import top.rainyrun.mall.dao.pojo.MallItemCatExample;

public interface MallItemCatMapper {
    long countByExample(MallItemCatExample example);

    int deleteByExample(MallItemCatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MallItemCat record);

    int insertSelective(MallItemCat record);

    List<MallItemCat> selectByExample(MallItemCatExample example);

    MallItemCat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MallItemCat record, @Param("example") MallItemCatExample example);

    int updateByExample(@Param("record") MallItemCat record, @Param("example") MallItemCatExample example);

    int updateByPrimaryKeySelective(MallItemCat record);

    int updateByPrimaryKey(MallItemCat record);
}