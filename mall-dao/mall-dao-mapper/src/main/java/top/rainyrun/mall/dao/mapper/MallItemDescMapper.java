package top.rainyrun.mall.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.rainyrun.mall.dao.pojo.MallItemDesc;
import top.rainyrun.mall.dao.pojo.MallItemDescExample;

public interface MallItemDescMapper {
    long countByExample(MallItemDescExample example);

    int deleteByExample(MallItemDescExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(MallItemDesc record);

    int insertSelective(MallItemDesc record);

    List<MallItemDesc> selectByExampleWithBLOBs(MallItemDescExample example);

    List<MallItemDesc> selectByExample(MallItemDescExample example);

    MallItemDesc selectByPrimaryKey(Long itemId);

    int updateByExampleSelective(@Param("record") MallItemDesc record, @Param("example") MallItemDescExample example);

    int updateByExampleWithBLOBs(@Param("record") MallItemDesc record, @Param("example") MallItemDescExample example);

    int updateByExample(@Param("record") MallItemDesc record, @Param("example") MallItemDescExample example);

    int updateByPrimaryKeySelective(MallItemDesc record);

    int updateByPrimaryKeyWithBLOBs(MallItemDesc record);

    int updateByPrimaryKey(MallItemDesc record);
}