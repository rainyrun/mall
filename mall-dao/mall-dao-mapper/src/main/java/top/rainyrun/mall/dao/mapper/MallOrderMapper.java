package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallOrder;
import top.rainyrun.mall.dao.pojo.MallOrderExample;

public interface MallOrderMapper {
    long countByExample(MallOrderExample example);

    int deleteByExample(MallOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(MallOrder record);

    int insertSelective(MallOrder record);

    List<MallOrder> selectByExample(MallOrderExample example);

    MallOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") MallOrder record, @Param("example") MallOrderExample example);

    int updateByExample(@Param("record") MallOrder record, @Param("example") MallOrderExample example);

    int updateByPrimaryKeySelective(MallOrder record);

    int updateByPrimaryKey(MallOrder record);
}