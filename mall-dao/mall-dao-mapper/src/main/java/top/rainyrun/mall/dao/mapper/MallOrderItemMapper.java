package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallOrderItem;
import top.rainyrun.mall.dao.pojo.MallOrderItemExample;

public interface MallOrderItemMapper {
    long countByExample(MallOrderItemExample example);

    int deleteByExample(MallOrderItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(MallOrderItem record);

    int insertSelective(MallOrderItem record);

    List<MallOrderItem> selectByExample(MallOrderItemExample example);

    MallOrderItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MallOrderItem record, @Param("example") MallOrderItemExample example);

    int updateByExample(@Param("record") MallOrderItem record, @Param("example") MallOrderItemExample example);

    int updateByPrimaryKeySelective(MallOrderItem record);

    int updateByPrimaryKey(MallOrderItem record);
}